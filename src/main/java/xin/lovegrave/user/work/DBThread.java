package xin.lovegrave.user.work;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import xin.lovegrave.user.domain.Systemlog;
import xin.lovegrave.user.service.SystemLogService;

import java.util.Date;

/**
 * 线程池中工作的线程
 * spring 多例
 */
@Component
@Scope("prototype")
public class DBThread implements Runnable {
    private String msg;
    private Logger log = LoggerFactory.getLogger(DBThread.class);

    @Autowired
    SystemLogService systemLogService;



    @Override
    public void run() {
        //模拟在缓存插入数据
        Systemlog systemlog = new Systemlog();
        systemlog.setTime(new Date());
        systemlog.setLogdescribe(msg);
        systemLogService.insert(systemlog);
        log.info("insert->" + msg);
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
