# HikRobotCamera
海康机器人工业面阵相机的Java SDK，目前仅支持拍照，不支持参数查询、设置等其他功能。

# 编译
```
mvn clean install
```

# 运行

```
java -jar ${maven_repo}\com\happen23\agv\agv-hik_camera\1.0-SNAPSHOT\agv-hik_camera-1.0-SNAPSHOT.jar;${maven_repo}\net\java\dev\jna\jna\5.5.0\jna-5.5.0.jar com.happen23.agv.hik_camera.HikJnaCamera
```
