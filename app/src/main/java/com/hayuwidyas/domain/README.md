# Domain Layer

This directory contains the **Domain Layer** (Business Logic Layer) following Clean Architecture principles.

## Structure

```
domain/
├── model/          # Domain models/entities
├── repository/     # Repository interfaces
├── usecase/        # Use cases/interactors
└── util/          # Domain-specific utilities
```

## Purpose

The Domain Layer is responsible for:

- **Business Logic**: Core business rules and logic independent of frameworks
- **Use Cases**: Application-specific business rules and workflows
- **Domain Models**: Enterprise-wide business entities
- **Repository Contracts**: Interfaces for data access without implementation details
- **Business Validation**: Input validation and business rule enforcement

## Key Components

### Domain Models
Pure Kotlin data classes representing business entities:
- **Product**: Luxury leather bag products
- **CartItem**: Shopping cart items with quantity and customization
- **WishlistItem**: Saved products for later purchase
- **Order**: Customer orders with items and shipping details
- **User**: Customer account information
- **Category**: Product categorization

### Repository Interfaces
Contracts for data access without implementation details:
- **ProductRepository**: Product data operations
- **CartRepository**: Shopping cart management
- **WishlistRepository**: Wishlist operations
- **AuthRepository**: User authentication
- **OrderRepository**: Order management

### Use Cases
Single-responsibility business operations:
- **GetProductsUseCase**: Retrieve and filter products
- **GetProductDetailUseCase**: Get detailed product information
- **AddToCartUseCase**: Add items to shopping cart
- **AddToWishlistUseCase**: Save products to wishlist
- **PlaceOrderUseCase**: Process customer orders
- **AuthenticateUserUseCase**: Handle user authentication

## Design Principles

### Clean Architecture Rules
1. **Dependency Rule**: Dependencies point inward toward the domain
2. **Independence**: Domain layer is independent of UI, frameworks, and data sources
3. **Testability**: Business logic can be tested without external dependencies
4. **Separation of Concerns**: Each class has a single responsibility

### Use Case Pattern
```kotlin
class GetProductsUseCase(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(
        query: String = "",
        category: String? = null,
        sortBy: SortOption = SortOption.NEWEST
    ): Flow<Result<List<Product>>> {
        // Business logic here
        return repository.getProducts(query, category, sortBy)
    }
}
```

## Data Flow

1. **Presentation Layer** calls use case
2. **Use Case** applies business logic
3. **Use Case** calls repository interface
4. **Repository Implementation** (in data layer) executes operation
5. **Result** flows back through layers

## Best Practices

- ✅ Keep domain models pure (no Android/framework dependencies)
- ✅ Use sealed classes for representing different states
- ✅ Implement repository interfaces, not concrete classes
- ✅ Make use cases single-purpose and testable
- ✅ Use Result/Either types for error handling
- ✅ Validate business rules in use cases
- ✅ Keep use cases stateless
- ✅ Use dependency injection for repository interfaces

## Testing Strategy

- **Unit Tests**: Test use cases with mock repositories
- **Business Logic Tests**: Validate all business rules
- **Edge Case Tests**: Test error conditions and boundary cases
- **Integration Tests**: Test use case interactions