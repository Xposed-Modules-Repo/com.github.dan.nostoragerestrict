# Build Instructions for NoStorageRestrict

This project is configured for **Reproducible Builds**.

## Environment
- **JDK**: OpenJDK 21 (LTS)
- **Gradle**: 9.5.0 (via `./gradlew`)
- **Android Gradle Plugin**: 9.3.0

## Building the APK
To build a reproducible release APK, run:

```bash
./gradlew clean assembleRelease --no-build-cache --no-configuration-cache --no-daemon
```

## Signing
The build script is configured to look for a `keystore.properties` file in the project root. If not found, it will produce an **unsigned** APK, which is preferred for IzzyOnDroid verification builders.

If you wish to sign the APK, create `keystore.properties` with the following:
```properties
storeFile=path/to/your/keystore.jks
storePassword=your_store_password
keyAlias=your_key_alias
keyPassword=your_key_password
```
