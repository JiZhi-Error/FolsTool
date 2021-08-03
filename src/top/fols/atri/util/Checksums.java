package top.fols.atri.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.Adler32;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

import top.fols.atri.io.Streams;
import top.fols.box.io.digest.XChecksumOutputStream;
import top.fols.box.util.encode.XHexEncoder;

@SuppressWarnings("rawtypes")
public class Checksums {
	
	public static long getValue(Checksum d, InputStream input) throws IOException {
		XChecksumOutputStream out = Checksums.wrapToStream(d);
		Streams.copy(input, out);
		return out.getValue();
	}
	
	public static long getValue(Checksum d, byte[] input) throws IOException {
		XChecksumOutputStream out = Checksums.wrapToStream(d);
		Streams.copy(input, out);
		return out.getValue();
	}


	public static String toHex(long value) {
        return Long.toHexString(value);
    }
	
	public static XChecksumOutputStream<OutputStream> wrapToStream(Checksum d) {
		return new XChecksumOutputStream<>(d);
	}
	
	public static Adler32 adler32Instance() {
		return new Adler32();
	}
	public static CRC32 crc32Instance() {
		return new CRC32();
	}
}
