package com.paasit.pai.core.blogic.dto.order;

import java.io.Serializable;

import com.paasit.pai.core.bean.ServiceResponseBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author mayu
 * @Description 订单新增ResponseDTO
 * @date: 2019年7月4日
 * @version 1.0   
 */
@Data
public class OrderInfoF01RespM01 extends ServiceResponseBase implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5539453267726978153L;
	
	
	  /**
     * 受影响的行数
     */
    @ApiModelProperty(value="受影响的行数")
    private Integer result;
    
    /**
     * 主键id
     */
    @ApiModelProperty(value="主键id")
    private String id;
	

}
