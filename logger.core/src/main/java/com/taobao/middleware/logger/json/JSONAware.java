package com.taobao.middleware.logger.json;

/**
 * Beans that support customized output of JSON text shall implement this interface.  
 */
public interface JSONAware {
	/**
	 * @return JSON text
	 */
	String toJSONString();
}
