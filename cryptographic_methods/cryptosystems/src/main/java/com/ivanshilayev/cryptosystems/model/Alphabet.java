package com.ivanshilayev.cryptosystems.model;

import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class Alphabet {
    private int size;
    List<Character> symbols;

    public Alphabet(String line) {
        size = line.length();
        symbols = line.chars().mapToObj(e -> (char) e).collect(Collectors.toList());
    }
}
