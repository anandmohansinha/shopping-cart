package com.java.cart;

import com.java.client.PriceApi;
import com.java.client.PriceApiClient;
import com.java.config.ConfigLoader;
import com.java.config.ConfigTaxProvider;
import com.java.config.TaxProvider;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class ShoppingCart {

    private final Map<String, CartItem> items = new HashMap<>();
    private final PriceApi priceApi;
    private final TaxProvider taxProvider;


    public ShoppingCart(PriceApi priceApi, TaxProvider taxProvider) {
        this.priceApi = priceApi;
        this.taxProvider = taxProvider;
    }
    public ShoppingCart() {
        this(new PriceApiClient(), new ConfigTaxProvider());
    }
    public void addProduct(String productName, int quantity) throws Exception {
        if (productName == null || productName.isBlank()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        CartItem existingItem = items.get(productName);

        if (existingItem == null) {
            double price = priceApi.getPrice(productName);
            Product product = new Product(productName, price);
            items.put(productName, new CartItem(product, quantity));
        } else {
            existingItem.addQuantity(quantity);
        }
    }
    public double getSubtotal() {
        return round(items.values().stream()
                .mapToDouble(CartItem::getTotalPrice)
                .sum());
    }
    private double round(double value) {
        return BigDecimal.valueOf(value)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
    public Map<String, CartItem> getItems() {
        return Collections.unmodifiableMap(items);
    }

    public double getTotal() {
        return round(getSubtotal() + getTax());
    }
    public double getTax() {
        return round(getSubtotal() * taxProvider.getTaxRate());
    }

}
