package ru.devpro.dto;



import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.devpro.model.ShelterLocation;


import java.time.LocalDateTime;



@Data
public class ShelterDTO {
    private Long id;
    private String name;
    private String safety;
    private LocalDateTime dateTime;
    @ToString.Exclude
    private ShelterLocationDTO shelterLocationDTO;



}
