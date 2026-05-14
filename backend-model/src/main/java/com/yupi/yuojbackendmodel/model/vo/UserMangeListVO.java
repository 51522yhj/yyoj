package com.yupi.yuojbackendmodel.model.vo;

import lombok.Data;

import java.util.List;

/**
 * @Description: +total
 * @Author: Yhj
 * @Date: 2025/2/4 16:47
 */
@Data
public class UserMangeListVO {
private Long total;
private List<UserListVO> userListVOList;
}
