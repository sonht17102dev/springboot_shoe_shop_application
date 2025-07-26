CREATE DATABASE  IF NOT EXISTS `spring_shoe_store_ecommerce`;
USE `spring_shoe_store_ecommerce`;
SET FOREIGN_KEY_CHECKS=0; -- to disable them
ALTER DATABASE spring_shoe_store_ecommerce CHARACTER SET 'utf8mb4';




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
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

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
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;


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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;




DROP TABLE IF EXISTS `product`;
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
    product_image_id bigint,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `product_image`;
-- Bảng product_image
CREATE TABLE product_image (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    created_at DATETIME(6),
    updated_at DATETIME(6),
    is_primary BIT(1),
    image_url VARCHAR(500),
    product_id BIGINT,     -- Khóa ngoại đến bảng 'product'
    FOREIGN KEY (product_id) REFERENCES product(id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `brand`;
CREATE TABLE brand (  
    id BIGINT PRIMARY KEY AUTO_INCREMENT,  
    created_at DATETIME(6),  
    updated_at DATETIME(6),  
    product_id BIGINT,     -- Khóa ngoại đến bảng 'product'
    name VARCHAR(50)  ,
    FOREIGN KEY (product_id) REFERENCES product(id)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;  


SET FOREIGN_KEY_CHECKS=1; -- to re-enable them
INSERT INTO `users_roles` (user_id,role_id)
VALUES 
(1, 1),
(2, 1),
(3, 2);



-- Thêm dữ liệu mẫu cho bảng brand  
INSERT INTO brand (created_at, updated_at, name) VALUES  
(NOW(), NOW(), 'Brand A'),  
(NOW(), NOW(), 'Brand B'),
(NOW(), NOW(), 'Brand C'),  
(NOW(), NOW(), 'Brand D');  



-- Thêm dữ liệu mẫu cho bảng product  
INSERT INTO product (created_at, updated_at, description, is_delete, name, price, status, version_name, brand_id) VALUES  
(NOW(), NOW(), 'Product 1 Description', 0, 'Product 1', 100000, 'Dang ban', 'v1.0', 1),  
(NOW(), NOW(), 'Product 2 Description', 0, 'Product 2', 200000, 'Ngung ban', 'v1.1', 2),
(NOW(), NOW(), 'Product 3 Description', 0, 'Product 3', 300000, 'Dang ban', 'v1.0', 3),  
(NOW(), NOW(), 'Product 4 Description', 0, 'Product 4', 400000, 'Dang ban', 'v1.1', 3),
(NOW(), NOW(), 'Product 5 Description', 0, 'Product 5', 500000, 'Dang ban', 'v1.0', 4),  
(NOW(), NOW(), 'Product 6 Description', 0, 'Product 6', 600000, 'Dang ban', 'v1.1', 2);

