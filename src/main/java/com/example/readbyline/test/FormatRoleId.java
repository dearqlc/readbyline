package com.example.readbyline.test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

/**
 * @Author QLC
 * @Date 2022/12/8 10:48
 */
public class FormatRoleId {

    public static void main(String[] args) {
        InputStream inputStream = PrintNumber.class.getClassLoader().getResourceAsStream("txt/agreementNo.txt");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)))) {
            String temp;
            while ((temp = br.readLine()) != null) {
                System.out.print('\'' + temp + '\'' + ',');
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
