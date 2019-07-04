package com.paasit.pai.core.blogic.java.employee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paasit.pai.core.blogic.dto.commonRelationQuery.CommonRelationQueryRespM01;
import com.paasit.pai.core.blogic.dto.employee.EmployeeRoleOrgRelReqtM01;
import com.paasit.pai.core.blogic.dto.employee.EmployeeRoleOrgRelRespM01;
import com.paasit.pai.core.blogic.dto.employeeObj.v0_1.EmployeeObjCustomF04ReqtM01;
import com.paasit.pai.core.blogic.dto.employeeObj.v0_1.EmployeeObjCustomF04RespM01;
import com.paasit.pai.core.blogic.dto.orgEmployeeRel.v0_1.OrgEmployeeRelF05ReqtM01;
import com.paasit.pai.core.blogic.dto.sysRoleEmployeeRel.v0_1.SysRoleEmployeeRelF05ReqtM01;
import com.paasit.pai.core.common.domain.I18nMessage;
import com.paasit.pai.core.exception.BizLogicException;
import com.paasit.pai.core.feign.employeeObj.v0_1.EmployeeObjClient;
import com.paasit.pai.core.feign.orgEmployeeRel.v0_1.OrgEmployeeRelClient;
import com.paasit.pai.core.feign.sysRoleEmployeeRel.v0_1.SysRoleEmployeeRelClient;
import com.paasit.pai.core.service.BizLogic;
import com.paasit.pai.core.vo.CommonRelationQueryFilterVO;

/**
 * @author mayu
 * @Description
 * @date: 2019年7月1日
 * @version 1.0
 */
@Service("EmployeeF01BLogic")
@Transactional(rollbackFor = Exception.class)
public class EmployeeF05BLogic implements BizLogic<EmployeeRoleOrgRelReqtM01, EmployeeRoleOrgRelRespM01> {

	@Autowired
	private EmployeeObjClient employeeObjClient;

	@Autowired
	private SysRoleEmployeeRelClient sysRoleEmployeeRelClient;

	@Autowired
	private OrgEmployeeRelClient orgEmployeeRelClient;

	@Override
	public EmployeeRoleOrgRelRespM01 execute(EmployeeRoleOrgRelReqtM01 params) throws Exception {

		EmployeeRoleOrgRelRespM01 employeeRoleOrgRelRespM01 = new EmployeeRoleOrgRelRespM01();
		// 查询人员信息
		employeeRoleOrgRelRespM01.setEmployeeVo(getEmployeeObjData(params));

		// 查询人员所属角色列表
		employeeRoleOrgRelRespM01.setRoleVoList(getEmployeeRoleByEidData(params));

		// 查询人员所属机构列表
		employeeRoleOrgRelRespM01.setOrgVoList(getEmployeeOrgByEidData(params));

		return employeeRoleOrgRelRespM01;
	}

	/**
	 * @Description 查询人员信息
	 * @date: 2019年7月1日
	 * @param params
	 * @return List
	 */
	public List<Map<String, Object>> getEmployeeObjData(EmployeeRoleOrgRelReqtM01 params) throws Exception {
		// 过滤查询人员信息
		EmployeeObjCustomF04ReqtM01 employeeObjCustomF04ReqtM01 = new EmployeeObjCustomF04ReqtM01();
		employeeObjCustomF04ReqtM01.setFilter("id = '" + params.getId() + "'");
		// 过滤字段集合
		List<CommonRelationQueryFilterVO> fieldsList = new ArrayList<CommonRelationQueryFilterVO>();
		String fields = params.getFields();
		if (StringUtils.isBlank(fields)) {
			CommonRelationQueryFilterVO vo1 = new CommonRelationQueryFilterVO();
			vo1.setName("employeeNo");
			fieldsList.add(vo1);
			CommonRelationQueryFilterVO vo2 = new CommonRelationQueryFilterVO();
			vo2.setName("employeeName");
			fieldsList.add(vo2);
		} else {
			String[] fieldsArr = fields.split(",");
			for (String field : fieldsArr) {
				CommonRelationQueryFilterVO vo = new CommonRelationQueryFilterVO();
				vo.setName(field);
				fieldsList.add(vo);
			}
		}
		employeeObjCustomF04ReqtM01.setFields(fieldsList);
		EmployeeObjCustomF04RespM01 employeeObjCustomF04RespM01 = employeeObjClient
				.getEmployeeObjByCustomPage(employeeObjCustomF04ReqtM01);
		
		//调用服务出错、异常处理
		if(!employeeObjCustomF04RespM01.getResponseCode().equals("100"))
			throw new BizLogicException(new I18nMessage("EmployeeF05BLogicMSG01"));
		
		return employeeObjCustomF04RespM01.getData();
	}

	/**
	 * @Description 查询人员角色
	 * @date: 2019年7月1日
	 * @param params
	 * @return list
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getEmployeeRoleByEidData(EmployeeRoleOrgRelReqtM01 params) throws Exception {
		SysRoleEmployeeRelF05ReqtM01 sysRoleEmployeeRelF05ReqtM01 = new SysRoleEmployeeRelF05ReqtM01();
		sysRoleEmployeeRelF05ReqtM01.setBId(params.getId());
		// obj过滤字段
		ArrayList<CommonRelationQueryFilterVO> objFieldsList = new ArrayList<CommonRelationQueryFilterVO>();
		CommonRelationQueryFilterVO objFilterVO = new CommonRelationQueryFilterVO();
		objFilterVO.setName("roleObjName");
		objFieldsList.add(objFilterVO);
		// rel过滤字段
		ArrayList<CommonRelationQueryFilterVO> relFiledsList = new ArrayList<CommonRelationQueryFilterVO>();
		CommonRelationQueryFilterVO rolFilterVo = new CommonRelationQueryFilterVO();
		rolFilterVo.setName("bid");
		relFiledsList.add(rolFilterVo);

		sysRoleEmployeeRelF05ReqtM01.setObjFields(objFieldsList);
		sysRoleEmployeeRelF05ReqtM01.setRelFields(relFiledsList);

		CommonRelationQueryRespM01 commonRelationQueryRespM01 = sysRoleEmployeeRelClient
				.listObjectByBId(sysRoleEmployeeRelF05ReqtM01);
		
		//调用服务出错、异常处理
		if(!commonRelationQueryRespM01.getResponseCode().equals("100"))
			throw new BizLogicException(new I18nMessage("EmployeeF05BLogicMSG02"));
		
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> map : commonRelationQueryRespM01.getData()) {
			Map<String, Object> m = new HashMap<String, Object>();
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				m.putAll((Map<String, Object>) entry.getValue());
			}
			returnList.add(m);
		}
		return returnList;
	}

	/**
	 * @Description 查询人员结构信息
	 * @date: 2019年7月1日
	 * @param params
	 * @return list
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getEmployeeOrgByEidData(EmployeeRoleOrgRelReqtM01 params) throws Exception {
		OrgEmployeeRelF05ReqtM01 orgEmployeeRelF05ReqtM01 = new OrgEmployeeRelF05ReqtM01();
		orgEmployeeRelF05ReqtM01.setBId(params.getId());
		// obj过滤字段
		ArrayList<CommonRelationQueryFilterVO> objFieldsList = new ArrayList<CommonRelationQueryFilterVO>();
		CommonRelationQueryFilterVO objFilterVO = new CommonRelationQueryFilterVO();
		objFilterVO.setName("displayName");
		objFieldsList.add(objFilterVO);
		// rel过滤字段
		ArrayList<CommonRelationQueryFilterVO> relFiledsList = new ArrayList<CommonRelationQueryFilterVO>();
		CommonRelationQueryFilterVO rolFilterVo = new CommonRelationQueryFilterVO();
		rolFilterVo.setName("bid");
		relFiledsList.add(rolFilterVo);

		orgEmployeeRelF05ReqtM01.setObjFields(objFieldsList);
		orgEmployeeRelF05ReqtM01.setRelFields(relFiledsList);

		CommonRelationQueryRespM01 commonRelationQueryRespM01 = orgEmployeeRelClient
				.listObjectByBId(orgEmployeeRelF05ReqtM01);
		
		//调用服务出错、异常处理
		if(!commonRelationQueryRespM01.getResponseCode().equals("100"))
			throw new BizLogicException(new I18nMessage("EmployeeF05BLogicMSG03"));

		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> map : commonRelationQueryRespM01.getData()) {
			Map<String, Object> m = new HashMap<String, Object>();
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				m.putAll((Map<String, Object>) entry.getValue());
			}
			returnList.add(m);
		}
		return returnList;
	}
}
