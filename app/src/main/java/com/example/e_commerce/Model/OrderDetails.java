package com.example.e_commerce.Model;

import java.util.ArrayList;

public class OrderDetails {
    private int order_id;
    private ArrayList<Product> products;

    public OrderDetails() {
    }

    public OrderDetails(int order_id) {
        this.order_id = order_id;
        this.products = new ArrayList<>();
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    // Method to get a list of product names
    public ArrayList<String> getProductNames() {
        ArrayList<String> productNames = new ArrayList<>();
        for (Product product : products) {
            productNames.add(product.getName());
        }
        return productNames;
    }

    // Method to get a list of product quantities
    public ArrayList<Integer> getProductQuantities() {
        ArrayList<Integer> productQuantities = new ArrayList<>();
        for (Product product : products) {
            productQuantities.add(product.getQuantity());
        }
        return productQuantities;
    }
    public ArrayList<Double> getProductPrices() {
        ArrayList<Double> productPrices = new ArrayList<>();
        for (Product product : products) {
            productPrices.add(product.getPrice());
        }
        return productPrices;
    }
}
