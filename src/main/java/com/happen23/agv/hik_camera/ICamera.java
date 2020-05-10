package com.happen23.agv.hik_camera;

public interface ICamera {
    void init();
    byte[] takePhoto(String cameraName);
    void destroy();
}
