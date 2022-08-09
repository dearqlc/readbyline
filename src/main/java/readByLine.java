import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class readByLine {

    private static final List<String> jinJuList = new ArrayList<>();

    public static void main(String[] args) {
        // 读取resources下的jinju.txt
        InputStream inputStream = readByLine.class.getClassLoader().getResourceAsStream("jinju.txt");
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

        // 随机打印一行
        Random random = new Random();
        System.out.println(jinJuList.get(random.nextInt(jinJuList.size())));

        // 打印金句列表
        System.out.println(jinJuList);
    }

}
