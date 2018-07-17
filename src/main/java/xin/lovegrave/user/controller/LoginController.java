package xin.lovegrave.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xin.lovegrave.user.ehcache.EhCacheServiceImpl;
import xin.lovegrave.user.redis.RedisService;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private RedisService redisService;

    @Autowired
    private EhCacheServiceImpl ehCacheService;

    @RequestMapping("/set")
    public void set(){
        for (int i = 0; i < 100; i++) {
            redisService.setString("qq"+i,"在家照顾孩子的妈妈（工作家庭两不误）"+i,2000);
        }

    }

    @RequestMapping("/setValue")
    public void setValue(){
        for (int i = 0; i < 100; i++) {
            ehCacheService.save("cache","ww"+i,"在家照顾孩子的妈妈（工作家庭两不误）"+i);
        }
    }

    @RequestMapping("/get")
    public String get(){
        String str = "";
        for (int i = 0; i < 100; i++) {
            str+=redisService.getString("qq"+i);
        }
        return str;
    }
    @RequestMapping("/getValue")
    public Object getValue(){
        String str = "";
        for (int i = 0; i <100 ; i++) {
            str+=ehCacheService.getValue("cache","ww"+i);
        }
        return  str;
    }
}
