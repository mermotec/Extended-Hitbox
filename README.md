<div align="center">

# ✨ Extended Hitbox ✨

### *Precision Made Easy*

[![Minecraft](https://img.shields.io/badge/Minecraft-1.21.4-brightgreen?style=for-the-badge&logo=minecraft)](https://www.minecraft.net/)
[![CurseForge](https://img.shields.io/badge/Download-CurseForge-orange?style=for-the-badge&logo=curseforge)](https://www.curseforge.com/minecraft/mc-mods/extended-hitbox)
[![License](https://img.shields.io/badge/License-All%20Rights%20Reserved-red?style=for-the-badge)](LICENSE)

*Expand entity hitboxes with elegance. Target with confidence.*

[📥 Download](#-download) • [🎮 Features](#-features) • [📖 Usage](#-usage) • [🔧 Installation](#-installation)

---

</div>

## 🌟 Overview

Extended Hitbox is a powerful client-side modification that revolutionizes entity interaction in Minecraft. Seamlessly expand hitboxes for easier targeting, enhanced precision, and superior control—all without affecting server-side mechanics.

Built for **Fabric**, **NeoForge**, and ~~Forge~~ (discontinued).

> **⚠️ Note:** Forge support has been discontinued due to persistent compatibility issues. Please use Fabric or NeoForge for the best experience.

---

## ✨ Features

<table>
<tr>
<td width="50%">

### 🎯 **Smart Targeting**
Dynamically expand entity hitboxes for effortless precision in combat and interaction.

</td>
<td width="50%">

### ⚡ **Lightning Fast**
Optimized performance with zero lag. Client-side only—no server installation needed.

</td>
</tr>
<tr>
<td width="50%">

### 🎨 **Fully Customizable**
Independent width and height control with real-time adjustments via intuitive commands.

</td>
<td width="50%">

### 🔔 **Visual Feedback**
Instant action bar notifications keep you informed of every change.

</td>
</tr>
</table>

---

## 🎮 Usage

### **Keybinds**

| Key | Action | Description |
|-----|--------|-------------|
| **H** | Toggle Expansion | Enable or disable hitbox expansion instantly |

> *Default multiplier: 1.5x — Fully adjustable via commands*

### **Commands**

All commands are client-side and begin with `/hitbox`:

```bash
/hitbox set <value>      # Adjust both width and height (0.1 - 10.0)
/hitbox width <value>    # Modify width multiplier independently
/hitbox height <value>   # Modify height multiplier independently
/hitbox reset            # Restore default settings (1.0x)
/hitbox get              # Display current configuration
```

#### **Examples**

```bash
/hitbox set 2.5          # Expand hitboxes to 2.5x their original size
/hitbox width 3.0        # Make entities 3x wider
/hitbox height 1.2       # Increase height by 20%
/hitbox get              # Check active multipliers
```

---

## 📥 Download

### **Supported Platforms**

| Platform | Status | Minecraft Version | Download |
|----------|--------|-------------------|----------|
| **Fabric** | ✅ Active | 1.21.4 | [CurseForge](https://www.curseforge.com/minecraft/mc-mods/extended-hitbox) |
| **NeoForge** | ✅ Active | 1.21.4 | [CurseForge](https://www.curseforge.com/minecraft/mc-mods/extended-hitbox) |
| **Forge** | ❌ Discontinued | — | *No longer supported* |

> **Why no Forge?** Forge compatibility introduced numerous breaking errors that compromised mod stability. Development has shifted to Fabric and NeoForge for superior reliability.

---

## 🔧 Installation

### **For Fabric Users**

1. Install **[Fabric Loader](https://fabricmc.net/use/)** for Minecraft 1.21.4
2. Download **[Fabric API](https://modrinth.com/mod/fabric-api)** (v0.119.4+)
3. Download **Extended Hitbox** from [CurseForge](https://www.curseforge.com/minecraft/mc-mods/extended-hitbox)
4. Place both `.jar` files in your `.minecraft/mods` folder
5. Launch Minecraft and enjoy!

### **For NeoForge Users**

1. Install **[NeoForge](https://neoforged.net/)** for Minecraft 1.21.4
2. Download **Extended Hitbox** from [CurseForge](https://www.curseforge.com/minecraft/mc-mods/extended-hitbox)
3. Place the `.jar` file in your `.minecraft/mods` folder
4. Launch Minecraft and enjoy!

---

## 🛠️ Building from Source

### **Prerequisites**
- **Java 21** or higher
- **Git**

### **Build Instructions**

```bash
# Clone the repository
git clone https://github.com/mermotec/Extended-Hitbox.git
cd Extended-Hitbox

# Build the project
./gradlew build          # Linux/Mac
gradlew.bat build        # Windows
```

**Output:** Compiled `.jar` files will be located in `build/libs/`

---

## 📋 Technical Specifications

| Component | Details |
|-----------|----------|
| **Minecraft Version** | 1.21.4 |
| **Mod Loaders** | Fabric (0.18.4+), NeoForge |
| **Dependencies** | Fabric API 0.119.4+ (Fabric only) |
| **Java Version** | 21 |
| **Environment** | Client-side only |

---

## 📜 License

**All Rights Reserved © 2026 mermotec**

This software is protected by copyright law. Unauthorized reproduction, modification, or distribution is prohibited without explicit written permission.

---

## 🔗 Links

<div align="center">

[![CurseForge](https://img.shields.io/badge/CurseForge-Project%20Page-orange?style=for-the-badge&logo=curseforge)](https://www.curseforge.com/minecraft/mc-mods/extended-hitbox)
[![GitHub](https://img.shields.io/badge/GitHub-Source%20Code-black?style=for-the-badge&logo=github)](https://github.com/mermotec/Extended-Hitbox)
[![Issues](https://img.shields.io/badge/GitHub-Report%20Issues-red?style=for-the-badge&logo=github)](https://github.com/mermotec/Extended-Hitbox/issues)

</div>

---

<div align="center">

### 💎 Crafted with Precision

*Developed by* ***[mermotec](https://github.com/mermotec)***

⭐ *If you enjoy Extended Hitbox, consider starring the repository!* ⭐

</div>