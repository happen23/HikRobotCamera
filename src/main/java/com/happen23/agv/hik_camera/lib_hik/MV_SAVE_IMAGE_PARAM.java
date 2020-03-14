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
@Structure.FieldOrder({"pData", "nDataLen", "enPixelType", "nWidth", "nHeight", "pImageBuffer", "nImageLen", "nBufferSize", "enImageType"})
public class MV_SAVE_IMAGE_PARAM extends Structure {
	/**
	 * [IN]     ch:\u8f93\u5165\u6570\u636e\u7f13\u5b58 | en:Input Data Buffer<br>
	 * C type : unsigned char*
	 */
	public Pointer pData;
	/** [IN]    ch:\u8f93\u5165\u6570\u636e\u5927\u5c0f | en:Input Data Size */
	public int nDataLen;
	/**
	 * @see MvGvspPixelType<br>
	 * [IN]     ch:\u8f93\u5165\u6570\u636e\u7684\u50cf\u7d20\u683c\u5f0f | en:Input Data Pixel Format<br>
	 * C type : MvGvspPixelType
	 */
	public int enPixelType;
	/** [IN]     ch:\u56fe\u50cf\u5bbd | en:Image Width */
	public short nWidth;
	/** [IN]     ch:\u56fe\u50cf\u9ad8 | en:Image Height */
	public short nHeight;
	/**
	 * [OUT]    ch:\u8f93\u51fa\u56fe\u7247\u7f13\u5b58 | en:Output Image Buffer<br>
	 * C type : unsigned char*
	 */
	public Pointer pImageBuffer;
	/** [OUT]    ch:\u8f93\u51fa\u56fe\u7247\u5927\u5c0f | en:Output Image Size */
	public int nImageLen;
	/** [IN]     ch:\u63d0\u4f9b\u7684\u8f93\u51fa\u7f13\u51b2\u533a\u5927\u5c0f | en:Output buffer size provided */
	public int nBufferSize;
	/**
	 * @see MV_SAVE_IAMGE_TYPE<br>
	 * [IN]     ch:\u8f93\u51fa\u56fe\u7247\u683c\u5f0f | en:Output Image Format<br>
	 * C type : MV_SAVE_IAMGE_TYPE
	 */
	public int enImageType;
	public MV_SAVE_IMAGE_PARAM() {
		super();
	}
	/**
	 * @param pData [IN]     ch:\u8f93\u5165\u6570\u636e\u7f13\u5b58 | en:Input Data Buffer<br>
	 * C type : unsigned char*<br>
	 * @param nDataLen [IN]    ch:\u8f93\u5165\u6570\u636e\u5927\u5c0f | en:Input Data Size<br>
	 * @param enPixelType @see MvGvspPixelType<br>
	 * [IN]     ch:\u8f93\u5165\u6570\u636e\u7684\u50cf\u7d20\u683c\u5f0f | en:Input Data Pixel Format<br>
	 * C type : MvGvspPixelType<br>
	 * @param nWidth [IN]     ch:\u56fe\u50cf\u5bbd | en:Image Width<br>
	 * @param nHeight [IN]     ch:\u56fe\u50cf\u9ad8 | en:Image Height<br>
	 * @param pImageBuffer [OUT]    ch:\u8f93\u51fa\u56fe\u7247\u7f13\u5b58 | en:Output Image Buffer<br>
	 * C type : unsigned char*<br>
	 * @param nImageLen [OUT]    ch:\u8f93\u51fa\u56fe\u7247\u5927\u5c0f | en:Output Image Size<br>
	 * @param nBufferSize [IN]     ch:\u63d0\u4f9b\u7684\u8f93\u51fa\u7f13\u51b2\u533a\u5927\u5c0f | en:Output buffer size provided<br>
	 * @param enImageType @see MV_SAVE_IAMGE_TYPE<br>
	 * [IN]     ch:\u8f93\u51fa\u56fe\u7247\u683c\u5f0f | en:Output Image Format<br>
	 * C type : MV_SAVE_IAMGE_TYPE
	 */
	public MV_SAVE_IMAGE_PARAM(Pointer pData, int nDataLen, int enPixelType, short nWidth, short nHeight, Pointer pImageBuffer, int nImageLen, int nBufferSize, int enImageType) {
		super();
		this.pData = pData;
		this.nDataLen = nDataLen;
		this.enPixelType = enPixelType;
		this.nWidth = nWidth;
		this.nHeight = nHeight;
		this.pImageBuffer = pImageBuffer;
		this.nImageLen = nImageLen;
		this.nBufferSize = nBufferSize;
		this.enImageType = enImageType;
	}
	public MV_SAVE_IMAGE_PARAM(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends MV_SAVE_IMAGE_PARAM implements Structure.ByReference {
		
	};
	public static class ByValue extends MV_SAVE_IMAGE_PARAM implements Structure.ByValue {
		
	};
}