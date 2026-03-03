# Extended Hitbox

[![Minecraft Version](https://img.shields.io/badge/Minecraft-1.21.4-green.svg)](https://www.minecraft.net/)
[![Fabric API](https://img.shields.io/badge/Fabric%20API-0.119.4-orange.svg)](https://fabricmc.net/)
[![CurseForge](https://img.shields.io/badge/CurseForge-Download-blue.svg)](https://www.curseforge.com/minecraft/mc-mods/extended-hitbox)

A client-side Fabric mod for Minecraft 1.21.4 that allows you to expand entity hitboxes, making it easier to target and interact with entities in-game.

## ✨ Features

- **Toggle hitbox expansion** with a simple keybind (default: `H`)
- **Customizable multipliers** for width and height independently
- **Client commands** for fine-tuned control
- **Visual feedback** with action bar messages
- **Client-side only** - no server installation required
- **Lightweight** - minimal performance impact

## 📥 Installation

1. Install [Fabric Loader](https://fabricmc.net/use/) for Minecraft 1.21.4
2. Download [Fabric API](https://modrinth.com/mod/fabric-api) version 0.119.4 or newer
3. Download Extended Hitbox from [CurseForge](https://www.curseforge.com/minecraft/mc-mods/extended-hitbox)
4. Place both `.jar` files in your `.minecraft/mods` folder
5. Launch Minecraft with the Fabric profile

## 🎮 Usage

### Keybind

- **H** (default) - Toggle hitbox expansion on/off
  - When enabled: Entity hitboxes expand by 1.5x (configurable)
  - When disabled: All hitboxes return to normal

### Commands

All commands are client-side and start with `/hitbox`:

```
/hitbox set <multiplier>       - Set both width and height (0.1-10.0)
/hitbox width <multiplier>     - Set width multiplier only
/hitbox height <multiplier>    - Set height multiplier only
/hitbox reset                  - Reset to 1.0x and disable expansion
/hitbox get                    - Display current settings
```

**Examples:**
```
/hitbox set 2.0        - Make all hitboxes 2x larger
/hitbox width 3.0      - Make hitboxes 3x wider
/hitbox height 1.2     - Make hitboxes 1.2x taller
/hitbox get            - Check current multipliers
```

## 🛠️ Building from Source

### Prerequisites
- Java 21 or higher
- Git

### Build Steps

```bash
git clone https://github.com/mermotec/Extended-Hitbox.git
cd Extended-Hitbox

# On Windows
gradlew.bat build

# On Linux/Mac
./gradlew build
```

The compiled `.jar` will be in `build/libs/`

## 📝 Technical Details

- **Minecraft Version:** 1.21.4
- **Mod Loader:** Fabric Loader 0.18.4+
- **Fabric API:** 0.119.4+
- **Java Version:** 21
- **Environment:** Client-side only

## 📜 License

All Rights Reserved © 2026 mermotec

## 🔗 Links

- [CurseForge Page](https://www.curseforge.com/minecraft/mc-mods/extended-hitbox)
- [Source Code](https://github.com/mermotec/Extended-Hitbox)
- [Report Issues](https://github.com/mermotec/Extended-Hitbox/issues)

## ❤️ Credits

Developed by [mermotec](https://github.com/mermotec)