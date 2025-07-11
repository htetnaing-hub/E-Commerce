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

}
