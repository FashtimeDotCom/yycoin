/**
 * File Name: TcpShareDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-7-10<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tcp.dao;


import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.tcp.bean.TcpVSOutBean;


/**
 * TcpVSOutDAO
 * 
 * @author ZHUZHU
 * @version 2018-14-10
 * @see TcpVSOutDAO
 * @since 3.0
 */
public interface TcpVSOutDAO extends DAO<TcpVSOutBean, TcpVSOutBean>
{

    boolean deleteByApplyId(String applyId);
}
