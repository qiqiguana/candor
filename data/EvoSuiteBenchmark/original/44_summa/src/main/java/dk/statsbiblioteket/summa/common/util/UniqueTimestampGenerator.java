/*
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package dk.statsbiblioteket.summa.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utility class that generates unique timestamps. The resulting timestamps
 * can not be treated as dates directly since they are constructed from the
 * system time by appending a salt. To retrieve the system time from the
 * generated timestamps use {@link #systemTime(long)}.
 * <p/>
 * The binary format of UniqueTimestampGenerator is part of its API and is
 * guaranteed to be stable.
 * <p/>
 * All timestamps generated with {@link #next()} are guaranteed to come out
 * in a sorted order. That is, they can be sorted as the standard Java
 * {@code long} type.
 */
public class UniqueTimestampGenerator {

    /**
     * Number of bits out of 64 used to store the system time.
     */
    public static final int TIME_BITS = 44;

    /**
     * Number of bits out of 64 used to store the salt.
     */
    public static final int SALT_BITS = 64 - TIME_BITS;

    /**
     * Maximum number of unique timestamps per millisecond.
     */
    // TODO: Shifting TIME_BITS is wrong. Fix this code
    public static final long MAX_SALT = ~0 >>> TIME_BITS; // ~0 is all ones

    /**
     * The maximal system time at which this class will function correctly.
     */
    public static final long MAX_TIME = (~MAX_SALT) >>> (SALT_BITS + 1);

    private long salt;
    private long last;
    private DateFormat dateFormat;

    /**
     * Create a new UniqueTimestampGenerator.
     * @throws AssertionError if the system time is past {@link #MAX_TIME}
     */
    public UniqueTimestampGenerator() {
        salt = 0;
        last = -1;

        if (System.currentTimeMillis () >= MAX_TIME) {
            throw new AssertionError("System time past " + MAX_TIME + ". UniqueTimestampGenerator can not" +
                                     " function past " + new Date(MAX_TIME));
        }

        dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
    }

    /**
     * Return a salted timestamp that is guaranteed to be unique among all
     * timestamps generated from this UniqueTimestampGenerator instance.
     * <p/>
     * To get the system time from the returned timestamp use the
     * {@link #systemTime(long)} method.
     * <p/>
     * If more than {@link #MAX_SALT} timestamps has been generated within the
     * same millisecond the genrator will sleep for 1 millisecond which will
     * reset the salt and allow for another {@link #MAX_SALT} timestamps to be
     * generated.
     * @return a sorted timestamp that is unique within the scope of this
     *         UniqueTimestampGenerator instance
     */
    public long next() {
        long sysTime = System.currentTimeMillis();

        updateSalt(sysTime);

        // If we reached the maximum salt value wait 1ms
        // (this will reset the salt)
        if (salt > MAX_SALT) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                // Ignore
            }
            return next();
        }

        return applySalt(sysTime);
    }

    /**
     * WARNING: Debug only - this method throws a RuntimeException if it is
     * impossible to create a unique timestamp.
     * @param systemTime The system time.
     * @return a unique long representing the system time.
     */
    long next(long systemTime) {
        updateSalt(systemTime);

        if (salt > MAX_SALT) {
            // We are screwed since this method can not just sleep() to wait for
            // a new system time...
            throw new RuntimeException("Forced to create non-unique salted timestamp");
        }

        return applySalt(systemTime);
    }

    /**
     * Applies the salt to the given system time. The returned value is valid
     * timestamp. To re-create the system time use {@link #systemTime(long)}
     *
     * @param systemTime the system timestamp to apply the salt to
     * @return a valid timestamp in the context of this class - ie. not a system
     *         time, but a salted timestamp that is a globally unique identifier
     */
    private long applySalt(long systemTime) {
        systemTime = systemTime << SALT_BITS;
        systemTime = systemTime | salt;
        return systemTime;
    }

    /**
     * If the system time has changed reset the salt,
     * the stamp will still be unique because the time-part has changed
     * @param systemTime the system time that should be used to calculate the
     *                   next salt value for
     */
    private void updateSalt(long systemTime) {
        if (systemTime == last) {
            salt++;
        } else {
            salt = 0;
            last = systemTime;
        }
    }

    /**
     * Extract the system time from a timestamp generated with {@link #next()}.
     * @param timestamp the system time as returned by
     * {@link System#currentTimeMillis()} on the creation of {@code timeStamp}
     * @return a unique time stamp.
     */
    public long systemTime(long timestamp) {
        return timestamp >>> SALT_BITS;
    }

    /**
     * Extract the salt part of {@code timeStamp}. The salt is unique for each
     * millisecond. That is for two time stamps {@code t1, t2} where
     * {@code systemTime(t1) == systemTime(t2)} the salt is guaranteed to be
     * different
     * @param timestamp the timestamp as generated by {@link #next()} to extract
     *                  the salt value from
     * @return the salt applied to timestamp to make it unique
     */
    public long salt(long timestamp) {
        // Clear the first TIME_BITS and return
        // (only the salt should be untouched)
        timestamp = timestamp << TIME_BITS;
        return timestamp >>> TIME_BITS;
    }

    /**
     * Return a salted timestamp that corresponds to the first unique timestamp
     * that would be created for {@code systemTime}. Use this method when you
     * need to check a system time against a salted timestamp.
     *
     * @param systemTime system time to convert to a salted timestamp
     *                   representing the first timestamp that would be
     *                   generated for {@code systemTime}
     * @return a timestamp useful for comparison against other timestamps
     */
    public long baseTimestamp (long systemTime) {
        return systemTime << SALT_BITS;
    }

    /**
     * Return a string representation of the salted timestamp {@code timestamp}.
     * The string is formatted as {@code "yyyy-MM-dd'T'HH:mm:ss.SSS"}, eg.
     * {@code 2001-07-04T12:08:56.235}.
     *
     * @param timestamp the salted timestamp to format
     * @return string formatted timestamp as described above
     */
    public String formatTimestamp(long timestamp){
        return formatSystemTime(systemTime(timestamp));
    }

    /**
     * Return a string representation of the (unsalted) system time
     * {@code systemTime} (as obtained from {@code System.currentTimeMillis()}).
     * The string is formatted as {@code "yyyy-MM-dd'T'HH:mm:ss.SSS"}, eg.
     * {@code 2001-07-04T12:08:56.235}.
     *
     * @param systemTime the system time, as obtained from
     *                   {@code System.currentTimeMillis()}
     * @return string formatted timestamp as described above
     */
    public String formatSystemTime(long systemTime) {
        return dateFormat.format(new Date(systemTime));
    }

    /**
     * Return a (unsalted) system time in milliseconds parsed from the string
     * {@code systemTime} based on the pattern
     * {@code "yyyy-MM-dd'T'HH:mm:ss.SSS"}.
     * <p/>
     * Use the method {@link #baseTimestamp(long)} to convert the result
     * into a salted timestamp. But beware that a timestamp generated this
     * way may not be unique.
     * @param systemTime string formatted system time as described above
     * @return number of milliseconds since the Unix Epoch
     * @throws ParseException if unable to parse String. 
     */
    public long parseSystemTime(String systemTime) throws ParseException {
        return dateFormat.parse(systemTime).getTime();
    }
}

