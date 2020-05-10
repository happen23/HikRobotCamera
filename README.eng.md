# HikRobotCamera
[Hik Robotics Camera](https://www.hikrobotics.com/) Java SDK
Currently, only taking photo is supported, other functions (params get & set) is under developing.

# compilation
```
mvn clean install
```

# run

```
java -jar ${maven_repo}\com\happen23\agv\agv-hik_camera\1.0-SNAPSHOT\agv-hik_camera-1.0-SNAPSHOT.jar;${maven_repo}\net\java\dev\jna\jna\5.5.0\jna-5.5.0.jar com.happen23.agv.hik_camera.HikJnaCamera
```
