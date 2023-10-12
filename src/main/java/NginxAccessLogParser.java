import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NginxAccessLogParser {

    private static final Map<String, LinkedHashMap<String, Integer>> hourAndRequests = new LinkedHashMap<>();

    private static int totalLines;

    private static final String REGEX_TIME = "";
    private static final String REGEX_REQUEST = "(\\d{2})\\:(\\d{2})\\:(\\d{2})\\s+(.*)\\s*(GET|POST|DELETE|PUT|PATCH)\\s(.*)\\s(HTTP/.*)\\s";

    private static final Pattern PATTERN_REQUEST = Pattern.compile(REGEX_REQUEST, Pattern.CASE_INSENSITIVE);

    public static void main(String[] args) {

//        Scanner scan = new Scanner(System.in);
//
//        System.out.println("请输入要解析的 nginx 日志的全路径：");
//
//        if (scan.hasNextLine()) {
//            String filePath = scan.nextLine();
//            System.out.println(filePath);
            parse("/Users/jwz/Desktop/nginxiip.txt");
//        }
    }


    private static void parse(String filePath) {

        printFile(filePath);

        InputStream is = null;
        Reader reader = null;
        BufferedReader bufferedReader = null;

        try {

            is = new FileInputStream(new File(filePath));
            reader = new InputStreamReader(is, "utf-8");
            bufferedReader = new BufferedReader(reader);

            String line;

            while ((line = bufferedReader.readLine()) != null) {

                totalLines++;

                parseRequests(line);

            }
        } catch (IOException e) {
            System.err.println("Error:" + e);
        } finally {
            try {
                if (bufferedReader != null) bufferedReader.close();
            } catch (IOException e) {
                System.err.println("Error:" + e);
            }
            try {
                if (reader != null) reader.close();
            } catch (IOException e) {
                System.err.println("Error:" + e);
            }
            try {
                if (is != null) is.close();
            } catch (IOException e) {
                System.err.println("Error:" + e);
            }

            printTotalLineNum();
            printRequests();
        }

    }


    private static void printFile(String filePath) {
        System.out.println("开始解析：" + filePath);
    }


    private static void printTotalLineNum() {
        System.out.println("总行数：" + totalLines);
    }


    private static void parseRequests(String line) {

        JSONObject jsonObject = JSON.parseObject(line);

        String key = jsonObject.getString("request");

        String hour = "1";
        if (hourAndRequests.get(hour) == null) {
            hourAndRequests.put(hour, new LinkedHashMap<>());
        }
        if (hourAndRequests.get(hour).get(key) == null) {
            hourAndRequests.get(hour).put(key, 1);
        } else {
            hourAndRequests.get(hour).put(key, hourAndRequests.get(hour).get(key) + 1);
        }

    }

    private static void printRequests() {

        Iterator<String> iterator = hourAndRequests.keySet().iterator();
        while (iterator.hasNext()) {
            String hour = iterator.next();
            Map<String, Integer> requests = hourAndRequests.get(hour);
            System.out.println("========== " + hour + " 时 ==========");
            Iterator<String> requestsIterator = requests.keySet().iterator();
            while (requestsIterator.hasNext()) {
                String uri = requestsIterator.next();
                if (requests.get(uri) < 10) {
                    continue;
                }
                System.out.println(requests.get(uri) + "次\t\t\t" + uri);
            }
            System.out.println("低于60次\t\t\t...");
        }
    }
}
