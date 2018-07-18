package my.fast.admin.framework.utils;

import java.security.MessageDigest;
import java.util.Random;

/**
 * @Author: ChenQingSong
 * @Date: 2018/6/14 15:27
 * @Description:
 */
public class SysUtils {

    public static final String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String Md5encode(String str) {
        return processEncode(str);
    }

    /**
     * 返回MD5码
     * @param str
     * @return
     */
    public static String processEncode(String str) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        char[] charArray = str.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }

            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    /**
     * 创建盐值
     * @param length 盐值位数
     * @return
     */
    public static String createSalt(int length) {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(SysUtils.Md5encode("admin"));
        System.out.println(SysUtils.createSalt(16));
    }
}
