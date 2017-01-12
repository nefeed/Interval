package com.gavin.interval;

/**
 * User: Gavin
 * E-mail: GavinChangCN@163.com
 * Desc:
 * Date: 2017-01-12
 * Time: 10:53
 */
public class LeftOpenRightOpenInterval extends AbstractInterval {
    protected static final String TAG = "LeftCloseRightMaxInterval";

    public LeftOpenRightOpenInterval(Double leftDigital, Double rightDigital) {
        super(IntervalEnum.getByCodde(IntervalEnum.LEFT_OPEN_RIGHT_OPEN.getCode()),
                leftDigital, rightDigital);
    }

    @Override
    public boolean isInThisInterval(Double number) {
        return number > this.getLeftDigital() && number < this.getRightDigital();
    }

    @Override
    public boolean isInThisInterval(AbstractInterval interval) {
        return (null != interval.getLeftDigital()
                && interval.getLeftDigital() > this.getLeftDigital())
                && (null != interval.getRightDigital()
                && interval.getRightDigital() < this.getRightDigital());
    }

    @Override
    public boolean isCrossAndThisInterval(AbstractInterval interval) {
        return interval.isInThisInterval(this) || (interval.getLeftDigital() > this.getLeftDigital()
                && interval.getLeftDigital() < this.getRightDigital())
                || (interval.getRightDigital() > this.getLeftDigital()
                && interval.getRightDigital() < this.getRightDigital());
    }
}
