package com.hayuwidyas.domain.usecase

import com.hayuwidyas.data.api.ApiResponse
import com.hayuwidyas.domain.model.DomainProduct
import com.hayuwidyas.domain.model.ProductFilter
import com.hayuwidyas.domain.model.ProductSortOption
import com.hayuwidyas.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Use case for fetching products with filtering and sorting
 */
class GetProductsUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    
    suspend operator fun invoke(
        page: Int = 1,
        perPage: Int = 20,
        filter: ProductFilter = ProductFilter(),
        sortOption: ProductSortOption = ProductSortOption.NEWEST
    ): Flow<ApiResponse<List<DomainProduct>>> {
        return productRepository.getProducts(
            page = page,
            perPage = perPage,
            orderBy = sortOption.orderBy,
            order = sortOption.order,
            category = filter.category,
            minPrice = filter.minPrice,
            maxPrice = filter.maxPrice,
            search = filter.search,
            featured = filter.featured
        ).map { response ->
            when (response) {
                is ApiResponse.Loading -> ApiResponse.Loading()
                is ApiResponse.Success -> {
                    val domainProducts = response.data.map { product ->
                        DomainProduct(
                            id = product.id,
                            name = product.name,
                            slug = product.slug,
                            permalink = product.permalink,
                            description = product.description,
                            shortDescription = product.shortDescription,
                            price = product.price,
                            regularPrice = product.regularPrice,
                            salePrice = product.salePrice,
                            onSale = product.onSale,
                            stockQuantity = product.stockQuantity,
                            stockStatus = product.stockStatus,
                            averageRating = product.averageRating,
                            ratingCount = product.ratingCount,
                            categories = product.categories.map { it.name },
                            images = product.images.map { it.src },
                            featured = product.featured,
                            variations = product.variations
                        )
                    }
                    ApiResponse.Success(domainProducts)
                }
                is ApiResponse.Error -> ApiResponse.Error(response.message, response.throwable)
            }
        }
    }
}