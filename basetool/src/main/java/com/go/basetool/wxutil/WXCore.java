package com.go.basetool.wxutil;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.apache.commons.codec.binary.Base64;

import net.sf.json.JSONObject;


/**
 * 封装对外访问方法
 * @author liuyazhuang
 *
 */
public class WXCore {

    private static final String WATERMARK = "watermark";
    private static final String APPID = "appid";
    /**
     * 解密数据
     * @return
     * @throws Exception
     */
    public static String decrypt(String appId, String encryptedData, String sessionKey, String iv){
        String result = "";
        try {
            AES aes = new AES();
            byte[] resultByte = aes.decrypt(Base64.decodeBase64(encryptedData), Base64.decodeBase64(sessionKey), Base64.decodeBase64(iv));
            if(null != resultByte && resultByte.length > 0){
                result = new String(WxPKCS7Encoder.decode(resultByte));
                JSONObject jsonObject = JSONObject.fromObject(result);
                String decryptAppid = jsonObject.getJSONObject(WATERMARK).getString(APPID);
                if(!appId.equals(decryptAppid)){
                    result = "";
                }
            }
        } catch (Exception e) {
            result = "";
            e.printStackTrace();
        }
        return result;
    }


    public static void main(String[] args) throws Exception{
//        String appId = "wx4f4bc4dec97d474b";
//        String encryptedData = "CiyLU1Aw2KjvrjMdj8YKliAjtP4gsMZMQmRzooG2xrDcvSnxIMXFufNstNGTyaGS9uT5geRa0W4oTOb1WT7fJlAC+oNPdbB+3hVbJSRgv+4lGOETKUQz6OYStslQ142dNCuabNPGBzlooOmB231qMM85d2/fV6ChevvXvQP8Hkue1poOFtnEtpyxVLW1zAo6/1Xx1COxFvrc2d7UL/lmHInNlxuacJXwu0fjpXfz/YqYzBIBzD6WUfTIF9GRHpOn/Hz7saL8xz+W//FRAUid1OksQaQx4CMs8LOddcQhULW4ucetDf96JcR3g0gfRK4PC7E/r7Z6xNrXd2UIeorGj5Ef7b1pJAYB6Y5anaHqZ9J6nKEBvB4DnNLIVWSgARns/8wR2SiRS7MNACwTyrGvt9ts8p12PKFdlqYTopNHR1Vf7XjfhQlVsAJdNiKdYmYVoKlaRv85IfVunYzO0IKXsyl7JCUjCpoG20f0a04COwfneQAGGwd5oa+T8yO5hzuyDb/XcxxmK01EpqOyuxINew==";
//        String sessionKey = "tiihtNczf5v6AKRyjwEUhQ==";
//        String iv = "r7BXXKkLb8qrSNn05n0qiA==";
        String AppSecret = "fda2c2df008f6937b0c06c0761fdf36e";
        String appId = "wx80a823d84c20aa94";
        String encryptedData = "0GSmIXx9zI2HzIaQ8CqxYz1EcUGuf9gWdI7zcdzU9THCwSzqUGNgri7cf9DH0bWIVPPmXEpNwXzP+cvnbu+ni/DgHxE42tKgv3SXVkvFhSE4FGs9zOYumQnvVdocIk65866VNoAeextzdaZSImBboI/iWvJzvPG/RqWGj1iuEKpSLl83zSG3vaHwpAh7rYItt2GPX1BvC0+0CfNKGjyiCyKAe5BaBjrYil0atr7TpKU7tXdAKneZLupTCPgKrcevCFowl0o41XPvVi2bpvGRkfOFZd5efS6ytsbmqmM+oztB4T52Stk0UUqOtGQipSpd800h6w4LVm29IoVbDsBthbC1Vwa9Ikr+N1kkYHNqP34wQPFGhF4YAdU71VsMMJ17j+DiYM5MZjz+JEhYXxPFSYKXcbFBxalZCZ4JmJreknDk9Qx6ttTyD0s9gQhYUX5X7fk+XmE63NSgEZlRKmWgPpL5BZq3jYsjhk86AKfRH1w=";
        String sessionKey = "5jTEJn4nLynWCPrTPK7esg==";
        String iv = "mNPi5btJN2TZD4E+L6F+vg==";
        String decrypt = decrypt(appId, encryptedData, sessionKey, iv);
        System.out.println(decrypt);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(decrypt);
        String nickName = jsonNode.path("nickName").asText();
        System.out.println(nickName);
        System.out.println(new String(nickName.getBytes(),"utf-8"));
    }
}