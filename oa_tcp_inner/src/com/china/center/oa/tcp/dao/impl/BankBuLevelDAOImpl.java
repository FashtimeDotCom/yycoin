/**
 * File Name: TcpApplyDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-7-10<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tcp.dao.impl;


import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.tcp.bean.BankBuLevelBean;
import com.china.center.oa.tcp.constanst.TcpConstanst;
import com.china.center.oa.tcp.constanst.TcpFlowConstant;
import com.china.center.oa.tcp.dao.BankBuLevelDAO;

import java.util.ArrayList;
import java.util.List;

public class BankBuLevelDAOImpl extends BaseDAO<BankBuLevelBean, BankBuLevelBean> implements BankBuLevelDAO
{
    @Override
    public List<BankBuLevelBean> queryByBearType(String bearType) {
        List<BankBuLevelBean> result = new ArrayList<BankBuLevelBean>();
        if ("2".equals(bearType)){
            //province manager
            result = jdbcOperation.queryObjectsBySql(
                    "select distinct provinceManagerId, provinceManager from T_CENTER_BANKBU_LEVEL")
                    .setMaxResults(600).list(BankBuLevelBean.class);
            for (BankBuLevelBean bean : result){
                bean.setId(bean.getProvinceManagerId());
                bean.setName(bean.getProvinceManager());
            }
        } else if ("3".equals(bearType)){
            //regionalManager
            result = jdbcOperation.queryObjectsBySql(
                    "select distinct regionalManagerId,regionalManager from T_CENTER_BANKBU_LEVEL")
                    .setMaxResults(600).list(BankBuLevelBean.class);
            for (BankBuLevelBean bean : result){
                bean.setId(bean.getRegionalManagerId());
                bean.setName(bean.getRegionalManager());
            }
        } else if ("4".equals(bearType)){
            //regionalDirector
            result = jdbcOperation.queryObjectsBySql(
                    "select distinct regionalDirectorId,regionalDirector from T_CENTER_BANKBU_LEVEL")
                    .setMaxResults(600).list(BankBuLevelBean.class);
            for (BankBuLevelBean bean : result){
                bean.setId(bean.getRegionalDirectorId());
                bean.setName(bean.getRegionalDirector());
            }
        }

        return result;
    }

    @Override
    public List<BankBuLevelBean> queryByBearTypeAndManager(String bearType, String manager) {
        List<BankBuLevelBean> result = new ArrayList<BankBuLevelBean>();
        if ("2".equals(bearType)){
            //provinceManager
            result = jdbcOperation.queryObjectsBySql(
                    "select id, name from T_CENTER_BANKBU_LEVEL where provinceManagerId='"+manager+"'")
                    .setMaxResults(600).list(BankBuLevelBean.class);
        } else if ("3".equals(bearType)){
            //regionalManager
            result = jdbcOperation.queryObjectsBySql(
                    "select id,name from T_CENTER_BANKBU_LEVEL where regionalManagerId='"+manager+"'")
                    .setMaxResults(600).list(BankBuLevelBean.class);
        } else if ("4".equals(bearType)){
            //regionalDirector
            result = jdbcOperation.queryObjectsBySql(
                    "select id,name from T_CENTER_BANKBU_LEVEL where regionalDirectorId='"+manager+"'")
                    .setMaxResults(600).list(BankBuLevelBean.class);
        }

        return result;
    }

    @Override
    public String queryHighLevelManagerId(String flowKey, int bearType, String stafferId) {
        List<String> result = new ArrayList<String>();
        if (TcpFlowConstant.WORK_PAY_MARKETING.equals(flowKey)
                || TcpFlowConstant.WORK_APPLY_MARKETING.equals(flowKey)){
            if (bearType == TcpConstanst.TCP_STATUS_REGIONAL_MANAGER){
                //regionalManager
                result = jdbcOperation.queryObjectsBySql(
                        "select regionalManagerId from T_CENTER_BANKBU_LEVEL where id='"+stafferId+"'")
                        .setMaxResults(600).list(String.class);
            } else if (bearType == TcpConstanst.TCP_STATUS_REGIONAL_DIRECTOR){
                //regionalDirector
                result = jdbcOperation.queryObjectsBySql(
                        "select regionalDirectorId from T_CENTER_BANKBU_LEVEL where regionalManagerId='"+stafferId+"'")
                        .setMaxResults(600).list(String.class);
            } else if (bearType == TcpConstanst.TCP_STATUS_REGIONAL_CEO){
                //manager
                result = jdbcOperation.queryObjectsBySql(
                        "select managerId from T_CENTER_BANKBU_LEVEL where regionalDirectorId='"+stafferId+"'")
                        .setMaxResults(600).list(String.class);
            }
        } else{
            if (bearType == TcpConstanst.TCP_STATUS_PROVINCE_MANAGER){
                //provinceManager
                result = jdbcOperation.queryObjectsBySql(
                        "select provinceManagerId from T_CENTER_BANKBU_LEVEL where id='"+stafferId+"'")
                        .setMaxResults(600).list(String.class);
            } else if (bearType == TcpConstanst.TCP_STATUS_REGIONAL_MANAGER){
                //regionalManager
                result = jdbcOperation.queryObjectsBySql(
                        "select regionalManagerId from T_CENTER_BANKBU_LEVEL where provinceManagerId='"+stafferId+"'")
                        .setMaxResults(600).list(String.class);
            } else if (bearType == TcpConstanst.TCP_STATUS_REGIONAL_DIRECTOR){
                //regionalDirector
                result = jdbcOperation.queryObjectsBySql(
                        "select regionalDirectorId from T_CENTER_BANKBU_LEVEL where regionalManagerId='"+stafferId+"'")
                        .setMaxResults(600).list(String.class);
            } else if (bearType == TcpConstanst.TCP_STATUS_REGIONAL_CEO){
                //manager
                result = jdbcOperation.queryObjectsBySql(
                        "select managerId from T_CENTER_BANKBU_LEVEL where regionalDirectorId='"+stafferId+"'")
                        .setMaxResults(600).list(String.class);
            }
        }

        return result.get(0);
    }
}
