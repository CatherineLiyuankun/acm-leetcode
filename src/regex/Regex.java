package regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Catherine on 15/8/4.
 */
public class Regex {
	public static void main(String[] args) {
		matches("s");
		split("Java Hello World  Java,Hello,,World|Sun");
		replaceFist("正则表达式 Hello World,正则表达式 Hello World");
		replaceAll("正则表达式 Hello World,正则表达式 Hello World");
		appendReplacement("正则表达式 Hello World,正则表达式 bye World");
		check("ceponline@yahoo.com.cn");
	}

	//matches():匹配整个字符串
	public static void matches(String str) {
		//查找以Java开头,任意结尾的字符串
		Pattern pattern = Pattern.compile("^Java.*");
		Matcher matcher = pattern.matcher("Java不是人");
		boolean b= matcher.matches();
		//当条件满足时，将返回true，否则返回false
		System.out.println(b);
	}
	//find():匹配子字符串
	//lookingAt():永远从整个字符串的开头开始匹配

	//以多条件分割字符串时
	public static void split(String str) {
		Pattern pattern = Pattern.compile("[, |]+");
		String[] strs = pattern.split(str);
		for (int i=0;i<strs.length;i++) {
			System.out.println(strs[i]);
		}
	}

	//文字替换（首次出现字符）
	public static void replaceFist(String str) {
		Pattern pattern = Pattern.compile("正则表达式");
		Matcher matcher = pattern.matcher(str);
		System.out.println(matcher.replaceFirst("Java"));
	}

	//文字替换（全部）
	public static void replaceAll(String str) {
		Pattern pattern = Pattern.compile("正则表达式");
		Matcher matcher = pattern.matcher(str);
		System.out.println(matcher.replaceAll("Java"));
	}

	//文字替换（置换字符
	public static void appendReplacement(String str) {
		Pattern pattern = Pattern.compile("正则表达式");
		Matcher matcher = pattern.matcher(str);
		StringBuffer sbr = new StringBuffer();
		//计数奇偶数
		int i  = 0;
		while (matcher.find()) {	//find():匹配子字符串
			i++;
			if(i%2 == 0){
				matcher.appendReplacement(sbr, "java");
			}else{
				matcher.appendReplacement(sbr, "JAVA");
			}
		}
		////不加这句话，字符串 bye World将会被遗弃
		matcher.appendTail(sbr);
		System.out.println(sbr.toString());
	}

	public static void check(String str) {
		////Pattern.CASE_INSENSITIVE大小写不敏感
		//验证是否为邮箱地址
		Pattern pattern = Pattern.compile("[//w//.//-]+@([//w//-]+//.)+[//w//-]+",Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(str);
		System.out.println(matcher.matches());
		//验证邮政编码
		System.out.println(pattern.matches("[0-9]{6}", "200038"));
		System.out.println(pattern.matches("//d{6}", "200038"));
		//验证电话号码
		System.out.println(pattern.matches("[0-9]{3,4}//-?[0-9]+", "02178989799"));


		//验证身份证:判断一个字符串是不是身份证号码，即是否是15或18位数字。
		//定义判别用户身份证号的正则表达式（要么是15位，要么是18位，最后一位可以为字母）
		String idNum = "123456789009876";
		Pattern idNumPattern = Pattern.compile("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])");
		//通过Pattern获得Matcher
		Matcher idNumMatcher = idNumPattern.matcher(idNum);
		//判断用户输入是否为身份证号
		if(idNumMatcher.matches()){
			System.out.println("您的出生年月日是：");
			//如果是，定义正则表达式提取出身份证中的出生日期
			Pattern birthDatePattern= Pattern.compile("\\d{6}(\\d{4})(\\d{2})(\\d{2}).*");//身份证上的前6位以及出生年月日
			//通过Pattern获得Matcher
			Matcher birthDateMather= birthDatePattern.matcher(idNum);
			//通过Matcher获得用户的出生年月日
			if(birthDateMather.find()){
				String year = birthDateMather.group(1);
				String month = birthDateMather.group(2);
				String date = birthDateMather.group(3);
				//输出用户的出生年月日
				System.out.println(year+"年"+month+"月"+date+"日");
			}
		}else{
			//如果不是，输出信息提示用户
			System.out.println("您输入的并不是身份证号");
		}
	}
}
