package com.ideamov.core.dao.impl;

import java.io.Serializable;

import java.util.List;

import java.util.Map;

import com.ideamov.util.page.Pager;

import com.ideamov.util.page.Sorter;

import com.ideamov.util.page.PagerUtil;

import org.springframework.stereotype.Repository;

import com.ideamov.core.bean.demo.Demo;

import com.ideamov.common.dao.impl.BaseDaoImpl;

import com.ideamov.core.dao.DemoDao;

@Repository
public class DemoDaoImpl extends BaseDaoImpl<Demo> implements DemoDao {

	 public Serializable saveDemo(Demo demo){

		return this.save(demo);

		}

	 public void updateDemo(String hql, Object... values){

		this.upadateHql(hql, values);

		}

	 public void deleteDemo(Demo demo){

		this.delete(demo);

		}

	 public int deleteDemoById(String id){

		return this.deleteById(id);

		}

	 public void deleteDemoByIds(String ids[]){

		this.deleteByIds(ids);

		}

	 public Demo getDemoById(Serializable id){

		return this.get(id);

		}

	 public List<Demo> getAllDemo(){

		return this.getAllDemo();

		}
@SuppressWarnings("unchecked")
	 public Map<Object, Object> pageList(Demo demo, Pager pager, Sorter[] sorters) throws Exception{

   DemoCriterior criterior = new DemoCriterior(demo, sorters, pager);

  int count = this.count(criterior.getCountHql(), criterior.getArgValues());

  pager = new Pager(pager == null ? 1 : pager.getNum(), pager == null ? Pager.DEFAULT_PAGE_SIZE : pager.getSize(),count);

  List<Demo> dataList = this.pageList(criterior.getHql(), pager.getStartRow(), pager.getSize(),criterior.getArgValues());


  return PagerUtil.dataFormat(pager, dataList);
		}


	 public void saveOrUpdateDemo(Demo demo){

		this.getSession().saveOrUpdate(demo);

		}

}