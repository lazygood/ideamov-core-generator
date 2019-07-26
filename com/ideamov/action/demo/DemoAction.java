package com.ideamov.action.demo;
import java.util.Map;
import com.ideamov.core.bean.demo.Demo;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import com.ideamov.util.page.PagerUtil;
import com.ideamov.util.poi.POIUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.ideamov.common.web.action.BaseAction;
import com.ideamov.core.service.DemoService;

@SuppressWarnings({"serial","unchecked"})
@Scope("property")
@Controller
public class DemoAction extends BaseAction{

@Resource
private DemoService demoService;

public Demo demo = new Demo();


//@RequiresPermissions("demo:toQueryDemo")
public String toQueryDemo() throws Exception {

Map<Object, Object> map = demoService.queryDemo(demo, pager, sorter);

this.getResquest().setAttribute("map", map);

return SUCCESS;

}


//@RequiresPermissions("demo:toViewDemo")
public String toViewDemo(){

demo = demoService.getDemo(demo.getId());

return SUCCESS;

}




//@RequiresPermissions("demo:toEditDemo")
public String toEditDemo() throws Exception{

if(StringUtils.isNotBlank(demo.getId())) demo = demoService.getDemo(demo.getId());

return SUCCESS;

}




//@RequiresPermissions("demo:editDemo")
public String editDemo() throws Exception{

if(StringUtils.isBlank(demo.getId())) demo.setId(null);

demoService.editDemo(demo);

if(StringUtils.isNotBlank(this.backUrl)) { this.redirect(this.backUrl); return null;}

return "toQueryDemo";

}






//@RequiresPermissions("demo:deleteDemo")
public String deleteDemo() throws Exception{

demoService.deleteDemo(demo.getId());

if (StringUtils.isNotBlank(this.backUrl)) {  this.redirect(this.backUrl); return null; }

return "toQueryDemo";

}



//@RequiresPermissions("demo:exportDemo")
public void exportDemo() throws Exception{
pager.setSize(0);
Map<Object, Object> map = demoService.queryDemo(demo, pager, sorter);
List<Demo> list = (List<Demo>) map.get(PagerUtil.DATA_COLLECTION_KEY);
List<Map<String, Object>> cols = new ArrayList<Map<String, Object>>();
List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
		for (Field field : Demo.class.getDeclaredFields()) { Map<String, Object> col = new HashMap<String, Object>();col.put("name", field.getName());cols.add(col);}
for (Demo demo : list) { rows.add(BeanUtils.describe(demo)); }
		POIUtils.exportToExcel(this.getResponse(), cols, rows, "Demo" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));

}



}