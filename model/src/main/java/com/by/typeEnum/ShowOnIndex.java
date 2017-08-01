package com.by.typeEnum;

import java.util.Locale;

/**
 * Created by yagamai on 16-3-30.
 */
public enum ShowOnIndex {
    NOTSHOWONINDEX, SHOWONINDEX;

    public static ShowOnIndex fromString(String source) {
        try {
            return ShowOnIndex.valueOf(source.toUpperCase(Locale.US));
        } catch (Exception e) {
            return null;
        }
    }
}
