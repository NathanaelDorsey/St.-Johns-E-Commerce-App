CREATE TABLE `administrator` (
  `admin_id` int NOT NULL,
  `username` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`admin_id`)
  
  CREATE TABLE `order` (
  `order_id` int NOT NULL,
  `user_id` varchar(45) DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  `date_ordered` datetime DEFAULT NULL,
  `total_price` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`order_id`)
  
  CREATE TABLE `order_item` (
  `order_number` int NOT NULL,
  `product_number` varchar(45) DEFAULT NULL,
  `quantity` varchar(45) DEFAULT NULL,
  `subtotal` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`order_number`)
  
  CREATE TABLE `product` (
  `product_id` int NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `description` varchar(45) DEFAULT NULL,
  `price` varchar(45) DEFAULT NULL,
  `rating` varchar(45) DEFAULT NULL,
  `stock_status` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`product_id`)
  
  CREATE TABLE `product_categorization` (
  `product_id` int NOT NULL,
  `category_id` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`product_id`)
  
  CREATE TABLE `product_category` (
  `category_id` int NOT NULL,
  `category_name` varchar(45) DEFAULT NULL,
  `parent_category_id` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`category_id`)
  
  CREATE TABLE `product_review` (
  `product_review_id` int NOT NULL,
  `user_id` varchar(45) DEFAULT NULL,
  `proudct_id` varchar(45) DEFAULT NULL,
  `rating` varchar(45) DEFAULT NULL,
  `review_text` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`product_review_id`)
  
  CREATE TABLE `shipping_information` (
  `ship_id` int NOT NULL,
  `order_id` varchar(45) DEFAULT NULL,
  `ship_method` varchar(45) DEFAULT NULL,
  `ship_address` varchar(45) DEFAULT NULL,
  `ship_status` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ship_id`)
  
  CREATE TABLE `transaction` (
  `transaction_id` int NOT NULL,
  `order_id` varchar(45) DEFAULT NULL,
  `amount` varchar(45) DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  `payment_method` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`transaction_id`)
  
  CREATE TABLE `user` (
  `user_id` int NOT NULL,
  `username` varchar(15) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `f_name` varchar(45) DEFAULT NULL,
  `l_name` varchar(45) DEFAULT NULL,
  `address` varchar(45) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`user_id`)