package com.paasit.pai.core.sql.dto.order;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author mayu
 * @Description [orderinfo][订单信息]查询方法的SQLinputDTO
 * @date: 2019年7月4日
 * @version 1.0   
 */
@Data
public class OrderInfoF04SQL01IM01 implements Serializable {

	
	private static final long serialVersionUID = 3770221247061141058L;

	
	/* 
	 * 订单名称
	 */
	private String orderName;
	
	/* 
	 * 开始时间
	 */
	private Date orderDateStart;
	
	/* 
	 * 结束时间
	 */
	private Date orderDateEnd;
	
	/* 
	 * 开始单号
	 */
	private Integer orderNumStart;
	
	/* 
	 * 结束单号
	 */
	private Integer orderNumEnd; 
}
