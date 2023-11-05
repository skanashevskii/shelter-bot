package ru.devpro.dto;



import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import ru.devpro.enums.AnimalType;
import ru.devpro.model.Avatar;


import java.time.LocalDateTime;

@Data
public class AnimalDTO {
    private Long id;

    private String name;

    private AnimalType type_animal;

    private String breed;

    private String text;

    private LocalDateTime dateTime;
    //@JsonIgnore
    private UserDTO userDTO;

    private Avatar avatar;


}
