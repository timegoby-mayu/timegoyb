package com.paasit.pai.core.blogic.dto.order;

import java.io.Serializable;

import com.paasit.pai.core.bean.ServiceResponseBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author mayu
 * @Description 订单删除ResponseDTO
 * @date: 2019年7月4日
 * @version 1.0   
 */
@Data
@ApiModel(description = "[orderinfo][订单]删除方法的ResponseDTO")
public class OrderInfoF02RespM01 extends  ServiceResponseBase implements Serializable{
	
	private static final long serialVersionUID = -8521347623059173507L;
	
	/**
     * 受影响的行数
     */
    @ApiModelProperty(value="受影响的行数")
    private Integer result;
}
