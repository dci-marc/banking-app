package banking.loggers;

import banking.loggers.formatters.JsonFormatter;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

final public class JsonLogger {
  private static Logger INSTANCE;

  static {
    JsonLogger.INSTANCE = Logger.getLogger(JsonLogger.class.getName());
    JsonLogger.INSTANCE.setUseParentHandlers(false);
    try {
      FileHandler fileHandler = new FileHandler("log.json");
      fileHandler.setFormatter(new JsonFormatter());
      JsonLogger.INSTANCE.addHandler(fileHandler);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private JsonLogger() {
    throw new AssertionError("Cannot instantiate JsonLogger");
  }

  public static Logger getLogger() {
    return JsonLogger.INSTANCE;
  }
}
