package com.testg.system.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.testg.system.entity.User;
import com.testg.system.mapper.UserMapper;
import com.testg.system.service.IUserService;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
    //@Autowired
    //private RedisTemplate redisTemplate;

    @Override
    public Map<String, Object> login(User user, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        //查询用户
        //结果不为空，则生成token
        LambdaQueryWrapper<User> Wrapper = new LambdaQueryWrapper<>();
        Wrapper.eq(User::getUsername,user.getUsername());
        Wrapper.eq(User::getPassword,user.getPassword());
        User LoginUser = this.baseMapper.selectOne(Wrapper);
        if(LoginUser != null){
            //使用uuid
            String key = "user:" + UUID.randomUUID();

            LoginUser.setPassword(null);
            //存入session
            HttpSession LoginSession = request.getSession();
            LoginSession.setAttribute("LoginUser",LoginUser);
            LoginSession.setAttribute("key",key);
            //生成cookie
            Cookie cookie_key = new Cookie("cookie_key",key);
            //设置cookie持久化时间：30分钟
            cookie_key.setMaxAge(30 * 60);
            //设置为当前项目全部带有此cookie
            cookie_key.setPath(request.getContextPath());
            //向客户端发送cookie
            response.addCookie(cookie_key);

            //存入redis（已禁用
            //redisTemplate.opsForValue().set(key,loginUser,30, TimeUnit.MINUTES);

            //返回数据
            Map<String, Object> data = new HashMap<>();
            data.put("token",key);
            return data;
        }
        return null;
    }



    @Override
    public Map<String, Object> getUserInfo(HttpServletRequest req, HttpServletResponse resp) {
        //以下为redis登录方案检测token功能，已禁用
        //Object obj = redisTemplate.opsForValue().get(token);
        Object obj = req.getCookies();

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
        public void logout(HttpSession session, HttpServletRequest request, HttpServletResponse response){
            // 删除session里面的用户信息
            session.removeAttribute("LoginUser");
            session.removeAttribute("key");
            // 保存cookie，实现自动登录
            Cookie cookie_username = new Cookie("cookie_key", "");
            // 设置cookie的持久化时间，0
            cookie_username.setMaxAge(0);
            // 设置为当前项目下都携带这个cookie
            cookie_username.setPath(request.getContextPath());
            // 向客户端发送cookie
            response.addCookie(cookie_username);
        }


}


