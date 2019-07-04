package com.paasit.pai.core.sql.dto.order;

import java.io.Serializable;

import com.paasit.pai.core.sql.dto.SqlAuditBaseDto;

import lombok.Data;

/**
 * @author mayu
 * @Description [orderinfo][订单信息]删除方法的SQLInputDTO
 * @date: 2019年7月4日
 * @version 1.0   
 */
@Data
public class OrderInfoF02SQL01IM01 extends SqlAuditBaseDto  implements Serializable{
	
	private static final long serialVersionUID = 2482116854154409887L;

	 /**
     * 主键id
     */
    private String id;
	
}
