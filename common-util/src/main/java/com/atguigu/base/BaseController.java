package com.atguigu.base;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author nicc
 * @version 1.0
 * @className BaseController
 * @description TODO
 * @date 2022-07-20 20:58
 */

public class BaseController {
    /**
     * 封装页面提交的分页参数及搜索条件
     * @param request
     * @return
     * roleName=管理员&roleCode=system&hobby=sports&hobby=music&hobby=art&pageNum=12&pageSize=10
     *  roleName=管理员&pageNum=12&pageSize=10
     *  roleName=管理员
     *  ""
     */
    protected Map<String, Object> getFilters(HttpServletRequest request) {
        //roleName   roleCode  hobby pageNum pageSize
        Enumeration<String> paramNames = request.getParameterNames();
        Map<String, Object> filters = new TreeMap();
        while(paramNames != null && paramNames.hasMoreElements()) {
            String paramName = (String)paramNames.nextElement(); //roleName  roleCode  hobby
            String[] values = request.getParameterValues(paramName);//[管理员] [system] [sports,music,art]
            if (values != null && values.length != 0) {
                if (values.length > 1) {
                    //map.put("hobby",[sports,music,art])
                    filters.put(paramName, values);
                } else {
                    //map.put("roleName","管理员")
                    //map.put("roleCode","system")
                    //map.put("pageNum",12)
                    //map.put("pageSize",10)
                    filters.put(paramName, values[0]);
                }
            }
        }
        if(!filters.containsKey("pageNum")) {
            filters.put("pageNum", 1);//默认查询第一页
        }
        if(!filters.containsKey("pageSize")) {
            filters.put("pageSize", 5);//默认每页5条记录
        }

        return filters;
    }
}
