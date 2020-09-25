package com.ivanshilyaev.cryptosystems;

import com.ivanshilyaev.cryptosystems.model.Alphabet;
import com.ivanshilyaev.cryptosystems.service.Cipher;
import com.ivanshilyaev.cryptosystems.service.exception.CipherException;
import com.ivanshilyaev.cryptosystems.service.impl.SimpleSubstitutionCipher;
import com.ivanshilyaev.cryptosystems.validator.Validator;
import com.ivanshilyaev.cryptosystems.validator.exception.ValidationException;
import com.ivanshilyaev.cryptosystems.validator.impl.SimpleSubstitutionCipherKeyValidator;

public class Runner {
    public static void main(String[] args) {
        try {
            Alphabet alphabet = new Alphabet("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
            Cipher cipher = new SimpleSubstitutionCipher();
            String text = "HELLOWORLD";
            String key = "XAZABCDEFGHIJKLMNOPQRSTUVW";

            Validator validator = new SimpleSubstitutionCipherKeyValidator(alphabet);
            validator.validate(key);

            String encrypted = cipher.encrypt(alphabet, text, key);
            String decrypted = cipher.decrypt(alphabet, encrypted, key);
            System.out.println("Encrypted text: " + encrypted);
            System.out.println("Decrypted text: " + decrypted);
        } catch (CipherException e) {
            System.out.println(e.getMessage());
        } catch (ValidationException e) {
            System.out.println(e.getMessage());
        }
    }
}
