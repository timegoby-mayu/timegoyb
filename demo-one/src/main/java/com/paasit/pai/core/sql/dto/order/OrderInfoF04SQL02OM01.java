package com.paasit.pai.core.sql.dto.order;

import java.io.Serializable;

import com.paasit.pai.core.sql.dto.SqlOutputBaseDto;

import lombok.Data;

/**
 * @author mayu
 * @Description [orderdetail][订单详情信息]查询方法的SQLOutputDTO
 * @date: 2019年7月4日
 * @version 1.0   
 */
@Data
public class OrderInfoF04SQL02OM01 extends SqlOutputBaseDto implements Serializable {

	
	private static final long serialVersionUID = 896467436556318634L;
	
	/**
	 * 订单明细ID
	 */
	private String id;
	
	
	/**
	 * 订单表ID
	 */
	private String orderId;
	
	/**
	 * 订单明细备注
	 */
	private String orderRemark;
	
	/**
	 * 订单明细金额
	 */
	private String orderAmount;

}
