package com.tangerinespecter.oms.system.domain.enums;

import cn.hutool.core.util.NumberUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.function.Function;

/**
 * 期货手续费枚举
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("all")
public enum FuturesChargesEnum {
    /**
     * 白银
     */
    AG_CHARGES("ag", "白银", p -> NumberUtil.mul(p, 0.0003, 15)),
    /**
     * 黄金
     */
    AU_CHARGES("au", "黄金", p -> new BigDecimal(60)),
    /**
     * 镍
     */
    NI_CHARGES("ni", "镍", p -> new BigDecimal(36)),
    /**
     * 锡
     */
    SN_CHARGES("sn", "锡", p -> new BigDecimal(18)),
    /**
     * 锌
     */
    ZN_CHARGES("zn", "锌", p -> new BigDecimal(18)),
    /**
     * 大豆1
     */
    A_CHARGES("a", "大豆1", p -> new BigDecimal(18)),
    /**
     * 大豆2
     */
    B_CHARGES("b", "大豆2", p -> new BigDecimal(18)),
    /**
     * 玉米
     */
    C_CHARGES("c", "玉米", p -> BigDecimal.valueOf(7.2)),
    /**
     * 玉米淀粉
     */
    CS_CHARGES("cs", "玉米淀粉", p -> new BigDecimal(9)),
    /**
     * 聚乙烯
     */
    L_CHARGES("l", "聚乙烯", p -> new BigDecimal(12)),
    /**
     * 豆粕
     */
    M_CHARGES("m", "豆粕", p -> new BigDecimal(9)),
    /**
     * 棕榈油
     */
    P_CHARGES("p", "棕榈油", p -> new BigDecimal(15)),
    /**
     * 聚氯乙烯
     */
    V_CHARGES("v", "PVC", p -> new BigDecimal(12)),
    /**
     * 豆油
     */
    Y_CHARGES("y", "豆油", p -> new BigDecimal(15)),
    /**
     * 乙二醇
     */
    EG_CHARGES("eg", "乙二醇", p -> new BigDecimal(24)),
    /**
     * 粳米
     */
    RR_CHARGES("rr", "粳米", p -> new BigDecimal(24)),
    /**
     * 苯乙烯
     */
    EB_CHARGES("eb", "苯乙烯", p -> new BigDecimal(36)),
    /**
     * 苹果
     */
    AP_CHARGES("ap", "苹果", p -> new BigDecimal(30)),
    /**
     * 棉花
     */
    CF_CHARGES("cf", "棉花", p -> BigDecimal.valueOf(25.8)),
    /**
     * 棉纱
     */
    CY_CHARGES("cy", "棉纱", p -> new BigDecimal(24)),
    /**
     * 玻璃
     */
    FG_CHARGES("fg", "玻璃", p -> new BigDecimal(18)),
    /**
     * 粳稻
     */
    JR_CHARGES("jr", "粳稻", p -> new BigDecimal(18)),
    /**
     * 晚粳稻
     */
    LR_CHARGES("lr", "晚粳稻", p -> new BigDecimal(18)),
    /**
     * 甲醇
     */
    MA_CHARGES("ma", "甲醇", p -> new BigDecimal(12)),
    /**
     * 菜油
     */
    OI_CHARGES("oi", "菜油", p -> new BigDecimal(12)),
    /**
     * 普麦
     */
    PM_CHARGES("pm", "普麦", p -> new BigDecimal(30)),
    /**
     * 早籼稻
     */
    RI_CHARGES("ri", "早籼稻", p -> new BigDecimal(15)),
    /**
     * 菜粕
     */
    RM_CHARGES("rm", "菜粕", p -> new BigDecimal(9)),
    /**
     * 菜籽
     */
    RS_CHARGES("rs", "菜籽", p -> new BigDecimal(12)),
    /**
     * 硅铁
     */
    SF_CHARGES("sf", "硅铁", p -> new BigDecimal(18)),
    /**
     * 锰硅
     */
    SM_CHARGES("sm", "锰硅", p -> new BigDecimal(18)),
    /**
     * 白糖
     */
    SR_CHARGES("SR", "白糖", p -> new BigDecimal(18)),
    /**
     * PTA
     */
    TA_CHARGES("ta", "PTA", p -> new BigDecimal(18)),
    /**
     * 强麦
     */
    WH_CHARGES("wh", "强麦", p -> new BigDecimal(15)),
    /**
     * 动力煤
     */
    ZC_CHARGES("zc", "动力煤", p -> new BigDecimal(24)),
    /**
     * 红枣
     */
    CJ_CHARGES("cj", "红枣", p -> new BigDecimal(30)),
    /**
     * 尿素
     */
    UR_CHARGES("ur", "尿素", p -> new BigDecimal(30)),
    /**
     * 纯碱
     */
    SA_CHARGES("sa", "纯碱", p -> new BigDecimal(21)),
    /**
     * 原油
     */
    SC_CHARGES("sc", "原油", p -> new BigDecimal(120)),
    /**
     * 20号胶
     */
    NR_CHARGES("nr", "20号胶", p -> new BigDecimal(60)),
    /**
     * 10年国债
     */
    T_CHARGES("t", "10年国债", p -> new BigDecimal(18)),
    /**
     * 5年国债
     */
    TF_CHARGES("tf", "5年国债", p -> new BigDecimal(18)),
    /**
     * 2年国债
     */
    TS_CHARGES("ts", "2年国债", p -> new BigDecimal(18)),
    /**
     * 中证500
     */
    IC_CHARGES("ic", "中证500", p -> NumberUtil.mul(p, 0.000138, 200)),
    /**
     * 沪深300
     */
    IF_CHARGES("if", "沪深300", p -> NumberUtil.mul(p, 0.000138, 300)),
    /**
     * 上证50
     */
    IH_CHARGES("ih", "上证50", p -> NumberUtil.mul(p, 0.000138, 300)),
    /**
     * 沥青
     */
    BU_CHARGES("bu", "沥青", p -> NumberUtil.mul(p, 0.0006, 10)),
    /**
     * 聚丙烯PP
     */
    PP_CHARGES("pp", "PP", p -> NumberUtil.mul(p, 0.00036, 5)),
    /**
     * 燃油
     */
    FU_CHARGES("fu", "燃油", p -> NumberUtil.mul(p, 0.0003, 10)),
    /**
     * 热卷
     */
    HC_CHARGES("hc", "热卷", p -> NumberUtil.mul(p, 0.0006, 10)),
    /**
     * 不锈钢
     */
    SS_CHARGES("ss", "不锈钢", p -> NumberUtil.mul(p, 0.0006, 5)),
    /**
     * 铅
     */
    PB_CHARGES("pb", "铅", p -> NumberUtil.mul(p, 0.00024, 5)),
    /**
     * 线材
     */
    WR_CHARGES("wr", "线材", p -> NumberUtil.mul(p, 0.00024, 10)),
    /**
     * 螺纹钢
     */
    RB_CHARGES("rb", "螺纹钢", p -> NumberUtil.mul(p, 0.0006, 10)),
    /**
     * 橡胶
     */
    RU_CHARGES("ru", "橡胶", p -> NumberUtil.mul(p, 0.00027, 10)),
    /**
     * 铜
     */
    CU_CHARGES("cu", "铜", p -> NumberUtil.mul(p, 0.0003, 5)),
    /**
     * 铁矿
     */
    I_CHARGES("i", "铁矿", p -> NumberUtil.mul(p, 0.0006, 100)),
    /**
     * 焦炭
     */
    J_CHARGES("j", "焦炭", p -> NumberUtil.mul(p, 0.00036, 100)),
    /**
     * 鸡蛋
     */
    JD_CHARGES("jd", "铜", p -> NumberUtil.mul(p, 0.0009, 10)),
    /**
     * 焦煤
     */
    JM_CHARGES("jm", "焦煤", p -> NumberUtil.mul(p, 0.00036, 60)),
    /**
     * 金属铝
     */
    AL_CHARGES("al", "铝", p -> new BigDecimal(18));

    /**
     * 期货代码
     */
    private String code;
    /**
     * 期货描述
     */
    private String desc;

    /**
     * 根据保证金获取每手手续费
     */
    private Function<Integer, BigDecimal> charges;

    /**
     * 获取手续费
     *
     * @param earnestMoneyPer 每手保证金
     * @return
     */
    public BigDecimal getCharges(Integer earnestMoneyPer) {
        return charges.apply(earnestMoneyPer);
    }

    public static void main(String[] args) {
        BigDecimal charges = FuturesChargesEnum.CU_CHARGES.getCharges(32610);
        System.out.println(charges);
    }
}
