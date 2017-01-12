package com.gavin.interval;

/**
 * User: Gavin
 * E-mail: GavinChangCN@163.com
 * Desc:
 * Date: 2017-01-12
 * Time: 10:53
 */
public class LeftMinRightOpenInterval extends AbstractInterval {
    protected static final String TAG = "LeftCloseRightMaxInterval";

    public LeftMinRightOpenInterval(Double rightDigital) {
        super(IntervalEnum.getByCodde(IntervalEnum.LEFT_MIN_RIGHT_OPEN.getCode()),
                Double.MIN_VALUE, rightDigital);
    }

    @Override
    public boolean isInThisInterval(Double number) {
        return number < this.getRightDigital();
    }

    @Override
    public boolean isInThisInterval(AbstractInterval interval) {
        return null != interval.getRightDigital()
                && interval.getRightDigital() < this.getRightDigital();
    }

    @Override
    public boolean isCrossAndThisInterval(AbstractInterval interval) {
        return interval.isInThisInterval(this) || (null == interval.getLeftDigital()
                || interval.getLeftDigital() < this.getRightDigital());
    }
}
