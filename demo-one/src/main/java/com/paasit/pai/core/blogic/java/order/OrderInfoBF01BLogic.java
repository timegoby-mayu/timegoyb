package com.paasit.pai.core.blogic.java.order;

import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.paasit.pai.core.blogic.dto.order.OrderInfoF01ReqtM01;
import com.paasit.pai.core.blogic.dto.order.OrderInfoF01RespM01;
import com.paasit.pai.core.dao.UpdateDAO;
import com.paasit.pai.core.service.BizLogic;
import com.paasit.pai.core.sql.dto.order.OrderInfoF01SQL01IM01;
import com.paasit.pai.core.sql.dto.order.OrderInfoF01SQL02IS01;
import com.paasit.pai.core.sql.dto.order.OrderInfoF01SQL03IS01;
import com.paasit.pai.core.utils.BeanCopierEx;

/**
 * @author mayu
 * @Description 订单添加
 * @date: 2019年7月4日
 * @version 1.0   
 */
@Service("OrderInfoBF01BLogic")
@Transactional(rollbackFor = Exception.class)
public class OrderInfoBF01BLogic implements BizLogic<OrderInfoF01ReqtM01, OrderInfoF01RespM01> {

	private final Logger log = LoggerFactory.getLogger(OrderInfoBF01BLogic.class);

	@Autowired
	private UpdateDAO updateDAO;

	@Override
	public OrderInfoF01RespM01 execute(OrderInfoF01ReqtM01 orderInfoF01ReqtM01) throws Exception {
		
		//定义返回值对象
		OrderInfoF01RespM01 result =new OrderInfoF01RespM01();
		
		// 构造订单sql001的查询条件
		OrderInfoF01SQL01IM01 orderInfoF01SQL01IM01 = new OrderInfoF01SQL01IM01();

		// 构造订单详情sql002的查询条件
		OrderInfoF01SQL02IS01 orderInfoF01SQL02IS01 = new OrderInfoF01SQL02IS01();

		// 将request参数拷贝到sql001Dto里
		BeanCopierEx.copy(orderInfoF01ReqtM01, orderInfoF01SQL01IM01);

		// 生成数据库的主键id
		orderInfoF01SQL01IM01.setId(UUID.randomUUID().toString());

		// 执行完全插入订单主表sql语句
		Integer rows = updateDAO.execute("OrderInfoF01SQL01IM01", orderInfoF01SQL01IM01);
		log.info("插入订单id为:{} 成功", orderInfoF01SQL01IM01.getId());

		// 执行完全插入订单详情表sql语句
		List<OrderInfoF01SQL03IS01>  listSql03= BeanCopierEx.copy(orderInfoF01ReqtM01.getOrderDetail(), OrderInfoF01SQL03IS01.class);

		for (OrderInfoF01SQL03IS01 orderInfoF01SQL03IS01 : listSql03) {
			orderInfoF01SQL03IS01.setId(UUID.randomUUID().toString());
			orderInfoF01SQL03IS01.setOrderId(orderInfoF01SQL01IM01.getId());
		}
		
		//将入参的订单详情list  SqlinputDTO进行转换对象
		if (listSql03.size() > 0) {
			orderInfoF01SQL02IS01.setOrderDetailList(listSql03);
			updateDAO.execute("OrderInfoF01SQL02IS01", orderInfoF01SQL02IS01);
			log.info("插入订单详情OrderId为:{} 成功", orderInfoF01SQL01IM01.getId());
		}
		 //返回受影响的行数
        result.setResult(rows);
        
        //返回主键id
        result.setId(orderInfoF01SQL01IM01.getId());
		return result;
	}

}
