package com.xbkaishui;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/**
 * @author xbkaishui
 * @version $Id: OrderUnitTest.java,  2020-09-16 3:22 PM xbkaishui Exp $$
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderUnitTest {
    
    @Test
    @Order(1)
    void firstTest() {
        System.out.println("in first order");
    }
    
    @Test
    @Order(2)
    void secondTest() {
        System.out.println("in second order");
    }
}
