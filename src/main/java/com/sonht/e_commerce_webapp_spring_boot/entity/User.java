package com.sonht.e_commerce_webapp_spring_boot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private boolean enabled;
    @Column(length = 255)  
    private String address;  

    @Column(name = "created_at", columnDefinition = "datetime(6)")  
    private Date createdAt;  

    @Column(name = "date_of_birth", columnDefinition = "datetime(6)")  
    private Date dateOfBirth;  

    @Column  
    private Boolean gender; // Sử dụng Boolean để ánh xạ kiểu bit  

    @Column(name = "image_data", length = 255)  
    private String imageData;  

    @Column(name = "image_path", length = 255)  
    private String imagePath;  

    @Column(name = "is_delete")  
    private Boolean isDelete; // Sử dụng Boolean để ánh xạ kiểu bit  

    @Column(nullable = false, length = 255)  
    private String name;  

    @Column(length = 255)  
    private String phone;  

    @Column(name = "updated_at", columnDefinition = "datetime(6)")  
    private Date updatedAt;  

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles = new ArrayList<>();


}