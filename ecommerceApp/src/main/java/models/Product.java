package models;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

public class Product {

   private int productId;
   private String productName;
   private String description;
   private Blob imageBlob;
   private double price;
   private double rating;
   private List<Category> categories = new ArrayList<>();
   private StockStatus stockStatus;

   public Product() {
   }

   public void setName(String name) {
      this.productName = name;
   }

   public void setPrice(double price) {
      this.price = price;
   }

   public void setImageBlob(Blob imageBlob) {
      this.imageBlob = imageBlob;
   }

   public void setRating(double rating) {
      this.rating = rating;
   }
   public void setStockStatus(StockStatus stockStatus) {
      this.stockStatus = stockStatus;
   }

   public enum StockStatus {
      In_Stock("In_Stock"),
      Out_of_Stock("Out_of_Stock");

      private final String displayName;

      StockStatus(String displayName) {
         this.displayName = displayName;
      }

      public String getDisplayName() {
         return displayName;
      }
   }

   public Product(int id, String name, double price, StockStatus stockStatus, String description, Blob image_data, double rating, List<Category> categories) {
      this.productId = id;
      this.productName = name;
      this.price = price;
      this.stockStatus = stockStatus;
      this.description = description;
      this.imageBlob = image_data;
      this.rating = rating;
      this.categories = categories;
   }

   // Getters
   public int getProductId() {
      return productId;
   }
   public String getProductName() {
      return productName;
   }
   public String getDescription() {
      return description;
   }
   public Blob getImageBlob() {
      return imageBlob;
   }
   public double getPrice() {
      return price;
   }
   public double getRating() {
      return rating;
   }
   public StockStatus getStockStatus() {
      return stockStatus;
   }
   public List<Category> getCategories() {
      return categories;
   }
   public void setCategories(List<Category> categories) {
      this.categories = categories;
   }
}
