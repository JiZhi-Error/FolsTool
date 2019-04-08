package top.fols.box.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import top.fols.box.time.XTimeTool;
public final class XCycleAverageSpeed {
	///***********
	private volatile long cyclespeednow ;//周期时间内的速率
	private Object lock = new Object();
	public XCycleAverageSpeed() {
		this.cyclespeednow = 0;
	}
	public void addForFreeLong(long freeLength) {
		synchronized (lock) {
			cyclespeednow += freeLength;
			isEnd = false;
			
			long newTime = XTimeTool.currentTimeMillis();
			if (newTime - averageSpeedBackNextUpdateTime >= getCycleAverageSpeedUpdateCycle) {
				averageSpeedList.fill(cyclespeednow);
				averageSpeedUpdateTimeList.fill(newTime);
				averageSpeedBackNextUpdateTime = newTime;
				cyclespeednow = 0;
			}
		}
	}
	public long getCycleAverageSpeedUpdateCycle() {
		return getCycleAverageSpeedUpdateCycle;
	}
	public void setCycleAverageSpeedUpdateCycle(long cycle) {
		this.getCycleAverageSpeedUpdateCycle = cycle;
	}
	private long getCycleAverageSpeedUpdateCycle;
	private double lastCycleAverageSpeed = 0;
	private long lastGetCycleAverageSpeedTime = 0;
	private XFixelArrayFill.longsFill averageSpeedList = new XFixelArrayFill.longsFill(6);
	private XFixelArrayFill.longsFill averageSpeedUpdateTimeList = new XFixelArrayFill.longsFill(6);
	private volatile long averageSpeedBackNextUpdateTime;
	private volatile boolean isEnd = false;

	public double getCycleAverageSpeed() {
		if (isEnd) {
			return 0D;
		} else {
			long newTime = XTimeTool.currentTimeMillis();
			//因为是周期速度 频繁的重新计算速度也无意义
			if (newTime - lastGetCycleAverageSpeedTime >= getCycleAverageSpeedUpdateCycle) {
				BigDecimal m = BigDecimal.ZERO;

				//3.1s以内的数据 / 除以3
				long timeRange = newTime - (getCycleAverageSpeedUpdateCycle * 3);
				int forLength = 0;
				for (int i = 0;i < averageSpeedUpdateTimeList.length();i++) {
					Long lastUpdate = averageSpeedUpdateTimeList.get(i);
					if (lastUpdate != null && lastUpdate.longValue() > timeRange) {
						m = m.add(new BigDecimal((double)averageSpeedList.get(i)));
						++forLength;
					}
				}
				//System.out.println(averageSpeedList);
				//System.out.println(averageSpeedUpdateTimeList);
				if (forLength == 0)
					return 0;
				m = m.divide(new BigDecimal(forLength), 12, RoundingMode.CEILING);

				lastGetCycleAverageSpeedTime = newTime;
				isEnd = true;
				return lastCycleAverageSpeed = m.doubleValue();
			} else {
				return lastCycleAverageSpeed;
			}
		}
	}
}

