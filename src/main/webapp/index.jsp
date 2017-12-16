<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- web路径：不以/开始的相对路径，找资源，以当前资源的路径为基准，经常容易出问题
以/开始的相对路径，找资源，以服务器的路径为标准（http://localhost:3306）,需要加上项目名
http://localhost:3306/crud
 -->
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%
	pageContext.setAttribute("APP_PATH", request.getContextPath());
%>
<script src="js/jquery-3.2.1.min.js"></script>
<link
	href="${APP_PATH}/static/bootstrap-3.3.7-dist/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="${APP_PATH}/static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
<title>员工列表</title>
</head>
<body>
	<!-- 员工修改的模态框 -->
	<div class="modal fade" id="emp_update_modal" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">员工修改</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal">
						<div class="form-group">
							<label for="empName" class="col-sm-2 control-label">员工姓名</label>
							<div class="col-sm-10">
								 <p class="form-control-static" id="empNameUpdate"></p>
								 <span id="helpBlock1" class="help-block"></span>
							</div>
						</div>
						<div class="form-group">
							<label for="emailInput" class="col-sm-2 control-label">邮箱</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="emailUpdate"
									name="email" placeholder=""> <span id="helpBlock2"
									class="help-block"></span>
							</div>
						</div>
						<div class="form-group">
							<label for="genderInput" class="col-sm-2 control-label">性别</label>
							<div class="col-sm-10">
								<label class="radio-inline"> <input type="radio"
									name="gender" id="updategender1" value="M" checked="checked">
									男
								</label> <label class="radio-inline"> <input type="radio"
									name="gender" id="updategender2" value="W"> 女
								</label>
							</div>
						</div>
						<div class="form-group">
							<label for="deptNameInput" class="col-sm-2 control-label">所属部门</label>
							<div class="col-sm-3">
								<select class="form-control" id="deptsUpdateSelect" name="dId">
								</select>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="empUpdate">更新</button>
				</div>
			</div>
		</div>
	</div>
	<!-- 员工新增的模态框 -->
	<div class="modal fade" id="emp_add_modal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">员工新增</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal">
						<div class="form-group">
							<label for="empName" class="col-sm-2 control-label">员工姓名</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="empNameInput"
									name="empName" placeholder="Lisa"> <span
									id="helpBlock1" class="help-block"></span>
							</div>
						</div>
						<div class="form-group">
							<label for="emailInput" class="col-sm-2 control-label">邮箱</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="emailInput"
									name="email" placeholder="email@qq.com"> <span
									id="helpBlock2" class="help-block"></span>
							</div>
						</div>
						<div class="form-group">
							<label for="genderInput" class="col-sm-2 control-label">性别</label>
							<div class="col-sm-10">
								<label class="radio-inline"> <input type="radio"
									name="gender" id="gender1" value="M" checked="checked">
									男
								</label> <label class="radio-inline"> <input type="radio"
									name="gender" id="gender2" value="W"> 女
								</label>
							</div>
						</div>
						<div class="form-group">
							<label for="deptNameInput" class="col-sm-2 control-label">所属部门</label>
							<div class="col-sm-3">
								<select class="form-control" id="deptsAddSelect" name="dId">
								</select>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="empSave">提交</button>
				</div>
			</div>
		</div>
	</div>
	<!-- 搭建页面 -->
	<div class="container">
		<!-- 标题 -->
		<div class=row>
			<div class="col-md-12">
				<h1>SSM-CRUD</h1>
			</div>
		</div>
		<!-- 按钮 -->
		<div class=row>
			<div class="col-md-4 col-md-offset-10">
				<button type="button" class="btn btn-primary" id="emp_add">新增</button>
				<button type="button" class="btn btn-danger">删除</button>
				<!--<button type="button" class="btn btn-primary" id="baidu">百度</button>-->
			</div>
		</div>
		<!-- 显示表格数据 -->
		<div class=row>
			<div class="col-md-12">
				<table class="table table-hover" id="table_emps">
					<thead>
						<tr>
							<th>id</th>
							<th>empName</th>
							<th>gender</th>
							<th>email</th>
							<th>department</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
		</div>
		<!-- 显示分页信息 -->
		<div class=row>
			<!-- 分页文字信息 -->
			<div class="col-md-6" id="page_info_area"></div>
			<!-- 分页条信息 -->
			<div class="col-md-6" id="page_nav_area"></div>
		</div>
	</div>
	<script type="text/javascript">
		var totalRecords,currentPage;
		$(function() {
			//去首页
			toPage(1);
		});
		function toPage(pn) {
			$.ajax({
				url : "${APP_PATH}/emps",
				data : "pn=" + pn,
				type : "GET",
				success : function(result) {
					/**
					接收服务器返回的json数据用js将json数据解析出来显示在页面
					1.显示员工信息 2.显示分页信息3.显示分页条信息
					 */
					build_emps_table(result);
					build_page_info(result);
					build_page_nav(result);
				}
			});
		}
		function build_emps_table(result) {
			$("#table_emps tbody").empty();
			var emps = result.extend.pageInfo.list;
			$.each(emps, function(index, item) {
				var empIdTd = $("<td></td>").append(item.empId);
				var empNameTd = $("<td></td>").append(item.empName);
				var genderTd = $("<td></td>").append(
						item.gender == "M" ? "男" : "女");
				var emailTd = $("<td></td>").append(item.email);
				var departmentNameTd = $("<td></td>").append(
						item.department.deptName);
				var editBtnTd = $("<button></button>").addClass(
						"btn btn-primary btn-sm edit_btn").append(
						"<span></span>").addClass("glyphicon glyphicon-pencil")
						.append("编辑");
				editBtnTd.attr("edit-id",item.empId);
				var delBtnTd = $("<button></button>").addClass(
						"btn btn-danger btn-sm").append("<span></span>")
						.addClass("glyphicon glyphicon-remove del_btn").append(
								"删除");
				var btnTd = $("<td></td>").append(editBtnTd).append(" ")
						.append(delBtnTd);
				$("<tr></tr>").append(empIdTd).append(empNameTd).append(
						genderTd).append(emailTd).append(departmentNameTd)
						.append(btnTd).appendTo("#table_emps tbody");
			});
		}
		//显示分页信息
		function build_page_info(result) {
			$("#page_info_area").empty();
			var page = result.extend.pageInfo;
			$("#page_info_area").append(
					"当前" + page.pageNum + "页，总" + page.pages + "页，总"
							+ page.total + "条记录。");
			totalRecords = page.total;
			currentPage= page.pageNum;
		}
		//显示分页条信息
		function build_page_nav(result) {
			$("#page_nav_area").empty();
			var ul = $("<ul></ul>").addClass("pagination");
			var firstPageLi = $("<li></li>").append(
					$("<a></a>").append("首页").attr("href", "#"));
			var laststPageLi = $("<li></li>").append(
					$("<a></a>").append("末页").attr("href", "#"));
			var prePageLi = $("<li></li>").append(
					$("<a></a>").append("&laquo;"));
			if (result.extend.pageInfo.hasPreviousPage == false) {
				firstPageLi.addClass("disabled");
				prePageLi.addClass("disabled");
			} else {
				firstPageLi.click(function() {
					toPage(1);
				});
				prePageLi.click(function() {
					toPage(result.extend.pageInfo.pageNum - 1);
				});
			}
			var nextPageLi = $("<li></li>").append(
					$("<a></a>").append("&raquo;"));
			if (result.extend.pageInfo.hasNextPage == false) {
				nextPageLi.addClass("disabled");
				laststPageLi.addClass("disabled");
			} else {
				nextPageLi.click(function() {
					toPage(result.extend.pageInfo.pageNum + 1);
				});
				laststPageLi.click(function() {
					toPage(result.extend.pageInfo.pages);
				});
			}
			ul.append(firstPageLi).append(prePageLi);
			$.each(result.extend.pageInfo.navigatepageNums, function(index,
					item) {
				var numLi = $("<li></li>").append($("<a></a>").append(item));
				if (result.extend.pageInfo.pageNum == item) {
					numLi.addClass("active");
				}
				numLi.click(function() {
					toPage(item);
				});
				ul.append(numLi);
			});
			ul.append(nextPageLi).append(laststPageLi);
			var nav = $("<nav></nav>");
			nav.append(ul);
			$("#page_nav_area").append(nav);
		}
		function reset_form(ele) {
			//清除表单内容
			$(ele)[0].reset();
			//清空表单样式
			$(ele).removeClass("has-error has-success");
			$(ele).find(".help-block").text("");
		}
		//点击新增按钮弹出模态框
		$("#emp_add").click(
				function() {
					reset_form("#emp_add_modal form");
					getDepts("#deptsAddSelect");
					$('#emp_add_modal').modal({
						backdrop : 'static'
					});
				});
		//查询部门信息将部门信息显示在下拉框中
		function getDepts(ele){
			$(ele).empty();
			//在弹出模态框之前查询出部门信息将其显示在下拉列表中
			$.ajax({
				url : "${APP_PATH}/deptNameSelect",
				type : "GET",
				success : function(result) {
					console.log(result);
					//将传回的json数据解析出来显示在下拉框中
					$.each(result.extend.depts, function() {
						var deptOption = $("<option></option>").append(this.deptName).attr("value",this.deptId);
						deptOption.appendTo(ele);
					});
				}
			});
		}
		//表单校验
		function validate_add_form() {
			//校验姓名
			var empName = $("#empNameInput").val();
			var validateEmpName = /(^[a-zA-Z0-9_-]{6,16}$)|(^[\u2E80-\u9FFF]{2,5})/;
			if (!validateEmpName.test(empName)) {
				//alert("用户名可以说2-5位中文或者6-16位数字字母组合");
				//$("#empNameInput").parent().addClass("has-error");
				//$("#empNameInput").next("span").text("用户名可以说2-5位中文或者6-16位数字字母组合!");
				validate_msg("#empNameInput", "fail",
						"用户名可以是2-5位中文或者6-16位数字字母组合!");
				return false;
			} else {
				//$("#empNameInput").parent().addClass("has-success");
				//$("#empNameInput").next("span").text("");
				validate_msg("#empNameInput", "success", "");
			}
			//校验邮箱
			var email = $("#emailInput").val();
			var validateEmail = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
			if (!validateEmail.test(email)) {
				//alert("邮箱格式不正确！");
				//$("#emailInput").parent().addClass("has-error");
				//$("#emailInput").next("span").text("邮箱格式不正确！");
				validate_msg("#emailInput", "fail", "邮箱格式不正确,请重新输入");
				return false;
			} else {
				//$("#emailInput").parent().addClass("has-success");
				//$("#emailInput").next("span").text("");
				validate_msg("#emailInput", "success", "");
			}
			return true;
		}
		function validate_msg(ele, status, msg) {
			$(ele).parent().removeClass("has-success has-error");
			$(ele).next("span").text("");
			if (status == "success") {
				$(ele).parent().addClass("has-success");
				$(ele).next("span").text(msg);
			} else {
				$(ele).parent().addClass("has-error");
				$(ele).next("span").text(msg);
			}
		}
		$("#empSave")
				.click(
						function() {
							//校验表单中的数据
							if (!validate_add_form()) {
								return false;
							}
							//1.判断之前的ajax用户名校验是否成功
							if ($("#empNameInput").attr("ajax-valite") == "fail") {
								return false;
							}
							$
									.ajax({
										url : "${APP_PATH}/emp",
										type : "POST",
										data : $("#emp_add_modal form")
												.serialize(),
										success : function(result) {
											//alert(result.msg);
											//保存成功之后要关闭模态框并且跳到查询页面的最后一页
											//alert($("#emp_add_modal form").serialize());
											if (result.code == 100) {
												$("#emp_add_modal").modal(
														'hide');
												toPage(totalRecords);
											} else {
												//console.log(result);
												//哪个字段错误就显示哪个信息
												if (undefined != result.extend.errorFields.email) {
													//显示邮箱错误信息
													validate_msg("#emailInput",
															"fail",
															"邮箱格式不正确,请重新输入");
												}
												if (undefined != result.extend.errorFields.empName) {
													//显示用户名错误信息
													validate_msg(
															"#empNameInput",
															"fail",
															"用户名必须是2-5位中文或者6-16位数字字母组合!");
												}
											}
										}
									});
						});
		//检验数据库中是否存在此姓名检验用户名是否可用
		$("#empNameInput").change(
				function() {
					var empName = this.value;
					$.ajax({
						url : "${APP_PATH}/checkUser",
						type : "POST",
						data : "empName=" + empName,
						success : function(result) {
							if (result.code == 100) {
								validate_msg("#empNameInput", "success",
										"用户名可用");
								$("#empNameInput").attr("ajax-valite",
										"success");
							} else {
								validate_msg("#empNameInput", "fail",
										result.extend.va_msg);
								$("#empNameInput").attr("ajax-valite", "fail");
							}
						}
					});
				});
		//点击编辑按钮弹出修改员工数据的模态框
		//如果直接使用之前的方法绑定click事件，因为我们是在按钮创建之前就绑定了事件所以绑定不上
		//解决办法：1.在按钮创建的时候绑定事件2.使用jquery提供的on方法
		$(document).on("click", ".edit_btn", function() {
			getDepts("#deptsUpdateSelect");
			getEmps($(this).attr("edit-id"));
			//把员工的id传给更新的按钮
			$("#empUpdate").attr("edit-id",$(this).attr("edit-id"));
			$('#emp_update_modal').modal({
				backdrop:'static'
			})
		});
		function getEmps(empId){
			//alert(1);
			$.ajax({
				url:"${APP_PATH}/empUpdate/"+empId,
				type:"GET",
				success:function(result){
					//console.log(result);
					$("#empNameUpdate").append(result.extend.emp_info.empName);
					//jquery向input框里添加内容
					$("#emailUpdate").val(result.extend.emp_info.email);
					
				}
			});
		}
		//点击更新，更新员工数据
		$("#empUpdate").click(function(){
			//验证邮箱格式是否合法
			//校验邮箱
			var email = $("#emailUpdate").val();
			var validateEmail = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
			if (!validateEmail.test(email)) {
				validate_msg("#emailUpdate", "fail", "邮箱格式不正确,请重新输入");
				return false;
			} else {
				validate_msg("#emailUpdate", "success", "");
			}
			//发送ajax请求更新员工数据
			$.ajax({
				url:"${APP_PATH}/empUpdateAndSave/"+$(this).attr("edit-id"),
				type:"PUT",
				data:$("#emp_update_modal form").serialize(),
				success:function(result){
					//alert(result.msg);
					$("#emp_update_modal").modal("hide");
					toPage(currentPage);
				}
			});
		});
	</script>
</body>
</html>