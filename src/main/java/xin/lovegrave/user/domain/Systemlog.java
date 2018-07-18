package xin.lovegrave.user.domain;

import lombok.Data;

import java.util.Date;
@Data
public class Systemlog {

    private Date time;
    private String logdescribe;
}
