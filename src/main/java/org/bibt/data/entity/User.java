package org.bibt.data.entity;

import lombok.*;
import lombok.experimental.Accessors;
import javax.persistence.Table;

/**
 * 用户
 *
 * @author ZengFanyong
 * @date 2021/1/23
 */
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, of = "userId")
@Builder
@Table(name = "sys_user")
@Accessors(chain = true)
public class User {

    /**
     * 主键
     */
    private Integer id;
    /**
     * 用户编号
     */
    private String userId;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 用户备注
     */
    private String userRemarks;

    /**
     * 获取用户
     *
     * @param userId    用户编号
     * @param userName  用户名称
     * @param encryptPassword   用户密码
     * @param remark    用户备注
     * @return User     用户信息
     */
    public static User getUser(String userId, String userName, String encryptPassword, String remark) {
        return User.builder().userId(userId).userName(userName).password(encryptPassword).userRemarks(remark).build();
    }
}
