import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MySQLGeneralLogParser {

	private static int totalLines;
	private static String startTime = "";
	private static String endTime = "";

	private static final Map<String, Integer> notDMLCounter = new LinkedHashMap<>();
	private static final Map<String, Integer> selectCounter = new LinkedHashMap<>();
	private static final Map<String, Integer> insertCounter = new LinkedHashMap<>();
	private static final Map<String, Integer> updateCounter = new LinkedHashMap<>();
	private static final Map<String, Integer> deleteCounter = new LinkedHashMap<>();

	private static final String REGEX_TIME = "^\\d{6}\\s+\\d{2}\\:\\d{2}\\:\\d{2}";
	private static final String REGEX_PREFIX = "\\s+\\d+\\s+Query\\s+";

	private static final String REGEX_NOT_DML = REGEX_PREFIX + "(set|commit|rollback|select\\s1)(.*)";
	private static final String REGEX_SELECT = REGEX_PREFIX + "(select)(.*)(from)(.*)(where)\\s+";
	private static final String REGEX_INSERT = REGEX_PREFIX + "(insert)(\\s+)(into)(\\s+)(\\w+)\\s+";
	private static final String REGEX_UPDATE = REGEX_PREFIX + "(update)(.*)(set)\\s+";
	private static final String REGEX_DELETE = REGEX_PREFIX + "(delete)(\\s+)(from)(\\s+)(\\w+)\\s+";

	private static final Pattern PATTERN_TIME = Pattern.compile(REGEX_TIME);
	private static final Pattern PATTERN_NOT_DML = Pattern.compile(REGEX_NOT_DML, Pattern.CASE_INSENSITIVE);
	private static final Pattern PATTERN_REGEX_SELECT = Pattern.compile(REGEX_SELECT, Pattern.CASE_INSENSITIVE);
	private static final Pattern PATTERN_REGEX_INSERT = Pattern.compile(REGEX_INSERT, Pattern.CASE_INSENSITIVE);
	private static final Pattern PATTERN_REGEX_UPDATE = Pattern.compile(REGEX_UPDATE, Pattern.CASE_INSENSITIVE);
	private static final Pattern PATTERN_REGEX_DELETE = Pattern.compile(REGEX_DELETE, Pattern.CASE_INSENSITIVE);

	public static void main(String[] args) {

//		Scanner scan = new Scanner(System.in);
//
//		System.out.println("请输入要解析的 MySQL/MariaDB general log 的全路径：");
//
//		if (scan.hasNextLine()) {
//			String filePath = scan.nextLine();
////            System.out.println(filePath);
//			parse(filePath);
//		}

		parse("/Users/jwz/Downloads/iZ883h0tvf9Z.log");
//		parse("/Users/jwz/Work/Collect/generallog-mysql.log");
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


			boolean timeShownUp = false;
			String line;

			while ((line = bufferedReader.readLine()) != null) {

				if(timeShownUp){
					String time = parseTime(line);
					if(time != null && !time.trim().isEmpty()) {
						endTime = time;
					}
				} else{
					String time = parseTime(line);
					if(time != null && !time.trim().isEmpty()) {
						startTime = time;
						timeShownUp = true;
					}
				}

				totalLines++;

				parseNotDML(line);
				parseSelect(line);
				parseUpdate(line);
				parseInsert(line);
				parseDelete(line);

			}
		} catch (IOException e) {
			System.err.println("Error:" + e);
		} finally {
			try {if (bufferedReader != null) bufferedReader.close();} catch (IOException e) { System.err.println("Error:" + e); }
			try {if (reader != null) reader.close();} catch (IOException e) { System.err.println("Error:" + e); }
			try {if (is != null) is.close();} catch (IOException e) { System.err.println("Error:" + e); }

			printTotalLineNum();
			printTime();
			System.out.println("----------------------not dml sql statistics----------------------");
			printSQLs(notDMLCounter);
			System.out.println("----------------------select sql statistics----------------------");
			printSQLs(selectCounter);
			System.out.println("----------------------insert sql statistics----------------------");
			printSQLs(insertCounter);
			System.out.println("----------------------update sql statistics----------------------");
			printSQLs(updateCounter);
			System.out.println("----------------------delete sql statistics----------------------");
			printSQLs(deleteCounter);
		}

	}

	private static String parseTime(String line) {

		Matcher matcher = PATTERN_TIME.matcher(line);
		while(matcher.find()){
			return matcher.group(0);
		}
		return "";
	}


	private static void printFile(String filePath) {
		System.out.println("开始解析：" + filePath);
	}


	private static void printTotalLineNum() {
		System.out.println("总行数：" + totalLines);
	}

	private static void printTime() {
		System.out.println("起始时间：" + startTime);
		System.out.println("结束时间：" + endTime);
	}


	private static void parseNotDML(String line){

		Matcher matcher = PATTERN_NOT_DML.matcher(line);
		while(matcher.find()){

			String table = matcher.group(1).trim();
			String g2 = matcher.group(2);
			if(g2 != null){
				table = table + " " + g2;
			}
			String sql = table.toLowerCase();
			if(notDMLCounter.get(sql) == null){
				notDMLCounter.put(sql, 1);
			} else {
				notDMLCounter.put(sql, notDMLCounter.get(sql) + 1);
			}
		}
	}

	private static void parseSelect(String line){

		Matcher matcher = PATTERN_REGEX_SELECT.matcher(line);
		while(matcher.find()){

			String table = matcher.group(4).trim();
			String sql = "select from " + table;
			sql = sql.toLowerCase();
			if(selectCounter.get(sql) == null){
				selectCounter.put(sql, 1);
			} else {
				selectCounter.put(sql, selectCounter.get(sql) + 1);
			}
		}
	}


	private static void parseInsert(String line){

		Matcher matcher = PATTERN_REGEX_INSERT.matcher(line);
		while(matcher.find()){

			String table = matcher.group(5).trim();
			String sql = "insert into " + table;
			sql = sql.toLowerCase();
			if(insertCounter.get(sql) == null){
				insertCounter.put(sql, 1);
			} else {
				insertCounter.put(sql, insertCounter.get(sql) + 1);
			}
		}
	}


	private static void parseUpdate(String line){

		Matcher matcher = PATTERN_REGEX_UPDATE.matcher(line);
		while(matcher.find()){

			String table = matcher.group(2).trim();
			String sql = "update " + table + " set ...";
			sql = sql.toLowerCase();
			if(updateCounter.get(sql) == null){
				updateCounter.put(sql, 1);
			} else {
				updateCounter.put(sql, updateCounter.get(sql) + 1);
			}
		}
	}


	private static void parseDelete(String line){

		Matcher matcher = PATTERN_REGEX_DELETE.matcher(line);
		while(matcher.find()){

			String table = matcher.group(5).trim();
			String sql = "delete from " + table + " where ...";
			sql = sql.toLowerCase();
			if(deleteCounter.get(sql) == null){
				deleteCounter.put(sql, 1);
			} else {
				deleteCounter.put(sql, deleteCounter.get(sql) + 1);
			}
		}
	}


	private static void printSQLs(Map<String, Integer> counter) {

		List<Map.Entry<String, Integer>> sortedEntries = counter.entrySet().stream()
				.sorted(Map.Entry.comparingByValue((o1, o2) -> o2-o1))
				.collect(Collectors.toList());

		for (Map.Entry<String, Integer> entry : sortedEntries) {
			System.out.println(entry.getValue() + " 条 \t\t" + entry.getKey());
		}
//		Iterator<String> iterator = counter.keySet().iterator();
//		while(iterator.hasNext()){
//			String sql = iterator.next();
//			System.out.println( counter.get(sql) + " 条 \t\t" + sql);
//		}
	}
}
