package com.example.study;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class AppTest {

    @Test
    void greetReturnsGreeting() {
        assertEquals("Hello, World!", new App().greet("World"));
    }
}
