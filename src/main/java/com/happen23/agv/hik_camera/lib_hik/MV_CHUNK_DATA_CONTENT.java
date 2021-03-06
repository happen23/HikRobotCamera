package com.happen23.agv.hik_camera.lib_hik;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;

/**
 * <i>native declaration : include\CameraParams.h</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 */
@Structure.FieldOrder({"pChunkData", "nChunkID", "nChunkLen", "nReserved"})
public class MV_CHUNK_DATA_CONTENT extends Structure {
	/** C type : unsigned char* */
	public Pointer pChunkData;
	public int nChunkID;
	public int nChunkLen;
	/**
	 * \u4fdd\u7559<br>
	 * C type : unsigned int[8]
	 */
	public int[] nReserved = new int[8];
	public MV_CHUNK_DATA_CONTENT() {
		super();
	}
	/**
	 * @param pChunkData C type : unsigned char*<br>
	 * @param nReserved \u4fdd\u7559<br>
	 * C type : unsigned int[8]
	 */
	public MV_CHUNK_DATA_CONTENT(Pointer pChunkData, int nChunkID, int nChunkLen, int nReserved[]) {
		super();
		this.pChunkData = pChunkData;
		this.nChunkID = nChunkID;
		this.nChunkLen = nChunkLen;
		if ((nReserved.length != this.nReserved.length)) 
			throw new IllegalArgumentException("Wrong array size !");
		this.nReserved = nReserved;
	}
	public MV_CHUNK_DATA_CONTENT(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends MV_CHUNK_DATA_CONTENT implements Structure.ByReference {
		
	};
	public static class ByValue extends MV_CHUNK_DATA_CONTENT implements Structure.ByValue {
		
	};
}
