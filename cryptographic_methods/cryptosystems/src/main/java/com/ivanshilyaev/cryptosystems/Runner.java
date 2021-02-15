package com.ivanshilyaev.cryptosystems;

import com.ivanshilyaev.cryptosystems.model.Alphabet;
import com.ivanshilyaev.cryptosystems.service.Cipher;
import com.ivanshilyaev.cryptosystems.service.exception.CipherException;
import com.ivanshilyaev.cryptosystems.service.impl.*;
import com.ivanshilyaev.cryptosystems.validator.Validator;
import com.ivanshilyaev.cryptosystems.validator.exception.ValidationException;
import com.ivanshilyaev.cryptosystems.validator.impl.*;

public class Runner {

    public static void main(String[] args) {
        try {
            String alphabetString = ",АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ абвгдеёжзийклмнопрстуфхцчшщъыьэюя.";
            //String alphabetString = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ ";
            String text = "шцзиЬчйЁЯДзгъЖфЕуэПеляИОСРуэщРикютОюЯДТуьудкъоИОкЖяЪфЯя.Щьже.ыРЁжеъЫЯЮ НхАПеляИОу ";
            String key = "Макс";

            Validator validator = new AlphabetValidator();
            validator.validate(alphabetString);
            Alphabet alphabet = new Alphabet(alphabetString);
            validator = new HillCipherKeyValidator(alphabet);
            validator.validate(key);

            Cipher cipher = new HillCipher();

            String decrypted = cipher.decrypt(alphabet, text, key);
            System.out.println("Decrypted: " + decrypted);

        } catch (CipherException | ValidationException e) {
            System.out.println(e.getMessage());
        }
    }
}
