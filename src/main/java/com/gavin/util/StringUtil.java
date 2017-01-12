package com.gavin.util;

import com.alibaba.fastjson.JSON;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StringUtil {

    /* 将list转为以split分割的字符串，首尾都包含分割符，比如：|a|b|c|d| */
    public static <T> String listToString(List<T> list, String split) {
        if (list == null || list.size() == 0) {
            return null;
        }
        StringBuffer buf = new StringBuffer();
        buf.append(split);
        for (T t : list) {
            buf.append(t.toString());
            buf.append(split);
        }
        return buf.toString();
    }

    public static String getUtf8(String str) {
        return getUtf8(str, null);
    }

    public static String getUtf8(String str, String encoding) {
        if (isEmpty(str)) {
            return null;
        }
        String retStr = null;
        try {
            if (!StringUtil.isEmpty(encoding)) {
                retStr = new String(str.getBytes(encoding), "UTF-8");
                return retStr;
            }

            if (new String(str.getBytes("ISO-8859-1"), "ISO-8859-1").equals(str)) {
                System.out.println("===========ISO-8859-1");

                retStr = new String(str.getBytes("ISO-8859-1"), "UTF-8");

            } else if (new String(str.getBytes("UTF-8"), "UTF-8").equals(str)) {
                System.out.println("UTF-8");
                retStr = str;
            } else if (new String(str.getBytes("GBK"), "GBK").equals(str)) {
                System.out.println("GBK");
                retStr = new String(str.getBytes("GBK"), "UTF-8");

            } else if (new String(str.getBytes("GB2312"), "GB2312").equals(str)) {
                System.out.println("GB2312");
                retStr = new String(str.getBytes("GB2312"), "UTF-8");
            }
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        return retStr;
    }

    /**
     * @param str
     * @param len
     * @return 取str的最多len位，若str的长度小于len，返回全部
     */
    public static String getSummary(String str, Integer len) {
        if (str == null || str.length() <= len) {
            return str;
        }
        return str.substring(0, len - 3) + "...";
    }

    public static String getSummary(String str, Integer len, String fix) {
        if (str == null || str.length() <= len) {
            return str;
        }

        return str.substring(0, len - fix.length()) + fix;

    }

    /* 将array转为以split分割的字符串，首尾都包含分割符，比如：|a|b|c|d| */
    public static <T> String arrayToString(T[] array, String split) {
        if (array == null) return "";
        StringBuffer buf = new StringBuffer();
        buf.append(split);
        for (T t : array) {
            buf.append(t.toString());
            buf.append(split);
        }
        return buf.toString();
    }

    /**
     * 将roleIds转为以List<String>
     *
     * @param roleIds 格式为|1|2|3|4|
     * @return
     */
    public static List<String> roleIdToList(String roleIds) {
        if (isEmpty(roleIds)) {
            return null;
        }

        String roleIdArr[] = roleIds.substring(1, roleIds.length() - 1).split("\\|");
        //获取不重复的roleId ID列表
        Set<String> roleIdsSet = new LinkedHashSet<String>();//按顺序
        for (String roleIdStr : roleIdArr) {
            roleIdsSet.add(roleIdStr);
        }

        List<String> roleIdList = new ArrayList<String>(roleIdsSet);

        return roleIdList;
    }

    /**
     * 将roleIds转为以List<Integer>
     *
     * @param roleIds 格式为|1|2|3|4|
     * @return
     */
    public static List<Integer> roleIdToListInt(String roleIds) {
        if (isEmpty(roleIds)) {
            return null;
        }

        String roleIdArr[] = roleIds.substring(1, roleIds.length() - 1).split("\\|");

        //获取不重复的roleId ID列表
        Set<Integer> roleIdsSet = new LinkedHashSet<Integer>();//按顺序
        for (String roleIdStr : roleIdArr) {
            roleIdsSet.add(Integer.valueOf(roleIdStr));
        }

        List<Integer> roleIdList = new ArrayList<Integer>(roleIdsSet);

        return roleIdList;
    }

    /**
     * @param strings |1|2|3|4| 字符串 转化为list<Integer>格式
     * @return strings 格式为|1|2|3|4|
     */
    public static List<Integer> toList(String strings) {

        List<Integer> list = new ArrayList<Integer>();
        list = toList(strings, "|");
        return list;
    }

    /**
     * @param strings |1|2|3|4| 字符串 转化为list<Integer>格式
     * @return strings 格式为|1|2|3|4|
     */
    public static List<Integer> toList(String strings, String spliteStr) {
        if (isEmpty(strings)) {
            return null;
        }
        switch (spliteStr) {
            case "|":
                spliteStr = "\\|";
                break;
            case "*":
                spliteStr = "\\*";
                break;
            case "+":
                spliteStr = "\\+";
                break;
            default:
                break;
        }
        List<Integer> list = new ArrayList<Integer>();
        String stringArr[] = strings.split(spliteStr);
        for (String item : stringArr) {
            if (isEmpty(item)) {//如果为空，则跳过
                continue;
            }
            list.add(Integer.valueOf(item));
        }
        return list;
    }

    public static Double toDouble(String str) {
        return toDouble(str, 0d);
    }

    public static Double toDouble(String str, Double defaultValue) {
        if (StringUtil.isEmpty(str))
            return defaultValue;

        try {
            return Double.valueOf(str.trim());
        } catch (Exception e) {
            e.printStackTrace();
            return defaultValue;
        }
    }

    public static Float toFloat(String str) {
        return toFloat(str, 0f);
    }

    public static Float toFloat(String str, Float defaultValue) {
        if (StringUtil.isEmpty(str))
            return defaultValue;

        try {
            return Float.valueOf(str.trim());
        } catch (Exception e) {
            // e.printStackTrace();
            return defaultValue;
        }
    }

    /**
     * 将sex 性别和int的对应
     *
     * @param sex 性别的中文字 男 ，女，未知等
     * @return ，0未知，1男，2女，3其他
     */
    public static Integer getSexInt(String sex) {
        if (StringUtil.isEmpty(sex)) {
            return 0;
        } else if ("男".equals(sex) || "男性".equals(sex)) {
            return 1;
        } else if ("女".equals(sex) || "女性".equals(sex)) {
            return 2;
        } else {
            return 3;
        }

    }

    public static String getSexStr(Integer sex) {
        if (sex == null || sex == 0) {
            return "未知";
        } else if (sex == 1) {
            return "男";
        } else if (sex == 2) {
            return "女";
        } else {
            return "其他";
        }
    }


    /* 将字符串转为Integer，作空指针和首尾空检查,如果异常，返回缺省值defaultValue */
    public static Integer toInt(String str, Integer defaultValue) {
        return StringToInteger(str, defaultValue);
    }

    public static Integer toInt(String str) {
        return StringToInteger(str, null);
    }

    public static Integer StringToInteger(String str) {
        return StringToInteger(str, null);
    }

    public static Integer StringToInteger(String str, Integer defaultValue) {
        if (StringUtil.isEmpty(str))
            return defaultValue;

        try {
            return Integer.valueOf(str.trim());
        } catch (Exception e) {
            e.printStackTrace();
            return defaultValue;
        }

    }


    /**
     * 将字符串转为Integer，作空指针和首尾空检查,如果异常，返回缺省值defaultValue
     *
     * @param str
     * @param defaultValue
     * @return
     */
    public static Long StringToLong(String str, Long defaultValue) {
        if (StringUtil.isEmpty(str))
            return defaultValue;

        try {
            return Long.valueOf(str.trim());
        } catch (Exception e) {
            e.printStackTrace();
            return defaultValue;
        }
    }

    public static Long StringToLong(String str) {
        return StringToLong(str, null);
    }

    public static boolean isEmpty(String value, boolean trim, char... trimChars) {
        if (trim)
            return value == null || trim(value, trimChars).length() <= 0;
        return value == null || value.length() <= 0;
    }

    public static boolean isEmpty(String value, boolean trim) {
        return isEmpty(value, trim, ' ');
    }

    public static boolean isEmpty(String value) {
        return isEmpty(value, true);
    }

    public static String nullSafeString(String value) {
        return value == null ? "" : value;
    }

    public static String trim(String value) {
        value = trim(3, value, ' ');//英文
        value = trim(3, value, '　');//中文全角空格
        return value;
    }

    public static String trim(String value, char... chars) {
        return trim(3, value, chars);
    }

    public static String trimStart(String value, char... chars) {
        return trim(1, value, chars);
    }

    public static String trimEnd(String value, char... chars) {
        return trim(2, value, chars);
    }

    private static String trim(int mode, String value, char... chars) {
        if (value == null || value.length() <= 0)
            return value;
        value = value.trim();
        int startIndex = 0, endIndex = value.length(), index = 0;
        if (mode == 1 || mode == 3) {
            // trim头部
            while (index < endIndex) {
                if (contains(chars, value.charAt(index++))) {
                    startIndex++;
                    continue;
                }
                break;
            }
        }

        if (startIndex >= endIndex)
            return "";

        if (mode == 2 || mode == 3) {
            // trim尾部
            index = endIndex - 1;
            while (index >= 0) {
                if (contains(chars, value.charAt(index--))) {
                    endIndex--;
                    continue;
                }
                break;
            }
        }

        if (startIndex >= endIndex)
            return "";

        return value.substring(startIndex, endIndex);
    }

    private static boolean contains(char[] chars, char chr) {
        if (chars == null || chars.length <= 0)
            return false;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == chr)
                return true;
        }
        return false;
    }

    public static String URLDecode(String str) {
        if (str == null)
            return "";
        try {
            return URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return str;
        } catch (Exception e) {
            e.printStackTrace();
            return str;
        }
    }

    /**
     * 判断输入的字符串是否为自然数
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {

        if (str == null || "".equals(str)) {
            return false;
        }

        Pattern pattern = Pattern.compile("^0$|(^(\\+)?[1-9]([0-9]*))");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }


    /**
     * 输出内容保留空格，换行等
     *
     * @param content
     * @return
     */
    public static String outHtml(String content) {
        if (isEmpty(content)) {
            return "";
        }

        content = ((content.replaceAll("<(.+?)>", "&lt;$1&gt;")).replaceAll(" ", "&nbsp;")).replaceAll("\n", "<br>");

        return content;
    }


    /**
     * 过滤sql 注入的语法 , = '
     *
     * @param content
     * @return
     */
    public static String filterSql(String content) {
        if (isEmpty(content)) {
            return "";
        }

        content = content.replaceAll("'", "").replaceAll(",", "").replaceAll("=", "");

        return content;
    }


    /**
     * 判断一个图片路径是否为http开头，或则/开头，或则ID开头，然后返回一阁url路径
     *
     * @param
     */
    public static String getImgUrl(String image) {
        return getImgUrl(image, "");
    }

    /**
     * 判断一个图片路径是否为http开头，或则/开头，或则ID开头，然后返回一阁url路径
     *
     * @param image
     * @param baseUrl
     */

    public static String getImgUrl(String image, String baseUrl) {
        if (isEmpty(baseUrl)) {
            baseUrl = "";
        }
        if (isEmpty(image)) {
            return "";
        }
        if (image.startsWith("http")) {
            return image;
        } else if (image.startsWith("/")) {
            return baseUrl + image;
        } else if (image.startsWith("API")) {
            return baseUrl + "/" + image;
        } else {
            return baseUrl + "/API/image/get.do?imageId=" + image;
        }
    }

    /**
     * 判断字符串分割后是否含有目标的字符串
     *
     * @param str    源字符串
     * @param split  分隔符
     * @param tarStr 目标字符串
     * @return
     */
    public static boolean contains(String str, String split, String tarStr) {
        if (isEmpty(str) || isEmpty(split) || isEmpty(tarStr)) {
            return false;
        }
        String[] splitedStr = str.split(split);
        for (String singleStr : splitedStr) {
            if (tarStr.equals(singleStr)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param doub
     * @param formatStr 转换格式  默认为钱币格式(#,##0.00) 如:123,456,789.12
     *                  还可以是###0.0或###0.00等
     * @return
     */
    public static String formatDouble(Object doub, String formatStr) {
        if (doub == null) {
            return "0.0";
        }
        if (isEmpty(formatStr)) {
            formatStr = "#,##0.00";
        }

        DecimalFormat decimalFormat = new DecimalFormat(formatStr);//格式化设置
        return decimalFormat.format(doub);
    }

    public static String formatDouble(Object doub) {
        if (doub == null) {
            return "0.0";
        }
        return formatDouble(doub, "#,##0.00");
    }

    /**
     * 格式化数据，返回纯数字
     *
     * @param doub
     * @param decimalDigit 保留小数位
     * @param divisor      除数，比如统计图标x轴单位为“万元”，需要除以10000
     * @return 无“,”号，无单位，纯数字
     */
    public static String formatDouble(Double doub, int decimalDigit, int divisor) {
        if (doub == null) {
            return "0.0";
        }
        if (divisor == 0) {//除数不能为0
            divisor = 1;
        }

        return BigDecimal.valueOf(doub).divide(new BigDecimal(divisor)).setScale(decimalDigit, BigDecimal.ROUND_HALF_UP).toPlainString();
    }


    /**
     * @param strs "adad,asdasd,司法所地方，第三方，sdfa,adf"(中英文逗号)
     * @return
     */
    public static List<String> getStringList(String strs) {
        List<String> list = new ArrayList<String>();
        if (strs == null) {
            return null;
        }
        String[] strsArray1 = strs.split(",");
        for (String strs1 : strsArray1) {
            String[] productArray = strs1.split("，");
            for (String str : productArray) {
                list.add(str);
            }
        }
        return list;
    }

    public static List<String> getListByCommaStr(String strs) {
        List<String> list = new ArrayList<String>();
        String[] strsArray = strs.split(",");
        for (String strs1 : strsArray) {
            String[] strArray = strs1.split("，");
            for (String str : strArray) {
                list.add(str);
            }
        }
        return list;
    }

    public static Integer indexof(String str, String searchvalue) {
        return str.indexOf(searchvalue);
    }

    /**
     * 过滤特殊字符表情方法
     *
     * @param codePoint
     * @return
     */
    public static boolean isNotEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) ||
                (codePoint == 0x9) ||
                (codePoint == 0xA) ||
                (codePoint == 0xD) ||
                ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
                ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }

    /**
     * 过滤emoji 或者 其他非文字类型的字符
     *
     * @param source
     * @return
     */
    public static String filterEmoji(String source) {
        if (StringUtil.isEmpty(source)) {
            return "";
        }
        int len = source.length();
        StringBuilder buf = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (isNotEmojiCharacter(codePoint)) {
                buf.append(codePoint);
            } else {
                buf.append("*");
            }
        }
        return buf.toString();
    }

    /***************************************************************************
     * repeat - 通过源字符串重复生成N次组成新的字符串。
     *
     * @param src
     *            - 源字符串 例如: 空格(" "), 星号("*"), "0", "浙江" 等等...
     * @param num
     *            - 重复生成次数
     * @return 返回已生成的重复字符串
     * @author kaka
     **************************************************************************/
    public static String repeat(String src, int num) {
        StringBuffer s = new StringBuffer();
        for (int i = 0; i < num; i++)
            s.append(src);
        return s.toString();
    }

    /**
     * 返回byte的数据大小对应的文本
     *
     * @param size
     * @return
     */
    public static String getDataSize(long size) {
        DecimalFormat formater = new DecimalFormat("####.00");
        if (size < 1024) {
            return size + "bytes";
        } else if (size < 1024 * 1024) {
            float kbsize = size / 1024f;
            return formater.format(kbsize) + "KB";
        } else if (size < 1024 * 1024 * 1024) {
            float mbsize = size / 1024f / 1024f;
            return formater.format(mbsize) + "MB";
        } else if (size < 1024 * 1024 * 1024 * 1024) {
            float gbsize = size / 1024f / 1024f / 1024f;
            return formater.format(gbsize) + "GB";
        } else {
            float gbsize = size / 1024f / 1024f / 1024f;
            return formater.format(gbsize) + "GB";
        }
    }

    /**
     * 输入数组形式 输出JSON {i: "...",}
     *
     * @param str
     * @return
     */
    public static String array2JSON(String... str) {
        Map<Integer, String> param = new HashMap<>();
        for (int i = 0; i < str.length; i++) {
            param.put(i, str[i]);
        }
        return JSON.toJSONString(param);
    }
}