package com.paasit.pai.core.blogic.dto.order;

import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author mayu
 * @Description 订单详情新增方法RequsetDTO
 * @date: 2019年7月4日
 * @version 1.0   
 */
@Data
@ApiModel(description = "[orderdetail][订单]插入方法的RequestDTO")
public class OrderInfoF01ReqtS01 implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -710513782463527497L;


	/**
	 * 订单明细ID
	 */
	@ApiModelProperty(value = "订单明细ID", required = true)
	private String id;
	
	
	/**
	 * 订单表ID
	 */
	@ApiModelProperty(value = "订单明细ID", required = true)
	private String orderId;
	
	/**
	 * 订单明细备注
	 */
	@ApiModelProperty(value = "订单明细备注", required = true)
	private String orderRemark;
	
	/**
	 * 订单明细金额
	 */
	@ApiModelProperty(value = "订单明细金额", required = true)
	private BigDecimal orderAmount;
	

}
