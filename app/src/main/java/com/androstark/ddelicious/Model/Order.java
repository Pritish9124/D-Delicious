package com.androstark.ddelicious.Model;



public class Order
{
    private String ProductId;
    private String ProductName;
    private String Quantity;
    private String Price;
    private String ProductImage;

    public Order() {
    }

    public Order(String productId, String productName, String quantity, String price,String productImage) {
        ProductId = productId;
        ProductName = productName;
        Quantity = quantity;
        Price = price;
        ProductImage=productImage;

    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getProductImage() {
        return ProductImage;
    }

    public void setProductImage(String productImage) {
        ProductImage = productImage;
    }
}
