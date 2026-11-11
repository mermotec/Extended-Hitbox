# Gradle Wrapper Files

Extract these files to your project root directory.

## Files included:
- gradle/wrapper/gradle-wrapper.properties
- gradlew (for Linux/Mac)
- gradlew.bat (for Windows)

## Important:
The gradle-wrapper.jar file will be downloaded automatically the first time you run gradlew.

## To build your mod:

Windows:
    gradlew.bat build

Linux/Mac:
    chmod +x gradlew
    ./gradlew build

The first run will download Gradle 8.8 and all dependencies.
This is normal and can take 5-30 minutes.

Subsequent builds will be much faster!
