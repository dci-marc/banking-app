package banking.loggers.formatters;

import java.time.ZonedDateTime;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

final public class JsonFormatter extends Formatter {
  @Override
  public String format(LogRecord logRecord) {
    return String.format(
        "{%s: %s, %s: %s, %s: %s}%n",
        "level", logRecord.getLevel(),
        "time", ZonedDateTime.now(),
        "message",
        logRecord.getMessage()
    );
  }
}
