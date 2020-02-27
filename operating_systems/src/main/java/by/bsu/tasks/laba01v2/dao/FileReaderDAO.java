package by.bsu.tasks.laba01v2.dao;

import by.bsu.tasks.laba01v2.dao.exception.DAOException;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

public class FileReaderDAO {
    public String[] read(File file) throws DAOException {
        String[] entries;
        try (Stream<String> stream = Files.lines(Paths.get(file.getAbsolutePath()))) {
            Object[] array = stream.toArray();
            entries = Arrays.copyOf(array, array.length, String[].class);
        } catch (IOException | UncheckedIOException e) {
            throw new DAOException("Couldn't read from file", e.getCause());
        }
        return entries;
    }
}
