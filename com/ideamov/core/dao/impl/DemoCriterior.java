package com.ideamov.core.dao.impl;
import com.ideamov.core.bean.demo.Demo;
import com.ideamov.util.page.AbstractHQLCriterior;
import org.apache.commons.lang.StringUtils;
import com.ideamov.util.page.Pager;
import com.ideamov.util.page.Sorter;

public class DemoCriterior extends AbstractHQLCriterior {

public DemoCriterior(Object o, Sorter[] sorters, Pager pager) {
super(o, sorters, pager);
}

@Override
protected void parse(Object o, Sorter[] sorters, Pager pager) {

super.hql.append("select o from Demo o where  1=1  ");

Demo queryModel = (Demo) o;

if (queryModel != null) {

if (StringUtils.isNotBlank(queryModel.getId())) { super.hql.append(" and o.id like  ? "); super.argValues.add("%"+queryModel.getId()+"%");}
if (StringUtils.isNotBlank(queryModel.getName())) { super.hql.append(" and o.name like  ? "); super.argValues.add("%"+queryModel.getName()+"%");}
if (queryModel.getAge()!=null) { super.hql.append(" and o.age =  ? "); super.argValues.add(queryModel.getAge());}
if (StringUtils.isNotBlank(queryModel.getMemoText())) { super.hql.append(" and o.memoText like  ? "); super.argValues.add("%"+queryModel.getMemoText()+"%");}
if (StringUtils.isNotBlank(queryModel.getImgUrl())) { super.hql.append(" and o.imgUrl like  ? "); super.argValues.add("%"+queryModel.getImgUrl()+"%");}
if (queryModel.getDemoType()!=null) { super.hql.append(" and o.demoType =  ? "); super.argValues.add(queryModel.getDemoType());}
if (queryModel.getDemoState()!=null) { super.hql.append(" and o.demoState =  ? "); super.argValues.add(queryModel.getDemoState());}
if (queryModel.getBalanceMoney()!=null) { super.hql.append(" and o.balanceMoney =  ? "); super.argValues.add(queryModel.getBalanceMoney());}
if (StringUtils.isNotBlank(queryModel.getBirthdayDateBegin())) {  super.hql.append(" and o.birthdayDate >= '"+queryModel.getBirthdayDateBegin()+" 00:00:00' "); }
if (StringUtils.isNotBlank(queryModel.getBirthdayDateEnd()))   {  super.hql.append(" and o.birthdayDate <= '"+queryModel.getBirthdayDateEnd()+" 23:59:59' "); }
if (StringUtils.isNotBlank(queryModel.getCreateTimeBegin())) {  super.hql.append(" and o.createTime >= '"+queryModel.getCreateTimeBegin()+" 00:00:00' "); }
if (StringUtils.isNotBlank(queryModel.getCreateTimeEnd()))   {  super.hql.append(" and o.createTime <= '"+queryModel.getCreateTimeEnd()+" 23:59:59' "); }



}

if (null != sorters) {
super.hql.append(" order by ");
for (Sorter s : sorters) {
super.hql.append(" o." + s.getSort() + " " + s.getOrder() + " ");
}
}else { super.hql.append(" order by  o.createTime  desc "); }
}


}