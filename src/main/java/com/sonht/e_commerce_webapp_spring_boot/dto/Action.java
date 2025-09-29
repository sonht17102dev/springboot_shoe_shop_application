package com.sonht.e_commerce_webapp_spring_boot.dto;


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
/*
 * Lớp Action đại diện cho một hành động với các thuộc tính như tiêu đề, tên lớp CSS và trạng thái.
 */
public class Action {
    private String title;
    private String className;
    private String status;
}
