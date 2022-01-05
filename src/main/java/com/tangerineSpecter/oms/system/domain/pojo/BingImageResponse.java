package com.tangerinespecter.oms.system.domain.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 必应壁纸响应结果
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BingImageResponse implements Serializable {

    /**
     * 图片信息
     */
    private List<ImageInfo> images;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ImageInfo {
        /**
         * 开始时间
         */
        private Long startdate;
        /**
         * 结束时间
         */
        private Long enddate;
        /**
         * 壁纸url
         */
        private String url;
        /**
         * 完整开始时间
         */
        private Long fullstartdate;
        /**
         * 跳转搜索链接
         */
        private String copyrightlink;
        /**
         * 版权信息
         */
        private String copyright;
        /**
         * 标题
         */
        private String title;
        /**
         * hash值
         */
        private String hsh;

        public String getUrl() {
            return "https://cn.bing.com" + url;
        }
    }
}
