CREATE DATABASE  IF NOT EXISTS `spring_shoe_store_ecommerce` ;
USE `spring_shoe_store_ecommerce`;
SET FOREIGN_KEY_CHECKS=0; -- to disable them
ALTER DATABASE spring_shoe_store_ecommerce CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;


DROP TABLE IF EXISTS `order_web`;
CREATE TABLE order_web (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    created_at DATETIME(6),
    updated_at DATETIME(6),
    consignee VARCHAR(100),
    consignee_phone VARCHAR(20),
    delivery_address VARCHAR(200),
    delivery_status VARCHAR(50),
    payment_method VARCHAR(50),
    payment_status VARCHAR(50),
    sent_mail BIT(1),
    total_amount BIGINT,
    customer_id INT
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;  

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
    category_id bigint,
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
   -- product_id BIGINT,     -- Khóa ngoại đến bảng 'product'
    name VARCHAR(50)  
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;  


DROP TABLE IF EXISTS `category`;
CREATE TABLE category (  
    id BIGINT PRIMARY KEY AUTO_INCREMENT,  
    created_at DATETIME(6),  
    updated_at DATETIME(6),  
    product_id BIGINT,     -- Khóa ngoại đến bảng 'product'
    name VARCHAR(50)  ,
    FOREIGN KEY (product_id) REFERENCES product(id)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;  

DROP TABLE IF EXISTS `color`;
CREATE TABLE color (  
    id BIGINT PRIMARY KEY AUTO_INCREMENT,  
    created_at DATETIME(6),  
    updated_at DATETIME(6),  
    code VARCHAR(50),
    name VARCHAR(50)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;  

DROP TABLE IF EXISTS `product_color`;
CREATE TABLE product_color (  
    id BIGINT PRIMARY KEY AUTO_INCREMENT,  
    created_at DATETIME(6),  
    updated_at DATETIME(6),  
    color_id BIGINT,
    product_id BIGINT,
    CONSTRAINT fk_product_color_color FOREIGN KEY (color_id) REFERENCES color(id),
    CONSTRAINT fk_product_color_product FOREIGN KEY (product_id) REFERENCES product(id)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;  

-- DROP TABLE IF EXISTS `user_wishlist`;
-- CREATE TABLE user_wishlist (
--     id BIGINT PRIMARY KEY AUTO_INCREMENT,
--     created_at DATETIME(6),
--     updated_at DATETIME(6),
--     product_id BIGINT,
--     customer_id BIGINT
-- )ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;  

DROP TABLE IF EXISTS `product_size`;
CREATE TABLE product_size (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
	created_at DATETIME(6),
    updated_at DATETIME(6),
    size int NOT NULL,
    quantity int NOT NULL,
    product_id BIGINT NOT NULL,
    order_web_detail_id BIGINT, 
    CONSTRAINT fk_orderwebdetail FOREIGN KEY (order_web_detail_id) REFERENCES order_web_detail(id),
    CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES product(id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4; 

DROP TABLE IF EXISTS `cart_item`;
CREATE TABLE cart_item (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    created_at DATETIME(6),
    updated_at DATETIME(6),
    quantity INT,
    product_size_id BIGINT,
    user_id BIGINT,
    CONSTRAINT fk_cartitem_productsize FOREIGN KEY (product_size_id)
        REFERENCES product_size(id)
);
DROP TABLE IF EXISTS `order_web_detail`;
CREATE TABLE order_web_detail (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    created_at DATETIME(6),
    updated_at DATETIME(6),
    price BIGINT,
    quantity INT,
    total_amount BIGINT,
     order_web_id BIGINT,
    product_size_id BIGINT NOT NULL,
	CONSTRAINT fk_product_size FOREIGN KEY (product_size_id) REFERENCES product_size(id)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;  




-- Thêm dữ liệu mẫu cho bảng brand  
INSERT INTO brand (created_at, updated_at, name) VALUES  
(NOW(), NOW(), 'NIKE'),  
(NOW(), NOW(), 'ADIDAS'),
(NOW(), NOW(), 'PUMA'),  
(NOW(), NOW(), 'VANS'),
(NOW(), NOW(), 'CONVERSE'),  
(NOW(), NOW(), 'NEW BALANCE');  

-- Thêm dữ liệu mẫu cho bảng category  
INSERT INTO category (created_at, updated_at, name, product_id) VALUES  
(NOW(), NOW(), 'Cổ thấp / low top', 1),  
(NOW(), NOW(), 'Cổ trung / mid top',2),
(NOW(), NOW(), 'Cổ cao / High top', 2),  
(NOW(), NOW(), 'Slide', 3),
(NOW(), NOW(), 'Phụ kiện', 4);  


-- Thêm dữ liệu mẫu cho bảng product  
INSERT INTO product (created_at, updated_at, description, is_delete, name, price, status, version_name, brand_id,  category_id) VALUES  
(NOW(), NOW(), 'Nike Zoom REP Description', 0, 'Zoom', 500000, 'Đang bán', 'A182 REP', 1, 1),  
(NOW(), NOW(), 'Nike Zoom REP Description', 0, 'Zoom', 500000, 'Đang bán', 'A182 REP', 1, 1), 
(NOW(), NOW(), 'Nike Jordan 1 Description', 0, 'Nike Jordan 1', 2000000, 'Đang bán', 'v1.1', 1,  3),
(NOW(), NOW(), 'Adidas Alphabounce Instinct M Description', 0, 'Alphabounce Instinct', 700000, 'Đang bán', 'M', 2,  2),
(NOW(), NOW(), 'Adidas Alphabounce Instinct M Description', 0, 'Alphabounce Instinct', 700000, 'Đang bán', 'M', 2,  2),  
(NOW(), NOW(), 'Adidas A170 Tím REP Description', 0, 'Adidas A170', 500000, 'Đang bán', 'REP', 2,  2),
(NOW(), NOW(), 'Giày Puma Nitro Trắng Đen REP 1:1 Description', 0, 'Puma Nitro', 500000, 'Đang bán', 'REP 1:1', 3,  2),  
(NOW(), NOW(), 'Vans Classic Đen Trắng REP 1:1 Description', 0, 'Classic Đen Trắng', 1200000, 'Đang bán', 'REP 1:1', 4,  1);

-- Thêm dữ liệu mẫu
INSERT INTO color (created_at, updated_at, code, name) VALUES
(NOW(), NOW(), '#000000', 'Đen'),
(NOW(), NOW(), '#008000', 'Xanh rêu'),
(NOW(), NOW(), '#808080', 'Đen Trắng'),
(NOW(), NOW(), '#00FFFF', 'Xám xanh ngọc'),
(NOW(), NOW(), 'white', 'Trắng'),
(NOW(), NOW(), 'yellow', 'Vàng'),
(NOW(), NOW(), 'orange', 'Cam'),
(NOW(), NOW(), 'purple', 'Tím'),
(NOW(), NOW(), 'pink', 'Hồng'),
(NOW(), NOW(), 'brown', 'Nâu');

-- Thêm dữ liệu mẫu

INSERT INTO product_color (created_at, updated_at, color_id, product_id) VALUES
(NOW(), NOW(), 1, 1), 
(NOW(), NOW(), 2, 2), 
(NOW(), NOW(), 1, 3),
(NOW(), NOW(), 1, 4),
(NOW(), NOW(), 4, 5),
(NOW(), NOW(), 9, 6),
(NOW(), NOW(), 5, 7),
(NOW(), NOW(), 1, 8);
INSERT INTO product_image (created_at, updated_at, is_primary, image_url, product_id) VALUES
(NOW(6), NOW(6), b'1', 'Nike Zoom A182.jpg', 1),
(NOW(6), NOW(6), b'1', 'Nike Zoom A182 Xanh Rêu REP.jpg', 2),
(NOW(6), NOW(6), b'1', 'Adidas Alphabounce Instinct M.jpg', 4),
(NOW(6), NOW(6), b'1', 'Adidas Alphabounce Instinct Xám Xanh Ngọc M.jpg', 5),
(NOW(6), NOW(6), b'1', 'Adidas A170 Tím REP.jpg', 6),
(NOW(6), NOW(6), b'1', 'Giày Puma Nitro Trắng Đen REP.jpg', 7),
(NOW(6), NOW(6), b'1', 'Nike Jordan 1.jpg', 3),
(NOW(6), NOW(6), b'1', 'Vans Classic Đen Trắng REP.jpg', 8);

INSERT INTO order_web (created_at, updated_at, consignee, consignee_phone, delivery_address, delivery_status, payment_method, payment_status, sent_mail, total_amount, customer_id)
VALUES 
(NOW(), NOW(), 'Nguyen Van A', '0912345678', '123 Le Loi, HCMC', 'successful', 'COD', 'Đã thanh toán', 0, 1500000, 1),
(NOW(), NOW(), 'Tran Thi B', '0987654321', '456 Tran Hung Dao, HN', 'successful', 'ATM', 'Đã thanh toán', 1, 2200000, 2),
(NOW(), NOW(), 'Le Van C', '0909009009', '789 Hai Ba Trung, HCMC', 'delivery2', 'ATM', 'Chưa thanh toán', 1, 1000000, 1),
(NOW(), NOW(), 'Pham Thi D', '0922334455', '321 Nguyen Trai, HN', 'cancel', 'COD', 'Đã hủy bỏ', 0, 500000, 3),
(NOW(), NOW(), 'Do Van E', '0933221122', '147 Le Thanh Ton, HCMC', 'wait', 'COD', 'Chưa thanh toán', 0, 1750000, 2),
(NOW(), NOW(), 'Truong Van F', '0933221444', '79 Au Co, HCMC', 'unprocessed', 'ATM', 'Chưa thanh toán', 0, 1750000, 1);

INSERT INTO `user` (`email`, `password`, `enabled`, `address`, `created_at`, `date_of_birth`, `gender`, `image_data`, `image_path`, `is_delete`, `name`, `phone`, `updated_at`)  
VALUES   
('user1@example.com', '$2a$10$eeYhEmsvdvT5QrDvBGQA..lBz8309hSBrziv.hCM0m7zSWQ/y3Zpy', 1, '123 Main St', NOW(), '1990-01-01', b'0', NULL, NULL, b'0', 'User One', '1234567890', NOW()),  
('user2@example.com', '$2a$10$eeYhEmsvdvT5QrDvBGQA..lBz8309hSBrziv.hCM0m7zSWQ/y3Zpy', 1, '456 Elm St', NOW(), '1992-02-02', b'1', NULL, NULL, b'0', 'User Two', '0987654321', NOW()),  
('user3@example.com', '$2a$10$eeYhEmsvdvT5QrDvBGQA..lBz8309hSBrziv.hCM0m7zSWQ/y3Zpy', 1, '789 Oak St', NOW(), '1988-03-03', b'0', NULL, NULL, b'0', 'User Three', '5555555555', NOW());

SET FOREIGN_KEY_CHECKS=1; -- to re-enable them
INSERT INTO `users_roles` (user_id,role_id)
VALUES 
(1, 1),
(2, 1),
(3, 2);


-- INSERT INTO user_wishlist (created_at, updated_at, product_id, customer_id)
-- VALUES 
-- (NOW(), NOW(), 101, 1),
-- (NOW(), NOW(), 102, 2),
-- (NOW(), NOW(), 103, 1),
-- (NOW(), NOW(), 104, 3),
-- (NOW(), NOW(), 105, 2);
INSERT INTO product_size (created_at, updated_at, quantity, size, product_id) VALUES
(NOW(), NOW(), 43, 41, 1),
(NOW(), NOW(), 42, 42, 1),
(NOW(), NOW(), 44, 43, 1),
(NOW(), NOW(), 20, 41, 2),
(NOW(), NOW(), 10, 42, 2),
(NOW(), NOW(), 10, 43, 2),
(NOW(), NOW(), 20, 41, 3),
(NOW(), NOW(), 20, 42, 3),
(NOW(), NOW(), 20, 43, 3),
(NOW(), NOW(), 10, 42, 4),
(NOW(), NOW(), 20, 41, 5),
(NOW(), NOW(), 10, 42, 6),
(NOW(), NOW(), 20, 41, 7),
(NOW(), NOW(), 20, 41, 8);

INSERT INTO order_web_detail (created_at, updated_at, price, quantity, total_amount, order_web_id, product_size_id)
VALUES 
(NOW(), NOW(), 300000, 2, 600000, 1,1),
(NOW(), NOW(), 500000, 1, 500000, 2,2),
(NOW(), NOW(), 700000, 1, 700000, 3,3),
(NOW(), NOW(), 250000, 2, 500000, 2,2),
(NOW(), NOW(), 350000, 3, 1050000, 1,1),
(NOW(), NOW(), 350000, 1, 350000, 6,4);

 
