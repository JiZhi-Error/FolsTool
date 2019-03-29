package top.fols.box.util;
import top.fols.box.annotation.XAnnotations;
import top.fols.box.time.XTimeTool;
public class XCycleSpeedLimiter {
	protected static long currentTimeMillis() {
		return XTimeTool.currentTimeMillis();
	}
	private volatile long cycle;
	private volatile long cycleUpdateTime;
	private volatile long cyclemaxspeed;
	private volatile long cyclespeednow ;//周期时间内的速率
	private volatile boolean isLimit;
	private final Object lock = new Object();
	public XCycleSpeedLimiter() {
		this.cycle = XTimeTool.time_1s;
		this.cyclespeednow = 0;
		this.cyclemaxspeed = 8192;
		this.cycleUpdateTime = currentTimeMillis();
		this.isLimit = false;
	}
	public int waitForFreeInt(@XAnnotations("max length is preferably waitfor free length multiples")int waiforFreeLength) {
		return (int)waitForFreeLong(waiforFreeLength);
	}
	public long waitForFreeLong(@XAnnotations("max length is preferably waitfor free length multiples")long waiforFreeLength) {
		if (waiforFreeLength > cyclemaxspeed)
			throw new NumberFormatException("pieceLength can't > cyclemaxspeed");
		synchronized (lock) {
			while (true) {
				if (!isLimit || cyclespeednow + waiforFreeLength <= cyclemaxspeed)
					break;
				else
					while (true) {
						if (!isLimit)
							break;
						long newTime = currentTimeMillis();
						if (newTime - cycleUpdateTime >= cycle) {
							cycleUpdateTime = newTime;
							cyclespeednow = 0;
							break;
						}
					}
			}
			if (!isLimit) {
				long newTime = currentTimeMillis();
				if (newTime - cycleUpdateTime >= cycle) {
					cycleUpdateTime = newTime;
					cyclespeednow = 0;
				}
			}
			everySecondAverageSpeed.addForFreeLong(waiforFreeLength);
			cyclespeednow += waiforFreeLength;
			return waiforFreeLength;
		}
	}
	public long getCycleUseSpeed() {
		if (isCycleProcessEnd())
			return 0;
		return this.cyclespeednow;
	}
	public long getCycleMaxSpeed() {
		return this.cyclemaxspeed;
	}
	public void setCycleMaxSpeed(long cycleMaxSpeed)throws NumberFormatException {
		if (cycleMaxSpeed < 1)
			throw new NumberFormatException("size error cycleMaxSpeed " + cycleMaxSpeed);
		this.cyclemaxspeed = cycleMaxSpeed;
		//this.cyclespeednow = 0;
		//this.backTime = currentTimeMillis();
	}	
	public void setCycle(long cycle) {
		if (cycle < 1)
			throw new NumberFormatException("size error cycle " + cycle);
		this.cycle = cycle;
		//this.cyclespeednow = 0;
		//this.backTime = currentTimeMillis();
	}
	public long getCycle() {
		return this.cycle;
	}
	public long getCycleFreeSpeed() {
		long length;
		return (length = getCycleMaxSpeed() - getCycleUseSpeed()) < 0 ?0: length;
	}
	public boolean setLimit(boolean b) {
		return this.isLimit = b;
	}
	public boolean isLimit() {
		return this.isLimit;
	}
	public String toString() {
		return String.format("[cycle=%s, cyclemaxspeed=%s, cyclespeednow=%s, islimit=%s]", getCycle(), getCycleMaxSpeed(), getCycleUseSpeed(), isLimit);
	}




	public boolean isCycleProcessEnd() {
		long newTime = currentTimeMillis();
		if (newTime - cycleUpdateTime >= cycle)
			return true;
		return false;
	}
	private XEverySecondAverageSpeed everySecondAverageSpeed = new XEverySecondAverageSpeed();
	public double getEverySecondAverageSpeed() {return everySecondAverageSpeed.getEverySecondAverageSpeed();}
	public long getEverySecondAverageSpeedUpdateCycle() {return everySecondAverageSpeed.getEverySecondAverageSpeedUpdateCycle();}
	public void setGetEverySecondAverageSpeedUpdateCycle(long cycle) {everySecondAverageSpeed.setGetEverySecondAverageSpeedUpdateCycle(cycle);}
}
