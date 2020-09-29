package com.ivanshilyaev.cryptosystems;

import com.ivanshilyaev.cryptosystems.model.Alphabet;
import com.ivanshilyaev.cryptosystems.service.Cipher;
import com.ivanshilyaev.cryptosystems.service.exception.CipherException;
import com.ivanshilyaev.cryptosystems.service.impl.CaesarCipher;
import com.ivanshilyaev.cryptosystems.validator.Validator;
import com.ivanshilyaev.cryptosystems.validator.exception.ValidationException;
import com.ivanshilyaev.cryptosystems.validator.impl.AlphabetValidator;
import com.ivanshilyaev.cryptosystems.validator.impl.CaesarCipherKeyValidator;

public class Runner {

    public static void main(String[] args) {
        try {
            String alphabetString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            String text = "HELLO";
            String key = "B";

            Validator validator = new AlphabetValidator();
            validator.validate(alphabetString);
            Alphabet alphabet = new Alphabet(alphabetString);
            validator = new CaesarCipherKeyValidator(alphabet);
            validator.validate(key);

            Cipher cipher = new CaesarCipher();

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
