package com.hayuwidyas.domain.usecase

import com.hayuwidyas.domain.model.Product
import com.hayuwidyas.domain.model.Category
import com.hayuwidyas.domain.repository.ProductRepository
import com.hayuwidyas.util.Result
import io.mockk.*
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

class GetProductsUseCaseTest {

    private lateinit var productRepository: ProductRepository
    private lateinit var getProductsUseCase: GetProductsUseCase

    private val mockProducts = listOf(
        Product(
            id = 1,
            name = "Classic Leather Handbag",
            slug = "classic-leather-handbag",
            description = "Premium handcrafted leather handbag",
            shortDescription = "Classic elegance",
            price = 1500000.0,
            regularPrice = 1500000.0,
            salePrice = null,
            sku = "CLH-001",
            stockQuantity = 10,
            stockStatus = "instock",
            manageStock = true,
            categories = listOf(Category(1, "Handbags", "handbags")),
            images = listOf("https://example.com/image1.jpg"),
            featured = true,
            status = "publish",
            dateCreated = "2024-01-01T00:00:00",
            dateModified = "2024-01-01T00:00:00"
        ),
        Product(
            id = 2,
            name = "Executive Briefcase",
            slug = "executive-briefcase",
            description = "Professional leather briefcase",
            shortDescription = "Business professional",
            price = 2000000.0,
            regularPrice = 2000000.0,
            salePrice = null,
            sku = "EB-002",
            stockQuantity = 5,
            stockStatus = "instock",
            manageStock = true,
            categories = listOf(Category(2, "Briefcases", "briefcases")),
            images = listOf("https://example.com/image2.jpg"),
            featured = false,
            status = "publish",
            dateCreated = "2024-01-02T00:00:00",
            dateModified = "2024-01-02T00:00:00"
        )
    )

    @Before
    fun setup() {
        productRepository = mockk()
        getProductsUseCase = GetProductsUseCase(productRepository)
    }

    @Test
    fun `invoke with empty query returns all products successfully`() = runTest {
        // Given
        every { productRepository.getProducts(any(), any(), any()) } returns 
            flowOf(Result.Success(mockProducts))

        // When
        val result = getProductsUseCase().toList()

        // Then
        assertEquals(1, result.size)
        assertTrue(result[0] is Result.Success)
        assertEquals(mockProducts, (result[0] as Result.Success).data)
        
        verify { productRepository.getProducts("", null, any()) }
    }

    @Test
    fun `invoke with search query filters products correctly`() = runTest {
        // Given
        val query = "briefcase"
        val filteredProducts = listOf(mockProducts[1]) // Only briefcase
        every { productRepository.getProducts(query, any(), any()) } returns 
            flowOf(Result.Success(filteredProducts))

        // When
        val result = getProductsUseCase(query).toList()

        // Then
        assertEquals(1, result.size)
        assertTrue(result[0] is Result.Success)
        assertEquals(filteredProducts, (result[0] as Result.Success).data)
        
        verify { productRepository.getProducts(query, null, any()) }
    }

    @Test
    fun `invoke with category filter returns category products`() = runTest {
        // Given
        val categorySlug = "handbags"
        val categoryProducts = listOf(mockProducts[0]) // Only handbag
        every { productRepository.getProducts(any(), categorySlug, any()) } returns 
            flowOf(Result.Success(categoryProducts))

        // When
        val result = getProductsUseCase(category = categorySlug).toList()

        // Then
        assertEquals(1, result.size)
        assertTrue(result[0] is Result.Success)
        assertEquals(categoryProducts, (result[0] as Result.Success).data)
        
        verify { productRepository.getProducts("", categorySlug, any()) }
    }

    @Test
    fun `invoke with query and category combines filters`() = runTest {
        // Given
        val query = "leather"
        val categorySlug = "handbags"
        val filteredProducts = listOf(mockProducts[0])
        every { productRepository.getProducts(query, categorySlug, any()) } returns 
            flowOf(Result.Success(filteredProducts))

        // When
        val result = getProductsUseCase(query, categorySlug).toList()

        // Then
        assertEquals(1, result.size)
        assertTrue(result[0] is Result.Success)
        assertEquals(filteredProducts, (result[0] as Result.Success).data)
        
        verify { productRepository.getProducts(query, categorySlug, any()) }
    }

    @Test
    fun `invoke with different sort options calls repository correctly`() = runTest {
        // Given
        val sortOptions = listOf(
            SortOption.NEWEST,
            SortOption.OLDEST,
            SortOption.PRICE_LOW_TO_HIGH,
            SortOption.PRICE_HIGH_TO_LOW,
            SortOption.NAME_A_TO_Z,
            SortOption.NAME_Z_TO_A
        )

        sortOptions.forEach { sortOption ->
            every { productRepository.getProducts(any(), any(), sortOption) } returns 
                flowOf(Result.Success(mockProducts))

            // When
            val result = getProductsUseCase(sortBy = sortOption).toList()

            // Then
            assertEquals(1, result.size)
            assertTrue(result[0] is Result.Success)
            
            verify { productRepository.getProducts("", null, sortOption) }
            clearMocks(productRepository, answers = false)
        }
    }

    @Test
    fun `invoke returns error when repository fails`() = runTest {
        // Given
        val exception = RuntimeException("Network error")
        every { productRepository.getProducts(any(), any(), any()) } returns 
            flowOf(Result.Error(exception))

        // When
        val result = getProductsUseCase().toList()

        // Then
        assertEquals(1, result.size)
        assertTrue(result[0] is Result.Error)
        assertEquals(exception, (result[0] as Result.Error).exception)
    }

    @Test
    fun `invoke returns loading state when repository is loading`() = runTest {
        // Given
        every { productRepository.getProducts(any(), any(), any()) } returns 
            flowOf(Result.Loading)

        // When
        val result = getProductsUseCase().toList()

        // Then
        assertEquals(1, result.size)
        assertTrue(result[0] is Result.Loading)
    }

    @Test
    fun `invoke handles empty result list`() = runTest {
        // Given
        every { productRepository.getProducts(any(), any(), any()) } returns 
            flowOf(Result.Success(emptyList()))

        // When
        val result = getProductsUseCase().toList()

        // Then
        assertEquals(1, result.size)
        assertTrue(result[0] is Result.Success)
        assertTrue((result[0] as Result.Success).data.isEmpty())
    }

    @Test
    fun `invoke validates input parameters`() = runTest {
        // Given
        val query = "   "  // Whitespace only
        val trimmedQuery = ""
        every { productRepository.getProducts(trimmedQuery, any(), any()) } returns 
            flowOf(Result.Success(mockProducts))

        // When
        val result = getProductsUseCase(query).toList()

        // Then
        verify { productRepository.getProducts(trimmedQuery, null, any()) }
    }

    @Test
    fun `invoke with null category passes null to repository`() = runTest {
        // Given
        every { productRepository.getProducts(any(), null, any()) } returns 
            flowOf(Result.Success(mockProducts))

        // When
        val result = getProductsUseCase(category = null).toList()

        // Then
        verify { productRepository.getProducts("", null, any()) }
    }

    @Test
    fun `invoke handles repository throwing exception`() = runTest {
        // Given
        val exception = IllegalStateException("Repository error")
        every { productRepository.getProducts(any(), any(), any()) } throws exception

        // When & Then
        try {
            getProductsUseCase().toList()
            fail("Expected exception to be thrown")
        } catch (e: IllegalStateException) {
            assertEquals("Repository error", e.message)
        }
    }

    @Test
    fun `invoke with long query string handles correctly`() = runTest {
        // Given
        val longQuery = "a".repeat(1000)
        every { productRepository.getProducts(longQuery, any(), any()) } returns 
            flowOf(Result.Success(emptyList()))

        // When
        val result = getProductsUseCase(longQuery).toList()

        // Then
        assertEquals(1, result.size)
        assertTrue(result[0] is Result.Success)
        verify { productRepository.getProducts(longQuery, null, any()) }
    }

    @Test
    fun `invoke maintains flow behavior for multiple emissions`() = runTest {
        // Given
        every { productRepository.getProducts(any(), any(), any()) } returns 
            flowOf(
                Result.Loading,
                Result.Success(mockProducts)
            )

        // When
        val results = getProductsUseCase().toList()

        // Then
        assertEquals(2, results.size)
        assertTrue(results[0] is Result.Loading)
        assertTrue(results[1] is Result.Success)
        assertEquals(mockProducts, (results[1] as Result.Success).data)
    }
}