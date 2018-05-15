package com.ideamov.utils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.ideamov.dto.ClassPathMessage;

public class FileClassUtil {

	/**
	 * 获取所有文件列表
	 * 
	 * @param rootFile
	 * @param fileList
	 * @throws IOException
	 */
	public static List<ClassPathMessage> listFiles(String packageUrl, File rootFile, List<ClassPathMessage> ClassMessageList)
			throws IOException {

		File[] allFiles = rootFile.listFiles();
		for (File file : allFiles) {
			if (file.isDirectory()) {
				listFiles(packageUrl, file, ClassMessageList);
			} else {
				String path = file.getCanonicalPath();

				System.out.println(path);

				if (path.endsWith(".java") && path.contains(packageUrl)) {

					int index = path.indexOf(packageUrl);

					String className = path.substring(index, path.lastIndexOf("."));// 截取包名并去掉后缀名
					className = className.replace("\\", "."); // 获得标准类名

					String classHbmXmlUrl = path.replace(".java", ".hbm.xml");// 获得类hbm文件

					ClassPathMessage classMessage = new ClassPathMessage();

					classMessage.setClassName(className);
					classMessage.setClassHbmXmlUrl(classHbmXmlUrl);

					ClassMessageList.add(classMessage);

					System.out.println(className);
				}

			}
		}
		return ClassMessageList;
	}

}
