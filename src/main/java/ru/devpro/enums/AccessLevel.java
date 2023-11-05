package ru.devpro.enums;

import lombok.Getter;

@Getter
public enum AccessLevel {
    OWNER,      // Доступ только владельцам
    VOLUNTEER,  // Доступ только волонтерам
    BOTH,       // Доступ владелец,волонтер
    GUEST,       // Доступ пользователь
}
