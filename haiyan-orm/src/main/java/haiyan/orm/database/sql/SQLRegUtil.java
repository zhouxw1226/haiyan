package haiyan.orm.database.sql;

import haiyan.common.DebugUtil;
import haiyan.common.StringUtil;
import haiyan.common.exception.Warning;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SQLRegUtil {

	// 如果要要求表达式所匹配的内容是整个字符串，而不是从字符串中找一部分，那么可以在表达式的首尾使用 "^" 和 "$"，比如："^\d+$"
	// 要求整个字符串只有数字

	// 如果要求匹配的内容是一个完整的单词，而不会是单词的一部分，那么在表达式首尾使用 "\b"，比如：使用
	// "\b(if|while|else|void|int……)\b" 来匹配程序中的关键字。
	public static void main(String[] args) {
		String regex;
		// System.out.println("cad".matches("c((\\-*\\d*|\\s*|\\w*)*)"));
		// System.out
		// .println(" ID = 1 "
		// .matches("(\\w+\\.){0,1}\\w+\\s*(=|LIKE|IS)\\s*'?(\\w+\\.){0,1}[\\w%]+'?"));
		// ?=={0,1} +=={1,} *=={0,}
		// regex = "(\\w+\\.){0,1}\\w+\\s*(=|IN|LIKE|IS)"
		// + "(|\\s*\\()((\\s*'?(\\w+\\.)?[\\w%]+'?)(\\s*\\,)*)+(\\s*\\)){0,1}";
		regex = "(\\s*(AND|OR|WHERE)\\s*)?(\\s+|(\\w+\\.)?)(ID)\\s*(=|IN|LIKE|IS)" //
				+ "(\\s*\\()?" //
					+ "((\\s*'?(\\w+\\.)?[\\w%]+'?)(\\s*\\,)?)+" //
				+ "(\\s*\\))?"; //
		// + "(\\s*(AND|OR)\\s*)?";
		String SQL;
		SQL = "select * from (select * from XX) where  TT.ID=1 ";
		System.out.println("-");
		debug(SQL, regex);
		SQL = "select * from TT where  ID =    'd' or ID like 3 ";
		System.out.println("--");
		debug(SQL, regex);
		SQL = "select * from TT where   ID =('d') ";
		System.out.println("---");
		debug(SQL, regex);
		SQL = "select * from (select * from XX) where TT.ID in   (1) ";
		System.out.println("----");
		debug(SQL, regex);
		SQL = "select * from TT where      ID in('1','b','c') or ID= 2 or ID is null ";
		System.out.println("-----");
		debug(SQL, regex);
		// System.out.println(matcher.find());
	}

	public static void debug(String s, String regex) {
		Pattern patt = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher matcher = patt.matcher(s);
		while (matcher.find()) {
			String t = matcher.group();
			System.out.println(t);
			String regex2 = "(=|IN|LIKE|IS)" //
					+ "(\\s*\\()?" //
						+ "((\\s*'?(\\w+\\.)?[\\w%]+'?)(\\s*\\,)*)+" //
					+ "(\\s*\\))?"; //
			// + "(\\s*(AND|OR)\\s*)?";
			debug2(t, regex2);
		}
	}

	public static void debug2(String s, String regex) {
		Pattern patt = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher matcher = patt.matcher(s);
		while (matcher.find()) {
			String t = matcher.group();
			if (t.toLowerCase().indexOf("in") == 0) {

			} else if (t.toLowerCase().indexOf("=") == 0) {

			}
			System.out.println("结果:" + t);
		}
	}

	public static void match(String str, String regex) {
		// , String[] asTable
		// String regex = "(?:(?:)\\s+)";
		// regex +=
		// "(?:(?:(?:\\w+\\.)?\\w+(?:\\s+as)?(?:\\s+\\w+)?\\s*,\\s*)*)";
		// regex += "(?:\\w+\\.)?(\\w+)";
		//
		// Pattern patt = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		// Matcher matcher = patt.matcher(str);
		// //ArrayList<String> list = new ArrayList<String>();
		// while (matcher.find()) {
		// return true;
		// }
		// return false;
		throw new Warning("请使用String.match('正则表达式')判断.");
	}
	/**
	 * @param SQL
	 * @return
	 */
	public static String[] getEventTableFromSQL(String SQL) { // , String[] asTable
		DebugUtil.info(">dbm.getEventTableFromSQL().matcher(SQL)=" + SQL);
		return getTableFromSQL(SQL);
	}
	/**
	 * @param SQL
	 * @return String[]
	 */
	public static String[] getTableFromSQL(String SQL) { // , String[] asTable
		DebugUtil.info(">dbm.getTableFromSQL().matcher(SQL)=" + SQL);
		String regex = "(?:(?:from|join|update|into)\\s+)";
		regex += "(?:(?:(?:\\w+\\.)?\\w+(?:\\s+as)?(?:\\s+\\w+)?\\s*,\\s*)*)";
		regex += "(?:\\w+\\.)?(\\w+)"; // {%s}
		Pattern patt = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher matcher = patt.matcher(SQL);
		ArrayList<String> list = new ArrayList<String>();
		while (matcher.find()) {
			DebugUtil.info(">dbm.getTableFromSQL().match() \"" + matcher.group()
					+ "\" at positions " + matcher.start() + "-" + (matcher.end() - 1));
			String temp = matcher.group().toUpperCase().replaceAll("FROM", "")
					.replaceAll("JOIN", "").replaceAll("UPDATE", "")
					.replaceAll("INTO", "").replaceAll("DELETE", "")
					.replaceAll("INSERT INTO", "");
			String[] tbls = StringUtil.split(temp, ",");
			// Node node = new Node(matcher.group(), matcher.start(),
			// (matcher.end() - 1));
			// valueMap.put(matcher.group(), matcher.group());
			for (int i = 0; i < tbls.length; i++) {
				temp = StringUtil.split(tbls[i].trim(), " ")[0].toUpperCase();
				if (!list.contains(temp)) {
					list.add(temp); // 本来就是从sql取都是真实表名
					// list.add(ConfigUtil.getRealTableName(temp));
				}
			}
		}
		return (String[]) list.toArray(new String[0]);
	}
}
