package ru.devpro.service;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.devpro.dto.AnimalDTO;
import ru.devpro.mapers.AnimalMapper;
import ru.devpro.model.Animal;
import ru.devpro.model.Avatar;
import ru.devpro.repositories.AvatarRepository;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarServiceImpl implements AvatarService {
    @Value("${path.to.avatars.folder}")
    private String avatarsDir;
    private final AvatarRepository avatarRepository;
    private final AnimalService animalService;
    private final AnimalMapper animalMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(AvatarServiceImpl.class);

    public AvatarServiceImpl(AvatarRepository avatarRepository, AnimalService animalService) {
        this.avatarRepository = avatarRepository;
        this.animalService = animalService;
        this.animalMapper = AnimalMapper.INSTANCE;
    }

   /* public void uploadAvatar(Long animalId, MultipartFile avatarFile) throws IOException {
        if (animalId == null || avatarFile == null) {
            throw new IllegalArgumentException("Invalid animalId or avatarFile");
        }
        LOGGER.info("Uploading avatar for student with ID: {}", animalId);

        AnimalDTO animalDTO = animalService.findAnimalById(animalId);

        // Преобразовать AnimalDTO в Animal с использованием маппера
        Animal animal = animalMapper.toEntity(animalDTO);

        //путь к папке где аватар и берем расширение файла(т е после ".")
        Path filePath = Path.of(avatarsDir, animalId + "." + getExtensions(avatarFile.getOriginalFilename()));
        //проверка папки по указанному пути и если нет то создаст
        Files.createDirectories(filePath.getParent());
        //удаление старой версии файла если есть
        Files.deleteIfExists(filePath);


        try (
                //открытие входного потока
                InputStream is = avatarFile.getInputStream();
                //создание файла для записи
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                //расширяем поток с размером 1024
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                //запись
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            //передача данных + закрытие потока
            bis.transferTo(bos);
        }
        //заполняем обьект
        Avatar avatar = findAvatar(animalId);
        avatar.setAnimal(animal);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        //переформатируем в нужный размер исходный файл
        avatar.setPreview(generateImagePreview(filePath));
        //сохраняем в БД
        avatarRepository.save(avatar);
        LOGGER.info("Avatar uploaded successfully for animal with ID: {}", animalId);
    }*/

    public void uploadAvatar(MultipartFile avatarFile) throws IOException {
        if (avatarFile == null) {
            throw new IllegalArgumentException("Invalid animalId or avatarFile");
        }

        //AnimalDTO animalDTO = animalService.findAnimalById(animalId);

        // Преобразовать AnimalDTO в Animal с использованием маппера
        //Animal animal = animalMapper.toEntity(animalDTO);

        //путь к папке где аватар и берем расширение файла(т е после ".")
        Path filePath = Path.of(avatarsDir,  "." + getExtensions(avatarFile.getOriginalFilename()));
        //проверка папки по указанному пути и если нет то создаст
        Files.createDirectories(filePath.getParent());
        //удаление старой версии файла если есть
        Files.deleteIfExists(filePath);


        try (
                //открытие входного потока
                InputStream is = avatarFile.getInputStream();
                //создание файла для записи
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                //расширяем поток с размером 1024
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                //запись
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            //передача данных + закрытие потока
            bis.transferTo(bos);
        }
        //заполняем обьект
        Avatar avatar = new Avatar();

        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        //переформатируем в нужный размер исходный файл
        avatar.setPreview(generateImagePreview(filePath));
        //сохраняем в БД
        avatarRepository.save(avatar);
        //LOGGER.info("Avatar uploaded successfully for animal with ID: {}", animalId);
    }



    /**
     * findAvatar - метод поиска картинки у студента
     *
     * @param animalId - индитификатор животного
     * @return возврашает найденное или создает новый
     */
    public Avatar findAvatar(Long animalId) {
        LOGGER.info("Finding avatar for student with ID: {}", animalId);
        return avatarRepository.findById(animalId).orElse(new Avatar());
    }
    public Page<Avatar> listAvatars(Pageable pageable) {
        LOGGER.info("Listing avatars with pagination: page={}, size={}",
                pageable.getPageNumber(),
                pageable.getPageSize());
        return avatarRepository.findAll(pageable);
    }

    /**
     * generateImagePreview - создание уменьшенного изображения
     *
     * @param filePath - путь к файлу искомому
     * @return -
     * @throws IOException
     */
    public byte[] generateImagePreview(Path filePath) throws IOException {
        LOGGER.info("Generating image preview for file: {}", filePath);
        try (InputStream is = Files.newInputStream(filePath);
            BufferedInputStream bis = new BufferedInputStream(is, 1024);
            ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            BufferedImage image = ImageIO.read(bis);

            int height = image.getHeight() / (image.getWidth() / 100);
            BufferedImage preview = new BufferedImage(100, height, image.getType());
            Graphics2D graphics2D = preview.createGraphics();
            graphics2D.drawImage(image, 0, 0, 100, height, null);
            graphics2D.dispose();

            ImageIO.write(preview, getExtensions(filePath.getFileName().toString()), baos);
            LOGGER.info("Image preview generated for file: {}", filePath);
            return baos.toByteArray();
        }

    }

    private String getExtensions(String fileName) {
        LOGGER.debug("Getting extension for file name: {}", fileName);
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
        LOGGER.debug("Extension found: {}", extension);
        return extension;
    }
}