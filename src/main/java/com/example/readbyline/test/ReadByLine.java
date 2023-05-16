package com.example.readbyline.test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * @author QLC
 */
public class ReadByLine {

    private static final List<String> JIN_JU_LIST = new ArrayList<>();

    public static void main(String[] args) {
        InputStream inputStream = ReadByLine.class.getClassLoader().getResourceAsStream("txt/jinju.txt");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)))) {
            StringBuilder str = new StringBuilder();
            String temp;
            while ((temp = br.readLine()) != null) {
                if (!temp.isEmpty()) {
                    // 如果该行不为空，则继续append到str中
                    str.append("\r\n").append(temp);
                } else {
                    // 将一句金句添加到List中
                    JIN_JU_LIST.add(str.toString());
                    // 重置str
                    str = new StringBuilder();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 随机打印一行
        Random random = new Random();
        System.out.println(JIN_JU_LIST.get(random.nextInt(JIN_JU_LIST.size())));

        // 打印金句列表
        System.out.println(JIN_JU_LIST);
    }

}
