package com.sonht.e_commerce_webapp_spring_boot.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;

@Configuration
public class WebConfig  {

    /**
     * Cấu hình bộ lọc mã hóa ký tự để đảm bảo rằng tất cả các yêu cầu và phản hồi sử dụng mã hóa UTF-8.
     * @return
     */

    @Bean
	public CharacterEncodingFilter characterEncodingFilter() {
		CharacterEncodingFilter filter = new CharacterEncodingFilter();
		filter.setEncoding("UTF-8");
		filter.setForceEncoding(true);
		return filter;
	}


   
}
