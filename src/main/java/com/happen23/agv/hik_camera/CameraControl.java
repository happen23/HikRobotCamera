package com.happen23.agv.hik_camera;

import com.happen23.agv.hik_camera.lib_hik.*;
import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Vector;

public class CameraControl implements ICamera {
    public static final int JPG_QUALITY = 60;

    MV_CC_DEVICE_INFO_LIST mDevList = new MV_CC_DEVICE_INFO_LIST();
    Vector<Camera> mCameras = new Vector<>(HikLibrary.MV_MAX_DEVICE_NUM);

    interface InitState {
        int ENUMERATED = 10;
        int HANDLE_CREATED = 20;
        int OPENED = 30;
        int FRAME_BUF_ALLOCATED = 40;
        int IMAGE_BUF_ALLOCATED = 50;
        int GRAB_STARTED = 60;
    }

    public CameraControl() {
        ;
    }

    public static void main(String[] args) {
        CameraControl cameraControl = null;
        try {
            cameraControl = new CameraControl();
            Thread.sleep(1000);
            byte[] image = cameraControl.takePhoto(0);
            if (image != null){
                File file = new File("a.jpg");
                FileOutputStream out = new FileOutputStream(file);
                out.write(image);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }catch(InterruptedException e){
            e.printStackTrace();
        }finally{
            if (cameraControl != null) {
                cameraControl.destroy();
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
            camera.mName = new String(cameraInfo.SpecialInfo.stGigEInfo.chUserDefinedName);
            camera.mInitState = InitState.ENUMERATED;
            mCameras.add(camera);

            PointerByReference handle = new PointerByReference();
            int res_creat = HikLibrary.INSTANCE.MV_CC_CreateHandle(handle, cameraInfo);
            if (res_creat != HikLibrary.MV_OK) {
                throw new CameraException(camera.mName, res_creat, "create fail");
            }
            camera.mPtrRef = handle;
            camera.mInitState = InitState.HANDLE_CREATED;

            int res_open = HikLibrary.INSTANCE.MV_CC_OpenDevice(handle.getValue(), HikLibrary.MV_ACCESS_Exclusive, (short)0);
            if (res_open != HikLibrary.MV_OK) {
                throw new CameraException(camera.mName, res_open, "open fail");
            }
            camera.mInitState = InitState.OPENED;

            MVCC_INTVALUE intParam = new MVCC_INTVALUE();
            int res_get_param = HikLibrary.INSTANCE.MV_CC_GetIntValue(handle.getValue(),
                    "PayloadSize", intParam);
            if (res_get_param != HikLibrary.MV_OK) {
                throw new CameraException(camera.mName, res_get_param, "query param fail");
            }
            int frameSize = intParam.nCurValue;
            System.out.println("camera index "+ i + ", name " + camera.mName  +", frame size = " + frameSize);
            camera.mFrameBuffer = new Memory(frameSize);
            camera.mInitState = InitState.FRAME_BUF_ALLOCATED;
            camera.mImageBuffer = new Memory(frameSize);
            camera.mInitState = InitState.IMAGE_BUF_ALLOCATED;

            int res_start_grab = HikLibrary.INSTANCE.MV_CC_StartGrabbing(handle.getValue());
            if (res_start_grab != HikLibrary.MV_OK) {
                throw new CameraException(camera.mName, res_start_grab, "start grab fail");
            }
            camera.mInitState = InitState.GRAB_STARTED;

            System.out.println("camera " + i + " initialized!");
        }
    }

    public byte[] takePhoto(int camIdx){
        MV_FRAME_OUT_INFO_EX frameInfo = new MV_FRAME_OUT_INFO_EX();
        if (mCameras.isEmpty()){
            System.out.println("no camera available!");
            return null;
        }
        Camera camera = mCameras.get(camIdx);
        Pointer handle = camera.mPtrRef.getValue();
        Memory frameBuffer = camera.mFrameBuffer;
        Memory imageBuffer = camera.mImageBuffer;
        ByteBuffer frameByteBuffer = frameBuffer.getByteBuffer(0, frameBuffer.size());
        ByteBuffer imageByteBuffer = imageBuffer.getByteBuffer(0, imageBuffer.size());
        //int res_capture = HikLibrary.INSTANCE.MV_CC_GetOneFrameTimeout(handle,
        //        frameByteBuffer, frameByteBuffer.capacity(), frameInfo, 1000);
        System.out.println("handle: "+handle.toString()+", frame size = " + frameBuffer.size());
        int res_capture = HikLibrary.INSTANCE.MV_CC_GetOneFrameTimeout(handle,
                frameBuffer, (int)frameBuffer.size(), frameInfo, 1000);
        if (res_capture != HikLibrary.MV_OK) {
            System.out.println("camera " + camIdx + " capture image fail, err_code = " + res_capture);
            return null;
        }
        MV_SAVE_IMAGE_PARAM_EX imageParam = new MV_SAVE_IMAGE_PARAM_EX();
        // 源数据
        imageParam.pData = frameBuffer;
        imageParam.nDataLen = frameInfo.nFrameLen;
        imageParam.enPixelType = frameInfo.enPixelType;
        imageParam.nWidth = frameInfo.nWidth;
        imageParam.nHeight = frameInfo.nHeight;
        imageParam.nJpgQuality = JPG_QUALITY;
        // 目标数据
        imageParam.enImageType = HikLibrary.MV_SAVE_IAMGE_TYPE.MV_Image_Jpeg;
        imageParam.pImageBuffer = imageBuffer;
        imageParam.nBufferSize = imageByteBuffer.capacity();
        // 编码压缩图片
        int res_encode = HikLibrary.INSTANCE.MV_CC_SaveImageEx2(handle, imageParam);
        if (res_encode != HikLibrary.MV_OK) {
            System.out.println("camera " + camIdx + " capture image fail, err_code = " + res_encode);
            return null;
        }

        return imageBuffer.getByteArray(0, imageParam.nImageLen);
    }

    public void destroy() {
        for (int i = 0; i < mCameras.size(); i++) {
            Camera camera = mCameras.get(i);
            Pointer handle = camera.mPtrRef.getValue();
            if (camera.mInitState >= InitState.GRAB_STARTED) {
                int res_stop_grab = HikLibrary.INSTANCE.MV_CC_StopGrabbing(handle);
                if (res_stop_grab != HikLibrary.MV_OK) {
                    throw new CameraException(camera.mName, res_stop_grab, "stop grab fail");
                }
            }

            if (camera.mInitState >= InitState.OPENED) {
                int res_close = HikLibrary.INSTANCE.MV_CC_CloseDevice(handle);
                if (res_close != HikLibrary.MV_OK) {
                    throw new CameraException(camera.mName, res_close, "close fail");
                }
            }

            if (camera.mInitState >= InitState.HANDLE_CREATED) {
                int res_destroy = HikLibrary.INSTANCE.MV_CC_DestroyHandle(handle);
                if (res_destroy != HikLibrary.MV_OK) {
                    throw new CameraException(camera.mName, res_destroy, "destroy fail");
                }
            }
            System.out.println("camera index "+ i + ", name " + camera.mName  +"destroyed");
        }
    }
}
