<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="预开票管理" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script type="text/javascript">

var gurl = '../tcp/preinvoice.do?method=';
var addUrl = '../preInvoice/addPreInvoice.jsp';
var ukey = 'PreInvoice';

var allDef = getAllDef();
var guidMap;
var thisObj;
function load()
{
     preload();
     
     guidMap = {
         title: '销售预开票列表',
         url: gurl + 'querySelfPreInvoice&type=22',
         colModel : [
             {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id} lstatus={status}>', width : 40, align: 'center'},
             {display: '标识', name : 'id', width : '15%'},
             {display: '目的', name : 'name', width : '15%'},
             {display: '申请人', name : 'stafferName', width : '8%'},
             {display: '处理人', name : 'processer', width : '8%'},
             {display: '系列', name : 'stype', cc: 'tcpStype', width : '5%'},
             {display: '状态', name : 'status', cc: 'tcpStatus', width : '10%'},
             {display: '计划开单日期', name : 'planOutTime', width : '10%'},
             {display: '预开票金额', name : 'showTotal', sortable: true, cname: 'total', width : '8%'},
             {display: '时间', name : 'logTime', sortable: true, width : 'auto'}
             ],
         extAtt: {
             id : {begin : '<a href=' + gurl + 'find' + ukey + '&id={id}>', end : '</a>'}
         },
         buttons : [
             {id: 'add', bclass: 'add', onpress : addBean, auth: '0000'},
             {id: 'update', bclass: 'update', onpress : updateBean, auth: '0000'},
             {id: 'del', bclass: 'del',  onpress : delBean, auth: '0000'},
             {id: 'search', bclass: 'search', onpress : doSearch},
             {id: 'cancel', caption: '退票', bclass: 'del', onpress : cancelBean, auth: '0000'}
             ],
        <p:conf/>
     };
     
     $("#mainTable").flexigrid(guidMap, thisObj);
}
 
function $callBack()
{
    loadForm();
    highlights($("#mainTable").get(0), ['结束'], 'blue');
    highlights($("#mainTable").get(0), ['驳回'], 'red');
}

function addBean(opr, grid)
{
    $l(gurl + 'preForAdd' + ukey + '&type=22');
    //$l(addUrl);
}

function delBean(opr, grid)
{
    if (getRadio('checkb') && (getRadio('checkb').lstatus == 0 || getRadio('checkb').lstatus == 1))
    {    
        if(window.confirm('确定删除?'))    
        $ajax(gurl + 'delete' + ukey + '&id=' + getRadioValue('checkb'), callBackFun);
    }
    else
    $error('不能操作');
}

function updateBean()
{
	if (getRadio('checkb') && getRadioValue('checkb'))
	{	
		$l(gurl + 'find' + ukey + '&update=1&id=' + getRadioValue('checkb'));
	}
	else
	$error('不能操作');
}

function cancelBean(opr, grid)
{
    if (getRadio('checkb') && (getRadio('checkb').lstatus == 0 || getRadio('checkb').lstatus == 1))
    {
        if(window.confirm('确定退票?'))
            $ajax(gurl + 'cancel' + ukey + '&id=' + getRadioValue('checkb'), callBackFun);
    }
    else
        $error('不能操作');
}

function doSearch()
{
    $modalQuery('../admin/query.do?method=popCommonQuery2&key=tcp.querySelfPreInvoice');
}

</script>
</head>
<body onload="load()" class="body_class">
<form name="mainForm" method="post">
<p:cache></p:cache>
</form>
<p:message></p:message>
<table id="mainTable" style="display: none"></table>
<p:query height="300px"/>
</body>