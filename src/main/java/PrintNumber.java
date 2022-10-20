import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @Author QLC
 * @Date 2022/9/14 10:08
 * Content:
 */
public class PrintNumber {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        InputStream inputStream = PrintNumber.class.getClassLoader().getResourceAsStream("sx120.txt");
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

}
