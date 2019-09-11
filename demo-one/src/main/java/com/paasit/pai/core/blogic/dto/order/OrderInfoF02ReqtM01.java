package com.paasit.pai.core.blogic.dto.order;

import java.io.Serializable;

import com.paasit.pai.core.common.constraints.LengthMax;
import com.paasit.pai.core.common.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author mayu
 * @Description 订单删除RequestDTO
 * @date: 2019年7月4日
 * @version 1.0   
 */
@Data
@ApiModel(description = "[userInfo][订单]删除方法的RequestDTO")
public class OrderInfoF02ReqtM01 implements Serializable {

	private static final long serialVersionUID = -1331115232978645247L;
	
	 /**
     * 主键id
     */
    @ApiModelProperty(value="主键id", required = true)
    @NotBlank(message="{com.paasit.pai.core.demo.id.NotBlank}")
    @LengthMax(value=36, message="{com.paasit.pai.core.demo.id.LengthMax}")
    private String id;

}
