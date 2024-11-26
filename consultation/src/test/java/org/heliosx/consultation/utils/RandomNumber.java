package org.heliosx.consultation.utils;

import java.util.concurrent.ThreadLocalRandom;

public class RandomNumber {
    public static long getRandomLong() {
        return ThreadLocalRandom.current().nextLong();
    }

    public static int getRandomInt(int max) {
        return ThreadLocalRandom.current().nextInt(max);
    }
}
