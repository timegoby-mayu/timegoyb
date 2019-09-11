package com.paasit.pai.core.sql.dto.order;

import java.io.Serializable;

import com.paasit.pai.core.sql.dto.SqlAuditBaseDto;

import lombok.Data;

/**
 * @author mayu
 * @Description [orderdetail][订单详情信息]删除方法的SQInputDTO
 * @date: 2019年7月4日
 * @version 1.0   
 */
@Data	
public class OrderInfoF02SQL02IS01 extends SqlAuditBaseDto  implements Serializable {
	
	
	private static final long serialVersionUID = 7118454542173630746L;
	
	 /**
        * 订单表主键
     */
    private String orderId;

}
