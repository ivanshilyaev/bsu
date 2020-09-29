package com.ivanshilyaev.cryptosystems.service;

import com.ivanshilyaev.cryptosystems.dao.DAOFactory;
import com.ivanshilyaev.cryptosystems.dao.LineReader;
import com.ivanshilyaev.cryptosystems.dao.exception.DAOException;

public class LineReaderCommand {
    public String readLine(String path) throws DAOException {
        DAOFactory factory = DAOFactory.getInstance();
        LineReader reader = factory.getLineReader();
        return reader.readLineFromFile(path);
    }
}
