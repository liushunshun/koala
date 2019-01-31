package com.koala.core.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KoalaCoreLogger {

    public static Logger koalaCoreLogger;

    static {
        koalaCoreLogger = LoggerFactory.getLogger("koalaCore");
    }

}
