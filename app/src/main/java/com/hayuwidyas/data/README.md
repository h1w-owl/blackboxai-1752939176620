# Data Layer

This directory contains the **Data Layer** implementation following Clean Architecture principles.

## Structure

```
data/
├── local/          # Local data sources (Room database, SharedPreferences)
├── remote/         # Remote data sources (API clients, DTOs)
├── repository/     # Repository implementations
└── mapper/         # Data mappers between DTOs and domain models
```

## Purpose

The Data Layer is responsible for:

- **Data Management**: Handling all data operations including network requests, database operations, and caching
- **Repository Pattern**: Implementing repository interfaces defined in the domain layer
- **Data Mapping**: Converting between different data representations (API DTOs ↔ Domain Models ↔ Database Entities)
- **Caching Strategy**: Managing local data storage and offline capabilities
- **Data Sources**: Abstracting data sources (remote APIs, local database, preferences)

## Key Components

### Local Data Sources
- **Room Database**: SQLite database for offline storage
- **DataStore**: Modern replacement for SharedPreferences
- **File Storage**: Local file management

### Remote Data Sources
- **WooCommerce API**: Product, order, and customer data
- **Firebase Services**: Authentication, storage, and real-time updates
- **Image CDN**: Product image management

### Repository Implementations
- **ProductRepositoryImpl**: Product data management with caching
- **CartRepositoryImpl**: Shopping cart operations
- **WishlistRepositoryImpl**: Wishlist management
- **AuthRepositoryImpl**: User authentication and session management

## Data Flow

1. **Repository** receives request from domain layer
2. **Repository** checks local cache first
3. If cache miss, **Repository** fetches from remote API
4. **Mapper** converts API response to domain model
5. **Repository** caches result locally
6. **Repository** returns domain model to use case

## Best Practices

- ✅ Always implement repository interfaces from domain layer
- ✅ Use mappers to convert between data models
- ✅ Implement proper error handling and network state management
- ✅ Cache data locally for offline support
- ✅ Use coroutines for asynchronous operations
- ✅ Handle network timeouts and retries
- ✅ Validate data before passing to domain layer