package com.tangerinespecter.oms.system.domain.pojo;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.RandomUtil;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author 丢失的橘子
 * @link https://github.com/usememos/memos
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("memos数据格式")
public class MemosInfo implements Serializable {

    private List<Memos> data;

    /**
     * 获取随机内容
     *
     * @return memo内容
     */
    public CharSequence getRandomContent() {
        final int totalSize = CollUtil.size(this.data);
        return CollUtil.get(this.data, RandomUtil.randomInt(totalSize)).getContent();
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Memos implements Serializable {

        private Long id;

        private String content;
    }
}
