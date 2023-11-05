package ru.devpro.enums;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor

public enum BotCommand {
    START("Привет, рад приветствовать тебя!", "/start"),
    SHELTERS("\uD83D\uDC08   Приюты   \uD83D\uDC36", "/shelters"),
    SHELTER_INFO("Общая информация", "/info_shelters"),
    PASS("Оформление пропуска", "/pass"),
    ADDRESS("Расписание работы и адрес", "/adress"),
    HELP("Позвать волонтера", "/help"),
    GET_INFO("Как взять животное из приюта", "/get"),
    CAT_SHELTER("Приют для кошек", "/cat_shelter"),
    DOG_SHELTER("Приют для собак", "/dog_shelter"),
    CAT_SHELTER_INFO_MENU("меню информации о приюте кошек", "/cat_shelter_info_menu"),
    DOG_SHELTER_INFO_MENU("меню информации о приюте собак", "/dog_shelter_info_menu"),
    ADOPT_CAT("информация для усыновителя кошки", "/preparing_for_adoption_dog"),
    ADOPT_DOG("информация для усыновителя собаки", "/preparing_for_adoption_cat"),
    ABOUT_CAT_SHELTER("информация о приюте для кошек", "about_cat_shelter"),
    ABOUT_DOG_SHELTER("информация о приюте для собак", "about_dog_shelter"),
    CAT_SHELTER_CONTACT_INFO("расписание, адрес и схема проезда", "cat_shelter_contact_info"),
    DOG_SHELTER_CONTACT_INFO("расписание, адрес и схема проезда", "dog_shelter_contact_info"),
    CAT_SHELTER_PASS_REG("контакты для оформления пропуска","cat_shelter_pass_reg"),
    DOG_SHELTER_PASS_REG("контакты для оформления пропуска","dog_shelter_pass_reg"),
    DOG_SHELTER_SAFETY_RECOMMENDATIONS("техника безопасности в приюте", "shelter_safety_recommendations"),
    CAT_SHELTER_SAFETY_RECOMMENDATIONS("техника безопасности в приюте", "cat_shelter_safety_recommendations"),
    RULES_FOR_GETTING_TO_KNOW_CAT("правила знакомства с питомцем", "rules_for_getting_to_know_cat"),
    RULES_FOR_GETTING_TO_KNOW_DOG("правила знакомства с питомцем", "rules_for_getting_to_know_dog"),
    DOCUMENTS_FOR_ADOPTION_CAT("документы для усыновления питомца", "documents_for_adoption_cat"),
    DOCUMENTS_FOR_ADOPTION_DOG("документы для усыновления питомца", "documents_for_adoption_dog"),
    TRANSPORTATION_RECOMMENDATION_FOR_CAT("транспортировка животного", "transportation_recommendations_for_cat"),
    TRANSPORTATION_RECOMMENDATION_FOR_DOG("транспортировка животного", "transportation_recommendations_for_dog"),
    RECOMMENDATION_FOR_CAT_CUB_HOUSE("домик котенка", "recommendations_for_cat_cub_house"),
    RECOMMENDATION_FOR_DOG_CUB_HOUSE("домик щенка", "recommendations_for_dog_cub_house"),
    RECOMMENDATION_FOR_CAT_ADULT_HOUSE("домик взрослой кошки", "recommendations_for_cat_adult_house"),
    RECOMMENDATION_FOR_DOG_ADULT_HOUSE("домик взрослой собаки", "recommendations_for_dog_adult_house"),
    RECOMMENDATION_FOR_DISABLED_CAT_HOUSE("домик питомца с инвалидностью", "recommendations_for_disabled_cat_house"),
    RECOMMENDATION_FOR_DISABLED_DOG_HOUSE("домик питомца с инвалидностью", "recommendations_for_disabled_dog_house"),
    TIPS_FROM_DOG_HANDLER("советы кинолога", "tips_from_dog_handler"),
    RECOMMENDED_DOG_HANDLERS_LIST("рекомендуемые кинологи", "recommended_dog_handler_list"),
    POSSIBLE_REASON_FOR_REFUSAL_FOR_ADOPTION_CAT("причины отказа в усыновлении", "possible_reasons_for_refusal_of_adoption_cat"),
    POSSIBLE_REASON_FOR_REFUSAL_FOR_ADOPTION_DOG("причины отказа в усыновлении", "possible_reasons_for_refusal_of_adoption_dog"),
    REPORT_ABOUT_PET("отправить отчет о питомце", "/report_about_pet"),
    COMMUNICATION_REQUEST("запрос на обратную связь", "/communication_request"),
    CALL_VOLUNTEER("Позвать волонтера", "/call_volunteer"),
    PHONE("Телефоны", "/phone"),
    EMAIL("электронная почта", "/email"),
    BACK_DOG_SHELTER("<- назад", "back_dog_shelter"),
    BACK_CAT_SHELTER("<- назад", "back_cat_shelter"),
    BACK_START_MENU("<- назад", "back_start_menu"),
    BACK_CAT_INFO_MENU("<- назад", "back_cat_info_menu"),
    BACK_DOG_INFO_MENU("<- назад", "back_dog_info_menu"),
    BACK_CAT_ADOPTION_MENU("<- назад", "back_cat_adoption_menu"),
    BACK_DOG_ADOPTION_MENU("<- назад", "back_dog_adoption_menu"),
    SCHEDULE("Расписание", "schedule"),
    SECURITY("Безопасность","security"),
    CONTACT("Контакты","contact"),
    SAFETY("Техника безопасности","safety"),
    ABOUT_SHELTERS("О Приютах","/about_shelters" ),
    CAT_SHELTER_INFO("Основная информация о приюте", "/cat_info"),
    DOG_SHELTER_INFO("Основная информация о приюте", "/dog_info"),
    SAFE("Техника безопасности на территории", "/safe"),
    REPORT("Прислать отчет о питомце", "/report"),
    TEXT("Заполнить отчет", "/text"),
    PHOTO("Прислать фото животного", "/photo"),

    DATE("Как познакомится с животным", "/date"),
    DOCUMENTS("Необходимые документы", "/documents"),
    TRANSPORTATION("Рекомендации по транспортировке", "/transportation"),
    DOG_HOME("Рекомендации по обустройству дома для собаки", "/home"),
    CAT_HOME("Рекомендации по обустройству дома для кошки", "/cathome"),
    PUPPY_HOME("Рекомендации по обустройству дома для щенка", "/puppyhome"),
    KITTEN_HOME("Рекомендации по обустройству дома для котенка", "/kittenhome"),
    DISABLED_DOG("Рекомендации для собаки с ограниченными возможностями", "/disableddog"),
    DISABLED_CAT("Рекомендации для кошки с ограниченными возможностями", "/disabledcat"),
    CONTACTS("Наш телефон и контакты для связи", "/contacts"),
    //DOG_TIPS("Советы кинолога по первичному общению с собакой", "/dogtips"),
    CYNOLOGISTS("Рекомендуемые кинологи", "/cynologists"),
    ADVICE_CYNOLOGISTS("Советы кинолога", "/advice_cynologists"),
    REFUSAL("Почему мы можем отказать и не дать забрать собаку", "/refusal"),

    SET_ACCESS_LEVEL("Сменить права пользователя", "/setAccessLevel"),

    I_AM_VOLUNTEER("Стать волонтером", "/iamvolunteer"),
    I_AM_GUEST("Стать гостем", "/iamguest"),


    ADD_PET("Добавить питомца","/addPet"),
    ACCEPTANCE_OF_THE_REQUEST("Принятие запроса","Принял"),
    PET_MENU("Меню питомцев","/petMenu"),
    CHANGE_OF_OWNER("Новый хозяин питомцу","/setOwner"),
    CHANGE_DATE_ADOPTION("Увелечение испытательно срока вручную","/setDateAdoption"),
    GET_ALL_PET("Получить список всех питомцев","/getAll"),
    USER_VOLUNTEER_MENU("Меню пользователей","/userVolunteerMenu"),
    GET_ALL_USER_GUEST("Получить список всех гостей","/getAllUserGuest"),
    REQUEST_FOR_REPORT("Пропущен день отчета","/requestForReport"),
    PROBATIONARY_PERIOD_NOT_PASSED("Испытательный срок не пройден","/periodNotPassed"),
    INCORRECT_REPORT("Неправильный отчет","/incorrectReport"),
    CHANGE_DATE_ADOPTION_30_DAYS("Увеличение испытательно срока на 30 дней","/setDateAdoption30Days"),
    CHANGE_DATE_ADOPTION_14_DAYS("Увеличение испытательно срока на 14 дней","/setDateAdoption14Days"),
    CHANGE_DATE_MENU("Изменение испытательного срока","/setDate"),
    CHECK_REPORT_DAY_TODAY("Посмотреть отчет за сегодня","/checkReport"),
    CHECK_REPORT_PER_DATE("Посмотреть отчет за определенную дату","/checkReportPerDate"),
    GET_ALL_PET_REPORTS("Посмотреть все отчеты питомца","/checkAllReportsPet"),
    CHECK_HAS_NOT_REPORT("Есть ли отчет на определенную дату","/checkHasNotReport"),
    CHECK_REPORT_DAY_TODAY_ALL("Посмотреть все отчеты за сегодня","/checkAllReportToday"),
    REPORT_MENU("Меню отчетов","/reportMenu"),

    UNKNOWN_COMMAND("Неизвестная команда",""),
    CHECK_STATE("Проверка","/checkState"), // Добавляем команду CHECK_STATE
    ADD_SHELTER_LOCATION("Добавить адрес приюта","/addAddress" ),
    ADD_SHELTER("Добавить приют","/addShelter" ),
    ADD_USER("Добавить пользователя","/addUser" ),
    ADD_ANIMAL("Добавить животное","/addAnimal" );


    private final String description;
    private final String command;

    /**
     * Возвращает готовую кнопку с описанием и командой
     */



    public InlineKeyboardButton getButton() {
        return new InlineKeyboardButton(description).callbackData(command);
    }

    /**
     * Возвращает Enum команды
     */
    public static BotCommand getCommandName(String command) {
        for (BotCommand commandName : BotCommand.values()) {
            if (commandName.getCommand().equals(command)) {
                return commandName;
            }
        }
        return UNKNOWN_COMMAND;
    }



}

