package com.smit.telegrambotexample1;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/**
 *
 * @author SmiT
 */
public class TelegramBotExample1 {

    public static void main(String[] args) throws TelegramApiException {
        System.out.println("Starting bot...");
        
        TelegramBot bot = new TelegramBot();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(bot);
        
        System.out.println("Bot is running.");
    }
}
