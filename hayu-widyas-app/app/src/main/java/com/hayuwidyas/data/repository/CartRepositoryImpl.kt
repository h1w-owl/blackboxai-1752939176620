package com.hayuwidyas.data.repository

import com.hayuwidyas.data.local.dao.CartDao
import com.hayuwidyas.data.local.entity.CartItemEntity
import com.hayuwidyas.domain.model.CartItem
import com.hayuwidyas.domain.model.Product
import com.hayuwidyas.domain.repository.CartRepository
import com.hayuwidyas.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Cart Repository Implementation
 * 
 * Handles local cart storage and synchronization with Firebase
 */
@Singleton
class CartRepositoryImpl @Inject constructor(
    private val cartDao: CartDao
) : CartRepository {

    override fun getCartItems(): Flow<List<CartItem>> {
        return cartDao.getAllCartItems().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    override fun getCartItemsCount(): Flow<Int> {
        return cartDao.getCartItemsCount()
    }

    override fun getTotalCartQuantity(): Flow<Int> {
        return cartDao.getTotalCartQuantity().map { it ?: 0 }
    }

    override fun getCartTotalPrice(): Flow<Double> {
        return cartDao.getCartTotalPrice().map { it ?: 0.0 }
    }

    override suspend fun addToCart(
        product: Product,
        quantity: Int,
        selectedColor: String?,
        selectedSize: String?
    ): Resource<CartItem> {
        return try {
            val existingItem = cartDao.getCartItemByProductAndVariations(
                productId = product.id,
                selectedColor = selectedColor,
                selectedSize = selectedSize
            )

            if (existingItem != null) {
                // Update existing item quantity
                val newQuantity = existingItem.quantity + quantity
                cartDao.updateCartItemQuantity(existingItem.id, newQuantity)
                Resource.Success(existingItem.copy(quantity = newQuantity).toDomainModel())
            } else {
                // Create new cart item
                val cartItem = CartItemEntity(
                    id = "${product.id}_${selectedColor}_${selectedSize}",
                    productId = product.id,
                    productName = product.name,
                    productPrice = product.price,
                    productImage = product.primaryImage,
                    quantity = quantity,
                    selectedColor = selectedColor,
                    selectedSize = selectedSize
                )
                cartDao.insertCartItem(cartItem)
                Resource.Success(cartItem.toDomainModel())
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to add item to cart")
        }
    }

    override suspend fun updateCartItemQuantity(
        cartItemId: String,
        quantity: Int
    ): Resource<CartItem> {
        return try {
            cartDao.updateCartItemQuantity(cartItemId, quantity)
            val updatedItem = cartDao.getCartItemById(cartItemId)
            if (updatedItem != null) {
                Resource.Success(updatedItem.toDomainModel())
            } else {
                Resource.Error("Cart item not found")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to update cart item")
        }
    }

    override suspend fun incrementCartItemQuantity(cartItemId: String): Resource<CartItem> {
        return try {
            cartDao.incrementCartItemQuantity(cartItemId)
            val updatedItem = cartDao.getCartItemById(cartItemId)
            if (updatedItem != null) {
                Resource.Success(updatedItem.toDomainModel())
            } else {
                Resource.Error("Cart item not found")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to increment quantity")
        }
    }

    override suspend fun decrementCartItemQuantity(cartItemId: String): Resource<CartItem> {
        return try {
            cartDao.decrementCartItemQuantity(cartItemId)
            val updatedItem = cartDao.getCartItemById(cartItemId)
            if (updatedItem != null) {
                Resource.Success(updatedItem.toDomainModel())
            } else {
                Resource.Error("Cart item not found")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to decrement quantity")
        }
    }

    override suspend fun removeFromCart(cartItemId: String): Resource<Unit> {
        return try {
            cartDao.deleteCartItemById(cartItemId)
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to remove item from cart")
        }
    }

    override suspend fun removeProductFromCart(productId: Int): Resource<Unit> {
        return try {
            cartDao.deleteCartItemByProductId(productId)
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to remove product from cart")
        }
    }

    override suspend fun clearCart(): Resource<Unit> {
        return try {
            cartDao.clearCart()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to clear cart")
        }
    }

    override suspend fun isProductInCart(productId: Int): Boolean {
        return cartDao.isProductInCart(productId)
    }

    override suspend fun isVariationInCart(
        productId: Int,
        selectedColor: String?,
        selectedSize: String?
    ): Boolean {
        return cartDao.isVariationInCart(productId, selectedColor, selectedSize)
    }

    override suspend fun getCartItemByProductAndVariations(
        productId: Int,
        selectedColor: String?,
        selectedSize: String?
    ): CartItem? {
        return cartDao.getCartItemByProductAndVariations(productId, selectedColor, selectedSize)?.toDomainModel()
    }

    override suspend fun syncCartWithFirebase(userId: String): Resource<Unit> {
        // TODO: Implement Firebase sync
        return Resource.Success(Unit)
    }

    override suspend fun loadCartFromFirebase(userId: String): Resource<List<CartItem>> {
        // TODO: Implement Firebase load
        return Resource.Success(emptyList())
    }

    override suspend fun saveCartToFirebase(userId: String): Resource<Unit> {
        // TODO: Implement Firebase save
        return Resource.Success(Unit)
    }

    override suspend fun mergeCartWithFirebase(userId: String): Resource<List<CartItem>> {
        // TODO: Implement Firebase merge
        return Resource.Success(emptyList())
    }

    override suspend fun cleanupOldCartItems(olderThanDays: Int): Resource<Unit> {
        return try {
            val cutoffTime = System.currentTimeMillis() - (olderThanDays * 24 * 60 * 60 * 1000)
            cartDao.deleteOldCartItems(cutoffTime)
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to cleanup old cart items")
        }
    }

    override suspend fun getCartSummary(): CartRepository.CartSummary {
        val summary = cartDao.getCartSummary()
        return CartRepository.CartSummary(
            itemCount = summary?.itemCount ?: 0,
            totalQuantity = summary?.totalQuantity ?: 0,
            subtotal = summary?.totalPrice ?: 0.0,
            tax = 0.0, // TODO: Calculate tax based on location
            shipping = 0.0, // TODO: Calculate shipping based on location
            total = summary?.totalPrice ?: 0.0
        )
    }

    override suspend fun validateCartItems(): Resource<List<CartRepository.CartValidationResult>> {
        // TODO: Implement validation logic
        return Resource.Success(emptyList())
    }

    override suspend fun updateProductPricesInCart(): Resource<Unit> {
        // TODO: Implement price update logic
        return Resource.Success(Unit)
    }

    private fun CartItemEntity.toDomainModel(): CartItem {
        return CartItem(
            id = this.id,
            product = Product(
                id = this.productId,
                name = this.productName,
                description = "",
                shortDescription = "",
                price = this.productPrice,
                regularPrice = this.productPrice,
                salePrice = null,
                onSale = false,
                stockStatus = "instock",
                stockQuantity = null,
                images = listOf(this.productImage),
                categories = emptyList(),
                tags = emptyList(),
                averageRating = 0.0,
                ratingCount = 0,
                featured = false,
                sku = "",
                weight = null,
                dimensions = null,
                attributes = emptyMap()
            ),
            quantity = this.quantity,
            selectedColor = this.selectedColor,
            selectedSize = this.selectedSize,
            addedAt = this.addedAt
        )
    }
}
