# macOS Setup Guide for Hayu Widyas E-Commerce Android Project

This guide will help you set up and build the **cursor/build-hayu-widyas-e-commerce-system-9012** Android/Kotlin project on macOS.

## Prerequisites

Before starting, ensure you have the following:
- macOS 10.15 (Catalina) or later
- Administrative privileges on your Mac
- Stable internet connection
- At least 8GB of free disk space

## 1. Install Homebrew (if not already installed)

Homebrew is a package manager for macOS that makes installing development tools easier.

```bash
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
```

After installation, add Homebrew to your PATH:
```bash
echo 'eval "$(/opt/homebrew/bin/brew shellenv)"' >> ~/.zprofile
eval "$(/opt/homebrew/bin/brew shellenv)"
```

## 2. Install Java 17 (or above)

### Option A: Using Homebrew (Recommended)
```bash
# Install OpenJDK 17
brew install openjdk@17

# Create symlink for system Java wrappers
sudo ln -sfn /opt/homebrew/opt/openjdk@17/libexec/openjdk.jdk /Library/Java/JavaVirtualMachines/openjdk-17.jdk
```

### Option B: Install Latest Java
```bash
# For the latest Java version
brew install openjdk
sudo ln -sfn /opt/homebrew/opt/openjdk/libexec/openjdk.jdk /Library/Java/JavaVirtualMachines/openjdk.jdk
```

### Verify Java Installation
```bash
java -version
javac -version
```

You should see Java 17 or higher in the output.

## 3. Install Android Studio

### Using Homebrew Cask (Recommended)
```bash
brew install --cask android-studio
```

### Alternative: Manual Download
1. Download Android Studio from [developer.android.com](https://developer.android.com/studio)
2. Drag the Android Studio app to your Applications folder
3. Launch Android Studio and follow the setup wizard

### Initial Android Studio Setup
1. Open Android Studio
2. Complete the setup wizard (this will download Android SDK automatically)
3. Install the latest Android SDK Platform and Build Tools when prompted
4. Make note of the Android SDK location (usually `/Users/[username]/Library/Android/sdk`)

## 4. Set Environment Variables

Add the following environment variables to your shell profile. Choose your shell:

### For Zsh (default on macOS Catalina+)
```bash
# Open your zsh profile
nano ~/.zshrc

# Add these lines at the end
export JAVA_HOME="/opt/homebrew/opt/openjdk@17/libexec/openjdk.jdk/Contents/Home"
export ANDROID_HOME="$HOME/Library/Android/sdk"
export PATH="$PATH:$ANDROID_HOME/emulator"
export PATH="$PATH:$ANDROID_HOME/platform-tools"
export PATH="$PATH:$ANDROID_HOME/cmdline-tools/latest/bin"

# Save and reload
source ~/.zshrc
```

### For Bash
```bash
# Open your bash profile
nano ~/.bash_profile

# Add the same export statements as above
# Save and reload
source ~/.bash_profile
```

### Verify Environment Variables
```bash
echo $JAVA_HOME
echo $ANDROID_HOME
```

Both should display the correct paths.

## 5. Configure Project gradle.properties

Navigate to the project root (`cursor/build-hayu-widyas-e-commerce-system-9012/hayu-widyas-app/`) and update the `gradle.properties` file:

```bash
cd cursor/build-hayu-widyas-e-commerce-system-9012/hayu-widyas-app
nano gradle.properties
```

Ensure your `gradle.properties` includes these optimizations:

```properties
# Gradle Performance Settings
org.gradle.jvmargs=-Xmx4096m -XX:+UseParallelGC -XX:+HeapDumpOnOutOfMemoryError -Dfile.encoding=UTF-8
org.gradle.parallel=true
org.gradle.caching=true
org.gradle.configureondemand=true

# AndroidX Migration
android.useAndroidX=true
android.enableJetifier=true

# Build Performance
android.enableR8.fullMode=true
android.nonTransitiveRClass=true
kapt.incremental.apt=false

# Kotlin
kotlin.code.style=official
```

## 6. Set Up Firebase (google-services.json)

⚠️ **IMPORTANT**: You must obtain a valid `google-services.json` file from your Firebase project.

### Steps:
1. Go to [Firebase Console](https://console.firebase.google.com/)
2. Select your project (or create one if needed)
3. Go to Project Settings → General
4. Under "Your apps" section, download the `google-services.json` file
5. Place it in the correct location:

```bash
# The file should be placed here:
cursor/build-hayu-widyas-e-commerce-system-9012/hayu-widyas-app/app/google-services.json
```

### Verify Package Name Match
Ensure the `package_name` in `google-services.json` matches the `applicationId` in `app/build.gradle`:
- **Expected applicationId**: `com.hayuwidyas`
- **File location**: `hayu-widyas-app/app/build.gradle` (line ~19)

## 7. Project Structure Overview

The project follows this structure:
```
cursor/build-hayu-widyas-e-commerce-system-9012/
├── hayu-widyas-app/                 # Main Android project
│   ├── app/
│   │   ├── src/main/
│   │   │   ├── AndroidManifest.xml  # Main manifest file
│   │   │   ├── java/                # Kotlin source files
│   │   │   └── res/                 # Resources (layouts, images, etc.)
│   │   ├── build.gradle             # App module configuration
│   │   └── google-services.json     # Firebase configuration (you need to add this)
│   ├── build.gradle                 # Project-level build file
│   ├── gradle.properties            # Gradle settings
│   └── settings.gradle              # Module settings
└── hayu-widyas-dashboard/           # Additional module
```

## 8. Build the Project

Navigate to the Android project directory and build:

```bash
# Navigate to the Android project
cd cursor/build-hayu-widyas-e-commerce-system-9012/hayu-widyas-app

# Make gradlew executable (if needed)
chmod +x gradlew

# Clean the project
./gradlew clean

# Build debug APK
./gradlew assembleDebug
```

### Successful Build Output
You should see:
```
BUILD SUCCESSFUL in [time]
```

The APK will be located at:
`hayu-widyas-app/app/build/outputs/apk/debug/app-debug.apk`

## 9. Troubleshooting Common Issues

### OutOfMemoryError
If you encounter memory issues:

```bash
# Increase heap size in gradle.properties
echo "org.gradle.jvmargs=-Xmx6144m -XX:+UseParallelGC" >> gradle.properties

# Or set environment variable
export GRADLE_OPTS="-Xmx6144m"
```

### Missing AndroidManifest.xml
**Error**: "No Android manifest found"
**Solution**: Verify the manifest location:
```bash
ls -la hayu-widyas-app/app/src/main/AndroidManifest.xml
```

### Gradle Cache Lock Errors
```bash
# Clear Gradle cache
rm -rf ~/.gradle/caches/
rm -rf ~/.gradle/daemon/

# Clean and rebuild
./gradlew clean
./gradlew assembleDebug
```

### Firebase/Google Services Errors
**Error**: "google-services.json is missing"
**Solutions**:
1. Verify file placement: `hayu-widyas-app/app/google-services.json`
2. Check package name matches: `com.hayuwidyas`
3. Validate JSON syntax:
```bash
python -m json.tool app/google-services.json
```

### SDK License Issues
```bash
# Accept all SDK licenses
$ANDROID_HOME/cmdline-tools/latest/bin/sdkmanager --licenses
```

### Permission Denied on gradlew
```bash
chmod +x gradlew
```

### Kotlin Compiler Issues
If you encounter Kotlin compilation errors:
```bash
# Clear Kotlin cache
rm -rf ~/.kotlin
./gradlew clean
./gradlew assembleDebug
```

### macOS-Specific Issues

#### Command Line Tools Missing
```bash
xcode-select --install
```

#### Rosetta 2 (for Apple Silicon Macs)
If using an M1/M2 Mac and encountering architecture issues:
```bash
# Install Rosetta 2
softwareupdate --install-rosetta
```

#### Java Version Conflicts
```bash
# Check all Java versions
/usr/libexec/java_home -V

# Set specific Java version
export JAVA_HOME=$(/usr/libexec/java_home -v 17)
```

## 10. Running the App

### Option 1: Using Android Studio
1. Open Android Studio
2. Choose "Open an existing project"
3. Navigate to `cursor/build-hayu-widyas-e-commerce-system-9012/hayu-widyas-app`
4. Wait for Gradle sync to complete
5. Click the "Run" button or press Ctrl+R

### Option 2: Using Command Line with Emulator
```bash
# List available emulators
$ANDROID_HOME/emulator/emulator -list-avds

# Start an emulator (replace 'Pixel_4_API_30' with your emulator name)
$ANDROID_HOME/emulator/emulator -avd Pixel_4_API_30 &

# Install the APK
$ANDROID_HOME/platform-tools/adb install app/build/outputs/apk/debug/app-debug.apk
```

### Option 3: Install on Physical Device
```bash
# Enable USB debugging on your Android device
# Connect via USB and install
$ANDROID_HOME/platform-tools/adb install app/build/outputs/apk/debug/app-debug.apk
```

## 11. Additional Notes

- **WooCommerce Configuration**: The project includes WooCommerce API integration. Update the API keys in `app/build.gradle` if needed.
- **Minimum SDK**: The app requires Android 7.0 (API level 24) or higher.
- **Target SDK**: Built for Android 14 (API level 34).
- **Development**: Uses Jetpack Compose for UI, Hilt for dependency injection, and Room for local database.

## 12. Getting Help

If you encounter issues not covered in this guide:

1. **Check Android Studio logs**: View → Tool Windows → Build
2. **Enable Gradle debug logs**:
   ```bash
   ./gradlew assembleDebug --debug > build.log 2>&1
   ```
3. **Check system requirements**: Ensure your Mac meets minimum requirements for Android development
4. **Update tools**: Keep Android Studio, SDK, and build tools updated

---

**Happy coding! 🚀**