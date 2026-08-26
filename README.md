# Extended Hitbox

Expand the hitboxes of living entities in Minecraft, with fine control over width and height. Client-side only, built for Fabric and NeoForge.

[![Minecraft](https://img.shields.io/badge/Minecraft-1.21.x-62B47A?style=flat-square&logo=minecraft&logoColor=white)](https://www.minecraft.net/)
[![CurseForge](https://img.shields.io/badge/CurseForge-Extended%20Hitbox-E04E14?style=flat-square&logo=curseforge&logoColor=white)](https://www.curseforge.com/minecraft/mc-mods/extended-hitbox)
[![Version](https://img.shields.io/badge/version-26.1.2-blue?style=flat-square)]()
[![Stars](https://img.shields.io/github/stars/mermotec/Extended-Hitbox?style=flat-square&color=FFD700)](https://github.com/mermotec/Extended-Hitbox/stargazers)

---

## Contents

- [Features](#features)
- [Usage](#usage)
- [Download](#download)
- [Installation](#installation)
- [Requesting a Version](#requesting-a-version)
- [Support](#support)
- [Tech Specs](#tech-specs)
- [License](#license)

## Features

- Expands hitboxes for all living entities, excluding the player
- Instant toggle with a single keybind
- Independent width and height control, from 0.1x to 10.0x
- Action bar feedback when settings change
- Full command suite for fine-tuned adjustments
- Runs entirely client-side with no measurable performance cost

Supported on Fabric and NeoForge. Forge support has been discontinued due to ongoing compatibility issues.

## Usage

### Keybind

| Key | Action |
|-----|--------|
| `H` | Toggle hitbox expansion (default 1.5x) |

### Commands

```
/hitbox set <value>      Set both dimensions (0.1 - 10.0)
/hitbox width <value>    Adjust width only
/hitbox height <value>   Adjust height only
/hitbox reset            Restore default values
/hitbox get              Check current settings
```

Examples:

```
/hitbox set 2.5
/hitbox width 3.0
/hitbox get
```

## Download

| Platform | Status | Minecraft | Link |
|----------|--------|-----------|------|
| Fabric | Active | 1.21.x | [CurseForge](https://www.curseforge.com/minecraft/mc-mods/extended-hitbox) |
| NeoForge | Active | 1.21.x | [CurseForge](https://www.curseforge.com/minecraft/mc-mods/extended-hitbox) |
| Forge | Discontinued | N/A | Not supported |

Current version: **26.1.2**

## Installation

### Fabric

1. Install [Fabric Loader](https://fabricmc.net/use/) for Minecraft 1.21.x
2. Download [Fabric API](https://modrinth.com/mod/fabric-api) (v0.119.4 or later)
3. Download Extended Hitbox from [CurseForge](https://www.curseforge.com/minecraft/mc-mods/extended-hitbox)
4. Place both `.jar` files in `.minecraft/mods`
5. Launch Minecraft

### NeoForge

1. Install [NeoForge](https://neoforged.net/) for Minecraft 1.21.x
2. Download Extended Hitbox from [CurseForge](https://www.curseforge.com/minecraft/mc-mods/extended-hitbox)
3. Place the `.jar` file in `.minecraft/mods`
4. Launch Minecraft

## Requesting a Version

If you need support for a different Minecraft version or loader, open an issue using the template below.

```
Mod Loader: Fabric / NeoForge
Minecraft Version: e.g. 1.20.1
Additional Notes: optional
```

Submit a request through [GitHub Issues](https://github.com/mermotec/Extended-Hitbox/issues/new) or [contact mermotec directly](https://github.com/mermotec). Typical response time is 24 to 48 hours.

## Support

Bug reports and feature requests can be filed through [GitHub Issues](https://github.com/mermotec/Extended-Hitbox/issues). Source is available [here](https://github.com/mermotec/Extended-Hitbox).

## Tech Specs

| Component | Details |
|-----------|---------|
| Minecraft | 1.21.x |
| Loaders | Fabric 0.18.4+, NeoForge |
| Java | 21+ |
| Environment | Client-side only |
| Version | 26.1.2 |

## License

All rights reserved, © 2026 mermotec. This software is protected by copyright law. Unauthorized reproduction, modification, or distribution is prohibited.

---

Developed by [mermotec](https://github.com/mermotec). If you find Extended Hitbox useful, consider starring the repository.
