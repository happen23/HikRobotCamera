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
@Structure.FieldOrder({"pUserFileName", "pDevFileName", "nReserved"})
public class MV_CC_FILE_ACCESS extends Structure {
	/**
	 * \u7528\u6237\u6587\u4ef6\u540d | en:User file name<br>
	 * C type : const char*
	 */
	public Pointer pUserFileName;
	/**
	 * \u8bbe\u5907\u6587\u4ef6\u540d | en:Device file name<br>
	 * C type : const char*
	 */
	public Pointer pDevFileName;
	/**
	 * \u9884\u7559 | en:Reserved<br>
	 * C type : unsigned int[32]
	 */
	public int[] nReserved = new int[32];
	public MV_CC_FILE_ACCESS() {
		super();
	}
	/**
	 * @param pUserFileName \u7528\u6237\u6587\u4ef6\u540d | en:User file name<br>
	 * C type : const char*<br>
	 * @param pDevFileName \u8bbe\u5907\u6587\u4ef6\u540d | en:Device file name<br>
	 * C type : const char*<br>
	 * @param nReserved \u9884\u7559 | en:Reserved<br>
	 * C type : unsigned int[32]
	 */
	public MV_CC_FILE_ACCESS(Pointer pUserFileName, Pointer pDevFileName, int nReserved[]) {
		super();
		this.pUserFileName = pUserFileName;
		this.pDevFileName = pDevFileName;
		if ((nReserved.length != this.nReserved.length)) 
			throw new IllegalArgumentException("Wrong array size !");
		this.nReserved = nReserved;
	}
	public MV_CC_FILE_ACCESS(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends MV_CC_FILE_ACCESS implements Structure.ByReference {
		
	};
	public static class ByValue extends MV_CC_FILE_ACCESS implements Structure.ByValue {
		
	};
}