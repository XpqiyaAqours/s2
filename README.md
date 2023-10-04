# s2
订单管理系统后端
基于SpringBoot2，MybatisPlus，MySQL，Redis。
完成用户登录注销系统。
通过UUID产生token，通过redis存储token。
完成拦截响应式统一返回值封装，直接返回data自动封装成json，手动抛出异常返回500，全局异常响应。
实现分页查找用户接口，使用了mybatisplus的分页插件。
尚未实现用户权限管理。
尚未实现订单管理功能。


