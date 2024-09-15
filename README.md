[中文](README_CN.md)

Try [another app](https://github.com/wthee/pcr-tool) with a more modern design and powerful features!

# ShizuruNotes

An **unofficial** Android information application for "Princess Connect Re:Dive".

## About v3.1.0 (Archive Version)

ShizuruNotes is archived as of v3.1.0, we suggest that all users migrate to [pcr-tool](https://github.com/wthee/pcr-tool).

However for users still willing to stick on ShizuruNotes, we also provide manually database file importing as an escape hatch. Please refer to the following documentations for details.

Changes in v3.1.0:

### Manually database file importing

Go to *Settings - Manually select DB file* and select a file in your local storage. Note the followings:

- Selected file must be an unhashed DB file
- Selected file can be safely deleted after importing because a duplication will be saved in the App's private directory
- Since we can't retrieve the version information from DB directly, *Setting - Database Version* will always display 0

### Removal of update checking

Automatic update check is removed in v3.1.0 as the project is going to archive.

## Requirement

* Android 8+

## Build

Requires Android Studio Hedgehog or later.  

Create a `local.properties` in project's root directory if it does not exist before building.

```bash
touch local.properties
```

### Debug Version App

Leave `local.properties` empty and simply use the following command to build a **debug variant** app.

```bash
./gradlew :app:assembleDebug
```

### Release Version App

Signature related informations must be added to `local.properties` first before building a release version app:

```bash
signing.storeFile=${PATH_TO_YOUR_KEY_STORE_FILE}
signing.storePassword=${YOUR_KEY_STORE_PASSWORD}
signing.keyAlias=${YOUR_KEY_ALIAS}
signing.keyPassword=${YOUR_KEY_PASSWORD}
```

After that you can build app with **release variant** by using:

```bash
./gradlew :app:assembleRelease
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
