package com.smit.telegrambotfilebrowser;

import java.io.File;
import java.io.Serializable;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 *
 * @author SmiT
 */
class TelegramBot extends TelegramLongPollingBot {

    private final Properties properties;
    private final FileBrowser fileBrowser;
    private final File rootFolder;

    TelegramBot(Properties properties) {
        this.properties = properties;
        this.rootFolder = new File(properties.getProperty("folder"));
        fileBrowser = new FileBrowser(rootFolder);
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()
                && update.getMessage().hasText()
                && update.getMessage().getText().equals("/start")) {

            SendMessage sm = new SendMessage();
            sm.setChatId(update.getMessage().getChatId());
            sm.setText("File browser: [" + rootFolder.getName() + "]");
            sm.setReplyMarkup(fileBrowser.getKeyboard(rootFolder));
            executeMethod(sm);

        } else if (update.hasCallbackQuery()) {
            String fileId = update.getCallbackQuery().getData();
            File file = fileBrowser.getFile(fileId);

            if (file.isDirectory()) {
                EditMessageReplyMarkup em = new EditMessageReplyMarkup();
                em.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
                em.setChatId(update.getCallbackQuery().getMessage().getChatId());
                em.setReplyMarkup(fileBrowser.getKeyboard(file));
                executeMethod(em);
            } else {
                SendDocument sd = new SendDocument();
                sd.setChatId(update.getCallbackQuery().getMessage().getChatId());
                sd.setDocument(new InputFile(file));
                executeMethod(sd);
            }
        }
    }

    private <T extends Serializable, Method extends PartialBotApiMethod<T>> void executeMethod(Method method) {
        try {
            if (method instanceof BotApiMethod) {
                execute((BotApiMethod<?>) method);
            } else if (method instanceof SendDocument) {
                execute((SendDocument) method);
            } else {
                throw new UnsupportedOperationException();
            }
        } catch (TelegramApiException ex) {
            Logger.getLogger(TelegramBot.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getBotUsername() {
        return properties.getProperty("botName");
    }

    @Override
    public String getBotToken() {
        return properties.getProperty("botToken");
    }

}
