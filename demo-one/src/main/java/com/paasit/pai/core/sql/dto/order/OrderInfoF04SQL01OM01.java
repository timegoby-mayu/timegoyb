package com.paasit.pai.core.sql.dto.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.paasit.pai.core.sql.dto.SqlOutputBaseDto;

import lombok.Data;

/**
 * @author mayu
 * @Description [orderinfo][订单信息]查询方法的SQLOutputDTO
 * @date: 2019年7月4日
 * @version 1.0   
 */
@Data
public class OrderInfoF04SQL01OM01 extends SqlOutputBaseDto implements Serializable{
	
	private static final long serialVersionUID = 8733660268545412843L;
	
	
	/**
	 * 主键
	 */
	private String Id;
	/**
	 * 订单名称
	 */
	private String orderName;

	/**
	 * 订单描述
	 */
	private String orderDesc;

	/**
	 * 订单编号
	 */
	private Integer orderNum;

	/**
	 * 下单时间
	 */
	private Date orderDate;
	
	/**
	 * 订单金额
	 */
	private BigDecimal orderAmt;

}
