package com.happen23.agv.hik_camera.lib_hik;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;

/**
 * <i>native declaration : include\CameraParams.h</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 */
@Structure.FieldOrder({"nWidthValue", "nWidthMin", "nWidthMax", "nWidthInc", "nHeightValue", "nHeightMin", "nHeightMax", "nHeightInc", "fFrameRateValue", "fFrameRateMin", "fFrameRateMax", "enPixelType", "nSupportedPixelFmtNum", "enPixelList", "nReserved"})
public class MV_IMAGE_BASIC_INFO extends Structure {
	public short nWidthValue;
	public short nWidthMin;
	public int nWidthMax;
	public int nWidthInc;
	public int nHeightValue;
	public int nHeightMin;
	public int nHeightMax;
	public int nHeightInc;
	public float fFrameRateValue;
	public float fFrameRateMin;
	public float fFrameRateMax;
	/** ch:\u5f53\u524d\u7684\u50cf\u7d20\u683c\u5f0f | en:Current pixel format */
	public int enPixelType;
	/** ch:\u652f\u6301\u7684\u50cf\u7d20\u683c\u5f0f\u79cd\u7c7b | en:Support pixel format */
	public int nSupportedPixelFmtNum;
	/** C type : unsigned int[64] */
	public int[] enPixelList = new int[64];
	/** C type : unsigned int[8] */
	public int[] nReserved = new int[8];
	public MV_IMAGE_BASIC_INFO() {
		super();
	}
	public MV_IMAGE_BASIC_INFO(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends MV_IMAGE_BASIC_INFO implements Structure.ByReference {
		
	};
	public static class ByValue extends MV_IMAGE_BASIC_INFO implements Structure.ByValue {
		
	};
}
