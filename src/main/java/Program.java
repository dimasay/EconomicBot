import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import processors.MessageProcessor;

import javax.swing.*;
import java.awt.*;

public class Program {
    public static void main(String[] args) {
        JFrame frame = new JFrame("EcomonicBot");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(260, 100);
        frame.setVisible(true);
        frame.add(new JLabel("EconomicBot server for Telegram was started"), BorderLayout.CENTER);

        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new EconomicBot(new MessageProcessor()));
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }
}
