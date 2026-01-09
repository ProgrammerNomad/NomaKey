# NomaKey - Run in Emulator Script
# This script helps you run the NomaKey app in an Android emulator

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  NomaKey Authenticator - Run Script" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Set Android SDK path
$ANDROID_SDK = "$env:LOCALAPPDATA\Android\Sdk"
$ADB = "$ANDROID_SDK\platform-tools\adb.exe"
$EMULATOR = "$ANDROID_SDK\emulator\emulator.exe"

# Check if Android SDK exists
if (-not (Test-Path $ANDROID_SDK)) {
    Write-Host "❌ Android SDK not found!" -ForegroundColor Red
    Write-Host "Please install Android Studio from: https://developer.android.com/studio" -ForegroundColor Yellow
    Write-Host ""
    Write-Host "After installation, create an emulator:" -ForegroundColor Yellow
    Write-Host "1. Open Android Studio" -ForegroundColor White
    Write-Host "2. Tools -> AVD Manager" -ForegroundColor White
    Write-Host "3. Create Virtual Device" -ForegroundColor White
    Write-Host "4. Select Pixel 5 or Pixel 6" -ForegroundColor White
    Write-Host "5. Choose API 34 (Android 14)" -ForegroundColor White
    exit 1
}

Write-Host "✅ Android SDK found at: $ANDROID_SDK" -ForegroundColor Green
Write-Host ""

# Check if ADB exists
if (-not (Test-Path $ADB)) {
    Write-Host "❌ ADB not found!" -ForegroundColor Red
    Write-Host "Please ensure Android SDK is properly installed." -ForegroundColor Yellow
    exit 1
}

Write-Host "✅ ADB found" -ForegroundColor Green
Write-Host ""

# Check for running emulators/devices
Write-Host "Checking for connected devices..." -ForegroundColor Cyan
$devices = & $ADB devices | Select-String "device$" | Measure-Object
$deviceCount = $devices.Count

if ($deviceCount -eq 0) {
    Write-Host "⚠️  No emulator or device is running!" -ForegroundColor Yellow
    Write-Host ""
    Write-Host "OPTIONS:" -ForegroundColor Cyan
    Write-Host ""

    # Check for available AVDs
    if (Test-Path $EMULATOR) {
        Write-Host "Checking for available emulators..." -ForegroundColor Cyan
        $avds = & $EMULATOR -list-avds

        if ($avds) {
            Write-Host "✅ Found emulators:" -ForegroundColor Green
            $avds | ForEach-Object { Write-Host "   - $_" -ForegroundColor White }
            Write-Host ""
            Write-Host "To start an emulator, run:" -ForegroundColor Yellow
            Write-Host "   emulator -avd <emulator_name>" -ForegroundColor White
            Write-Host ""
            Write-Host "Example:" -ForegroundColor Yellow
            Write-Host "   emulator -avd $($avds[0])" -ForegroundColor White
        } else {
            Write-Host "⚠️  No emulators found!" -ForegroundColor Yellow
            Write-Host ""
            Write-Host "Please create one in Android Studio:" -ForegroundColor Yellow
            Write-Host "1. Open Android Studio" -ForegroundColor White
            Write-Host "2. Tools -> AVD Manager" -ForegroundColor White
            Write-Host "3. Create Virtual Device" -ForegroundColor White
            Write-Host "4. Select a device (e.g., Pixel 5)" -ForegroundColor White
            Write-Host "5. Choose API 34 (Android 14)" -ForegroundColor White
        }
    } else {
        Write-Host "Please start an emulator from Android Studio:" -ForegroundColor Yellow
        Write-Host "1. Open Android Studio" -ForegroundColor White
        Write-Host "2. Click AVD Manager icon" -ForegroundColor White
        Write-Host "3. Click Play button next to an emulator" -ForegroundColor White
    }

    Write-Host ""
    Write-Host "After starting an emulator, run this script again!" -ForegroundColor Cyan
    exit 1
}

Write-Host "✅ Found $deviceCount connected device(s)" -ForegroundColor Green
& $ADB devices
Write-Host ""

# Build and install
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Building and Installing NomaKey..." -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

Write-Host "Building APK..." -ForegroundColor Yellow
$buildOutput = & .\gradlew.bat assembleDebug 2>&1

if ($LASTEXITCODE -eq 0) {
    Write-Host "✅ Build successful!" -ForegroundColor Green
    Write-Host ""

    Write-Host "Installing on device..." -ForegroundColor Yellow
    $installOutput = & .\gradlew.bat installDebug 2>&1

    if ($LASTEXITCODE -eq 0) {
        Write-Host "✅ Installation successful!" -ForegroundColor Green
        Write-Host ""

        Write-Host "Launching NomaKey..." -ForegroundColor Yellow
        & $ADB shell am start -n com.nomakey.authenticator/.MainActivity

        Write-Host ""
        Write-Host "========================================" -ForegroundColor Cyan
        Write-Host "✅ NomaKey is now running!" -ForegroundColor Green
        Write-Host "========================================" -ForegroundColor Cyan
        Write-Host ""
        Write-Host "Check the emulator screen for the app." -ForegroundColor White
        Write-Host ""
        Write-Host "To view logs, run:" -ForegroundColor Yellow
        Write-Host "   adb logcat | Select-String 'nomakey'" -ForegroundColor White

    } else {
        Write-Host "❌ Installation failed!" -ForegroundColor Red
        Write-Host $installOutput
    }

} else {
    Write-Host "❌ Build failed!" -ForegroundColor Red
    Write-Host $buildOutput
    Write-Host ""
    Write-Host "Try cleaning first:" -ForegroundColor Yellow
    Write-Host "   .\gradlew.bat clean" -ForegroundColor White
}

Write-Host ""

