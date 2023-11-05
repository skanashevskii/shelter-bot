package ru.devpro.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.devpro.dto.ReportDTO;
import ru.devpro.dto.UserDTO;
import ru.devpro.enums.AccessLevel;

import ru.devpro.mapers.ReportMapper;
import ru.devpro.model.Report;

import ru.devpro.repositories.ReportRepository;
import ru.devpro.repositories.UsersRepository;


import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private final ReportRepository reportRepository;
    private final UsersRepository usersRepository;
    private final ReportMapper reportMapper;

    public ReportServiceImpl(ReportRepository reportRepository, UsersRepository usersRepository) {
        this.reportRepository = reportRepository;
        this.usersRepository = usersRepository;
        this.reportMapper = ReportMapper.INSTANCE;
    }

    public List<ReportDTO> getReportsForUser(UserDTO userDTO) {
        AccessLevel userAccessLevel = userDTO.getAccessLevel();

        if (AccessLevel.OWNER.equals(userAccessLevel)) {
            List<Report> reports = reportRepository.findByAccessLevelIn(Arrays.asList(AccessLevel.OWNER, AccessLevel.BOTH));
            return reports.stream()
                    .map(reportMapper::toDTO)
                    .collect(Collectors.toList());
        } else if (AccessLevel.VOLUNTEER.equals(userAccessLevel)) {
            List<Report> reports = reportRepository.findByAccessLevelIn(Arrays.asList(AccessLevel.VOLUNTEER, AccessLevel.BOTH));
            return reports.stream()
                    .map(reportMapper::toDTO)
                    .collect(Collectors.toList());
        }

        return Collections.emptyList();
    }

    @Override
    public ReportDTO createReport(ReportDTO reportDTO) {
        var user = usersRepository.findById(reportDTO.getId()).orElseThrow(()->
                new IllegalStateException("User not found"));
        reportDTO.setReportDate(LocalDateTime.now());
        Report report = reportMapper.toEntity(reportDTO);
        report.setUser(user);
        Report savedReport = reportRepository.save(report);
        return reportMapper.toDTO(savedReport);
    }

    @Override
    public ReportDTO editReport(ReportDTO reportDTO) {
        Report report = reportMapper.toEntity(reportDTO);

        // Попытка найти существующий отчет по его ID
        Optional<Report> existingReport = reportRepository.findById(report.getId());

        if (existingReport.isPresent()) {
            Report updatedReport = existingReport.get();
            // Вносим необходимые изменения в updatedReport,
            // например, название, содержание и другие поля.
            updatedReport.setReportDate(report.getReportDate());
            updatedReport.setFilePath(report.getFilePath());
            updatedReport.setAccessLevel(report.getAccessLevel());
            updatedReport.setUser(report.getUser());

            // Сохраняем обновленный отчет в репозитории
            updatedReport = reportRepository.save(updatedReport);

            // Преобразем обновленный отчет обратно в ReportDTO
            return reportMapper.toDTO(updatedReport);
        } else {
            // Обработка случая, если отчет не найден
            throw new EntityNotFoundException("Отчет с ID " + report.getId() + " не найден");
        }
    }


    @Override
    public void deleteReport(Long reportId) {
        reportRepository.deleteById(reportId);
    }

    @Override
    public ReportDTO findReportById(Long reportId) {
        Optional<Report> reportOptional = reportRepository.findById(reportId);
        return reportOptional.map(reportMapper::toDTO).orElse(null);
    }

    @Override
    public Collection<ReportDTO> findAll() {
        List<Report> reports = reportRepository.findAll();
        return reports.stream().map(reportMapper::toDTO).collect(Collectors.toList());
    }
}
