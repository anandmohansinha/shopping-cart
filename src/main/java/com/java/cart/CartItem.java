package com.java.cart;

public class CartItem {
    private final Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public void addQuantity(int qty){
        this.quantity = quantity+qty;
    }

    public double getTotalPrice(){
        return product.getPrice()*quantity;
    }

    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }
}
