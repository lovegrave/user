package xin.lovegrave.user.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wy
 * @title Bean封装的工具类
 */
public class BeanUtil {
    private static final Log logger = LogFactory.getLog(BeanUtil.class);
    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * 将Map中的键值全转换成驼峰
     *
     * @param map 请求
     * @return Map
     */
    private static Pattern linePattern = Pattern.compile("_(\\w)");

    /**
     * 将请求参数封装成Bean对象
     *
     * @param request 请求
     * @param cls     类class
     * @return Object
     */
    public static <T> T createBean(HttpServletRequest request, Class<T> cls) {
        try {
            T obj = cls.newInstance();
            Map map = request.getParameterMap();
            BeanUtils.populate(obj, map);
            return obj;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    /**
     * 将Map封装成Bean对象
     *
     * @param map 请求
     * @param cls 类class
     * @return Object
     */
    public static <T> T createBean(Map map, Class<T> cls) {
        try {
            // 清空sqlMapConfig设置可以返回null,不然会报错
            Map noNullMap = new HashMap(16);
            map.forEach((k, v) -> {
                if (v != null) {
                    noNullMap.put(k, v);
                }
            });

            T obj = cls.newInstance();
            BeanUtils.populate(obj, noNullMap);
            return obj;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    /**
     * 将  orig 对象的属性赋值到 dest 对象中
     *
     * @param dest
     * @param orig
     */
    public static void copyProperties(Object dest, Object orig) {
        try {
            org.springframework.beans.BeanUtils.copyProperties(orig,dest);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 将Map中的键值全转换成小写
     *
     * @param map 请求
     * @return Map
     */
    public static Map mapKeyToLowerCase(Map map) {
        try {
            Map rv = new HashMap(16);
            for (Object obj : map.entrySet()) {
                Map.Entry me = (Map.Entry) obj;
                rv.put(me.getKey().toString().toLowerCase(), me.getValue());
            }
            return rv;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    public static Map mapKeyToHump(Map map) {
        try {
            Map rv = new HashMap(16);
            for (Object obj : map.entrySet()) {
                Map.Entry me = (Map.Entry) obj;
                // 转化为小写
                String key = me.getKey().toString().toLowerCase();

                Matcher matcher = linePattern.matcher(key);
                StringBuffer sb = new StringBuffer();
                while (matcher.find()) {
                    matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
                }
                matcher.appendTail(sb);

                rv.put(sb.toString(), me.getValue());
            }
            return rv;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }


    /**
     * 将以json方式提交的字符串封装成Bean对象
     *
     * @param json json对象
     * @param cls  bean的class
     * @return Object
     */
    public static <T> T createBean(String json, Class<T> cls) {
        if (json == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mapper.setDateFormat(formatter);
        try {
            return mapper.readValue(json, cls);
        } catch (IOException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    /**
     * 将对象转换成字符串输出
     *
     * @param obj 某对象
     * @return
     */
    public static String objectToString(Object obj) {
        if (obj instanceof String) {
            return (String) obj;
        } else if (obj instanceof Boolean) {
            return obj.toString();
        } else if (obj instanceof Date) {
            return FORMATTER.format(obj);
        } else if (obj instanceof BigDecimal) {
            return ((BigDecimal) obj).toPlainString();
        } else if (obj instanceof Double) {
            return (new BigDecimal((Double) obj)).toPlainString();
        } else if (obj instanceof Float) {
            return (new BigDecimal((Float) obj)).toPlainString();
        } else if (obj instanceof Long) {
            return obj.toString();
        } else if (obj instanceof Integer) {
            return obj.toString();
        } else if (obj instanceof Short) {
            return obj.toString();
        }
        return null;
    }

    /**
     * 将对象转换成字符串输出(如果为空则用指定值替换)
     *
     * @param obj      某对象
     * @param paddingV 替换字符串
     * @return
     */
    public static String objectToString(Object obj, String paddingV) {
        if (isNullAndEmpty(obj)) {
            return paddingV;
        } else {
            return objectToString(obj);
        }
    }

    /**
     * 判断对象是否为NULL或者是空字符串
     *
     * @param obj 某字符串
     * @return 包含则返回true，否则返回false
     */
    public static boolean isNullAndEmpty(Object obj) {
        if (null == obj) {
            return true;
        } else if (obj instanceof String) {
            return "".equalsIgnoreCase(obj.toString());
        } else if (obj instanceof List) {
            return ((List) obj).isEmpty();
        } else if (obj instanceof Map) {
            return ((Map) obj).isEmpty();
        }
        return false;
    }

    /**
     * 判断对象是否不为NULL或者是空字符串
     *
     * @param obj 某字符串
     * @return 包含则返回true，否则返回false
     */
    public static boolean notNullAndEmpty(Object obj) {
        return !isNullAndEmpty(obj);
    }

    /**
     * 对象转换json字符串
     *
     * @param obj 对象
     * @return String
     */
    public static String toJsonString(Object obj) {
        if (obj == null) {
            return null;
        }
        // can reuse, share globally
        ObjectMapper mapper = new ObjectMapper();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mapper.setDateFormat(formatter);
        try {
            StringWriter sb = new StringWriter();
            mapper.writeValue(sb, obj);
            return sb.toString();
        } catch (IOException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

}
