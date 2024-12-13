package org.dcistudent.banking.managers;

import lombok.NonNull;
import org.dcistudent.banking.entities.Transaction;
import org.dcistudent.banking.hydrators.TransactionHydrator;
import org.dcistudent.banking.interfaces.entities.EntityInterface;
import org.dcistudent.banking.managers.criterias.FindByUuid;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class TransactionManager extends AbstractManager {
    private static final String FILE_PATH = "src/main/resources/transactions.csv";

    public TransactionManager() {
        super(FILE_PATH);
    }

    @NonNull
    public Map<String, Transaction> findAllByDays(FindByUuid id, Integer intervalDays) {
        Map<String, EntityInterface> map = super.findAll(new TransactionHydrator());
        Map<String, Transaction> transactions = new HashMap<>();
        map
                .values()
                .stream()
                .map(Transaction.class::cast)
                .filter(transaction -> transaction.getAccountId().equals(id.getId()))
                .filter(transaction -> LocalDateTime
                        .parse(transaction.getDatetime())
                        .isAfter(LocalDateTime.now().minusDays(intervalDays))
                )
                .forEach(transaction -> transactions.put(transaction.getId(), transaction))
        ;

        return transactions;
    }
}
