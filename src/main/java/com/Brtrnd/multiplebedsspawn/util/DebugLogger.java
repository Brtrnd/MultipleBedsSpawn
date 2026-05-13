package com.Brtrnd.multiplebedsspawn.util;

import com.Brtrnd.multiplebedsspawn.MultipleBedsSpawn;

/**
 * Simple debug logger wrapper.
 */
public class DebugLogger {

    public static void log(String message) {
        if (!MultipleBedsSpawn.getInstance()
                .getConfig()
                .getBoolean("debug", false)) return;

        MultipleBedsSpawn.getInstance()
                .getLogger()
                .info("[DEBUG] " + message);
    }
}
``