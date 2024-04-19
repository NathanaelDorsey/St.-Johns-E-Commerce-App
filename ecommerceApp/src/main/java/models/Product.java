package models;

import java.sql.Blob;

public class Product {
   public void setProductId(int productId) {
      this.productId = productId;
   }

   private int productId;
   private String productName;
   private String description;
   private Blob imageBlob;
   private double price;
   private double rating;
   private StockStatus stockStatus;

   public enum StockStatus {
      IN_STOCK("In Stock"),
      OUT_OF_STOCK("Out of Stock");

      private final String displayName;

      StockStatus(String displayName) {
         this.displayName = displayName;
      }

      public String getDisplayName() {
         return displayName;
      }
   }

   public Product(int id, String name, double price, StockStatus stockStatus, String description, Blob imageBlob, double rating) {
      this.productId = id;
      this.productName = name;
      this.price = price;
      this.stockStatus = stockStatus;
      this.description = description;
      this.imageBlob = imageBlob;  // Use Blob for image data
      this.rating = rating;
   }

   // Getters and Setters
   public int getProductId() {
      return productId;
   }

   public String getProductName() {
      return productName;
   }

   public String getDescription() {
      return description;
   }

   public Blob getImageBlob() {  // Getter for image Blob
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
}