package com.ideamov.core.service.impl;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import com.ideamov.core.bean.demo.Demo;
import com.ideamov.util.page.Pager;
import com.ideamov.util.page.Sorter;
import com.ideamov.util.page.UpdateHQLParser;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ideamov.common.web.exception.MyException;
import com.ideamov.core.dao.DemoDao;
import com.ideamov.core.service.DemoService;

@Service
@Transactional
public class DemoServiceImpl implements DemoService {

@Resource
private DemoDao demoDao;



public Serializable addDemo(Demo demo){

return demoDao.saveDemo(demo);

}

public Demo getDemo(String id){

return demoDao.getDemoById(id);

}

public Map<Object, Object> queryDemo(Demo demo, Pager pager, Sorter[] sorters) throws Exception{

return demoDao.pageList(demo, pager, sorters);

}

public int deleteDemo(String id){

return demoDao.deleteDemoById(id);

}

public void deleteDemoByIds(String ids[]){

demoDao.deleteDemoByIds(ids);

}

public void updateDemo(Demo demo) throws Exception{

UpdateHQLParser up = new UpdateHQLParser(demo, "id");

demoDao.updateDemo(up.getUpdateHql(), up.getArgValues());

}



public Demo editDemo(Demo demo) throws Exception{
//持久化保存
 if(StringUtils.isBlank(demo.getId())) {
demo.setCreateTime(new Date());
demoDao.saveDemo(demo);
return demo;
}
//持久化更新
else
{
Demo local = demoDao.getDemoById(demo.getId());
//选择持久化快照需要更新的字段

			if (local == null) { throw new MyException("无效id"); }


if (StringUtils.isNotBlank(demo.getId())){ local.setId(demo.getId());}
if (StringUtils.isNotBlank(demo.getName())){ local.setName(demo.getName());}
if (demo.getAge()!=null){ local.setAge(demo.getAge());}
if (StringUtils.isNotBlank(demo.getMemoText())){ local.setMemoText(demo.getMemoText());}
if (StringUtils.isNotBlank(demo.getImgUrl())){ local.setImgUrl(demo.getImgUrl());}
if (demo.getDemoType()!=null){ local.setDemoType(demo.getDemoType());}
if (demo.getDemoState()!=null){ local.setDemoState(demo.getDemoState());}
if (demo.getBalanceMoney()!=null){ local.setBalanceMoney(demo.getBalanceMoney());}
if (demo.getBirthdayDate()!=null){ local.setBirthdayDate(demo.getBirthdayDate());}
if (demo.getCreateTime()!=null){ local.setCreateTime(demo.getCreateTime());}
return local;

}







}




}