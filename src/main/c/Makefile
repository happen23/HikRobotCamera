all:
	mkdir -p build
	javac -h include ../java/com/lanplus/lanova/camera/HikCamera.java
	gcc -g -shared -o build/libhik_camera.so src/libhik_camera.c -I$(JAVA_HOME)/include/linux -I$(JAVA_HOME)/include -I./include -fPIC -Wl,-rpath=$(MVCAM_COMMON_RUNENV)/armhf -L$(MVCAM_COMMON_RUNENV)/armhf -lMvCameraControl

clean:
	rm -f include/com_lanplus_lanova_*.h
	rm -rf build/

install:
	cp -f build/libhik_camera.so /lib/arm-linux-gnueabihf/
