package com.kasumov.med_ci.util.TelegramBot;

public class StringConstansTelegramBot {
    public static final String START_MESSAGE = "Для регистрации в телеграм боте введите команду /registration + email";
    public static final String NOT_RECOGNIZED = "Извините, команда не распознана";
    public static final String SUCCESS_UNSUBSCRIBE = "Вы успешно отписаны";
    public static final String LOG_ERROR = "Возникла ошибка: ";
    public static final String PATIENT_NOT_FOUND = "Пациент с таким email не зарегистрирован в больнице, обратитесь, пожалуйста, в регистратуру";
    public static final String SUBJECT_OF_MAIL = "Подтверждающий код для регистрации в телеграм боте";
    public static final String SEND_VERIFICATION_CODE = "На указанный email отправлен код подтверждения, введите его через комманду /code ######";
    public static final String INCORRECT_CODE = "Введен некорректный код";
    public static final String SUCCESS_REGISTRATION = "Регистрация прошла успешно";
    public static final String COMMAND_START = "/start";
    public static final String COMMAND_REGISTRATION = "/registration";
    public static final String COMMAND_CODE = "/code";
    public static final String COMMAND_UNSUBSCRIBE = "/unsubscribe";
}
