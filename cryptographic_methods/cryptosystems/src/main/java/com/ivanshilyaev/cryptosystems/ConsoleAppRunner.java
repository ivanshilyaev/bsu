package com.ivanshilyaev.cryptosystems;

import com.ivanshilyaev.cryptosystems.dao.DAOFactory;
import com.ivanshilyaev.cryptosystems.dao.LineReader;
import com.ivanshilyaev.cryptosystems.dao.exception.DAOException;

public class ConsoleAppRunner {
    private void start() {
        DAOFactory factory = DAOFactory.getInstance();
        LineReader reader = factory.getLineReader();
        try {
            System.out.println(reader.readLineFromFile("/Users/ivansilaev/Downloads/gitRepos/bsu/cryptographic_methods/cryptosystems/src/main/resources/alphabet.txt"));
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ConsoleAppRunner().start();
    }
}
