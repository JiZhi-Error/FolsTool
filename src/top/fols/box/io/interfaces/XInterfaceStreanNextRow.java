package top.fols.box.io.interfaces;
import java.io.IOException;
import top.fols.box.annotation.XAnnotations;
import top.fols.box.statics.XStaticFixedValue;

public interface XInterfaceStreanNextRow<E extends Object> {
	public static final byte[] nullBytes = XStaticFixedValue.nullbyteArray;
    @XAnnotations("default") 
	public static final byte[] Bytes_NextLineN = XStaticFixedValue.Bytes_NextLineN;
	public static final byte[] Bytes_NextLineRN = XStaticFixedValue.Bytes_NextLineRN;

	public static final char[] nullChars = XStaticFixedValue.nullcharArray;
    @XAnnotations("default")
	public static final char[] Chars_NextLineN = XStaticFixedValue.Chars_NextLineN;
	public static final char[] Chars_NextLineRN = XStaticFixedValue.Chars_NextLineRN;


	public abstract E readLine() throws IOException;
    public abstract E readLine(E splitChar)throws IOException;
	public abstract E readLine(E splitChar, boolean resultAddSplitChar)throws IOException;
	public abstract E readLineDefaultSplitChar();
    public abstract boolean readLineIsReadToSeparator();

}
