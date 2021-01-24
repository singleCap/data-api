package org.bibt.data.base.event;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

import java.io.Serializable;

/**
 * 基础事件
 *
 * @author ZengFanyong
 * @date 2021/1/23
 */
@Getter
@Setter
@ToString
public abstract class AbstractSpringEvent extends ApplicationEvent implements Serializable{
    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = 1L;

    private String eventId;

    public AbstractSpringEvent(Object source) {
        super(source);
    }
}
