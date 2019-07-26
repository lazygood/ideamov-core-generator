package com.ideamov.core.service;
import java.io.Serializable;
import java.util.Map;
import com.ideamov.core.bean.demo.Demo;
import com.ideamov.util.page.Pager;
import com.ideamov.util.page.Sorter;

public interface DemoService{

Serializable addDemo(Demo demo);

Demo getDemo(String id);

Map<Object, Object> queryDemo(Demo demo, Pager pager, Sorter[] sorters) throws Exception;

int deleteDemo(String id);

void deleteDemoByIds(String ids[]);

void updateDemo(Demo demo) throws Exception;


Demo editDemo(Demo demo) throws Exception;

}