package top.fols.atri.buffer.bytes;

import static top.fols.atri.lang.Finals.*;
public class ByteArrayBuffer extends ByteBufferOption {
	public ByteArrayBuffer() {
		this(EMPTY_BYTE_BUFFER, 0, 0);
	}
	public ByteArrayBuffer(byte[] array) {
		this(array, 0, array.length);
	}
	public ByteArrayBuffer(byte[] buffer, int position, int size) {
		super(buffer, position, size);
	}

	@Override public int stream_read(byte[] buf, int off, int len) { 
		return -1;
	}
}
