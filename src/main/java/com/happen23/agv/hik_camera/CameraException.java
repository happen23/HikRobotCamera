package com.happen23.agv.hik_camera;

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
                ", mErrCode=" + String.format("0x%x", mErrCode) +
                ", mErrMsg='" + mErrMsg + '\'' +
                '}';
    }
}
