package org.dcistudent.banking.factories;

import org.dcistudent.banking.models.AbstractAccount;
import org.dcistudent.banking.models.GoldAccount;
import org.dcistudent.banking.models.PlatinumAccount;
import org.dcistudent.banking.models.SilverAccount;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AccountFactory {
    private static final Map<Integer, String> ACCOUNT_TYPES;

    static {
        ACCOUNT_TYPES = new HashMap(Map.of(
                1, "SilverAccount",
                2, "GoldAccount",
                3, "PlatinumAccount"
        ));
    }

    public static AbstractAccount create(Integer type) {
        String accountId;

        if (ACCOUNT_TYPES.containsKey(type) == false) {
            throw new IllegalArgumentException("Invalid account type.");
        }

        accountId = UUID.randomUUID().toString();
        return switch (type) {
            case 1 -> new SilverAccount(accountId);
            case 2 -> new GoldAccount(accountId);
            case 3 -> new PlatinumAccount(accountId);
            default -> throw new IllegalArgumentException("Invalid account type.");
        };
    }
}
