[中文](README_CN.md)

Try [another app](https://github.com/wthee/pcr-tool) with a more modern design and powerful features!

# ShizuruNotes
An **unofficial** Android tool application for "Princess Connect Re:Dive".

## Requirement
* Android 8+

## Build
Requires Android Studio Arctic Fox or later.  

Before building, you need to create a `local.properties` in project's root directory if it does not exist.

```sh
$ touch local.properties
```

### Debug Version App
You can build app with **debug variant** by executing the following command directly. 

```sh
$ ./gradlew :app:assembleDebug
```

### Release Version App
If you want to build a release version app, you must add the following lines into `local.properties` first:

imre```sh
signing.storeFile=${PATH_TO_YOUR_KEY_STORE_FILE}
signing.storePassword=${YOUR_KEY_STORE_PASSWORD}
signing.keyAlias=${YOUR_KEY_ALIAS}
signing.keyPassword=${YOUR_KEY_PASSWORD}
```

After that you can build app with **release variant**:

```sh
$ ./gradlew :app:assembleRelease
```

## Features
* Chara
* Chara Derivative Status
* Clan Battle Boss
* Dungeon
* Equipment
* Drop Search
* Event Boss
* Event Calendar
* Event Notification
* Rank Comparison

## Localization
Japanese and Chinese are fully supported and maintained with each update.  
Korean and English are also somehow supported, yet may be updated with less frequency.  
* Korean strings are provided by [applemintia](https://twitter.com/_applemintia).  
* English strings are provided by [southrop](https://github.com/southrop) & [MightyZanark](https://github.com/MightyZanark).

## References
ShizuruNotes is highly inspired by the following illustrious repos, thanks them:
* [PrincessGuide](https://github.com/superk589/PrincessGuide)
* [redive_master_db_diff](https://github.com/esterTion/redive_master_db_diff)
* [DereHelper](https://github.com/Lazyeraser/DereHelper)

ShizuruNotes also uses [these](OPENSOURCE.md) open source software.

## Related projects
* [KasumiNotes](https://github.com/HerDataSam/KasumiNotes) The KR server version of ShizuruNotes, developed by [HerDataSam](https://github.com/HerDataSam).

## License
ShizuruNotes is licensed under the Apache License 2.0. 
