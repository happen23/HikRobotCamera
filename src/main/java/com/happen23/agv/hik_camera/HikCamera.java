package com.happen23.agv.hik_camera;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class HikCamera implements ICamera {
   static {
       System.loadLibrary("hik_camera");
   }
   @Override
   public native void init();
   @Override
   public native byte[] takePhoto(int camIdx);
   @Override
   public native void destroy();

   public static void main(String[] args) {
       HikCamera camera = new HikCamera();
       camera.init();
       byte[] image = camera.takePhoto(0);
       if (image != null){
           try {
               File file = new File("b.jpg");
               FileOutputStream out = new FileOutputStream(file);
               out.write(image);
           }catch (IOException e){
               e.printStackTrace();
           }
       }
       camera.destroy();
   }
}
