# 🚀 Running NomaKey in Android Emulator

## ✅ BUILD SUCCESSFUL!

Your APK has been built successfully:
- **Location**: `phone\build\outputs\apk\debug\phone-debug.apk`
- **Build Type**: Debug
- **Status**: Ready to install

---

## Method 1: Using Gradle (Recommended - Easiest!)

### Step 1: Start an Android Emulator
Open Android Studio and start an emulator:
1. Click the **AVD Manager** icon (phone with Android icon)
2. Click the **Play button** (▶) next to any emulator
3. Wait for the emulator to boot completely

### Step 2: Install and Run
Once the emulator is running, open PowerShell in the project directory and run:

```bash
cd C:\xampp\htdocs\NomaKey
.\gradlew.bat installDebug
```

This will:
- ✅ Build the APK (if needed)
- ✅ Install it on the running emulator
- ✅ The app icon will appear in the emulator's app drawer

### Step 3: Launch the App
On the emulator, open the app drawer and tap the **NomaKey** app icon.

---

## Method 2: Using Android Studio (GUI Method)

### Option A: Import and Run
1. Open **Android Studio**
2. **File** → **Open** → Select `C:\xampp\htdocs\NomaKey`
3. Wait for Gradle sync to complete
4. Click the **green Run button** (▶) in the toolbar
5. Select your emulator from the device dropdown
6. Click OK

### Option B: Open Existing Project
If already opened in Android Studio:
1. Make sure an emulator is running (or it will start one)
2. Select **phone** configuration in the dropdown (top toolbar)
3. Click the **Run button** (▶)
4. Wait for installation and app will auto-launch

---

## Method 3: Using ADB (Manual Install)

### Step 1: Ensure ADB is Available
```bash
# Check if ADB is in your PATH
adb version
```

If not found, ADB is typically located at:
```
C:\Users\<YourUsername>\AppData\Local\Android\Sdk\platform-tools\adb.exe
```

### Step 2: Check Device Connection
```bash
# List connected devices/emulators
adb devices
```

You should see output like:
```
List of devices attached
emulator-5554   device
```

### Step 3: Install APK
```bash
cd C:\xampp\htdocs\NomaKey
adb install phone\build\outputs\apk\debug\phone-debug.apk
```

### Step 4: Launch App
```bash
# Launch the app
adb shell am start -n com.nomakey.authenticator/.MainActivity
```

---

## Method 4: Drag and Drop (Simplest!)

1. Start your Android emulator
2. Navigate to `C:\xampp\htdocs\NomaKey\phone\build\outputs\apk\debug\`
3. **Drag** the `phone-debug.apk` file
4. **Drop** it onto the emulator window
5. Wait for installation to complete
6. Find the app in the app drawer

---

## Quick Commands Reference

```bash
# Navigate to project
cd C:\xampp\htdocs\NomaKey

# Build debug APK
.\gradlew.bat assembleDebug

# Install on connected device/emulator
.\gradlew.bat installDebug

# Uninstall from device
.\gradlew.bat uninstallDebug

# Build and install in one command
.\gradlew.bat installDebug

# Launch app after installing
adb shell am start -n com.nomakey.authenticator/.MainActivity

# View app logs
adb logcat | Select-String "NomaKey"
```

---

## Troubleshooting

### No emulator available
**Solution**: Create one in Android Studio
1. Open Android Studio
2. **Tools** → **AVD Manager**
3. Click **Create Virtual Device**
4. Select a device (e.g., Pixel 5)
5. Select a system image (API 34 or 26-34)
6. Click Finish

### "No devices found"
**Solution**: Start the emulator first
```bash
# List available emulators
emulator -list-avds

# Start a specific emulator
emulator -avd <emulator_name>
```

### "Installation failed"
**Solutions**:
1. Uninstall old version first:
   ```bash
   .\gradlew.bat uninstallDebug
   ```

2. Or use ADB:
   ```bash
   adb uninstall com.nomakey.authenticator
   ```

3. Then try installing again:
   ```bash
   .\gradlew.bat installDebug
   ```

### "ADB not found"
**Solution**: Add Android SDK to PATH
1. Find your Android SDK location (usually `C:\Users\<Username>\AppData\Local\Android\Sdk`)
2. Add `platform-tools` folder to system PATH
3. Or use full path:
   ```bash
   C:\Users\<Username>\AppData\Local\Android\Sdk\platform-tools\adb.exe devices
   ```

### App crashes on launch
**Solution**: Check logs
```bash
# View crash logs
adb logcat *:E

# Filter NomaKey logs only
adb logcat | Select-String "com.nomakey"
```

---

## What You'll See

When the app launches, you'll see:

1. **Account List Screen** (currently empty)
   - A floating action button (+) to add accounts
   - Empty state message

2. **Current Features** (UI only):
   - Basic Material Design 3 theme
   - Account list structure
   - Add account button

3. **To Be Implemented**:
   - QR code scanner for adding accounts
   - OTP code display with countdown
   - Account management (edit/delete)
   - Manual account entry

---

## Testing the App

### Core Functionality (Backend is Ready!)
The following are already implemented in the `core` module:

✅ **OTP Generation**
- TOTP (Time-based) - RFC 6238
- HOTP (Counter-based) - RFC 4226
- Supports SHA1, SHA256, SHA512

✅ **Secure Storage**
- AES-256-GCM encryption
- Android Keystore integration
- Encrypted SharedPreferences

✅ **Account Management**
- Save/retrieve/delete accounts
- JSON serialization
- Base32 encoding/decoding

### UI (Needs Integration)
⏳ **In Progress**:
- Connect UI to backend
- Add QR code scanner
- Display OTP codes
- Show countdown timer

---

## Development Workflow

### Make Changes and Test
```bash
# 1. Make code changes in Android Studio

# 2. Build and install
.\gradlew.bat installDebug

# 3. Or use Android Studio's Run button (faster)
```

### Check Logs While Running
```bash
# Terminal 1: View all logs
adb logcat

# Terminal 2: Filter by package
adb logcat | Select-String "nomakey"

# View only errors
adb logcat *:E
```

### Clean Build (If Issues)
```bash
.\gradlew.bat clean assembleDebug installDebug
```

---

## Emulator Requirements

### Minimum Specs
- **API Level**: 26+ (Android 8.0+)
- **Target API**: 34 (Android 14)
- **RAM**: 2GB minimum (4GB recommended)
- **Storage**: 2GB free space

### Recommended Emulator
- **Device**: Pixel 5 or Pixel 6
- **API Level**: 34 (latest)
- **ABI**: x86_64 or ARM64
- **Google APIs**: Not required (app works offline)

---

## Next Steps After Running

1. **Explore the UI**
   - Check the Material Design 3 theme
   - Test the navigation structure
   - Review the empty state

2. **Add Backend Integration**
   - Connect AccountListScreen to SecretEncryption
   - Implement QR code scanning
   - Display actual OTP codes

3. **Test Core Features**
   - Add test accounts
   - Verify OTP generation
   - Test encryption/decryption

---

## 🎯 Summary

**To run your app RIGHT NOW:**

```bash
# 1. Start an emulator in Android Studio
# 2. Run this command:
cd C:\xampp\htdocs\NomaKey
.\gradlew.bat installDebug
# 3. Open the app from the emulator's app drawer
```

**That's it!** 🚀

Your NomaKey authenticator is built and ready to run!

---

*Built on: January 9, 2026*
*APK Location: `phone\build\outputs\apk\debug\phone-debug.apk`*
*Package: `com.nomakey.authenticator`*

