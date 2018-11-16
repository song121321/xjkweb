/*
 * 显示消费详情
 */

function showViewBox(id) {
	if (!id)
		return;
	$.getJSON('./mgr/consume/findConsumeByid', {
		id : id
	}, function(data) {
		if (!$.isSuccess(data))
			return;
		BootstrapDialog.showModel($('div.view-consume-box'));
		var desc = $('#view-consume-box-desc').empty();
		desc.html(data.body.detail);
	});
}

/*
 * 显示消费变更记录
 */
function showRecordBox(id) {
	if (!id)
		return;
	$.getJSON('./mgr/consume/getConsumeLogByConsumeId', {
		consumeId : id
	}, function(data) {
		if (!$.isSuccess(data))
			return;
		BootstrapDialog.showModel($('div.log-consume-box'));
		var tbody = $('tbody.table-log-consume-box').empty();
		$.each(data.body, function(i, v) {
			$('<tr></tr>').append($('<td></td>').append(i)).append(
					$('<td></td>').append(getStatusMessage(v.status))).append(
					$('<td></td>').append(v.account_nickname)).append(
					$('<td></td>').append(v.ctime)).append(
					$('<td></td>').append(v.descp)).appendTo(tbody);
		});
	});
}

function getStatusMessage(status) {
	if (status == 0) {
		return '新增';
	}
	if (status == 1) {
		return '更新';
	}
	if (status == 2) {
		return '删除';
	}
	if (status == 3) {
		return '查询';
	}
}
