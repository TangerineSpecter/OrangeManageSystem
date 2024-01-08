package com.tangerinespecter.oms.system.domain.pojo;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author 丢失的橘子
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("飞书Markdown基本卡片消息模板")
public class FsMarkdownCardMessage implements Serializable {

    @JSONField(name = "msg_type")
    private String msgType = "interactive";

    @JSONField(name = "card")
    private Card card = new Card();

    public FsMarkdownCardMessage(String title, String content) {
        this.setTitle(title);
        this.setContent(content);
    }

    private void setTitle(String title) {
        this.card.header.title.content = title;
    }

    private void setContent(String content) {
        this.card.elements.add(new Card.Element(content));
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Card implements Serializable {
        private Header header = new Header();

        private List<Element> elements = CollUtil.newArrayList();

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        private static class Header implements Serializable {

            private String template = "red";

            private Title title = new Title();

            @Data
            @AllArgsConstructor
            @NoArgsConstructor
            public static class Title implements Serializable {
                private String tag = "plain_text";

                private String content;
            }

        }

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        private static class Element implements Serializable {

            private String tag = "div";

            private Text text = new Text();

            public Element(String content) {
                this.text.content = content;
            }

            @Data
            @AllArgsConstructor
            @NoArgsConstructor
            public static class Text implements Serializable {
                private String tag = "lark_md";

                private String content;
            }
        }

    }

}
