package com.paasit.pai.core.sql.dto.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.paasit.pai.core.blogic.dto.order.OrderInfoF01ReqtS01;
import com.paasit.pai.core.sql.dto.SqlAuditBaseDto;
import lombok.Data;

/**
 * @author mayu
 * @Description [orderinfo][订单信息]新增方法的SQLinputDTO
 * @date: 2019年7月4日
 * @version 1.0   
 */
@Data
public class OrderInfoF01SQL01IM01 extends SqlAuditBaseDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6621851019829029043L;
	
	

	/**
	 * 主键id
	 */
	private String id;

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
