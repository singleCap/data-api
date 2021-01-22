package org.bibt.data.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 测试实体
 *
 * @author zengfanyong
 * @date 2021/1/21 22:55
 */
@Data
@AllArgsConstructor
public class TestDomain {
    /** 编号 */
    private final int id;
    /** 内容 */
    private final String content;
}
