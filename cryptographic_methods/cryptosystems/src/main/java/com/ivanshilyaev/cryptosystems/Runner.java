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
            String alphabetString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            //String alphabetString = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ ";
            String text = "HELLOWORLD";
            String key = "KEY";

            Validator validator = new AlphabetValidator();
            validator.validate(alphabetString);
            Alphabet alphabet = new Alphabet(alphabetString);
            validator = new VigenereCipherKeyValidator(alphabet);
            validator.validate(key);

            Cipher cipher = new VigenereCipher();

            String encrypted = cipher.encrypt(alphabet, text, key);
            String decrypted = cipher.decrypt(alphabet, encrypted, key);
            System.out.println("Encrypted: " + encrypted);
            System.out.println("Decrypted: " + decrypted);

        } catch (CipherException e) {
            System.out.println(e.getMessage());
        } catch (ValidationException e) {
            System.out.println(e.getMessage());
        }
    }
}
