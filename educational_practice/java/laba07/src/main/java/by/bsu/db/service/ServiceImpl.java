package by.bsu.db.service;

import by.bsu.db.dao.Transaction;

public abstract class ServiceImpl implements Service {
    protected Transaction transaction = null;

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}
