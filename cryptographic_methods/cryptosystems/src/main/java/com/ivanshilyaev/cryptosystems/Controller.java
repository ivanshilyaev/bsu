package com.ivanshilyaev.cryptosystems;

import com.ivanshilyaev.cryptosystems.model.Alphabet;
import com.ivanshilyaev.cryptosystems.service.Cipher;
import com.ivanshilyaev.cryptosystems.service.exception.CipherException;
import com.ivanshilyaev.cryptosystems.service.exception.CipherException;
import com.ivanshilyaev.cryptosystems.service.impl.*;
import com.ivanshilyaev.cryptosystems.validator.Validator;
import com.ivanshilyaev.cryptosystems.validator.exception.ValidationException;
import com.ivanshilyaev.cryptosystems.validator.impl.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@org.springframework.stereotype.Controller
@RequestMapping("/")
public class Controller {

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @PostMapping("/index")
    public String processTask(Model model,
                              @RequestParam(name = "alphabet") String alphabetString,
                              @RequestParam(name = "text") String text,
                              @RequestParam(name = "key") String key,
                              @RequestParam(name = "cipher") String cipherChoice,
                              @RequestParam(name = "task") String task) {
        String result = "";
        try {
            Validator validator = new AlphabetValidator();
            validator.validate(alphabetString);
            Alphabet alphabet = new Alphabet(alphabetString);
            validator = new TextValidator(alphabet);
            validator.validate(text);

            Cipher cipher;

            switch (cipherChoice) {
                case "Caesar cipher" -> {
                    cipher = new CaesarCipher();
                    validator = new CaesarCipherKeyValidator(alphabet);
                    validator.validate(key);
                }
                case "Affine cipher" -> {
                    cipher = new AffineCipher();
                    validator = new AffineCipheKeyValidator(alphabet);
                    validator.validate(key);
                }
                case "Simple substitution cipher" -> {
                    cipher = new SimpleSubstitutionCipher();
                    validator = new SimpleSubstitutionCipherKeyValidator(alphabet);
                    validator.validate(key);
                }
                case "Hill cipher" -> {
                    cipher = new HillCipher();
                    validator = new HillCipherKeyValidator(alphabet);
                    validator.validate(key);
                }
                case "Transposition cipher" -> {
                    cipher = new TranspositionCipher();
                    validator = new TranspositionCipherKeyValidator(alphabet);
                    validator.validate(key);
                }
                case "Vigenere cipher" -> {
                    cipher = new VigenereCipher();
                    validator = new VigenereCipherKeyValidator(alphabet);
                    validator.validate(key);
                }
                default -> {
                    cipher = new CaesarCipher();
                }
            }

            switch (task) {
                case "encrypt": {
                    result = cipher.encrypt(alphabet, text, key);
                    break;
                }
                case "decrypt": {
                    result = cipher.decrypt(alphabet, text, key);
                    break;
                }
                default:
                    break;
            }
        } catch (CipherException | ValidationException e) {
            result = e.getMessage();
        }

        model.addAttribute("result", result);
        System.out.println(result);
        return "index";
    }
}
