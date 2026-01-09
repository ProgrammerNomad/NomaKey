# NomaKey Authenticator

<p align="center">
  <strong>A secure, offline-first authenticator for Android & Wear OS</strong>
</p>

<p align="center">
  <a href="#what-is-nomakey">What is NomaKey?</a> •
  <a href="#the-problem-we-solve">The Problem</a> •
  <a href="#core-philosophy--trust-model">Philosophy</a> •
  <a href="#features">Features</a> •
  <a href="#architecture">Architecture</a> •
  <a href="#technology-stack">Tech Stack</a> •
  <a href="#getting-started">Getting Started</a>
</p>

---

## What is NomaKey?

**NomaKey** is a secure, offline-first authenticator application for Android phones and Wear OS smartwatches that generates one-time passwords (OTP) for two-factor authentication (2FA).

The app is designed with a unique approach to reliability and independence:

✅ **Phone and watch both work independently** — No constant connection needed  
✅ **OTPs are generated locally** — All computation happens on your device  
✅ **Works without internet** — Never requires a network connection  
✅ **Works even if watch is disconnected** — True device independence  
✅ **No custom cloud, no servers, no user accounts** — Zero external dependencies  
✅ **Encrypted local storage** — All sensitive data is protected with AES-256  
✅ **Android system backup only** — Uses Google's built-in encrypted backup  

---

## The Problem We Solve

Existing authenticator apps create **real-world problems** that frustrate users:

| Problem | Real-World Impact |
|---------|------------------|
| **Smartwatches disconnect often** | You can't access codes when your phone is out of range |
| **Users lose access when phone is not nearby** | Forgot your phone at home? No authentication for you |
| **Manual backup of secrets is painful and unsafe** | Exporting secrets to files creates security risks |
| **Cloud-based apps raise privacy concerns** | Your authentication data lives on someone else's server |
| **Switching devices causes permanent lockout** | Lost your phone? Lost all your accounts |

### NomaKey's Solution

NomaKey solves these problems by being **local-first, independent, and recoverable** without sacrificing security. After a one-time setup, your phone and watch function as completely separate authenticators — no connection required.

---

## Core Philosophy & Trust Model

NomaKey is built on six fundamental principles:

### 1. **Local-First**
OTPs are generated on the device using local computation. No remote server is ever involved in generating your codes.

### 2. **Offline-First**
No internet required after initial setup. NomaKey works on airplanes, in basements, and anywhere connectivity is unavailable.

### 3. **Independence**
The watch and phone function separately after sync. If one device dies, the other continues working.

### 4. **Zero Custom Cloud**
NomaKey never stores or transmits authentication data to any external server owned by us or anyone else.

### 5. **OS-Managed Backup**
We rely exclusively on Android's encrypted system backup (Google Backup). We never implement custom backup solutions that could introduce vulnerabilities.

### 6. **User Trust**
No data collection. No analytics. No tracking. Your authentication secrets are yours alone.

> ### Trust Statement
> 
> **NomaKey never stores or transmits authentication data to any external server.**  
> All secrets are encrypted locally and backed up only through Android's system backup mechanism, which is encrypted and tied to your Google account.

---

## Supported Platforms

| Platform | Support | Notes |
|----------|---------|-------|
| **Android Phone** | ✅ Yes | Primary platform with QR scanning |
| **Wear OS Smartwatch** | ✅ Yes | Full independence after sync |
| **Offline Usage** | ✅ Yes | No internet required |
| **Disconnected Watch** | ✅ Yes | Works without phone connection |
| **Custom Cloud** | ❌ No | By design — local only |
| **iOS App** | ❌ Not in scope | Future consideration |

---

## Supported OTP Standards

NomaKey follows **industry standards** to ensure compatibility with all major services:

### OTP Types Supported

- **TOTP** (Time-based One-Time Password) — [RFC 6238](https://datatracker.ietf.org/doc/html/rfc6238)
- **HOTP** (HMAC-based One-Time Password) — [RFC 4226](https://datatracker.ietf.org/doc/html/rfc4226)

### Configuration Options

| Parameter | Supported Values |
|-----------|-----------------|
| **Digits** | 6, 8 |
| **Period** | 30 seconds, 60 seconds |
| **Algorithms** | SHA-1, SHA-256, SHA-512 |

### Compatibility

NomaKey works with all major services including:

- Google / Gmail
- GitHub
- Microsoft / Azure / Office 365
- Amazon Web Services (AWS)
- Banking institutions
- Enterprise SSO systems
- Any service supporting standard TOTP/HOTP

---

## Features

### 🔐 Security Features

- **AES-256 Encryption** — All secrets are encrypted using industry-standard encryption
- **Android Keystore Integration** — Hardware-backed security where available
- **Screenshot Blocking** — Prevents accidental exposure of OTP codes
- **App Auto-Lock** — Configurable timeout for automatic locking
- **Biometric / PIN Lock** — Optional additional authentication layer
- **No Clipboard Exposure** — Codes are displayed but never copied insecurely
- **No Logging** — Secrets never appear in logs or crash reports

### 📱 Phone Features

- **QR Code Scanning** — Easy account setup by scanning standard QR codes
- **Large OTP Display** — Clear, readable codes with countdown timer
- **Account Management** — Organize and search your accounts
- **Backup Status Indicator** — See when your data was last backed up
- **"Send to Watch" Action** — One-tap sync to your Wear OS device
- **Settings & Preferences** — Customize behavior to your needs

### ⌚ Watch Features

- **Large OTP Display** — Optimized for quick glances
- **Circular Timer Indicator** — Visual countdown for TOTP codes
- **Swipe Navigation** — Quickly move between accounts
- **Minimal Taps** — Access codes with minimal interaction
- **High Contrast UI** — Readable in all lighting conditions
- **No Phone Required** — Generate codes even when phone is off

### 🔄 Sync & Backup

- **One-Time Encrypted Sync** — Transfer secrets to watch securely
- **Android Auto Backup** — Automatic encrypted backup via Google
- **Seamless Restore** — Get your accounts back on new devices
- **No Manual Export** — Eliminates insecure secret handling

---

## Architecture

### High-Level System Design

```
┌─────────────────────────┐       ┌─────────────────────────┐
│    Android Phone        │       │    Wear OS Watch        │
├─────────────────────────┤       ├─────────────────────────┤
│ • QR Code Scanner       │       │ • No Camera             │
│ • Account Management    │       │ • Local OTP Generation  │
│ • Encrypted Storage     │       │ • Encrypted Storage     │
│ • OTP Generation        │       │ • Independent Operation │
│ • Google Backup         │       │ • Sync Receiver Only    │
│ • Watch Sync Sender     │       │                         │
└───────────┬─────────────┘       └───────────┬─────────────┘
            │                                 │
            │   One-Time Encrypted Sync       │
            │   (Wear OS Data Layer API)      │
            └─────────────────────────────────┘
                     ↓
            Secrets transferred once
            Then: Complete independence
```

### Key Architecture Principles

1. **Shared Core Logic** — OTP generation, encryption, and data models are shared between phone and watch
2. **Device-Specific UI** — Each platform has optimized user interface code
3. **One-Time Sync** — Secrets are transferred once, then devices operate independently
4. **No Persistent Connection** — After sync, no communication is needed

---

## Phone ↔ Watch Communication

### Technology: Wear OS Data Layer API

We use Google's official **Wear OS Data Layer API** for device communication.

#### Why This Technology?

✅ **Automatic Transport** — Works over Bluetooth, Wi-Fi, or LTE automatically  
✅ **Secure** — Built-in encryption and authentication  
✅ **Play Store Approved** — Official Google solution, no policy issues  
✅ **Handles Disconnections** — Automatic retry and queuing  
✅ **No Custom Networking** — No complex pairing or connection management  

#### Sync Model

**When Sync Happens:**
- When adding a new account on the phone
- When restoring a new watch
- When user manually triggers "Send to Watch"

**What Gets Synced:**
- Account name/issuer
- Encrypted OTP secret
- OTP configuration (period, digits, algorithm)

**What Happens After Sync:**
- Phone and watch operate **completely independently**
- No connection is required for daily use
- Each device generates its own OTPs locally

---

## Data Storage & Encryption

### Local Storage Architecture

Both phone and watch use identical security measures:

#### Encryption Stack

```
┌──────────────────────────────────────┐
│  Application Layer                   │
│  (Handles OTP generation)            │
└─────────────┬────────────────────────┘
              ↓
┌──────────────────────────────────────┐
│  EncryptedSharedPreferences          │
│  (AES-256-GCM encryption)            │
└─────────────┬────────────────────────┘
              ↓
┌──────────────────────────────────────┐
│  Android Keystore                    │
│  (Hardware-backed where available)   │
└─────────────┬────────────────────────┘
              ↓
┌──────────────────────────────────────┐
│  Encrypted Storage                   │
│  (At-rest encryption)                │
└──────────────────────────────────────┘
```

#### Security Guarantees

✅ **No Plaintext Secrets** — Secrets are never stored unencrypted  
✅ **Hardware Protection** — Uses Android Keystore for key management  
✅ **Memory Protection** — Sensitive data cleared from memory after use  
✅ **Root Protection** — Additional checks on rooted/compromised devices  

---

## Backup & Recovery

### How Backup Works

NomaKey uses **Android Auto Backup**, which is:

- Built into the Android operating system
- Encrypted end-to-end by Google
- Tied to your Google account
- Automatic and transparent

### What NomaKey Does

✅ Marks data as "should be backed up"  
✅ Ensures data is encrypted before backup  
✅ Follows Android backup best practices  

### What NomaKey Does NOT Do

❌ Upload data to custom servers  
❌ Create manual export files  
❌ Require email/password login  
❌ Generate cloud-based OTPs  

### Restore Flow

When you get a new phone:

1. **Install NomaKey** from Play Store
2. **Android Auto-Restores** your encrypted data
3. **Open NomaKey** — Your accounts are already there
4. **Optional:** Send secrets to your watch (one-time sync)
5. **Done** — Both devices work independently again

---

## Technology Stack

NomaKey is built with modern, official Android technologies chosen for speed, reliability, and long-term maintainability.

### Core Technologies

| Component | Technology | Reason |
|-----------|-----------|---------|
| **Language** | Kotlin | Official Android language, concise & safe |
| **Phone UI** | Jetpack Compose | Modern declarative UI, less boilerplate |
| **Watch UI** | Compose for Wear OS | Consistent UI paradigm across devices |
| **Architecture** | Multi-module | Shared core logic, separate UI modules |
| **Navigation** | Compose Navigation | Type-safe navigation |
| **Storage** | EncryptedSharedPreferences | Secure encrypted storage |
| **Security** | Android Keystore | Hardware-backed key storage |
| **Device Sync** | Wear OS Data Layer API | Official Google device communication |
| **OTP Library** | RFC-compliant implementation | TOTP (RFC 6238) & HOTP (RFC 4226) |

### UI Acceleration (Like shadcn for Web)

To accelerate development, we use:

- **Material 3 Compose Components** — Pre-built UI components
- **Accompanist Libraries** — Pager, animations, system UI control
- **Compose Destinations** — Simplified navigation
- **Compose Testing** — Built-in testing support

### Benefits of This Stack

✅ **Single UI Paradigm** — Compose everywhere reduces context switching  
✅ **Less Boilerplate** — More features with less code  
✅ **Faster Development** — Rapid iteration and testing  
✅ **Easier Maintenance** — Modern, well-documented APIs  
✅ **Best Wear OS Support** — Official Google technology  

---

## Project Structure

### Recommended Module Architecture

```
com.nomakey.authenticator/
│
├── core/                           # Shared business logic
│   ├── otp/                        # TOTP/HOTP implementation
│   │   ├── TotpGenerator.kt
│   │   ├── HotpGenerator.kt
│   │   └── OtpConfig.kt
│   │
│   ├── crypto/                     # Encryption utilities
│   │   ├── SecretEncryption.kt
│   │   └── KeystoreManager.kt
│   │
│   └── model/                      # Data models
│       ├── Account.kt
│       └── OtpType.kt
│
├── phone/                          # Phone app module
│   ├── ui/                         # Phone UI screens
│   │   ├── AccountListScreen.kt
│   │   ├── AddAccountScreen.kt
│   │   └── SettingsScreen.kt
│   │
│   ├── qr/                         # QR scanning
│   │   └── QrScanner.kt
│   │
│   └── watchsync/                  # Watch communication
│       └── WatchSyncManager.kt
│
├── watch/                          # Wear OS module
│   ├── ui/                         # Watch UI screens
│   │   ├── OtpDisplayScreen.kt
│   │   └── AccountListScreen.kt
│   │
│   └── storage/                    # Watch-specific storage
│       └── WatchStorage.kt
│
├── backup/                         # Backup/restore logic
│   └── BackupManager.kt
│
└── security/                       # Security features
    ├── ScreenshotBlocker.kt
    ├── AppLockManager.kt
    └── BiometricAuth.kt
```

### Module Dependencies

```
phone  ──┐
         ├──> core
watch  ──┘

backup ──> core
security ──> core
```

Core logic is shared. Only UI and device-specific features differ.

---

## Time Handling

**Critical Detail:** TOTP (Time-based OTP) depends on accurate time synchronization.

### How NomaKey Handles Time

✅ **Uses System Time** — `System.currentTimeMillis()`  
✅ **Detects Time Drift** — Warns if device time appears significantly incorrect  
✅ **No Internet Time Sync** — Not required; relies on device clock  
⚠️ **User Responsibility** — Device automatic time sync should be enabled  

### Why This Matters

If your device time is wrong by more than 30 seconds, OTP codes may not match the server's expected codes. NomaKey detects large discrepancies and warns users.

---

## UI & UX Design

### Phone UI Screens

| Screen | Purpose | Key Elements |
|--------|---------|--------------|
| **Account List** | Main screen showing all accounts | Large OTP codes, countdown timer, search |
| **Add Account** | QR scanning and manual entry | Camera view, manual input form |
| **Settings** | App configuration | Backup status, lock settings, about |
| **Send to Watch** | Watch sync management | Device selection, sync status |

### Watch UI Screens

| Screen | Purpose | Key Elements |
|--------|---------|--------------|
| **OTP Display** | Show current code | Extra-large digits, circular timer |
| **Account List** | Navigate accounts | Swipe gestures, minimal taps |

### Design Principles

- **Glanceability** — See codes instantly without deep navigation
- **High Contrast** — Readable in all lighting conditions
- **Minimal Taps** — Complete tasks in 1-2 taps whenever possible
- **Accessibility** — Support for TalkBack and large text
- **Material Design 3** — Modern, familiar Android design language

---

## Privacy & Compliance

### Our Privacy Promise

NomaKey is designed with **privacy by default**:

| Feature | NomaKey's Approach |
|---------|-------------------|
| **Personal Data Collection** | ❌ None |
| **Analytics / Telemetry** | ❌ None |
| **User Tracking** | ❌ None |
| **Advertisements** | ❌ None |
| **Account Creation** | ❌ Not required |
| **Network Access** | 🟡 Only for QR code help links (optional) |

### Play Store Compliance

NomaKey is designed for **transparent Play Store approval**:

✅ **No Dangerous Permissions** — Only camera (for QR scanning) and local storage  
✅ **Clear Privacy Policy** — Honest declaration of data practices  
✅ **Accurate Data Use Declaration** — Google Play Data Safety form completed correctly  
✅ **Encryption Disclosure** — Uses strong encryption (reportable in some regions)  

### Regulatory Compliance

- **GDPR Compliant** — No personal data processing
- **CCPA Compliant** — No data sale or tracking
- **SOC 2 Friendly** — Suitable for enterprise security audits

---

## What is NOT Included (By Design)

NomaKey intentionally **does not** include:

| Feature | Why Not |
|---------|---------|
| **SMS-based OTP** | Security risk; SMS is interceptable |
| **Push Authentication** | Requires server infrastructure |
| **Web Dashboard** | Increases attack surface unnecessarily |
| **Multi-User Accounts** | Adds complexity and privacy concerns |
| **iOS App** | Currently out of scope (future consideration) |
| **Custom Backend / APIs** | Violates our "no server" principle |
| **Social Features** | No need to share authentication data |

---

## Future Enhancements (Roadmap)

Potential features under consideration:

### Phase 1 (Security & Usability)
- [ ] Manual encrypted export/import (with strong warnings)
- [ ] Account grouping and tagging
- [ ] Custom account icons
- [ ] Search and filtering improvements

### Phase 2 (Platform Expansion)
- [ ] Tablet-optimized UI
- [ ] Desktop companion app (view-only)
- [ ] iOS version (requires separate development)

### Phase 3 (Advanced Features)
- [ ] Advanced restore options
- [ ] Multiple watch support
- [ ] Account templates for common services
- [ ] Import from other authenticators

### Phase 4 (Community)
- [ ] Open-source release
- [ ] Community translations
- [ ] Plugin architecture for extensions

**Note:** All future features must maintain our core principles: local-first, no custom servers, and privacy-focused.

---

## Getting Started (For Developers)

### Prerequisites

To build and run NomaKey, you need:

- **Android Studio** — Latest stable version (Hedgehog or newer)
- **Kotlin** — 1.9.0+ (included with Android Studio)
- **Jetpack Compose** — Knowledge of declarative UI
- **Wear OS SDK** — Install via Android Studio SDK Manager
- **Physical Devices** — Emulators work, but testing on real hardware is recommended

### Required Dependencies

```kotlin
// Core
implementation("androidx.core:core-ktx:1.12.0")
implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")

// Compose
implementation("androidx.compose.ui:ui:1.6.0")
implementation("androidx.compose.material3:material3:1.2.0")

// Wear OS
implementation("androidx.wear.compose:compose-material:1.3.0")
implementation("com.google.android.gms:play-services-wearable:18.1.0")

// Security
implementation("androidx.security:security-crypto:1.1.0-alpha06")

// OTP
// Use RFC-compliant TOTP/HOTP library
```

### Build Instructions

1. **Clone the Repository**
   ```bash
   git clone https://github.com/yourusername/nomakey.git
   cd nomakey
   ```

2. **Open in Android Studio**
   ```
   File > Open > Select project directory
   ```

3. **Sync Gradle**
   ```
   Android Studio will automatically sync dependencies
   ```

4. **Run on Phone**
   ```
   Select 'phone' module > Run
   ```

5. **Run on Watch**
   ```
   Select 'watch' module > Run (requires paired Wear OS device)
   ```

### Development Workflow

```bash
# Run tests
./gradlew test

# Run lint checks
./gradlew lint

# Build release APK
./gradlew assembleRelease

# Install on connected device
./gradlew installDebug
```

---

## Contributing

**Status:** This project is currently in active development. Contribution guidelines will be published when the project reaches a stable release.

For now, feel free to:
- Open issues for bugs or feature requests
- Submit pull requests with improvements
- Provide feedback on the architecture

All contributions must align with our core principles: local-first, privacy-focused, and no custom servers.

---

## License

This project is licensed under the **MIT License**. See the [LICENSE](LICENSE) file for complete details.

---

## Support & Contact

- **Issues:** [GitHub Issues](https://github.com/yourusername/nomakey/issues)
- **Discussions:** [GitHub Discussions](https://github.com/yourusername/nomakey/discussions)
- **Security:** Report vulnerabilities privately to security@nomakey.app

---

## Acknowledgments

NomaKey is built on the shoulders of giants:

- **Android Open Source Project** — For the platform
- **Jetpack Compose Team** — For the modern UI framework
- **RFC Authors** — For standardizing TOTP/HOTP
- **Security Researchers** — For continuous improvement of Android security

---

<p align="center">
  <strong>Built with ❤️ for privacy and independence</strong>
</p>

<p align="center">
  <sub>NomaKey • Offline-First Authentication • 2026</sub>
</p>
