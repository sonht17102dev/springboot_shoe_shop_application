package com.sonht.e_commerce_webapp_spring_boot.entity;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "cart_item")
public class CartItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer quantity;

    // Mỗi cart item gắn với 1 product size
    @ManyToOne
    @JoinColumn(name = "product_size_id")
    private ProductSize productSize;

    // Mỗi cart item thuộc về 1 customer
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public String formatPrice(double price) {
        return String.format("%,.0f", price) + " đ"; // Định dạng giá với dấu phẩy và thêm đơn vị tiền tệ
    }
}
