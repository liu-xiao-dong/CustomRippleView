# CustomRippleView
自定义波纹效果，速度、颜色、粗细可自定义，效果见效果图




### apk安装 ：
[https://github.com/liu-xiao-dong/JD-Test/raw/master/app/app-release.apk](https://github.com/liu-xiao-dong/JD-Test/raw/master/app/app-release.apk)  




### Specs
  [![API](https://img.shields.io/badge/API-11%2B-blue.svg?style=flat)](https://img.shields.io/badge/API-11%2B-blue.svg?style=flat) [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)


效果图：

![CustomRippleView](https://github.com/liu-xiao-dong/CustomRippleView/blob/master/screenshot/screenshot.gif?raw=true) 

使用方法：

<com.lxd.rippleview.RippleView
        android:id="@+id/ripple_view"
        android:layout_centerInParent="true"
        android:layout_width="150dp"   
        android:layout_height="150dp"
        app:circle_color="@color/white"   //圆环的颜色
        app:ripple_duration="5000"  //圆环从开始到结束的时间
        app:circle_num="10"   //最多同时显示几个圆环
        app:stroke_width="1dp"  //圆环宽度
        app:max_radius_multiple="2"   //最大圆环半径相对原始半径的倍数
        />


在父布局中添加： android:clipChildren="false"




# License

```
Copyright 2017 aritraroy

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.