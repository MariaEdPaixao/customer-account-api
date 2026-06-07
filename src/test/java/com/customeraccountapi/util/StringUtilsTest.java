package com.customeraccountapi.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringUtilsTest {

    @Test
    @DisplayName("Should convert a String camelCase to snake_case")
    void camelToSnack() {
        assertEquals("test_string_util", StringUtils.camelToSnack("testStringUtil"));
    }

    @Test
    @DisplayName("Should keep when already in snack_case")
    void shouldKeepSnakeCase() {
        assertEquals(
                "test_string",
                StringUtils.camelToSnack("test_string")
        );
    }
}