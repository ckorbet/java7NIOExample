import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender

import static ch.qos.logback.classic.Level.DEBUG
import static ch.qos.logback.classic.Level.INFO

/*
 * This is a Groovy DLS Descriptior for LogBack configuration
 */

appender("STDOUT", ConsoleAppender) {
  encoder(PatternLayoutEncoder) {
    pattern = "[%d{HH:mm:ss,SSS}] %-5p: %m [%C.%M:%L]%n"
  }
}

logger("com.lordofthejars.foo", INFO)
root(DEBUG, ["STDOUT"])