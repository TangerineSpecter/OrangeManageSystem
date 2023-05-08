package com.tangerinespecter.oms.system.domain.pojo;

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

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Memos implements Serializable {

        private Long id;

        private String content;
    }
}
