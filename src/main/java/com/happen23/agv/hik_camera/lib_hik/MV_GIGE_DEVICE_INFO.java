package com.happen23.agv.hik_camera.lib_hik;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;

/**
 * <i>native declaration : include\CameraParams.h</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 */
@Structure.FieldOrder({"nIpCfgOption", "nIpCfgCurrent", "nCurrentIp", "nCurrentSubNetMask", "nDefultGateWay", "chManufacturerName", "chModelName", "chDeviceVersion", "chManufacturerSpecificInfo", "chSerialNumber", "chUserDefinedName", "nNetExport", "nReserved"})
public class MV_GIGE_DEVICE_INFO extends Structure {
	public int nIpCfgOption;
	/** IP configuration:bit31-static bit30-dhcp bit29-lla */
	public int nIpCfgCurrent;
	/** curtent  | en: */
	public int nCurrentIp;
	/** curtent subnet mask */
	public int nCurrentSubNetMask;
	/** current gateway | en */
	public int nDefultGateWay;
	/** C type : unsigned char[32] */
	public byte[] chManufacturerName = new byte[32];
	/** C type : unsigned char[32] */
	public byte[] chModelName = new byte[32];
	/** C type : unsigned char[32] */
	public byte[] chDeviceVersion = new byte[32];
	/** C type : unsigned char[48] */
	public byte[] chManufacturerSpecificInfo = new byte[48];
	/** C type : unsigned char[16] */
	public byte[] chSerialNumber = new byte[16];
	/** C type : unsigned char[16] */
	public byte[] chUserDefinedName = new byte[16];
	/** ch:\u7f51\u53e3IP\u5730\u5740 | en:GIGE IP Address */
	public int nNetExport;
	/** C type : unsigned int[4] */
	public int[] nReserved = new int[4];
	public MV_GIGE_DEVICE_INFO() {
		super();
	}
	public MV_GIGE_DEVICE_INFO(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends MV_GIGE_DEVICE_INFO implements Structure.ByReference {
		
	};
	public static class ByValue extends MV_GIGE_DEVICE_INFO implements Structure.ByValue {
		
	};
}
