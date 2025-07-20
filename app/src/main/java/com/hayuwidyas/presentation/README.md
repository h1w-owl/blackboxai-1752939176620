# Presentation Layer

This directory contains the **Presentation Layer** (UI Layer) following Clean Architecture principles and MVVM pattern.

## Structure

```
presentation/
├── ui/
│   ├── screen/         # Compose screens
│   ├── component/      # Reusable UI components
│   ├── theme/          # App theming (colors, typography, shapes)
│   └── navigation/     # Navigation setup
├── viewmodel/          # ViewModels for state management
└── util/              # UI utilities and extensions
```

## Purpose

The Presentation Layer is responsible for:

- **User Interface**: Jetpack Compose UI components and screens
- **State Management**: ViewModel pattern with StateFlow/LiveData
- **User Interactions**: Handling user input and navigation
- **UI State**: Managing loading, error, and success states
- **Data Binding**: Connecting UI with business logic through ViewModels

## Key Components

### Screens
Individual app screens built with Jetpack Compose:
- **OnboardingScreen**: App introduction and setup
- **AuthScreen**: Login and registration
- **HomeScreen**: Product catalog and navigation
- **ProductDetailScreen**: Detailed product information
- **CartScreen**: Shopping cart management
- **WishlistScreen**: Saved products
- **ProfileScreen**: User account management
- **CheckoutScreen**: Order placement and payment

### ViewModels
State management following MVVM pattern:
- **ProductViewModel**: Product listing and search
- **ProductDetailViewModel**: Single product details
- **CartViewModel**: Shopping cart operations
- **WishlistViewModel**: Wishlist management
- **AuthViewModel**: User authentication
- **HomeViewModel**: Dashboard and navigation

### UI Components
Reusable Compose components:
- **ProductCard**: Product display component
- **CustomButton**: Branded button styles
- **LoadingIndicator**: Loading states
- **ErrorMessage**: Error display
- **SearchBar**: Product search interface
- **BottomNavigation**: Main navigation

### Theme System
Material 3 theming for Hayu Widyas brand:
- **Colors**: Luxury brand color palette (Black, White, Pink #ED1B76)
- **Typography**: Elegant font system
- **Shapes**: Rounded corners and elevation
- **Components**: Styled Material 3 components

## Architecture Pattern

### MVVM (Model-View-ViewModel)
```kotlin
@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(ProductUiState())
    val uiState: StateFlow<ProductUiState> = _uiState.asStateFlow()
    
    fun loadProducts() {
        viewModelScope.launch {
            getProductsUseCase().collect { result ->
                _uiState.value = when (result) {
                    is Success -> _uiState.value.copy(
                        products = result.data,
                        isLoading = false
                    )
                    is Error -> _uiState.value.copy(
                        error = result.message,
                        isLoading = false
                    )
                }
            }
        }
    }
}
```

### UI State Management
```kotlin
data class ProductUiState(
    val products: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val searchQuery: String = "",
    val selectedCategory: String? = null
)
```

## Navigation

Using Jetpack Navigation Compose:
- **NavHost**: Main navigation controller
- **Screen Sealed Class**: Type-safe screen definitions
- **Deep Links**: Support for external navigation
- **Nested Navigation**: Tab and drawer navigation

## State Management Principles

1. **Unidirectional Data Flow**: Data flows down, events flow up
2. **Single Source of Truth**: ViewModel holds the UI state
3. **Immutable State**: UI state objects are immutable
4. **Reactive UI**: UI recomposes based on state changes

## Best Practices

- ✅ Use StateFlow for observable state in ViewModels
- ✅ Collect state in Compose using collectAsStateWithLifecycle()
- ✅ Handle all UI states (loading, success, error, empty)
- ✅ Keep ViewModels free of Android framework dependencies
- ✅ Use sealed classes for navigation and UI events
- ✅ Implement proper error handling and user feedback
- ✅ Follow Material 3 design guidelines
- ✅ Use Hilt for dependency injection
- ✅ Write UI tests for critical user flows

## Performance Optimization

- **LazyColumn/LazyRow**: Efficient list rendering
- **Image Loading**: Coil for optimized image loading
- **State Hoisting**: Lift state to appropriate levels
- **Recomposition Optimization**: Stable parameters and remember
- **Memory Management**: Proper lifecycle handling in ViewModels

## Testing Strategy

- **Unit Tests**: ViewModel logic and state transformations
- **UI Tests**: Compose testing with ComposeTestRule
- **Integration Tests**: Screen-level user interaction flows
- **Screenshot Tests**: Visual regression testing