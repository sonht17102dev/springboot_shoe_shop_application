CREATE DATABASE  IF NOT EXISTS `spring_shoe_store_ecommerce`;
USE `spring_shoe_store_ecommerce`;
SET foreign_key_checks = 0;
DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS `role`;
DROP TABLE IF EXISTS `brand`;
DROP TABLE IF EXISTS `category`;
DROP TABLE IF EXISTS `product`;
SET foreign_key_checks = 1;


DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(50) NOT NULL,
  `password` char(80) NOT NULL,
  `enabled` tinyint NOT NULL,  
  `address` varchar(255),
  `created_at` datetime(6),
  `date_of_birth` datetime(6),
  `gender` bit,
  `image_data` varchar(255),
  `image_path` varchar(255),
  `is_delete` bit,
  `name` varchar(255) not null,
  `phone` varchar(255),
  `updated_at` datetime(6),
   constraint UKob8kqyqqgmefl0aco34akdtpe unique (email),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;


INSERT INTO `user` (`email`, `password`, `enabled`, `address`, `created_at`, `date_of_birth`, `gender`, `image_data`, `image_path`, `is_delete`, `name`, `phone`, `updated_at`)  
VALUES   
('user1@example.com', '$2a$10$eeYhEmsvdvT5QrDvBGQA..lBz8309hSBrziv.hCM0m7zSWQ/y3Zpy', 1, '123 Main St', NOW(), '1990-01-01', b'0', NULL, NULL, b'0', 'User One', '1234567890', NOW()),  
('user2@example.com', '$2a$10$eeYhEmsvdvT5QrDvBGQA..lBz8309hSBrziv.hCM0m7zSWQ/y3Zpy', 1, '456 Elm St', NOW(), '1992-02-02', b'1', NULL, NULL, b'0', 'User Two', '0987654321', NOW()),  
('user3@example.com', '$2a$10$eeYhEmsvdvT5QrDvBGQA..lBz8309hSBrziv.hCM0m7zSWQ/y3Zpy', 1, '789 Oak St', NOW(), '1988-03-03', b'0', NULL, NULL, b'0', 'User Three', '5555555555', NOW());

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;


INSERT INTO `role` (name)
VALUES 
('ROLE_CUSTOMER'),('ROLE_ADMIN');

SET FOREIGN_KEY_CHECKS = 0;


DROP TABLE IF EXISTS `users_roles`;

CREATE TABLE `users_roles` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  
  PRIMARY KEY (`user_id`,`role_id`),
  
  KEY `FK_ROLE_idx` (`role_id`),
  
  CONSTRAINT `FK_USER_05` FOREIGN KEY (`user_id`) 
  REFERENCES `user` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION,
  
  CONSTRAINT `FK_ROLE` FOREIGN KEY (`role_id`) 
  REFERENCES `role` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

SET FOREIGN_KEY_CHECKS = 1;


CREATE TABLE brand (  
    id BIGINT PRIMARY KEY AUTO_INCREMENT,  
    created_at DATETIME(6),  
    updated_at DATETIME(6),  
    name VARCHAR(50)  
);  

CREATE TABLE category (  
    id BIGINT PRIMARY KEY AUTO_INCREMENT,  
    created_at DATETIME(6),  
    updated_at DATETIME(6),  
    name VARCHAR(50)  
);  

CREATE TABLE product (  
    id BIGINT NOT NULL AUTO_INCREMENT,  
    created_at DATETIME(6),  
    updated_at DATETIME(6),  
    description VARCHAR(500),  
    is_delete BIT(1),  
    name VARCHAR(100),  
    price BIGINT,  
    status VARCHAR(50),  
    version_name VARCHAR(50),  
    brand_id BIGINT,  
    category_id BIGINT,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS `products_categories`;
CREATE TABLE `products_categories` (
  `product_id` BIGINT NOT NULL,
  `category_id` BIGINT NOT NULL,
  
  PRIMARY KEY (`product_id`,`category_id`),
  KEY `FK_category_idx` (`category_id`),
  CONSTRAINT `FK_PRODUCT_01` FOREIGN KEY (`product_id`) 
  REFERENCES `product` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION,
  
  CONSTRAINT `FK_CATEGORY` FOREIGN KEY (`category_id`) 
  REFERENCES `category` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `products_brands`;
CREATE TABLE `products_brands` (
  `product_id` BIGINT NOT NULL,
  `brand_id` BIGINT NOT NULL,
  
  PRIMARY KEY (`product_id`,`brand_id`),
  KEY `FK_brand_idx` (`brand_id`),
  CONSTRAINT `FK_PRODUCT_02` FOREIGN KEY (`product_id`) 
  REFERENCES `product` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION,
  
  CONSTRAINT `FK_BRAND` FOREIGN KEY (`brand_id`) 
  REFERENCES `brand` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

SET FOREIGN_KEY_CHECKS = 1;
INSERT INTO `users_roles` (user_id,role_id)
VALUES 
(1, 1),
(2, 1),
(3, 2);



-- Thêm dữ liệu mẫu cho bảng brand  
INSERT INTO brand (created_at, updated_at, name) VALUES  
(NOW(), NOW(), 'Brand A'),  
(NOW(), NOW(), 'Brand B');  

-- Thêm dữ liệu mẫu cho bảng category  
INSERT INTO category (created_at, updated_at, name) VALUES  
(NOW(), NOW(), 'Category X'),  
(NOW(), NOW(), 'Category Y');  

-- Thêm dữ liệu mẫu cho bảng product  
INSERT INTO product (created_at, updated_at, description, is_delete, name, price, status, version_name, brand_id, category_id) VALUES  
(NOW(), NOW(), 'Product 1 Description', 0, 'Product 1', 100000, 'Dang ban', 'v1.0', 1, 1),  
(NOW(), NOW(), 'Product 2 Description', 0, 'Product 2', 200000, 'Dang ban', 'v1.1', 2, 2),
(NOW(), NOW(), 'Product 3 Description', 0, 'Product 3', 100000, 'Dang ban', 'v1.0', 1, 1),  
(NOW(), NOW(), 'Product 4 Description', 0, 'Product 4', 100000, 'Dang ban', 'v1.0', 1, 1),  
(NOW(), NOW(), 'Product 5 Description', 0, 'Product 5', 200000, 'Dang ban', 'v1.1', 2, 2),
(NOW(), NOW(), 'Product 6 Description', 0, 'Product 6', 200000, 'Dang ban', 'v1.1', 2, 2);    

INSERT INTO `products_categories` (product_id, category_id)
VALUES 
(1, 1), (2, 2);

INSERT INTO `products_brands` (product_id, brand_id)
VALUES 
(1, 1), (2, 2);
