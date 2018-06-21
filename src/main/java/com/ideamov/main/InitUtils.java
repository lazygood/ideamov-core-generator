package com.ideamov.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.ideamov.utils.CharUtil;

@SuppressWarnings("rawtypes")
public class InitUtils {
	
	private String packageName; // 类包名

	private String packageAction;// 控制层包名

	private String packageService;// 服务层包名

	private String packageServiceImpl;// 服务层包名

	private String packageDao; // 数据访问层包名

	private String packageDaoImpl;// 数据访问层包名

	private Class clazz; // 反射类对象

	private String Name; // 首字母大写类名

	private String name; // 首字母小写类名

	private String lowerName; // 全字母小写类名

	private List<String> fieldEN; // 类英文属性名

	private List<String> fieldCN; // 类中文属性名

	private List<String> fieldDN; // 类数据库字段名

	private List<String> fieldLength; // 类数据库字段长度

	private List<String> fieldTyep; // 类属性类型

	public InitUtils(Class clazz,String classHbmXmlUrl) throws IOException, ClassNotFoundException, DocumentException {
         
		this.clazz = clazz; //得到类反射

		DataInit(classHbmXmlUrl); //初始化类相关属性

		generatorDirectory(); //创建文件

	}

 

	private void generatorDirectory() throws IOException, ClassNotFoundException {

		File file = new File(Package.URL.get(Package.DAO_KEY));
		file.mkdirs();
		generatorDao(Package.URL.get(Package.DAO_KEY));

		file = new File(Package.URL.get(Package.DAOIMPL_KEY));
		file.mkdirs();
		generatorDaoImpl(Package.URL.get(Package.DAOIMPL_KEY));
		generatorCriterior(Package.URL.get(Package.DAOIMPL_KEY));

		file = new File(Package.URL.get(Package.SERVICE_KEY));
		file.mkdirs();
		generatorService(Package.URL.get(Package.SERVICE_KEY));

		file = new File(Package.URL.get(Package.SERVICEIMPL_KEY));
		file.mkdirs();
		generatorServiceImpl(Package.URL.get(Package.SERVICEIMPL_KEY));

		file = new File(Package.UrlChangeDir(this.packageAction));
		file.mkdirs();
		generatorAction(Package.URL.get(Package.ACTION_KEY));
		
		generatorStrutsXml(Package.URL.get(Package.ACTION_KEY));
		
	    generatorAjaxAction(Package.URL.get(Package.ACTION_KEY));
		
		generatorAjaxStrutsXml(Package.URL.get(Package.ACTION_KEY));

		file = new File(Package.URL.get(Package.PAGE_KEY) + name);
		file.mkdirs();
		generatorPage(Package.URL.get(Package.PAGE_KEY) + name);

	}

	private void generatorAjaxStrutsXml(String directoryName) throws IOException {
		File file = new File(Package.UrlChangeDir(this.packageAction) +"/" + "struts_" + name +"_ajax.xml");
		String packageName = directoryName.replace("/", ".");
		       packageName = packageName.substring(0, packageName.length() - 1);

		file.createNewFile();
		
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
		
		bufferedWriter.write(
				"<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\r\n"
			  + "<!DOCTYPE struts PUBLIC \"-//Apache Software Foundation//DTD Struts Configuration 2.3//EN\" \"http://struts.apache.org/dtds/struts-2.3.dtd\">\r\n"
			  + "<struts>\r\n"
			  + " <package name=\""+this.lowerName+"_ajax\" namespace=\"/"+this.lowerName+"/ajax\" extends=\"common\">\r\n"
			  + "<action name=\"*\" class=\"com.ideamov.action."+this.lowerName+"."+this.Name+"Ajax\" method=\"{1}\">\r\n"
			  + "</action>\r\n"
			  + "</package>\r\n"
			  + "</struts>\r\n"
				);
		
		bufferedWriter.flush();
		bufferedWriter.close();
		
	}

	private void generatorAjaxAction(String directoryName) throws IOException {
	 
		File file = new File( Package.UrlChangeDir(this.packageAction) +"/"+ Name + "Ajax.java");

		String packageName = directoryName.replace("/", ".");
		
		       packageName = packageName.substring(0, packageName.length() - 1);

		file.createNewFile();
		
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
		 
		bufferedWriter.write(
				 "package "+packageName+"."+this.lowerName+";\r\n"+
		         "import java.util.Map;\r\n"+
		         "import "+this.packageName+"."+Name+";\r\n"+
		         "import javax.annotation.Resource;\r\n"+
		         "import org.springframework.context.annotation.Scope;\r\n"+
		         "import org.springframework.stereotype.Controller;\r\n"+
		         "import com.ideamov.common.web.action.BaseAction;\r\n"+
		         "import com.google.gson.Gson; import com.ideamov.common.web.exception.MyException; import com.ideamov.common.web.fiter.ExceptionInterceptor;\r\n"+
		         "import "+Package.PACKAGE.get(Package.SERVICE_KEY)+"."+Name+"Service;\r\n"+
		         "import org.apache.commons.lang3.StringUtils;\r\n"+
		         "\r\n"+
		         "@SuppressWarnings({ \"serial\", \"rawtypes\", \"unchecked\" })\r\n"+
		         "@Scope(\"property\")\r\n"+
		         "@Controller\r\n"+
		         "public class "+Name+"Ajax extends BaseAction{\r\n"+
		         "\r\n"+
		         "@Resource\r\n"+
		         "private "+this.Name+"Service "+name+"Service;\r\n"+
		         "\r\n"+
		         "public "+this.Name+" "+this.name+" = new "+this.Name+"();\r\n"+
		         "\r\n"+
		         "\r\n"+
		         "public void toQuery"+this.Name+"() throws Exception {\r\n"+
		         "Map<Object, Object> map = "+this.name+"Service.query"+this.Name+"("+this.name+", pager, sorter); this.utf8Response().getWriter().write(this.getGsonTime().toJson(map));\r\n"+
		         "}\r\n"+
		         "\r\n"+
		         "public void add"+this.Name+"() throws Exception {\r\n"+
		         "if (StringUtils.isNotBlank("+this.name+".getId())) throw new MyException(\"id必须为空\"); "+this.name+"Service.edit"+this.Name+"("+this.name+"); Map result = ExceptionInterceptor.getSuccessMap(); result.put(\"id\", " + this.name+ ".getId());  this.utf8Response().getWriter().write(new Gson().toJson(result));\r\n"+
		         "}\r\n"+
		         "\r\n"+
		         "public void updateDemo() throws Exception {\r\n"+
		         "if (StringUtils.isBlank("+this.name+".getId())) throw new MyException(\"id不能空\"); "+this.name+"Service.edit"+this.Name+"("+this.name+"); Map result = ExceptionInterceptor.getSuccessMap(); this.utf8Response().getWriter().write(new Gson().toJson(result));\r\n"+
		         "}\r\n"+
		         "\r\n"+
		         "public void delete"+this.Name+"() throws Exception {\r\n"+
		         "if (StringUtils.isBlank("+this.name+".getId())) "+this.name+".setId(null); "+this.name+"Service.delete"+this.Name+"("+this.name+".getId()); Map result = ExceptionInterceptor.getSuccessMap(); this.utf8Response().getWriter().write(new Gson().toJson(result));\r\n"+
		         "}\r\n"+
		         "\r\n"+
		          "}"); 
		
		
	 
		
		bufferedWriter.flush();
		bufferedWriter.close();
	}

	private void generatorPage(String directoryName) throws ClassNotFoundException, IOException {

		String listName = directoryName + "/" + "toQuery"+this.Name+".jsp";
		
		String editName = directoryName + "/" + "toEdit"+this.Name+".jsp";

		this.write(listName, this.getListTemplate());

		this.write(editName, this.getEditTemplate());

	}

	private void generatorStrutsXml(String directoryName) throws IOException {
		File file = new File(Package.UrlChangeDir(this.packageAction) +"/" + "struts_" + name +".xml");
		String packageName = directoryName.replace("/", ".");
		       packageName = packageName.substring(0, packageName.length() - 1);

		file.createNewFile();
		
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
		
		bufferedWriter.write(
				"<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\r\n"
			  + "<!DOCTYPE struts PUBLIC \"-//Apache Software Foundation//DTD Struts Configuration 2.3//EN\" \"http://struts.apache.org/dtds/struts-2.3.dtd\">\r\n"
			  + "<struts>\r\n"
			  + " <package name=\""+this.lowerName+"\" namespace=\"/"+this.lowerName+"\" extends=\"common\">\r\n"
			  + "<action name=\"*\" class=\"com.ideamov.action."+this.lowerName+"."+this.Name+"Action\" method=\"{1}\">\r\n"
			  + "<result>/WEB-INF/page/"+this.name+"/{1}.jsp</result>\r\n"
			  + "<result name=\"toQuery"+this.Name+"\" type=\"redirect\" >toQuery"+this.Name+".action?pager.num=${pager.num}</result>\r\n"
			  + "</action>\r\n"
			  + "</package>\r\n"
			  + "</struts>\r\n"
				);
		
		bufferedWriter.flush();
		bufferedWriter.close();
		
	}

	private void generatorAction(String directoryName) throws IOException {
		
		File file = new File( Package.UrlChangeDir(this.packageAction) +"/"+ Name + "Action.java");

		String packageName = directoryName.replace("/", ".");
		
		       packageName = packageName.substring(0, packageName.length() - 1);

		file.createNewFile();
		
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
		 
		bufferedWriter.write(
				 "package "+packageName+"."+this.lowerName+";\r\n"+
		         "import java.util.Map;\r\n"+
		         "import "+this.packageName+"."+Name+";\r\n"+
		         "import java.util.ArrayList;\r\n"+
		         "import java.util.Date;\r\n"+
		         "import java.util.HashMap;\r\n"+
		         "import java.util.List;\r\n"+
		         "import com.ideamov.util.page.PagerUtil;\r\n"+
		         "import com.ideamov.util.poi.POIUtils;\r\n"+
		         "import org.apache.commons.beanutils.BeanUtils;\r\n"+
		         "import org.apache.commons.lang3.StringUtils;\r\n"+
		         "import java.lang.reflect.Field;\r\n"+
		         "import java.text.SimpleDateFormat;\r\n"+
		         "import javax.annotation.Resource;\r\n"+
		         "import org.springframework.context.annotation.Scope;\r\n"+
		         "import org.springframework.stereotype.Controller;\r\n"+
		         "import com.ideamov.common.web.action.BaseAction;\r\n"+
		         "import "+Package.PACKAGE.get(Package.SERVICE_KEY)+"."+Name+"Service;\r\n"+
		         "\r\n"+
		         "@SuppressWarnings({\"serial\",\"unchecked\"})\r\n"+
		         "@Scope(\"property\")\r\n"+
		         "@Controller\r\n"+
		         "public class "+Name+"Action extends BaseAction{\r\n"+
		         "\r\n"+
		         "@Resource\r\n"+
		         "private "+this.Name+"Service "+name+"Service;\r\n"+
		         "\r\n"+
		         "public "+this.Name+" "+this.name+" = new "+this.Name+"();\r\n"+
		         "\r\n"+
		         "\r\n"+
		         "//@RequiresPermissions(\""+this.name+":toQuery"+this.Name+"\")\r\n"+
		         "public String toQuery"+Name+"() throws Exception {\r\n"+
		         "\r\n"+
		         "Map<Object, Object> map = "+this.name+"Service.query"+this.Name+"("+this.name+", pager, sorter);\r\n"+
		         "\r\n"+
		         "this.getResquest().setAttribute(\"map\", map);\r\n"+
		         "\r\n"+
		         "return SUCCESS;\r\n"+
		         "\r\n"+
		         "}\r\n"+
		         "\r\n"+
		         "\r\n"+
		         "//@RequiresPermissions(\""+this.name+":toView"+this.Name+"\")\r\n"+
		         "public String toView"+Name+"(){\r\n"+
		         "\r\n"+
		         ""+this.name+" = "+this.name+"Service.get"+this.Name+"("+this.name+".getId());\r\n"+
		         "\r\n"+
		         "return SUCCESS;\r\n"+
		         "\r\n"+
		         "}\r\n"+
		         "\r\n"+
		         "\r\n"+
		     
		         "\r\n"+
		      
		         "\r\n"+
		         "//@RequiresPermissions(\""+this.name+":toEdit"+this.Name+"\")\r\n"+
		         "public String toEdit"+Name+"() throws Exception{\r\n"+
		         "\r\n"+
		         "if(StringUtils.isNotBlank("+this.name+".getId())) "+this.name+" = "+this.name+"Service.get"+this.Name+"("+this.name+".getId());\r\n"+
		         "\r\n"+
		         "return SUCCESS;\r\n"+
		         "\r\n"+
		         "}\r\n"+
		         "\r\n"+
		         "\r\n"+
		         "\r\n"+
		         "\r\n"+
		         "//@RequiresPermissions(\""+this.name+":edit"+this.Name+"\")\r\n"+
		         "public String edit"+this.Name+"() throws Exception{\r\n"+
		         "\r\n"+
		         "if(StringUtils.isBlank("+this.name+".getId())) "+this.name+".setId(null);\r\n"+
		         "\r\n"+
		         ""+this.name+"Service.edit"+this.Name+"("+this.name+");\r\n"+
		         "\r\n"+
		         "if(StringUtils.isNotBlank(this.backUrl)) { this.redirect(this.backUrl); return null;}\r\n"+
		         "\r\n"+
		         "return \"toQuery"+this.Name+"\";\r\n"+
		         "\r\n"+
		         "}\r\n"+
		         "\r\n"+
		         "\r\n"+
		         "\r\n"+
		         "\r\n"+
		         "\r\n"+
		         "\r\n"+
		         "//@RequiresPermissions(\""+this.name+":delete"+Name+"\")\r\n"+
		         "public String delete"+Name+"() throws Exception{\r\n"+
		         "\r\n"+
		         ""+this.name+"Service.delete"+this.Name+"("+this.name+".getId());\r\n"+
		         "\r\n"+
		         "if (StringUtils.isNotBlank(this.backUrl)) {  this.redirect(this.backUrl); return null; }\r\n"+
		         "\r\n"+
		         "return \"toQuery"+this.Name+"\";\r\n"+
		         "\r\n"+
		         "}\r\n"+
		         "\r\n"+
		         "\r\n"+
		         "\r\n"+
		         "//@RequiresPermissions(\""+this.name+":export"+Name+"\")\r\n"+
		         "public void export"+Name+"() throws Exception{\r\n"+
		         "pager.setSize(0);\r\n"+
		         "Map<Object, Object> map = "+this.name+"Service.query"+this.Name+"("+this.name+", pager, sorter);\r\n"+
		         "List<"+this.Name+"> list = (List<"+this.Name+">) map.get(PagerUtil.DATA_COLLECTION_KEY);\r\n"+
		         "List<Map<String, Object>> cols = new ArrayList<Map<String, Object>>();\r\n"+
		         "List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();\r\n"+
		         "		for (Field field : "+this.Name+".class.getDeclaredFields()) { Map<String, Object> col = new HashMap<String, Object>();col.put(\"name\", field.getName());cols.add(col);}\r\n"+
		         "for ("+this.Name+" "+this.name+" : list) { rows.add(BeanUtils.describe("+this.name+")); }\r\n"+
		         "		POIUtils.exportToExcel(this.getResponse(), cols, rows, \""+this.Name+"\" + new SimpleDateFormat(\"yyyyMMddHHmmss\").format(new Date()));\r\n"+
		         "\r\n"+
		         "}\r\n"+
		         "\r\n"+
		         "\r\n"+
		         "\r\n"+
		          "}"); 
		
		bufferedWriter.flush();
		bufferedWriter.close();
		
	}

	private void generatorServiceImpl(String directoryName) throws IOException {
		 
			File file = new File(directoryName + Name + "ServiceImpl.java");

			String packageName = directoryName.replace("/", ".");
			       packageName = packageName.substring(0, packageName.length() - 1);

			file.createNewFile();
			
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
			
			bufferedWriter.write(
					 "package "+packageName+";\r\n"+
			         "import java.io.Serializable;\r\n"+
					 "import java.util.Date;\r\n"+
			         "import java.util.Map;\r\n"+
			         "import "+this.packageName+"."+Name+";\r\n"+
			         "import com.ideamov.util.page.Pager;\r\n"+
			         "import com.ideamov.util.page.Sorter;\r\n"+
			         "import com.ideamov.util.page.UpdateHQLParser;\r\n"+
			         "import javax.annotation.Resource;\r\n"+
			         "import org.apache.commons.lang3.StringUtils;"+
			         "import org.springframework.stereotype.Service;\r\n"+
			         "import org.springframework.transaction.annotation.Transactional;\r\n"+
			         "import com.ideamov.common.web.exception.MyException;\r\n"+
			         "import "+Package.PACKAGE.get(Package.DAO_KEY)+"."+Name+"Dao;\r\n"+
			         "import "+Package.PACKAGE.get(Package.SERVICE_KEY)+"."+Name+"Service;\r\n"+
			         "\r\n"+
			         "@Service\r\n"+
			         "@Transactional\r\n"+
			         "public class "+Name+"ServiceImpl implements "+Name+"Service {\r\n"+
			         "\r\n"+
			         "@Resource\r\n"+
			         "private "+Name+"Dao "+name+"Dao;\r\n"+
			         "\r\n"+
			         "\r\n"+
			         "\r\n"+
			         "public Serializable add"+Name+"("+Name+" "+name+"){\r\n"+
			         "\r\n"+
			         "return "+name+"Dao.save"+Name+"("+name+");\r\n"+
			         "\r\n"+
			         "}\r\n"+
			         "\r\n"+
			         "public "+Name+" get"+Name+"(String id){\r\n"+
			         "\r\n"+
			         "return "+name+"Dao.get"+Name+"ById(id);\r\n"+
			         "\r\n"+
			         "}\r\n"+
			         "\r\n"+
			         "public Map<Object, Object> query"+Name+"("+Name+" "+name+", Pager pager, Sorter[] sorters) throws Exception{\r\n"+
			         "\r\n"+
			         "return "+name+"Dao.pageList("+name+", pager, sorters);\r\n"+
			         "\r\n"+
			         "}\r\n"+
			         "\r\n"+
			         "public int delete"+Name+"(String id){\r\n"+
			         "\r\n"+
			         "return "+name+"Dao.delete"+Name+"ById(id);\r\n"+
			         "\r\n"+
			         "}\r\n"+
			         "\r\n"+
			         "public void delete"+Name+"ByIds(String ids[]){\r\n"+
			         "\r\n"+
			         ""+name+"Dao.delete"+Name+"ByIds(ids);\r\n"+
			         "\r\n"+
			         "}\r\n"+
			         "\r\n"+
			         "public void update"+Name+"("+Name+" "+name+") throws Exception{\r\n"+
			         "\r\n"+
			         "UpdateHQLParser up = new UpdateHQLParser("+name+", \"id\");\r\n"+
			         "\r\n"+
			         ""+name+"Dao.update"+Name+"(up.getUpdateHql(), up.getArgValues());\r\n"+
			         "\r\n"+
			         "}\r\n"+
			         "\r\n"+
			         "\r\n"+
			         "\r\n"+
			         "public "+Name+" edit"+Name+"("+Name+" "+name+") throws Exception{\r\n"+
			         "//持久化保存\r\n"+
			         " if(StringUtils.isBlank("+name+".getId())) {\r\n"+
					 ""+name+".setCreateTime(new Date());\r\n"+
			         ""+name+"Dao.save"+Name+"("+name+");\r\n"+
			         "return "+name+";\r\n"+
			         "}\r\n"+
			         "//持久化更新\r\n"+
			         "else\r\n"+
			         "{\r\n"+
			         ""+Name+" local = "+name+"Dao.get"+Name+"ById("+name+".getId());\r\n"+
			         "//选择持久化快照需要更新的字段\r\n"+
			         "\r\n"+
			         "			if (local == null) { throw new MyException(\"无效id\"); }\r\n"+
			         "\r\n"+
			         "\r\n"+this.editField()+
			         "return local;\r\n"+
			         "\r\n"+
			         "}\r\n"+
			         "\r\n"+
			         "\r\n"+
			         "\r\n"+
			         "\r\n"+
			         "\r\n"+
			         "\r\n"+
			         "\r\n"+
			         "}\r\n"+
			         "\r\n"+
			         "\r\n"+
			         "\r\n"+
			         "\r\n"+
			          "}"); 
			
			bufferedWriter.flush();
			bufferedWriter.close();
		
	}

	private String editField() {
		StringBuilder stringBuilder = new StringBuilder();
		
		for(int i=0;i<this.fieldTyep.size();i++)
		{
			String field = this.fieldEN.get(i);  //首字母小写属性名
			String Field =CharUtil.firstUpper( this.fieldEN.get(i) );  //首字母大写属性名
			if("String".equals(fieldTyep.get(i)))
			{
				
				 stringBuilder.append("if (StringUtils.isNotBlank("+this.name+".get"+Field+"())){ local.set"+Field+"("+this.name+".get"+Field+"());}");
				 stringBuilder.append("\r\n");
			}else if ("Integer".equals(fieldTyep.get(i))||"Date".equals(fieldTyep.get(i))||"Double".equals(fieldTyep.get(i)))
			{
				 stringBuilder.append("if ("+this.name+".get"+Field+"()!=null){ local.set"+Field+"("+this.name+".get"+Field+"());}");
				 stringBuilder.append("\r\n");
			}
			else
			{
				
			}
			
		}
 
		return stringBuilder.toString();
	}

	private void generatorService(String directoryName) throws IOException {
	 
		File file = new File(directoryName + Name + "Service.java");

		String packageName = directoryName.replace("/", ".");
		       packageName = packageName.substring(0, packageName.length() - 1);

		file.createNewFile();
		
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
		
		bufferedWriter.write(
				 "package "+packageName+";\r\n"+
		         "import java.io.Serializable;\r\n"+
		         "import java.util.Map;\r\n"+
		         "import "+this.packageName+"."+Name+";\r\n"+
		         "import com.ideamov.util.page.Pager;\r\n"+
		         "import com.ideamov.util.page.Sorter;\r\n"+
		         "\r\n"+
		         "public interface "+Name+"Service{\r\n"+
		         "\r\n"+
		         "Serializable add"+Name+"("+Name+" "+name+");\r\n"+
		         "\r\n"+
		         ""+Name+" get"+Name+"(String id);\r\n"+
		         "\r\n"+
		         "Map<Object, Object> query"+Name+"("+Name+" "+name+", Pager pager, Sorter[] sorters) throws Exception;\r\n"+
		         "\r\n"+
		         "int delete"+Name+"(String id);\r\n"+
		         "\r\n"+
		         "void delete"+Name+"ByIds(String ids[]);\r\n"+
		         "\r\n"+
		         "void update"+Name+"("+Name+" "+name+") throws Exception;\r\n"+
		         "\r\n"+
		         "\r\n"+
		         ""+Name+" edit"+Name+"("+Name+" "+name+") throws Exception;\r\n"+
		         "\r\n"+
		          "}"); 

		
		bufferedWriter.flush();
		bufferedWriter.close();
		
		
	}

	private void generatorCriterior(String directoryName) throws IOException {
		File file = new File(directoryName+Name+"Criterior.java");
		 
		String packageName=directoryName.replace("/",".");
	           packageName=packageName.substring(0,packageName.length()-1);
		
		file.createNewFile();
		
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
		
		bufferedWriter.write(
				 "package "+packageName+";\r\n"+
		         "import "+this.packageName+"."+Name+";\r\n"+
		         "import com.ideamov.util.page.AbstractHQLCriterior;\r\n"+
				 "import org.apache.commons.lang.StringUtils;\r\n"+
		         "import com.ideamov.util.page.Pager;\r\n"+
		         "import com.ideamov.util.page.Sorter;\r\n"+
		         "\r\n"+
		         "public class "+Name+"Criterior extends AbstractHQLCriterior {\r\n"+
		         "\r\n"+
		         "public "+this.Name+"Criterior(Object o, Sorter[] sorters, Pager pager) {\r\n"+
		         "super(o, sorters, pager);\r\n"+
		         "}\r\n"+
		         "\r\n"+
		         "@Override\r\n"+
		         "protected void parse(Object o, Sorter[] sorters, Pager pager) {\r\n"+
		         "\r\n"+
		         "super.hql.append(\"select o from "+Name+" o where  1=1  \");\r\n"+
		         "\r\n"+
		         ""+this.Name+" queryModel = ("+this.Name+") o;\r\n"+
		         "\r\n"+
		         "if (queryModel != null) {\r\n"+
		         "\r\n"+
		         "if (StringUtils.isNotBlank(queryModel.getId())) { super.hql.append(\" and o.id =  ? \"); super.argValues.add(queryModel.getId());}\r\n"+
		         "\r\n"+
		         "}\r\n"+
		         "\r\n"+
		         "if (null != sorters) {\r\n"+
		         "super.hql.append(\" order by \");\r\n"+
		         "for (Sorter s : sorters) {\r\n"+
		         "super.hql.append(\" o.\" + s.getSort() + \" \" + s.getOrder() + \" \");\r\n"+
		         "}\r\n"+
		         "}else { super.hql.append(\" order by  o.createTime  desc \"); }\r\n"+
		         "}\r\n"+
		         "\r\n"+
		         "\r\n"+
		          "}"); 
				
				
				bufferedWriter.flush();
				bufferedWriter.close();
		
	}

	private void generatorDao(String directoryName) throws IOException {
		File file = new File(directoryName+Name+"Dao.java");
		 
		String packageName=directoryName.replace("/",".");
	           packageName=packageName.substring(0,packageName.length()-1);
		
		file.createNewFile();
		
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
	 
		bufferedWriter.write(
		 "package "+packageName+";\r\n"+
         "\r\n"+
         "import java.io.Serializable;\r\n"+
         "\r\n"+
         "import java.util.List;\r\n"+
         "\r\n"+
         "import java.util.Map;\r\n"+
         "\r\n"+
         "import com.ideamov.util.page.Pager;\r\n"+
         "\r\n"+
         "import com.ideamov.util.page.Sorter;\r\n"+
         "\r\n"+
         "import "+this.packageName+"."+Name+";\r\n"+
         "\r\n"+
         "public interface "+this.Name+"Dao {\r\n"+
         "\r\n"+
         "	Serializable save"+this.Name+"("+this.Name+" "+this.name+");\r\n"+
         "\r\n"+
         "	void update"+this.Name+"(String hql, Object... values);\r\n"+
         "\r\n"+
         "	void delete"+this.Name+"("+this.Name+" "+this.name+");\r\n"+
         "\r\n"+
         "	int delete"+this.Name+"ById(String id);\r\n"+
         "\r\n"+
         "	void delete"+this.Name+"ByIds(String ids[]);\r\n"+
         "\r\n"+
         "	"+this.Name+" get"+this.Name+"ById(Serializable id);\r\n"+
         "\r\n"+
         "	List<"+this.Name+"> getAll"+this.Name+"();\r\n"+
         "\r\n"+
         "	Map<Object, Object> pageList("+this.Name+" "+this.name+", Pager pager, Sorter[] sorters) throws Exception;\r\n"+
         "\r\n"+
         "void saveOrUpdate"+Name+"("+this.Name+" "+this.name+");\r\n"+
         "\r\n"+
         "\r\n"+
          "}"); 
		
		
		bufferedWriter.flush();
		bufferedWriter.close();		

	}
 
	private void generatorDaoImpl(String directoryName) throws IOException {
		
		File file = new File(directoryName+Name+"DaoImpl.java");
		 
		String packageName=directoryName.replace("/",".");
		
	           packageName=packageName.substring(0,packageName.length()-1);
		
		file.createNewFile();
		
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
	 
		bufferedWriter.write(
		 "package "+packageName+";\r\n"+
         "\r\n"+
         "import java.io.Serializable;\r\n"+
         "\r\n"+
         "import java.util.List;\r\n"+
         "\r\n"+
         "import java.util.Map;\r\n"+
         "\r\n"+
         "import com.ideamov.util.page.Pager;\r\n"+
         "\r\n"+
         "import com.ideamov.util.page.Sorter;\r\n"+
         "\r\n"+
         "import com.ideamov.util.page.PagerUtil;\r\n"+
         "\r\n"+
         "import org.springframework.stereotype.Repository;\r\n"+
         "\r\n"+
         "import "+this.packageName+"."+Name+";\r\n"+
         "\r\n"+
         "import com.ideamov.common.dao.impl.BaseDaoImpl;\r\n"+
         "\r\n"+
         "import "+Package.PACKAGE.get(Package.DAO_KEY)+"."+Name+"Dao;\r\n"+
         "\r\n"+
         "@Repository\r\n"+
         "public class "+this.Name+"DaoImpl extends BaseDaoImpl<"+Name+"> implements "+Name+"Dao {\r\n"+
         "\r\n"+
         "	 public Serializable save"+this.Name+"("+this.Name+" "+this.name+"){\r\n"+
         "\r\n"+
         "		return this.save("+name+");\r\n"+
         "\r\n"+
         "		}\r\n"+
         "\r\n"+
         "	 public void update"+this.Name+"(String hql, Object... values){\r\n"+
         "\r\n"+
         "		this.upadateHql(hql, values);\r\n"+
         "\r\n"+
         "		}\r\n"+
         "\r\n"+
         "	 public void delete"+this.Name+"("+this.Name+" "+this.name+"){\r\n"+
         "\r\n"+
         "		this.delete("+name+");\r\n"+
         "\r\n"+
         "		}\r\n"+
         "\r\n"+
         "	 public int delete"+this.Name+"ById(String id){\r\n"+
         "\r\n"+
         "		return this.deleteById(id);\r\n"+
         "\r\n"+
         "		}\r\n"+
         "\r\n"+
         "	 public void delete"+this.Name+"ByIds(String ids[]){\r\n"+
         "\r\n"+
         "		this.deleteByIds(ids);\r\n"+
         "\r\n"+
         "		}\r\n"+
         "\r\n"+
         "	 public "+this.Name+" get"+this.Name+"ById(Serializable id){\r\n"+
         "\r\n"+
         "		return this.get(id);\r\n"+
         "\r\n"+
         "		}\r\n"+
         "\r\n"+
         "	 public List<"+this.Name+"> getAll"+this.Name+"(){\r\n"+
         "\r\n"+
         "		return this.getAll"+Name+"();\r\n"+
         "\r\n"+
         "		}\r\n"+
         "@SuppressWarnings(\"unchecked\")\r\n"+
         "	 public Map<Object, Object> pageList("+this.Name+" "+this.name+", Pager pager, Sorter[] sorters) throws Exception{\r\n"+
         "\r\n"+
         "   "+this.Name+"Criterior criterior = new "+this.Name+"Criterior("+this.name+", sorters, pager);\r\n"+
         "\r\n"+
         "  int count = this.count(criterior.getCountHql(), criterior.getArgValues());\r\n"+
         "\r\n"+
         "  pager = new Pager(pager == null ? 1 : pager.getNum(), pager == null ? Pager.DEFAULT_PAGE_SIZE : pager.getSize(),count);\r\n"+
         "\r\n"+
         "  List<"+this.Name+"> dataList = this.pageList(criterior.getHql(), pager.getStartRow(), pager.getSize(),criterior.getArgValues());\r\n"+
         "\r\n"+
         "\r\n"+
         "  return PagerUtil.dataFormat(pager, dataList);\r\n"+
         "		}\r\n"+
         "\r\n"+
         "\r\n"+
         "	 public void saveOrUpdate"+this.Name+"("+this.Name+" "+this.name+"){\r\n"+
         "\r\n"+
         "		this.getSession().saveOrUpdate("+this.name+");\r\n"+
         "\r\n"+
         "		}\r\n"+
         "\r\n"+
          "}"); 
		
		
		bufferedWriter.flush();
		bufferedWriter.close();		


	}

	private void DataInit(String classHbmXmlUrl) throws DocumentException {

		Name = clazz.getSimpleName();//类名

		name = Name.substring(0, 1).toLowerCase() + Name.substring(1); //首字母小写类名

		lowerName = Name.toLowerCase();//全小写类名

		packageName = clazz.getPackage().getName();//包名

		Field[] fields = clazz.getDeclaredFields();//反射获取类的File对象，通过File对象获取类中定义的属性名和属性类型

		fieldEN = new ArrayList<String>();

		fieldTyep = new ArrayList<String>();

		for (Field field : fields) {
			if ("serialVersionUID".equals(field.getName())) {
				continue;
			}

			fieldEN.add(field.getName());
			fieldTyep.add(field.getType().getSimpleName());

		}

		/**
		 * 以下为MVC架构的 DAO SERVICE ACTION  的路径命名参数
		 */
		this.packageAction = Package.PACKAGE.get(Package.ACTION_KEY);

		this.packageService = Package.PACKAGE.get(Package.SERVICE_KEY);

		this.packageServiceImpl = Package.PACKAGE.get(Package.SERVICEIMPL_KEY);

		this.packageDao = Package.PACKAGE.get(Package.DAO_KEY);

		this.packageDaoImpl = Package.PACKAGE.get(Package.DAOIMPL_KEY);

		this.packageAction += "." + this.lowerName;

		this.packageService += "." + this.lowerName;

		this.packageServiceImpl += "." + this.lowerName;

		this.packageDao += "." + this.lowerName;

		this.packageDaoImpl += "." + this.lowerName;
		
		/**
		 * 初始化hbm文件参数
		 */
		//0 参数初始化
			fieldCN = new ArrayList<String>();
			fieldDN = new ArrayList<String>();
			fieldLength = new ArrayList<String>();
			fieldCN.add("编号");
			fieldDN.add("id");
			fieldLength.add("32");
		//1 创建解析器,加载文档对象
		  SAXReader reader = new SAXReader();
	       Document document = reader.read(new File(classHbmXmlUrl));
		//2 获得根元素
	      Element hibernateMappingElement = 	document.getRootElement();
	      Element classElement = hibernateMappingElement.element("class");
	      
	    //3 通过根元素遍历子元素
	      Iterator<Element> it = classElement.elementIterator("property");
	      while(it.hasNext()){
	    	  
	    	 Element propertyElement =  it.next();
	    	 
	    	 Element columnElement = propertyElement.element("column");
	    	 
	    	 String fileEN = propertyElement.attributeValue("name");
	    	 
	    	 String filedCN = columnElement.elementText("comment");
	    	  
	    	 String filedDN =  columnElement.attributeValue("name");

	    	 String filedLength =  columnElement.attributeValue("length");

			if (filedLength == null)
				filedLength = "11";

			fieldCN.add(filedCN);
			fieldDN.add(filedDN);
			fieldLength.add(filedLength);

			System.out.println(filedCN + " " + filedDN + " " + filedLength);
	    	
	      }

	}

	private void write(String fileRealName, String content) throws IOException {

		File file = new File(fileRealName);

		file.createNewFile();

		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));

		bufferedWriter.write(content);

		bufferedWriter.flush();

		bufferedWriter.close();

	}
	
	
	public String getEditTemplate()
	{
		   
		   List<String> Text = new ArrayList<String>();  //是否包含富文本编辑器，    默认： 否
		   List<String> File = new ArrayList<String>();  //是否包含文件上传插件，    默认： 否
		
		 StringBuilder  builder = new StringBuilder();
			
			builder.append("<%@ page language=\"java\" contentType=\"text/html; charset=utf-8\" pageEncoding=\"utf-8\"%>").append("\r\n");
			builder.append("<%-- 全局变量    --%><%@ include file=\"/common/taglibs.jsp\"%>").append("\r\n");
			builder.append("<!doctype html>").append("\r\n");
			builder.append("<html class=\"no-js\">").append("\r\n");
			builder.append("<head>").append("\r\n");
			builder.append("<%-- 公共样式    --%><%@include file=\"../common/title.jsp\"%>").append("\r\n");
			builder.append("</head>").append("\r\n");
			builder.append("<body>").append("\r\n");
			builder.append("<%-- 页面头部    --%><%@include file=\"../common/header.jsp\"%>").append("\r\n");
			builder.append("").append("\r\n");
			builder.append("<div class=\"am-cf admin-main\">").append("\r\n");
			builder.append("").append("\r\n");
			builder.append("<%-- 页面左部    --%><%@include file=\"../common/left.jsp\"%>").append("\r\n");
			builder.append("").append("\r\n");
			builder.append("<%-- 右部内容   -开始   --%>").append("\r\n");
			builder.append("").append("\r\n");		 
			builder.append("<div id=\"admin-content\" class=\"admin-content\">").append("\r\n");	
			builder.append("<form class=\"am-form am-form-horizontal\"  method=\"post\" action=\"${path}/"+this.name.toLowerCase()+"/edit"+this.Name+".action\"  data-am-validator >").append("\r\n");	
			builder.append("").append("\r\n");	
			builder.append("<input type=\"hidden\"  name=\"backUrl\"  value=\"${backUrl }\" >").append("\r\n");	
			builder.append("").append("\r\n");	
			builder.append("<div class=\"admin-content-body\"><div class=\"am-cf am-padding am-padding-bottom-0\"><div class=\"am-fl am-cf\"><strong class=\"am-text-primary am-text-lg\">edit "+this.Name+"</strong></div></div>").append("\r\n");
			builder.append("").append("\r\n");	
			builder.append("<hr />").append("\r\n");		 
			builder.append("").append("\r\n");
			builder.append("<div>").append("\r\n");	
			builder.append("").append("\r\n");		 
			builder.append("<div class=\"am-u-sm-12 am-u-md-4 am-u-md-push-8\" style=\"display: none;\" > <div class=\"am-panel am-panel-default\"> <div class=\"am-panel-bd\"> <div class=\"am-g\">  </div> </div></div></div>").append("\r\n");
			builder.append("").append("\r\n");	
			
			builder.append("<div class=\"am-u-sm-12 am-u-md-8 am-u-md-pull-4\">").append("\r\n");	
			builder.append("").append("\r\n");
			for( int i=0;i<fieldEN.size();i++)
			{
			  //Type(String Double Integer Type )>Dn
			  //如果类属性命名为id 隐藏
				String T  = fieldTyep.get(i);//java类型
				String EN = fieldEN.get(i);//英文名
				String CN = fieldCN.get(i);//中文名
				String DN = fieldDN.get(i);//数据库字段名
				String DS = fieldLength.get(i);//数据库字段长度
				
			    if("String".equals(T))
			    {
				      //如果为id(主键)隐藏文本框
					  if("id".equals(EN))
					  {
						  builder.append("<div style=\"display: none;\"  class=\"am-form-group\"> <label for=\"user-name\" class=\"am-u-sm-3 am-form-label\">"+EN+" </label><div class=\"am-u-sm-9\"><input type=\"text\"   name=\""+this.name+"."+EN+"\" placeholder=\"\"  value=\"${"+this.name+"."+EN+"}\"  ></div></div>").append("\r\n");  
					  }
					  //如果数据库字段命名包含_text引入富文本编辑器
					  else if(fieldDN.get(i).lastIndexOf("_text")>-1)
					  {
						  Text.add(this.name+"_"+EN);
						  builder.append("<div class=\"am-form-group\"> <label for=\"user-name\" class=\"am-u-sm-3 am-form-label\">"+fieldCN.get(i)+" </label><div class=\"am-u-sm-9\"> <textarea rows=\"10\" id=\""+this.name+"_"+EN+"\"  name=\""+this.name+"."+EN+"\" style=\"style=\"width: 105%;height: 300px\"\" > ${"+this.name+"."+EN+"} </textarea> </div></div>").append("\r\n");
					  }
					  //如果数据库字段命名包含_url引入文件上传插件
					  else if(fieldDN.get(i).lastIndexOf("_url")>-1)
					  {
						  File.add(this.name+"_"+EN);
						  builder.append("<div class=\"am-form-group\"> <label for=\"user-name\" class=\"am-u-sm-3 am-form-label\">"+fieldCN.get(i)+" </label><div class=\"am-u-sm-9\">  <input id=\"up_"+this.name+"_"+EN+"\" type=\"file\" name=\"file\" class=\"am-input-sm\" accept=\".jpg,.png\" style=\"margin-top: 11px;\" form=\"upFrom_"+this.name+"_"+EN+"\"> <input id=\"upInput_"+this.name+"_"+EN+"\" type=\"hidden\" name=\""+this.name+"."+EN+"\" value=\"${"+this.name+"."+EN+"}\"> </div></div>").append("\r\n");
						  builder.append("<div class=\"am-form-group\"> <label for=\"user-name\" class=\"am-u-sm-3 am-form-label\"> </label><div class=\"am-u-sm-9\">  <img id=\"upImage_"+this.name+"_"+EN+"\" style=\"width: 300px; height: 200px\" src=\"${"+this.name+"."+EN+"}\" alt=\"无图片显示\"> </div></div>").append("\r\n");
					  }
					  else
					  {
					  builder.append("<div class=\"am-form-group\"> <label for=\"user-name\" class=\"am-u-sm-3 am-form-label\">"+fieldCN.get(i)+" </label><div class=\"am-u-sm-9\"><input type=\"text\"   name=\""+this.name+"."+EN+"\" placeholder=\"\"  value=\"${"+this.name+"."+EN+"}\" maxlength=\""+DS+"\" required ></div></div>").append("\r\n");
					  }
			    }else if ("Integer".equals(T))
			    {
					  //如果是数据字典引入标签
					  if(fieldDN.get(i).lastIndexOf("_state")>-1||fieldDN.get(i).lastIndexOf("_type")>-1)
					  {
					  builder.append("<div class=\"am-form-group\"> <label for=\"user-name\" class=\"am-u-sm-3 am-form-label\">"+fieldCN.get(i)+" </label><div class=\"am-u-sm-9\">    <ideamov:select  code=\""+DN.toUpperCase()+"\"  name=\""+this.name+"."+EN+"\" key=\"${"+this.name+"."+EN+"}\" /> </div></div>").append("\r\n");
					  }
					  //如果是普通数值
					  else
					  {
				      builder.append("<div class=\"am-form-group\"> <label for=\"user-name\" class=\"am-u-sm-3 am-form-label\">"+fieldCN.get(i)+" </label><div class=\"am-u-sm-9\"><input type=\"text\"   name=\""+this.name+"."+EN+"\" placeholder=\"\"  value=\"${"+this.name+"."+EN+"}\"    pattern=\"^\\d{1,"+DS+"}$\" maxlength=\""+fieldLength.get(i)+"\" required ></div></div>").append("\r\n");
					  }
			    }else if ("Date".equals(T))
			    {
					  //如果数据库字段命名为create_time编辑页面不显示
					  if(!"create_time".equals(DN))
					  {
					  builder.append("<div class=\"am-form-group\"> <label for=\"user-name\" class=\"am-u-sm-3 am-form-label\">"+CN+" </label><div class=\"am-u-sm-9\"><input type=\"text\"   name=\""+this.name+"."+EN+"\" placeholder=\"\"  value=\"<fmt:formatDate value=\"${"+this.name+"."+EN+"}\" pattern=\"yyyy-MM-dd\"/>\" data-am-datepicker readonly required ></div></div>").append("\r\n");  
					  }
			    }else if ("Double".equals(T))
			    {
			    	  builder.append("<div class=\"am-form-group\"> <label for=\"user-name\" class=\"am-u-sm-3 am-form-label\">"+fieldCN.get(i)+" </label><div class=\"am-u-sm-9\"><input type=\"text\"   name=\""+this.name+"."+EN+"\" placeholder=\"\"  value=\"${"+this.name+"."+EN+"}\" maxlength=\""+DS+"\" required ></div></div>").append("\r\n");
			    }
			    else
			    {
				   
			    }
			  
			}
			builder.append("").append("\r\n");
			builder.append("<div class=\"am-form-group\"> <div class=\"am-u-sm-6 am-u-sm-push-6\"><button type=\"submit\" class=\"am-btn am-btn-primary\">提交保存</button></div></div>").append("\r\n");	
			builder.append("</div>").append("\r\n");		
			
			builder.append("</div>").append("\r\n");
			builder.append("</div>").append("\r\n");	
			builder.append("</form>").append("\r\n");		
            //如果包含文件上传插件,加载表单			
			if(File.size()>0)
			{
				for(String fileId : File)
				{
				builder.append("<form action=\"${path}/"+this.name.toLowerCase()+"/upload\" method=\"post\" id=\"upFrom_"+fileId+"\" enctype=\"multipart/form-data\"></form>").append("\r\n");
				}
			}
			builder.append("</div>").append("\r\n");
			builder.append("").append("\r\n");	
			builder.append("<%-- 右部内容   -结束  --%>").append("\r\n");
			builder.append("</div>").append("\r\n");
			builder.append("").append("\r\n");
			builder.append("<%-- 页面底部    --%><%@include file=\"../common/footer.jsp\"%>").append("\r\n");
            //如果包含富文本编辑器,加载样式			
			if(Text.size()>0)
			{
				builder.append("<!-- UM配置文件、编辑器源码文件、样式 -->").append("\r\n");
				builder.append("<link href=\"${path }/res/assets/umeditor/themes/default/css/umeditor.css\" type=\"text/css\" rel=\"stylesheet\">").append("\r\n");
				builder.append("<script type=\"text/javascript\" src=\"${path }/res/assets/umeditor/third-party/template.min.js\"></script>").append("\r\n");
				builder.append("<script type=\"text/javascript\" src=\"${path }/res/assets/umeditor/umeditor.config.js\" charset=\"utf-8\"></script>").append("\r\n");
				builder.append("<script type=\"text/javascript\" src=\"${path }/res/assets/umeditor/umeditor.min.js\" charset=\"utf-8\"></script>").append("\r\n");
				builder.append("<script type=\"text/javascript\" src=\"${path }/res/assets/umeditor/lang/zh-cn/zh-cn.js\"></script>").append("\r\n");
				builder.append("<script type=\"text/javascript\"> function ueEditorInit(obj) { var editor = UM.getEditor(obj); } </script>").append("\r\n");
				for(String textId : Text)
				{
					builder.append("<script type=\"text/javascript\"> $(function() { ueEditorInit('"+textId+"'); }); </script>").append("\r\n");
				}
				
			}
            //如果包含文件上传插件,初始化参数			
			if(File.size()>0)
			{
				for(String fileId : File)
				{
					builder.append("<script type=\"text/javascript\"> $(\"#up_"+fileId+"\").change(function() { myConfig.file.upload(\"upFrom_"+fileId+"\", \"upInput_"+fileId+"\", \"upImage_"+fileId+"\"); }); </script>").append("\r\n");
				}
			}
			builder.append("").append("\r\n");
			builder.append("</body>").append("\r\n");
			builder.append("</html>").append("\r\n");
			builder.append("").append("\r\n");

			
			return builder.toString();
		
	}
	
	public String getListTemplate()
	{
		
		 StringBuilder  builder = new StringBuilder();
			
			builder.append("<%@ page language=\"java\" contentType=\"text/html; charset=utf-8\" pageEncoding=\"utf-8\"%>").append("\r\n");
			builder.append("<%-- 全局变量    --%><%@ include file=\"/common/taglibs.jsp\"%>").append("\r\n");
			builder.append("<!doctype html>").append("\r\n");
			builder.append("<html class=\"no-js\">").append("\r\n");
			builder.append("<head>").append("\r\n");
			builder.append("<%-- 公共样式    --%><%@include file=\"../common/title.jsp\"%>").append("\r\n");
			builder.append("</head>").append("\r\n");
			builder.append("<body>").append("\r\n");
			builder.append("<%-- 页面头部    --%><%@include file=\"../common/header.jsp\"%>").append("\r\n");
			builder.append("").append("\r\n");
			builder.append("<div class=\"am-cf admin-main\">").append("\r\n");
			builder.append("").append("\r\n");
			builder.append("<%-- 页面左部    --%><%@include file=\"../common/left.jsp\"%>").append("\r\n");
			builder.append("").append("\r\n");
			builder.append("<%-- 右部内容   -开始   --%>").append("\r\n");
			builder.append("<div id=\"admin-content\" class=\"admin-content\">").append("\r\n");
			builder.append("").append("\r\n");
			builder.append("<div class=\"admin-content-body\">").append("").append("\r\n");
			builder.append("<div class=\"am-cf am-padding am-padding-bottom-0\"> <div class=\"am-fl am-cf\"> <strong class=\"am-text-primary am-text-lg\">"+this.Name+"</strong> </div> </div>").append("\r\n");
			builder.append("").append("\r\n");
			builder.append("<hr>").append("\r\n");
			builder.append("").append("\r\n");
			builder.append("<div class=\"am-g\">").append("\r\n");
			builder.append("<form id=\"myForm\"  method=\"post\" >").append("\r\n");
			builder.append("<div class=\"am-u-sm-12 am-u-md-7\">").append("\r\n");
			builder.append("<div class=\"am-btn-toolbar\">").append("\r\n");
			builder.append("<div class=\"am-btn-group am-btn-group-xs\">").append("\r\n");
			builder.append("<button  type=\"button\"  class=\"am-btn am-btn-default\" onclick=\"myConfig.page.edit('${path}/"+this.name+"/toEdit"+this.Name+".action?"+this.name+".id=${row.id}&pager.num=${map.pager.num}&pmenu=${param.pmenu }')\" ><span class=\"am-icon-plus\"></span>新增</button>").append("\r\n");
			builder.append("</div>").append("\r\n");
			builder.append("</div>").append("\r\n");
			builder.append("</div>").append("\r\n");
			builder.append("").append("\r\n");
			builder.append("<div class=\"am-u-sm-12 am-u-md-3\">").append("\r\n");
			builder.append("<div class=\"am-input-group am-input-group-sm\">").append("\r\n");
			builder.append("<input type=\"text\" name="+this.name+".id\" class=\"am-form-field\" placeholder=\"id查询\" value=\"${"+this.name+".id }\" >").append("\r\n");
			builder.append("<span class=\"am-input-group-btn\"> <button class=\"am-btn am-btn-default\" type=\"button\" onclick=\"myConfig.page.go('${path}/"+this.name+"/toQuery"+this.Name+"','1','${param.pmenu }');\" >搜索</button> <button class=\"am-btn am-btn-default\" type=\"button\" onclick=\"myConfig.page.import('${path}/"+this.name+"/export"+this.Name+"');\">导出</button></span>").append("\r\n");
			builder.append("</div>").append("\r\n");
			builder.append("</div>").append("\r\n");
			builder.append("</form>").append("\r\n");
			builder.append("</div>").append("\r\n");
			builder.append("").append("\r\n");
			
			builder.append("<div class=\"am-g\">").append("\r\n");
			builder.append("<div class=\"am-u-sm-12\">").append("\r\n");
			builder.append("<form class=\"am-form\">").append("\r\n");
			builder.append("<table class=\"am-table am-table-striped am-table-hover table-main\">").append("\r\n");
			builder.append("<thead>").append("\r\n");
			builder.append("<tr>").append("\r\n");
			
			for(String field:this.fieldCN)
			{
				builder.append("<th>"+field+"</th>");
			}
			
			builder.append("<th>操作</th>");
			builder.append("</tr>").append("\r\n");
			builder.append("</thead>").append("\r\n");
			builder.append("<tbody>").append("\r\n");
			 
			builder.append("<c:forEach items=\"${map.rows}\" var=\"row\">").append("\r\n");
			builder.append("<tr>").append("\r\n");
			
			for(int i =0 ;i<this.fieldEN.size();i++)
			{
				  //Type(String Double Integer Type )>Dn
				  //如果类属性命名为id 隐藏
				String T  = fieldTyep.get(i);//java类型
				String EN = fieldEN.get(i);//英文名
				String CN = fieldCN.get(i);//中文名
				String DN = fieldDN.get(i);//数据库字段名
				String DS = fieldLength.get(i);//数据库字段长度
			    if(EN.lastIndexOf("Date")>-1||EN.lastIndexOf("Time")>-1)
			    {
				builder.append("<td><fmt:formatDate value=\"${row."+EN+"}\" pattern=\"yyyy-MM-dd\"/></td>");
			    }
			    else if(EN.lastIndexOf("Type")>-1||EN.lastIndexOf("State")>-1)
			    {
			    builder.append("<td><ideamov:text  code=\""+DN.toUpperCase()+"\" key=\"${row."+EN+"}\" /></td>");	
			    }
			    else
			    {
			    builder.append("<td>${row."+EN+"}</td>");	
			    }
			}
			
			builder.append("<td>").append("\r\n");
			builder.append("<div class=\"am-btn-toolbar\">").append("\r\n");
			builder.append("<div class=\"am-btn-group am-btn-group-xs\">").append("\r\n");
			builder.append("<button type=\"button\" class=\"am-btn am-btn-default am-btn-xs am-text-secondary\"              onclick=\"myConfig.page.edit('${path}/"+this.name+"/toEdit"+this.Name+".action?"+this.name+".id=${row.id}&pager.num=${map.pager.num}&pmenu=${param.pmenu }')\"> <span class=\"am-icon-pencil-square-o\"></span> 编辑 </button>").append("\r\n");
			builder.append("<button type=\"button\" class=\"am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only\" onclick=\"myConfig.page.edit('${path}/"+this.name+"/delete"+this.Name+".action?"+this.name+".id=${row.id}&pager.num=${map.pager.num}&pmenu=${param.pmenu }')\" ><span class=\"am-icon-trash-o\">        </span> 删除</button>").append("\r\n");
			builder.append("</div>").append("\r\n");
			builder.append("</div>").append("\r\n");
			builder.append("</td>").append("\r\n");
			builder.append("</tr>").append("\r\n");
			builder.append("</c:forEach>").append("\r\n");
			builder.append("</tbody>").append("\r\n");
			builder.append("</table>").append("\r\n");
			builder.append("").append("\r\n");
			builder.append("<div class=\"am-cf\">").append("\r\n");
			builder.append("共 ${map.pager.rowCount} 条记录").append("\r\n");
			builder.append("<div class=\"am-fr\">").append("\r\n");
			builder.append("<ul class=\"am-pagination\">").append("\r\n");
			builder.append("<li><a href=\"javascript:void(0)\" onclick=\"myConfig.page.go('${path}/"+this.name+"/toQuery"+this.Name+"','1','${param.pmenu }');\"                  >第一页</a></li>").append("\r\n");
			builder.append("<li><a href=\"javascript:void(0)\" onclick=\"myConfig.page.go('${path}/"+this.name+"/toQuery"+this.Name+"','${map.pager.prev}','${param.pmenu }');\"  >上一页</a></li>").append("\r\n");
			builder.append("<li><a href=\"javascript:void(0)\" onclick=\"myConfig.page.go('${path}/"+this.name+"/toQuery"+this.Name+"','${map.pager.next}','${param.pmenu }');\"  >下一页</a></li>").append("\r\n");
			builder.append("<li><a href=\"javascript:void(0)\" onclick=\"myConfig.page.go('${path}/"+this.name+"/toQuery"+this.Name+"','${map.pager.count}','${param.pmenu }');\" >最末页</a></li>").append("\r\n");
			builder.append("</ul>").append("\r\n");
			builder.append("</div>").append("\r\n");
			builder.append("</div>").append("\r\n");
			builder.append("</form>").append("\r\n");
			builder.append("</div>").append("\r\n");
			builder.append("</div>").append("\r\n");
			builder.append("</div>").append("\r\n");
			builder.append("</div>").append("\r\n");
			builder.append("<%-- 右部内容   -结束  --%>").append("\r\n");
			builder.append("</div>").append("\r\n");
			builder.append("").append("\r\n");
			builder.append("<%-- 页面底部    --%><%@include file=\"../common/footer.jsp\"%>").append("\r\n");
			builder.append("").append("\r\n");
			builder.append("</body>").append("\r\n");
			builder.append("</html>").append("\r\n");
			builder.append("").append("\r\n");
 
			
			return builder.toString();
		 
	}
	
}