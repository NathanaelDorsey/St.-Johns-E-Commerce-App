package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCart {
    private static class ShoppingCartHolder {
        private static final ShoppingCart INSTANCE = new ShoppingCart();
    }

    public static ShoppingCart getInstance() {
        return ShoppingCartHolder.INSTANCE;
    }

    private Map<Product, Integer> cartItems;
    private double totalCartAmount;

    public ShoppingCart() {
        cartItems = new HashMap<>();
        totalCartAmount = 0.0;
    }

    public void addToCart(Product product, int quantityToAdd) {
        if (product == null || quantityToAdd <= 0) {
            System.out.println("Invalid product or quantity");
            return;
        }
        int currentQuantity = cartItems.getOrDefault(product, 0);
        cartItems.put(product, currentQuantity + quantityToAdd);
        totalCartAmount += product.getPrice() * quantityToAdd;
        System.out.println("Added " + quantityToAdd + " units of " + product.getProductName() + " to cart.");
    }

    public void removeFromCart(Product product, int quantityToRemove) {
        Integer currentQuantity = cartItems.get(product);
        if (currentQuantity != null && currentQuantity > 0) {
            if (currentQuantity > 1) {
                cartItems.put(product, currentQuantity - quantityToRemove);
            } else {
                cartItems.remove(product);
            }
            totalCartAmount -= product.getPrice();
        }
    }

    public int getAmountInCart(Product product) {
        return cartItems.getOrDefault(product, 0);
    }

    public void clearCart() {
        cartItems.clear();
        totalCartAmount = 0.0;
    }

    public List<Product> getCartItems() {
        return new ArrayList<>(cartItems.keySet());
    }

    public double getTotalCartAmount() {
        return totalCartAmount;
    }
}