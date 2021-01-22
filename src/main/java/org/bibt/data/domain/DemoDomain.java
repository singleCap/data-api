package org.bibt.data.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 实体Demo
 *
 * @author zengfanyong
 * @date 2021/1/21 22:55
 */
@Data
@AllArgsConstructor
public class DemoDomain {
    /**
     * 编号
     */
    @ApiModelProperty(value = "编号")
    private final int id;
    /**
     * 内容
     */
    @ApiModelProperty(value = "内容")
    private final String content;
}
