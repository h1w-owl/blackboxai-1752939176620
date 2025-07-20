package com.hayuwidyas.domain.usecase

import com.hayuwidyas.data.api.ApiResponse
import com.hayuwidyas.domain.model.DomainProduct
import com.hayuwidyas.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Use case for fetching a single product by ID
 */
class GetProductUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    
    suspend operator fun invoke(productId: Int): Flow<ApiResponse<DomainProduct>> {
        return productRepository.getProduct(productId).map { response ->
            when (response) {
                is ApiResponse.Loading -> ApiResponse.Loading()
                is ApiResponse.Success -> {
                    val product = response.data
                    val domainProduct = DomainProduct(
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
                    ApiResponse.Success(domainProduct)
                }
                is ApiResponse.Error -> ApiResponse.Error(response.message, response.throwable)
            }
        }
    }
}