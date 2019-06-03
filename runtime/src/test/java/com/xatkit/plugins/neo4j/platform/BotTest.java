package com.xatkit.plugins.neo4j.platform;

import com.xatkit.Xatkit;

/**
 * This class is used to run existing bots, and should not contain test cases.
 */
public class BotTest {

    public static void main(String[] args) {
        Xatkit.main(new String[]{"<Path to Xatkit properties file>"});
        try {
            Thread.sleep(10000000);
        }catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
}
