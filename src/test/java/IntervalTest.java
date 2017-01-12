import com.alibaba.fastjson.JSON;
import com.gavin.interval.AbstractInterval;
import org.junit.Test;

/**
 * User: Gavin
 * E-mail: GavinChangCN@163.com
 * Desc:
 * Date: 2017-01-11
 * Time: 21:12
 */
public class IntervalTest {
    protected static final String TAG = "GavinTest";

    @Test
    public void interval() {
        String intervalStr = "(100, 200)";
        AbstractInterval interval1 = AbstractInterval.checkIntevalStr(intervalStr);
        System.out.println(interval1.getType());
        System.out.println(JSON.toJSONString(interval1));

        System.out.println("2在区间(100, 200)内吗：" + interval1.isInThisInterval(2d));
        System.out.println("152在区间(100, 200)内吗：" + interval1.isInThisInterval(152d));

        intervalStr = "[100,Max)";
        AbstractInterval interval2 = AbstractInterval.checkIntevalStr(intervalStr);
        System.out.println(interval2.getType());
        System.out.println(JSON.toJSONString(interval2));
        System.out.println("区间(100, 200)在 区间 [100,Max)内吗：" + interval2.isInThisInterval(interval1));

        intervalStr = "(mIn, 500)";
        AbstractInterval interval3 = AbstractInterval.checkIntevalStr(intervalStr);
        System.out.println(interval3.getType());
        System.out.println(JSON.toJSONString(interval3));

        System.out.println("区间(min, 500)与区间（100，200）有交集吗：" + interval1.isCrossAndThisInterval(interval3));
    }
}
