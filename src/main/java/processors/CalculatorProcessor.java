package processors;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class CalculatorProcessor {
    static Map<String, Boolean> usersLightBoolean;
    static Map<String, Boolean> usersGasBoolean;

    CalculatorProcessor() {
        usersLightBoolean = new HashMap<>();
        usersGasBoolean = new HashMap<>();
    }

    void setCalculatorButtons(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow lawKeyboardRow = new KeyboardRow();
        lawKeyboardRow.add(new KeyboardButton("Свет"));

        KeyboardRow calculatorKeyboardRow2 = new KeyboardRow();
        calculatorKeyboardRow2.add(new KeyboardButton("Газ"));

        KeyboardRow calculatorKeyboardRow3 = new KeyboardRow();
        calculatorKeyboardRow3.add(new KeyboardButton("Назад"));

        keyboardRowList.add(lawKeyboardRow);
        keyboardRowList.add(calculatorKeyboardRow2);
        keyboardRowList.add(calculatorKeyboardRow3);

        replyKeyboardMarkup.setKeyboard(keyboardRowList);

        sendMessage.setText("Выберите калькулятор");
    }

    void setButtonsForFirstButton(SendMessage sendMessage, String fromId) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(new KeyboardButton("Назад к выбору калькулятора"));
        keyboardRowList.add(keyboardRow);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);

        sendMessage.setText("Введите площадь квартиры потребителя в таком формате: 30.7");
        usersLightBoolean.replace(String.valueOf(fromId), true);

    }

    void calculateForLight(String message, SendMessage sendMessage) {
        double tariff = Double.valueOf(message);
        double electricityPerDay = tariff * 0.75;
        double thirdTariff = electricityPerDay / 3;
        double tariffForOneDay = ((thirdTariff * 2) * 0.84) + (thirdTariff * 0.45);
        double seasonTariff = tariffForOneDay * 180;
        sendMessage.setText(String.format("Количество электричества в день = %(.2f .\n" +
                "Cтоимость одного дня электроотопления = %(.2f грн.\n" +
                "Cтоимость за сезон электроотопления = %(.2f грн.", electricityPerDay, tariffForOneDay, seasonTariff));
    }

    void setButtonsForSecondButton(SendMessage sendMessage, String fromId) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(new KeyboardButton("Назад к выбору калькулятора"));
        keyboardRowList.add(keyboardRow);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);


        sendMessage.setText("\"Введите площадь квартиры потребителя в таком формате: 30.7");
        usersGasBoolean.replace(String.valueOf(fromId), true);
    }

    void calculateForGas(String message, SendMessage sendMessage) {
        double tariff = Double.valueOf(message);
        double gasPerDay = tariff * 0.125;
        double tariffForOneDay = gasPerDay * 8.55;
        double seasonTariff = tariffForOneDay * 180;
        sendMessage.setText(String.format("Количество потребляемого газа в день = %(.2f .\n" +
                "Cтоимость одного дня газового отопления = %(.2f грн.\n" +
                "Cтоимость за сезон газового отопления = %(.2f грн.", gasPerDay, tariffForOneDay, seasonTariff));
    }
}