package com.ivanshilyaev.diehard;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class BinFileReader {

    public static byte[] readBytes(String fileName) throws IOException {
        return Files.readAllBytes(Paths.get(fileName));
    }
}
