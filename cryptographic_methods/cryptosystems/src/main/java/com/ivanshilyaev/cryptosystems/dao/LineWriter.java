package com.ivanshilyaev.cryptosystems.dao;

import com.ivanshilyaev.cryptosystems.dao.exception.DAOException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class LineWriter {
    public void writeLineToFile(String line, String path) throws DAOException {
        try {
            Files.write(Paths.get(path), line.getBytes(), StandardOpenOption.WRITE);
        } catch (IOException e) {
            throw new DAOException("Couldn't write to file");
        }
    }
}
