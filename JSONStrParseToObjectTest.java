package cn.com.tcsl.datacube.sys.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.ArrayList;
import java.util.Map;

/**
 * @Description:
 * @Auther: zhangpeng
 * @Date: 2018/12/27
 */
public class JSONStrParseToObjectTest {
    private static String hexString = "{\"data\":{\"id\":null,\"fullname\":\"\\xe5\\xa4\\xa9\\xe5\\xa4\\xa9\",\"username\":\"15900322627\",\"password\":\"8a6f2805b4515ac12058e79e66539be9\",\"confirmPassword\":null,\"mobile\":\"15900322627\",\"email\":null,\"idCard\":null,\"type\":null,\"status\":1,\"creater\":\"\\xe5\\xa4\\xa9\\xe5\\xa4\\xa9\",\"operator\":null,\"timestamp\":2018,\"province\":null,\"city\":null,\"region\":null,\"address\":null,\"orgCode\":\"9759\",\"orgName\":\"\\xe9\\x87\\x8d\\xe5\\xba\\x86\\xe6\\x9d\\x8e\\xe5\\xad\\x90\\xe5\\x9d\\x9d\\xe9\\xa4\\x90\\xe9\\xa5\\xae\\xe6\\x80\\xbb\\xe9\\x83\\xa8\",\"excludeItem\":null,\"roleCode\":\"201711110000\",\"photo\":null,\"servicerCode\":null,\"roleName\":\"\\xe7\\xae\\xa1\\xe7\\x90\\x86\\xe5\\x91\\x98\",\"merchantName\":null,\"bizCounts\":1,\"corporationCodes\":[{\"id\":\"000036\"}],\"corporationUserDTOS\":[{\"page\":1,\"limit\":20,\"id\":\"181123161143000079\",\"userId\":\"180101063535000032\",\"userNickName\":\"\\xe5\\xa4\\xa9\\xe5\\xa4\\xa9\",\"userName\":null,\"mobile\":\"15900322627\",\"roleId\":\"181123161143000081\",\"roleName\":\"\\xe7\\xae\\xa1\\xe7\\x90\\x86\\xe5\\x91\\x98\",\"roleSymbol\":\"admin\",\"corporationCode\":\"000036\",\"corporationName\":\"\\xe5\\xb0\\x8f\\xe4\\xbc\\x99\\xe5\\xad\\x90\",\"corporationLogo\":null,\"isActive\":true,\"activeTime\":\"2018-11-23 16:22:22\",\"isDel\":null,\"operater\":null,\"lastLoginTime\":\"2018-12-26 09:07:15\",\"disable\":true}]}}";


    public static JModel addTree(Map.Entry<String, Object>  jsonObject){
        System.out.println(jsonObject.toString());
        JModel j = new JModel();
        j.setSName(jsonObject.getKey());
        if(jsonObject.getValue() != null){
            if (jsonObject.getValue().getClass().equals(JSONObject.class)){
                for (Map.Entry<String, Object> s :((JSONObject)jsonObject.getValue()).entrySet()){
                    j.getChildList().add(addTree(s));
                }
            }
            if (jsonObject.getValue().getClass().equals(JSONArray.class)){
                for(Object xx: (JSONArray)jsonObject.getValue()){
                    JSONObject xxx = (JSONObject)xx; 
                    JModel x = new JModel();
                    x.setSName("+++");
                    for (Map.Entry<String, Object> s :xxx.entrySet()){
                        x.getChildList().add(addTree(s));
                    }
                    j.getChildList().add(x);
                }

            }
        }
        return j;
    }

    public static void main(String[] args) throws Exception {
        JSONObject param = JSON.parseObject(hexString.replaceAll("\\\\","*"));
        System.out.println(param);
        JModel source = new JModel();
        for(Map.Entry<String, Object> e : param.entrySet()){
            source.setSName(e.getKey());
            source.getChildList().add(addTree(e));
        }

        System.out.println(source);
//        for(Map.Entry<String, Object> e : resultTable.entrySet()){
//            System.out.println("key="+e.getKey()+";  value="+e.getValue());
//        }


//        String source = "study 啊哈 and make progress everyday";
//        System.out.println("source : "+source);
//
//        String hexStr = bytes2HexStr(source.getBytes("utf8"));  //编码
//        System.out.println("encode result : "+ hexStr);
//
//        String rawSource = new String(hexStr2Bytes(hexString),"utf8");  //解码
//        System.out.println("decode result : "+rawSource);

    }

    //byte转为hex串
    static String bytes2HexStr(byte[] byteArr) {
        if (null == byteArr || byteArr.length < 1) return "";
        StringBuilder sb = new StringBuilder();
        for (byte t : byteArr) {
            if ((t & 0xF0) == 0) sb.append("0");
            sb.append(Integer.toHexString(t & 0xFF));  //t & 0xFF 操作是为去除Integer高位多余的符号位（java数据是用补码表示）
        }
        return sb.toString();
    }

    //hex串转为byte
    static byte[] hexStr2Bytes(String hexStr) {
        System.out.println(hexStr);
        hexStr = hexStr.replaceAll("\\\\x","");
        System.out.println(hexStr);
        if (null == hexStr || hexStr.length() < 1) return null;

        int byteLen = hexStr.length() / 2;
        byte[] result = new byte[byteLen];
        char[] hexChar = hexStr.toCharArray();
        for(int i=0 ;i<byteLen;i++){
            result[i] = (byte)(Character.digit(hexChar[i*2],16)<<4 | Character.digit(hexChar[i*2+1],16));
        }

        return result;
    }

    @Data
    public static class JModel {
        private String sName;
        private ArrayList<JModel> childList = new ArrayList<>();
    }

}
