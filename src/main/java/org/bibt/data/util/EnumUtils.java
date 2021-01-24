package org.bibt.data.util;

import lombok.extern.slf4j.Slf4j;

/**
 * 枚举工具
 *
 * @author zengfanyong
 * @date 2020/11/18 17:49
 */
@Slf4j
public class EnumUtils {

    /** 禁止创建对象 */
    private EnumUtils() {
        throw new UnsupportedOperationException("Construct EnumUtils");
    }

    /**
     * 获取枚举实例
     *
     * @param enumClass
     *      枚举类的class信息
     * @param enumName
     *      枚举名称
     * @param <E>
     *      泛型枚举
     * @return
     *      枚举实例
     */
    public static <E extends Enum<E>> E getEnum(final Class<E> enumClass, final String enumName) {
        if (StringUtils.isBlank(enumName)) {
            return null;
        }

        try {
            return Enum.valueOf(enumClass, enumName);
        } catch (final IllegalArgumentException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 判断枚举是否可用
     *
     * @param enumClass
     *      枚举类的class信息
     * @param enumName
     *      枚举名称
     * @param <E>
     *      泛型枚举
     * @return
     *      枚举的可用性
     *      true：可用
     *      false：不可用
     */
    public static <E extends Enum<E>> boolean isAvailableEnum(final Class<E> enumClass, final String enumName) {
        if (StringUtils.isBlank(enumName)) {
            return false;
        }

        try {
            Enum.valueOf(enumClass, enumName);
            return true;
        } catch (final IllegalArgumentException e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }
}
