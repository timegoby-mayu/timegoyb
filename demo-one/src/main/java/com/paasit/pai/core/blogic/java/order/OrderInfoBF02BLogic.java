package com.paasit.pai.core.blogic.java.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paasit.pai.core.blogic.dto.order.OrderInfoF02ReqtM01;
import com.paasit.pai.core.blogic.dto.order.OrderInfoF02RespM01;
import com.paasit.pai.core.dao.UpdateDAO;
import com.paasit.pai.core.service.BizLogic;
import com.paasit.pai.core.sql.dto.order.OrderInfoF02SQL01IM01;
import com.paasit.pai.core.sql.dto.order.OrderInfoF02SQL02IS01;
import com.paasit.pai.core.utils.BeanCopierEx;

/**
 * @author mayu
 * @Description 订单订单删除
 * @date: 2019年7月4日
 * @version 1.0   
 */
@Service("OrderInfoBF02BLogic")
@Transactional(rollbackFor = Exception.class)
public class OrderInfoBF02BLogic implements BizLogic<OrderInfoF02ReqtM01, OrderInfoF02RespM01> {
	 /**
     * 日志
     */
    private final Logger log = LoggerFactory.getLogger(OrderInfoBF02BLogic.class);

    /**
     * 定义更新dao
     */
    @Autowired
    private UpdateDAO updateDAO;

	@Override
	public OrderInfoF02RespM01 execute(OrderInfoF02ReqtM01 orderInfoF02ReqtM01) throws Exception {
		 //定义返回值
		OrderInfoF02RespM01 result = new OrderInfoF02RespM01();
		
        //定义订单表输入参数sqldto
		OrderInfoF02SQL01IM01 orderInfoF02SQL01IM01 = new OrderInfoF02SQL01IM01();
        BeanCopierEx.copy(orderInfoF02ReqtM01, orderInfoF02SQL01IM01);
        
        //定义订单订单详情入参sqldto
        OrderInfoF02SQL02IS01 orderInfoF02SQL02IS01 =new OrderInfoF02SQL02IS01();
        orderInfoF02SQL02IS01.setOrderId(orderInfoF02ReqtM01.getId());
        
        //删除订单主表
        Integer rows = updateDAO.execute("OrderInfoF02SQL01IM01", orderInfoF02SQL01IM01);
        log.info("删除订单Id：{}成功！", orderInfoF02ReqtM01.getId());
        
        //删除订单详细表
        updateDAO.execute("OrderInfoF02SQL02IS01", orderInfoF02SQL02IS01);
        log.info("删除订单详情orderId：{}成功！", orderInfoF02ReqtM01.getId());
        
        result.setResult(rows);
        return result;
	}

}
