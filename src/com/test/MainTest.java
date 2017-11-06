package com.test;

import com.moxe.Main;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    protected BigDecimal value;
    protected BigDecimal increment;
    protected BigDecimal price;
    protected RoundingMode roundingMode;
    protected String description;
    protected Main tester;

    @BeforeEach
    void setUp() {
        value = new BigDecimal("57.501");
        increment = new BigDecimal(0.05);
        roundingMode = RoundingMode.UP;
        price = new BigDecimal("100");
        description = "400 imported at 45 crate of Almond Snickers at 75.99";
        tester = new Main();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void calcImportTax() {
        assertEquals(new BigDecimal("5.00"), tester.calcImportTax(price));
    }

    @Test
    void round() {
        assertEquals(new BigDecimal("57.55"), tester.round(value, increment, roundingMode));
    }

    @Test
    void calcSalesTax() {
        assertEquals(new BigDecimal("10.00"), tester.calcSalesTax(price));
    }

    @Test
    void parseItem() {
        assertEquals("400", tester.parseItem(description)[0]);
        assertEquals("imported at 45 crate of Almond Snickers", tester.parseItem(description)[1]);
        assertEquals("75.99", tester.parseItem(description)[2]);
    }



}