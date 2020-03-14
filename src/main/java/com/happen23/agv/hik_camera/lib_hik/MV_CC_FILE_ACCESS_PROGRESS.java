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
@Structure.FieldOrder({"nCompleted", "nTotal", "nReserved"})
public class MV_CC_FILE_ACCESS_PROGRESS extends Structure {
	/** \u5df2\u5b8c\u6210\u7684\u957f\u5ea6 | en:Completed Length */
	public long nCompleted;
	/** \u603b\u957f\u5ea6 | en:Total Length */
	public long nTotal;
	/**
	 * \u9884\u7559 | en:Reserved<br>
	 * C type : unsigned int[8]
	 */
	public int[] nReserved = new int[8];
	public MV_CC_FILE_ACCESS_PROGRESS() {
		super();
	}
	/**
	 * @param nCompleted \u5df2\u5b8c\u6210\u7684\u957f\u5ea6 | en:Completed Length<br>
	 * @param nTotal \u603b\u957f\u5ea6 | en:Total Length<br>
	 * @param nReserved \u9884\u7559 | en:Reserved<br>
	 * C type : unsigned int[8]
	 */
	public MV_CC_FILE_ACCESS_PROGRESS(long nCompleted, long nTotal, int nReserved[]) {
		super();
		this.nCompleted = nCompleted;
		this.nTotal = nTotal;
		if ((nReserved.length != this.nReserved.length)) 
			throw new IllegalArgumentException("Wrong array size !");
		this.nReserved = nReserved;
	}
	public MV_CC_FILE_ACCESS_PROGRESS(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends MV_CC_FILE_ACCESS_PROGRESS implements Structure.ByReference {
		
	};
	public static class ByValue extends MV_CC_FILE_ACCESS_PROGRESS implements Structure.ByValue {
		
	};
}
