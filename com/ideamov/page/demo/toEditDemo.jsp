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
<form class="am-form am-form-horizontal"  method="post" action="${path}/demo/editDemo.action"  data-am-validator >

<input type="hidden"  name="backUrl"  value="${backUrl }" >

<div class="admin-content-body"><div class="am-cf am-padding am-padding-bottom-0"><div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">edit Demo</strong></div></div>

<hr />

<div>

<div class="am-u-sm-12 am-u-md-4 am-u-md-push-8" style="display: none;" > <div class="am-panel am-panel-default"> <div class="am-panel-bd"> <div class="am-g">  </div> </div></div></div>

<div class="am-u-sm-12 am-u-md-8 am-u-md-pull-4">

<div style="display: none;"  class="am-form-group"> <label for="user-name" class="am-u-sm-3 am-form-label">id </label><div class="am-u-sm-9" style="margin-top: 5px;"><input type="text"   name="demo.id" placeholder=""  value="${demo.id}"  ></div></div>
<div class="am-form-group"> <label for="user-name" class="am-u-sm-3 am-form-label">姓名(字符) </label><div class="am-u-sm-9" style="margin-top: 5px;"><input type="text"   name="demo.name" placeholder=""  value="${demo.name}" maxlength="32" required ></div></div>
<div class="am-form-group"> <label for="user-name" class="am-u-sm-3 am-form-label">年龄(数字) </label><div class="am-u-sm-9" style="margin-top: 5px;"><input type="text"   name="demo.age" placeholder=""  value="${demo.age}"    pattern="^\d{1,11}$" maxlength="11" required ></div></div>
<div class="am-form-group"> <label for="user-name" class="am-u-sm-3 am-form-label">描述(文本) </label><div class="am-u-sm-9" style="margin-top: 5px;"> <textarea rows="10" id="demo_memoText"  name="demo.memoText" style="style="width: 105%;height: 300px"" > ${demo.memoText} </textarea> </div></div>
<div class="am-form-group"> <label for="user-name" class="am-u-sm-3 am-form-label">头像(文件) </label><div class="am-u-sm-9" style="margin-top: 5px;">  <input id="up_demo_imgUrl" type="file" name="file" class="am-input-sm" accept=".jpg,.png" style="margin-top: 11px;" form="upFrom_demo_imgUrl"> <input id="upInput_demo_imgUrl" type="hidden" name="demo.imgUrl" value="${demo.imgUrl}"> </div></div>
<div class="am-form-group"> <label for="user-name" class="am-u-sm-3 am-form-label"> </label><div class="am-u-sm-9" style="margin-top: 5px;">  <img id="upImage_demo_imgUrl" style="width: 300px; height: 200px" src="${demo.imgUrl}" alt="无图片显示"> </div></div>
<div class="am-form-group"> <label for="user-name" class="am-u-sm-3 am-form-label">类型(字典) </label><div class="am-u-sm-9" style="margin-top: 5px;">    <ideamov:select  code="DEMO_TYPE"  name="demo.demoType" key="${demo.demoType}" /> </div></div>
<div class="am-form-group"> <label for="user-name" class="am-u-sm-3 am-form-label">状态(字典) </label><div class="am-u-sm-9" style="margin-top: 5px;">    <ideamov:select  code="DEMO_STATE"  name="demo.demoState" key="${demo.demoState}" /> </div></div>
<div class="am-form-group"> <label for="user-name" class="am-u-sm-3 am-form-label">金额(金额) </label><div class="am-u-sm-9" style="margin-top: 5px;"><input type="text"   name="demo.balanceMoney" placeholder=""  value="${demo.balanceMoney}"  pattern="^\d+(\.\d{1,6})?$" maxlength="11" required ></div></div>
<div class="am-form-group"> <label for="user-name" class="am-u-sm-3 am-form-label">日期(日期) </label><div class="am-u-sm-9" style="margin-top: 5px;"><input type="text"   name="demo.birthdayDate" placeholder=""  value="<fmt:formatDate value="${demo.birthdayDate}" pattern="yyyy-MM-dd"/>" data-am-datepicker readonly required ></div></div>

<div class="am-form-group"> <div class="am-u-sm-6 am-u-sm-push-6"><button type="submit" class="am-btn am-btn-primary">提交保存</button></div></div>
</div>
</div>
</div>
</form>
<form action="${path}/demo/upload" method="post" id="upFrom_demo_imgUrl" enctype="multipart/form-data"></form>
</div>

<%-- 右部内容   -结束  --%>
</div>

<%-- 页面底部    --%><%@include file="../common/footer.jsp"%>
<!-- UM配置文件、编辑器源码文件、样式 -->
<link href="${path }/res/assets/umeditor/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${path }/res/assets/umeditor/third-party/template.min.js"></script>
<script type="text/javascript" src="${path }/res/assets/umeditor/umeditor.config.js" charset="utf-8"></script>
<script type="text/javascript" src="${path }/res/assets/umeditor/umeditor.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${path }/res/assets/umeditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript"> function ueEditorInit(obj) { var editor = UM.getEditor(obj); } </script>
<script type="text/javascript"> $(function() { ueEditorInit('demo_memoText'); }); </script>
<script type="text/javascript"> $("#up_demo_imgUrl").change(function() { myConfig.file.upload("upFrom_demo_imgUrl", "upInput_demo_imgUrl", "upImage_demo_imgUrl"); }); </script>

</body>
</html>

