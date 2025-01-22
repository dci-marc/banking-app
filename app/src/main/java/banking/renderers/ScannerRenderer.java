package banking.renderers;

import lombok.NonNull;

public final class ScannerRenderer {
    private static final String SEPARATOR =
            "================================================================================"
    ;

    private ScannerRenderer() {}

    @NonNull
    public static void renderSeparated(String line) {
        System.out.printf("%n%s%n%s%n", line, SEPARATOR);
    }

    public static void renderSeparator() {
        System.out.printf("%s%n", SEPARATOR);
    }

    @NonNull
    public static void renderInput(String message) {
        System.out.printf("%s: ", message);
    }

    public static void renderInputChoice() {
        System.out.print("Choice: ");
    }
}
