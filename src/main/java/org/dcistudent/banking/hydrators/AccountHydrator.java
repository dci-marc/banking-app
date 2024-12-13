package org.dcistudent.banking.hydrators;

import lombok.NonNull;
import org.dcistudent.banking.entities.Account;
import org.dcistudent.banking.factories.AccountFactory;
import org.dcistudent.banking.interfaces.models.AccountInterface;

public final class AccountHydrator extends AbstractHydrator {
    @NonNull
    public Account hydrate(String[] fields) {
        Account account = new Account();
        account.setId(String.valueOf(fields[0]));
        account.setCustomerId(String.valueOf(fields[1]));
        account.setPin(Integer.parseInt(fields[2]));
        account.setAccountType(Integer.parseInt(fields[3]));
        account.setBalance(Double.parseDouble(fields[4]));
        account.setOverdraftCount(Integer.parseInt(fields[5]));

        return account;
    }

    @NonNull
    public static AccountInterface hydrate(Account accountEntity) {
        AccountInterface account = AccountFactory.create(accountEntity.getAccountType());
        account.setId(accountEntity.getId());
        account.setCustomerId(accountEntity.getCustomerId());
        account.setPin(accountEntity.getPin());
        account.setAccountType(accountEntity.getAccountType());
        account.setBalance(accountEntity.getBalance());
        account.setOverdraftCount(accountEntity.getOverdraftCount());

        return account;
    }

    @NonNull
    public static Account hydrate(AccountInterface account) {
        Account entity = new Account();
        entity.setId(account.getId());
        entity.setCustomerId(account.getCustomerId());
        entity.setPin(account.getPin());
        entity.setAccountType(account.getAccountType());
        entity.setBalance(account.getBalance());
        entity.setOverdraftCount(account.getOverdraftCount());

        return entity;
    }
}
