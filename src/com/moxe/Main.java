package com.moxe;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws Exception {
	    ArrayList<String> data;
	    ReadFile reader = new ReadFile();
	    data = reader.read(args[0]);
        printReceipt(data);
    }

    /**
     * Prints out the receipt for the shopping basket that was read
     * in via a text file.
     *
     * @param  data  a String ArrayList containing the text read in
     *               from the input file
     * @throws Exception
     */
    private static void printReceipt(ArrayList<String> data) throws Exception {
        String[] itemInfo;
        new BigDecimal(0);
        BigDecimal salesTax;
        BigDecimal importTax;
        BigDecimal basketTotal = new BigDecimal(0);
        BigDecimal totalTax = new BigDecimal(0);
        for (String obj : data) {
            itemInfo = parseItem(obj);
            Item item = new Item(itemInfo[0],itemInfo[1],itemInfo[2]);
            BigDecimal itemTotal = item.price;
            if (!item.isCandy() && !item.isCoffee() && !item.isPopcorn()) {
                salesTax = calcSalesTax(item.price);
                itemTotal = itemTotal.add(salesTax);
                totalTax = totalTax.add(salesTax);
            }
            if (item.isImported()) {
                importTax = calcImportTax(item.price);
                itemTotal = itemTotal.add(importTax);
                totalTax = totalTax.add(importTax);
            }
            itemTotal = itemTotal.multiply(new BigDecimal(item.quantity));
            basketTotal = basketTotal.add(itemTotal);
            System.out.println(item.quantity + " " + item.description + ": " + itemTotal);
        }
        System.out.println("Sales Taxes: " + totalTax);
        System.out.println("Total: " + basketTotal);
    }

    /**
     * Calculates the tax on an imported item
     * using a rate of 5%.
     *
     * @param  price  the price of the item
     * @return a BigDecimal representation of the import tax
     */
    public static BigDecimal calcImportTax(BigDecimal price) {
        BigDecimal increment = new BigDecimal("0.05");
        BigDecimal importTax = price.multiply(new BigDecimal("0.05"));
        importTax = round(importTax, increment, RoundingMode.UP);
        return importTax;
    }

    /**
     * Calculates the sales tax on an item
     * using a rate of 10%.
     *
     * @param  price  the price of the item
     * @return a BigDecimal representation of the sales tax
     */
    public static BigDecimal calcSalesTax(BigDecimal price) {
        BigDecimal increment = new BigDecimal("0.05");
        BigDecimal salesTax = price.multiply(new BigDecimal("0.1"));
        salesTax = round(salesTax, increment, RoundingMode.UP);
        return salesTax;
    }

    /**
     * Rounds the value based on the increment and rounding mode.
     *
     * @param  value        the value to be rounded.
     * @param  increment    the incremental value to be rounded by
     * @param  roundingMode the direction the rounding is applied
     * @return the value rounded as a BigDecimal
     */
    public static BigDecimal round(BigDecimal value, BigDecimal increment, RoundingMode roundingMode) {
        if (increment.signum() == 0) {
            return value;
        } else {
            BigDecimal divided = value.divide(increment, 0, roundingMode);
            BigDecimal result = divided.multiply(increment).setScale(2,RoundingMode.HALF_UP);
            return result;
        }
    }


    /**
     * Parses the shopping basket input to get the item
     * quantity, item description, and the item price.
     *
     * @param  obj  the shopping basket item as a string
     * @return a String array containing the three item fields
     */
    public static String[] parseItem(String obj) {
        String[] itemInfo = new String[3];
        itemInfo[0] = obj.split(" ")[0];
        itemInfo[1] = obj.substring(itemInfo[0].length()+1, obj.lastIndexOf("at")-1);
        itemInfo[2] = obj.substring(obj.lastIndexOf("at")+3);
        itemInfo[2] = itemInfo[2].replaceAll(",", "");
        return itemInfo;
    }
}
