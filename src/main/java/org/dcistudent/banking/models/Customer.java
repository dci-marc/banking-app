package org.dcistudent.banking.models;

import lombok.*;
import org.dcistudent.banking.exceptions.validations.customers.PasswordValidationException;
import org.dcistudent.banking.exceptions.validations.customers.UsernameValidationException;
import org.dcistudent.banking.factories.AccountFactory;
import org.dcistudent.banking.interfaces.models.AccountInterface;
import org.dcistudent.banking.interfaces.models.CustomerInterface;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
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
    private AccountInterface account;
    private static final Integer PASSWORD_MIN_LENGTH = 6;

    public void setUsername(String username) {
        if (username.isBlank()) {
            throw new UsernameValidationException("Username cannot be empty.");
        }

        if (!username.matches("[a-z]+")) {
            throw new UsernameValidationException("Username must contain only lowercase letters.");
        }

        this.username = username;
    }

    public void setPassword(String password) throws NoSuchAlgorithmException {
        if (password.isBlank()) {
            throw new PasswordValidationException("Password cannot be empty.");
        }

        if (password.length() < PASSWORD_MIN_LENGTH) {
            throw new PasswordValidationException(
                    String.format("Password must be at least %d characters long.", PASSWORD_MIN_LENGTH)
            );
        }

        this.password = this.hashPassword(password);
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder(2 * hash.length);
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw e;
        }
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
