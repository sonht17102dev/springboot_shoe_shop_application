package com.sonht.e_commerce_webapp_spring_boot.dto;


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
/*
 * Lớp ProductDto đại diện cho dữ liệu sản phẩm với các thuộc tính như ID, tên, phiên bản, mô tả, giá, trạng thái xóa,
 * trạng thái, tên thương hiệu, danh mục và URL hình ảnh.
 * 
 */
public class ProductDto {

    private Long id;
    @NotNull
    @NotEmpty(message = "Tên sản phẩm không được để trống")
    private String name;
    
    @NotNull
    @NotEmpty(message = "Phiên bản sản phẩm không được để trống")
    private String versionName;

    @NotNull
    @NotEmpty(message = "Mô tả chi tiết không được để trống")
    private String description;

    @NotNull(message = "Giá không được để trống")
    @DecimalMin(value = "0", inclusive = false, message = "Giá phải lớn hơn 0")
    private Long price;

    private Boolean isDelete;
    @NotNull(message = "Vui lòng chọn trạng thái")
    private String status; 
    
    @NotNull(message = "Vui lòng chọn thương hiệu")
    private String brandName;

    @NotNull(message = "Vui lòng chọn danh mục")
    private String category;

    private String imageUrl;

    /*
     * Phương thức để lấy URL hình ảnh chính của sản phẩm. Nếu không có URL hình ảnh, trả về URL mặc định.
     */
    public String primaryImage() {
        return imageUrl != null && !imageUrl.isEmpty() ? imageUrl : "https://via.placeholder.com/150";
    }
    
}
