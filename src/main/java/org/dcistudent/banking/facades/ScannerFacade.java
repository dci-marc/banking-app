package org.dcistudent.banking.facades;

import org.dcistudent.banking.renderers.ScannerRenderer;

import java.util.Scanner;

public final class ScannerFacade {
    private static final ScannerFacade INSTANCE;
    private final Scanner scanner;

    static {
        INSTANCE = new ScannerFacade();
    }

    private ScannerFacade() {
        this.scanner = new Scanner(System.in).useDelimiter("\n");
    }

    public static ScannerFacade getInstance() {
        return INSTANCE;
    }

    public Integer getInt() {
        try {
            return Integer.parseInt(this.scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid input. Please enter a number.");
        }
    }

    public Double getDouble() {
        try {
            return Double.parseDouble(this.scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid input. Please enter a number.");
        }
    }

    public String getNonEmpty() {
        String input = this.scanner.nextLine().trim();
        if (input.isEmpty() == false) {
            return input;
        }
        throw new IllegalArgumentException("Invalid input. Please enter a non-empty string.");
    }
}
