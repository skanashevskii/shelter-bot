package ru.devpro.dto;


import lombok.Data;
import lombok.ToString;


import java.time.LocalDateTime;


@Data
public class ShelterLocationDTO {
    private Long id;
    private String address;
    private String city;
    private String state;
    private String zipcode;
    private LocalDateTime dateTime;
    @ToString.Exclude
    private ShelterDTO shelterDTO;


}
