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
@Structure.FieldOrder({"nReviceDataSize", "nThrowFrameCount", "nNetRecvFrameCount", "nRequestResendPacketCount", "nResendPacketCount"})
public class MV_NETTRANS_INFO extends Structure {
	/** ch:\u5df2\u63a5\u6536\u6570\u636e\u5927\u5c0f  [\u7edf\u8ba1StartGrabbing\u548cStopGrabbing\u4e4b\u95f4\u7684\u6570\u636e\u91cf] | en:Received Data Size  [Calculate the Data Size between StartGrabbing and StopGrabbing] */
	public long nReviceDataSize;
	/** ch:\u4e22\u5e27\u6570\u91cf | en:Throw frame number */
	public int nThrowFrameCount;
	public int nNetRecvFrameCount;
	/** \u8bf7\u6c42\u91cd\u53d1\u5305\u6570 */
	public long nRequestResendPacketCount;
	/** \u91cd\u53d1\u5305\u6570 */
	public long nResendPacketCount;
	public MV_NETTRANS_INFO() {
		super();
	}
	/**
	 * @param nReviceDataSize ch:\u5df2\u63a5\u6536\u6570\u636e\u5927\u5c0f  [\u7edf\u8ba1StartGrabbing\u548cStopGrabbing\u4e4b\u95f4\u7684\u6570\u636e\u91cf] | en:Received Data Size  [Calculate the Data Size between StartGrabbing and StopGrabbing]<br>
	 * @param nThrowFrameCount ch:\u4e22\u5e27\u6570\u91cf | en:Throw frame number<br>
	 * @param nRequestResendPacketCount \u8bf7\u6c42\u91cd\u53d1\u5305\u6570<br>
	 * @param nResendPacketCount \u91cd\u53d1\u5305\u6570
	 */
	public MV_NETTRANS_INFO(long nReviceDataSize, int nThrowFrameCount, int nNetRecvFrameCount, long nRequestResendPacketCount, long nResendPacketCount) {
		super();
		this.nReviceDataSize = nReviceDataSize;
		this.nThrowFrameCount = nThrowFrameCount;
		this.nNetRecvFrameCount = nNetRecvFrameCount;
		this.nRequestResendPacketCount = nRequestResendPacketCount;
		this.nResendPacketCount = nResendPacketCount;
	}
	public MV_NETTRANS_INFO(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends MV_NETTRANS_INFO implements Structure.ByReference {
		
	};
	public static class ByValue extends MV_NETTRANS_INFO implements Structure.ByValue {
		
	};
}
