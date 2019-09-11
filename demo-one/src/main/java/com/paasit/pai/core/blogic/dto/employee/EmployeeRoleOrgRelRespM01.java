package com.paasit.pai.core.blogic.dto.employee;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.paasit.pai.core.bean.ServiceResponseBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author mayu
 * @Description 人员、角色、机构查询ResponsDTO
 * @date: 2019年6月28日
 * @version 1.0   
 */
@Data
@ApiModel(description = "[Test][测试]测试方法响应的ResponseDTO")
public class EmployeeRoleOrgRelRespM01 extends ServiceResponseBase implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 8632271938334227363L;
	
	@ApiModelProperty(value="过滤字段，查询人员信息")
	private List<Map<String, Object>> employeeVo; 
    
    @ApiModelProperty(value="过滤字段，用户对应角色列表")
    private List<Map<String, Object>> roleVoList;
    
    @ApiModelProperty(value="过滤字段，用户对应机构")
    private List<Map<String, Object>> orgVoList;
    
}
