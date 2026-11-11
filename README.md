# Extended Hitbox Mod - Forge 1.20.1

## Version Information
- **Minecraft Version:** 1.20.1
- **Forge Version:** 47.4.0
- **Mod Version:** 1.2.0

## Description
Client-side mod that expands entity hitboxes with configurable multipliers for easier targeting and combat precision.

## Features
- Toggle expanded hitboxes with keybind (Default: H key)
- Configurable hitbox multiplier via commands
- Client-side only - works on any server
- No physics/collision when hitboxes are expanded

## Commands
- `/hitbox set <multiplier>` - Set hitbox multiplier (0.1 to 5.0)
- `/hitbox get` - Get current hitbox multiplier and status

## Installation
1. Install Minecraft Forge 1.20.1 - 47.4.0 or later
2. Place the mod JAR file in your mods folder
3. Launch Minecraft

## Building from Source
1. Extract this archive
2. Open terminal in the extracted folder
3. Run `./gradlew build` (Linux/Mac) or `gradlew.bat build` (Windows)
4. Find the built JAR in `build/libs/`

## Controls
- **H Key** - Toggle expanded hitboxes on/off

## Author
MermoTEC

## License
MIT License
