var moduleCode = '05002';
var position = {};

function initFun() {
	if(secure.find){
		dialog = BootstrapDialog.loading();
		findListInfo();
	}
	if(!secure.add)
		$('button.add-btn').remove();
	if(secure.add)
		$('button.add-btn').removeClass('hide');
}
/*
 * 获取职位列表数据
 * te5l.com [K]
 */
function findListInfo() {
	var typeName = $('input.searchInput').val();
	$.post('mgr/consume/consume-type/list', {
		typeName:typeName
	}, function(data){
		dialog.close();
		var tbody = $('tbody.tbody').empty();
		if(!$.isSuccess(data)) return;
		$.each(data.body, function(i, v){
			$("<tr></tr>")
			.append($("<td></td>").append("<img src="+v.icon+" height=20 width=20 />" ))
			.append($("<td></td>").append(v.descp))
			.append($("<td></td>").append(v.mtime))
			.append($("<td></td>").append(v.accountNickName))
			.append($("<td></td>").append(v.detail))
			.append($("<td></td>").append(analyzeBtns(v)))
			.appendTo(tbody);
		});
	}, 'json');
	
}
/*
 * 解析操作按钮
 * te5l.com [K]
 */
function analyzeBtns(v){
	var btns = "";
	btns += secure.del ? "<button type='button' class='btn btn-danger btn-xs' onclick='hintDelete("+v.id+")'><span class='glyphicon glyphicon-remove'></span>删除</button>" : "" ;
	return btns;
}

function analyzeSelect(id, curDepartient){
	return curDepartient > 0 && id == curDepartient ? " selected=true " : "" ;
}
/*
 * 显示添加窗口
 * te5l.com [K]
 */
function showAddBox(){
	$('.empty').removeClass('empty');
	$('input.addName').val("");
	$('textarea.addDesc').val("");
	BootstrapDialog.showModel($('div.add-box'));
}
/*
 * 添加职位信息
 */
function addConsumeType(){
	$.isSubmit = true;
	var desc = $.verifyForm($('input.addName'), true);
	var detail = $.verifyForm($('textarea.addDesc'), false);
	if(!$.isSubmit) return;
	dialog = BootstrapDialog.isSubmitted();
	$.post('mgr/consume/consume-type/addConsumeType', {
		desp : desc,
		detail : detail
	}, function(data){
		dialog.close();
		if(!$.isSuccess(data)) return;
		BootstrapDialog.hideModel($('div.add-box'));
		BootstrapDialog.msg(data.body, BootstrapDialog.TYPE_SUCCESS);
		findListInfo();
	}, 'json');
}
/*
 * 提示并确定删除职位信息
 * te5l.com [K]
 */
function hintDelete(id){
	if(!id) return;
	BootstrapDialog.confirm("请确认是否删除该消费类型!", function(result){
		if(!result) return;
		dialog = BootstrapDialog.isSubmitted();
		$.getJSON('mgr/consume/consume-type/deleteConsumeType',{id : id}, function(data){
			if(!$.isSuccess(data)) return;
			dialog.close();
			BootstrapDialog.msg(data.body, BootstrapDialog.TYPE_SUCCESS);
			findListInfo();
		});
	});
}