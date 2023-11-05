package ru.devpro.listener.handlersbd;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;

import com.pengrad.telegrambot.model.request.InputFile;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Component;

import org.springframework.web.multipart.MultipartFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.devpro.dto.AnimalDTO;
import ru.devpro.dto.ShelterDTO;
import ru.devpro.dto.ShelterLocationDTO;
import ru.devpro.dto.UserDTO;
import ru.devpro.enums.AccessLevel;
import ru.devpro.enums.AnimalType;
import ru.devpro.enums.State;
import ru.devpro.listener.Command;
import ru.devpro.model.Avatar;
import ru.devpro.repositories.AnimalsRepository;
import ru.devpro.repositories.AvatarRepository;
import ru.devpro.service.*;

import java.io.*;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;


@Component
public class ChangeBD implements Command {
    private final TelegramBot bot;

    private final Map<Long, State> stateMap = new HashMap<>();
    private final Map<Long, ShelterLocationDTO> shelterLocationPending = new HashMap<>();
    private final Map<Long, ShelterDTO> shelterPending = new HashMap<>();
    private final Map<Long, UserDTO> userPending = new HashMap<>();
    private final Map<Long, AnimalDTO> amimalPending = new HashMap<>();
    // Создание Map для отслеживания ожидающих фотографий
    private final Map<Long, Avatar> avatarPending = new HashMap<>();
    private final ShelterLocationService shelterLocationService;
    private final ShelterService shelterService;
    private final UserService userService;
    private final AnimalService animalService;
    private final AvatarRepository avatarRepository;
    private final AvatarService avatarService;
    private final AnimalsRepository animalsRepository;

    public ChangeBD(TelegramBot bot, ShelterLocationService shelterLocationService, ShelterService shelterService, UserService userService, AnimalService animalService, AvatarRepository avatarRepository, AvatarService avatarService, AnimalsRepository animalsRepository) {
        this.bot = bot;
        this.shelterLocationService = shelterLocationService;
        this.shelterService = shelterService;
        this.userService = userService;
        this.animalService = animalService;
        this.avatarRepository = avatarRepository;
        this.avatarService = avatarService;
        this.animalsRepository = animalsRepository;
    }


    public boolean ifSuitable(Update update) {
        return update != null && update.message() != null;
    }

    public void handle(Update update) throws TelegramApiException {
        System.out.println("Handling update...");
       if(ifSuitable(update)){
        if (update.message()!= null) {
            var chatId = update.message().chat().id();
            var text = update.message().text();
            System.out.println("Received text: " + text);

            State state = Arrays.stream(State.values())
                    .filter(s -> s.name().equalsIgnoreCase(text))
                    .findFirst()
                    .orElse(null);
            System.out.println("Received text: " + state);
            if (stateMap.containsKey(chatId)) {
                //handleAddShelterLocationInput(chatId, text);
                handleAddShelterInput(chatId, text);
                handleAddUserInput(chatId, text);
                handleAddAnimalInput(chatId, text);
            }
        }else if(update.message().photo()!=null){
            handlePhotoUpdate(update);

           }
        }
    }

    //Создание приюта
    public void createShelter(Long chatId) {
        // Создаем новый объект ShelterDTO
        ShelterDTO shelter = new ShelterDTO();
        // Создаем новый объект ShelterLocationDTO
        ShelterLocationDTO shelterLocation = new ShelterLocationDTO();
        // Устанавливаем текущее время в поле dateTime
        shelter.setDateTime(LocalDateTime.now());
        shelterLocation.setDateTime(LocalDateTime.now());
        // Устанавливаем состояние ожидания названия
        stateMap.put(chatId, State.AWAITING_NAME);
        // Сохраняем созданный объект в shelterPendingShelter
        shelterPending.put(chatId, shelter);
        shelterLocationPending.put(chatId, shelterLocation);
        // Выводим сообщение и запрашиваем у пользователя имя приюта
        System.out.println("State set to  for chatId: " + chatId);
        sendResponseMessage(chatId, "Пожалуйста, введите название приюта:");


    }

    //Создание животного
    public void createAnimalWithAvatar(Long chatId) {
        // Создаем новый объект AnimalDTO
        AnimalDTO animal = new AnimalDTO();

        // Устанавливаем текущее время в поле dateTime
        animal.setDateTime(LocalDateTime.now());

        // Устанавливаем состояние ожидания имени
        stateMap.put(chatId, State.AWAITING_ANIMAL_NAME);
        // Сохраняем созданный объект в animalPending
        amimalPending.put(chatId, animal);

        // Выводим сообщение и запрашиваем  имя
        System.out.println("State set to  for chatId: " + chatId);
        sendResponseMessage(chatId, "Пожалуйста, введите имя животного:");
    }

    /*
     * Создание пользователя*/
    public void createUser(Long chatId) {
        // Создаем новый объект UserDTO
        UserDTO user = new UserDTO();
        user.setChatId(chatId);
        // Устанавливаем текущее время в поле dateTime
        user.setDateTime(LocalDateTime.now());

        // Устанавливаем состояние ожидания имени
        stateMap.put(chatId, State.AWAITING_USER_NAME);
        // Сохраняем созданный объект в userPending
        userPending.put(chatId, user);

        // Выводим сообщение и запрашиваем  имя
        System.out.println("State set to  for chatId: " + chatId);
        sendResponseMessage(chatId, "Пожалуйста, введите имя пользователя:");


    }
    private void handlePhotoUpdate(Update update) {
        long chatId = update.message().chat().id();
        var photo = update.message().photo()[2]; // 3 - самое лучшее качество
        var getFile = bot.execute(new GetFile(photo.fileId()));

        String photoFileName = photo.fileId() + ".jpg"; // Пример имени файла и расширения
        String photoFilePath = "./avatars/" + photoFileName; // Замените на реальный путь

        try (var in = new URL(bot.getFullFilePath(getFile.file())).openStream();
             var out = new FileOutputStream(photo.fileId())) {
            byte[] bytes = in.readAllBytes();
            in.transferTo(out);
            // Сохраните информацию о фотографии в сущности Avatar
            Avatar avatar = new Avatar();
            avatar.setFilePath(photoFilePath);
            avatar.setFileSize(bytes.length);
            avatar.setMediaType("image/jpeg"); // Замените на реальный MIME-тип
            avatar.setPreview(bytes);


            // Сохраните сущность Avatar в Map
            avatarPending.put(chatId,avatar);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void handleAddUserInput(Long chatId, String text) throws TelegramApiException {
        UserDTO user = userPending.get(chatId);
        State currentState = stateMap.get(chatId);


        switch (currentState) {
            case AWAITING_USER_NAME -> {
                user.setName(text);
                stateMap.put(chatId, State.AWAITING_FAMILY); // Переход к фамилии
                sendResponseMessage(chatId, "Пожалуйста, введите Фамилию:");
            }
            case AWAITING_FAMILY -> {
                user.setFamily(text);
                stateMap.put(chatId, State.AWAITING_ROLE);
                sendResponseMessage(chatId, "Пожалуйста, введите роль пользователя:");
            }
            case AWAITING_ROLE -> {
                AccessLevel role = getAccessLevelFromText(text);
                if (role != null) {
                    user.setRole(role.name()); // Используйте name() для получения имени Enum в виде строки
                    stateMap.put(chatId, State.AWAITING_PHONE);
                    sendResponseMessage(chatId, "Пожалуйста, введите телефон пользователя:");
                } else {
                    sendResponseMessage(chatId, "Некорректная роль пользователя. Пожалуйста, введите роль снова:");
                }
            }
            case AWAITING_PHONE -> {
                user.setTelephone(text);
                stateMap.put(chatId, State.AWAITING_EMAIL);
                sendResponseMessage(chatId, "Пожалуйста, e-mail пользователя:");
            }
            case AWAITING_EMAIL -> {
                user.setEmail(text);
                // Все поля заполнены, добавляем данные в базу

                addUserToDatabase(user);
                userPending.remove(chatId);
                stateMap.remove(chatId);
                sendResponseMessage(chatId, "Данные пользователя успешно добавлены в базу.");
            }
        }
    }

    private void handleAddAnimalInput(Long chatId, String text) throws TelegramApiException {
        AnimalDTO animal = amimalPending.get(chatId);
        State currentState = stateMap.get(chatId);


        switch (currentState) {
            case AWAITING_ANIMAL_NAME -> {
                animal.setName(text);
                stateMap.put(chatId, State.AWAITING_TYPE); // Переход к типу
                sendResponseMessage(chatId, "Пожалуйста, введите тип животного:");
            }
            case AWAITING_TYPE -> {
                AnimalType type = getTypeAnimalFromText(text);
                if (type != null) {
                    animal.setType_animal(type);
                    stateMap.put(chatId, State.AWAITING_BREED);
                    sendResponseMessage(chatId, "Пожалуйста, введите породу:");
                } else {
                    sendResponseMessage(chatId, "Некорректный тип животного(CAT/DOG)." +
                            " Пожалуйста, введите тип снова:");
                }
            }
            case AWAITING_BREED -> {
                animal.setBreed(text);
                stateMap.put(chatId, State.AWAITING_ANIMAL_TEXT);
                sendResponseMessage(chatId, "Пожалуйста, введите краткое описание животного:");

            }

            case AWAITING_ANIMAL_TEXT -> {
                animal.setText(text);
                stateMap.put(chatId, State.AWAITING_ANIMAL_PHOTO);
                sendResponseMessage(chatId, "Пожалуйста, добавьте фото животного:");
            }
            case AWAITING_ANIMAL_PHOTO ->{

                Avatar avatar = avatarPending.get(chatId);
                if (avatar != null) {
                    // Сохранение фото животного в базе данных
                    /*try {
                       //Update update=update.message().photo()[3];
                        //var photo = update.message().photo()[3]; // 3 - самое лучшее качество
                       // var getFile = bot.execute(new GetFile(photo.fileId()));
                       // saveAnimalWithAvatar(chatId, animal, avatar, update);
                        sendResponseMessage(chatId, "Данные животного успешно добавлены в базу.");
                   // } catch (IOException e) {
                         handleUploadError(chatId);
                    }*/
                } else {
                    sendResponseMessage(chatId, "Вы должны загрузить фото животного.");
                }
            }
        }
    }

    private void saveAnimalWithAvatar(Long chatId, AnimalDTO animal, Avatar avatar, Update update) throws IOException {
        var photo = update.message().photo()[3]; // 3 - самое лучшее качество
        var getFile = bot.execute(new GetFile(photo.fileId()));

        // Получить URL для загрузки файла
        String fileUrl = bot.getFullFilePath(getFile.file());

        try (InputStream in = new URL(fileUrl).openStream();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            //MultipartFile photoFile = createMultipartFile(outputStream.toByteArray(), "photo.jpg");

            //avatarService.uploadAvatar(photoFile);
            avatarRepository.save(avatar);
            animal.setAvatar(avatar);
            avatarPending.remove(chatId);
            amimalPending.remove(chatId);
            stateMap.remove(chatId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void handleUploadError(Long chatId) {
        sendResponseMessage(chatId, "Произошла ошибка при загрузке фото животного.");
    }




    private AccessLevel getAccessLevelFromText(String text) {
        try {
            return AccessLevel.valueOf(text.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null; // Роль не найдена
        }
    }

    private AnimalType getTypeAnimalFromText(String text) {
        try {
            return AnimalType.valueOf(text.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null; // Тип не найден
        }
    }

    private void addUserToDatabase(UserDTO user) {
        // Сохраните пользователя в базе данных
        userService.createUser(user);
    }
   private void addAnimalToDatabase(AnimalDTO animal) {
        animalService.createAnimal(animal);
    }

    private void handleAddShelterInput(Long chatId, String text) throws TelegramApiException {

        State currentState = stateMap.get(chatId);
        System.out.println("Current state for chatId " + chatId + ": " + currentState);

        ShelterDTO shelter = shelterPending.get(chatId);

        if (currentState == State.AWAITING_NAME) {
            shelter = shelterPending.get(chatId);
            shelter.setName(text);
            stateMap.put(chatId, State.AWAITING_SAFETY); // Переход к рекомендациям по безопасности
            sendResponseMessage(chatId, "Пожалуйста, введите рекомендации по безопасности:");
        } else if (currentState == State.AWAITING_SAFETY) {
            shelter = shelterPending.get(chatId);
            shelter.setSafety(text);
            stateMap.put(chatId, State.AWAITING_ADDRESS); // Теперь переходим к запросу адреса приюта
            sendResponseMessage(chatId, "Пожалуйста, введите адрес приюта:");
        } else if (
                currentState == State.AWAITING_ADDRESS ||
                        currentState == State.AWAITING_CITY ||
                        currentState == State.AWAITING_STATE ||
                        currentState == State.AWAITING_ZIPCODE) {
            ShelterLocationDTO shelterLocation = shelterLocationPending.get(chatId);

            switch (currentState) {
                case AWAITING_ADDRESS -> {
                    shelterLocation.setAddress(text);
                    stateMap.put(chatId, State.AWAITING_CITY);
                    sendResponseMessage(chatId, "Пожалуйста, введите город приюта:");
                }
                case AWAITING_CITY -> {
                    shelterLocation.setCity(text);
                    stateMap.put(chatId, State.AWAITING_STATE);
                    sendResponseMessage(chatId, "Пожалуйста, введите штат приюта:");
                }
                case AWAITING_STATE -> {
                    shelterLocation.setState(text);
                    stateMap.put(chatId, State.AWAITING_ZIPCODE);
                    sendResponseMessage(chatId, "Пожалуйста, введите почтовый индекс приюта:");
                }
                case AWAITING_ZIPCODE -> {

                    System.out.println("ShelterLocationDTO: " + shelter.getShelterLocationDTO()); // Вывод для отладки
                    shelterLocation.setZipcode(text);
                    // Все поля заполнены, добавляем данные в базу

                    addShelterAndAddressToDatabase(shelter, shelterLocation);
                    shelterPending.remove(chatId);
                    shelterLocationPending.remove(chatId);
                    stateMap.remove(chatId);
                    sendResponseMessage(chatId, "Данные приюта успешно добавлены в базу.");
                }
            }
        }


    }

    private void addShelterAndAddressToDatabase(ShelterDTO shelter, ShelterLocationDTO shelterLocation) {
        // Сохраните приют в базе данных

        shelterLocationService.createShelterLocation(shelterLocation);
        shelterService.createShelter(shelter);

        // Дополнительная логика, если необходимо
    }


    private void sendResponseMessage(Long chatId, String message) {
        SendMessage responseMessage = new SendMessage(chatId, message);
        bot.execute(responseMessage);
    }


}

