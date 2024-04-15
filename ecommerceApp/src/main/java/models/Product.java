package models;
public class Product {
   private int productId;
   private String productName;
   private String description;
   private String imageSrc;
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

   public Product(int id, String name, double price, StockStatus stockStatus, String description, String imageSrc, double rating) {
      this.productId = id;
      this.productName = name;
      this.price = price;
      this.stockStatus = stockStatus;
      this.description = description;
      this.imageSrc = imageSrc;
      this.rating = rating;
   }

   // Getters and Setters for all fields, including:

   public StockStatus getStockStatus() {
      return stockStatus;
   }

   public void setStockStatus(StockStatus stockStatus) {
      this.stockStatus = stockStatus;
   }

   public String getStockStatusDisplay() {
      return stockStatus.getDisplayName();
   }

   public int getProductId() {
      return productId;
   }

   public void setProductId(int productId) {
      this.productId = productId;
   }
   public String getImageSrc() {
      return imageSrc;
   }

   public void setImageSrc(String imageSrc) {
      this.imageSrc = imageSrc;
   }
   public String getProductName() {
      return productName;
   }

   public void setProductName(String productName) {
      this.productName = productName;
   }

   public String getDescription() {
      return description;
   }
   public void setDescription(String description) {
      this.description = description;
   }


   public double getPrice() {
      return price;
   }

   public void setPrice(double price) {
      this.price = price;
   }


   public double getRating() {
      return rating;
   }

   public void setRating(double rating) {
      this.rating = rating;
   }
   public void setStockStatus(String stockStatus) {
      this.stockStatus = StockStatus.valueOf(stockStatus);
   }


}