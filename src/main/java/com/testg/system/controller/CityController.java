package com.testg.system.controller;

import com.testg.system.entity.City;
import com.testg.system.service.ICityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author dlx
 * @since 2023-09-21
 */
@RestController
@RequestMapping("/city")
public class CityController {
    @Autowired
    private ICityService cityService;

    @GetMapping("/all")
    public List<City> getAllCity(){
        List<City> list = cityService.list();
        return list;



    }


}
