package ru.devpro.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.devpro.dto.ShelterLocationDTO;
import ru.devpro.enums.BotCommand;
import ru.devpro.listener.handlersbd.ChangeBD;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
public class QueryCommands implements Command {

    private final TelegramBot bot;
    private final ChangeBD changeBD;

    public QueryCommands(TelegramBot bot, ChangeBD changeBD) {
        this.bot = bot;

        this.changeBD = changeBD;
    }

    @Override
    public boolean ifSuitable(Update update) {
        if (update != null && update.callbackQuery() != null) {
            String text = update.callbackQuery().data();
            return Arrays.stream(BotCommand.values())
                    .anyMatch(botCommand -> botCommand.getCommand().equalsIgnoreCase(text));

        }
        return false;
    }

    @Override
    public void handle(Update update) throws TelegramApiException {
        if (update.callbackQuery() != null) {
            var chatId = update.callbackQuery().message().chat().id();
            var callbackData = update.callbackQuery().data();
            //sendSheltersMessage(chatId, callbackData);
            System.out.println("Received callbackData: " + callbackData);
            // Находим команду из enum, используя callbackData
            BotCommand command = Arrays.stream(BotCommand.values())
                    .filter(cmd -> cmd.getCommand().equalsIgnoreCase(callbackData))
                    .findFirst()
                    .orElse(null);

            if (command != null) {
                switch (command) {
                    case CALL_VOLUNTEER -> sendVolunteerMessage(chatId);
                    case SHELTERS -> createSheltersButtons(chatId);
                    case PASS -> sendPassInfo(chatId);
                    case ADDRESS -> sendAddressMessage(chatId);
                    case CYNOLOGISTS -> sendKinologsInfo(chatId);
                    case ADVICE_CYNOLOGISTS -> sendKinologsAdvice(chatId);
                    //case ADD_SHELTER_LOCATION -> ChangeBD.createShelterLocation(chatId);
                    case ADD_SHELTER -> changeBD.createShelter(chatId);
                    case ADD_USER -> changeBD.createUser(chatId);
                    case ADD_ANIMAL -> changeBD.createAnimalWithAvatar(chatId);
                    case CAT_SHELTER -> createCatShelterButtons(chatId);
                    case DOG_SHELTER -> createDogShelterButtons(chatId);
                    case SCHEDULE -> sendScheduleMessage(chatId);
                    case ABOUT_SHELTERS -> sendShelterInfo(chatId);
                    case SECURITY -> sendSecurityMessage(chatId);
                    case SAFETY -> sendSafetyMessage(chatId);
                    case CONTACT -> sendContactMessage(chatId);
                    case CAT_SHELTER_INFO-> sendCatShelterAddress(chatId);
                    case DOG_SHELTER_INFO-> sendDogShelterAddress(chatId);
                    default -> bot.execute(new SendMessage(chatId, "Извините, не могу обработать данную команду."));
                }
            }


        }


    }




    private void sendAddressMessage(Long chatId) {

            RestTemplate restTemplate = new RestTemplate();
            String apiUrl = "http://localhost:8080/shelterLocation/{id}"; // Замените на актуальный URL

            // Задайте параметры для запроса
            Map<String, String> uriVariables = new HashMap<>();
            uriVariables.put("id", "1"); // Замените на нужный идентификатор

            ShelterLocationDTO shelterLocationDTO = restTemplate.getForObject(apiUrl, ShelterLocationDTO.class, uriVariables);

            if (shelterLocationDTO != null) {
                // Формируйте сообщение с данными из базы данных
                String message =
                        "Адрес приюта:\n" +
                        "Улица: " + shelterLocationDTO.getAddress() + "\n" +
                        "Город: " + shelterLocationDTO.getCity() + "\n" +
                        "Район: " + shelterLocationDTO.getState() + "\n" +
                        "Почтовый индекс: " + shelterLocationDTO.getZipcode();

                SendMessage sendMessage = new SendMessage(chatId, message);

                bot.execute(sendMessage);
            } else {
                SendMessage notFoundMessage = new SendMessage(chatId, "Приют не найден.");

                bot.execute(notFoundMessage);
            }
        }
    private void sendCatShelterAddress(Long chatId) {
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "http://localhost:8080/shelterLocation/{id}"; // Замените на актуальный URL

        // Задайте параметры для запроса
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("id", "2"); // Замените на нужный идентификатор

        ShelterLocationDTO shelterLocationDTO = restTemplate.getForObject(apiUrl, ShelterLocationDTO.class, uriVariables);

        if (shelterLocationDTO != null) {
            // Формируйте сообщение с данными из базы данных
            String message = "Адрес приюта:\n" +
                    "Улица: " + shelterLocationDTO.getAddress() + "\n" +
                    "Город: " + shelterLocationDTO.getCity() + "\n" +
                    "Район: " + shelterLocationDTO.getState() + "\n" +
                    "Почтовый индекс: " + shelterLocationDTO.getZipcode();

            SendMessage sendMessage = new SendMessage(chatId, message);

            bot.execute(sendMessage);
        } else {
            SendMessage notFoundMessage = new SendMessage(chatId, "Приют не найден.");

            bot.execute(notFoundMessage);
        }
    }
    private void sendDogShelterAddress(Long chatId) {
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "http://localhost:8080/shelterLocation/{id}"; // Замените на актуальный URL

        // Задайте параметры для запроса
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("id", "3"); // Замените на нужный идентификатор

        ShelterLocationDTO shelterLocationDTO = restTemplate.getForObject(apiUrl, ShelterLocationDTO.class, uriVariables);

        if (shelterLocationDTO != null) {
            // Формируйте сообщение с данными из базы данных
            String message = "Адрес приюта:\n" +
                    "Улица: " + shelterLocationDTO.getAddress() + "\n" +
                    "Город: " + shelterLocationDTO.getCity() + "\n" +
                    "Район: " + shelterLocationDTO.getState() + "\n" +
                    "Почтовый индекс: " + shelterLocationDTO.getZipcode();

            SendMessage sendMessage = new SendMessage(chatId, message);

            bot.execute(sendMessage);
        } else {
            SendMessage notFoundMessage = new SendMessage(chatId, "Приют не найден.");

            bot.execute(notFoundMessage);
        }
    }


    void createSheltersButtons(Long chatId) {
        String downArrow = "👇";
        // If the user sent "/shelters," send a message with buttons
        InlineKeyboardMarkup sheltersKeyboard =
                new InlineKeyboardMarkup();
        sheltersKeyboard.addRow(BotCommand.CAT_SHELTER.getButton());
        sheltersKeyboard.addRow(BotCommand.DOG_SHELTER.getButton());
        sheltersKeyboard.addRow(BotCommand.ADD_SHELTER_LOCATION.getButton());
        sheltersKeyboard.addRow(BotCommand.ADD_SHELTER.getButton());
        sheltersKeyboard.addRow(BotCommand.ADD_USER.getButton());
        sheltersKeyboard.addRow(BotCommand.ADD_ANIMAL.getButton());

        SendMessage message = new SendMessage(chatId, "Выберите приют:" + "👇");
        message.replyMarkup(sheltersKeyboard);
        bot.execute(message);

        }

    private void sendContactMessage(Long chatId) {
    }

    private void sendSafetyMessage(Long chatId) {
    }

    private void sendSecurityMessage(Long chatId) {
    }

    private void sendVolunteerMessage(Long chatId) {
    }

    private void createCatShelterButtons(Long chatId) {
        InlineKeyboardMarkup catKeyboard = new InlineKeyboardMarkup(BotCommand.DATE.getButton());
        catKeyboard.addRow(BotCommand.DOCUMENTS.getButton());
        catKeyboard.addRow(BotCommand.TRANSPORTATION.getButton());
        catKeyboard.addRow(BotCommand.CAT_HOME.getButton());
        catKeyboard.addRow(BotCommand.KITTEN_HOME.getButton());
        catKeyboard.addRow(BotCommand.DISABLED_CAT.getButton());
        catKeyboard.addRow(BotCommand.CAT_SHELTER_INFO.getButton());

        SendMessage message = new SendMessage(chatId, "Выберите нужную опцию:" + "👇");
        message.replyMarkup(catKeyboard);
        bot.execute(message);
    }

    private void createDogShelterButtons(Long chatId) {

            InlineKeyboardMarkup dogKeyboard = new InlineKeyboardMarkup(BotCommand.DATE.getButton());
            dogKeyboard.addRow(BotCommand.DOCUMENTS.getButton());
            dogKeyboard.addRow(BotCommand.TRANSPORTATION.getButton());
            dogKeyboard.addRow(BotCommand.DOG_HOME.getButton());
            dogKeyboard.addRow(BotCommand.PUPPY_HOME.getButton());
            dogKeyboard.addRow(BotCommand.DISABLED_DOG.getButton());
            dogKeyboard.addRow(BotCommand.ADVICE_CYNOLOGISTS.getButton());
            dogKeyboard.addRow(BotCommand.CYNOLOGISTS.getButton());
            dogKeyboard.addRow(BotCommand.REFUSAL.getButton());
            dogKeyboard.addRow(BotCommand.DOG_SHELTER_INFO.getButton());

        SendMessage message = new SendMessage(chatId, "Выберите нужную опцию:" + "👇");
        message.replyMarkup(dogKeyboard);
        bot.execute(message);
    }

    private void sendScheduleMessage(Long chatId) {

    }

    private void sendAboutScheduleMessage(Long chatId) {
    }
    private void sendResponseMessage(Long chatId, String message) {
        SendMessage responseMessage = new SendMessage(chatId, message);
        bot.execute(responseMessage);
    }

    public String readShelterInfoFromFile() {
        try {
            //  путь к файлу с информацией о приюте
            Path filePath = Paths.get("./shelters_file/informaciya_o_priyute.txt");
            return new String(Files.readAllBytes(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            return "Не удалось прочитать информацию о приюте.";
        }
    }
    public void sendShelterInfo(Long chatId) {
        String shelterInfo = readShelterInfoFromFile(); // Получаем информацию о приюте из файла
        SendMessage sendMessage = new SendMessage(chatId, shelterInfo);

        bot.execute(sendMessage);
    }
    public String aboutPassInfoFromFile() {
        try {
            // на путь к файлу с информацией о приюте
            Path filePath = Paths.get("./shelters_file/propusk.txt");
            return new String(Files.readAllBytes(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            return "Не удалось прочитать информацию о приюте.";
        }
    }
    public void sendPassInfo(Long chatId) {
        String passInfo = aboutPassInfoFromFile(); // Получаем информацию о приюте из файла
        SendMessage sendMessage = new SendMessage(chatId, passInfo);

        bot.execute(sendMessage);
    }
    public String aboutKinologsFromFile() {
        try {
            //  путь к файлу с информацией о приюте
            Path kinilogsInfo = Paths.get("./shelters_file/kinologs.txt");
            return new String(Files.readAllBytes(kinilogsInfo));
        } catch (IOException e) {
            e.printStackTrace();
            return "Не удалось прочитать информацию о приюте.";
        }
    }
    public void sendKinologsInfo(Long chatId) {
        String kinologsInfo = aboutKinologsFromFile(); // Получаем информацию о кинологах из файла
        SendMessage sendMessage = new SendMessage(chatId, kinologsInfo);

        bot.execute(sendMessage);
    }
    private String aboutAdviceKinolog() {
        try {
            //  путь к файлу с информацией о приюте
            Path kinilogsAdvice = Paths.get("./shelters_file/advices_from_kinolog.txt");
            return new String(Files.readAllBytes(kinilogsAdvice));
        } catch (IOException e) {
            e.printStackTrace();
            return "Не удалось прочитать информацию о приюте.";
        }
    }
    public void sendKinologsAdvice(Long chatId) {
        String kinologAdvice = aboutAdviceKinolog(); // Получаем информацию советы кинолога из файла
        SendMessage sendMessage = new SendMessage(chatId, kinologAdvice);

        bot.execute(sendMessage);
    }
}
