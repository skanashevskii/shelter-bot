package ru.devpro.service;

import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import ru.devpro.enums.BotCommand;
import ru.devpro.enums.Messages;
import ru.devpro.enums.Status;

    public interface MenuService {
        void mainMenu(Long chatId, String messageText);

        /**
         * Вывод меню помощи
         */
        default SendMessage helpMenu(Long chatId) {

            InlineKeyboardMarkup inlineKeyboard;

            inlineKeyboard = new InlineKeyboardMarkup(BotCommand.START.getButton(), BotCommand.HELP.getButton());
            return new SendMessage(chatId, Messages.ERROR.getMessage()).replyMarkup(inlineKeyboard);
        }
    }

