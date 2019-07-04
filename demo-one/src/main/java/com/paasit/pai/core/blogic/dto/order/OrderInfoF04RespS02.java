package com.paasit.pai.core.blogic.dto.order;

import java.io.Serializable;

import com.paasit.pai.core.sql.dto.SqlOutputBaseDto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @author mayu
 * @Description 订单详情列表查询ResponseDTO
 * @date: 2019年7月4日
 * @version 1.0   
 */
@Data
public class OrderInfoF04RespS02 extends SqlOutputBaseDto implements Serializable {
	
	private static final long serialVersionUID = 3329924709946477400L;

	
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
	private String orderAmount;
}
