package com.sonht.e_commerce_webapp_spring_boot.service.impl;

import org.springframework.stereotype.Service;

import com.sonht.e_commerce_webapp_spring_boot.entity.OrderWebDetail;
import com.sonht.e_commerce_webapp_spring_boot.repository.OrderWebDetailRepository;
import com.sonht.e_commerce_webapp_spring_boot.service.OrderDetailService;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    private final OrderWebDetailRepository orderWebDetailRepository;

    public OrderDetailServiceImpl(OrderWebDetailRepository orderWebDetailRepository) {
        this.orderWebDetailRepository = orderWebDetailRepository;
    }

    /*
     * Lưu chi tiết đơn hàng vào cơ sở dữ liệu
     */
    @Override
    public void saveOrderWebDetail(OrderWebDetail orderWebDetail) {
        orderWebDetailRepository.save(orderWebDetail);
    }

}
