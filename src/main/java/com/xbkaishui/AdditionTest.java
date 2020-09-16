package com.xbkaishui;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class AdditionTest {

    private int sum = 1;

    @Test
    void addingTwoReturnsThree() {
        sum += 2;
        assertEquals(3, sum);
    }

    @Test
    void addingThreeReturnsFour() {
        sum += 3;
        assertEquals(4, sum);
    }
}
