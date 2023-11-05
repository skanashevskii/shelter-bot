package ru.devpro.service;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import org.springframework.stereotype.Service;
import ru.devpro.enums.BotCommand;

@Service

public class CreateKeyboardsService {

       /* public InlineKeyboardMarkup createButtonsStage2 (PetType shelterType){
            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            inlineKeyboardMarkup.addRow(new InlineKeyboardButton(BUTTON_RULES_MEETING_ANIMAL_TEXT).callbackData(BUTTON_RULES_MEETING_ANIMAL_CALLBACK_TEXT));
            inlineKeyboardMarkup.addRow(new InlineKeyboardButton(BUTTON_DOCS_FOR_ADOPTION_TEXT).callbackData(BUTTON_DOCS_FOR_ADOPTION_CALLBACK_TEXT));
            inlineKeyboardMarkup.addRow(new InlineKeyboardButton(BUTTON_RECOMMENDATIONS_FOR_TRANSPORT_TEXT).callbackData(BUTTON_RECOMMENDATIONS_FOR_TRANSPORT_CALLBACK_TEXT));
            inlineKeyboardMarkup.addRow(new InlineKeyboardButton(BUTTON_ARRANGEMENT_FOR_PUPPY_TEXT).callbackData(BUTTON_ARRANGEMENT_FOR_PUPPY_CALLBACK_TEXT));
            inlineKeyboardMarkup.addRow(new InlineKeyboardButton(BUTTON_ARRANGEMENT_FOR_ADULT_TEXT).callbackData(BUTTON_ARRANGEMENT_FOR_ADULT_CALLBACK_TEXT));
            inlineKeyboardMarkup.addRow(new InlineKeyboardButton(BUTTON_ADVICES_FOR_DISABLED_PET_TEXT).callbackData(BUTTON_ADVICES_FOR_DISABLED_PET_CALLBACK_TEXT));
            if (shelterType.equals(DOG)) {
                inlineKeyboardMarkup.addRow(new InlineKeyboardButton(BUTTON_ADVICES_FROM_KINOLOG_TEXT).callbackData(BUTTON_ADVICES_FROM_KINOLOG_CALLBACK_TEXT));
                inlineKeyboardMarkup.addRow(new InlineKeyboardButton(BUTTON_RECOMMENDED_KINOLOGS_TEXT).callbackData(BUTTON_RECOMMENDED_KINOLOGS_CALLBACK_TEXT));
            }
            inlineKeyboardMarkup.addRow(new InlineKeyboardButton(BUTTON_REASONS_FOR_REFUSAL_TEXT).callbackData(BUTTON_REASONS_FOR_REFUSAL_CALLBACK_TEXT));
            inlineKeyboardMarkup.addRow(new InlineKeyboardButton(BUTTON_SHARE_CONTACT_DETAILS_TEXT).callbackData(BUTTON_SHARE_CONTACT_CALLBACK_TEXT));
            return inlineKeyboardMarkup;
        }*/

}
