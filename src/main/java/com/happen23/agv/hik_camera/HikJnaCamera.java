package com.happen23.agv.hik_camera;

import com.happen23.agv.hik_camera.lib_hik.*;
import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.UUID;

public class HikJnaCamera implements ICamera {

    private MV_CC_DEVICE_INFO_LIST mDevList = new MV_CC_DEVICE_INFO_LIST();
    private HashMap<String, Camera> mCameras = new HashMap<>();
    private HashMap<Pointer, String> mCameraNames = new HashMap<>();
    private String mNameEncode;
    private String mImageFormat;
    private int mJpegQuality = 80;
    private HikLibrary.MV_CC_RegisterImageCallBackEx_cbOutput_callback captureCallback
            = (Pointer pData, MV_FRAME_OUT_INFO_EX frameInfo, Pointer handle) -> {
        String cameraName = mCameraNames.get(handle);
        if (cameraName == null) {
            System.out.println("unknown camera "+handle+" sent this frame!");
            return;
        }
        Camera camera = mCameras.get(cameraName);
        if (camera == null) {
            System.out.println("not found camera named "+cameraName+" !");
            return;
        }
        if (camera.mState != State.GENERATING_IMAGE)
            return;

        MV_SAVE_IMAGE_PARAM_EX imageParam = new MV_SAVE_IMAGE_PARAM_EX();
        // 源数据
        imageParam.pData = pData;
        imageParam.nDataLen = frameInfo.nFrameLen;
        imageParam.enPixelType = frameInfo.enPixelType;
        imageParam.nWidth = frameInfo.nWidth;
        imageParam.nHeight = frameInfo.nHeight;
        // 目标数据
        if (mImageFormat.equals("bmp")) {
            imageParam.enImageType = HikLibrary.MV_SAVE_IAMGE_TYPE.MV_Image_Bmp;
        }else if (mImageFormat.equals("jpg")) {
            imageParam.enImageType = HikLibrary.MV_SAVE_IAMGE_TYPE.MV_Image_Jpeg;
            imageParam.nJpgQuality = mJpegQuality;
        }else{
            System.out.println("unknown image format: "+mImageFormat);
        }
        imageParam.pImageBuffer = camera.mImageBuffer;
        imageParam.nBufferSize = (int)camera.mImageBuffer.size();
        // 编码压缩图片
        int res_encode = HikLibrary.INSTANCE.MV_CC_SaveImageEx2(handle, imageParam);
        if (res_encode != HikLibrary.MV_OK) {
            System.out.println("camera " + cameraName + " generate image fail, err_code = 0x" + Integer.toHexString(res_encode));
            camera.mImageSize = 0;
        }else {
            camera.mImageSize = imageParam.nImageLen;
        }
        synchronized (camera) {
            if (camera.mState == State.GENERATING_IMAGE) {
                camera.mState = State.GRABING;
            } else {
                System.out.println("system busy? camera state: "+camera.mState);
            }
        }
    };

    interface State {
        int ENUMERATED = 10;
        int HANDLE_CREATED = 20;
        int OPENED = 30;
        int FRAME_BUF_ALLOCATED = 40;
        int IMAGE_BUF_ALLOCATED = 50;
        int CALLBACK_REGISTERED = 55;
        int GRABING = 60;
        int GENERATING_IMAGE = 70;
    }

    public class Camera {
        public String mName;
        PointerByReference mPtrRef;
        int mState;
        int mImageSize;
        Memory mImageBuffer;
    }

    public class CameraException extends RuntimeException {
        public String mCameraName;
        public int mErrCode;
        public String mErrMsg;
        public CameraException(String cameraName, int errCode, String errMsg){
            mCameraName = cameraName;
            mErrCode = errCode;
            mErrMsg = errMsg;
        }

        @Override
        public String toString() {
            return "CameraException{" +
                    "mCameraName='" + mCameraName + '\'' +
                    ", mErrCode=0x" + Integer.toHexString(mErrCode) +
                    ", mErrMsg='" + mErrMsg + '\'' +
                    '}';
        }
    }

    public HikJnaCamera(String nameEncode, String imageFormat) {
        mNameEncode = nameEncode;
        mImageFormat = imageFormat;
    }

    public static void main(String[] args) {
        HikJnaCamera hikJnaCamera = null;
        String format = "jpg";
        try {
            hikJnaCamera = new HikJnaCamera("gbk", format);
            hikJnaCamera.init();
            // 等待用户输入相机名字，顺便等待曝光稳定
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String cameraName = scanner.next();
                byte[] image = hikJnaCamera.takePhoto(cameraName);
                if (image != null){
                    File file = new File(cameraName+"."+format);
                    FileOutputStream out = new FileOutputStream(file);
                    out.write(image);
                }else{
                    break;
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }finally{
            if (hikJnaCamera != null) {
                hikJnaCamera.destroy();
            }
        }
    }

    @Override
    public void init() throws CameraException {
        int res_enum = HikLibrary.INSTANCE.MV_CC_EnumDevices(HikLibrary.MV_GIGE_DEVICE, mDevList);
        if (res_enum != HikLibrary.MV_OK) {
            throw new CameraException("", res_enum, "enum camera fail");
        }
        if (mDevList.nDeviceNum == 0){
            throw new CameraException("", res_enum, "no camera found!");
        }

        for (int i = 0; i < mDevList.nDeviceNum; i++) {
            MV_CC_DEVICE_INFO cameraInfo = mDevList.pDeviceInfo[i];
            Camera camera = new Camera();
            MV_GIGE_DEVICE_INFO gigeInfo = (MV_GIGE_DEVICE_INFO)cameraInfo.SpecialInfo.getTypedValue(MV_GIGE_DEVICE_INFO.class);
            byte[] rawCameraName = gigeInfo.chUserDefinedName;
            int j;
            for (j = 0; rawCameraName[j] != 0; j++);
            int rawCameraNameLen = j;
            rawCameraName = Arrays.copyOf(rawCameraName, rawCameraNameLen);
            BufferedReader br;
            try {
                br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(rawCameraName), mNameEncode));
                String name = br.readLine();
                if (name.startsWith("Vir")) {
                    camera.mName = "c"+(i+1);
                }else{
                    camera.mName = name;
                }
            }catch (UnsupportedEncodingException e){
                System.out.println("custom camera name decode fail, not support encoding: "+mNameEncode);
            }catch (IOException e){
                camera.mName = UUID.randomUUID().toString();
                System.out.println("custom camera name decode fail!"+e.getMessage());
            }
            camera.mState = State.ENUMERATED;
            mCameras.put(camera.mName, camera);

            PointerByReference handle = new PointerByReference();
            int res_creat = HikLibrary.INSTANCE.MV_CC_CreateHandle(handle, cameraInfo);
            if (res_creat != HikLibrary.MV_OK) {
                throw new CameraException(camera.mName, res_creat, "create fail");
            }
            camera.mPtrRef = handle;
            mCameraNames.put(handle.getValue(), camera.mName);
            camera.mState = State.HANDLE_CREATED;

            int res_open = HikLibrary.INSTANCE.MV_CC_OpenDevice(handle.getValue(), HikLibrary.MV_ACCESS_Exclusive, (short)0);
            if (res_open != HikLibrary.MV_OK) {
                throw new CameraException(camera.mName, res_open, "open fail");
            }
            camera.mState = State.OPENED;

            // 探测网络最佳包大小并尝试设置之
            int packetSize = HikLibrary.INSTANCE.MV_CC_GetOptimalPacketSize(handle.getValue());
            if (packetSize > 0)
            {
                int res_pkt_size = HikLibrary.INSTANCE.MV_CC_SetIntValue(handle.getValue(), "GevSCPSPacketSize", packetSize);
                if(res_pkt_size != HikLibrary.MV_OK)
                {
                    System.out.printf("camera %s set pkt size=%d fail, err_code=0x%08x\n", camera.mName, packetSize, res_pkt_size);
                }
            } else {
                System.out.printf("camera %s get pkt size=%d, but it must > 0\n", camera.mName, packetSize);
            }

            MVCC_INTVALUE intParam = new MVCC_INTVALUE();
            int res_get_param = HikLibrary.INSTANCE.MV_CC_GetIntValue(handle.getValue(),
                    "PayloadSize", intParam);
            if (res_get_param != HikLibrary.MV_OK) {
                throw new CameraException(camera.mName, res_get_param, "query param fail");
            }
            int frameSize = intParam.nCurValue;
            System.out.println("camera index "+ i + ", name " + camera.mName  +", frame size = " + frameSize);
            camera.mState = State.FRAME_BUF_ALLOCATED;
            camera.mImageBuffer = new Memory(3072*2048*4+2048);
            camera.mState = State.IMAGE_BUF_ALLOCATED;

            int res_reg_cb = HikLibrary.INSTANCE.MV_CC_RegisterImageCallBackEx(handle.getValue(),
                    captureCallback, camera.mPtrRef.getValue());
            if (res_reg_cb != HikLibrary.MV_OK) {
                throw new CameraException(camera.mName, res_reg_cb, "reg callback fail");
            }
            camera.mState = State.CALLBACK_REGISTERED;

            int res_start_grab = HikLibrary.INSTANCE.MV_CC_StartGrabbing(handle.getValue());
            if (res_start_grab != HikLibrary.MV_OK) {
                throw new CameraException(camera.mName, res_start_grab, "start grab fail");
            }
            camera.mState = State.GRABING;

            System.out.println("camera " + i + " initialized!");
        }
    }

    @Override
    public byte[] takePhoto(String cameraName){
        Camera camera = mCameras.get(cameraName);
        if (camera == null)
            return null;
        synchronized (camera){
            if (camera.mState == State.GRABING) {
                camera.mState = State.GENERATING_IMAGE;
            } else {
                System.out.println ("can not take photo, camera state :"+camera.mState);
                return null;
            }
        }
        try {
            while (camera.mState != State.GRABING) {
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            ;
        }
        if (camera.mImageSize == 0) {
            return null;
        } else {
            return camera.mImageBuffer.getByteArray(0, camera.mImageSize);
        }
    }

    public void destroy() {
        for (Camera camera: mCameras.values()) {
            Pointer handle = camera.mPtrRef.getValue();
            if (camera.mState >= State.GRABING) {
                int res_stop_grab = HikLibrary.INSTANCE.MV_CC_StopGrabbing(handle);
                if (res_stop_grab != HikLibrary.MV_OK) {
                    throw new CameraException(camera.mName, res_stop_grab, "stop grab fail");
                }
            }

            if (camera.mState >= State.OPENED) {
                int res_close = HikLibrary.INSTANCE.MV_CC_CloseDevice(handle);
                if (res_close != HikLibrary.MV_OK) {
                    throw new CameraException(camera.mName, res_close, "close fail");
                }
            }

            if (camera.mState >= State.HANDLE_CREATED) {
                int res_destroy = HikLibrary.INSTANCE.MV_CC_DestroyHandle(handle);
                if (res_destroy != HikLibrary.MV_OK) {
                    throw new CameraException(camera.mName, res_destroy, "destroy fail");
                }
            }
            System.out.println("camera " + camera.mName  +" destroyed");
        }
    }
}
