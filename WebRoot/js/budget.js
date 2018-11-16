var moduleCode = '06002';
var bean = {};
function initFun() {
	if (secure.find) {
		findMonth();
		findListInfo();
		dialog = BootstrapDialog.loading();
	}
	if(!secure.add) $('button.add-btn').remove();
	if(secure.add ) $('button.add-btn').removeClass('hide');
}
$(function() {
	$('.search-select, .dropdown-menu').on('click', function(e) {
		$target = $(e.target);
		var searchBtn = $('button.search-btn');
		searchBtn.text($target.text());
		searchBtn.append("<span class='caret'></span>");
		searchBtn.attr('name', $target.attr('name'));
	});
	$('select.select-department').change(function(e){
		findPosition($(this).val());
	});
});


function findListInfo() {
	var budgetName = $('input.searchInput').val();
	var month = $('input.querymonth').val().substring(0,7); 
	var totalMoney = 0;
	var totalLeft = 0;
	var counts = 0;
	$.post('./mgr/budget/list', {
		descp:budgetName,
		month:month
	}, function(data) {
		var tbody = $('tbody.tbody-info').empty();
		dialog.close();
		if(!$.isSuccess(data)) return;
		$.each(data.body, function(i,v){
			var color = "";
			if(v.leftje<v.je*0.1){
				color = "class='danger'";
			}else if(v.leftje<v.je*0.5){
				color = "class='warning'";
			}
			$("<tr "+color+" ></tr>")
			.append($("<td></td>").append(v.id))
			.append($("<td></td>").append(v.year+'年'+v.month+'月'))
			.append($("<td></td>").append(v.descp))
			.append($("<td></td>").append(v.consumeNumber))
			.append($("<td></td>").append("￥"+v.je.toFixed(2)))
			.append($("<td></td>").append("￥"+v.useJe.toFixed(2)))
			.append($("<td></td>").append("￥"+v.leftje.toFixed(2)))
			.append($("<td></td>").append(v.mtime))
			.append($("<td></td>").append(v.detail))
			.append($("<td></td>").append(analysisBtns(v)))
			.appendTo(tbody);
			totalMoney = totalMoney+v.je;
			totalLeft = totalLeft+v.leftje;
			counts = counts+v.consumeNumber;
		});
		$("<tr></tr>")
		.append($("<td></td>").append(""))
		.append($("<td></td>").append(""))
		.append($("<td></td>").append("总计"))
		.append($("<td></td>").append(counts))
		.append($("<td></td>").append("￥"+totalMoney.toFixed(2)))
		.append($("<td></td>").append("￥"+(totalMoney-totalLeft).toFixed(2)))
		.append($("<td></td>").append("￥"+totalLeft.toFixed(2)))
		.append($("<td></td>").append("" ))
		.append($("<td></td>").append('本月已花费￥:'+(totalMoney-totalLeft).toFixed(2)))
		.append($("<td></td>").append(""))
		.appendTo(tbody);
	}, 'json');
}




function findAccountType(curIndex, eml){
	eml.empty()
	.append("<option "+analyzeSelect(1,curIndex) +" value=0>储蓄型</option>")
	.append("<option "+analyzeSelect(2,curIndex) +" value=1>信用型</option>");
}

function analyzeSelect(id, curIndex){
	return curIndex > 0 && id == curIndex ? " selected=true " : "" ;
}


/*
 * 给当前月份赋值 Ryan
 */
function findMonth(){
	var date = new Date();
	var month = date.getMonth()<9 ?"0"+(date.getMonth()+1):(date.getMonth()+1);
	var str = date.getFullYear()+'-'+month;
	$('input.querymonth').val(str);
	
}
/*
 * 解析按钮组 te5l.com [K]
 */
function analysisBtns(v){
	var btns = "";
	btns += secure.modify ? "<button type='button' class='btn btn-primary btn-xs' onclick='showModifyBox("+v.id+")'><span class='glyphicon glyphicon-pencil'></span>编辑</button>" : "" ;
	btns += secure.modify ? "<button type='button' class='btn btn-warning btn-xs' onclick='showRecordBox("+v.id+")'><span class='glyphicon glyphicon glyphicon-time'></span>日志</button>" : "" ;
	btns += secure.modify ? "<button type='button' class='btn btn-danger btn-xs' onclick='deleteBudget("+v.id+")'><span class='glyphicon glyphicon-trash'></span>删除</button>" : "" ;
	return btns;
}

function showAddBox(){
	$('.empty').removeClass('empty');
	$('input.desc-add-box').val("");
	$('input.je-modify-box').val("");
	$('textarea.desc-detail-box').val("");

	BootstrapDialog.showModel($('div.add-budget-box'));
}

function showModifyBox(id){
	$('.empty').removeClass('empty');
	if(!id) return;
	dialog = BootstrapDialog.loading();
	$.getJSON('./mgr/budget/getBudgetById', {id:id}, function(data){
		dialog.close();
		if(!$.isSuccess(data)) return;
		bean.id = data.body.id;
		
		$('input.desc-modify-box').val(data.body.descp);
		$('input.je-modify-box').val(data.body.je);
		$('textarea.detail-modify-box').val(data.body.detail);
		BootstrapDialog.showModel($('div.modify-budget-box'));
	});
}

function buildBean(){
	bean.descp = $.verifyForm($('input.desc-add-box'), true);
	var date = new Date();
	bean.year = date.getFullYear();
	bean.month = date.getMonth()+1;
	bean.je = $.verifyForm($('input.je-add-box'), true);
	bean.leftje = bean.je;
	bean.status = 0;
	bean. n1 =2;
	bean. n2=3;
	bean. c1 = 'ss';
	bean. c2 = 'dd';
	bean.detail = $.verifyForm($('textarea.detail-add-box'), true);
}
function buildModifyBean(){
	bean.descp = $.verifyForm($('input.desc-modify-box'), true);
	bean.je = $.verifyForm($('input.je-modify-box'), true);
	bean.status = 0;
	bean. n1 =2;
	bean. n2=3;
	bean. c1 = 'ss';
	bean. c2 = 'dd';
	bean.detail = $.verifyForm($('textarea.detail-modify-box'), true);;
}
function save(){ 
	buildBean();
	dialog = BootstrapDialog.isSubmitted();
	$.post('./mgr/budget/save',{data : JSON.stringify(bean)}, function(data){
		dialog.close();
		if(!$.isSuccess(data)) return;
		BootstrapDialog.show({
			title:"提示信息",
			type:BootstrapDialog.TYPE_SUCCESS,
			closable : true,
			message : "新的预算保存成功!",
			onhidden : function(dialogRef) {
				window.location.href = "budget.html";
			}
		});
	}, 'json');
};


function modify(){ 
	buildModifyBean();
	dialog = BootstrapDialog.isSubmitted();
	$.post('./mgr/budget/modify',{data : JSON.stringify(bean)}, function(data){
		dialog.close();
		if(!$.isSuccess(data)) return;
		BootstrapDialog.show({
			title:"提示信息",
			type:BootstrapDialog.TYPE_SUCCESS,
			closable : true,
			message : "预算修改成功!",
			onhidden : function(dialogRef) {
				window.location.href = "budget.html";
			}
		});
	}, 'json');
};


function deleteBudget(id){
	if(!id) return;
	BootstrapDialog.confirm('确定要删除此预算吗？', function(result){
		if(!result) return;
		dialog = BootstrapDialog.loading();
		$.getJSON('./mgr/budget/delete', {id : id}, function(data){
			dialog.close();
			if(!$.isSuccess(data)) return;
			BootstrapDialog.msg(data.body, BootstrapDialog.TYPE_SUCCESS);
			findListInfo();
		});
	});
}


/*
 * 显示消费变更记录
 */
function showRecordBox(id) {
	if (!id)
		return;
	$.getJSON('./mgr/budget/getBudgetLogByBudgetId', {
		budgetId : id
	}, function(data) {
		if (!$.isSuccess(data))
			return;
		BootstrapDialog.showModel($('div.log-budget-box'));
		var tbody = $('tbody.table-log-budget-box').empty();
		$.each(data.body, function(i, v) {
			$('<tr></tr>').append($('<td></td>').append(i)).append(
					$('<td></td>').append((v.je+v.leftje).toFixed(2))).append(
					$('<td></td>').append(-v.je)).append(
					$('<td></td>').append(v.leftje)).append(
					$('<td></td>').append(v.consumeid)).append(
					$('<td></td>').append(v.account_nickname)).append(	
					$('<td></td>').append(v.ctime)).append(
					$('<td></td>').append(v.descp)).appendTo(tbody);
		});
	});
}

