package com.sonht.e_commerce_webapp_spring_boot.dto;

import java.util.List;

import com.sonht.e_commerce_webapp_spring_boot.entity.OrderWeb;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DataOrderDetailResponse {
    private OrderWeb orderWeb;

    private List<Action> actions;
    private boolean hasCancelled;
}
