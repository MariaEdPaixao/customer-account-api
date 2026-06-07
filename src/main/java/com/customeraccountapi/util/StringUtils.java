package com.customeraccountapi.util;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class StringUtils {

    public static String camelToSnack(String value){
        return value.replaceAll("([a-z])([A-Z])", "$1_$2")
                .toLowerCase();
    }
}
