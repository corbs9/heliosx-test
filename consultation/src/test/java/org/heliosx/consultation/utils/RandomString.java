package org.heliosx.consultation.utils;

import org.apache.commons.text.RandomStringGenerator;

public class RandomString {
    private static final RandomStringGenerator rsg =
            RandomStringGenerator.builder().get();

    public static String getRandomString() {
        return rsg.generate(6);
    }
}
