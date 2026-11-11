# Extended Hitbox - Fabric 1.20.1

Client-side mod that expands entity hitboxes with configurable multipliers.

## IMPORTANT: First Time Setup

### Method 1: Using IntelliJ IDEA (EASIEST)
1. Open this folder in IntelliJ IDEA
2. IDEA will automatically download Gradle and all dependencies
3. Wait for indexing to complete (5-30 minutes first time)
4. Run build from the Gradle panel or use the build task

### Method 2: Command Line
First, generate the Gradle wrapper files:

**If you have Gradle installed:**
```bash
gradle wrapper --gradle-version 8.8
```

**Then build the mod:**

Windows:
```cmd
gradlew.bat build
```

Linux/Mac:
```bash
./gradlew build
```

The compiled .jar file will be in `build/libs/`

## Usage

### In-Game Controls:
- Press **H** to toggle hitbox expansion on/off
- Use `/hitbox set <multiplier>` to set the expansion multiplier (0.1 to 5.0)
- Use `/hitbox get` to check current settings and status

### Example:
```
/hitbox set 2.0    # Makes hitboxes 2x larger
/hitbox set 0.5    # Makes hitboxes half size
/hitbox get        # Shows current multiplier and status
```

## Requirements

### For Playing:
- Java 17 or higher
- Minecraft 1.20.1
- Fabric Loader 0.15.11 or higher
- Fabric API 0.92.2+1.20.1 or higher

### For Development:
- Java Development Kit (JDK) 17 or higher
- Gradle 8.1+ (or use IntelliJ IDEA)
- Fabric Loom 1.7

## Troubleshooting

### "Plugin fabric-loom was not found"
**Solution:** You need to generate the Gradle wrapper first. See setup instructions above.

### "Could not resolve net.fabricmc:fabric-loom"
**Solution:** Check your internet connection. The build system needs to download dependencies.

### Build is very slow the first time
**This is normal!** The first build downloads all Minecraft and Fabric dependencies (can be 500MB+). 
This can take 5-30 minutes depending on your internet speed. Subsequent builds are much faster.

### Java version errors
**Solution:** Make sure you have Java 17 or higher installed. Check with: `java -version`

## Features

- **Client-side only** - No server installation required
- **Configurable multipliers** - Adjust hitbox size from 0.1x to 5.0x
- **Toggle on/off** - Press H to instantly enable/disable
- **No collision** - Entities won't push you when hitboxes are expanded
- **Command system** - Precise control via chat commands
- **Automatic restoration** - Hitboxes return to normal when disabled

## Technical Details

This mod uses Fabric's event system to dynamically modify entity bounding boxes
on the client side. It stores original hitbox dimensions and restores them when
disabled, ensuring no permanent changes to entities.

Author: MermoTEC
License: MIT
Version: 1.1.0
