package com.shop.pss.excel.convert;

import java.util.Map;

public interface IConvertClass {

//	public void initExistsCachData()  throws Exception;

	public Map<String,Object> getConvertDataMap()  throws Exception;

	public void dispose();
}
