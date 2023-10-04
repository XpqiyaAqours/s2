package com.testg.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.testg.system.entity.User;
import com.testg.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author dlx
 * @since 2023-10-02
 */
@RestController
@RequestMapping("/system/user")
public class UserController {
    @Autowired
    private IUserService userService;

    @GetMapping("/all")
    public List<User> getalluser(){
        List<User> list = userService.list();
        return list;
    }
    @PostMapping("/login")
    public Map<String,Object> login(@RequestBody User user){
        Map<String,Object> data=userService.login(user);
        if(data != null) {
            return data;
        }
        throw new RuntimeException("用户名或密码错误");
    }

    @GetMapping("/info")
    public Map<String,Object> getUserInfo(@RequestParam("token")String token){
        //根据token获取用户信息
        Map<String,Object> data=userService.getUserInfo(token);
        if(data != null) {
            return data;
        }
        throw new RuntimeException("用户无效");
    }
    @PostMapping("/logout")
    public String logout(@RequestHeader("X-token")String token){
        userService.logout(token);
        String data = "登出成功";
        return data;
    }

    @GetMapping("/list")
    //用户查询功能
    public  Map<String,Object> getUserList(@RequestParam(value = "username",required = false)String username,
                                      @RequestParam(value = "phone",required = false)String phone,
                                      @RequestParam(value = "pageNo")long pageNo,
                                      @RequestParam(value = "pageSize")long pageSize){

        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.hasLength(username),User::getUsername,username);
        wrapper.eq(StringUtils.hasLength(username),User::getPhone,phone);

        Page<User> Page = new Page<>(pageNo,pageSize);
        userService.page(Page,wrapper);
        Map<String,Object> data =new HashMap<>();
        data.put("total",Page.getTotal());
        data.put("rows",Page.getRecords());
        return data;

    }

}
