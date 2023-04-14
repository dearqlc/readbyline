package com.example.readbyline.test;


import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * @Author QLC
 * @Date 2022/12/26 22:44
 */
public class DuplicateCompany {

    public static void main(String[] args) throws IOException {

        // 2016年的公司列表
        List<String> com1 = new ArrayList<>();
        // 2017年的公司列表
        List<String> com2 = new ArrayList<>();
        // 2019年的公司列表
        List<String> com3 = new ArrayList<>();
        // 结果表
        List<String> result = new ArrayList<>();

        InputStream inputStream = new ClassPathResource("com1.txt").getInputStream();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)))) {
            String temp;
            while ((temp = br.readLine()) != null) {
                com1.add(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        inputStream = new ClassPathResource("com2.txt").getInputStream();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)))) {
            String temp;
            while ((temp = br.readLine()) != null) {
                com2.add(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        inputStream = new ClassPathResource("com3.txt").getInputStream();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)))) {
            String temp;
            while ((temp = br.readLine()) != null) {
                com3.add(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        HashMap<String, Integer> map = new HashMap<>(256);
        // 将2016的数据放到map里，将每个公司出现的次数置为0
        com1.forEach(c -> map.put(c, 0));
        // 匹配2017的 如果有重复则次数加1
        com2.forEach(c -> {
            if (map.containsKey(c)) {
                map.put(c, map.get(c) + 1);
            }
        });
        // 匹配2019的，此时次数为1就是前两次都出现过的，直接加入结果
        com3.forEach(c -> {
            if (map.containsKey(c) && map.get(c) == 1) {
                result.add(c);
            }
        });
        System.out.println(result.size());
    }
}
