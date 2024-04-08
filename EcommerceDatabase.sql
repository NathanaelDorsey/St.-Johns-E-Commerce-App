-- Create the database schema
CREATE SCHEMA IF NOT EXISTS ecommercedatabase;

-- Use the newly created schema
USE ecommercedatabase;

-- Create the tables within the ecommercedatabase schema
CREATE TABLE `administrator` (
  `admin_id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) DEFAULT NULL,
  `password` VARCHAR(45) DEFAULT NULL,
  PRIMARY KEY (`admin_id`)
);

CREATE TABLE `orders` (
  `order_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `status` VARCHAR(45) DEFAULT NULL,
  `date_ordered` DATETIME DEFAULT NULL,
  `total_price` DECIMAL(10, 2) DEFAULT NULL,
  PRIMARY KEY (`order_id`)
);

CREATE TABLE `order_item` (
  `order_number` INT NOT NULL,
  `product_id` INT NOT NULL,
  `quantity` INT DEFAULT NULL,
  `subtotal` DECIMAL(10, 2) DEFAULT NULL,
  PRIMARY KEY (`order_number`)
);

CREATE TABLE `product` (
  `product_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) DEFAULT NULL,
  `description` TEXT,
  `price` DECIMAL(10, 2) DEFAULT NULL,
  `rating` FLOAT DEFAULT NULL,
  `stock_status` VARCHAR(45) DEFAULT NULL,
  PRIMARY KEY (`product_id`)
);

CREATE TABLE `product_categorization` (
  `product_id` INT NOT NULL,
  `category_id` INT NOT NULL,
  PRIMARY KEY (`product_id`, `category_id`),
  FOREIGN KEY (`product_id`) REFERENCES `product`(`product_id`),
  FOREIGN KEY (`category_id`) REFERENCES `product_category`(`category_id`)
);

CREATE TABLE `product_category` (
  `category_id` INT NOT NULL AUTO_INCREMENT,
  `category_name` VARCHAR(45) DEFAULT NULL,
  `parent_category_id` INT DEFAULT NULL,
  PRIMARY KEY (`category_id`),
  FOREIGN KEY (`parent_category_id`) REFERENCES `product_category`(`category_id`)
);

CREATE TABLE `product_review` (
  `product_review_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `product_id` INT NOT NULL,
  `rating` INT DEFAULT NULL,
  `review_text` TEXT,
  PRIMARY KEY (`product_review_id`),
  FOREIGN KEY (`user_id`) REFERENCES `user`(`user_id`),
  FOREIGN KEY (`product_id`) REFERENCES `product`(`product_id`)
);

CREATE TABLE `shipping_information` (
  `ship_id` INT NOT NULL AUTO_INCREMENT,
  `order_id` INT NOT NULL,
  `ship_method` VARCHAR(45) DEFAULT NULL,
  `ship_address` VARCHAR(255) DEFAULT NULL,
  `ship_status` VARCHAR(45) DEFAULT NULL,
  PRIMARY KEY (`ship_id`),
  FOREIGN KEY (`order_id`) REFERENCES `orders`(`order_id`)
);

CREATE TABLE `transaction` (
  `transaction_id` INT NOT NULL AUTO_INCREMENT,
  `order_id` INT NOT NULL,
  `amount` DECIMAL(10, 2) DEFAULT NULL,
  `status` VARCHAR(45) DEFAULT NULL,
  `payment_method` VARCHAR(45) DEFAULT NULL,
  PRIMARY KEY (`transaction_id`),
  FOREIGN KEY (`order_id`) REFERENCES `orders`(`order_id`)
);

CREATE TABLE `user` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) DEFAULT NULL,
  `password` VARCHAR(45) DEFAULT NULL,
  `email` VARCHAR(100) DEFAULT NULL,
  `f_name` VARCHAR(45) DEFAULT NULL,
  `l_name` VARCHAR(45) DEFAULT NULL,
  `address` VARCHAR(255) DEFAULT NULL,
  `phone` VARCHAR(45) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
);

-- Insert a new administrator with specific username and password
INSERT INTO ecommercedatabase.administrator (username, password)
VALUES ('X03607503', 'BigBoss22');
