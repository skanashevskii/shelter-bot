package ru.devpro.model;

import jakarta.persistence.*;
import lombok.*;
import ru.devpro.enums.AccessLevel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "reports")
@Data

public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "report_date")
    private LocalDateTime reportDate;  // Дата отчета

    @Column(name = "file_path")
    private String filePath;  // Путь к файлу отчета

    @Enumerated(EnumType.STRING)
    @Column(name = "access_level")
    private AccessLevel accessLevel;  // Поле для управления доступом

    @Column(name="report_time", nullable = false)
    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;  // Ссылка на владельца отчета

    @ManyToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;

    public Report() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return Objects.equals(id, report.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}