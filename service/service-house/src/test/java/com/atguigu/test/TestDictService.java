package com.atguigu.test;

import com.atguigu.service.DictService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.Map;

/**
 * @author nicc
 * @version 1.0
 * @className TestDictService
 * @description TODO
 * @date 2022-07-23 10:14
 */
@SpringJUnitConfig(locations = {"classpath:spring/spring-*.xml"})
public class TestDictService {

    @Autowired
    private DictService dictService;

    @Test
    public void test(){
        List<Map<String, Object>> znodes = dictService.findZnodes(4000l);

        znodes.forEach(System.out::println);
    }
}
