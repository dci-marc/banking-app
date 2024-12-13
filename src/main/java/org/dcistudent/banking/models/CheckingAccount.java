package org.dcistudent.banking.models;

import lombok.*;

/**
 * The checking account implementation will diverge from the requirement made by Omar due to misreading from my side.
 * From Omar, silver, gold or platinum are types of a savings or checking account that specifies limits.
 *
 * This implementation diverges from that as the name very well fits a German debit card (Giro account) which checks
 * your balance/overdraft instantly when paying in a shop. An international debit card always grants the payment and the
 * balance is checked a while after the payment which can result in a decline of the payment for a shop minutes or hours
 * later.
 * This does not differ to the silver, gold or platinum accounts/cards, but that the balance was checked immediately on
 * a payment and is booked instantly. For simplicity this will be just echoed out in the account service class.
 */
@Getter @Setter
public class CheckingAccount extends AbstractAccount {
    private Integer accountType = 5;
    public static final Double LIMIT_WITHDRAWAL = 1000.00;
    public static final Double LIMIT_DEPOSIT = 3000.00;

    @NonNull
    public CheckingAccount(String accountId) {
        super(accountId, LIMIT_WITHDRAWAL, LIMIT_DEPOSIT);
    }
}
