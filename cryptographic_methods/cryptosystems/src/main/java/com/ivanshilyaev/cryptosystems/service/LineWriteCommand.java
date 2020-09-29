package com.ivanshilyaev.cryptosystems.service;

import com.ivanshilyaev.cryptosystems.dao.DAOFactory;
import com.ivanshilyaev.cryptosystems.dao.LineWriter;
import com.ivanshilyaev.cryptosystems.dao.exception.DAOException;

public class LineWriteCommand {
    public void writeLine(String line, String path) throws DAOException {
        DAOFactory factory = DAOFactory.getInstance();
        LineWriter writer = factory.getLineWriter();
        writer.writeLineToFile(line, path);
    }
}
