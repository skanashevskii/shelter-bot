package ru.devpro.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;

import jakarta.annotation.PostConstruct;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Service
@Log4j2
public class UpdateListener implements UpdatesListener {

    private final TelegramBot bot;
    private final List<Command> commands;

    public UpdateListener(TelegramBot bot, List<Command> commands) {
        this.bot = bot;
        this.commands = commands;
    }

    @PostConstruct
    void init() {
        bot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            log.debug("Handle update: {}", update);


            commands.stream()
                    .filter(command -> command.ifSuitable(update))
                    .forEach(command -> {
                        try {
                            command.handle(update);
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                    });
        });

        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

   /* private void processButtonClick(Update update) {
        CallbackQuery callbackQuery = update.callbackQuery();
        if (callbackQuery != null) {
            long chatId = callbackQuery.message().chat().id();
            switch (callbackQuery.data()) {
                case BUTTON_CAT_SHELTER_CALLBACK_TEXT:
                    // Cat shelter selected
                    sendButtonClickMessage(chatId, BUTTON_CAT_SHELTER_TEXT);
                    processCatShelterClick(chatId);
                    break;
                case BUTTON_DOG_SHELTER_CALLBACK_TEXT:
                    // Dog shelter selected
                    sendButtonClickMessage(chatId, BUTTON_DOG_SHELTER_TEXT);
                    processDogShelterClick(chatId);
                    break;
                case BUTTON_STAGE1_CALLBACK_TEXT:
                    // General info about the shelter (stage 1)
                    sendButtonClickMessage(chatId, BUTTON_STAGE1_TEXT);
                    processStage1Click(chatId);
                    break;
                case BUTTON_STAGE2_CALLBACK_TEXT:
                    // How to adopt a dog/cat (stage 2)
                    sendButtonClickMessage(chatId, BUTTON_STAGE2_TEXT);
                    processStage2Click(chatId, update);
                    break;
                case BUTTON_STAGE3_CALLBACK_TEXT:
                    // Send a follow-up report (stage 3)
                    sendButtonClickMessage(chatId, BUTTON_STAGE3_TEXT);
                    processStage3Click(chatId);
                    break;
                case BUTTON_REPORT_TEMPLATE_CALLBACK_TEXT:
                    SendMessage instructionMessage = new SendMessage(chatId, ADOPTION_REPORT_INSTRUCTION);
                    sendMessage(instructionMessage);
                    break;
                case BUTTON_SEND_REPORT_CALLBACK_TEXT:
                    saveAdoptionReport(chatId);
                    break;
                case BUTTON_SHARE_CONTACT_CALLBACK_TEXT:
                    // Share your contact details
                    sendButtonClickMessage(chatId, BUTTON_SHARE_CONTACT_DETAILS_TEXT);
                    shareContact(update);
                    break;
                case BUTTON_INFO_SHELTER_CALLBACK_TEXT:
                    // Safety information
                    sendButtonClickMessage(chatId, BUTTON_INFO_SHELTER_TEXT_ANSWER);
                    processGettingInformationAboutShelter(chatId);
                    break;
                case BUTTON_INFO_SECURITY_CALLBACK_TEXT:
                    // Obtaining security contacts
                    sendButtonClickMessage(chatId, BUTTON_INFO_SECURITY_TEXT_ANSWER);
                    processGettingInformationAboutSecurity(chatId);
                    break;
                case BUTTON_INFO_SAFETY_PRECAUTIONS_CALLBACK_TEXT:
                    // Obtaining Safety Instructions
                    sendButtonClickMessage(chatId, BUTTON_INFO_SAFETY_PRECAUTIONS_TEXT_ANSWER);
                    processGettingInformationAboutSafetyPrecautions(chatId);
                    break;
                case BUTTON_RULES_MEETING_ANIMAL_CALLBACK_TEXT:
                    // Instruction how to meet animal first time
                    sendButtonClickMessage(chatId, BUTTON_RULES_MEETING_ANIMAL_TEXT_ANSWER);
                    processInfoMeetingClick(chatId);
                    break;
                case BUTTON_DOCS_FOR_ADOPTION_CALLBACK_TEXT:
                    // List of required docs
                    sendButtonClickMessage(chatId, BUTTON_DOCS_FOR_ADOPTION_TEXT_ANSWER);
                    processListOfDocsClick(chatId);
                    break;
                case BUTTON_RECOMMENDATIONS_FOR_TRANSPORT_CALLBACK_TEXT:
                    // Recommendation how to transport animals
                    sendButtonClickMessage(chatId, BUTTON_RECOMMENDATIONS_FOR_TRANSPORT_TEXT_ANSWER);
                    processTransportAnimal(chatId);
                    break;
                case BUTTON_ARRANGEMENT_FOR_PUPPY_CALLBACK_TEXT:
                    //  Arrangement for little animal in house
                    sendButtonClickMessage(chatId, BUTTON_ARRANGEMENT_FOR_PUPPY_TEXT_ANSWER);
                    processRecForLittle(chatId);
                    break;
                case BUTTON_ARRANGEMENT_FOR_ADULT_CALLBACK_TEXT:
                    // Arrangement for adult animal in house
                    sendButtonClickMessage(chatId, BUTTON_ARRANGEMENT_FOR_ADULT_TEXT_ANSWER);
                    processRecForAdult(chatId);
                    break;
                case BUTTON_ADVICES_FOR_DISABLED_PET_CALLBACK_TEXT:
                    // Advices how to be with disable animals
                    sendButtonClickMessage(chatId, BUTTON_ADVICES_FOR_DISABLED_PET_TEXT_ANSWER);
                    processRecForDisable(chatId);
                    break;
                case BUTTON_ADVICES_FROM_KINOLOG_CALLBACK_TEXT:
                    // Advices from kinolog
                    sendButtonClickMessage(chatId, BUTTON_ADVICES_FROM_KINOLOG_TEXT_ANSWER);
                    processKinologAdvices(chatId);
                    break;
                case BUTTON_RECOMMENDED_KINOLOGS_CALLBACK_TEXT:
                    // List of recommended kinologs
                    sendButtonClickMessage(chatId, BUTTON_RECOMMENDED_KINOLOGS_TEXT_ANSWER);
                    processRecKinologs(chatId);
                    break;
                case BUTTON_REASONS_FOR_REFUSAL_CALLBACK_TEXT:
                    // Reasons why we can refuse you
                    sendButtonClickMessage(chatId, BUTTON_REASONS_FOR_REFUSAL_TEXT_ANSWER);
                    processReasonsRefusal(chatId);
                    break;
                case BUTTON_CANCEL_SEND_REPORT_CALLBACK_TEXT:
                    // Cancel Send Report
                    cancelSendReport(chatId);
                    break;
            }
        }


    }*/
}



