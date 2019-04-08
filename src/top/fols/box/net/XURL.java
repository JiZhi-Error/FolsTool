package top.fols.box.net;

import java.util.List;
import top.fols.box.annotation.XAnnotations;
import top.fols.box.annotation.XExample;
import top.fols.box.lang.XString;
import top.fols.box.util.XObjects;

@XAnnotations("it does not convert absolute addresses, protocol://user@host:port/dir/filename?param=value&multiplyParam=value")
public class XURL {
	private String uUrl = null; //origin url
	private String uProtocol = null;// http https ...
	private String uHostAndPort = null;// xxx.xxx.xxx:xx
	private String uRoot = null;// http://xxx.xxx.xxx
	private String uDir = null;//  /a/
	private String uPath = null;// /index.html
	private String uRef = null;//#后面内容
	private String uUser = null;//协议和@之间的内容

	private String uAuthority = null;//协议和根目录之间的内容
	private String uUrlFormat = null;//格式化后的URL
	private String uHost = null;
	private int uPort = -1;
	private String uFilePath = null;// uPath去除?#符号后的文件路径
	private String uFileNameDetailed = null;
	private String uFileName = null;
	private String[] uLevelDomainSplit = null;
	private String uDirName = null;
	
	public static final String HttpProtocolSplitChars = "://";
	public static final String DomainLevelSplitChars = ".";
	public static final String HostPortSplitChars = ":";
	public static final String PathSplitChars = "/";
	public static final char PathSplitchar = '/';
	public static final String UserAndHostSplitChars = "@";
	public static final String UrlAndParamSplitChars = "?";
	public static final String ParamSplitChars = "&";
	public static final String ParamKeyValueSplitChars = "=";

	private static String getFormatUrl0(String url) {
		if (XObjects.isEmpty(url))
			return url;
		StringBuilder buf = new StringBuilder();
		int size = url.length();
		boolean lastSplitChar = false;
		for (int i = 0;i < size;i++) {
			if (url.charAt(i) == PathSplitchar) {
				if (!lastSplitChar)
					buf.append(PathSplitchar);
				lastSplitChar = true;
				continue;
			} else {
				lastSplitChar = false;
				buf.append(url.charAt(i));
			}
		}
		String newUrl = buf.toString();
		buf = null;
		return newUrl;
	}



	public int getDomainLevel() {
		return getDomainLevelList().length;
	}
	public String getDomainLevel(int level) {
		return getDomainLevelList()[level - 1];
	}
	private String[] getDomainLevelList() {
		if (this.uLevelDomainSplit != null)
			return this.uLevelDomainSplit;
		List<String> Hostsplit = XString.split(getHost(), DomainLevelSplitChars);
		if (Hostsplit.size() <= 1) {
			this.uLevelDomainSplit = new String[]{getHost()};
		} else {
			int level = Hostsplit.size();
			StringBuilder sj = new StringBuilder();
			String[] s0 = new String[level];
			sj.append(Hostsplit.get(level - 1));
			s0[0] = Hostsplit.get(level - 1);
			for (int i = level - 1 - 1;i >= 0;i--) {
				sj.insert(0, Hostsplit.get(i) + ".");
				s0[level - i - 1] = sj.toString();
			}
			sj = null;
			this.uLevelDomainSplit = s0;
		}
		Hostsplit.clear();
		return this.uLevelDomainSplit;
	}


	@XAnnotations("basic processed create an Url passed in by the instance")
	public String getUrl() {
		return uUrl;
	}
	@XAnnotations("processed Url")
	public String getUrlFormat() {
		if (uUrlFormat != null)
			return this.uUrlFormat;
		return uUrlFormat = new StringBuilder(getRoot()).append(getPathDetailed()).toString();
	}
	@XAnnotations("http https ...")
	public String getProtocol() {
		/*
		 http://127.0.0.1:7777/_HM4X/u
		 http://
		 */
		return uProtocol;
	}
	public String getHost() {
		if (uHost != null)
			return uHost;
		String newHost = "";
		int start = getHostAndPort().indexOf(HostPortSplitChars);
		if (start > -1)
			newHost = getHostAndPort().substring(0, start);
		else
			newHost = getHostAndPort();
		uHost = newHost;
		return uHost;
	}
	public int getPort() {
		if (uPort != -1)
			return uPort;
		int port = -1;
		try {
			int start = getHostAndPort().indexOf(HostPortSplitChars);
			if (start > -1) {
				String portstr = getHostAndPort().substring(start + HostPortSplitChars.length(), getHostAndPort().length());
				if (!portstr.equals(""))
					port = Integer.parseInt(portstr.trim());
				else
					port = -1;
			}
			uPort = port;
		} catch (Exception e) {
			uPort = -1;
		}
		return uPort;
	}
	public String getHostAndPort() {
		/*
		 http://127.0.0.1:7777/_HM4X/u

		 127.0.0.1:7777
		 */
		return uHostAndPort;
	}
	public String getUser() {
		return this.uUser;
	}





	@XAnnotations("url root")
	@XExample(e = "http://xxx:xx https://xxx ...",v="")
	public String getRoot() {
		/*
		 http://127.0.0.1:7777/_HM4X/u
		 http://127.0.0.1:7777
		 127.0.0.1:7777/1

		 http://127.0.0.1:7777/_HM4X/u
		 http://127.0.0.1:7777
		 127.0.0.1:7777/1
		 */
		return uRoot;
	}


	@XAnnotations("url dir")
	@XExample({
			@XExample(e = "http://127.0.0.1:7777/_HM4X/u",v= "/_HM4X/"),
			@XExample(e = "http://127.0.0.1:7777/",v="/")
		})
	public String getDir() {
		/*
		 http://127.0.0.1:7777/_HM4X/u
		 /_HM4X/
		 */
		return uDir;
	}



	@XAnnotations("url getPathDetailed() after formatting")
	@XExample({
			@XExample(e = "http://127.0.0.1:7777/_HM4X/u?asd&a=2",v= "/_HM4X/u")
		})
	public String getFilePath() {
		/*
		 http://127.0.0.1:7777/_HM4X/u?asd
		 /_HM4X/u
		 */
		if (this.uFilePath != null)
			return this.uFilePath;
		String tmp = getPathDetailed();
		int lastSplit = tmp.lastIndexOf(PathSplitChars);
		int start;
		if ((start = tmp.indexOf("#")) > -1)
			tmp = tmp.substring(0, start);
		if ((start = tmp.lastIndexOf(UrlAndParamSplitChars)) > -1 && start > lastSplit)
			tmp = tmp.substring(0, start);
		return this.uFilePath = tmp;
	}

	@XAnnotations("url path and param")
	@XExample({
			@XExample(e = "http://127.0.0.1:7777/_HM4X/u?asd&a=2",v= "/_HM4X/u?asd&a=2"),
			@XExample(e = "http://127.0.0.1:7777/",v= "/"),
			@XExample(e = "http://127.0.0.1:7777",v= "/")
		})
	public String getPathDetailed() {
		/*
		 http://127.0.0.1:7777/_HM4X/u?asd
		 /_HM4X/u?asd
		 */
		return uPath;
	}



	@XAnnotations("url getFileNameDetailed() after formatting")
	@XExample({
			@XExample(e = "http://127.0.0.1:7777/_HM4X/u?asd&a=2",v= "u")
		})
	public String getFileName() {
		/*
		 http://127.0.0.1:7777/_HM4X/u&r??_#hash.html#?a=b
		 u&r
		 */
		if (uFileName != null)
			return uFileName;
		String tmp = getFilePath();
		if (tmp.startsWith(PathSplitChars) == false)
			tmp =  new StringBuilder(PathSplitChars).append(tmp).toString();
		int start = -1;
		if ((start = tmp.lastIndexOf(PathSplitChars)) > -1)
			tmp = tmp.substring(start + PathSplitChars.length(), tmp.length());
		uFileName = tmp;
		return uFileName;
	}

	@XAnnotations("url filename and param")
	@XExample({
			@XExample(e = "http://127.0.0.1:7777/_HM4X/u?asd&a=2",v= "u?asd&a=2")
		})
	public String getFileNameDetailed() {
		/*
		 http://127.0.0.1:7777/_HM4X/u&r??_#hash.html#?a=b
		 u&r??_#hash.html#?a=b
		 */
		if (uFileNameDetailed != null)
			return uFileNameDetailed;
		String tmp = getPathDetailed();
		if (tmp.startsWith(PathSplitChars) == false)
			tmp =  new StringBuilder(PathSplitChars).append(tmp).toString();
		int start = -1;
		if ((start = tmp.lastIndexOf(PathSplitChars)) > -1)
			tmp = tmp.substring(start + PathSplitChars.length(), tmp.length());
		uFileNameDetailed = tmp;
		return uFileNameDetailed;
	}



	@XAnnotations("url dir name")
	@XExample({
			@XExample(e = "http://_HM4X:nmsl/gvv/e/ec/cc../.././gg/sss/",v= "sss"),
			@XExample(e = "http://_HM4X:nmsl/gvv/e/ec/cc../.././gg/sss/u",v= "sss"),
			@XExample(e = "http://_HM4X:nmsl/a",v= ""),
			@XExample(e = "http://_HM4X:nmsl)",v= "")
		})
	public String getDirName() {
		/*
		 http://127.0.0.1:7777/_HM4X/u&r??_#hash.html#?a=b
		 _HM4X
		 */
		if (uDirName != null)
			return uDirName;
		String tmp = getDir();
		int lastIndex = tmp.lastIndexOf(PathSplitChars);
		int startIndex = tmp.lastIndexOf(PathSplitChars, lastIndex - PathSplitChars.length());
		if (lastIndex > startIndex)
			uDirName = tmp.substring(startIndex + PathSplitChars.length(), lastIndex);
		else
			uDirName = "";
		return uDirName;
	}



	@XExample({
			@XExample(e = "http://_HM4X:nmsl/gvv/e/ec/cc../gg/sss/",v= "/gvv/e/ec/cc../gg/"),
			@XExample(e = "http://_HM4X:nmsl/gvv/e/ec/cc../gg/sss/u",v= "/gvv/e/ec/cc../gg/sss/"),
			@XExample(e = "http://_HM4X:nmsl/a",v= "/"),
			@XExample(e = "http://_HM4X:nmsl)",v= "/")
		})
	public XURL getParent() {
		String tmp = getPathDetailed();
		int lastIndex = tmp.lastIndexOf(PathSplitChars);
		if (tmp.endsWith(PathSplitChars))
			lastIndex = tmp.lastIndexOf(PathSplitChars, lastIndex - PathSplitChars.length());
		if (lastIndex <= -1) {
			tmp = "";
		} else {
			tmp = tmp.substring(0, lastIndex + PathSplitChars.length());
		}
		return new XURL(new StringBuilder(getRoot()).append(tmp).toString());
	}

	@XAnnotations("getPathDetailed().endsWith(Path_SplitChar);")
	public boolean isDir() {
		return getPathDetailed().endsWith(PathSplitChars);
	}
	@XAnnotations("#...")
	public String getRef() {
		return this.uRef;
	}



	public String getUserAndHostAndPort() {
		if (this.uAuthority != null)
			return this.uAuthority;
		StringBuilder buf = new StringBuilder();
		if (getUser() != null)
			buf.append(getUser()).append(UserAndHostSplitChars);
		buf.append(getHostAndPort());
		this.uAuthority = buf.toString();
		buf = null;
		return this.uAuthority;
	}


	public XURL(String spec) {
		if (spec == null)
			spec = "";

		int start = 0;
		int limit = spec.length();
		//remove #
		int removeNotesStart = -1;
		if ((removeNotesStart = spec.indexOf("#")) > -1) {
			limit = removeNotesStart;
			uRef = spec.substring(removeNotesStart + 1, spec.length());
		}
		while ((limit > 0) && (spec.charAt(limit - 1) <= ' '))
			limit--;
		while ((start < limit) && (spec.charAt(start) <= ' ')) 
			start++;

		this.uUrl = spec = spec.substring(start, limit);
		int protocolStrLen = this.HttpProtocolSplitChars.length();
		int protocolStart = spec.indexOf(this.HttpProtocolSplitChars);
		if (protocolStart > -1) {
			this.uProtocol = spec.substring(0, protocolStart);
			spec = spec.substring(protocolStrLen + protocolStart, spec.length());
		} else {
			this.uProtocol = null;
			protocolStart = 0;
		}
		int split = spec.indexOf(PathSplitChars);
		int paramSplitCharIndex = spec.indexOf(UrlAndParamSplitChars);
		if (paramSplitCharIndex > -1 && (paramSplitCharIndex < split || split <= -1)) {
			spec =
				new StringBuilder(spec.substring(0, paramSplitCharIndex))
				.append(PathSplitchar)
				.append(spec.substring(paramSplitCharIndex, spec.length()))
				.toString();
		} else {
			if (split <= -1)
				spec = new StringBuilder(spec).append(XURL.PathSplitChars).toString();
		}
		int hostAndPortEndIndex = (spec = getFormatUrl0(spec)).indexOf(PathSplitChars);// http://ip.cn:8080/a/b/c/1.html >>17
		int userIx = spec.lastIndexOf(UserAndHostSplitChars, hostAndPortEndIndex - 1);
		if (userIx > -1)
			this.uUser = spec.substring(0, userIx);
		this.uHostAndPort = spec.substring(userIx <= -1 ?0: userIx + UserAndHostSplitChars.length(), hostAndPortEndIndex);//  >>ip.cn:8080

		StringBuilder buf = new StringBuilder();
		if (this.uProtocol != null)
			buf.append(this.uProtocol).append(this.HttpProtocolSplitChars);
		if (this.uUser != null)
			buf.append(this.uUser).append(UserAndHostSplitChars);
		this.uRoot = buf.append(this.uHostAndPort).toString();buf = null;//  >>http://ip.cn:8080

		this.uPath = (spec = spec.substring(hostAndPortEndIndex, spec.length()));// /a/b/c/1.html
		this.uDir = spec.substring(0, spec.lastIndexOf(XURL.PathSplitChars) + XURL.PathSplitChars.length()); // /a/b/c/
		this.uUrlFormat = null;
		this.uHost = null;
		this.uPort = -1;
		this.uFileNameDetailed = null;
		this.uFileName = null;
		this.uLevelDomainSplit = null;
		this.uDirName = null;

	}



	@Override
	public String toString() {
		// TODO: Implement this method
		return getUrlFormat();
	}

	public XURLParam getParam() {
		String pathd = getFileNameDetailed();
		XURLParam xurlp = new XURLParam();
		XURLParam.set(xurlp, new StringBuilder(pathd).append(ParamSplitChars).toString());
		XURLParam.init(xurlp);
		return xurlp;
	}





	//?
	/*

	 getAbsAddres("//XSt/*?:]/tt/////.//./././a/b/v//x//a/v**v//n///...//../../();").equals(new File("//XSt/*?:]/tt/////.//./././a/b/v//x//a/v**v//n///...//../../();").getCanonicalPath()); >> true
	 getAbsAddres("//XSt/*?:]/tt/////.//./././a/b/v//x//a/v**v//n///...//../../();"); >> "/XSt/*?:]/tt/a/b/v/x/a/v**v/();"
	 */
	public static String getAbsAddres(String a) {
		if (a == null)
			return null;
		XURL xurl = new XURL(a);
		String pathDetailed = xurl.getPathDetailed();
		if (xurl.getProtocol() == null)
			return new StringBuilder().append(HttpProtocolSplitChars).append(xurl.getHostAndPort())
				.append(getAbsPath(pathDetailed)).toString();
		return new StringBuilder(xurl.getRoot())
			.append(getAbsPath(pathDetailed)).toString();
	}
	public static String getAbsPath(String a) {
		if (a == null)
			return null;
		a = getFormatUrl0(a);
		if (!a.startsWith(PathSplitChars)) 
			a = new StringBuilder(PathSplitChars).append(a).toString();
		String u = a.trim();a = null;
		return parsePath(u);
	}
	private static String parsePath(String path) {
		int i = 0;
		while ((i = path.indexOf("/./")) >= 0) {
			path = path.substring(0, i) + path.substring(i + 2);
		}
		while ((i = path.indexOf("/../")) >= 0) {
			int last = path.lastIndexOf('/', i - 1);
			if (last >= 0) 
				path = path.substring(0, last) + path.substring(i + 3, path.length());
			else
				path = path.substring(0, i) + path.substring(i + 3, path.length());
		}
		// Remove trailing .. if possible
		i = path.length() - 3;
		if (path.endsWith("/..")) {
			int last = path.lastIndexOf('/', path.length() - 3 - 1);
			if (last >= 0) 
				path = path.substring(0, last + 1);
			else
				path = "/";
		}
		// Remove trailing .
		if (path.endsWith("/."))
			path = path.substring(0, path.length() - 1);
		return path;
	}




	public static XURL abs(String url) {
		return new XURL(getAbsAddres(url));
	}
	public XURL abs() {
		return abs(getUrlFormat());
	}
}
