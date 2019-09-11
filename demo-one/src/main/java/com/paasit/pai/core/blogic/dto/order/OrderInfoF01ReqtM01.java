package com.paasit.pai.core.blogic.dto.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.paasit.pai.core.common.constraints.DateRange;
import com.paasit.pai.core.common.constraints.Digits;
import com.paasit.pai.core.common.constraints.IntegerRange;
import com.paasit.pai.core.common.constraints.LengthMax;
import com.paasit.pai.core.common.constraints.NotBlank;
import com.paasit.pai.core.common.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author mayu
 * @Description 订单新增方法RequestDTO
 * @date: 2019年7月4日
 * @version 1.0   
 */
@Data
@ApiModel(description = "[orderinfo][订单]插入方法的RequestDTO")
public class OrderInfoF01ReqtM01 implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 201018994932610307L;

	/**
	 * 主键id
	 */
	@ApiModelProperty(value = "主键id")
	@LengthMax(value = 36, message = "{com.paasit.pai.core.blogic.dto.order.id.LengthMax}")
	private String id;

	/**
	 * 订单名称
	 */
	@ApiModelProperty(value = "订单名称")
	@NotNull(message = "{com.paasit.pai.core.blogic.dto.order.orderName.NotBlank}")
	@LengthMax(value = 10, message = "{com.paasit.pai.core.blogic.dto.order.orderName.LengthMax}")
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
	@NotNull(message = "{com.paasit.pai.core.blogic.dto.order.orderNum.NotNull}")
	@IntegerRange(min = 10, max = 10000, message = "{com.paasit.pai.core.blogic.dto.order.orderNum.IntegerRange}")
	private Integer orderNum;

	/**
	 * 下单时间
	 */
	@ApiModelProperty(value = "下单时间")
	@NotNull(message = "{com.paasit.pai.core.blogic.dto.demo.orderDate.NotNull}")
	@DateRange(min="1980-01-01", max="2000-12-31", message="{com.paasit.pai.core.blogic.dto.order.orderDate.DateRange}")
	private Date orderDate;
	
	/**
	 * 订单金额
	 */
	@ApiModelProperty(value = "订单金额")
	@NotNull(message = "{com.paasit.pai.core.blogic.dto.demo.orderAmt.NotNull}")
	@Digits(integer=4, fraction=2, message="{com.paasit.pai.core.blogic.dto.order.orderAmt.Digits}")
	private BigDecimal orderAmt;
	
	
	/**
	 * 订单详情列表
	 */
	@ApiModelProperty(value = "订单详情列表")
	private List<OrderInfoF01ReqtS01> orderDetail;
	
   
	
}
