# Dependency Injection (DI) Layer

This directory contains the **Dependency Injection** setup using Dagger Hilt for managing dependencies across the application.

## Structure

```
di/
├── NetworkModule.kt       # Network-related dependencies (Retrofit, OkHttp)
├── DatabaseModule.kt      # Database dependencies (Room)
├── RepositoryModule.kt    # Repository implementations binding
├── FirebaseModule.kt      # Firebase services
├── UseCaseModule.kt       # Use case dependencies
└── AppModule.kt          # Application-level dependencies
```

## Purpose

The DI Layer is responsible for:

- **Dependency Management**: Providing and managing object dependencies
- **Singleton Management**: Ensuring single instances where needed
- **Configuration**: Setting up external libraries and services
- **Abstraction Binding**: Binding interfaces to implementations
- **Scope Management**: Managing dependency lifecycles

## Key Modules

### NetworkModule
Provides network-related dependencies:
```kotlin
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient
    
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit
    
    @Provides
    @Singleton
    fun provideWooCommerceApiService(retrofit: Retrofit): WooCommerceApiService
}
```

### DatabaseModule
Provides database dependencies:
```kotlin
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    
    @Provides
    @Singleton
    fun provideHayuWidyasDatabase(@ApplicationContext context: Context): HayuWidyasDatabase
    
    @Provides
    fun provideProductDao(database: HayuWidyasDatabase): ProductCacheDao
    
    @Provides
    fun provideCartDao(database: HayuWidyasDatabase): CartDao
}
```

### RepositoryModule
Binds repository interfaces to implementations:
```kotlin
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    
    @Binds
    abstract fun bindProductRepository(
        productRepositoryImpl: ProductRepositoryImpl
    ): ProductRepository
    
    @Binds
    abstract fun bindCartRepository(
        cartRepositoryImpl: CartRepositoryImpl
    ): CartRepository
}
```

### FirebaseModule
Provides Firebase services:
```kotlin
@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {
    
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth
    
    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore
    
    @Provides
    @Singleton
    fun provideFirebaseStorage(): FirebaseStorage
}
```

## Dependency Scopes

### Application Scope (@Singleton)
- Network clients (Retrofit, OkHttp)
- Database instances
- Firebase services
- Repository implementations
- Use cases

### Activity Scope (@ActivityScoped)
- ViewModels (automatically scoped by Hilt)
- Activity-specific services

### Fragment Scope (@FragmentScoped)
- Fragment-specific dependencies
- UI-related services

## Hilt Integration

### Application Class
```kotlin
@HiltAndroidApp
class HayuWidyasApplication : Application() {
    // Application setup
}
```

### ViewModels
```kotlin
@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val searchProductsUseCase: SearchProductsUseCase
) : ViewModel() {
    // ViewModel implementation
}
```

### Activities/Fragments
```kotlin
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    // Activity implementation
}
```

## Configuration Management

### Build Configuration
Access build-time configuration:
```kotlin
@Provides
@Named("base_url")
fun provideBaseUrl(): String = BuildConfig.WOOCOMMERCE_BASE_URL

@Provides
@Named("consumer_key")
fun provideConsumerKey(): String = BuildConfig.WOOCOMMERCE_CONSUMER_KEY
```

### Runtime Configuration
Manage runtime settings:
```kotlin
@Provides
@Singleton
fun provideAppPreferences(@ApplicationContext context: Context): AppPreferences
```

## Testing Support

### Test Modules
Create test-specific modules for testing:
```kotlin
@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [NetworkModule::class]
)
object TestNetworkModule {
    
    @Provides
    @Singleton
    fun provideMockApiService(): WooCommerceApiService = mockk()
}
```

## Best Practices

- ✅ Use appropriate scopes for dependencies
- ✅ Prefer constructor injection over field injection
- ✅ Use @Binds for interface implementations
- ✅ Use @Provides for external libraries
- ✅ Keep modules focused and cohesive
- ✅ Use qualifiers (@Named) for multiple implementations
- ✅ Provide mock implementations for testing
- ✅ Document complex dependency graphs

## Common Qualifiers

```kotlin
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IoDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DefaultDispatcher
```

## Error Handling

- Check for circular dependencies
- Verify scope compatibility
- Ensure all dependencies are provided
- Use proper component hierarchies
- Test dependency injection in isolation