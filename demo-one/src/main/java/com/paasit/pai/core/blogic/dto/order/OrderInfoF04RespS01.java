package com.paasit.pai.core.blogic.dto.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.paasit.pai.core.sql.dto.SqlOutputBaseDto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author mayu
 * @Description 订单列表查询ResponseDTO
 * @date: 2019年7月4日
 * @version 1.0   
 */
@Data
public class OrderInfoF04RespS01 extends SqlOutputBaseDto implements Serializable {
	
	private static final long serialVersionUID = 1915988350413415649L;


	/**
	 * 主键id
	 */
	@ApiModelProperty(value = "主键id")
	private String id;

	/**
	 * 订单名称
	 */
	@ApiModelProperty(value = "订单名称")
	private String orderName;

	/**
	 * 订单描述
	 */
	@ApiModelProperty(value = "订单描述")
	private String orderDesc;

	/**
	 * 订单编号
	 */
	@ApiModelProperty(value = "订单编号")
	private Integer orderNum;

	/**
	 * 下单时间
	 */
	@ApiModelProperty(value = "下单时间")
	private Date orderDate;
	
	/**
	 * 订单金额
	 */
	@ApiModelProperty(value = "订单金额")
	private BigDecimal orderAmt;
	
	
	/**
	 * 订单详情列表
	 */
	@ApiModelProperty(value = "订单详情列表")
	private List<OrderInfoF04RespS02> orderDetail;

}
