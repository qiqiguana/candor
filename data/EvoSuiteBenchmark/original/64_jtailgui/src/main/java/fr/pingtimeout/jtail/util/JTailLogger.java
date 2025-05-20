/*
 * Copyright (c) 2010 Pierre Laporte.
 *
 * This file is part of JTailPlus.
 *
 * JTailPlus is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JTailPlus is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JTailPlus.  If not, see <http://www.gnu.org/licenses/>.
 */

package fr.pingtimeout.jtail.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe utilitaire de Logging. Cette classe permet (de manière pas très correcte) de faire du logging sans avoir
 * de variable de log à déclarer. L'ensemble des logs est conditionné par une valeur statique pour pouvoir bénéficier
 * des optimisations du compilateur en cas de désactivation.
 *
 * @author Pierre Laporte
 *         Date: 14 avr. 2010
 */
public final class JTailLogger {
    public enum LoggerLevel {
        DEBUG, INFO, WARN, ERROR
    }

    private final static Map<String, Logger> loggerMap = new HashMap<String, Logger>();

    private static Logger getLogger() {
        final int indexOfCallingClass = 2;
        final String callingClass = new Throwable().getStackTrace()[indexOfCallingClass].getClassName();
        final String callingMethod = new Throwable().getStackTrace()[indexOfCallingClass].getMethodName();

        if (!loggerMap.containsKey(callingClass)) {
            loggerMap.put(callingClass, LoggerFactory.getLogger(callingClass));
        }

        return loggerMap.get(callingClass);
    }

    public static void debug(String s) {
        if (JTailConstants.LOG_LEVEL == LoggerLevel.DEBUG) {
            getLogger().debug(s);
        }
    }

    public static void debug(String s, Object o) {
        if (JTailConstants.LOG_LEVEL == LoggerLevel.DEBUG) {
            getLogger().debug(s, o);
        }
    }

    public static void debug(String s, Object o, Object o1) {
        if (JTailConstants.LOG_LEVEL == LoggerLevel.DEBUG) {
            getLogger().debug(s, o, o1);
        }
    }

    public static void debug(String s, Object[] objects) {
        if (JTailConstants.LOG_LEVEL == LoggerLevel.DEBUG) {
            getLogger().debug(s, objects);
        }
    }

    public static void debug(String s, Throwable throwable) {
        if (JTailConstants.LOG_LEVEL == LoggerLevel.DEBUG) {
            getLogger().debug(s, throwable);
        }
    }

    public static void debug(Marker marker, String s) {
        if (JTailConstants.LOG_LEVEL == LoggerLevel.DEBUG) {
            getLogger().debug(marker, s);
        }
    }

    public static void debug(Marker marker, String s, Object o) {
        if (JTailConstants.LOG_LEVEL == LoggerLevel.DEBUG) {
            getLogger().debug(marker, s, o);
        }
    }

    public static void debug(Marker marker, String s, Object o, Object o1) {
        if (JTailConstants.LOG_LEVEL == LoggerLevel.DEBUG) {
            getLogger().debug(marker, s, o, o1);
        }
    }

    public static void debug(Marker marker, String s, Object[] objects) {
        if (JTailConstants.LOG_LEVEL == LoggerLevel.DEBUG) {
            getLogger().debug(marker, s, objects);
        }
    }

    public static void debug(Marker marker, String s, Throwable throwable) {
        if (JTailConstants.LOG_LEVEL == LoggerLevel.DEBUG) {
            getLogger().debug(marker, s, throwable);
        }
    }

    public static void info(String s) {
        if (JTailConstants.LOG_LEVEL == LoggerLevel.DEBUG
                || JTailConstants.LOG_LEVEL == LoggerLevel.INFO) {
            getLogger().info(s);
        }
    }

    public static void info(String s, Object o) {
        if (JTailConstants.LOG_LEVEL == LoggerLevel.DEBUG
                || JTailConstants.LOG_LEVEL == LoggerLevel.INFO) {
            getLogger().info(s, o);
        }
    }

    public static void info(String s, Object o, Object o1) {
        if (JTailConstants.LOG_LEVEL == LoggerLevel.DEBUG
                || JTailConstants.LOG_LEVEL == LoggerLevel.INFO) {
            getLogger().info(s, o, o1);
        }
    }

    public static void info(String s, Object[] objects) {
        if (JTailConstants.LOG_LEVEL == LoggerLevel.DEBUG
                || JTailConstants.LOG_LEVEL == LoggerLevel.INFO) {
            getLogger().info(s, objects);
        }
    }

    public static void info(String s, Throwable throwable) {
        if (JTailConstants.LOG_LEVEL == LoggerLevel.DEBUG
                || JTailConstants.LOG_LEVEL == LoggerLevel.INFO) {
            getLogger().info(s, throwable);
        }
    }

    public static void info(Marker marker, String s) {
        if (JTailConstants.LOG_LEVEL == LoggerLevel.DEBUG
                || JTailConstants.LOG_LEVEL == LoggerLevel.INFO) {
            getLogger().info(marker, s);
        }
    }

    public static void info(Marker marker, String s, Object o) {
        if (JTailConstants.LOG_LEVEL == LoggerLevel.DEBUG
                || JTailConstants.LOG_LEVEL == LoggerLevel.INFO) {
            getLogger().info(marker, s, o);
        }
    }

    public static void info(Marker marker, String s, Object o, Object o1) {
        if (JTailConstants.LOG_LEVEL == LoggerLevel.DEBUG
                || JTailConstants.LOG_LEVEL == LoggerLevel.INFO) {
            getLogger().info(marker, s, o, o1);
        }
    }

    public static void info(Marker marker, String s, Object[] objects) {
        if (JTailConstants.LOG_LEVEL == LoggerLevel.DEBUG
                || JTailConstants.LOG_LEVEL == LoggerLevel.INFO) {
            getLogger().info(marker, s, objects);
        }
    }

    public static void info(Marker marker, String s, Throwable throwable) {
        if (JTailConstants.LOG_LEVEL == LoggerLevel.DEBUG
                || JTailConstants.LOG_LEVEL == LoggerLevel.INFO) {
            getLogger().info(marker, s, throwable);
        }
    }

    public static void warn(String s) {
        if (JTailConstants.LOG_LEVEL == LoggerLevel.DEBUG
                || JTailConstants.LOG_LEVEL == LoggerLevel.INFO
                || JTailConstants.LOG_LEVEL == LoggerLevel.WARN) {
            getLogger().warn(s);
        }
    }

    public static void warn(String s, Object o) {
        if (JTailConstants.LOG_LEVEL == LoggerLevel.DEBUG
                || JTailConstants.LOG_LEVEL == LoggerLevel.INFO
                || JTailConstants.LOG_LEVEL == LoggerLevel.WARN) {
            getLogger().warn(s, o);
        }
    }

    public static void warn(String s, Object[] objects) {
        if (JTailConstants.LOG_LEVEL == LoggerLevel.DEBUG
                || JTailConstants.LOG_LEVEL == LoggerLevel.INFO
                || JTailConstants.LOG_LEVEL == LoggerLevel.WARN) {
            getLogger().warn(s, objects);
        }
    }

    public static void warn(String s, Object o, Object o1) {
        if (JTailConstants.LOG_LEVEL == LoggerLevel.DEBUG
                || JTailConstants.LOG_LEVEL == LoggerLevel.INFO
                || JTailConstants.LOG_LEVEL == LoggerLevel.WARN) {
            getLogger().warn(s, o, o1);
        }
    }

    public static void warn(String s, Throwable throwable) {
        if (JTailConstants.LOG_LEVEL == LoggerLevel.DEBUG
                || JTailConstants.LOG_LEVEL == LoggerLevel.INFO
                || JTailConstants.LOG_LEVEL == LoggerLevel.WARN) {
            getLogger().warn(s, throwable);
        }
    }

    public static void warn(Marker marker, String s) {
        if (JTailConstants.LOG_LEVEL == LoggerLevel.DEBUG
                || JTailConstants.LOG_LEVEL == LoggerLevel.INFO
                || JTailConstants.LOG_LEVEL == LoggerLevel.WARN) {
            getLogger().warn(marker, s);
        }
    }

    public static void warn(Marker marker, String s, Object o) {
        if (JTailConstants.LOG_LEVEL == LoggerLevel.DEBUG
                || JTailConstants.LOG_LEVEL == LoggerLevel.INFO
                || JTailConstants.LOG_LEVEL == LoggerLevel.WARN) {
            getLogger().warn(marker, s, o);
        }
    }

    public static void warn(Marker marker, String s, Object o, Object o1) {
        if (JTailConstants.LOG_LEVEL == LoggerLevel.DEBUG
                || JTailConstants.LOG_LEVEL == LoggerLevel.INFO
                || JTailConstants.LOG_LEVEL == LoggerLevel.WARN) {
            getLogger().warn(marker, s, o, o1);
        }
    }

    public static void warn(Marker marker, String s, Object[] objects) {
        if (JTailConstants.LOG_LEVEL == LoggerLevel.DEBUG
                || JTailConstants.LOG_LEVEL == LoggerLevel.INFO
                || JTailConstants.LOG_LEVEL == LoggerLevel.WARN) {
            getLogger().warn(marker, s, objects);
        }
    }

    public static void warn(Marker marker, String s, Throwable throwable) {
        if (JTailConstants.LOG_LEVEL == LoggerLevel.DEBUG
                || JTailConstants.LOG_LEVEL == LoggerLevel.INFO
                || JTailConstants.LOG_LEVEL == LoggerLevel.WARN) {
            getLogger().warn(marker, s, throwable);
        }
    }
}
