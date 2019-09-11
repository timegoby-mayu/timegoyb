package com.paasit.pai.core.sql.dto.order;

import java.io.Serializable;
import java.math.BigDecimal;
import com.paasit.pai.core.sql.dto.SqlAuditBaseDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * @author mayu
 * @Description [orderinfo][订单详情信息]新增方法的SQLinputDTO
 * @date: 2019年7月4日
 * @version 1.0   
 */
@Data
public class OrderInfoF01SQL03IS01 extends SqlAuditBaseDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -641838341250348434L;
	
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
