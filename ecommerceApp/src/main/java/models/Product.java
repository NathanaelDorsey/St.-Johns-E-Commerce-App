package models;
public class Product {
   private int productId;
   private String ImageSrc;
   private String productName;
   private String description;
   private double price;
   private double rating;
   private String stockStatus;

   public int getProductId() {
      return productId;
   }

   public void setProductId(int productId) {
      this.productId = productId;
   }
   public String getImageSrc() {
      return ImageSrc;
   }

   public void setImageSrc(String imageSrc) {
      ImageSrc = imageSrc;
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
   public String getStockStatus() {
      return stockStatus;
   }

   public void setStockStatus(String stockStatus) {
      this.stockStatus = stockStatus;
   }


}