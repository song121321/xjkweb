var moduleCode = '07001';
var bean = {};
function initFun() {
	if (secure.find) {
		// findYear();
		findBank(0, $('select.bank-main'));
		findListInfo();
		dialog = BootstrapDialog.loading();
	}
	if (!secure.add)
		$('button.add-empl-btn').remove();
	if (secure.add)
		$('button.add-empl-btn').removeClass('hide');
}
$(function() {
	$('.search-select, .dropdown-menu').on('click', function(e) {
		$target = $(e.target);
		var searchBtn = $('button.search-btn');
		searchBtn.text($target.text());
		searchBtn.append("<span class='caret'></span>");
		searchBtn.attr('name', $target.attr('name'));
	});
	$('select.select-department').change(function(e) {
		findPosition($(this).val());
	});
});
/*
 * 获取收入列表信息
 */
function findListInfo() {
	var content = $('input.search-val').val();
	var bankId = $.verifyForm($('select.bank-main'), true);
	var currency_icontext = '';
	var totalMoney = 0;
	var counts = 0;
	$.getJSON('./mgr/income/list', {
		content : content,
		accId : 0,
		bankId : bankId,
		page : page
	},
			function(data) {
				var tbody = $('tbody.tbody-info').empty();
				dialog.close();
				if (!$.isSuccess(data))
					return;
				$.each(data.body, function(i, v) {
					$("<tr></tr>").append($("<td></td>").append(v.id)).append(
							$("<td></td>").append(v.descp)).append(
							$("<td></td>").append(v.accountName)).append(
							$("<td></td>").append(v.bankName)).append(
							$("<td></td>").append('￥' + v.je)).append(
							$("<td></td>").append(v.mtime)).append(
							$("<td></td>").append(v.mtime)).append(
							$("<td></td>").append(analysisBtns(v))).appendTo(
							tbody);
					currency_icontext = '￥';
					totalMoney = totalMoney + v.je;
					counts = counts + 1;
				});
				$("<tr></tr>").append($("<td></td>").append(counts)).append(
						$("<td></td>").append('本页总计')).append(
						$("<td></td>").append("")).append(
						$("<td></td>").append(''))
						.append(
								$("<td></td>").append(
										currency_icontext + ''
												+ totalMoney.toFixed(2)))
						.append($("<td></td>").append('')).append(
								$("<td></td>").append('')).append(
								$("<td></td>").append('')).appendTo(tbody);
			}, 'json');
	// 获取分页信息
	$.getJSON('./mgr/income/counts', {
		content : content,
		bankId : bankId,
		accId : 1,
		page : page
	}, function(data) {
		$.analysisPage(data.body);
	}, 'json');
}

/*
 * 获取银行卡 Ryan
 */
function findBank(curIndex, eml) {
	eml.empty().append("<option value=0>请选择支付账户</option>");
	$.post('./mgr/bank/banks', {
		zhName : ''
	}, function(data) {
		if (!$.isSuccess(data))
			return;
		$.each(data.body, function(i, v) {
			$(
					"<option " + analyzeSelect(v.id, curIndex) + " value="
							+ v.id + "></option>").append(v.descp)
					.appendTo(eml);
		});
	}, 'json');
}

function analyzeSelect(id, curIndex) {
	return curIndex > 0 && id == curIndex ? " selected=true " : "";
}
/*
 * 给当前月份赋值 Ryan
 */
function findMonth() {
	var date = new Date();
	var month = date.getMonth() < 9 ? "0" + (date.getMonth() + 1) : (date
			.getMonth() + 1);
	var str = date.getFullYear() + '-' + month;
	$('input.querymonth').val(str);

}
/*
 * 解析按钮组 te5l.com [K]
 */
function analysisBtns(v) {
	var btns = "";
	btns += secure.modify ? "<button type='button' class='btn btn-primary btn-xs' onclick='showModifyBox("
			+ v.id
			+ ")'><span class='glyphicon glyphicon-pencil'></span>编辑</button>"
			: "";
	btns += secure.modify ? "<button type='button' class='btn btn-danger btn-xs' onclick='deleteincome("
			+ v.id
			+ ")'><span class='glyphicon glyphicon-trash'></span>删除</button>"
			: "";
	return btns;
}

function showAddBox() {
	$('.empty').removeClass('empty');
	$('input.desc-add-box').val("");
	$('input.je-modify-box').val("");
	$('textarea.desc-detail-box').val("");
	findBank(0, $('select.bank-add-box'));
	BootstrapDialog.showModel($('div.add-income-box'));
}

function showModifyBox(id) {
	$('.empty').removeClass('empty');
	if (!id)
		return;
	dialog = BootstrapDialog.loading();
	$.getJSON('./mgr/income/findIncomeByid', {
		id : id
	}, function(data) {
		dialog.close();
		if (!$.isSuccess(data))
			return;
		bean.id = data.body.id;
		findBank(data.body.bankId, $('select.bank-modify-box'));
		$('input.desc-modify-box').val(data.body.descp);
		$('input.je-modify-box').val(data.body.je);
		$('textarea.detail-modify-box').val(data.body.detail);
		BootstrapDialog.showModel($('div.modify-income-box'));
	});
}

function buildBean() {
	bean.descp = $.verifyForm($('input.desc-add-box'), true);
	bean.bankid = $.verifyForm($('select.bank-add-box'), true);
	bean.accountid = 1;
	bean.currencyid = 1;
	bean.je = $.verifyForm($('input.je-add-box'), true);
	bean.status = 0;
	bean.n1 = 2;
	bean.n2 = 3;
	bean.c1 = 'ss';
	bean.c2 = 'dd';
	bean.detail = $.verifyForm($('textarea.detail-add-box'), true);
	;
}
function buildModifyBean() {
	bean.descp = $.verifyForm($('input.desc-modify-box'), true);
	bean.bankid = $.verifyForm($('select.bank-modify-box'), true);
	bean.accountid = 1;
	bean.currencyid = 1;
	bean.je = $.verifyForm($('input.je-modify-box'), true);
	bean.status = 0;
	bean.n1 = 2;
	bean.n2 = 3;
	bean.c1 = 'ss';
	bean.c2 = 'dd';
	bean.detail = $.verifyForm($('textarea.detail-modify-box'), true);
	;
}
function save() {
	buildBean();
	dialog = BootstrapDialog.isSubmitted();
	$.post('./mgr/income/save', {
		data : JSON.stringify(bean)
	}, function(data) {
		dialog.close();
		if (!$.isSuccess(data))
			return;
		var dlg = BootstrapDialog.show({
			title : "提示信息",
			type : BootstrapDialog.TYPE_SUCCESS,
			closable : true,
			message : "新的收入记录保存成功!",
			onhidden : function(dialogRef) {
				window.location.href = "income.html";
			}
		});
		setTimeout(function() {
			dlg.close();
		}, 200);
	}, 'json');
};

function modify() {
	buildModifyBean();
	dialog = BootstrapDialog.isSubmitted();
	$.post('./mgr/income/modify', {
		data : JSON.stringify(bean)
	}, function(data) {
		dialog.close();
		if (!$.isSuccess(data))
			return;
		var dlg = BootstrapDialog.show({
			title : "提示信息",
			type : BootstrapDialog.TYPE_SUCCESS,
			closable : true,
			message : "收入记录修改成功!",
			onhidden : function(dialogRef) {
				window.location.href = "income.html";
			}
		});
		setTimeout(function() {
			dlg.close();
		}, 200);
	}, 'json');
};

function deleteincome(id) {
	if (!id)
		return;
	BootstrapDialog.confirm('确定要删除此收入记录吗？', function(result) {
		if (!result)
			return;
		dialog = BootstrapDialog.loading();
		$.getJSON('./mgr/income/delete', {
			id : id
		}, function(data) {
			dialog.close();
			if (!$.isSuccess(data))
				return;
			BootstrapDialog.msg(data.body, BootstrapDialog.TYPE_SUCCESS);
			findListInfo();
		});
	});
}
