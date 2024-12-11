package org.dcistudent.banking.factories;

import org.dcistudent.banking.interfaces.models.AccountInterface;
import org.dcistudent.banking.models.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class AccountFactory {
    public static final Map<Integer, String> ACCOUNT_TYPES;

    static {
        ACCOUNT_TYPES = new HashMap(Map.of(
                1, "Silver",
                2, "Gold",
                3, "Platinum",
                4, "Savings",
                5, "Checking"
        ));
    }

    private AccountFactory() {}

    public static AccountInterface create(Integer type) {
        String accountId;

        if (ACCOUNT_TYPES.containsKey(type) == false) {
            throw new IllegalArgumentException("Invalid account type.");
        }

        accountId = UUID.randomUUID().toString();
        return switch (type) {
            case 1 -> new SilverAccount(accountId);
            case 2 -> new GoldAccount(accountId);
            case 3 -> new PlatinumAccount(accountId);
            case 4 -> new SavingsAccount(accountId);
            case 5 -> new CheckingAccount(accountId);
            default -> throw new IllegalArgumentException("Invalid account type.");
        };
    }
}
