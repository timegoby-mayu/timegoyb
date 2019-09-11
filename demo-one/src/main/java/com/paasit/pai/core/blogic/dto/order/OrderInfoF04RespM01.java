package com.paasit.pai.core.blogic.dto.order;

import java.io.Serializable;
import java.util.List;

import com.paasit.pai.core.bean.PageControllerInfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author mayu
 * @Description 订单查询ResponseDTO
 * @date: 2019年7月4日
 * @version 1.0   
 */
@Data
@ApiModel(description = "[userinfo][订单]订单列表查询分页")
public class OrderInfoF04RespM01 implements Serializable{
	
	
	private static final long serialVersionUID = 6164550588749144927L;
	
    
	 /**
          * 订单列表
     */
    @ApiModelProperty(value="订单列表信息")
	private List<OrderInfoF04RespS01> orderList;
    
    /**
     * 分页DTO
     */
    @ApiModelProperty(value="分页DTO")
    private PageControllerInfo pageControllerInfo;

}
