function importStockItem(e, data){
    var result = data.result;
    var items = result.msg;
    var ret = result.ret;

    if (ret == 0){
        swal("导入模板出错！", result.msg, "error");
        return;
    }

    for (var i=0;i<items.length;i++){
        var item = items[i];
        var check = document.getElementById('check_init_' + i);
        check.checked = true;

        var productName = $O('productName_' + i);
        productName.value=item.productName;

        var productId = $O('productId_' + i);
        productId.value=item.productId;

        var providerName = $O('providerName_'+i);
        providerName.value=item.providerName;

        var providerId = $O('providerId_'+i);
        providerId.value=item.providerId;

        var price = $O('price_'+i);
        price.value=item.price;
        $d('price_' + i, false);

        var amount = $O('amount_'+i);
        amount.value=item.amount;
        $d('amount_' + i, false);

        var invoiceType = $O('invoiceType_'+i);
        invoiceType.value=item.invoiceType;
        var invoiceTypeOptions = invoiceType.options;
        for (var j = 0; j < invoiceTypeOptions.length; j++)
        {
            if (invoiceTypeOptions[j].value == invoiceType.value)
            {
                invoiceTypeOptions[j].selected = "selected";
            }
        }

        var dutyId = $O('dutyId_'+i);
        dutyId.value=item.dutyId;
        var dutyOptions = dutyId.options;
        for (var j = 0; j < dutyOptions.length; j++)
        {
            if (dutyOptions[j].value == dutyId.value)
            {
                dutyOptions[j].selected = "selected";
            }
        }

        var deliveryDate = $O('deliveryDate_'+i);
//        deliveryDate.value=item.deliveryDate;
//        console.log(deliveryDate);
//        console.log(deliveryDate.value);
        $("#deliveryDate_"+i).val(item.deliveryDate);

        var arrivalDate = $O('arrivalDate_'+i);
//        arrivalDate.value=item.arrivalDate;
//        console.log(arrivalDate);
//        console.log(arrivalDate.value);
        $("#arrivalDate_"+i).val(item.arrivalDate);
    }
}

//deprecated see pickadate.js
//function showDate(){
//    $('.datepicker').pickadate({
//        format:'yyyy-mm-dd',
//        monthsFull:["一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"],
//        monthsShort:["一","二","三","四","五","六","七","八","九","十","十一","十二"],
//        weekdaysFull:["星期日","星期一","星期二","星期三","星期四","星期五","星期六"],
//        weekdaysShort:["日","一","二","三","四","五","六"],
//        today:"今日",clear:"清除",close:"关闭",firstDay:1,formatSubmit:"yyyy-mm-dd"
//    });
//}

function calDateInner(obj, name)
{
    var tr = getTrObject(obj);

    var el = getInputInTr(tr, name);

    return calDate(el)
}

function lverify()
{
    var checkArr = document.getElementsByName('check_init');

    var isSelect = false;

    var imap = {};

    for (var i = 0; i < checkArr.length; i++)
    {
        var obj = checkArr[i];

        var index = obj.value;

        if (obj.checked)
        {
            isSelect = true;
            if ($O('productName_' + i).value == '' || $O('productId_' + i).value == '' )
            {
                alert('产品不能为空');
                return false;
            }

            var providerId = $O('providerId_' + i).value;
            if ($O('providerName_' + i).value == '' || providerId == '' )
            {
                alert('供应商不能为空');
                return false;
            }

            if ($O('invoiceType_' + i).value == '')
            {
                alert('发票类型不能为空');
                return false;
            }

            if ($O('dutyId_' + i).value == '')
            {
                alert('纳税实体不能为空');
                return false;
            }

            if ($$('amount_' + i)  == null)
            {
                alert('请选择产品是否满足数量要求');
                return false;
            }

            //if (imap[$O('productId_' + i).value] == $O('productId_' + i).value)
            //{
            //    alert('选择的产品不能重复');
            //    return false;
            //}
            //imap[$O('productId_' + i).value] = $O('productId_' + i).value;


            //#172 2016/2/14 将现在的一张采购单上产品不能重复的规则改为 供应商+产品 不能重复
            var productId = $O('productId_' + i).value;
            var key = productId+providerId;
            //console.log(key);
            if (imap[key] == key)
            {
                alert('选择的产品不能重复');
                return false;
            }

            imap[key] = key;
        }
    }

    if (!isSelect)
    {
        alert('请选择采购产品');
        return false;
    }

    return true;
}