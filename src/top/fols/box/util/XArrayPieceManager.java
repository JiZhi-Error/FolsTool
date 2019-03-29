package top.fols.box.util;
public class XArrayPieceManager implements java.io.Serializable {
	private static final long serialVersionUID = 784920843L;

	private long length;//FileLength
	private long pieceCount;//
	private long pieceSize;//BufferSize
	public static int pieceBufDefaultSize = 8192;

	public XArrayPieceManager(long length, long pieceSize) {
		updatepPieceInfo(length, pieceSize);
	}
	public XArrayPieceManager(long pieceSize) {
		updatepPieceInfo(Long.MAX_VALUE, pieceSize);
	}

	public void updatepPieceInfo(long PieceBufSize) {
		updatepPieceInfo(this.length, PieceBufSize);
	}
	public void updatepPieceInfo(long length, long PieceBufSize) {
		if (PieceBufSize < 1)
			throw new RuntimeException("pieceBufSize=" + PieceBufSize + ", min=1");
		if (length < 0)
			throw new RuntimeException("length=" + length + ", min=0");

		this.pieceSize = PieceBufSize;
		this.length = length;
		this.pieceCount = getIndexPiece(length);
		if (this.pieceCount == 0 && length > 0)
			++this.pieceCount;
		else if (((this.pieceCount - 1) * this.pieceSize) + this.pieceSize != length)
			++this.pieceCount;
	}

	public long getPieceIndexStart(long newPiece) {
		if (newPiece < 0 || newPiece >= pieceCount)
			throw new IndexOutOfBoundsException(String.format("hopePiece=%s, minPiece=%s, pieceCount=%s", newPiece, 0, pieceCount));
		return newPiece * this.pieceSize;
	}
	public long getPieceIndexEnd(long newPiece) {
		if (newPiece < 0 || newPiece >= pieceCount)
			throw new IndexOutOfBoundsException(String.format("hopePiece=%s, minPiece=%s, pieceCount=%s", newPiece, 0, pieceCount));
		long end = (newPiece * this.pieceSize) + this.pieceSize;
		if (end < 1)
			end = Long.MAX_VALUE;
		if (end >= length)
			return length - 1;
		return end - 1;
	}


	public long getPieceLength(long newPiece) {
		return (getPieceIndexEnd(newPiece) + 1) - getPieceIndexStart(newPiece);
	}
	public long getPieceNumber() {
		return this.pieceCount;
	}
	public long getPieceBufSize() {
		return this.pieceSize;
	}
	public long getIndexPiece(long index) {
		long newPiece = index / this.pieceSize;
		return newPiece;
	}
	public long length() {
		return length;
	}
}
