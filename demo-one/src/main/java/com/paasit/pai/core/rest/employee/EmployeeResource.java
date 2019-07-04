package com.paasit.pai.core.rest.employee;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.paasit.pai.core.blogic.dto.employee.EmployeeRoleOrgRelReqtM01;
import com.paasit.pai.core.blogic.dto.employee.EmployeeRoleOrgRelRespM01;
import com.paasit.pai.core.service.BizLogic;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author mayu
 * @Description  查询人员、角色、机构信息
 * @date: 2019年6月28日
 * @version 1.0   
 */
@RestController
@RequestMapping("/employee")
@Api(value="员工信息接口")
public class EmployeeResource {

	
	private final Logger log = LoggerFactory.getLogger(EmployeeResource.class);
	
	@Autowired
	@Qualifier("EmployeeF01BLogic")
	private  final BizLogic<EmployeeRoleOrgRelReqtM01,EmployeeRoleOrgRelRespM01> employeeF01BLogic = null;
	
	
	/**
	 * @Description
	 * @date: 2019年7月4日
	 * @param employeeRoleOrgRelReqtM01
	 * @return ResponseEntity
	 * @throws Exception
	 */
	@PostMapping("/info")
    @Timed
    @ApiOperation(value="人员基本信息查询", notes="员工角色机构查询，自定义过滤字段查询字段间用，号隔开")
    public ResponseEntity<EmployeeRoleOrgRelRespM01> createDemo(@ApiParam @RequestBody @Validated EmployeeRoleOrgRelReqtM01 employeeRoleOrgRelReqtM01)
            throws Exception {
        log.debug("REST请求查询人员基本角色机构资源 : {}", employeeRoleOrgRelReqtM01);
        EmployeeRoleOrgRelRespM01 result = employeeF01BLogic.execute(employeeRoleOrgRelReqtM01);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
