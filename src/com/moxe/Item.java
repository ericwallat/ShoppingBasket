package com.moxe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Item Model Object
 *
 * Various attributes of shopping basket items, and related behaviors.
 *
 * Note that {@link BigDecimal} is used to model the price - not double or float.
 *
 * @author Eric Wallat
 */
public class Item {

    int quantity;
    String description;
    BigDecimal price;
    private static final String KEY = "jATPdmsRLrf1uNjTbZfE56UA5oFnFJy0kNyESr2L";

    /**
     * Constructor.
     *
     * @param quantity (required) quantity of the item
     * @param description (required) description of the item
     * @param price (required) price of the item
     */
    public Item(String quantity, String description, String price) {
        this.quantity = Integer.parseInt(quantity);
        this.description = description;
        this.price = new BigDecimal(price);
    }

    /**
     * Checks to see if the item is candy.
     *
     * @return true if item is candy, false otherwise
     * @throws Exception
     */
    public boolean isCandy() throws Exception {
        String[] desSplit = this.description.split(" ");
        String item = desSplit[desSplit.length-1].toLowerCase();
        return checkItem(item);
    }

    /**
     * Checks a USDA database to see if item falls in the sweets category.
     *
     * @return true if item is candy, false otherwise
     * @throws IOException
     */
    private boolean checkItem(String item) throws IOException {
        String urlString = "https://api.nal.usda.gov/ndb/search/?format=xml&fg=Sweets&max=25&offset=0&api_key=" + KEY
                            + "&q=" + item;
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        if (response.toString().contains("error status='400'")) {
            return false;
        } else {
            return true;
        }

    }

    /**
     * Checks to see if the item is popcorn.
     *
     * @return true if item is popcorn, false otherwise
     */
    public boolean isPopcorn() {
        if (this.description.toLowerCase().contains("popcorn")) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Checks to see if the item is coffee.
     *
     * @return true if item is coffee, false otherwise
     */
    public boolean isCoffee() {
        if (this.description.toLowerCase().contains("coffee")) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Checks to see if the item is imported.
     *
     * @return true if item is imported, false otherwise
     */
    public boolean isImported() {
        if (this.description.toLowerCase().contains("import")) {
            return true;
        }
        else {
            return false;
        }
    }
}
