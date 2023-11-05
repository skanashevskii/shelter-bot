package ru.devpro.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;


import ru.devpro.enums.AccessLevel;



import java.time.LocalDateTime;


@Data
public class ReportDTO {

    private Long id;
    private LocalDateTime reportDate;  // Дата отчета
    private String filePath;  // Путь к файлу отчета
    private AccessLevel accessLevel;  // Поле для управления доступом
    private LocalDateTime dateTime;
    //@JsonIgnore
    private UserDTO userDTO;  // Ссылка на владельца отчета
    //@JsonIgnore
    private AnimalDTO animalDTO;


}
