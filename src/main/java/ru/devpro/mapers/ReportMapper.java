package ru.devpro.mapers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import ru.devpro.dto.ReportDTO;

import ru.devpro.model.Report;

@Mapper
public interface ReportMapper {
    ReportMapper INSTANCE = Mappers.getMapper(ReportMapper.class);
    Report toEntity(ReportDTO reportDTO); // Метод для преобразования DTO в сущность
    ReportDTO toDTO(Report report); // Метод для преобразования сущности в DTO
}
