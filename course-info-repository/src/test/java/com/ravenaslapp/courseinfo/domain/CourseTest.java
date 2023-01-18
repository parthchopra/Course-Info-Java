package com.ravenaslapp.courseinfo.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CourseTest {

    @Test
    void blankIdThrowsException() {
        var exception = assertThrows(
                IllegalArgumentException.class, () -> new Course(null, "testName", 0, "testUrl", Optional.empty())
        );

        assertTrue(exception.getMessage().contentEquals("No value present.!"));
    }

    @Test
    void blankNameThrowsException() {
        var exception = assertThrows(
                IllegalArgumentException.class, () -> new Course("testId", "", 0, "testUrl", Optional.empty())
        );

        assertTrue(exception.getMessage().contentEquals("No value present.!"));
    }

    @Test
    void blankUrlThrowsException() {
        var exception = assertThrows(
                IllegalArgumentException.class, () -> new Course("testId", "testName", 0, null, Optional.empty())
        );

        assertTrue(exception.getMessage().contentEquals("No value present.!"));
    }
}