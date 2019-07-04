package com.paasit.pai.core.blogic.java.order;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paasit.pai.core.bean.PageControllerInfo;
import com.paasit.pai.core.blogic.dto.order.OrderInfoF04ReqtM01;
import com.paasit.pai.core.blogic.dto.order.OrderInfoF04RespM01;
import com.paasit.pai.core.blogic.dto.order.OrderInfoF04RespS01;
import com.paasit.pai.core.blogic.dto.order.OrderInfoF04RespS02;
import com.paasit.pai.core.blogic.java.demo.DemoF04BLogic;
import com.paasit.pai.core.consts.CommonConsts;
import com.paasit.pai.core.context.BLContext;
import com.paasit.pai.core.dao.QueryDAO;
import com.paasit.pai.core.service.BizLogic;
import com.paasit.pai.core.sql.dto.order.OrderInfoF04SQL01IM01;
import com.paasit.pai.core.sql.dto.order.OrderInfoF04SQL01OM01;
import com.paasit.pai.core.sql.dto.order.OrderInfoF04SQL02IS01;
import com.paasit.pai.core.sql.dto.order.OrderInfoF04SQL02OM01;
import com.paasit.pai.core.utils.BeanCopierEx;
import com.paasit.pai.core.utils.Pub;

/**
 * @author mayu
 * @Description  订单列表查询
 * @date: 2019年7月4日
 * @version 1.0   
 */
@Service("OrderInfoBF04BLogic")
public class OrderInfoBF04BLogic implements BizLogic<OrderInfoF04ReqtM01, OrderInfoF04RespM01> {
	
	  /**
     * 日志
     */
    private final Logger log = LoggerFactory.getLogger(DemoF04BLogic.class);

    /**
     * 定义查询dao
     */
    @Autowired
    protected QueryDAO queryDAO;

	@Override
	public OrderInfoF04RespM01 execute(OrderInfoF04ReqtM01 params) throws Exception {
		//定义返回结果
		OrderInfoF04RespM01 result =new OrderInfoF04RespM01();
		
		//定义订单主表查询sqlDTO
		OrderInfoF04SQL01IM01 orderInfoF04SQL01IM01 =new OrderInfoF04SQL01IM01();
		
		//将请求参数赋值给sqlDTO
		BeanCopierEx.copy(params, orderInfoF04SQL01IM01);
		
		 //从上下文中获取分页信息
        PageControllerInfo pageInput = (PageControllerInfo)BLContext.getValue(CommonConsts.PAGE_INFO);
        
        //调用queryDAO进行查询
        List<OrderInfoF04SQL01OM01> queryOrderList = new  ArrayList<OrderInfoF04SQL01OM01>();
        queryOrderList = queryDAO.executeForObjectListByPage("OrderInfoF04SQL01", orderInfoF04SQL01IM01, pageInput);
        log.info("查询订单列表信息完成！");
        
        //定义订单详情查询入参SqlDTO
        List<OrderInfoF04SQL02IS01> orderInfoF04SQL02IS01List = new ArrayList<OrderInfoF04SQL02IS01>();
        for(OrderInfoF04SQL01OM01 orderInfoF04SQL01OM01: queryOrderList) {
        	OrderInfoF04SQL02IS01 orderInfoF04SQL02IS01 = new OrderInfoF04SQL02IS01();
        	orderInfoF04SQL02IS01.setOrderId(orderInfoF04SQL01OM01.getId());
        	orderInfoF04SQL02IS01List.add(orderInfoF04SQL02IS01);
        }
        
        //查询订单详情入参sqlDTO
        List<OrderInfoF04SQL02OM01> detaillist = new ArrayList<OrderInfoF04SQL02OM01>();
        detaillist = queryDAO.executeForObjectList("OrderInfoF04SQL02", orderInfoF04SQL02IS01List);
        log.info("查询订单详情信息完成！");
        
        //将查询出来的订单列表SqlOutDTO赋值到ResponseDTO
        ArrayList<OrderInfoF04RespS01> respOrderList = BeanCopierEx.copy(queryOrderList, OrderInfoF04RespS01.class);
        
        //将查询出来的订单详情SqlOutDTO赋值到ResponseDTO
        ArrayList<OrderInfoF04RespS02> respOrderDetailList = BeanCopierEx.copy(detaillist, OrderInfoF04RespS02.class);
        
                
        //将订单和订单详情绑定
        for(OrderInfoF04RespS01 orderInfoF04RespS01 : respOrderList) {
        	List<OrderInfoF04RespS02> list =new ArrayList<OrderInfoF04RespS02>();
        	for(OrderInfoF04RespS02 orderInfoF04RespS02: respOrderDetailList) {
        		if(orderInfoF04RespS01.getId().equals(orderInfoF04RespS02.getOrderId())) {
        			list.add(orderInfoF04RespS02);
        		}
        	}
        	orderInfoF04RespS01.setOrderDetail(list);
        }
        result.setOrderList(respOrderList);	
        //设置分页信息
        result.setPageControllerInfo(Pub.getPageControllerInfo(queryOrderList));
		return result;
	}

}
