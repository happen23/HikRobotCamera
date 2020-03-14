package com.happen23.agv.hik_camera.lib_hik;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : include\CameraParams.h</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 */
@Structure.FieldOrder({"fCurValue", "fMax", "fMin", "nReserved"})
public class MVCC_FLOATVALUE extends Structure {
	/** ch:\u5f53\u524d\u503c | en:Current Value */
	public float fCurValue;
	public float fMax;
	public float fMin;
	/** C type : unsigned int[4] */
	public int[] nReserved = new int[4];
	public MVCC_FLOATVALUE() {
		super();
	}
	/**
	 * @param fCurValue ch:\u5f53\u524d\u503c | en:Current Value<br>
	 * @param nReserved C type : unsigned int[4]
	 */
	public MVCC_FLOATVALUE(float fCurValue, float fMax, float fMin, int nReserved[]) {
		super();
		this.fCurValue = fCurValue;
		this.fMax = fMax;
		this.fMin = fMin;
		if ((nReserved.length != this.nReserved.length)) 
			throw new IllegalArgumentException("Wrong array size !");
		this.nReserved = nReserved;
	}
	public MVCC_FLOATVALUE(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends MVCC_FLOATVALUE implements Structure.ByReference {
		
	};
	public static class ByValue extends MVCC_FLOATVALUE implements Structure.ByValue {
		
	};
}