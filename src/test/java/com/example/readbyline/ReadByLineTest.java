package com.example.readbyline;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @Author :QLC
 * @Date :2023/5/24 17:13
 */
public class ReadByLineTest {

    /**
     * 格式化roleId
     */
    @Test
    public void formatRoleId() {
        InputStream inputStream = ReadByLineTest.class.getClassLoader().getResourceAsStream("txt/guangdong.txt");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)))) {
            String temp;
            StringBuilder stringBuilder = new StringBuilder();
            while ((temp = br.readLine()) != null) {
                stringBuilder.append("'").append(temp).append("', ");
            }
            System.out.println(stringBuilder.substring(0, stringBuilder.length() - 2));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 打印手机号码
     */
    @Test
    public void printNumber() {
        List<String> list = new ArrayList<>();
        InputStream inputStream = ReadByLineTest.class.getClassLoader().getResourceAsStream("txt/sx120.txt");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)))) {
            String temp;
            while ((temp = br.readLine()) != null) {
                String[] split = temp.split("\\s+");
                for (String s : split) {
                    if (s.matches("([012356789]){11}")) {
                        list.add(s);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Collections.sort(list);
        for (String s : list) {
            System.out.println(s + " = " + s.substring(0, 3) + ", " + s.substring(7, 11));
        }
    }

    /**
     * 打印金句
     */
    @Test
    public void readByLine() {
        List<String> jinJuList = new ArrayList<>();
        InputStream inputStream = ReadByLineTest.class.getClassLoader().getResourceAsStream("txt/jinju.txt");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)))) {
            StringBuilder str = new StringBuilder();
            String temp;
            while ((temp = br.readLine()) != null) {
                if (!temp.isEmpty()) {
                    // 如果该行不为空，则继续append到str中
                    str.append("\r\n").append(temp);
                } else {
                    // 将一句金句添加到List中
                    jinJuList.add(str.toString());
                    // 重置str
                    str = new StringBuilder();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 打印金句列表
        System.out.println(jinJuList);
    }

}
