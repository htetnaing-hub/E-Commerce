package com.glowmart.shop_management.common;

import com.glowmart.shop_management.dto.ProductDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

public class CommonFunction {

    public static boolean isValidName(String name){
        if (name == null || name.trim().isEmpty()){
            return false;
        }
        /*Check if it's only digits*/
        if (name.matches("\\d+")) {
            return false;
        }
        /*Check if it contains only letters and spaces (no special characters)*/
        if (!name.matches("[a-zA-Z ]+")) {
            return false;
        }
        return true;
    }

    public static boolean isValidId(String input){
        if (input == null || input.trim().isEmpty()){
            return false;
        }
        // Check if input contains only digits
        if (!input.matches("\\d+")) {
            return false;
        }
        try {
            Long id = Long.parseLong(input);
            return id > 0;
        } catch (NumberFormatException e){
            return false;
        }
    }

    public static boolean isValidPriceAndDiscount(Double originalPrice, Double discountPercentage){
        if ((originalPrice != null && originalPrice > 0)
                && (discountPercentage != null && discountPercentage >= 0 && discountPercentage <= 100)){
            return true;
        } else {
            return false;
        }
    }

    public static Double calculateDiscountAmount(Double originalPrice, Double discountPercentage){
        return originalPrice * (discountPercentage / 100);
    }

    public static String createFile(MultipartFile file, ProductDto productDto) throws IOException {
        /*Create uploads directory if not exists*/
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path uploadPath = Paths.get("uploads");
        if (Files.notExists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        /*Save file*/
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return fileName;
    }

}
