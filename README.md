# Hayu Widyas - Luxury Leather Bag E-Commerce Platform

A complete production-grade e-commerce ecosystem for "Hayu Widyas," a luxury handcrafted leather bag brand. This system consists of a modern Android mobile application and a professional web admin dashboard, both built with world-class development practices.

## 🎯 Project Overview

**Hayu Widyas** represents the pinnacle of luxury leather craftsmanship, and this platform reflects that standard through:

- **Modern Architecture**: Clean Architecture, MVVM, and industry best practices
- **Premium User Experience**: Elegant UI/UX designed for luxury brand standards
- **Production Ready**: Zero missing components, complete with documentation
- **Scalable Design**: Built to handle growth and future enhancements
- **Security First**: Enterprise-grade security and data protection

## 📱 Applications

### 1. Android Kotlin Mobile App
**Location**: `hayu-widyas-app/`

A premium customer-facing mobile application featuring:

- **✨ Elegant UI**: Jetpack Compose with Material 3 design
- **🔐 Secure Auth**: Firebase Authentication with Google Sign-In
- **🛍️ Shopping Experience**: Product browsing, cart, wishlist, orders
- **📱 Modern Architecture**: Clean Architecture + MVVM + Dagger Hilt
- **⚡ High Performance**: Room caching, lazy loading, smooth animations
- **🌐 Localization**: English + Indonesian support

**Tech Stack**: Kotlin, Jetpack Compose, Clean Architecture, Firebase, WooCommerce API, Room, Retrofit

### 2. Web Admin Dashboard
**Location**: `hayu-widyas-dashboard/`

A sophisticated admin dashboard for business management:

- **📊 Analytics Dashboard**: Real-time sales reporting and insights
- **🛒 Product Management**: CRUD operations with image upload
- **📋 Order Processing**: Order management and status tracking
- **👥 Customer Management**: Customer database and analytics
- **🎨 Professional Design**: Tailwind CSS with luxury brand theming
- **📱 Responsive**: Works perfectly on desktop and mobile

**Tech Stack**: Next.js 14, TypeScript, Tailwind CSS, Firebase Admin, WooCommerce API, Recharts

## 🏗️ System Architecture

```
📱 MOBILE APP (Android Kotlin)
├── Presentation Layer (Jetpack Compose + MVVM)
├── Domain Layer (Use Cases + Repository Interfaces)
└── Data Layer (Room DB + Retrofit + Firebase)

🌐 WEB DASHBOARD (Next.js TypeScript)
├── Frontend (React + Tailwind CSS)
├── API Layer (Custom + WooCommerce)
└── Backend Services (Firebase Admin)

🔄 SHARED SERVICES
├── WooCommerce REST API (Products, Orders, Customers)
├── Firebase (Authentication, Storage, Analytics)
└── Database (Room + Firestore + WooCommerce)
```

## 🚀 Quick Start

### Prerequisites
- **Android Development**: Android Studio Hedgehog+, JDK 17+, Android SDK 34
- **Web Development**: Node.js 18+, npm/yarn
- **Services**: Firebase project, WooCommerce store with REST API

### 1. Clone Repository
```bash
git clone https://github.com/your-username/hayu-widyas.git
cd hayu-widyas
```

### 2. Android App Setup
```bash
cd hayu-widyas-app
# Configure Firebase (add google-services.json)
# Update WooCommerce API credentials in build.gradle
./gradlew assembleDebug
```

### 3. Web Dashboard Setup
```bash
cd hayu-widyas-dashboard
npm install
cp .env.example .env.local
# Configure environment variables
npm run dev
```

## 🎨 Features Highlight

### Mobile App Features
- **🎬 Onboarding**: 3-screen luxury brand introduction
- **🔐 Authentication**: Email/Password + Google Sign-In
- **🛍️ Product Catalog**: Advanced search, filter, sort with pagination
- **💝 Wishlist**: Save favorites with cross-device sync
- **🛒 Shopping Cart**: Guest and user carts with local storage
- **📦 Order Management**: Complete order lifecycle
- **👤 User Profile**: Account management and history
- **🌙 Dark Mode**: Light/Dark theme support
- **🌐 Multi-language**: English + Indonesian

### Dashboard Features
- **📊 Sales Analytics**: Today/week/month metrics with charts
- **📈 Performance Tracking**: Top products, customer analytics
- **🛒 Product Management**: Full CRUD with image upload
- **📋 Order Processing**: Status updates and tracking
- **👥 Customer Database**: Complete customer management
- **📝 Content Management**: Blog posts and page management
- **🔒 Admin Security**: Firebase Auth with role-based access
- **📱 Responsive Design**: Mobile-optimized interface

## 🛠️ Technology Stack

### Mobile (Android)
- **Language**: Kotlin
- **UI**: Jetpack Compose + Material 3
- **Architecture**: Clean Architecture + MVVM
- **DI**: Dagger Hilt
- **Database**: Room + SQLite
- **Networking**: Retrofit + OkHttp
- **Images**: Coil Compose
- **Auth**: Firebase Auth
- **Navigation**: Navigation Compose

### Web Dashboard
- **Framework**: Next.js 14 (App Router)
- **Language**: TypeScript
- **Styling**: Tailwind CSS + Shadcn/ui
- **Components**: Radix UI + Headless UI
- **Charts**: Recharts
- **Forms**: React Hook Form + Zod
- **Auth**: Firebase Auth
- **API**: Axios + SWR

### Shared Services
- **Backend API**: WooCommerce REST API
- **Authentication**: Firebase Auth
- **Storage**: Firebase Storage
- **Database**: Firebase Firestore + Room
- **Analytics**: Firebase Analytics

## 📂 Project Structure

```
hayu-widyas/
├── hayu-widyas-app/                 # Android Kotlin App
│   ├── app/src/main/java/com/hayuwidyas/
│   │   ├── di/                      # Dependency Injection
│   │   ├── data/                    # Data Layer
│   │   │   ├── api/                 # API Services
│   │   │   ├── local/               # Room Database
│   │   │   ├── model/               # Data Models
│   │   │   └── repository/          # Repository Implementations
│   │   ├── domain/                  # Domain Layer
│   │   │   ├── model/               # Domain Models
│   │   │   ├── repository/          # Repository Interfaces
│   │   │   └── usecase/             # Use Cases
│   │   └── presentation/            # Presentation Layer
│   │       ├── ui/                  # Compose Screens
│   │       ├── navigation/          # Navigation
│   │       └── theme/               # App Theming
│   ├── build.gradle                 # App Dependencies
│   └── README.md                    # Android Setup Guide
├── hayu-widyas-dashboard/           # Next.js Admin Dashboard
│   ├── pages/                       # Next.js Pages
│   ├── components/                  # React Components
│   ├── lib/                         # Utilities & Services
│   ├── styles/                      # Global Styles
│   ├── public/                      # Static Assets
│   ├── .env.example                 # Environment Template
│   └── README.md                    # Dashboard Setup Guide
└── README.md                        # This file
```

## 🔧 Configuration

### Environment Variables

**Android App** (`app/build.gradle`):
```kotlin
buildConfigField "String", "WOOCOMMERCE_BASE_URL", "\"https://hayuwidyas.com/wp-json/wc/v3/\""
buildConfigField "String", "WOOCOMMERCE_CONSUMER_KEY", "\"ck_your_key\""
buildConfigField "String", "WOOCOMMERCE_CONSUMER_SECRET", "\"cs_your_secret\""
```

**Web Dashboard** (`.env.local`):
```env
NEXT_PUBLIC_FIREBASE_API_KEY=your_api_key
WOOCOMMERCE_BASE_URL=https://hayuwidyas.com/wp-json/wc/v3/
WOOCOMMERCE_CONSUMER_KEY=ck_your_key
WOOCOMMERCE_CONSUMER_SECRET=cs_your_secret
```

## 🚀 Deployment

### Android App
```bash
# Debug APK
./gradlew assembleDebug

# Release APK (requires keystore)
./gradlew assembleRelease

# App Bundle for Play Store
./gradlew bundleRelease
```

### Web Dashboard
```bash
# Build for production
npm run build

# Deploy to Vercel
vercel

# Or deploy to other platforms
npm run build && npm start
```

## 🧪 Testing

### Android
```bash
./gradlew test                    # Unit tests
./gradlew connectedAndroidTest    # Instrumented tests
```

### Web Dashboard
```bash
npm run test                      # Run tests
npm run lint                      # Code linting
npm run type-check               # TypeScript checking
```

## 📊 Performance Features

### Mobile App
- **⚡ Fast Loading**: Lazy loading and pagination
- **💾 Offline Support**: Room database caching
- **🎨 Smooth Animations**: 60fps transitions
- **🔧 Optimized Images**: Coil with memory management
- **📱 Responsive UI**: Works on all screen sizes

### Web Dashboard
- **🚀 Next.js Optimization**: Static generation and ISR
- **📊 Efficient Data Fetching**: SWR with caching
- **🎨 Modern UI**: Tailwind CSS optimization
- **📱 Mobile First**: Responsive design principles
- **⚡ Code Splitting**: Dynamic imports

## 🔒 Security

- **🔐 Firebase Authentication**: Enterprise-grade security
- **🛡️ API Security**: OAuth 2.0 + Rate limiting
- **💾 Encrypted Storage**: EncryptedSharedPreferences
- **🌐 HTTPS Only**: Secure data transmission
- **🔒 Input Validation**: Comprehensive sanitization
- **👤 Role-Based Access**: Admin-only dashboard access

## 📄 Documentation

- **📱 [Android App Documentation](hayu-widyas-app/README.md)**
- **🌐 [Web Dashboard Documentation](hayu-widyas-dashboard/README.md)**
- **🔧 Setup guides included in each README**
- **📊 API documentation in code comments**
- **🎯 Architecture decision records**

## 🤝 Contributing

1. Fork the repository
2. Create feature branch: `git checkout -b feature/amazing-feature`
3. Commit changes: `git commit -m 'Add amazing feature'`
4. Push to branch: `git push origin feature/amazing-feature`
5. Open Pull Request

## 📄 License

Copyright © 2024 Hayu Widyas. All rights reserved.

This project is proprietary software developed for Hayu Widyas luxury leather bag brand.

## 🏆 Production Ready

This system is built to **production standards** and includes:

- ✅ **Complete Architecture**: No placeholder code
- ✅ **Comprehensive Documentation**: Setup guides and API docs
- ✅ **Security Implementation**: Enterprise-grade protection
- ✅ **Performance Optimization**: Fast and scalable
- ✅ **Error Handling**: Robust error management
- ✅ **Testing Structure**: Unit and integration tests
- ✅ **Deployment Ready**: CI/CD friendly configuration
- ✅ **Monitoring Setup**: Analytics and crash reporting
- ✅ **Scalable Design**: Built for growth

---

**Built with ❤️ for luxury craftsmanship and modern technology** ✨

*Representing the perfect fusion of traditional leather artistry with cutting-edge mobile and web technology.*
