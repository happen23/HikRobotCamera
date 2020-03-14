# HikRobotCamera
[Hik Robotics Camera](https://www.hikrobotics.com/) Java SDK, including JNA and JNI implementaion
Currently, only taking photo is supported, other functionalities (params get & set) is under developing.

# compilation
## JNA
```
mvn install
```

## JNI
only Linux-armhf (eg. Raspberry Pi) is supported! for other ABI, please modify Makefile.

```
cd ${project_root}/src/main/c
make
sudo make install

cd ${project_root}
mvn install
```

# run

## JNA
```
java -jar ${maven_repo}\com\happen23\agv\agv-hik_camera\1.0-SNAPSHOT\agv-hik_camera-1.0-SNAPSHOT.jar;${maven_repo}\net\java\dev\jna\jna\5.5.0\jna-5.5.0.jar com.happen23.agv.hik_camera.CameraControl
```

## JNI
```
java -jar ${maven_repo}\com\happen23\agv\agv-hik_camera\1.0-SNAPSHOT\agv-hik_camera-1.0-SNAPSHOT.jar com.happen23.agv.hik_camera.HikCamera
```
