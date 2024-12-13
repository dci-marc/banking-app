package org.dcistudent.banking.models;

import lombok.*;
import org.dcistudent.banking.exceptions.validations.customers.PasswordValidationException;
import org.dcistudent.banking.exceptions.validations.customers.UsernameValidationException;
import org.dcistudent.banking.factories.AccountFactory;
import org.dcistudent.banking.interfaces.models.AccountInterface;
import org.dcistudent.banking.interfaces.models.CustomerInterface;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.NoSuchAlgorithmException;

@Getter @Setter
public final class Customer implements CustomerInterface {
    @NonNull
    private String id;
    @NonNull
    private String username = "";
    @NonNull
    private String password = "";
    @NonNull
    private String firstName;
    @NonNull
    private Boolean active = true;
    @NonNull
    private AccountInterface account;
    private static final Integer PASSWORD_MIN_LENGTH = 6;

    @NonNull
    public void setUsername(String username) {
        if (username.isBlank()) {
            throw new UsernameValidationException("Username cannot be empty.");
        }

        if (!username.matches("[a-z]+")) {
            throw new UsernameValidationException("Username must contain only lowercase letters.");
        }

        this.username = username;
    }

    @NonNull
    public void setPassword(String password) throws NoSuchAlgorithmException {
        if (password.isBlank()) {
            throw new PasswordValidationException("Password cannot be empty.");
        }

        if (password.length() < PASSWORD_MIN_LENGTH) {
            throw new PasswordValidationException(
                    String.format("Password must be at least %d characters long.", PASSWORD_MIN_LENGTH)
            );
        }

        this.password = password;
    }

    @NonNull
    public String hashPassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    @NonNull
    public Boolean verifyPassword(String password) {
        return new BCryptPasswordEncoder().matches(password, this.getPassword());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getFirstName());
        sb.append(" (");
        sb.append(AccountFactory.ACCOUNT_TYPES.get(this.getAccount().getAccountType()));
        sb.append(" Account)");
        return sb.toString();
    }
}
