package org.bibt.data.util;

/**
 * String工具
 *
 * @author zengfanyong
 * @date 2020/11/18 10:23
 */
public class StringUtils {
    /** 空字符串 */
    public static final String EMPTY = "";

    /** 禁止创建对象 */
    private StringUtils() {
        throw new UnsupportedOperationException("Construct StringUtils");
    }

    /**
     * 检查字符串是否为空
     * null 和 ""
     *
     * @param cs
     *      输入的字符串
     * @return boolean
     *      true：空
     *      false：非空
     */
    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    /**
     * 检查字符串是否不为空
     * 非null 和 非""
     *
     * @param cs
     *      输入的字符串
     * @return boolean
     *      true：非空
     *      false：空
     */
    public static boolean isNotEmpty(final CharSequence cs) {
        return !isEmpty(cs);
    }

    /**
     * 检查字符串是否为空白
     * null 和 "" 和 "任意数量的空白字符"
     *
     * @param s
     *      输入的字符串
     * @return boolean
     *      true：空白
     *      false：非空白
     */
    public static boolean isBlank(final String s) {
        if (isEmpty(s)) {
            return true;
        }

        return s.trim().length() == 0;
    }

    /**
     * 检查字符串是否为空白
     * 非null 和 非"" 和 非"任意数量的空白字符"
     *
     * @param s
     *      输入的字符串
     * @return boolean
     *      true：非空白
     *      false：空白
     */
    public static boolean isNotBlank(final String s) {
        return !isBlank(s);
    }


}
