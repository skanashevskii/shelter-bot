package ru.devpro.service;


import ru.devpro.dto.ReportDTO;


import java.util.Collection;

public interface ReportService {
    ReportDTO createReport(ReportDTO reportDTO); // Метод для создания отчета

    ReportDTO editReport(ReportDTO reportDTO);

    void deleteReport(Long repostId);

    ReportDTO findReportById(Long reportId);

    Collection<ReportDTO> findAll();
}
