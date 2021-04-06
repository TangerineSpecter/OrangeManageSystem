package com.tangerinespecter.oms;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.RandomUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Scanner;

public class DrawDemoTest {

    private static List<Reward> rewards = CollUtil.newArrayList();

    private static List<Reward> rewardList = CollUtil.newArrayList();

    private static Integer totalAmount = 1000;

    private static Reward firstPrize = new Reward("五星", 6, 5);

    private static Reward secondPrize = new Reward("四星", 51, 4);

    private static Reward thirdPrize = new Reward("三星", 943, 3);

    private static Integer totalMoney = 0;
    /**
     * 十连次数（10发保底）
     */
    private static Integer tenCount = 0;
    /**
     * 总次数（90发保底）
     */
    private static Integer totalCount = 0;

    private static void initReward() {
        System.out.println("初始化奖池ing....");
        rewardList.add(firstPrize);
        rewardList.add(secondPrize);
        rewardList.add(thirdPrize);
        for (int index = 0; index < totalAmount; index++) {
            int i = RandomUtil.randomInt(rewardList.size());
            Reward reward = rewardList.get(i);
            if (reward.getAmount() > 0) {
                reward.setAmount(reward.getAmount() - 1);
                rewards.add(reward);
            } else {
                index--;
            }
        }
    }

    private static void start() {
        initReward();
        System.out.println("开始抽奖...");
        while (true) {
            System.out.println("操作选择？1:单抽；2：十连抽；3：抽到5星停止；4：重置；5：停止");
            Scanner input = new Scanner(System.in);
            int in = input.nextInt();
            if (in == 4) {
                totalCount = 0;
                totalMoney = 0;
                tenCount = 0;
                continue;
            }
            if (in == 5) {
                break;
            }
            action(in);
            System.out.println("当前消费原石：" + totalMoney);
        }
        System.out.println("抽奖结束...");
    }

    private static void action(Integer action) {
        int count = 0;
        while (true) {
            int randomInt = RandomUtil.randomInt(0, totalAmount);
            Reward result = rewards.get(randomInt);
            if (result.getLevel() != 3) {
                tenCount = 0;
            }
            totalMoney += 160;
            System.out.println("抽奖结果：" + result.getName());
            count++;
            tenCount++;
            totalCount++;
            if (action == 1) {
                return;
            }
            if (action == 2 && count == 10) {
                return;
            }
            if (action == 3 && result.getLevel() == 5) {
                System.out.println("恭喜抽中五星，总共消费原石：" + totalMoney + "，抽奖次数：" + totalCount);
                return;
            }
        }
    }


    public static void main(String[] args) {
        start();
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class Reward {

        private String name;

        private Integer amount;

        private Integer level;
    }
}
