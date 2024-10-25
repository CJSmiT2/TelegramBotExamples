package com.smit.telegrambotfilebrowser;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

/**
 *
 * @author SmiT
 */
class FileBrowser {

    private final Map<String, File> filesMap = new HashMap();
    private final File rootFolder;

    FileBrowser(File rootFolder) {
        this.rootFolder = rootFolder;
    }

    InlineKeyboardMarkup getKeyboard(File folder) {
        filesMap.clear();

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        if (!folder.equals(rootFolder)) {
            String idPrevFolder = createIdentificator(folder.getParentFile());
            filesMap.put(idPrevFolder, folder.getParentFile());

            InlineKeyboardButton back = new InlineKeyboardButton();
            back.setText("< return");
            back.setCallbackData(idPrevFolder);
            keyboard.add(List.of(back));
        }

        for (File file : folder.listFiles()) {
            String id = createIdentificator(file);
            filesMap.put(id, file);

            InlineKeyboardButton button = new InlineKeyboardButton();
            if (file.isDirectory()) {
                button.setText("📁 " + file.getName() + "/");
            } else {
                button.setText("📄 " + file.getName());
            }

            button.setCallbackData(id);
            keyboard.add(List.of(button));
        }

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(keyboard);
        return markup;
    }

    private String createIdentificator(File file) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(file.getAbsolutePath().getBytes());
            return Base64.getEncoder().encodeToString(hashBytes);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(FileBrowser.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new RuntimeException();
    }
    
    File getFile(String id){
        return filesMap.get(id);
    }

}
