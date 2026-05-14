package com.yupi.yuojbackendmodel.model.dto.questionsubmit;

import lombok.Data;

/**
 * @Description: 请求该月的刷题数
 * @Author: Yhj
 * @Date: 2025/2/8 0:40
 */
@Data
public class DateRequest {
    private String year;
    private String month;

}
