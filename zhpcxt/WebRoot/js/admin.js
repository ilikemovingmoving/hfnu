﻿//用户登录
function login(path, closed) {
	$("body").append("<div id='dlg_login' style='overflow:hidden;'></div>");
}
//修改密码
function editpass(path) {
	$("body").append("<div id='dlg_update_pwd' style='padding:20px;'></div>");
	$('#dlg_update_pwd').dialog({
		href: path + '/admin/pwd_update.jsp',
		modal: true,
		closed: false,
		title: '修改密码',
		width: 310,
		buttons: [{
			text: '提交',
			iconCls: 'icon-ok',
			handler: function () {
				$('#form_pwd_update').form('submit', {
					url: path + '/PwdUpdateServlet',
					onSubmit: function () {
						return $(this).form('validate');
					},
					success: function (data) {
						if (data == "-1") {
							$.messager.alert('系统消息', '修改密码失败', 'error');
						} else {
							$.messager.alert('系统消息', '密码修改成功', 'info');
							$('#dlg_update_pwd').dialog('refresh');
							$('#dlg_update_pwd').dialog('close');
						}
					}
				});
			}
		}, {
			text: '重置',
			iconCls: 'icon-reload',
			handler: function () {
				$('#dlg_update_pwd').dialog('refresh');
			}
		}]
	});
}
//添加辅导员
function adminAdd(path) {
	$("body").append("<div id='dlg_systemuser_add' style='padding:20px;'></div>");
	$('#dlg_systemuser_add').dialog({
		href: path + '/admin/systemuser_add.jsp',
		modal: true,
		closed: false,
		title: '添加辅导员',
		width: 300,
		buttons: [{
			text: '提交',
			iconCls: 'icon-ok',
			handler: function () {
				$('#form_systemuser_add').form('submit', {
					url: path + '/AdminAddServlet',
					onSubmit: function () {
						return $(this).form('validate');
					},
					success: function (data) {
						if (data == "-1") {
							$.messager.alert('系统消息', '用户名已存在', 'error');
						} else {
							$.messager.alert('系统消息', '添加成功', 'info', function () {
								//$('#dlg_login').dialog('refresh');
								//$('#dlg_login').dialog('close');
								//$("#dlg_systemuser_add").dialog('close');
								location.href = path + '/admin/systemuser.jsp';
							}, true);
						}
					}
				});
			}
		}, {
			text: '重置',
			iconCls: 'icon-reload',
			handler: function () {
				$('#dlg_systemuser_add').dialog('refresh');
			}
		}]
	});
}
//新增班级
function classAdd(path) {
	$("body").append("<div id='dlg_class_add' style='padding:20px;'></div>");
	$('#dlg_class_add').dialog({
		href: path + '/admin/class_add.jsp',
		modal: true,
		closed: false,
		title: '新增班级',
		width: 300,
		height: 250,
		buttons: [{
			text: '提交',
			iconCls: 'icon-ok',
			handler: function () {
				$('#form_class_add').form('submit', {
					url: path + '/ClassAddServlet',
					onSubmit: function () {
						return $(this).form('validate');
					},
					success: function (data) {
						if (data == "-1") {
							$.messager.alert('系统消息', '用户名已存在', 'error');
						} else {
							$.messager.alert('系统消息', '添加成功', 'info');
							$('#dlg_class_add').dialog('refresh');
							$('#dlg_class_add').dialog('close');
							location.href = path + '/admin/allClassInfo.jsp';
						}
					}
				});
			}
		}, {
			text: '重置',
			iconCls: 'icon-reload',
			handler: function () {
				$('#dlg_class_add').dialog('refresh');
			}
		}]
	});
}
//新建课程
function courseAdd(path) {
	/*$('#showAllCourse').dialog('close');
	$('#showAllCourse').dialog('destory');*/
	$("body").append("<div id='dlg_course_add' style='padding:20px;'></div>");
	$('#dlg_course_add').dialog({
		href: path + '/admin/createCourse.jsp',
		modal: true,
		closed: false,
		title: '新建课程',
		width: 400,
		height: 200,
		buttons: [{
			text: '提交',
			iconCls: 'icon-ok',
			handler: function () {
				/*$('#dlg_flip').dialog('close');
				$('#dlg_flip').dialog('destroy');*/
				//$(".panel-tool-close").click();
				$('#from_course_add').form('submit', {
					url: path + '/CourseAddServlet',
					onSubmit: function () {
						return $(this).form('validate');
					},
					success: function (data) {
						if (data == "-1") {
							$.messager.alert('系统消息', '新建失败', 'error');
							//$.messager.alert('系统消息', '课程已存在', 'error');
						} else {
							$.messager.alert('系统消息', '添加成功', 'info', function () {
								$('#dlg_course_add').dialog('refresh');
								$('#dlg_course_add').dialog('close');
								//$('#dlg_login').dialog('refresh');
								//$('#dlg_login').dialog('close');
								//location.href = path + '/admin/allClassInfo.jsp';
							}, false);
						}
					}
				});
			}
		}, {
			text: '重置',
			iconCls: 'icon-reload',
			handler: function () {
				$('#dlg_course_add').dialog('refresh');
			}
		}]
	});
}
//列出未通过的课程信息
function showTheCourseJS(path, student_id, class_id) {
	//var schoolYear = document.getElementById("schoolYear").value;
	//var term = document.getElementById("term").value;
	$("body").append("<div id='dlg_flip' style='padding:20px;'></div>");
	$('#dlg_flip').dialog({
		href: path + '/admin/stuNoQualifiedCourse.jsp?student_id=' + student_id + '&class_id=' + class_id ,//+ '&school_year=' + schoolYear + '&term=' + term,
		modal: true,
		closed: false,
		title: '未通过课程',
		width: 400,
		top:'($(window).height() - this.height())/2',
	});
	$('.panel-tool-close').bind("click", function () {
		$('#dlg_flip').dialog('close');
		$('#dlg_flip').dialog('destroy');
	});
}
//展示学生在校学习情况
function showSituation(path,student_id, class_id) {
	//var schoolYear = document.getElementById("schoolYear").value;
	//var term = document.getElementById("term").value;
	$("body").append("<div id='dlg_situation' style='padding:20px;'></div>");
	$('#dlg_situation').dialog({
		href: path + '/secretary/situation.jsp?student_id='+student_id+"&class_id="+class_id,
		modal: true,
		closed: false,
		title: '学生在校学习情况',
		width: 400,
		top:'($(window).height() - this.height())/2',
	});
	$('.panel-tool-close').bind("click", function () {
		$('#dlg_situation').dialog('close');
		$('#dlg_situation').dialog('destroy');
	});
}
//所有课程
function showAllCourse(path) {
	//var schoolYear = document.getElementById("schoolYear").value;
	//var term = document.getElementById("term").value;
	$("body").append("<div id='showAllCourse' style='padding:20px;position:absolute;overflow:auto;'></div>");
	$('#showAllCourse').dialog({
		href: path + '/counsellor/allCourse.jsp',
		modal: true,
		closed: false,
		title: '选课',
		width: 400,
		height: 400,
		top:'($(window).height() - this.height())/2',
	});
	/*$('#dlg_flip').bind("click", function () {
		$('#dlg_flip').dialog('close');
		$('#dlg_flip').dialog('destroy');
	});*/
}
//更换辅导员
function changeCmgr(path) {
	$("body").append("<div id='dlg_change_cmgr' style='padding:20px;'></div>");
	$('#dlg_change_cmgr').dialog({
		href: path + '/admin/changeCmgr.jsp',
		modal: true,
		closed: false,
		title: '更换辅导员',
		width: 250,
		height: 200,
		buttons: [{
			text: '提交',
			iconCls: 'icon-ok',
			handler: function () {
				$('#from_change_cmgr').form('submit', {
					url: path + '/DelServlet',
					onSubmit: function () {
						return $(this).form('validate');
					},
					success: function (data) {
						if (data == "-1") {
							$.messager.alert('系统消息', '修改失败', 'error');
						} else {
							$.messager.alert('系统消息', '修改成功', 'info', function () {
								$('#dlg_login').dialog('refresh');
								$('#dlg_login').dialog('close');
								location.href = path + '/admin/classStuInfo.jsp';
							}, false);
						}
					}
				});
			}
		}, {
			text: '取消',
			iconCls: 'icon-cancel',
			handler: function () {
				$('#dlg_change_cmgr').dialog('close');
			}
		}]
	});
}
//分配班级给辅导员
function giveClassToMgr(path, mgr) {
	//alert(mgr);
	$("body").append("<div id='dlg_giveClassToMgr' style='padding:20px;text-align: left;'></div>");
	$('#dlg_giveClassToMgr').dialog({
		href: path + '/admin/giveClassToMgr.jsp?mgr=' + mgr,
		modal: true,
		closed: false,
		title: '分配班级',
		width: 350,
		height: 'auto',
		top:'($(window).height() - this.height())/2',
		resizable:true,
		buttons: [{
			text: '提交',
			iconCls: 'icon-ok',
			handler: function () {
				$('#from_giveClassToMgr').form('submit', {
					url: path + '/DelServlet',
					onSubmit: function () {
						return $(this).form('validate');
					},
					success: function (data) {
						if (data == "-1") {
							$.messager.alert('系统消息', '修改失败', 'error');
						} else {
							$.messager.alert('系统消息', '修改成功', 'info', function () {
								//$('#dlg_giveClassToMgr').dialog('refresh');
								$('#dlg_giveClassToMgr').dialog('close');
								location.href = path + '/admin/systemuser.jsp';
							}, false);
						}
					}
				});
			}
		}, {
			text: '取消',
			iconCls: 'icon-cancel',
			handler: function () {
				$('#dlg_giveClassToMgr').dialog('close');
			}
		}]
	});
}
//素质测评评分提交
function submit(path) {
	var daodesuyangPlus = parseInt(document.getElementById("daodesuyangPlus").value);
	var daodesuyangSub = parseInt(document.getElementById("daodesuyangSub").value);
	var studyingPlus = parseInt(document.getElementById("studyingPlus").value);
	var studyingSub = parseInt(document.getElementById("studyingSub").value);
	var suzhituozhanPlus = parseInt(document.getElementById("suzhituozhanPlus").value);
	var suzhituozhanSub = parseInt(document.getElementById("suzhituozhanSub").value);
	var doPlusOrSub = parseInt(document.getElementById("doPlusOrSub").value);
	if (isNaN(daodesuyangPlus) || isNaN(daodesuyangSub) || isNaN(studyingPlus) || isNaN(studyingSub) || isNaN(suzhituozhanPlus) || isNaN(suzhituozhanSub) || isNaN(doPlusOrSub)) {
		alert("请填写所有评分项");
	} else {
		$("body").append("<div id='dlg_submit' style='padding:20px;text-align: center;'></div>");
		$('#dlg_submit').dialog({
			href: path + '/counsellor/evaluatingSubmit.jsp?daodesuyangPlus=' + daodesuyangPlus
			+ '&daodesuyangSub=' + daodesuyangSub
			+ '&studyingPlus=' + studyingPlus
			+ '&studyingSub=' + studyingSub
			+ '&suzhituozhanPlus=' + suzhituozhanPlus
			+ '&suzhituozhanSub=' + suzhituozhanSub
			+ '&doPlusOrSub=' + doPlusOrSub,
			modal: true,
			closed: false,
			title: '素质综合测评评分',
			width: 350,
			buttons: [{
				text: '确定',
				iconCls: 'icon-ok',
				handler: function () {
					$('#suzhizeping').form('submit', {
						url: path + '/DelServlet',
						onSubmit: function () {
							return $(this).form('validate');
						},
						success: function (data) {
							if (data == "-1") {
								$.messager.alert('系统消息', '修改失败', 'error');
							} else {
								$.messager.alert('系统消息', '修改成功', 'info', function () {
									$('#dlg_submit').dialog('refresh');
									$('#dlg_submit').dialog('close');
									location.href = path + '/counsellor/studentInfoForRate.jsp';
								}, false);
							}
						}
					});
				}
			}, {
				text: '修改',
				iconCls: 'icon-cancel',
				handler: function () {
					$('#dlg_submit').dialog('close');
					$('#dlg_submit').dialog('destroy');
				}
			}]
		});
		$('.panel-tool-close').bind("click", function () {
			$('#dlg_submit').dialog('close');
			$('#dlg_submit').dialog('destroy');
		});
	}
}
//用户注销
function logout(path) {
	$.ajax({
		type: 'POST',
		url: path + '/RemoveServlet',
		data: 'mark=admin',
		success: function (msg) {
			window.location = (path + '/admin/index.jsp');
			
		}
	});
}
//解析JSON
function parseJson(text) {
	//extract JSON string
	var match;
	if ((match = /\{[\s\S]*\}|\[[\s\S]*\]/.exec(text))) {
		text = match[0];
	}
	var cx = /[\u0000\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g;
	cx.lastIndex = 0;
	if (cx.test(text)) {
		text = text.replace(cx, function (a) {
			return '\\u' + ('0000' + a.charCodeAt(0).toString(16)).slice(-4);
		});
	}
	if (/^[\],:{}\s]*$/.test(text.replace(/\\(?:["\\\/bfnrt]|u[0-9a-fA-F]{4})/g, '@').replace(/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g, ']').replace(/(?:^|:|,)(?:\s*\[)+/g, ''))) {
		return eval('(' + text + ')');
	}
	throw 'JSON parse error';
}
//上传文件
function upload(path) {
	$("body").append("<div id='dlg_upload' style='padding:20px;'></div>");
	$('#dlg_upload').dialog({
		href: path + '/admin/upload_file.jsp',
		modal: true,
		closed: false,
		title: '上传文件',
		width: 450,
		height: 150,
		closable: true,
		buttons: [{
			text: '上传',
			iconCls: 'icon-ok',
			handler: function () {
				$('#upload_file').form('submit', {
					url: path + '/admin/upload_json.jsp',
					onSubmit: function () {
						return $(this).form('validate');
					},
					success: function (data) {
						var json = parseJson(data);
						if (json.error === 1) {
							$.messager.alert('系统消息', json.message, 'error');
						} else {
							//window.navigate(path+'/index.jsp');
							$.messager.alert('系统消息', '上传成功', 'info', function () {
								$('#dlg_upload').dialog('refresh');
								$('#dlg_upload').dialog('close');
								$('#paths').val(json.url);
							}, false);
						}
					}
				});
			}
		}, {
			text: '重置',
			iconCls: 'icon-reload',
			handler: function () {
				$('#dlg_upload').dialog('refresh');
			}
		}]
	});
}