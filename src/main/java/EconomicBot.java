import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import processors.MessageProcessor;

public class EconomicBot extends TelegramLongPollingBot {
    private MessageProcessor messageProcessor;

    EconomicBot(MessageProcessor messageProcessor) {
        this.messageProcessor = messageProcessor;
    }

    @Override
    public void onUpdateReceived(Update update) {
        SendMessage sendMessage = messageProcessor.process(update.getMessage());
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "EconomicBot";
    }

    @Override
    public String getBotToken() {
        return "815982937:AAF1i0zoIbW8smAAfFRtTAf3bHzEZzi8KtU";
    }
}
