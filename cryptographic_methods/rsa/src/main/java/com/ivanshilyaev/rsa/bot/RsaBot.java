package com.ivanshilyaev.rsa.bot;

import com.ivanshilyaev.rsa.exception.RsaException;
import com.ivanshilyaev.rsa.service.RsaImpl;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class RsaBot extends TelegramLongPollingBot {

    private static final RsaImpl rsaImpl = new RsaImpl();

    @Override
    public String getBotUsername() {
        return "rsaCustomBot";
    }

    @Override
    public String getBotToken() {
        return "1789680564:AAHyVsnV67ozxljr2-7Gmj2HsAOg4g-cURg";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String text  = update.getMessage().getText();
            String returnText = "";
            switch (text) {
                case "/start": {
                    returnText = "Greetings from the amazing RSA Custom Bot!";
                    break;
                }
                case "/help": {
                    returnText = "Available commands:" +
                        System.lineSeparator() +
                        "/start - Greetings!" +
                        System.lineSeparator() +
                        "/help - View all commands" +
                        System.lineSeparator() +
                        "/gen {l} - Generate RSA module with length l, public and private keys" +
                        System.lineSeparator() +
                        "/enc {public key} {data} - Encrypt text using provided public key" +
                        System.lineSeparator() +
                        "/dec {data} - Decrypt text using generated parameters";
                    break;
                }
                default: {
                    try {
                        if (text.startsWith("/gen")) {
                            returnText = proceedGenCommand(text);
                        }
                        else if (text.startsWith("/enc")) {
                            returnText = proceedEncCommand(text);
                        }
                        else if (text.startsWith("/dec")) {
                            returnText = proceedDecCommand(text);
                        }
                    } catch (Exception ignored) {}
                    if (returnText.isEmpty()) {
                        returnText = "Unknown command! Use /help to view list of available commands";
                    }
                    break;
                }
            }
            SendMessage message = new SendMessage();
            message.setChatId(update.getMessage().getChatId().toString());
            message.setText(returnText);
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    private String proceedGenCommand(String text) {
        try {
            int l = Integer.parseInt(text.split(" ")[1]);
            rsaImpl.generate(l);
            String publicKey = rsaImpl.getPublicKey();
            return "Your public key is " + publicKey;
        } catch (Exception ignored) {}
        return "Error during parameters generation";
    }

    private String proceedEncCommand(String text) {
        try {
            String[] array = text.split(" ");
            String publicKey = array[1];
            String data = text.substring(6 + publicKey.length());
            return rsaImpl.encryptText(data, publicKey);
        } catch (RsaException e) {
            return e.getMessage();
        } catch (Exception ignored) {}
        return "Error during text encryption";
    }

    private String proceedDecCommand(String text) {
        try {
            String data = text.substring(5);
            return rsaImpl.decryptText(data);
        } catch (Exception ignored) {}
        return "Error during text decryption";
    }
}
