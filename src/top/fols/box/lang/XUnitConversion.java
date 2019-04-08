package top.fols.box.lang;

import java.math.BigDecimal;
import java.math.RoundingMode;
public class XUnitConversion {
	public static String unitCalc(String basel, String[] unitChars, int offUnit,BigDecimal[] perUnitSize, boolean round, int scale) {
		if (unitChars == null || unitChars.length == 0)
			throw new RuntimeException("unitChars for null");
		if (unitChars.length != perUnitSize.length)
			throw new RuntimeException("unitChars and perUnitSize length not equals");
		int i = offUnit;
		if (basel == null)
			return 0 + unitChars[i];
		if (new BigDecimal(basel).compareTo(perUnitSize[i]) == -1)
			return basel + unitChars[i];
		BigDecimal kiloByte = new BigDecimal(basel).divide(perUnitSize[i], 4, RoundingMode.DOWN);
		BigDecimal tmp;
		while (kiloByte.compareTo(BigDecimal.ZERO) == 1) {
			if (i + 1 > unitChars.length - 1)
				break;
			i++;
			tmp = kiloByte.divide(perUnitSize[i], 4, RoundingMode.DOWN);
			if (tmp.compareTo(BigDecimal.ONE) == -1)
				break;//<1
			kiloByte = tmp;
		}
		if (round) 
			return kiloByte.setScale(scale, BigDecimal.ROUND_HALF_UP).toString() + unitChars[i];
		return kiloByte.toString() + unitChars[i];
	}
	public static String unitCalc(String basel, String[] unitChars,int offUnit, BigDecimal perUnitSize, boolean round, int scale) {
		if (unitChars == null || unitChars.length == 0)
			throw new RuntimeException("unitChars for null");
		int i = offUnit;
		if (basel == null)
			return 0 + unitChars[i];
		if (new BigDecimal(basel).compareTo(perUnitSize) == -1)
			return basel + unitChars[i];
		BigDecimal kiloByte = new BigDecimal(basel).divide(perUnitSize, 4, RoundingMode.DOWN);
		BigDecimal tmp;
		while (kiloByte.compareTo(BigDecimal.ZERO) == 1) {
			if (i + 1 > unitChars.length - 1)
				break;
			i++;
			tmp = kiloByte.divide(perUnitSize, 4, RoundingMode.DOWN);
			if (tmp.compareTo(BigDecimal.ONE) == -1)
				break;//<1
			kiloByte = tmp;
		}
		if (round) 
			return kiloByte.setScale(scale, BigDecimal.ROUND_HALF_UP).toString() + unitChars[i];
		return kiloByte.toString() + unitChars[i];
	}
	
	
	
	
	
	
	public static String unitCalc(double basel, String[] unitChars,int offUnit, double[] perUnitSize, boolean round, int scale) {
		if (unitChars == null || unitChars.length == 0)
			throw new RuntimeException("unitChars for null");
		if (unitChars.length != perUnitSize.length)
			throw new RuntimeException("unitChars and perUnitSize length not equals");
		int i = offUnit;
		if (basel < perUnitSize[i])
			return Double.toString(basel) + unitChars[i];
		double kiloByte = basel / perUnitSize[i];
		double tmp;
		while (kiloByte > 0) {
			if (i + 1 > unitChars.length - 1)
				break;
			i++;
			tmp = kiloByte / perUnitSize[i];
			if (tmp < 1)
				break;//<1
			kiloByte = tmp;
		}
		if (round)
			return new BigDecimal(kiloByte).setScale(scale, BigDecimal.ROUND_HALF_UP).toString() + unitChars[i];
		return Double.toString(kiloByte) + unitChars[i];
	}
	public static String unitCalc(double basel, String[] unitChars, int offUnit,double perUnitSize, boolean round, int scale) {
		if (unitChars == null || unitChars.length == 0)
			throw new RuntimeException("unitChars for null");
		int i = 0;
		if (basel < perUnitSize)
			return Double.toString(basel) + unitChars[i];
		double kiloByte = basel / perUnitSize;
		double tmp;
		while (kiloByte > 0) {
			if (i + 1 > unitChars.length - 1)
				break;
			i++;
			tmp = kiloByte / perUnitSize;
			if (tmp < 1)
				break;//<1
			kiloByte = tmp;
		}
		if (round)
			return new BigDecimal(kiloByte).setScale(scale, BigDecimal.ROUND_HALF_UP).toString() + unitChars[i];
		return Double.toString(kiloByte) + unitChars[i];
	}
	
	
}
