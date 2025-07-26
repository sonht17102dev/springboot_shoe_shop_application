package com.sonht.e_commerce_webapp_spring_boot.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;

@Service
public class UploadService {

    private final ServletContext servletContext;

    public UploadService(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
    public String uploadFile( MultipartFile file) {
		// Lấy tên file gốc
		String fileName = file.getOriginalFilename();
		// Tạo chuỗi đường dẫn file
		String filePath = this.servletContext.getRealPath("/") + "resources"  
                + File.separator + "static" 
                + File.separator + "admin" 
                + File.separator + "assets" 
				+ File.separator + "images"
				+ File.separator + fileName;
		
		try {
			File newFile = new File(filePath);
			FileOutputStream outputStream = new FileOutputStream(newFile);
			outputStream.write(file.getBytes());
			outputStream.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
        return fileName;
	}
    public String handleSaveUploadFile(MultipartFile file, String targetFolder) {
        // Check if file is empty
        if (file.isEmpty()) 
            return "";

        String rootPath = this.servletContext.getRealPath("/resources/static/admin/assets/images");
        String finalName = "";
        try{

            byte[] bytes = file.getBytes();
            
            File dir = new File(rootPath + File.separator + targetFolder);
            if (!dir.exists())
            dir.mkdirs();
            

            // Create the file on server
            finalName = System.currentTimeMillis() + "-" + file.getOriginalFilename();
            File serverFile = new File(dir.getAbsolutePath() + File.separator + finalName);
            
            BufferedOutputStream stream = new BufferedOutputStream(
                new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return finalName;
    }
}
