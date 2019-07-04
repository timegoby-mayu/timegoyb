package com.paasit.pai.core.sql.dto.order;

import java.io.Serializable;

import lombok.Data;

/**
 * @author mayu
 * @Description [orderdetail][订单详情信息]查询方法的SQLInputDTO
 * @date: 2019年7月4日
 * @version 1.0   
 */
@Data
public class OrderInfoF04SQL02IS01 implements Serializable {
	
	private static final long serialVersionUID = 9195288569758805997L;
	/* 
	 * 订单编号
	 * 
	 */
	private String orderId;

}
