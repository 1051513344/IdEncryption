package com.laoxu.idjiami.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmojiFilter {

    private static Scanner cin;

    public static void main(String[] args) {
        System.out.println("1234567".substring(0, 6));
    }


    /**
     * 检测是否有emoji字符
     * @param source
     * @return 一旦含有就抛出
     */
    public static boolean containsEmoji(String source) {
        if (StringUtils.isBlank(source)) {
            return false;
        }

        int len = source.length();

        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);

            if (!isNotEmojiCharacter(codePoint)) {
                // do nothing，判断到了这里表明，确认有表情字符
                return true;
            }
        }

        return false;
    }

    /**
     * 过滤emoji 或者 其他非文字类型的字符
     * @param source
     * @return
     */
    public static String filterEmoji(String source) {

        if (!containsEmoji(source)) {
            return source;// 如果不包含，直接返回
        }
        // 到这里铁定包含
        StringBuilder buf = null;

        int len = source.length();

        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);

            if (isNotEmojiCharacter(codePoint)) {
                if (buf == null) {
                    buf = new StringBuilder(source.length());
                }

                buf.append(codePoint);
            } else {
            }
        }

        if (buf == null) {
            return "";
        } else {
            if (buf.length() == len) {// 这里的意义在于尽可能少的toString，因为会重新生成字符串
                buf = null;
                return source;
            } else {
                return buf.toString();
            }
        }

    }

    private static boolean isNotEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) || (codePoint == 0xD)
                || ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
                || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
                || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }

    /**
     * 过滤emoji 或者 其他非文字类型的字符
     * @param source
     * @return
     */
    public static String filterEmoji2(String source) {
        int len = source.length();
        StringBuilder buf = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (isNotEmojiCharacter(codePoint)) {
                buf.append(codePoint);
            }
        }
        return buf.toString();
    }

    public static boolean isContainsEmoji(String source) {
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (!isNotEmojiCharacter(codePoint)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @Description 将字符串中的emoji表情转换成可以在utf-8字符集数据库中保存的格式（表情占4个字节，需要utf8mb4字符集）
     * @param str 待转换字符串
     * @return 转换后字符串
     * @throws UnsupportedEncodingException exception
     */
    public static String emojiConvert1(String str) {
        String patternString = "([\\x{10000}-\\x{10ffff}\ud800-\udfff])";

        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            try {
                matcher.appendReplacement(sb,
                        "[[" + URLEncoder.encode(matcher.group(1), "UTF-8") + "]]");
            } catch (UnsupportedEncodingException e) {
                System.out.println(e.getMessage());
            }
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * @Description 还原utf8数据库中保存的含转换后emoji表情的字符串
     * @param str 转换后的字符串
     * @return 转换前的字符串
     * @throws UnsupportedEncodingException exception
     */
    public static String emojiRecovery2(String str){
        if (StringUtils.isBlank(str)){
            return str;
        }
        String patternString = "\\[\\[(.*?)\\]\\]";

        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(str);

        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            try {
                matcher.appendReplacement(sb, URLDecoder.decode(matcher.group(1), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                System.out.println(e.getMessage());
            }
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    public static String emojiRecovery3(String str) throws UnsupportedEncodingException {
        if (StringUtils.isBlank(str)){
            return str;
        }
        String patternString = "\\[\\[(.*?)\\]\\]";

        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(str);

        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "?");
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * @Description 将字符串中的emoji表情转换成可以在utf-8字符集数据库中保存的格式（表情占4个字节，需要utf8mb4字符集）
     * @param str 待转换字符串
     * @return 转换后字符串
     * @throws UnsupportedEncodingException exception
     */
    public static String emojiConvert(String str){
        if (null == str) {
            return str;
        }
        String patternString = "([\\x{10000}-\\x{10ffff}\ud800-\udfff])";

        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            try {
                matcher.appendReplacement(sb,
                        "#_" + Base64.encodeBase64URLSafeString(matcher.group(1).getBytes("UTF-8"))
                                + "_#");
            } catch (UnsupportedEncodingException e) {
                System.out.println(e.getMessage());
            }
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * @Description 还原utf8数据库中保存的含转换后emoji表情的字符串
     * @param str 转换后的字符串
     * @return 转换前的字符串
     * @throws UnsupportedEncodingException exception
     */
    public static String emojiRecovery(String str){
        if (null == str) {
            return str;
        }
        String patternString = "#_(.*?)_#";

        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(str);

        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            try {
                matcher.appendReplacement(sb,
                        new String(Base64.decodeBase64(matcher.group(1)), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                System.out.println(e.getMessage());
            }
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * @Description 将字符串中的emoji表情转换成可以在utf-8字符集数据库中保存的格式（表情占4个字节，需要utf8mb4字符集）
     * @param str 待转换字符串
     * @return 转换后字符串
     * @throws UnsupportedEncodingException exception
     */
    public static String emojiConvertWx(String str){
        String patternString = "([\\x{10000}-\\x{10ffff}\ud800-\udfff])";

        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            try {
                matcher.appendReplacement(sb,
                        "#_" + Base64.encodeBase64URLSafeString(matcher.group(1).getBytes("UTF-8"))
                                + "_#");
            } catch (UnsupportedEncodingException e) {
                System.out.println(e.getMessage());
            }
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * @Description 还原utf8数据库中保存的含转换后emoji表情的字符串
     * @param str 转换后的字符串
     * @return 转换前的字符串
     * @throws UnsupportedEncodingException exception
     */
    public static String emojiRecoveryWx(String str){
        String patternString = "#_(.*?)_#";

        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(str);

        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            try {
                matcher.appendReplacement(sb,
                        new String(Base64.decodeBase64(matcher.group(1)), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                System.out.println(e.getMessage());
            }
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}
