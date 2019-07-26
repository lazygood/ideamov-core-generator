<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%-- 全局变量    --%><%@ include file="/common/taglibs.jsp"%>
<!doctype html>
<html class="no-js">
<head>
<%-- 公共样式    --%><%@include file="../common/title.jsp"%>
</head>
<body>
<%-- 页面头部    --%><%@include file="../common/header.jsp"%>

<div class="am-cf admin-main">

<%-- 页面左部    --%><%@include file="../common/left.jsp"%>

<%-- 右部内容   -开始   --%>
<div id="admin-content" class="admin-content">

<div class="admin-content-body">
<div class="am-cf am-padding am-padding-bottom-0"> <div class="am-fl am-cf"> <strong class="am-text-primary am-text-lg">Demo</strong> </div> </div>

<hr>

<form id="myForm"  method="post" >
<div class="am-g">
<div class="am-u-sm-12 am-u-md-1">
<div class="am-btn-toolbar"><div class="am-btn-group am-btn-group-xs">
 <button  type="button"  class="am-btn am-btn-default" onclick="myConfig.page.edit('${path}/demo/toEditDemo.action?demo.id=${row.id}&pager.num=${map.pager.num}&pmenu=${param.pmenu}&cmenu=${param.cmenu}')" ><span class="am-icon-plus"></span>新增</button>
</div>
</div></div>

<div class="am-u-sm-12 am-u-md-3">
<div class="am-input-group am-input-group-sm">
<input type="text" name="demo.id" class="am-form-field" placeholder="id查询" value="${demo.id }"><span class="am-input-group-btn"> <button class="am-btn am-btn-default" type="button" onclick="myConfig.page.go('${path}/demo/toQueryDemo','1','${param.pmenu}','${param.cmenu}');" >搜索</button> <button class="am-btn am-btn-default" type="button" onclick="myConfig.page.import('${path}/demo/exportDemo');">导出</button></span>
</div>
</div>
</div>
</form>

<div class="am-g">
<div class="am-u-sm-12">
<form class="am-form">
<table class="am-table am-table-bd am-table-bdrs am-table-striped am-table-hover">
<thead>
<tr>
<th>编号</th><th>姓名(字符)</th><th>年龄(数字)</th><th>描述(文本)</th><th>头像(文件)</th><th>类型(字典)</th><th>状态(字典)</th><th>金额(金额)</th><th>日期(日期)</th><th>日期(日期)</th><th>操作</th></tr>
</thead>
<tbody>
<c:forEach items="${map.rows}" var="row">
<tr>
<td>${row.id}</td><td>${row.name}</td><td>${row.age}</td><td>${row.memoText}</td><td>${row.imgUrl}</td><td><ideamov:text  code="DEMO_TYPE" key="${row.demoType}" /></td><td><ideamov:text  code="DEMO_STATE" key="${row.demoState}" /></td><td>${row.balanceMoney}</td><td><fmt:formatDate value="${row.birthdayDate}" pattern="yyyy-MM-dd"/></td><td><fmt:formatDate value="${row.createTime}" pattern="yyyy-MM-dd"/></td><td>
<div class="am-btn-toolbar">
<div class="am-btn-group-xs">
<button type="button" class="am-btn am-btn-secondary am-round"              onclick="myConfig.page.edit('${path}/demo/toEditDemo.action?demo.id=${row.id}&pager.num=${map.pager.num}&pmenu=${param.pmenu}&cmenu=${param.cmenu}')"> <span class="am-icon-pencil-square-o"></span> 编辑 </button>
<button type="button" class="am-btn am-btn-danger am-round" onclick="myConfig.page.edit('${path}/demo/deleteDemo.action?demo.id=${row.id}&pager.num=${map.pager.num}&pmenu=${param.pmenu}&cmenu=${param.cmenu}')"> <span class="am-icon-trash-o">        </span> 删除</button>
</div>
</div>
</td>
</tr>
</c:forEach>
</tbody>
</table>

<div class="am-cf">
共 ${map.pager.rowCount} 条记录
<div class="am-fr">
<ul class="am-pagination">
<li><a href="javascript:void(0)" onclick="myConfig.page.go('${path}/demo/toQueryDemo','1','${param.pmenu}','${param.cmenu}');"                  >第一页</a></li>
<li><a href="javascript:void(0)" onclick="myConfig.page.go('${path}/demo/toQueryDemo','${map.pager.prev}','${param.pmenu}','${param.cmenu}');"  >上一页</a></li>
<li><a href="javascript:void(0)" onclick="myConfig.page.go('${path}/demo/toQueryDemo','${map.pager.next}','${param.pmenu}','${param.cmenu}');"  >下一页</a></li>
<li><a href="javascript:void(0)" onclick="myConfig.page.go('${path}/demo/toQueryDemo','${map.pager.count}','${param.pmenu}','${param.cmenu}');" >最末页</a></li>
</ul>
</div>
</div>
</form>
</div>
</div>
</div>
</div>
<%-- 右部内容   -结束  --%>
</div>

<%-- 页面底部    --%><%@include file="../common/footer.jsp"%>

</body>
</html>

