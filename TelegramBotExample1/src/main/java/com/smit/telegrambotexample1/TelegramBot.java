package com.smit.telegrambotexample1;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 *
 * @author SmiT
 */
public class TelegramBot extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {
        System.out.println("Input message: " + update.getMessage().getText());

        SendMessage sm = new SendMessage();
        sm.setChatId(update.getMessage().getChatId());
        sm.setText("Hello world!");

        try {
            execute(sm);
        } catch (TelegramApiException ex) {
            Logger.getLogger(TelegramBot.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("Output message: " + sm.getText());
    }

    @Override
    public String getBotUsername() {
        return "your_bot_name";
    }

    @Override
    public String getBotToken() {
        return "your_token";
    }
}
