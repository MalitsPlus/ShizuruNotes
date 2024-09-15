建议使用比静流笔记拥有更加现代化界面和强大功能的 [另一个app](https://github.com/wthee/pcr-tool) ！

# 静流笔记

这是一个为“公主连接Re:Dive”开发的**非**官方开源免费安卓App. 支持日服、国服数据.  

## v3.1.0 版本（archive 版本）说明

静流笔记最终版本为 v3.1.0，之后将停止维护，我们建议所有用户转移使用 [pcr-tool](https://github.com/wthee/pcr-tool)。

对于仍然想使用静流笔记的用户，我们在 v3.1.0 版本中新增了手动导入数据库文件功能作为逃生舱，用户可以手动将最新的数据库导入 App。使用方法请参考以下文档。

v3.1.0 版本中进行了以下修改：

### 手动导入数据库文件功能

你可以点击 *设置 - 手动导入数据库文件* 选择保存在手机中的数据库文件。但请注意以下事项：

- 导入的文件必须是 unhash 后的数据库文件
- 所选择的文件在导入后将被复制到 App 私有文件夹下，原始文件可以被安全删除
- 由于数据库中没有版本号识别子，手动导入后在设置里看到的 *数据库版本* 将永远显示为 0

### 移除更新检测

App 启动时将不再自动检测 App 版本和数据库版本更新。

## 系统需求

* Android 8+  

## 构建

需要 Android Studio Arctic Fox 或更高版本.  

在构建之前，你可能需要在项目根目录下新建一个命名为 `local.properties` 的文件（如果它不存在）.

```sh
touch local.properties
```

### Debug 版本 App

可直接执行以下指令以构建 **Debug 版本** 的 app:

```sh
./gradlew :app:assembleDebug
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
./gradlew :app:assembleRelease
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

* 韩文字符串由 [applemintia](https://twitter.com/_applemintia) 提供.  
* 英文字符串由 [southrop](https://github.com/southrop) & [MightyZanark](https://github.com/MightyZanark). 提供.  

## 引用与参考  

静流笔记的开发受到了以下项目的重要启发，感谢这些伟大的先驱者:  

* [PrincessGuide](https://github.com/superk589/PrincessGuide)  
* [redive_master_db_diff](https://github.com/esterTion/redive_master_db_diff)  
* [DereHelper](https://github.com/Lazyeraser/DereHelper)  

静流笔记还使用了[这些](OPENSOURCE.md)开源工具.  

## 许可证

使用、改造、分发静流笔记时请遵循 Apache License 2.0 开源许可证.  

## 安装包

[下载页面](https://github.com/MalitsPlus/ShizuruNotes/releases)  
