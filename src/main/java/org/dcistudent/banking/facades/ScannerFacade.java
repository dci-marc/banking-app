package org.dcistudent.banking.facades;

import java.util.Scanner;

public final class ScannerFacade {
    private static final Scanner SCANNER;

    static {
        SCANNER = new Scanner(System.in);
    }

    private ScannerFacade() {}

    public static Integer getInt() {
        try {
            return Integer.parseInt(SCANNER.nextLine().trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid input. Please enter a number.");
        }
    }

    public static Double getDouble() {
        try {
            return Double.parseDouble(SCANNER.nextLine().trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid input. Please enter a number.");
        }
    }

    public static String getNonEmpty() {
        String input = SCANNER.nextLine().trim();
        if (input.isEmpty() == false) {
            return input;
        }
        throw new IllegalArgumentException("Invalid input. Please enter a non-empty string.");
    }

    public static Boolean getYesNo() {
        String input = SCANNER.nextLine().trim();
        if (input.equalsIgnoreCase("y") || input.equalsIgnoreCase("yes")) {
            return true;
        }
        if (input.equalsIgnoreCase("n") || input.equalsIgnoreCase("no")) {
            return false;
        }
        throw new IllegalArgumentException("Invalid input. Please enter 'y' or 'n'.");
    }
}
