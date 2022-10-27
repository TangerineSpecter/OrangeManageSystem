package com.tangerinespecter.oms.common.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 问题管理高级查询
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionQueryObject extends BaseQueryObject {

    private String question;
}
