package com.ideamov.main;

import java.util.HashMap;
import java.util.Map;

import com.ideamov.run.GeneratorCoreMap;

public class Package {

	public static Map<String, String> URL = new HashMap<String, String>();
	
	public static Map<String, String> PACKAGE = new HashMap<String, String>();

	public static String DIRECTORY;

	public static String WEB_DIRECTORY;

	public static String DAO_KEY;

	public static String DAOIMPL_KEY;

	public static String SERVICE_KEY;

	public static String SERVICEIMPL_KEY;

	public static String ACTION_KEY;

	public static String PAGE_KEY;

	private static String DAO_VALUE;

	private static String DAOIMPL_VALUE;

	private static String SERVICE_VALUE;

	private static String SERVICEIMPL_VALUE;

	private static String ACTION_VALUE;

	private static String PAGE_VALUE;

	static {

		DAO_KEY = "DAO";

		DAOIMPL_KEY = "DAOIMPL";

		SERVICE_KEY = "SERVIER";

		SERVICEIMPL_KEY = "SERVICEIMPL";

		ACTION_KEY = "ACTION";

		PAGE_KEY = "PAGE";

		DAO_VALUE = GeneratorCoreMap.MAP.get("dao");

		DAOIMPL_VALUE = GeneratorCoreMap.MAP.get("daoImpl");

		SERVICE_VALUE = GeneratorCoreMap.MAP.get("service");

		SERVICEIMPL_VALUE = GeneratorCoreMap.MAP.get("serviceImpl");

		ACTION_VALUE = GeneratorCoreMap.MAP.get("controller");

		PAGE_VALUE = GeneratorCoreMap.MAP.get("jsp");

		URL.put(DAO_KEY, DAO_VALUE);

		URL.put(DAOIMPL_KEY, DAOIMPL_VALUE);

		URL.put(SERVICE_KEY, SERVICE_VALUE);

		URL.put(SERVICEIMPL_KEY, SERVICEIMPL_VALUE);

		URL.put(ACTION_KEY, ACTION_VALUE);

		URL.put(PAGE_KEY, PAGE_VALUE);

		PACKAGE.put(DAO_KEY, UrlChangePackage(DAO_VALUE));

		PACKAGE.put(DAOIMPL_KEY, UrlChangePackage(DAOIMPL_VALUE));

		PACKAGE.put(SERVICE_KEY, UrlChangePackage(SERVICE_VALUE));

		PACKAGE.put(SERVICEIMPL_KEY, UrlChangePackage(SERVICEIMPL_VALUE));

		PACKAGE.put(ACTION_KEY, UrlChangePackage(ACTION_VALUE));

		PACKAGE.put(PAGE_KEY, UrlChangePackage(PAGE_VALUE));

	}

	public static String UrlChangePackage(String url) {

		url = url.replace("/", ".");

		url = url.substring(0, url.length() - 1);

		return url;

	}
	
	public static String UrlChangeDir(String url) {

		url = url.replace(".", "/");

		return url;

	}

}
