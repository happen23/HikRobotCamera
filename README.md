# HikRobotCamera
海康机器人工业面阵相机的Java SDK，包含JNA版和JNI版
目前仅支持拍照，不支持参数查询、设置等其他功能。

# 编译
## JNA版
```
mvn install
```

## JNI版
仅在Linux-armhf（例如树莓派）下测试通过，其他平台请修改Makefile
```
cd ${project_root}/src/main/c
make
sudo make install

cd ${project_root}
mvn install
```

# 运行

## JNA版
```
java -jar ${maven_repo}\com\happen23\agv\agv-hik_camera\1.0-SNAPSHOT\agv-hik_camera-1.0-SNAPSHOT.jar;${maven_repo}\net\java\dev\jna\jna\5.5.0\jna-5.5.0.jar com.happen23.agv.hik_camera.CameraControl
```

## JNI版
```
java -jar ${maven_repo}\com\happen23\agv\agv-hik_camera\1.0-SNAPSHOT\agv-hik_camera-1.0-SNAPSHOT.jar com.happen23.agv.hik_camera.HikCamera
```
