package com.sonht.e_commerce_webapp_spring_boot.dto;

import java.util.Date;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductDto {

    private Long id;
    @NotNull
    @NotEmpty(message = "Tên sản phẩm không được để trống")
    private String name;
    
    @NotNull
    @NotEmpty(message = "Tên phiên bản không được để trống")
    private String versionName;

    @NotNull
    @NotEmpty(message = "Mô tả chi tiết không được để trống")
    private String description;

    @NotNull(message = "Giá không được để trống")
    @DecimalMin(value = "0", inclusive = false, message = "Price must be greater than 0")
    private double price;

    private Boolean isDelete;
    @NotNull(message = "Trạng thái không được để trống")
    private String status; 
    private Date updatedAt;
    private Date createdAt;
    
}
