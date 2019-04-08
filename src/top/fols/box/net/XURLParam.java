package top.fols.box.net;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import top.fols.box.annotation.XAnnotations;
import top.fols.box.lang.XString;
import top.fols.box.statics.XStaticFixedValue;

public class XURLParam {
	private String all;
	private boolean existParam;

	protected XURLParam() {}
	protected static void set(XURLParam o, String fileNameDetailed) {
		o.all = fileNameDetailed;
	}
	protected static void init(XURLParam o) {
		o.init();
	}


	public XURLParam(String url) {
		set(this, new StringBuilder(new XURL(url).getFileNameDetailed()).append(XURL.ParamSplitChars).toString());
		init(this);
	}
	private void init() {
		int ep = this.all == null ?-1: this.all.indexOf(XURL.UrlAndParamSplitChars);
		if (ep > -1) {
			this.all = this.all.substring(ep + XURL.UrlAndParamSplitChars.length(), this.all.length());
			this.existParam = true;
			if (this.existParam) {
				List<String> nk = new ArrayList<>();
				List<String> s = XString.split(this.all, XURL.ParamSplitChars);
				for (String kv:s) {
					int vindex = kv.indexOf(XURL.ParamKeyValueSplitChars);
					if (vindex > -1) {
						String k;
						String v;
						byte[] bs;

						bs = kv.substring(0, vindex).getBytes();
						k = new String(bs);
						bs = null;

						bs = kv.substring(vindex + XURL.ParamKeyValueSplitChars.length(), kv.length()).getBytes();
						v = new String(bs);
						bs = null;

						nk.add(k);
						param.put(k, v);
					}
				}
				this.keys = nk.toArray(new String[nk.size()]);
				nk.clear();
				s.clear();

			}
		}	
	}
	
	
	
	
	private String[] keys = XStaticFixedValue.nullStringArray;
	private Map<String,String> param = new HashMap<>();
	public String getValue(String key, boolean decode, String decoderCharset) {
		if (key == null || !existParam)
			return null;
		String result = param.get(key);
		if (result == null)
			return null;
		if (decode) {
			if (decoderCharset == null) {
				return XURLCoder.decodeS(result);
			} else {
				return XURLCoder.decodeS(result, decoderCharset);
			}
		}
		return result;
	}
	public String getValue(String key) {
		return this.getValue(key, true, null);
	}
	@XAnnotations("return not decode value.")
	public String getValueND(String key) {
		return this.getValue(key, false, null);
	}
	public String[] getKeys() {
		return this.keys;
	}
	public boolean containsKey(String key) {
		if (!existParam)
			return false;
		return this.param.containsKey(key);
	}
	public Map<String,String> getAll() {
		return this.param;
	}
	public boolean isExistParam() {
		return this.existParam;
	}
	@Override
	public String toString() {
		// TODO: Implement this method
		return this.param.toString();
	}
	public void clear() {
		this.param.clear();
	}


}
