package top.fols.box.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import top.fols.box.time.XTimeTool;
public class XEverySecondAverageSpeed {
	///***********
	private volatile long cyclespeednow ;//周期时间内的速率
	private final Object lock = new Object();
	public XEverySecondAverageSpeed() {
		this.cyclespeednow = 0;
	}
	public long addForFreeLong(long freeLength) {
		synchronized (lock) {
			long newTime = XTimeTool.currentTimeMillis();
			if (newTime - averageSpeedBackNextUpdateTime >= XTimeTool.time_1s) {
				averageSpeedList.fill(cyclespeednow);
				averageSpeedUpdateTimeList.fill(newTime);
				averageSpeedBackNextUpdateTime = newTime;
				cyclespeednow = 0;
			}
			cyclespeednow += freeLength;
			return freeLength;
		}
	}


	public long getEverySecondAverageSpeedUpdateCycle() {return getEverySecondAverageSpeedUpdateCycle;}
	public void setGetEverySecondAverageSpeedUpdateCycle(long cycle) {this.getEverySecondAverageSpeedUpdateCycle = cycle;}
	private long getEverySecondAverageSpeedUpdateCycle = 499;
	private double lastEverySecondAverageSpeed = 0;
	private long lastGetEverySecondAverageSpeedTime = XTimeTool.currentTimeMillis();


	private XFixelArrayFill.longsFill averageSpeedList = new XFixelArrayFill.longsFill(6);
	private XFixelArrayFill.longsFill averageSpeedUpdateTimeList = new XFixelArrayFill.longsFill(6);
	private volatile long averageSpeedBackNextUpdateTime = XTimeTool.currentTimeMillis();
	public double getEverySecondAverageSpeed() {
		long newTime = XTimeTool.currentTimeMillis();

		//因为是每秒速度 频繁的重新计算速度也无意义
		if (newTime - lastGetEverySecondAverageSpeedTime >= getEverySecondAverageSpeedUpdateCycle) {
			BigDecimal m = BigDecimal.ZERO;

			//3.1秒以内的数据
			long timeRange = XTimeTool.currentTimeMillis() - 3100;
			int forLength = 0;
			for (int i = 0;i < averageSpeedUpdateTimeList.length();i++) {
				Long lastUpdate = averageSpeedUpdateTimeList.get(i);
				if (lastUpdate != null && lastUpdate > timeRange) {
					m = m.add(new BigDecimal((double)averageSpeedList.get(i)));
					++forLength;
				}
			}
			if (forLength == 0)
				return 0;
			m = m.divide(new BigDecimal(forLength), 12, RoundingMode.CEILING);

			lastGetEverySecondAverageSpeedTime = newTime;
			return lastEverySecondAverageSpeed = m.doubleValue();
		} else {
			return lastEverySecondAverageSpeed;
		}
	}
}

