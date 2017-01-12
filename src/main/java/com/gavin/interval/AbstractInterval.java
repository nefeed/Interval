package com.gavin.interval;

import com.alibaba.fastjson.JSON;
import com.gavin.util.StringUtil;

/**
 * User: Gavin
 * E-mail: GavinChangCN@163.com
 * Desc:
 * Date: 2017-01-12
 * Time: 10:28
 */
public abstract class AbstractInterval {

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
    private IntervalEnum type;
    private Double leftDigital;
    private Double rightDigital;

    AbstractInterval(IntervalEnum type, Double leftDigital, Double rightDigital) {
        this.type = type;
        this.leftDigital = leftDigital;
        this.rightDigital = rightDigital;
    }

    /**
     * 判断数值是否在区间内
     *
     * @param number
     * @return
     */
    public abstract boolean isInThisInterval(Double number);

    /**
     * 判断区间是否在区间内
     *
     * @param interval
     * @return
     */
    public abstract boolean isInThisInterval(AbstractInterval interval);

    /**
     * 判断区间是否和该区间有交集
     *
     * @param interval
     * @return
     */
    public abstract boolean isCrossAndThisInterval(AbstractInterval interval);

    public IntervalEnum getType() {
        return type;
    }

    public void setType(IntervalEnum type) {
        this.type = type;
    }

    public Double getLeftDigital() {
        return leftDigital;
    }

    public void setLeftDigital(Double leftDigital) {
        this.leftDigital = leftDigital;
    }

    public Double getRightDigital() {
        return rightDigital;
    }

    public void setRightDigital(Double rightDigital) {
        this.rightDigital = rightDigital;
    }

    /**
     * 校准 区间形式的Str 并输出区间
     *
     * @param str
     * @return
     */
    public static AbstractInterval checkIntevalStr(String str) {
        String leftUnit = String.valueOf(str.trim().charAt(0));
        String rightUnit = String.valueOf(str.charAt(str.length() - 1));
        String[] value = str.substring(1, str.length() - 1).split(",");
        if ("min".equals(value[0].toLowerCase())) {
            value[0] = null;
        }
        if ("max".equals(value[1].toLowerCase())) {
            value[1] = null;
        }
        return checkInteval(new Interval(leftUnit, rightUnit, value[0], value[1]));
    }


    /**
     * 校准 JSON格式的区间数据Str 并输出区间
     *
     * @param jsonStr
     * @return
     */
    public static AbstractInterval checkIntevalJson(String jsonStr) {
        return checkInteval(JSON.parseObject(jsonStr, Interval.class));
    }

    private static AbstractInterval checkInteval(Interval inteval) {
        if ("[".equals(inteval.getLeftUnit())) {
            if ("]".equals(inteval.getRightUnit())) {
                // 左闭右闭
                if (StringUtil.isEmpty(inteval.getLeftValue())) {
                    // 左无穷
                    return new LeftMinRightCloseInterval(StringUtil.toDouble(inteval.getRightValue()));
                } else if (StringUtil.isEmpty(inteval.getRightValue())) {
                    // 右无穷
                    return new LeftCloseRightMaxInterval(StringUtil.toDouble(inteval.getLeftValue()));
                } else {
                    return new LeftCloseRightCloseInterval(StringUtil.toDouble(inteval.getLeftValue()), StringUtil.toDouble(inteval.getRightValue()));
                }
            } else {
                // 左闭右开
                if (StringUtil.isEmpty(inteval.getLeftValue())) {
                    // 左无穷
                    return new LeftMinRightOpenInterval(StringUtil.toDouble(inteval.getRightValue()));
                } else if (StringUtil.isEmpty(inteval.getRightValue())) {
                    // 右无穷
                    return new LeftCloseRightMaxInterval(StringUtil.toDouble(inteval.getLeftValue()));
                } else {
                    return new LeftCloseRightOpenInterval(StringUtil.toDouble(inteval.getLeftValue()), StringUtil.toDouble(inteval.getRightValue()));
                }
            }
        } else {
            if ("]".equals(inteval.getRightUnit())) {
                // 左开右闭
                if (StringUtil.isEmpty(inteval.getLeftValue())) {
                    // 左无穷
                    return new LeftMinRightCloseInterval(StringUtil.toDouble(inteval.getRightValue()));
                } else if (StringUtil.isEmpty(inteval.getRightValue())) {
                    // 右无穷
                    return new LeftCloseRightMaxInterval(StringUtil.toDouble(inteval.getLeftValue()));
                } else {
                    return new LeftOpenRightCloseInterval(StringUtil.toDouble(inteval.getLeftValue()), StringUtil.toDouble(inteval.getRightValue()));
                }
            } else {
                // 左开右开
                if (StringUtil.isEmpty(inteval.getLeftValue())) {
                    // 左无穷
                    return new LeftMinRightOpenInterval(StringUtil.toDouble(inteval.getRightValue()));
                } else if (StringUtil.isEmpty(inteval.getRightValue())) {
                    // 右无穷
                    return new LeftOpenRightMaxInterval(StringUtil.toDouble(inteval.getLeftValue()));
                } else {
                    return new LeftOpenRightOpenInterval(StringUtil.toDouble(inteval.getLeftValue()), StringUtil.toDouble(inteval.getRightValue()));
                }
            }
        }
    }

    private static class Interval {
        String leftUnit;
        String rightUnit;
        String leftValue;
        String rightValue;

        Interval(String leftUnit, String rightUnit, String leftValue, String rightValue) {
            this.leftUnit = leftUnit;
            this.rightUnit = rightUnit;
            this.leftValue = leftValue;
            this.rightValue = rightValue;
        }

        String getLeftUnit() {
            return leftUnit;
        }

        String getRightUnit() {
            return rightUnit;
        }

        String getLeftValue() {
            return leftValue;
        }

        String getRightValue() {
            return rightValue;
        }

    }
}
