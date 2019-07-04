package com.paasit.pai.core.blogic.dto.order;

import java.io.Serializable;
import java.util.Date;

import com.paasit.pai.core.common.constraints.IntegerRange;
import com.paasit.pai.core.common.constraints.LengthMax;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author mayu
 * @Description 订单查询RequestDTO
 * @date: 2019年7月4日
 * @version 1.0   
 */
@Data
@ApiModel(description = "[userinfo][订单]订单查询列表方法RequestDTO")
public class OrderInfoF04ReqtM01 implements Serializable {
	
	private static final long serialVersionUID = -6306973333824398785L;
	
	/* 
	 * 订单名称
	 */
	@ApiModelProperty(value ="订单名称")
	@LengthMax(value = 10, message = "{com.paasit.pai.core.blogic.dto.demo.orderName.LengthMax}")
	private String orderName;
	
	/* 
	 * 开始时间
	 */
	@ApiModelProperty(value="订单查询区间开始时间")
	private Date orderDateStart;
	
	/* 
	 * 结束时间
	 */
	@ApiModelProperty(value="订单查询区间结束时间")
	private Date orderDateEnd;
	
	/* 
	 * 开始单号
	 */
	@ApiModelProperty(value="订单查询区间开始单号")
	@IntegerRange(min = 10, max = 10000, message = "{com.paasit.pai.core.blogic.dto.demo.Integer}")
	private Integer orderNumStart;
	
	/* 
	 * 结束单号
	 */
	@ApiModelProperty(value="订单查询区间结束单号")
	@IntegerRange(min = 10, max = 10000, message = "{com.paasit.pai.core.blogic.dto.demo.Integer}")
	private Integer orderNumEnd; 
}
