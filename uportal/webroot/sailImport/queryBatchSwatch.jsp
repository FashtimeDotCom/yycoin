<%@ page contentType="text/html;charset=UTF-8" language="java"
    errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="批量审批明细" cal="true" guid="true"/>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/key.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/tableSort.js"></script>
<script language="javascript">
function exports()
{
	//document.location.href = '../finance/finance.do?method=exportFinanceItem';
}

function load()
{
	loadForm();
	
	bingTable("senfe");
}

function query()
{
	submit(null, null, null);
}

function addBean()
{
	$O('method').value = 'batchSwatch';

	submit('确定后系统将只处理结果为OK的订单', null, null);
}

function exports2(){
    if (window.confirm("确定导出当前结果?")){
        document.location.href = '../sail/outImport.do?method=exportBatchSwatch&batchId='+'${batchId}';
    }
}
</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../sail/outImport.do" method="post">
<input type="hidden" name="method" value="queryBatchSwatch"> 
<input type="hidden" value="2" name="firstLoad">
<input type="hidden" name="batchId" value="${batchId}">
<p:navigation
	height="22">
	<td width="550" class="navigation">批量审核</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>批量审核数据：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<table width="100%" align="center" cellspacing='1' class="table0" id="senfe">
			<tr align=center class="content0">
				<td align="center" width="12%" class="td_class" onclick="tableSort(this)"><strong>批次</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>单号</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>产品</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>数量</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>动作</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>客户</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this, true)"><strong>处理结果</strong></td>	
			</tr>

			<c:forEach items="${baList}" var="item"
				varStatus="vs">
				<tr class="${vs.index % 2 == 0 ? 'content1' : 'content2'}">
					<td align="left">${item.batchId}</td>
					<td align="left"><a href="../sail/out.do?method=findOut&fow=99&outId=${item.outId}" title="查看销售明细">${item.outId}</a>
					</td>
					<td align="left">${item.productName}</td>
					<td align="left">${item.amount}</td>										
					<td align="left">${item.action}</td>
					<td align="left">${item.customerName}</td>
					<td align="left">${item.result}</td>
				</tr>
			</c:forEach>
		</table>
		
		<p:formTurning form="formEntry" method="queryBatchApprove"></p:formTurning>

	</p:subBody>

	<p:line flag="1" />
	
	<p:button leftWidth="100%" rightWidth="0%">
        <div align="right">
        <c:if test="${flag == 0}">
        <input type="button" class="button_class"
            id="ok_add" style="cursor: pointer" value="&nbsp;&nbsp;确 定&nbsp;&nbsp;"
            onclick="addBean()">&nbsp;&nbsp;
        </c:if>
		<c:if test="${flag == 1}">
			<input type="button" class="button_class"
				   id="ok_add" style="cursor: pointer" value="&nbsp;&nbsp;导 出&nbsp;&nbsp;"
				   onclick="exports2()">&nbsp;&nbsp;
		</c:if>
        <input type="button" class="button_class"
            id="ok_b" style="cursor: pointer" value="&nbsp;&nbsp;返 回&nbsp;&nbsp;"
            onclick="javaScript:window.history.go(-1);"></div>
    </p:button>
	
	<p:message2 />
	
</p:body>
</form>
</body>
</html>

