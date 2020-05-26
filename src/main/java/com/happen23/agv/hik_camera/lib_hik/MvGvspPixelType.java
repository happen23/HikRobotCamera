package com.happen23.agv.hik_camera.lib_hik;

import com.sun.jna.FromNativeContext;
import com.sun.jna.NativeMapped;

public class MvGvspPixelType implements NativeMapped {
    private static String os_arch;
    private static String os_name;
    private Long value;
    static {
        os_arch = System.getProperty("os.arch");
        os_name = System.getProperty("os.name");
        System.out.println(os_name + ", "+os_arch);
    }

    public MvGvspPixelType() {}

    private MvGvspPixelType(Long value){
        this.value = value;
    }

    @Override
    public MvGvspPixelType fromNative(Object o, FromNativeContext fromNativeContext) {
        if (os_name.equals("Linux")) {
            return new MvGvspPixelType(Long.valueOf((long) o));
        } else {
            return new MvGvspPixelType(Integer.toUnsignedLong((int) o));
        }
    }

    @Override
    public Object toNative() {
        if (os_name.equals("Linux")) {
            return this.value;
        } else {
            return this.value.intValue();
        }
    }

    @Override
    public Class<?> nativeType() {
        if (os_name.equals("Linux")) {
            return Long.class;
        } else {
            return Integer.class;
        }
    }
}
