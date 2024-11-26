package org.heliosx.consultation.utils;

import static org.heliosx.consultation.utils.RandomNumber.getRandomInt;

public class RandomEnum {
    public static <T extends Enum<T>> T getRandomEnum(Class<T> clazz) {
        return clazz.getEnumConstants()[getRandomInt(clazz.getEnumConstants().length)];
    }
}
