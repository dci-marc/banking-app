package org.dcistudent.banking.services;

import lombok.NonNull;
import org.dcistudent.banking.entities.Transaction;
import org.dcistudent.banking.facades.ScannerFacade;
import org.dcistudent.banking.hydrators.TransactionHydrator;
import org.dcistudent.banking.interfaces.models.AccountInterface;
import org.dcistudent.banking.interfaces.models.TransactionInterface;
import org.dcistudent.banking.managers.TransactionManager;
import org.dcistudent.banking.managers.criterias.FindByUuid;
import org.dcistudent.banking.renderers.ScannerRenderer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.UUID;

public class TransactionService {
    @NonNull
    private final TransactionManager transactionManager;

    public TransactionService() {
        this.transactionManager = new TransactionManager();
    }

    public void getTransactionsByDays(AccountInterface account) {
        Integer days = 1;

        ScannerRenderer.renderSeparated("Transactions");
        ScannerRenderer.renderInput("Enter number of days back to view transactions (max 30)");
        try {
            days = ScannerFacade.getInt();
            if (days < 1 || days > 30) {
                throw new IllegalArgumentException("Invalid number of days. Days must be between 1 and 30.");
            }
        } catch (IllegalArgumentException e) {
            ScannerRenderer.renderSeparated(e.getMessage());
            this.getTransactionsByDays(account);
        }

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Map<String, Transaction> transactions = this.transactionManager.findAllByDays(new FindByUuid(account.getId()), days);
        transactions
                .values()
                .forEach(transaction -> System.out.printf(
                        "%s - %s: %.2f - %s: %s - %s: %s%n",
                        LocalDateTime.parse(transaction.getDatetime()).format(df),
                        "Amount",
                        transaction.getAmount(),
                        "Balance before",
                        transaction.getBalanceBefore(),
                        "Balance after",
                        transaction.getBalanceAfter()
                ))
        ;
        ScannerRenderer.renderSeparated(String.format("Total: %d", transactions.size()));
    }

    public void registerTransaction(TransactionInterface transaction) {
        transaction.setId(UUID.randomUUID().toString());
        transaction.setDatetime(LocalDateTime.now().toString());
        this.transactionManager.persist(new TransactionHydrator(), TransactionHydrator.hydrate(transaction));
    }
}
