package com.ideamov.run;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import com.ideamov.dto.ClassPathMessage;
import com.ideamov.main.InitUtils;
import com.ideamov.main.SaxHandler;
import com.ideamov.utils.FileClassUtil;

public class GeneratorCoreMap {

	public static Map<String, String> MAP = new HashMap<String, String>();

	public static List<String> KEY = new ArrayList<String>();

	public static List<String> VALUE = new ArrayList<String>();

	public void generator() throws Exception {

		SAXParserFactory factory = SAXParserFactory.newInstance();

		SAXParser parser = factory.newSAXParser();

		parser.parse(new File("src/main/java/config.xml"), new SaxHandler());

		if (KEY.size() == VALUE.size()) {
			for (int i = 0; i < KEY.size(); i++) {
				MAP.put(KEY.get(i), VALUE.get(i).replace(".", "/") + "/");
			}
		} else {
			new RuntimeException("xml解析异常");
		}

	}

	public static void main(String[] args) throws Exception {

		GeneratorCoreMap generatorSqlmap = new GeneratorCoreMap();

		generatorSqlmap.generator();

		/**
		 * 扫描指定包名目录
		 */
		String packageUrl = "com\\ideamov\\core\\bean\\";
		/**
		 * 扫描指定文件目录
		 */
		File rootPathFile = new File("src/main/java");

		System.out.println(rootPathFile);
		/**
		 * 获取目录下的所有包名
		 */
		List<ClassPathMessage> classMessages = FileClassUtil.listFiles(packageUrl, rootPathFile,
				new ArrayList<ClassPathMessage>());
		/**
		 * 生成相应包名
		 */
		System.out.println(classMessages);
		System.out.println("扫描匹配JavaBean文件数:"+classMessages.size());
		for (ClassPathMessage classMessage : classMessages) {
			new InitUtils(Class.forName(classMessage.getClassName()),classMessage.getClassHbmXmlUrl());
		}
		System.out.println("OVER");
		// new InitUtils(Class.forName("com.ideamov.core.bean.demo.Demo"));

	}

}
