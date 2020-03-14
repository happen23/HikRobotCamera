package com.happen23.agv.hik_camera;

import com.sun.jna.Memory;
import com.sun.jna.ptr.PointerByReference;

public class Camera {
    public String mName;
    PointerByReference mPtrRef;
    int mInitState;
    Memory mFrameBuffer;
    Memory mImageBuffer;
}
