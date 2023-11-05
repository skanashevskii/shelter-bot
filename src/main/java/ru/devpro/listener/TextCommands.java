package ru.devpro.listener;

import com.pengrad.telegrambot.TelegramBot;



import com.pengrad.telegrambot.model.Update;

import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;


import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.devpro.enums.BotCommand;
import ru.devpro.listener.handlersbd.ChangeBD;
import ru.devpro.service.ShelterLocationService;

import java.util.*;



@Component
public class TextCommands implements Command {
    private final TelegramBot bot;
    private final ChangeBD changeBD;
    //private final Map<Long, State> stateMap = new HashMap<>();
    //private final Map<Long, ShelterLocationDTO> userPendingShelter = new HashMap<>();
    private final ShelterLocationService shelterLocationService;
    public TextCommands(TelegramBot bot, ChangeBD changeBD, ShelterLocationService shelterLocationService) {
        this.bot = bot;
        this.changeBD = changeBD;
        this.shelterLocationService = shelterLocationService;
    }

    public boolean ifSuitable(Update update) {
        if (update != null && update.message() != null) {
            String text = update.message().text();
            return Arrays.stream(BotCommand.values())
                    .anyMatch(botCommand -> botCommand.getCommand().equalsIgnoreCase(text));

        }
        return false;
    }
    /*public boolean ifSuitable(Update update) {
        return update != null && update.message() != null;
    }*/

    public void handle(Update update) throws TelegramApiException {
        System.out.println("Handling update...");
            // Обработка text-запросов
        if (update.message() != null) {
            var chatId = update.message().chat().id();
            var text = update.message().text();
            System.out.println("Received text: " + text);

            BotCommand botCommand = Arrays.stream(BotCommand.values())
                    .filter(command -> command.getCommand().equalsIgnoreCase(text))
                    .findFirst()
                    .orElse(null);
            System.out.println("Received text: " + botCommand);
            if (botCommand != null) {
                // Вызываем соответствующий метод на основе команды
                switch (botCommand) {
                    case START -> sendStartMessage(chatId);
                    //case ADD_SHELTER_LOCATION -> ChangeBD.createShelterLocation(chatId);
                    case ADD_SHELTER -> changeBD.createShelter(chatId);

                    default -> bot.execute(new SendMessage(chatId, "Извините, не могу обработать данную команду."));
                }

            }
        }
    }
    private void sendStartMessage (Long chatId){
        String downArrow = "👇";
        // If the user sent "/start," send a message with buttons

        InlineKeyboardMarkup inlineKeyboard =
                new InlineKeyboardMarkup(BotCommand.ABOUT_SHELTERS.getButton());
        inlineKeyboard.addRow(BotCommand.ADDRESS.getButton());
        inlineKeyboard.addRow(BotCommand.PASS.getButton());
        inlineKeyboard.addRow(BotCommand.SAFETY.getButton());
        inlineKeyboard.addRow(BotCommand.PHONE.getButton());
        inlineKeyboard.addRow(BotCommand.SHELTERS.getButton());

        SendMessage message = new SendMessage(chatId, "Нажмите необходимую кнопку" + downArrow);
        message.replyMarkup(inlineKeyboard);
        bot.execute(message);
    }

  /*  public void createShelterLocation(Long chatId) {
        // Создаем новый объект ShelterLocationDTO
        ShelterLocationDTO shelterLocation = new ShelterLocationDTO();
        // Устанавливаем текущее время в поле dateTime
        shelterLocation.setDateTime(LocalDateTime.now());
        // Устанавливаем состояние ожидания адреса
        stateMap.put(chatId, State.AWAITING_ADDRESS);
        // Сохраняем созданный объект в userPendingShelter
        userPendingShelter.put(chatId, shelterLocation);
        // Выводим сообщение и запрашиваем у пользователя адрес приюта
        System.out.println("State set to AWAITING_ADDRESS for chatId: " + chatId);
        sendResponseMessage(chatId, "Пожалуйста, введите адрес приюта:");

    }

   private void handleAddShelterInput(Long chatId, String text) throws TelegramApiException {
       State currentState = stateMap.get(chatId);
       System.out.println("Current state for chatId " + chatId + ": " + currentState); // Вывод состояния в консоль);
       ShelterLocationDTO shelterLocation = userPendingShelter.get(chatId);

           switch (currentState) {

               case AWAITING_ADDRESS -> {
                   System.out.println("Processing AWAITING_ADDRESS state"); // Отладочный вывод для состояния
                   shelterLocation.setAddress(text);
                   stateMap.put(chatId, State.AWAITING_CITY);
                   System.out.println("State set to AWAITING_CITY for chatId: " + chatId);
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
                   shelterLocation.setZipcode(text);
                   // Все поля заполнены, добавляем данные в базу
                   addShelterToDatabase(chatId, shelterLocation);
                   userPendingShelter.remove(chatId);
                   stateMap.remove(chatId);
                   sendResponseMessage(chatId, "Данные приюта успешно добавлены в базу.");
               }
           }

   }

    private void addShelterToDatabase(Long chatId, ShelterLocationDTO shelterLocation) throws TelegramApiException {
        shelterLocationService.createShelterLocation(shelterLocation);
        userPendingShelter.remove(chatId);
        stateMap.remove(chatId);
    }


    private void sendResponseMessage(Long chatId, String message) {
        SendMessage responseMessage = new SendMessage(chatId, message);
        bot.execute(responseMessage);
    }*/





























}







