package com.example.sendwich.function;
//string의 null여부 검사
public class boolid {
    public static boolean isNull(String str){
        String name = str;
        boolean returnValue = true;

        if(name == null || name.equals("") || name.length() == 0){
            returnValue = false;
        }
        return returnValue;
    }
}
