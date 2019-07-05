package com.paasit.pai.core.sql.dto.order;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author mayu
 * @Description 订单查询SqlInputDTO
 * @date: 2019年7月5日
 * @version 1.0   
 */
@Data
public class OrderInfoF01SQL02IS01 implements Serializable {
	
	
	private static final long serialVersionUID = 3133468525962189950L;
 
	/**
	 * 订单明细列表
	 */
	@ApiModelProperty(value = "订单明细列表", required = true)
	List<OrderInfoF01SQL03IS01> orderDetailList;
	
	
}
