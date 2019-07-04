package com.paasit.pai.core.blogic.dto.employee;

import java.io.Serializable;

import com.paasit.pai.core.common.constraints.LengthMax;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author mayu
 * @Description 人员、角色、机构查询RequestDTO
 * @date: 2019年6月28日
 * @version 1.0   
 */
@Data
@ApiModel(description = "[员工]查询方法的RequestDTO")
public class EmployeeRoleOrgRelReqtM01 implements Serializable {
	
	/**
	 *序列号 
	 */
	private static final long serialVersionUID = 8322554320242690819L;
	
	/**
	  * 主键ID 
	 */
	@ApiModelProperty(value = "主键", required = true)
	
	@LengthMax(value = 36, message = "{com.paasit.pai.core.blogic.dto.employee.id.LengthMax}")
	private String id;
	
	/**
	  * 过滤字段
	 */
	@ApiModelProperty(value = "自定义过滤字段，多个字段用英文，隔开")
	private String fields;

}
