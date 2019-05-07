package processors;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

import static processors.CalculatorProcessor.usersGasBoolean;
import static processors.CalculatorProcessor.usersLightBoolean;

public class MessageProcessor {
    private CalculatorProcessor calculatorProcessor;

    public MessageProcessor() {
        this.calculatorProcessor = new CalculatorProcessor();
    }

    public SendMessage process(Message message) {
        String fromId = String.valueOf(message.getFrom().getId());
        if (!usersLightBoolean.containsKey(fromId)) {
            usersLightBoolean.put(fromId, false);
        }
        if (!usersGasBoolean.containsKey(fromId)) {
            usersGasBoolean.put(fromId, false);
        }

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());
        chooseAction(message, sendMessage);
        return sendMessage;
    }

    private void chooseAction(Message message, SendMessage sendMessage) {
        String fromId = String.valueOf(message.getFrom().getId());
        switch (message.getText()) {
            case "/start":
                setStartButtons(sendMessage);
                break;
            case "Назад":
            case "Да":
                setMainButtons(sendMessage);
                break;
            case "Назад к выбору калькулятора":
            case "Калькулятор":
                calculatorProcessor.setCalculatorButtons(sendMessage);
                break;
            case "Нет":
                sendMessage.setText("Очень зря");
                break;
            case "Свет":
                calculatorProcessor.setButtonsForFirstButton(sendMessage, fromId);
                break;
            case "Газ":
                calculatorProcessor.setButtonsForSecondButton(sendMessage, fromId);
                break;
            case "Общая информация":
                setMainButtons(sendMessage);
                setCommonInformation(sendMessage);
                break;
            case "Необходимые документы":
                setMainButtons(sendMessage);
                setRequiredDocuments(sendMessage);
                break;
            case "Пример заявления":
                setMainButtons(sendMessage);
                setExample(sendMessage);
                break;
            case "Контакты магазинов с электрокотлами":
                setMainButtons(sendMessage);
                setNumbersAndAdresses(sendMessage);
                break;
            case "Контакты специалистов по установке электрокотлов":
                setMainButtons(sendMessage);
                setNumbersOfWorkers(sendMessage);
                break;
            default:
                if (usersLightBoolean.get(fromId)) {
                    calculatorProcessor.setCalculatorButtons(sendMessage);
                    calculatorProcessor.calculateForLight(message.getText(), sendMessage);
                    usersLightBoolean.replace(fromId, false);
                }
                if (usersGasBoolean.get(fromId)) {
                    calculatorProcessor.setCalculatorButtons(sendMessage);
                    calculatorProcessor.calculateForGas(message.getText(), sendMessage);
                    usersGasBoolean.replace(fromId, false);
                }
        }
    }

    private void setStartButtons(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow startKeyboardRow = new KeyboardRow();
        startKeyboardRow.add(new KeyboardButton("Да"));

        KeyboardRow startKeyboardRow2 = new KeyboardRow();
        startKeyboardRow2.add(new KeyboardButton("Нет"));

        keyboardRows.add(startKeyboardRow);
        keyboardRows.add(startKeyboardRow2);

        replyKeyboardMarkup.setKeyboard(keyboardRows);

        sendMessage.setText("Вам нужна моя консультация?");
    }

    private void setMainButtons(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow mainKeyboardRow = new KeyboardRow();
        mainKeyboardRow.add(new KeyboardButton("Калькулятор"));

        KeyboardRow mainKeyboardRow2 = new KeyboardRow();
        mainKeyboardRow2.add(new KeyboardButton("Общая информация"));

        KeyboardRow mainKeyboardRow3 = new KeyboardRow();
        mainKeyboardRow3.add(new KeyboardButton("Необходимые документы"));

        KeyboardRow mainKeyboardRow4 = new KeyboardRow();
        mainKeyboardRow4.add(new KeyboardButton("Пример заявления"));

        keyboardRows.add(mainKeyboardRow);
        keyboardRows.add(mainKeyboardRow2);
        keyboardRows.add(mainKeyboardRow3);
        keyboardRows.add(mainKeyboardRow4);

        replyKeyboardMarkup.setKeyboard(keyboardRows);

        sendMessage.setText("Какая помощь Вам нужна?");
    }

    private void setExample(SendMessage sendMessage) {
        sendMessage.setText("Заступнику начальника\n" +
                "                                                                         РЕМ\n" +
                "з комерційних питань\n" +
                "ПрАТ «Рівнеобленерго»\n" +
                "______________________________________\n" +
                "(прізвище, ім’я, по батькові)\n" +
                "_________________________________________\n" +
                "                (прізвище, ім’я, по батькові споживача)\n" +
                "_________________________________________\n" +
                "_________________________________________\n" +
                "_________________________________________\n" +
                "                                      (адреса)\n" +
                "Телефон: ________________________________\n" +
                "Особовий рахунок: ________________________\n" +
                " \n" +
                "З А Я В А\n" +
                " \n" +
                "Прошу укласти зі мною, ______________________________________, (ПІБ)\n" +
                "договір про користування електроенергією за адресою ______________\n" +
                "_____________________________________ у зв’язку з  _____________ _____________________________________________________________\n" +
                "(підстава укладення договору)\n" +
                "        До заяви додаю наступні документи:  \n" +
                "1.Копія паспорта.\n" +
                "2.Копія ідентифікаційного коду.\n" +
                "3. Копії документів, що підтверджують право власності або право користування житлом :\n" +
                " _______________________________________________________\n" +
                " _______________________________________________________\n" +
                "4. Згода суб'єкта персональних даних на обробку ПрАТ «Рівнеобленерго» його даних.\n" +
                "5. Показник засобу обліку на момент написання заяви\n" +
                "__________________________________________\n" +
                " \n" +
                "«____»_______________20___р. _____________________ ");
    }

    private void setRequiredDocuments(SendMessage sendMessage) {
        sendMessage.setText("*Копия значимых страниц паспорта гражданина Украины;\n" +
                " * ИНН (Идентификационный код);\n" +
                " * Акт на право владения/пользования жильем;\n" +
                " * Техпаспорт на оборудование;\n" +
                " * Техпаспорт квартиры/частного дома;\n" +
                " * Договор с РЭС об энергообеспечении (действующий);\n" +
                " * Заявление о подключении электроотопления;\n" +
                " * Если право собственности на жилье зарегистрировано на другого человека, необходима его доверенность и паспорт.");
    }

    private void setCommonInformation(SendMessage sendMessage) {
        sendMessage.setText("С 1 октября по 30 апреля в Украине будет действовует льготный тариф при отоплении жилья электроустановками. Согласно постановлению НКРЭ Украины «О тарифах на электроэнергию, отпускаемую населению и населенным пунктам» от 23.04.2012 г., его действие распространяется на жилые дома (в т.ч. гостиничного типа и общежития).\\n\" +\n" +
                "Стоимость отопления 0,84 грн/кВт*ч, а предел электроэнергии по сниженной цене - 3000 кВт*ч/мес, каждый киловатт выше - оплачивается по 1,68 грн.\n" +
                "В Украине разрешено индивидуальное теплообеспечение, это значит, что оформить его могут даже жильцы многоквартирных домов, подключенных к котельным.\n" +
                "При двухтарифном учете после 23:00 и до 07:00 будет стоить 0,45 грн/кВт*ч.\n" +
                "Стоимость двухтарифного счетчика от 1200 грн. Окупиться в первый же месяц.");
    }

    private void setNumbersAndAdresses(SendMessage sendMessage) {
        sendMessage.setText("097 547 58 69 г.Одесса ул. Екатериненская 87\n" +
                "063 512 47 78 г. Киев ул. Дзержинского 115\n" +
                "063 787 87 87 г. Днепропетровск ул. Завоская 124\n" +
                "6. Номера и адреса где можно приобрести двухтарифный счетчик- 096 588 98 99 г.Одесса ул. Екатериненская 154\n" +
                "063 787 89 65  г. Киев ул. НИколаева 187\n" +
                "063 323 55 66  г. Днепропетровск ул. Шевченко 65");
    }

    private void setNumbersOfWorkers(SendMessage sendMessage) {
        sendMessage.setText("Киев- 097 547 89 78 - Вадим\n" +
                "Одесса- 067 584 02 15 – Константин\n" +
                "Херсон – 063 541 45 15 – Николай\n" +
                "Харьков – 097 512 98 85 - Илья\n" +
                "Тернополь – 093 322 56 32 - Дмитрий\n" +
                "Николаев – 096 215 23 21 - Иван\n" +
                "Полтава – 097 546 98 57 - Николай\n" +
                "Львов – 093 547 54 21 - Михаил\n" +
                "Винница – 093 547 58 47 - Илья\n" +
                "Суммы – 096 547 54 89 - Борис\n" +
                "Чернигов –  093 547 56 52 - Михаил\n" +
                "Днипро – 063 547 86 95 - Николай\n" +
                "Запорожье – 087 545 12 65 - Эдуард\n" +
                "Кривой рог-  096 548 45 87 - Константин\n" +
                "7. Номер платной консультации - 097 325 45 69 – Светлана\n" +
                "063 569 87 45 – Марьяна\n" +
                "057 568 74 41 - Екатерина");
    }
}