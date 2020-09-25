package com.ivanshilyaev.cryptosystems.dao;

public class DAOFactory {
    private static final DAOFactory instance = new DAOFactory();

    private final LineReader reader = new LineReader();
    private final LineWriter writer = new LineWriter();

    private DAOFactory() {
    }

    public static DAOFactory getInstance() {
        return instance;
    }

    public LineReader getLineReader() {
        return reader;
    }

    public LineWriter getLineWriter() {
        return writer;
    }
}
