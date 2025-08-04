package com.sonht.e_commerce_webapp_spring_boot.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
@Table(name = "order_web")
public class OrderWeb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String consignee;

    @Column(name = "consignee_phone")
    private String consigneePhone;

    @Column(name = "delivery_address")
    private String deliveryAddress;

    @Column(name = "delivery_status") //trạng thái vận chuyển
    private String deliveryStatus;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "payment_status") //trạng thái thanh toán
    private String paymentStatus;

    @Column(name = "sent_mail")
    private Boolean sentMail;

    @Column(name = "total_amount")
    private Long totalAmount;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "orderWeb", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderWebDetail> orderWebDetails = new ArrayList<>();
}

