package com.happen23.agv.hik_camera;

public interface ICamera {
    void init();
    byte[] takePhoto(int camIdx);
    void destroy();
}
