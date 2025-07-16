package com.glowmart.shop_management.common;

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

}
