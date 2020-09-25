package com.ivanshilyaev.cryptosystems.dao;

import com.ivanshilyaev.cryptosystems.dao.exception.DAOException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

public class LineReader {
    public String readLineFromFile(String path) throws DAOException {
        String[] entries;
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            Object[] array = stream.toArray();
            entries = Arrays.copyOf(array, array.length, String[].class);
        } catch (IOException e) {
            throw new DAOException("Couldn't read from file", e.getCause());
        }
        return entries[0];
    }
}
