package com.wjl.o2o.web.superadmin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wjl.o2o.entity.Area;
import com.wjl.o2o.service.AreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("superadmin")
public class AreaController {
	Logger logger = LoggerFactory.getLogger(AreaController.class);
	@Autowired
	private AreaService areaService;
	
	@RequestMapping(value="/listarea",method = RequestMethod.GET)
	@ResponseBody
	private Map<String,Object> listArea(){
		logger.info("======start======");
		long startTime = System.currentTimeMillis();
		Map<String,Object> resultMap = new HashMap<String, Object>();
		List<Area> arealist = new ArrayList<Area>();
		System.out.println("controller is runing.....");
		try {
			arealist = areaService.getArea();
			resultMap.put("rows", arealist);
			resultMap.put("total", arealist.size());
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("success", false);
			resultMap.put("errmsg", e.toString());
		}
		logger.error("test error!");
		long endTime = System.currentTimeMillis();
		logger.debug("costTime:[{}ms]",endTime-startTime);
		logger.info("============end=============");
		return resultMap;
	}
	
}
