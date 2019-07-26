package com.ideamov.action.demo;
import java.util.Map;
import com.ideamov.core.bean.demo.Demo;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.ideamov.common.web.action.BaseAction;
import com.google.gson.Gson; import com.ideamov.common.web.exception.MyException; import com.ideamov.common.web.fiter.ExceptionInterceptor;
import com.ideamov.core.service.DemoService;
import org.apache.commons.lang3.StringUtils;

@SuppressWarnings({ "serial", "rawtypes", "unchecked" })
@Scope("property")
@Controller
public class DemoAjax extends BaseAction{

@Resource
private DemoService demoService;

public Demo demo = new Demo();


public void toQueryDemo() throws Exception {
Map<Object, Object> map = demoService.queryDemo(demo, pager, sorter); this.utf8Response().getWriter().write(this.getGsonTime().toJson(map));
}

public void addDemo() throws Exception {
if (StringUtils.isNotBlank(demo.getId())) throw new MyException("id必须为空"); demoService.editDemo(demo); Map result = ExceptionInterceptor.getSuccessMap(); result.put("id", demo.getId());  this.utf8Response().getWriter().write(new Gson().toJson(result));
}

public void updateDemo() throws Exception {
if (StringUtils.isBlank(demo.getId())) throw new MyException("id不能空"); demoService.editDemo(demo); Map result = ExceptionInterceptor.getSuccessMap(); this.utf8Response().getWriter().write(new Gson().toJson(result));
}

public void deleteDemo() throws Exception {
if (StringUtils.isBlank(demo.getId())) demo.setId(null); demoService.deleteDemo(demo.getId()); Map result = ExceptionInterceptor.getSuccessMap(); this.utf8Response().getWriter().write(new Gson().toJson(result));
}

}