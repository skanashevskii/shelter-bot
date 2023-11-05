package ru.devpro.model;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import ru.devpro.enums.AccessLevel;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;



@Entity
@Table(name = "app_user")
@Data

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="chat_id",nullable = false)
    private Long chatId;

    @Schema(description = "Имя")
    @Column(name="name",nullable = false)
    @NotEmpty(message = "name cannot be empty")
    @Size(min = 1, max = 50)
    private String name;

    @Schema(description = "Фамилия")
    @Column(name="family",nullable = false)
    @NotEmpty(message = "family cannot be empty")
    @Size(min = 1, max = 50)
    private String family;

    @Schema(description = "Роль пользователя (owner/volunteer)")
    @Column(name="role",nullable = false)
    @NotEmpty(message = "Role cannot be empty")
    @Size(min = 1, max = 50)
    private String role;

    @Schema(description = "Телефон")
    @Column(name="telephone_number",nullable = false)
    @Pattern(regexp = "^[0-9]{7,15}$", message = "Invalid phone number format")
    @Size(min = 7, max = 15, message = "Phone number must be between 7 and 15 characters")
    private String telephone;

    @Schema(description = "e-mail")
    @Column(name="email",nullable = false)
    @Email(message = "Не правильный Email",
            regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    @NotEmpty(message = "Email cannot be empty")
    @Size(min = 10, max = 50)
    private String email;

    @Column(name="user_time", nullable = false)
    private LocalDateTime dateTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "access_level")
    private AccessLevel accessLevel;

    @ManyToOne
    @JoinColumn(name = "shelter_id")
    private Shelter shelter;

    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
    private Set<Animal> animals;
    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
    private List<Report> reports;

    public User() {
    }

    public User(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
