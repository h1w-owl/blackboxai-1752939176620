# Hayu Widyas - Android Kotlin App

A luxury handcrafted leather bag e-commerce mobile application built with modern Android development practices and Clean Architecture principles.

## 🏗️ Architecture

This app follows **Clean Architecture** with **MVVM** pattern:

```
📱 Presentation Layer (UI)
├── Jetpack Compose UI
├── ViewModels (MVVM)
├── Navigation (Compose Navigation)
└── Themes & Components

🔄 Domain Layer (Business Logic)
├── Use Cases
├── Repository Interfaces
└── Domain Models

💾 Data Layer
├── Repository Implementations
├── Remote Data Source (Retrofit + WooCommerce API)
├── Local Data Source (Room Database)
└── Data Models
```

## 🛠️ Tech Stack

- **UI**: Jetpack Compose with Material 3
- **Architecture**: Clean Architecture + MVVM
- **DI**: Dagger Hilt
- **Navigation**: Navigation Compose
- **Networking**: Retrofit + OkHttp
- **Local DB**: Room Database
- **Image Loading**: Coil Compose
- **Authentication**: Firebase Auth
- **Storage**: Firebase Storage
- **Backend**: WooCommerce REST API
- **Async**: Kotlin Coroutines + Flow
- **Security**: EncryptedSharedPreferences

## 🎯 Features

### Core Features
- ✅ **Onboarding Flow**: 3-screen intro with luxury branding
- ✅ **Authentication**: Email/Password + Google Sign-In (Firebase)
- ✅ **Product Catalog**: Browse with pagination, search, filter, sort
- ✅ **Product Detail**: Multiple images, variations, stock info
- ✅ **Shopping Cart**: Add/remove/update items, guest + user carts
- ✅ **Wishlist**: Save favorite items with sync across devices
- ✅ **Order Management**: Place orders via WooCommerce API
- ✅ **User Profile**: Account management and order history

### Technical Features
- 🔒 **Secure API**: OAuth authentication with WooCommerce
- 📱 **Responsive UI**: Works on all screen sizes
- 🌙 **Dark Mode**: Light/Dark theme support
- 🌐 **Localization**: English + Indonesian support
- ⚡ **Performance**: Lazy loading, caching, smooth animations
- 🔄 **Offline Support**: Local caching with Room database
- 🔐 **Security**: Encrypted storage for sensitive data

## 🚀 Setup Instructions

### Prerequisites
- Android Studio Hedgehog | 2023.1.1 or later
- JDK 17 or later
- Android SDK 34
- Git

### 1. Clone the Repository
```bash
git clone https://github.com/your-username/hayu-widyas.git
cd hayu-widyas/hayu-widyas-app
```

### 2. Configure Firebase

1. Go to [Firebase Console](https://console.firebase.google.com/)
2. Create a new project or use existing one
3. Add Android app with package name: `com.hayuwidyas`
4. Download `google-services.json` and place in `app/` directory
5. Enable Authentication (Email/Password + Google)
6. Enable Firestore Database
7. Enable Storage

### 3. Configure WooCommerce API

1. Go to your WordPress admin → WooCommerce → Settings → Advanced → REST API
2. Create new API key with Read/Write permissions
3. Update `app/build.gradle` with your credentials:

```kotlin
buildConfigField "String", "WOOCOMMERCE_BASE_URL", "\"https://yourdomain.com/wp-json/wc/v3/\""
buildConfigField "String", "WOOCOMMERCE_CONSUMER_KEY", "\"ck_your_key_here\""
buildConfigField "String", "WOOCOMMERCE_CONSUMER_SECRET", "\"cs_your_secret_here\""
```

### 4. Build and Run

```bash
./gradlew assembleDebug
```

Or open in Android Studio and run.

## 📂 Project Structure

```
app/src/main/java/com/hayuwidyas/
├── di/                          # Dependency Injection
│   ├── NetworkModule.kt
│   ├── DatabaseModule.kt
│   ├── RepositoryModule.kt
│   └── FirebaseModule.kt
├── data/                        # Data Layer
│   ├── api/                     # Remote data source
│   │   ├── WooCommerceApiService.kt
│   │   └── ApiResponse.kt
│   ├── local/                   # Local data source
│   │   ├── HayuWidyasDatabase.kt
│   │   ├── CartDao.kt
│   │   ├── WishlistDao.kt
│   │   └── ProductCacheDao.kt
│   ├── model/                   # Data models
│   │   ├── Product.kt
│   │   ├── CartItem.kt
│   │   ├── WishlistItem.kt
│   │   ├── Order.kt
│   │   └── User.kt
│   └── repository/              # Repository implementations
│       ├── ProductRepositoryImpl.kt
│       ├── CartRepositoryImpl.kt
│       ├── WishlistRepositoryImpl.kt
│       └── AuthRepositoryImpl.kt
├── domain/                      # Domain Layer
│   ├── model/                   # Domain models
│   │   └── DomainModels.kt
│   ├── repository/              # Repository interfaces
│   │   ├── ProductRepository.kt
│   │   ├── CartRepository.kt
│   │   ├── WishlistRepository.kt
│   │   └── AuthRepository.kt
│   └── usecase/                 # Use cases
│       ├── GetProductsUseCase.kt
│       ├── CartUseCases.kt
│       └── WishlistUseCases.kt
├── presentation/                # Presentation Layer
│   ├── navigation/              # Navigation
│   │   ├── AppNavGraph.kt
│   │   └── Screen.kt
│   ├── ui/                      # UI screens
│   │   ├── onboarding/
│   │   ├── auth/
│   │   ├── home/
│   │   ├── shop/
│   │   ├── product/
│   │   ├── cart/
│   │   ├── wishlist/
│   │   └── profile/
│   ├── theme/                   # App theming
│   │   ├── Color.kt
│   │   ├── Theme.kt
│   │   └── Type.kt
│   └── MainActivity.kt
├── util/                        # Utilities
│   └── Extensions.kt
└── HayuWidyasApplication.kt     # Application class
```

## 🔧 Configuration

### Environment Variables
Set these in your `app/build.gradle`:

```kotlin
defaultConfig {
    // WooCommerce API
    buildConfigField "String", "WOOCOMMERCE_BASE_URL", "\"https://hayuwidyas.com/wp-json/wc/v3/\""
    buildConfigField "String", "WOOCOMMERCE_CONSUMER_KEY", "\"ck_your_key\""
    buildConfigField "String", "WOOCOMMERCE_CONSUMER_SECRET", "\"cs_your_secret\""
}
```

### ProGuard Rules
The app includes production-ready ProGuard configuration for release builds.

## 🧪 Testing

```bash
# Run unit tests
./gradlew test

# Run instrumented tests
./gradlew connectedAndroidTest

# Run all tests
./gradlew check
```

## 📦 Build Release APK

```bash
# Generate signed APK (requires keystore setup)
./gradlew assembleRelease

# Generate App Bundle for Play Store
./gradlew bundleRelease
```

## 🚀 Deployment

### Debug APK
Debug APK is automatically generated and can be found in:
`app/build/outputs/apk/debug/app-debug.apk`

### Release APK
1. Create keystore file
2. Configure signing in `app/build.gradle`
3. Run `./gradlew assembleRelease`

## 🤝 Contributing

1. Fork the repository
2. Create feature branch: `git checkout -b feature/amazing-feature`
3. Commit changes: `git commit -m 'Add amazing feature'`
4. Push to branch: `git push origin feature/amazing-feature`
5. Open Pull Request

## 📱 Screenshots

[Add screenshots of the app here]

## 🆘 Troubleshooting

### Common Issues

1. **Build fails with Firebase error**
   - Ensure `google-services.json` is in correct location
   - Check package name matches Firebase configuration

2. **WooCommerce API not working**
   - Verify API credentials are correct
   - Check if WooCommerce REST API is enabled
   - Ensure HTTPS is configured for production

3. **Room database issues**
   - Clear app data and rebuild
   - Check if database migration is needed

## 📄 License

Copyright © 2024 Hayu Widyas. All rights reserved.

## 🔗 Related Projects

- [Hayu Widyas Dashboard](../hayu-widyas-dashboard/) - Admin web dashboard
- [Hayu Widyas Website](https://hayuwidyas.com) - Official website

---

**Built with ❤️ for luxury leather bag enthusiasts**