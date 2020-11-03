package com.go.basetool.img;

import java.util.Base64;

/**
 * 将图片转成Base64字符串
 */
public class Img2Str {
    final static Base64.Decoder decoder = Base64.getDecoder();

    public static String img2Str(byte[] img){
        return decoder.decode(img).toString();
    }
}
