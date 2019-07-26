package com.ideamov.core.dao;

import java.io.Serializable;

import java.util.List;

import java.util.Map;

import com.ideamov.util.page.Pager;

import com.ideamov.util.page.Sorter;

import com.ideamov.core.bean.demo.Demo;

public interface DemoDao {

	Serializable saveDemo(Demo demo);

	void updateDemo(String hql, Object... values);

	void deleteDemo(Demo demo);

	int deleteDemoById(String id);

	void deleteDemoByIds(String ids[]);

	Demo getDemoById(Serializable id);

	List<Demo> getAllDemo();

	Map<Object, Object> pageList(Demo demo, Pager pager, Sorter[] sorters) throws Exception;

void saveOrUpdateDemo(Demo demo);


}