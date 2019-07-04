package com.paasit.pai.core.rest.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.paasit.pai.core.blogic.dto.order.OrderInfoF01ReqtM01;
import com.paasit.pai.core.blogic.dto.order.OrderInfoF01RespM01;
import com.paasit.pai.core.blogic.dto.order.OrderInfoF02ReqtM01;
import com.paasit.pai.core.blogic.dto.order.OrderInfoF02RespM01;
import com.paasit.pai.core.blogic.dto.order.OrderInfoF04ReqtM01;
import com.paasit.pai.core.blogic.dto.order.OrderInfoF04RespM01;
import com.paasit.pai.core.service.BizLogic;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author mayu
 * @Description
 * @date: 2019年7月1日
 * @version 1.0
 */
@RestController("OrderInfoResource")
@RequestMapping("/api")
@Api(value = "订单信息接口")
public class OrderInfoResource {

	private final Logger log = LoggerFactory.getLogger(OrderInfoResource.class);

	@Autowired
	@Qualifier("OrderInfoBF01BLogic")
	private final BizLogic<OrderInfoF01ReqtM01, OrderInfoF01RespM01> orderInfoBF01BLogic = null;

	@Autowired
	@Qualifier("OrderInfoBF02BLogic")
	private final BizLogic<OrderInfoF02ReqtM01, OrderInfoF02RespM01> orderInfoBF02BLogic = null;
	
	@Autowired
	@Qualifier("OrderInfoBF04BLogic")
	private final BizLogic<OrderInfoF04ReqtM01, OrderInfoF04RespM01> orderInfoBF04BLogic = null;

	@PostMapping("/order/info")
	@Timed
	@ApiOperation(value = "新增订单信息", notes = "订单新增[createOrderInfo]")
	public ResponseEntity<OrderInfoF01RespM01> createOrderInfo(
			@ApiParam @RequestBody @Validated OrderInfoF01ReqtM01 orderInfoF01ReqtM01) throws Exception {
		log.debug("REST请求新增订单资源 : {}", orderInfoF01ReqtM01);
		OrderInfoF01RespM01 result = orderInfoBF01BLogic.execute(orderInfoF01ReqtM01);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@DeleteMapping("/order/delete")
	@Timed
	@ApiOperation(value = "删除订单信息", notes = "订单删除[deleteOrderInfo]")
	public ResponseEntity<OrderInfoF02RespM01> deleteOrderInfo(
			@ApiParam @RequestBody @Validated OrderInfoF02ReqtM01 orderInfoF02ReqtM01) throws Exception {
		log.debug("REST请求订订单删除资源 : {}", orderInfoF02ReqtM01);
		OrderInfoF02RespM01 result = orderInfoBF02BLogic.execute(orderInfoF02ReqtM01);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping("/order/page")
	@Timed
	@ApiOperation(value = "订单查询分页", notes = "根据DemoF04ReqtM01进行分页和排序查询")
	public ResponseEntity<OrderInfoF04RespM01> getOrderByPage(@ApiParam @RequestBody @Validated  OrderInfoF04ReqtM01 orderInfoF04ReqtM01)
			throws Exception {
		log.debug("REST请求查询订单资源: {}", orderInfoF04ReqtM01);
		OrderInfoF04RespM01 result = orderInfoBF04BLogic.execute(orderInfoF04ReqtM01);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

}
