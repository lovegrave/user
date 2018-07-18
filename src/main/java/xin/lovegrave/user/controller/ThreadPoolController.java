package xin.lovegrave.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xin.lovegrave.user.thread.ThreadPoolManager;

@RestController
public class ThreadPoolController {

    @Autowired
    ThreadPoolManager tpm;

    @RequestMapping("/pool")
    Object test() {
        for (int i = 0; i < 500; i++) {
            //模拟并发500条记录
            tpm.processOrders(Integer.toString(i));
        }

        return "ok";
    }
}
