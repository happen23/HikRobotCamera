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

如果觉得有用，请考虑捐赠
![支付宝二维码][qrcode]

[qrcode]:data:image/png;base64,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
wgG3H2g7u+yFLfAJfHkKdm112StrlW91Q4Dv6PTSixP2xlIEmnbwWZlgF0An0h9u6Vrx2bXC
/7VuNKPZPV/r1J79zjb75IYujwqXBN/4zJw9Pm0rx+7z2HvHGe26ttfd4sdZPvXg2Csv2+fe
/ui+E96W7dzq2qsv9t6xBW8vb2SHbY8o6a6urvGdsNW1507szTN7Z+N/pL+VnQ0+e0DshV/f
u6xFS9qaPRnX+a/RX+xOZWRj3Om0+au21W5X4uyJE2t2MT465NduPxT9Jbud8o19kf0FHQ/r
fG7zAv3t1oE9SLgMfA8MT73hytYDQPYdgbRzaVfwdT0JGyysCxt7V8tAFZ8NwZpNhD1nbl3Y
za78JWoMvvih3/zH5rG0nGwf9dNHl+Blz5O+59aDL7u0pZ5BbXkhk/S9BoFP4JszpkR58NrL
lx7uswWQtrqywWf33fLkMbWxTwZ0xmNfE4n82BsgXWFhT89Fxva0ipVvy+wN9/ZPz794r93O
jhd8XPFZBWGvkUWHpxVc/QU+e8Dw3n2T9iy6feOkcy6s6qwn+OyirZ2E/t9j4/HrfcuCf+jQ
tMDXdS009BpftCmwV7WstHnNNxeKoGUJ9Cs3FQTf5Tsm7FHeZc3OyZH/F319b+e/Rn+Jn6d/
xaX7Om3sK2mJ0/nzi48N27YrY75t1/YPFp/ZttXeafnk/PbH1kz7gm/f6t61dzJ+AbnzmWc7
qo/AZ1+di15NyWh2aqhhxRd/OSItcktsgU/gS1YgfudsWfa8/NJ9tmc8d9uxx0ezPqxU4Brf
lkPTUV/20m7arOR5Za3z2Bt3LRR99vqUvaCe5jx+MSvjI31mE13B66z4IrdnPrxQzBq+O59z
zAM+o2dGizZrttPMNrPLkSW3uuYhDRx2B+YP7ztiT26blPZRHJM3o8XPA5i3eKtrk2D+3Vt0
gz5+QnNZ/FaP217YKkHr1z6kmB22fX5NW90mbnXtC73xxNsyix9Ay/k9vgLgs0fhoh4zHsUo
Br52TtlXhhKJEK9Pu9RoX0lJo0b87Esa+Nr7+rEr9i/74GhX8NnKpRv8RHs7i5QEnx3e/rhi
3Mv/3cS+x9f75/ji9wLblVl2+a9rvaPHWRq61bUVa5XXJ34wdsvu43YLrAfP8V38eOsKmrXf
SP8KUGHwHZhYesnMbrwuy3t73z7e1p2V71O9GeAzueyV+Ggs9vJA+6XC/gKfnYHs5u/XHh+3
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
QmCC

