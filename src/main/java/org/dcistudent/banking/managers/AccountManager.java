package org.dcistudent.banking.managers;

import org.dcistudent.banking.entities.Account;
import org.dcistudent.banking.hydrators.AccountHydrator;
import org.dcistudent.banking.interfaces.entities.EntityInterface;
import org.dcistudent.banking.managers.criterias.FindByUuid;

import java.util.HashMap;
import java.util.Map;

public final class AccountManager extends AbstractManager {
    private static final String FILE_PATH = "src/main/resources/accounts.csv";

    public AccountManager() {
        super(FILE_PATH);
    }

    public Map<String, Account> findAll() {
        Map<String, EntityInterface> map = super.findAll(new AccountHydrator());
        Map<String, Account> accounts = new HashMap<>();
        map.forEach((k, v) -> accounts.put(k, (Account) v));

        return accounts;
    }

    public Account findById(FindByUuid criteria) throws Exception {
        return (Account) super.findById(new AccountHydrator(), criteria);
    }

    public Account findByCustomerId(FindByUuid criteria) {
        return this
                .findAll()
                .values()
                .stream()
                .filter(account -> account.getCustomerId().equals(criteria.getId()))
                .findFirst()
                .orElseThrow()
        ;
    }
}
