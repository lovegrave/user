package xin.lovegrave.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xin.lovegrave.user.domain.Systemlog;
import xin.lovegrave.user.ehcache.EhCacheServiceImpl;
import xin.lovegrave.user.service.SystemLogService;

/**
 * @author HL
 */
@Service
public class SystemLogServiceImpl implements SystemLogService {
    @Autowired
    EhCacheServiceImpl ehCacheService;
    @Override
    public void insert(Systemlog systemlog) {
        System.err.println(systemlog.getTime()+systemlog.getLogdescribe());
    }
}
