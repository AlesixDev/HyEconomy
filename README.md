# HyEconomy 💰

**HyEconomy** is a robust and secure economy implementation plugin for Hytale servers.

It is built directly upon the **EcoAPI** standard to provide a fully functional financial system. While EcoAPI acts as the bridge for developers, HyEconomy is the "bank" that handles data storage, transaction logic, and player balances.

---

## ✨ Features

* **Robust Storage:** Securely manages player balances with data integrity in mind.
* **EcoAPI Native:** Seamlessly powers any other plugin (Shops, Jobs, Claims) that depends on the EcoAPI standard.
* **High Performance:** Optimized to handle frequent transactions without impacting server performance.
* **Fully Configurable:** Customize currency names, symbols, and starting balances to fit your server's theme.

---

## ⚠️ Prerequisites

To run HyEconomy, you must have the following:

1.  **Hytale Server** (Access to the root directory).
2.  **EcoAPI:** This plugin relies on the abstract methods defined in EcoAPI. It will **not** load without it.

---

## 📥 Installation Guide

Follow these simple steps to install the economy system:

### 1. Download
* Download the latest release of **HyEconomy**.
* Download the latest release of **[EcoAPI](https://github.com/Zen-kun04/EcoAPI)**.

### 2. Install
Navigate to your server's root directory and locate the `mods` folder.

> 📂 **Path:** `./YourServer/mods/`

Drag and drop **BOTH** `.jar` files (HyEconomy and EcoAPI) into this folder.

### 3. Restart
Restart your Hytale server to load the new mods.

```bash
# Server Console
> stop
```

### 4. Verify
Check the server console during startup. You should see `EcoAPI` loading first, followed by `HyEconomy` successfully hooking into the API.

---

## ⚙️ Configuration
After the first restart, a configuration file will be generated in `./Server/HyEconomy/`.

### Common settings:
- `currency`: Set your currency (e.g., `$`, `€`, `Coins`).
- `starting`: The amount of money new players receive upon joining.

---

## 🎮 Commands

### User Commands
- `/balance` or `/bal` or `/money` - Check your current wallet balance.
- `/pay <player> <amount>` - Send money to another player.

### Admin Commands
- `/eco add <player> <amount>` - Add funds to a player's account.
- `/eco remove <player> <amount>` - Remove funds from a player's account.
- `/eco set <player> <amount>` - Force set a player's balance to a specific amount.

---

## 🤝 For Developers
If you are developing a plugin (like a Shop or Job system), do not depend on HyEconomy directly. Instead, depend on EcoAPI. HyEconomy is simply the implementation that runs in the background.
