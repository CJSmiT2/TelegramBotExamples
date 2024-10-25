package com.smit.telegrambotfilebrowser;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/**
 *
 * @author SmiT
 */
public class TelegramBotFileBrowser {

    public static void main(String[] args) throws TelegramApiException {
        
        Properties properties = loadProperties();
        
        System.out.println("Starting bot. properties: " + properties);
        
        TelegramBot bot = new TelegramBot(properties);
        TelegramBotsApi botApi = new TelegramBotsApi(DefaultBotSession.class);
        botApi.registerBot(bot);
        
        System.out.println("Bot is running.");
        
    }

    private static Properties loadProperties() {
        String fileName = "telegram_bot.properties";
        
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(fileName));
            return properties;
        } catch (IOException ex) {
            Logger.getLogger(TelegramBotFileBrowser.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new RuntimeException("Error load: " + fileName);
    }
}
