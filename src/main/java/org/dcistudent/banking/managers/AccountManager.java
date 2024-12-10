package org.dcistudent.banking.managers;

import org.dcistudent.banking.entities.Account;
import org.dcistudent.banking.hydrators.AccountHydrator;
import org.dcistudent.banking.interfaces.entities.EntitiyInterface;

import java.util.HashMap;
import java.util.Map;

public final class AccountManager extends AbstractManager {
    private static final String FILE_PATH = "src/main/resources/accounts.json";

    public AccountManager() {
        super(FILE_PATH);
    }

    public Map<String, Account> findAll() {
        Map<String, EntitiyInterface> map = super.findAll(new AccountHydrator());
        Map<String, Account> accounts = new HashMap<>();
        map.forEach((k, v) -> accounts.put(k, (Account) v));

        return accounts;
    }

    public Account findById(String id) {
        return (Account) super.findById(new AccountHydrator(), id);
    }
}
