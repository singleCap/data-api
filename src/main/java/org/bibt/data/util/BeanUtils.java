package org.bibt.data.util;

import lombok.extern.slf4j.Slf4j;
import org.bibt.data.enums.BusinessErrorEnum;
import org.bibt.data.exception.BusinessException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Bean工具
 *
 * @author ZengFanyong
 * @date 2021/1/23
 */
@Slf4j
public class BeanUtils {

    /**
     * 从配置文件复制Bean
     *
     * @param source    配置文件
     * @param target    目标class信息
     * @param <T>       目标类型
     * @return 目标Bean
     */
    public static <T> T copyProperties(Object source, Class<T> target){
        try {
            T t = target.newInstance();
            org.springframework.beans.BeanUtils.copyProperties(source, t);
            return t;
        } catch (Exception e) {
            log.error("【数据转换】数据转换出错，目标对象{}构造函数异常", target.getName(), e);
            throw new BusinessException(BusinessErrorEnum.COPY_ERROR);
        }
    }

    /**
     * 从配置文件复制Bean
     *
     * @param sourceList    List集合
     * @param target        目标class信息
     * @param <T>           目标类型
     * @return 目标Bean
     */
    public static <T> List<T> copyWithCollection(List<?> sourceList, Class<T> target){
        try {
            return sourceList.stream().map(s -> copyProperties(s, target)).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("【数据转换】数据转换出错，目标对象{}构造函数异常", target.getName(), e);
            throw new BusinessException(BusinessErrorEnum.COPY_ERROR);
        }
    }

    /**
     * 从配置文件复制Bean
     *
     * @param sourceList    Set集合
     * @param target        目标class信息
     * @param <T>           目标类型
     * @return 目标Bean
     */
    public static <T> Set<T> copyWithCollection(Set<?> sourceList, Class<T> target){
        try {
            return sourceList.stream().map(s -> copyProperties(s, target)).collect(Collectors.toSet());
        } catch (Exception e) {
            log.error("【数据转换】数据转换出错，目标对象{}构造函数异常", target.getName(), e);
            throw new BusinessException(BusinessErrorEnum.COPY_ERROR);
        }
    }
}
