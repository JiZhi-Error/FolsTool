package top.fols.box.net;

import java.util.List;
import top.fols.box.lang.XString;
import top.fols.box.util.XObjects;

public class XURL {

	private String UrlFormat = null;
	private String Url = null; // url
	private String Protocol = null;// http https ...
	private String HostAndPort = null;// xxx.xxx.xxx:xx
	private String Root = null;// http://xxx.xxx.xxx
	private String Dir = null;//  /a/
	private String Path = null;// /index.html
    private String HttpprotocolStr = "://";
	private String Host = null;
	private int Port = -1;
	private String FileNameDetailed = null;
	private String FileName = null;
	private String[] LevelDomainSplit = null;

	public static final String DomainLevel_SplitChar = ".";
	public static final String HostPort_SplitChar = ":";
	public static final String Path_SplitChar = "/";
	private static final String Path_SplitCharRepeatChar = "//";


	private String getHttpFormatUrl(String url) {
		if (XObjects.isEmpty(url))
			return url;
		while (url.indexOf(Path_SplitCharRepeatChar) > -1)
			url = url.replace(Path_SplitCharRepeatChar,  Path_SplitChar);
		return url;
	}



	
	public int getDomainLevel() {
		return getDomainLevelList().length;
	}
	public String getDomainLevel(int level) {
		return getDomainLevelList()[level - 1];
	}
	private String[] getDomainLevelList() {
		if (this.LevelDomainSplit != null)
			return this.LevelDomainSplit;
		List<String> Hostsplit = XString.split(getHost(), DomainLevel_SplitChar);
		if (Hostsplit.size() <= 1) {
			this.LevelDomainSplit = new String[]{getHost()};
		} else {
			int level = Hostsplit.size();
			StringBuffer sj = new StringBuffer();
			String[] s0 = new String[level];
			sj.append(Hostsplit.get(level - 1));
			s0[0] = Hostsplit.get(level - 1);
			for (int i = level - 1 - 1;i >= 0;i--) {
				sj.insert(0, Hostsplit.get(i) + ".");
				s0[level - i - 1] = sj.toString();
			}
			sj.delete(0, sj.length());
			this.LevelDomainSplit = s0;
		}
		Hostsplit.clear();
		return this.LevelDomainSplit;
	}



	public String getUrl() {
		return Url;
	}
	public String getUrlFormat() {
		if (UrlFormat != null)
			return this.UrlFormat;
		return UrlFormat = getRoot() + getPath();
	}
	public String getProtocol() {
		/*
		 http://127.0.0.1:7777/_HM4X/u
		 http://
		 */
		return Protocol;
	}
	public String getHost() {
		if (Host != null)
			return Host;
		String host = "";
		try {
			int start = getHostAndPort().indexOf(HostPort_SplitChar);//int start =HostAndPort.lastIndexOf(":");
			if (start > -1)
				host = getHostAndPort().substring(0, start);
			else
				host = getHostAndPort();
			Host = host;
		} catch (Exception e) {
			Host = "";
		}
		return Host;
	}
	public int getPort() {
		if (Port != -1)
			return Port;
		int port = -1;
		try {
			int start =getHostAndPort().indexOf(HostPort_SplitChar);//int start =HostAndPort.lastIndexOf(":");
			if (start > -1) {
				String portstr = getHostAndPort().substring(start + HostPort_SplitChar.length(), getHostAndPort().length());
				if (!portstr.equals(""))
					port = Integer.parseInt(portstr.trim());
				else
					port = -1;
			}
			Port = port;
		} catch (Exception e) {
			Port = -1;
		}
		return Port;
	}
	public String getHostAndPort() {
		return HostAndPort;
	}
	public String getRoot() {
		/*
		 http://127.0.0.1:7777/_HM4X/u
		 http://127.0.0.1:7777

		 127.0.0.1:7777/_HM4X/u
		 127.0.0.1:7777
		 */
		return Root;
	}
	public String getDir() {
		/*
		 http://127.0.0.1:7777/_HM4X/u
		 /_HM4X/
		 */
		return Dir;
	}
	public String getPath() {
		/*
		 http://127.0.0.1:7777/_HM4X/u
		 /_HM4X/u
		 */
		return Path;
	}
	public String getFileNameDetailed() {
		if (FileNameDetailed != null)
			return FileNameDetailed;
		String path = getPath();
		try {
			if (path.startsWith(Path_SplitChar) == false)
				path =  Path_SplitChar + path;
			int start = -1;
			/*
			 http://127.0.0.1:7777/_HM4X/u&r??_#hash.html#?a=b
			 u&r??_#hash.html#?a=b
			 */
			if ((start = path.lastIndexOf(Path_SplitChar)) > -1)
				path = path.substring(start + 1, path.length());
			FileNameDetailed = path;
		} catch (Exception e) {
			FileNameDetailed = "";
		}
		return FileNameDetailed;
	}

	public String getFileName() {
		if (FileName != null)
			return FileName;
		String path = getPath();
		try {
			if (path.startsWith(Path_SplitChar) == false)
				path =  Path_SplitChar + path;
			int start = -1;
			if ((start = path.lastIndexOf(Path_SplitChar)) > -1)
				path = path.substring(start + 1, path.length());
			/*
			 http://127.0.0.1:7777/_HM4X/u&r??_#hash.html#?a=b
			 u&r
			 */
			while ((start = path.lastIndexOf("?")) > -1)
				path = path.substring(0, start);
			while ((start = path.lastIndexOf("#")) > -1)
				path = path.substring(0, start);
			FileName = path;
		} catch (Exception e) {
			FileName = "";
		}
		return FileName;
	}


	public XURL(String url) {
		this(url, true);
	}
	public XURL(String urlAddres, boolean removeNotes) {
		if (urlAddres == null)
			urlAddres = "";
		//remove #
		int removeNotesStart = -1;
		if (removeNotes && (removeNotesStart = urlAddres.indexOf("#")) > -1)
			urlAddres = urlAddres.substring(0, removeNotesStart);

		this.Url = urlAddres;
		urlAddres = format(urlAddres);
		int protocolStrLen = this.HttpprotocolStr.length();
		int Protocolstart = urlAddres.indexOf(this.HttpprotocolStr);
		if (Protocolstart > -1) {
			this.Protocol = urlAddres.substring(0, Protocolstart);
			urlAddres = urlAddres.substring(protocolStrLen + Protocolstart, urlAddres.length());
		} else {
			this.Protocol = null;
			Protocolstart = 0;
		}	
		// http://ip.cn>>http://ip.cn/
		if (urlAddres.indexOf(XURL.Path_SplitChar) < 0)
			urlAddres +=  XURL.Path_SplitChar;
		urlAddres = getHttpFormatUrl(urlAddres);
		int HostAndPortEndstart = urlAddres.indexOf(XURL.Path_SplitChar);// http://ip.cn:8080/a/b/c/1.html >>17
		this.HostAndPort = urlAddres.substring(0, HostAndPortEndstart);//  >>ip.cn:8080
		if (this.Protocol == null)
			this.Root = this.HostAndPort ;//  >>http://ip.cn:8080
		else
			this.Root = this.Protocol + this.HttpprotocolStr + this.HostAndPort ;//  >>http://ip.cn:8080
		urlAddres = urlAddres.substring(HostAndPortEndstart, urlAddres.length());
		this.Path = urlAddres;// /a/b/c/1.html
		this.Dir = urlAddres.substring(0, urlAddres.lastIndexOf(XURL.Path_SplitChar) + XURL.Path_SplitChar.length()); // /a/b/c/
		if (! this.Dir.startsWith(XURL.Path_SplitChar))
			this.Dir =  XURL.Path_SplitChar + this.Dir;

		this.LevelDomainSplit = null;
		this.UrlFormat = null;
		this.Host = null;
		this.Port = -1;
		this.FileNameDetailed = null;
		this.FileName = null;
	}



	private static String format(String str) {
		byte[] value = str.getBytes();
		int len = value.length;
        int st = 0;
        while ((st < len) && ((value[st] & 0xff) <= ' ')) {
            st++;
        }
        return new String(value, st, len - st);
	}

	@Override
	public String toString() {
		// TODO: Implement this method
		return getUrl().toString();
	}




}
