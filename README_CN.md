# 静流笔记
这是一个为“公主连接Re:Dive”开发的**非**官方开源免费安卓App. 支持日服、国服数据.  

## 系统需求
* Android 8+  

## 构建
需要 Android Studio Arctic Fox 或更高版本.  

在构建之前，你可能需要在项目根目录下新建一个命名为 `local.properties` 的文件（如果它不存在）.

```sh
$ touch local.properties
```

### Debug 版本 App
可直接执行以下指令以构建 **Debug 版本** 的 app:

```sh
$ ./gradlew :app:assembleDebug
```

### Release 版本 App
如果你想构建 Release 版本的 App，必须先向 `local.properties` 中添加以下代码：

```sh
signing.storeFile=${PATH_TO_YOUR_KEY_STORE_FILE}
signing.storePassword=${YOUR_KEY_STORE_PASSWORD}
signing.keyAlias=${YOUR_KEY_ALIAS}
signing.keyPassword=${YOUR_KEY_PASSWORD}
```

之后便可执行以下指令构建 **Release 版本** 的 App：

```sh
$ ./gradlew :app:assembleRelease
```

## 功能
* 角色数据 
  * 角色派生数据计算 
  * Rank 对比  
* Boss 数据
  * 会战 Boss 数据 
  * 地下城 Boss 数据 
  * 活动 Boss 数据 
  * 世界 Boss 数据
* 装备信息 
* 掉落检索 
* 活动日历 
* 活动通知提醒 

## 本地化  
完整支持中文、日文. 部分支持韩文、英文.  
中文和日文字符串会随着 App 每个版本即时更新，韩文和英文的更新频率可能会较低.

- 韩文字符串由 [applemintia](https://twitter.com/_applemintia) 提供.  
- 英文字符串由 [southrop](https://github.com/southrop) & [MightyZanark](https://github.com/MightyZanark). 提供.  

## 引用与参考  
静流笔记的开发受到了以下项目的重要启发，感谢这些伟大的先驱者:  
* [PrincessGuide](https://github.com/superk589/PrincessGuide)  
* [redive_master_db_diff](https://github.com/esterTion/redive_master_db_diff)  
* [DereHelper](https://github.com/Lazyeraser/DereHelper)  

静流笔记还使用了[这些](OPENSOURCE.md)开源工具.  

## 许可证
使用、改造、分发静流笔记时请遵循 Apache License 2.0 开源许可证.  

## 咖啡
***人可以一天不吃饭，但不能一天不喝咖啡.***  

如果你喜欢本 App，可以请作者喝一杯咖啡，这将成为我持续更新的动力！ 

![coffee](coffee.jpg)

## 安装包
[下载页面](https://github.com/MalitsPlus/ShizuruNotes/releases)  
