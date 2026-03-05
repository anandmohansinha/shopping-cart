package com.java;

import com.java.cart.ShoppingCart;

public class ShoppingCartApplication {
    public static void main(String[] args) throws Exception {
        ShoppingCart cart = new ShoppingCart();

        cart.addProduct("cornflakes", 1);
        cart.addProduct("cornflakes", 1);
        cart.addProduct("weetabix", 1);

        System.out.println("Subtotal = " + cart.getSubtotal());
        System.out.println("Tax = " + cart.getTax());
        System.out.println("Total = " + cart.getTotal());
    }
}
