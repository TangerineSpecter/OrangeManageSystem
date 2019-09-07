package com.tangerinespecter.oms.system.domain.vo.tools;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

/**
 * 二维码信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QrCodeInfoVo {

    /**
     * 二维码内容
     */
    @NotBlank(message = "内容不能为空")
    private String content;
    /**
     * 高度
     */
    @NotNull(message = "高度不能为空")
    @Min(value = 100, message = "高度最小100")
    @Max(value = 500, message = "高度最大500")
    private Integer height;
    /**
     * 宽度
     */
    @NotNull(message = "宽度不能为空")
    @Max(value = 500, message = "宽度最大500")
    @Min(value = 100, message = "宽度最小100")
    private Integer width;
}
