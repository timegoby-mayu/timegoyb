package com.paasit.pai.core.blogic.dto.test;


import java.io.Serializable;

import lombok.Data;

/**
 * 描述:公共多语言dto
 * 
 * @version:0.0.1-SNAPSHOT
 * @author:谷春
 * @date:2017-7-3 17:52:46
 */
@Data
public class CommonLanguage implements Serializable {

	/**
     * @param string
     * @param string2
     */

    private static final long serialVersionUID = -294377745413475336L;

	/**
	 * 多语言键
	 */
	private String key;

	/**
	 * 多语言值
	 */
	private String value;

	/**
	 * 是否主要语言：当前使用
	 */
	private boolean main = false;
}