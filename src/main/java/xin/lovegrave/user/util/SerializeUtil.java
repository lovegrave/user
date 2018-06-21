package xin.lovegrave.user.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 序列化工具
 * Created by a on 2018/3/19.
 *
 * @author wy
 */
public class SerializeUtil {

    public static byte[] serialize(Object value) {
        if (value == null) {
            throw new NullPointerException("Can't serialize null");
        }
        byte[] rv = null;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream os = null;
        try {
            bos = new ByteArrayOutputStream();
            os = new ObjectOutputStream(bos);
            os.writeObject(value);
            os.close();
            bos.close();
            rv = bos.toByteArray();
        } catch (IOException e) {
            throw new IllegalArgumentException("Non-serializable object", e);
        } finally {
            try {
                if (os != null) os.close();
                if (bos != null) bos.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return rv;
    }

//    public static Map<byte[],byte[]> serialize(Map<String,List<String>> value) {
//        if (value == null) {
//            throw new NullPointerException("Can't serialize null");
//        }
//        ByteArrayOutputStream bos = null;
//        ObjectOutputStream os = null;
//        Map<byte[],byte[]> mapByte = new HashMap<>();
//        try {
//            for (Map.Entry<String, List<String>> entry : value.entrySet()) {
//                bos = new ByteArrayOutputStream();
//                os = new ObjectOutputStream(bos);
//                os.writeObject(entry.getKey());
//                os.close();
//                bos.close();
//                byte[] rv = bos.toByteArray();
//                bos = new ByteArrayOutputStream();
//                os = new ObjectOutputStream(bos);
//                os.writeObject(entry.getValue());
//                os.close();
//                bos.close();
//                byte[] rv1 = bos.toByteArray();
//                mapByte.put(rv,rv1);
//            }
//        } catch (IOException e) {
//            throw new IllegalArgumentException("Non-serializable object", e);
//        } finally {
//            try {
//                if(os!=null)os.close();
//                if(bos!=null)bos.close();
//            }catch (Exception e2) {
//                e2.printStackTrace();
//            }
//        }
//        return mapByte;
//    }

//    public static Map<String,List<String>> deserialize( Map<byte[],byte[]> in) {
//        ByteArrayInputStream bis = null;
//        ObjectInputStream is = null;
//        Map<String,List<String>> map = new HashMap<>();
//        try {
//            if(in != null) {
//                for (Map.Entry<byte[], byte[]> entry : in.entrySet()){
//                    bis=new ByteArrayInputStream(entry.getKey());
//                    is=new ObjectInputStream(bis);
//                    String rv= (String) is.readObject();
//                    is.close();
//                    bis.close();
//                    bis=new ByteArrayInputStream(entry.getValue());
//                    is=new ObjectInputStream(bis);
//                    List<String> rv1= (List<String>) is.readObject();
//                    is.close();
//                    bis.close();
//                    map.put(rv,rv1);
//                }
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }finally {
//            try {
//                if(is!=null)is.close();
//                if(bis!=null)bis.close();
//            } catch (Exception e2) {
//                e2.printStackTrace();
//            }
//        }
//        return map;
//    }

    public static Object deserialize(byte[] in) {
        Object rv = null;
        ByteArrayInputStream bis = null;
        ObjectInputStream is = null;
        try {
            if (in != null) {
                bis = new ByteArrayInputStream(in);
                is = new ObjectInputStream(bis);
                rv = is.readObject();
                is.close();
                bis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) is.close();
                if (bis != null) bis.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return rv;
    }

    /**
     * 列表序列化（用于Redis整存整取）
     *
     * @param value
     * @return
     */
    public static <T> byte[] serialize(List<T> value) throws IOException {
        if (value == null) {
            throw new NullPointerException("Can't serialize null");
        }
        byte[] rv = null;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream os = null;
        try {
            bos = new ByteArrayOutputStream();
            os = new ObjectOutputStream(bos);
            for (T obj : value) {
                os.writeObject(obj);
            }
            os.writeObject(null);
            os.close();
            bos.close();
            rv = bos.toByteArray();
        } catch (IOException e) {
            throw new IllegalArgumentException("Non-serializable object", e);
        } finally {
            os.close();
            bos.close();
        }
        return rv;
    }

    /**
     * 反序列化列表（用于Redis整存整取）
     *
     * @param in
     * @return
     */
    public static <T> List<T> unserializeForList(byte[] in) throws IOException {
        List<T> list = new ArrayList<T>();
        ByteArrayInputStream bis = null;
        ObjectInputStream is = null;
        try {
            if (in != null) {
                bis = new ByteArrayInputStream(in);
                is = new ObjectInputStream(bis);
                while (true) {
                    T obj = (T) is.readObject();
                    if (obj == null) {
                        break;
                    } else {
                        list.add(obj);
                    }
                }
                is.close();
                bis.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            is.close();
            bis.close();
        }
        return list;
    }
}
