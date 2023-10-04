package com.testg;

import com.testg.system.entity.City;
import com.testg.system.mapper.CityMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class Test1ApplicationTests {

    @Resource
    private CityMapper cityMapper;

    @Test
    void contextLoads() {
        List<City> cities = cityMapper.selectList(null);
        cities.forEach(System.out::println);



    }

}
