package com.gavin.interval;

/**
 * User: Gavin
 * E-mail: GavinChangCN@163.com
 * Desc:
 * Date: 2017-01-12
 * Time: 10:55
 */
public enum IntervalEnum {
    /**
     * 区间类型：
     * 1代表左闭，右边为无穷大的区间；
     * 2代表右闭，左边为无穷小的区间；
     * 3代表左开，右边为无穷大的区间；
     * 4代表右开，左边为无穷小的区间；
     * 5代表左闭右闭区间；
     * 6代表左闭右开区间；
     * 7代表左开右闭区间；
     * 8代表左开右开区间。
     */
    LEFT_CLOSE_RIGHT_MAX(1, "leftCloseRightMax", "左闭，右边为无穷大 区间"),
    LEFT_MIN_RIGHT_CLOSE(2, "leftMinRightClose", "右闭，左边为无穷小 区间"),
    LEFT_OPEN_RIGHT_MAX(3, "leftOpenRightMax", "左开，右边为无穷大 区间"),
    LEFT_MIN_RIGHT_OPEN(4, "leftMinRightOpen", "右开，左边为无穷小 区间"),
    LEFT_CLOSE_RIGHT_CLOSE(5, "leftCloseRightClose", "左闭右闭 区间"),
    LEFT_CLOSE_RIGHT_OPEN(6, "leftCloseRightOpen", "左闭右开 区间"),
    LEFT_OPEN_RIGHT_CLOSE(7, "leftOpenRightClose", "左开右闭 区间"),
    LEFT_OPEN_RIGHT_OPEN(8, "leftOpenRightOpen", "左开右开 区间");
    /**
     * 枚举值 --
     */
    private final Integer code;

    /**
     * 枚举描述 --英文名称
     */
    private final String alias;

    /**
     * 枚举信息 --中文名称
     */
    private final String name;

    IntervalEnum(Integer code, String alias, String name) {
        this.code = code;
        this.alias = alias;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getAlias() {
        return alias;
    }

    public String getName() {
        return name;
    }

    /**
     * 通过枚举<code>code</code>获得枚举
     *
     * @param code
     * @return IntervalEnum
     */
    public static IntervalEnum getByCodde(Integer code) {
        for (IntervalEnum cacheCode : values()) {
            if (code.equals(cacheCode.getCode())) {
                return cacheCode;
            }
        }
        return null;
    }
}
