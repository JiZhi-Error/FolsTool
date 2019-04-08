package top.fols.box.io.os;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigDecimal;
import java.text.Collator;
import java.util.Collections;
import java.util.List;
import top.fols.box.annotation.XAnnotations;
import top.fols.box.io.base.ns.XNsCharArrayWriterUtils;
import top.fols.box.lang.XUnitConversion;
import top.fols.box.statics.XStaticFixedValue;
import top.fols.box.util.ArrayListUtils;
import top.fols.box.util.XObjects;
public class XFileTool {
	public static void newFile(File file) throws IOException {
		if (file == null)
			throw new NullPointerException("file for null");
		try {
			if (!file.exists())
				if (!file.createNewFile())
					throw new IOException("canot new file:" + file);
			if (!file.canRead())
				throw new IOException("canot read file:" + file);
			if (!file.canWrite())
				throw new IOException("canot write file:" + file);
		} catch (IOException e) {
			file = null;
			throw e;
		}
	}
	public static byte[] readFile(File file) {
		if (!file.exists())
			return XStaticFixedValue.nullbyteArray;
		RandomAccessFile randomFile = null;
		byte[] fileBytes;
		long length;
		try { 
			randomFile = new RandomAccessFile(file, "r");
			length = randomFile.length();
			if (length > Integer.MAX_VALUE)
				throw new OutOfMemoryError("overflow memory file length > " + Integer.MAX_VALUE);
			fileBytes = new byte[(int)length];
			randomFile.read(fileBytes);
			return fileBytes;
		} catch (Exception e) {
			return XStaticFixedValue.nullbyteArray;
		} finally {
			try {
				if (randomFile != null)
					randomFile.close();
			} catch (Exception e) {
				randomFile = null;
			}
		}
	}
	public static void clearFile(File file) throws IOException {
		if (!file.exists())
			return;
		file.delete();
		newFile(file);
	}
	public static boolean saveFile(File file, byte[] bytes) throws IOException {
		newFile(file);
		if (bytes == null || bytes.length == 0) {
			clearFile(file);
			return true;
		}
		RandomAccessFile randomFile = null;
		try { 
			randomFile = new RandomAccessFile(file, "rws");
			randomFile.write(bytes);
			randomFile.setLength(bytes.length);
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			try {
				if (randomFile != null)
					randomFile.close();
			} catch (Exception e) {
				randomFile = null;
			}
		}
	}




	@XAnnotations("1B = 8bit")
	public static final String[] fileUnit = new String[]{
		"B","KB","MB",
		"GB","TB","PB",
		"EB","ZB","YB",
		"BB","NB","DB",
		"CB"
	};
	public static final BigDecimal[] fileUnitSize = new BigDecimal[]{
		new BigDecimal(1024D),new BigDecimal(1024D),new BigDecimal(1024D),
		new BigDecimal(1024D),new BigDecimal(1024D),new BigDecimal(1024D),
		new BigDecimal(1024D),new BigDecimal(1024D),new BigDecimal(1024D),
		new BigDecimal(1024D),new BigDecimal(1024D),new BigDecimal(1024D),
		new BigDecimal(1024D)
	};
	public static String fileUnitFormat(String bytelength) {
		return fileUnitFormat(bytelength, true);
	}
	public static String fileUnitFormat(String bytelength, boolean round) {
		return XUnitConversion.unitCalc(bytelength, fileUnit, 0, fileUnitSize, round, 2);
	}
	public static String fileUnitFormat(double size) {
		return fileUnitFormat(size, true, 2);
	}
	public static String fileUnitFormat(double size, boolean round, int scale) {
		return XUnitConversion.unitCalc(size, fileUnit, 0, 1024, round, 2);
	}




	/*
	 文件获取绝对地址
	 getFormatPath("//XSt/*?:]/tt/////.//./././a/b/v//x//a/v**v//n///...//../../();").equals(new File("//XSt/*?:]/tt/////.//./././a/b/v//x//a/v**v//n///...//../../();").getCanonicalPath()); >> true
	 getFormatPath("//XSt/*?:]/tt/////.//./././a/b/v//x//a/v**v//n///...//../../();"); >> "/XSt/*?:]/tt/a/b/v/x/a/v**v/();"
	 */
	public static String getFormatPath(String path) {
		return getFormatPath(path, File.separatorChar);
	}
	public static String getFormatPath(String orginPath, char separatorChar) {
		if (orginPath.length() == 0)
			return Character.toString(separatorChar);
		XNsCharArrayWriterUtils buf  = new XNsCharArrayWriterUtils();
		
		StringBuilder path;
		path = new StringBuilder();
		path.append(separatorChar);
		int pathsize = orginPath.length();
		boolean lastSplitChar = true;
		int i = 0;
		if (orginPath.charAt(0) == separatorChar)
			i = 1;
		for (;i < pathsize;i++) {
			if (orginPath.charAt(i) == separatorChar) {
				if (!lastSplitChar)
					path.append(separatorChar);
				lastSplitChar = true;
				continue;
			} else {
				lastSplitChar = false;
				path.append(orginPath.charAt(i));
			}
		}
		int length = path.length();
		int absInd = 0;
		for (int ind = 0;ind < length;) {
			char c = path.charAt(ind);
			if (c == separatorChar) {
				if (ind + 2 < length && 
					path.charAt(ind + 1) == '.' &&
					path.charAt(ind + 2) == separatorChar) {
					//	/./
					ind += 2;
					continue;
				} else if (ind + 3 < length && 
						   path.charAt(ind + 1) == '.'  && 
						   path.charAt(ind + 2) == '.' && 
						   path.charAt(ind + 3) == separatorChar) {
					//	/../
					int last = buf.lastIndexOfBuff(separatorChar, absInd - 1, 0);
					//System.out.println(buf.toString() + "(" + absInd + ")" + "-" + "(" + last + ")");
					if (last >= 0) {
						buf.seekIndex(last);
						absInd = last;
					} else {
						buf.seekIndex(0);
						absInd = 0;
					}
					ind += 3;//
					continue;
				} 
			} 
			buf.write(c);
			ind += 1;
			absInd += 1;
		}
		int size = buf.size();
		if (size >= 3 &&
			buf.getBuff()[size - 3] == separatorChar  &&
			buf.getBuff()[size - 2] == '.' &&
			buf.getBuff()[size - 1] == '.') {
			int last = buf.lastIndexOfBuff(separatorChar, size - 3 - 1, 0);
			if (last > -1) {
				buf.setSize(last + 1);
			} else {
				buf.setSize(0);
				buf.write(separatorChar);
			}
		} else if (size >= 2 &&
				   buf.getBuff()[size - 2] == separatorChar  &&
				   buf.getBuff()[size - 1] == '.') {
			buf.setSize(buf.size() - 1);
		} 
		String result;
		if (buf.size() >= 1 && buf.getBuff()[0] != separatorChar) {
			result = new StringBuilder(separatorChar)
				.append(buf.getBuff(), 0, buf.size())
				.toString();
		} else {
			result = new String(buf.getBuff(), 0, buf.size());
		}
		buf.releaseCache();
		path = null;
		return result;
	}
	
	
	
	/*
	 get Extension Name
	 得到扩展名
	 */
	public static String getExtensionName(String fileCanonicalPath,char separatorChar) { 
		if ((fileCanonicalPath != null) && (fileCanonicalPath.length() > 0)) { 
			int dot = fileCanonicalPath.lastIndexOf('.'); 
            if ((dot > fileCanonicalPath.lastIndexOf(separatorChar)) && (dot > -1) && (dot < (fileCanonicalPath.length() - 1))) { 
                return fileCanonicalPath.substring(dot + 1, fileCanonicalPath.length()); 
            } 
        } 
        return null; 
    } 
	public static String getExtensionName(String fileCanonicalPath) { 
		return getExtensionName(fileCanonicalPath,File.separatorChar);
	}
	/*
	 get Name
	 获得 文件名 带后缀
	 */
	public static String getName(String fileCanonicalPath,char separatorChar) { 
        if ((fileCanonicalPath != null) && (fileCanonicalPath.length() > 0)) { 
            int dot = fileCanonicalPath.lastIndexOf(separatorChar);
			if (dot + 1 < fileCanonicalPath.length()) { 
				return fileCanonicalPath.substring(dot + 1, fileCanonicalPath.length()); 
            }
        } 
        return null; 
    } 
	public static String getName(String fileCanonicalPath) { 
		return getName(fileCanonicalPath,File.separatorChar);
	}
	/*
	 get Name No Ex
	 获得文件名不带扩展名 不带路径
	 */
	public static String getNameNoExtension(String fileCanonicalPath,char separatorChar) { 
        if ((fileCanonicalPath != null) && (fileCanonicalPath.length() > 0)) { 
            int dot = fileCanonicalPath.lastIndexOf(separatorChar);
			if (dot + 1 < (fileCanonicalPath.length())) { 
				fileCanonicalPath = fileCanonicalPath.substring(dot + 1, fileCanonicalPath.length()); 
				int dot2 = fileCanonicalPath.lastIndexOf('.');
				if (dot2 > -1) {
					return fileCanonicalPath.substring(0,dot2);
				}else{
					return fileCanonicalPath;
				}
            }
        } 
        return null; 
    } 
	public static String getNameNoExtension(String fileCanonicalPath){
		return getNameNoExtension(fileCanonicalPath,File.separatorChar);
	}






	/*
	 Get Dir File List
	 获取文件夹文件列表
	 Parameter:filePath 路径,recursion 递增搜索,adddir 列表是否添加文件夹
	 */
	public static List<String> getFileList(String filePath, boolean recursion, boolean adddir) throws IOException {
		List<String> List = new ArrayListUtils<>();
		return getFilesList(List, new File(filePath) , recursion, adddir, new StringBuilder());
    }
	private static List<String> getFilesList(List<String> list, File filePath, boolean recursion, boolean adddir, StringBuilder baseDir) throws IOException {
		File[] files = filePath.listFiles();
		if (files != null)
			for (File file : files) {
				if (file == null)
					continue;
				String name = file.getName();
				if (file.isDirectory()) {
					if (recursion) {
						getFilesList(list, file.getCanonicalFile(), true, adddir, new StringBuilder(baseDir).append(name).append(File.separator));
					}	
					if (adddir) {
						list.add(new StringBuilder(baseDir).append(name).append(File.separator).toString());
					}
				} else {
					list.add(new StringBuilder(baseDir).append(name).toString()) ;
				}
			}
		return list;
    }




	public static List<String> listFilesSort(File filePath, boolean adddir) {
		List<String> d = new ArrayListUtils<>();
		List<String> f = new ArrayListUtils<String>();
		File[] files = filePath.listFiles();
		if (files != null)
			for (File file : files) {
				if (file == null)
					continue;
				String name = file.getName();
				if (file.isDirectory()) {
					if (adddir) {
						d.add(new StringBuilder(name).append(File.separator).toString());
					}
				} else {
					f.add(name) ;
				}
			}
		Collections.sort(d, Collator.getInstance(java.util.Locale.CHINA));
		Collections.sort(f, Collator.getInstance(java.util.Locale.CHINA));
		if (adddir) {
			d.addAll(f);
			f.clear();
			return d;
		} else {
			d.clear();
			return f;
		}
    }






	/*
	 is Parent Directory
	 文件是否在父目录

	 Parameter:FilePath 文件,ParentDir 目录
	 isParentDirectory("/sdcard/a/b"  ,  "/../sdcard/") true
	 */
	public static boolean isParentDirectory(File FilePath, File ParentDir) {
		if (XObjects.isEmpty(FilePath) || XObjects.isEmpty(ParentDir))
			return false;
		String Parent;
		try {
			Parent = ParentDir.getCanonicalPath();
		} catch (IOException e) {
			Parent = ParentDir.getAbsolutePath();
		}
		if (!Parent.endsWith(File.separator))
			Parent += File.separator;
		String FileParent;
		try {
			FileParent = FilePath.getCanonicalPath();
		} catch (IOException e) {
			FileParent = FilePath.getAbsolutePath();
		}
		if (FileParent.startsWith(Parent) && ParentDir.exists())
			return true;
		return false;
	}


}
