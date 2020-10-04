package com.ivanshilyaev.cryptosystems;

import com.ivanshilyaev.cryptosystems.dao.exception.DAOException;
import com.ivanshilyaev.cryptosystems.model.Alphabet;
import com.ivanshilyaev.cryptosystems.service.Cipher;
import com.ivanshilyaev.cryptosystems.service.LineReaderCommand;
import com.ivanshilyaev.cryptosystems.service.LineWriteCommand;
import com.ivanshilyaev.cryptosystems.service.exception.CipherException;
import com.ivanshilyaev.cryptosystems.service.impl.*;
import com.ivanshilyaev.cryptosystems.validator.Validator;
import com.ivanshilyaev.cryptosystems.validator.exception.ValidationException;
import com.ivanshilyaev.cryptosystems.validator.impl.*;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleAppRunner {

    private static final String ALPHABET_PATH_DEFAULT = "src/main/resources/alphabet.txt";
    private static final String TEXT_PATH_DEFAULT = "src/main/resources/in.txt";
    private static final String KEY_PATH_DEFAULT = "src/main/resources/key.txt";
    private static final String ENCRYPT_PATH_DEFAULT = "src/main/resources/encrypt.txt";
    private static final String DECRYPT_PATH_DEFAULT = "src/main/resources/decrypt.txt";
    private static final String DEFAULT = "d";

    private void start() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter alphabet file path (d - default, 0 - default alphabet):");
            String alphabetPath = scanner.nextLine();
            if (alphabetPath.equals(DEFAULT)) {
                alphabetPath = ALPHABET_PATH_DEFAULT;
            }
            System.out.println("Enter text file path (d - default):");
            String textPath = scanner.nextLine();
            if (textPath.equals(DEFAULT)) {
                textPath = TEXT_PATH_DEFAULT;
            }
            System.out.println("Enter key file path (d - default):");
            String keyPath = scanner.nextLine();
            if (keyPath.equals(DEFAULT)) {
                keyPath = KEY_PATH_DEFAULT;
            }
            System.out.println("Enter operation: 0 - encrypt, 1 - decrypt");
            int operationChoice = scanner.nextInt();
            System.out.println("Enter cryptosystem to use:");
            System.out.println("0 - Caesar cipher");
            System.out.println("1 - Affine cipher");
            System.out.println("2 - Simple substitution cipher");
            System.out.println("3 - Hill cipher");
            System.out.println("4 - Transposition cipher");
            System.out.println("5 - Vigenere cipher");
            int cipherChoice = scanner.nextInt();

            LineReaderCommand readerCommand = new LineReaderCommand();

            String alphabetString;
            if (alphabetPath.equals("0")) {
                alphabetString = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ ";
            } else {
                alphabetString = readerCommand.readLine(alphabetPath);
            }
            String text = readerCommand.readLine(textPath);
            String key = readerCommand.readLine(keyPath);

            Validator validator = new AlphabetValidator();
            validator.validate(alphabetString);
            Alphabet alphabet = new Alphabet(alphabetString);
            validator = new TextValidator(alphabet);
            validator.validate(text);

            Cipher cipher;

            switch (cipherChoice) {
                case 0 -> {
                    cipher = new CaesarCipher();
                    validator = new CaesarCipherKeyValidator(alphabet);
                    validator.validate(key);
                }
                case 1 -> {
                    cipher = new AffineCipher();
                    validator = new AffineCipheKeyValidator(alphabet);
                    validator.validate(key);
                }
                case 2 -> {
                    cipher = new SimpleSubstitutionCipher();
                    validator = new SimpleSubstitutionCipherKeyValidator(alphabet);
                    validator.validate(key);
                }
                case 3 -> {
                    cipher = new HillCipher();
                    validator = new HillCipherKeyValidator(alphabet);
                    validator.validate(key);
                }
                case 4 -> {
                    cipher = new TranspositionCipher();
                    validator = new TranspositionCipherKeyValidator(alphabet);
                    validator.validate(key);
                }
                case 5 -> {
                    cipher = new VigenereCipher();
                    validator = new VigenereCipherKeyValidator(alphabet);
                    validator.validate(key);
                }
                default -> {
                    cipher = new CaesarCipher();
                }
            }

            LineWriteCommand writeCommand = new LineWriteCommand();
            switch (operationChoice) {
                case 0: {
                    String encrypted = cipher.encrypt(alphabet, text, key);
                    writeCommand.writeLine(encrypted, ENCRYPT_PATH_DEFAULT);
                    break;
                }
                case 1: {
                    String decrypted = cipher.decrypt(alphabet, text, key);
                    writeCommand.writeLine(decrypted, DECRYPT_PATH_DEFAULT);
                    break;
                }
                default:
                    break;
            }

            System.out.println("Success!");
        } catch (DAOException e) {
            System.out.println("Error: invalid file path!");
        } catch (ValidationException e) {
            System.out.println(e.getMessage());
        } catch (CipherException e) {
            e.printStackTrace();
        } catch (InputMismatchException e) {
            System.out.println("Error: incorrect input!");
        }
    }

    public static void main(String[] args) {
        new ConsoleAppRunner().start();
    }
}
