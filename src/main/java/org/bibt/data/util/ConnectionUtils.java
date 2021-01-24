package org.bibt.data.util;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Objects;

/**
 * 连接资源工具
 *
 * @author zengfanyong
 * @date 2020/11/18 14:58
 */
@Slf4j
public class ConnectionUtils {

    /** 禁止创建对象 */
    private ConnectionUtils() {
        throw new UnsupportedOperationException("Construct ConnectionUtils");
    }

    /**
     * 释放连接资源
     *
     * @param resources
     *      连接资源
     */
    public static void releaseResource(final AutoCloseable... resources) {
        if (resources == null || resources.length == 0) {
            return;
        }

        Arrays.stream(resources).filter(Objects::nonNull)
                .forEach(resource -> {
                    try {
                        resource.close();
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                    }


                });
    }
}
