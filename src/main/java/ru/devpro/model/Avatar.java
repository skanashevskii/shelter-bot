package ru.devpro.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "avatar")
@Data

public class Avatar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "путь к файлу на диске(оригиналу)")
    private String filePath;

    @Schema(description = "размер файла")
    private long fileSize;

    @Schema(description = "Тип файла")
    private String mediaType;

    @Lob
    @Schema(description = "массив байт(хранение в БД)")
    private byte[] preview;

    @OneToOne(fetch = FetchType.EAGER)
    private Animal animal;

    public Avatar() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Avatar avatar = (Avatar) o;
        return Objects.equals(id, avatar.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
//образец для сох в БД при Report
/*    var message = update.message();
        if (message.photo() != null) {
                var photo = update.message().photo()[3]; // 3 - самое лучшее качество
                var getFile = bot.execute(new GetFile(photo.fileId()));
                try (var in = new URL(bot.getFullFilePath(getFile.file())).openStream();
                var out = new FileOutputStream(photo.fileId())) {
                 // для примера просто сделал случайное название файла,
                  лучше прописать путь и расширение

                byte[] bytes = in.readAllBytes();

                in.transferTo(out);
                } catch (IOException e) {
                throw new RuntimeException(e);
                }
                }*/
