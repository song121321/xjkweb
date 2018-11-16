var moduleCode = '06001';
var bean = {};
function initFun() {
	if (secure.find) {
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


/*
 * 获取银行列表信息
 * 
 */
function findListInfo() {
	var zhName = $('input.searchInput').val();
	$.post('./mgr/bank/banks', {
		zhName:zhName
	}, function(data) {
		var tbody = $('tbody.tbody-info').empty();
		dialog.close();
		if(!$.isSuccess(data)) return;
		$.each(data.body, function(i,v){
			$("<tr></tr>")
			.append($("<td></td>").append(v.id))
			.append($("<td></td>").append(v.descp))
			.append($("<td></td>").append(v.fromBankName))
			.append($("<td></td>").append(v.account.nickname))
			.append($("<td></td>").append(v.accounttype==0?'储蓄型':'信用型'))
			.append($("<td></td>").append(v.je))
			.append($("<td></td>").append(v.ctime))
			.append($("<td></td>").append(v.detail))
			.append($("<td></td>").append(analysisBtns(v)))
			.appendTo(tbody);
		});
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
 * 获取银行卡
 * Ryan
 */
function findBank(curIndex, eml){
	eml.empty().append("<option value=0>请选择来源账户</option>");
	$.post('./mgr/bank/banks', {
		zhName : ''
	}, function(data) {
		if(!$.isSuccess(data)) return;
		$.each(data.body, function(i,v){
			$("<option "+analyzeSelect(v.id,curIndex) +" value="+v.id+"></option>")
			.append(v.descp)
			.appendTo(eml);
		});
	}, 'json');
}

/*
 * 获取银行卡 Ryan
 */
function findAccount(curIndex, eml){
	
	eml.empty().append("<option value=0>请选择所属用户</option>");
	
	$.post('mgr/account/findAccountList', {
		page : 1,
		searchValue : ''
	}, function(data) {
		if(!$.isSuccess(data)) return;
		$.each(data.body, function(i,v){
			$("<option "+analyzeSelect(v.id,curIndex) +" value="+v.id+"></option>")
			.append(v.nickname)
			.appendTo(eml);
		});
	}, 'json');
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
	btns += secure.modify ? "<button type='button' class='btn btn-danger btn-xs' onclick='deleteBank("+v.id+")'><span class='glyphicon glyphicon-trash'></span>删除</button>" : "" ;
	return btns;
}

function showAddBox(){
	$('.empty').removeClass('empty');
	$('input.desc-add-box').val("");
	$('input.je-modify-box').val("");
	$('textarea.desc-detail-box').val("");
	findBank(0, $('select.bank-add-box'));
	findAccount(0, $('select.account-add-box'));
	findAccountType(0, $('select.account-type-add-box'));
	BootstrapDialog.showModel($('div.add-bank-box'));
}

function showModifyBox(id){
	$('.empty').removeClass('empty');
	if(!id) return;
	dialog = BootstrapDialog.loading();
	$.getJSON('./mgr/bank/getBank', {id:id}, function(data){
		dialog.close();
		if(!$.isSuccess(data)) return;
		bean.id = data.body.id;
		findAccount(data.body.account.id, $('select.account-modify-box'));
		findAccountType(data.body.accounttype, $('select.account-add-modify-box'));
		$('input.desc-modify-box').val(data.body.descp);
		$('input.je-modify-box').val(data.body.je);
		$('textarea.detail-modify-box').val(data.body.detail);
		BootstrapDialog.showModel($('div.modify-bank-box'));
	});
}

function buildBean(){
	bean.descp = $.verifyForm($('input.desc-add-box'), true);
	bean.accountid = $.verifyForm($('select.account-add-box'), true);
	bean.fromBankId = $.verifyForm($('select.bank-add-box'), true);
	bean.accounttype = $.verifyForm($('select.account-type-add-box'), true);
	bean.currencyid = 1;
	bean.je = $.verifyForm($('input.je-add-box'), true);
	bean.status = 0;
	bean. n1 =2;
	bean. n2=3;
	bean. c1 = 'ss';
	bean. c2 = 'dd';
	bean.detail = $.verifyForm($('textarea.detail-add-box'), true);;
}
function buildModifyBean(){
	bean.descp = $.verifyForm($('input.desc-modify-box'), true);
	bean.accountid = $.verifyForm($('select.account-modify-box'), true);
	bean.accounttype = $.verifyForm($('select.account-type-modify-box'), true);
	bean.currencyid = 1;
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
	$.post('./mgr/bank/save',{data : JSON.stringify(bean)}, function(data){
		dialog.close();
		if(!$.isSuccess(data)) return;
		var dlg = BootstrapDialog.show({
			title:"提示信息",
			type:BootstrapDialog.TYPE_SUCCESS,
			closable : true,
			message : "新的支付账户保存成功!",
			onhidden : function(dialogRef) {
				window.location.href = "bank.html";
			}
		});
		setTimeout(function(){
			dlg.close();
		},200);
	}, 'json');
};


function modify(){ 
	buildModifyBean();
	dialog = BootstrapDialog.isSubmitted();
	$.post('./mgr/bank/modify',{data : JSON.stringify(bean)}, function(data){
		dialog.close();
		if(!$.isSuccess(data)) return;
		var dlg = BootstrapDialog.show({
			title:"提示信息",
			type:BootstrapDialog.TYPE_SUCCESS,
			closable : true,
			message : "支付账户修改成功!",
			onhidden : function(dialogRef) {
				window.location.href = "bank.html";
			}
		});
		setTimeout(function(){
			dlg.close();
		},200);
	}, 'json');
};


function deleteBank(id){
	if(!id) return;
	BootstrapDialog.confirm('确定要注销此支付账户吗？', function(result){
		if(!result) return;
		dialog = BootstrapDialog.loading();
		$.getJSON('./mgr/bank/delete', {id : id}, function(data){
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
	$.getJSON('./mgr/bank/getBankLogByBankId', {
		bankId : id
	}, function(data) {
		if (!$.isSuccess(data))
			return;
		BootstrapDialog.showModel($('div.log-bank-box'));
		var tbody = $('tbody.table-log-bank-box').empty();
		$.each(data.body, function(i, v) {
			$('<tr></tr>').append($('<td></td>').append(i)).append(
					$('<td></td>').append(v.beforeje)).append(
					$('<td></td>').append((-v.je).toFixed(2))).append(
					$('<td></td>').append(v.leftje)).append(
					$('<td></td>').append(v.account_nickname)).append(	
					$('<td></td>').append(v.ctime)).append(
					$('<td></td>').append(v.descp)).appendTo(tbody);
		});
	});
}

