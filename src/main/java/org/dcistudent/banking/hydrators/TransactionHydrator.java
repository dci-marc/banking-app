package org.dcistudent.banking.hydrators;

import lombok.NonNull;
import org.dcistudent.banking.entities.Transaction;
import org.dcistudent.banking.interfaces.models.TransactionInterface;

public final class TransactionHydrator extends AbstractHydrator {
    @NonNull
    public Transaction hydrate(String[] fields) {
        Transaction transaction = new Transaction();
        transaction.setId(String.valueOf(fields[0]));
        transaction.setAccountId(String.valueOf(fields[1]));
        transaction.setAmount(Double.valueOf(fields[2]));
        transaction.setBalanceBefore(Double.valueOf(fields[3]));
        transaction.setBalanceAfter(Double.valueOf(fields[4]));
        transaction.setDatetime(String.valueOf(fields[5]));
        transaction.setActive(Boolean.valueOf(fields[6]));

        return transaction;
    }

    @NonNull
    public static TransactionInterface hydrate(Transaction transactionEntity) {
        TransactionInterface transaction = new org.dcistudent.banking.models.Transaction();
        transaction.setId(transactionEntity.getId());
        transaction.setAccountId(transactionEntity.getAccountId());
        transaction.setAmount(transactionEntity.getAmount());
        transaction.setBalanceBefore(transactionEntity.getBalanceBefore());
        transaction.setBalanceAfter(transactionEntity.getBalanceAfter());
        transaction.setDatetime(transactionEntity.getDatetime());
        transaction.setActive(transactionEntity.getActive());

        return transaction;
    }

    @NonNull
    public static Transaction hydrate(TransactionInterface transaction) {
        Transaction entity = new Transaction();
        entity.setId(transaction.getId());
        entity.setAccountId(transaction.getAccountId());
        entity.setAmount(transaction.getAmount());
        entity.setBalanceBefore(transaction.getBalanceBefore());
        entity.setBalanceAfter(transaction.getBalanceAfter());
        entity.setDatetime(transaction.getDatetime());
        entity.setActive(transaction.getActive());

        return entity;
    }
}
