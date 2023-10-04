package com.testg.system.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.testg.system.entity.User;
import com.testg.system.mapper.UserMapper;
import com.testg.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dlx
 * @since 2023-10-02
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Map<String, Object> login(User user) {
        //查询用户
        //结果不为空，则生成token
        LambdaQueryWrapper<User> Wrapper = new LambdaQueryWrapper<>();
        Wrapper.eq(User::getUsername,user.getUsername());
        Wrapper.eq(User::getPassword,user.getPassword());
        User loginUser = this.baseMapper.selectOne(Wrapper);
        if(loginUser != null){
            //使用uuid
            String key = "user:" + UUID.randomUUID();
            //存入Redis
            loginUser.setPassword(null);
            redisTemplate.opsForValue().set(key,loginUser,30, TimeUnit.MINUTES);

            //返回数据
            Map<String, Object> data = new HashMap<>();
            data.put("token",key);
            return data;
        }
        return null;
    }

    @Override
    public Map<String, Object> getUserInfo(String token) {
        Object obj = redisTemplate.opsForValue().get(token);
        if (obj != null){
            User loginUser = JSON.parseObject(JSON.toJSONString(obj),User.class);
            Map<String, Object> data =new HashMap<>();
            data.put("name",loginUser.getUsername());
            data.put("avatar",loginUser.getAvatar());

            //角色
            List<String> rolelist = this.baseMapper.getRoleNameByUserId(loginUser.getId());
            data.put("roles",rolelist);
            return data;


        }
        return null;
        }

    @Override
    public void logout(String token) {
        redisTemplate.delete(token);
    }

}
