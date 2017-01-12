package com.gavin.interval;

/**
 * User: Gavin
 * E-mail: GavinChangCN@163.com
 * Desc:
 * Date: 2017-01-12
 * Time: 10:53
 */
public class LeftOpenRightMaxInterval extends AbstractInterval {
    protected static final String TAG = "LeftCloseRightMaxInterval";

    public LeftOpenRightMaxInterval(Double leftDigital) {
        super(IntervalEnum.getByCodde(IntervalEnum.LEFT_OPEN_RIGHT_MAX.getCode()), leftDigital, Double.MAX_VALUE);
    }

    @Override
    public boolean isInThisInterval(Double number) {
        return number > this.getLeftDigital();
    }

    @Override
    public boolean isInThisInterval(AbstractInterval interval) {
        return null != interval.getLeftDigital()
                && interval.getLeftDigital() > this.getLeftDigital();
    }

    @Override
    public boolean isCrossAndThisInterval(AbstractInterval interval) {
        return interval.isInThisInterval(this) || (null == interval.getRightDigital()
                || interval.getRightDigital() > this.getLeftDigital());
    }
}
