package com.sonht.e_commerce_webapp_spring_boot.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    
    private String username;

    @NotNull
    @NotEmpty(message = "Bình luận không được để trống")
    private String commentMessage;

    @NotNull(message = "Bạn phải chọn số sao đánh giá")
    @Min(value = 1, message = "Giá trị tối thiểu là 1")
    @Max(value = 5, message = "Giá trị tối đa là 5")
    private Integer rate;
    
    private Long productId;
}
