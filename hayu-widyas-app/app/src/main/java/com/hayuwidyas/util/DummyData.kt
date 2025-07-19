package com.hayuwidyas.util

import com.hayuwidyas.data.model.*
import com.hayuwidyas.domain.model.Product

/**
 * Dummy Data Provider for Hayu Widyas
 * 
 * Provides realistic fallback data based on actual products from hayuwidyas.com
 * Used when WooCommerce API is unavailable or for testing purposes.
 */
object DummyData {

    /**
     * Dummy products based on real Hayu Widyas products
     */
    fun getDummyProducts(): List<ProductDto> {
        return listOf(
            ProductDto(
                id = 1,
                name = "HAYU WIDYAS Calais tas kulit asli berkualitas – Tas Wanita 28cm",
                slug = "hayu-widyas-calais-28cm",
                permalink = "https://hayuwidyas.com/product/hayu-widyas-calais-tas-kulit-asli-berkualitas-tas-wanita-28cm/",
                dateCreated = "2024-01-15T10:00:00",
                dateModified = "2024-01-15T10:00:00",
                type = "simple",
                status = "publish",
                featured = true,
                catalogVisibility = "visible",
                description = "Tas kulit asli berkualitas tinggi dengan desain elegan dan timeless. Dibuat dengan keahlian tangan Indonesia selama 14 tahun. Material crocodile leather premium dengan finishing yang sempurna.",
                shortDescription = "Luxury crocodile leather handbag, handcrafted in Indonesia",
                sku = "HW-CAL-28-001",
                price = "18000000",
                regularPrice = "18000000",
                salePrice = null,
                onSale = false,
                purchasable = true,
                totalSales = 45,
                virtual = false,
                downloadable = false,
                manageStock = true,
                stockQuantity = 3,
                stockStatus = "instock",
                backorders = "no",
                backordersAllowed = false,
                backordered = false,
                soldIndividually = true,
                weight = "0.8",
                dimensions = DimensionsDto("28", "20", "12"),
                shippingRequired = true,
                shippingTaxable = true,
                shippingClass = "",
                shippingClassId = 0,
                reviewsAllowed = true,
                averageRating = "4.8",
                ratingCount = 12,
                relatedIds = listOf(2, 3, 4),
                upsellIds = emptyList(),
                crossSellIds = emptyList(),
                parentId = 0,
                purchaseNote = "",
                categories = listOf(
                    CategoryDto(1, "Crocodile Series", "crocodile-series"),
                    CategoryDto(2, "Top Handle Bags", "top-handle-bags")
                ),
                tags = listOf(
                    TagDto(1, "Luxury", "luxury"),
                    TagDto(2, "Handmade", "handmade"),
                    TagDto(3, "Sultan", "sultan")
                ),
                images = listOf(
                    ImageDto(
                        id = 1,
                        dateCreated = "2024-01-15T10:00:00",
                        dateModified = "2024-01-15T10:00:00",
                        src = "https://hayuwidyas.com/wp-content/uploads/2025/07/ginee_20250718162858897_2728616238-300x300.jpg",
                        name = "Calais 28cm Front",
                        alt = "Hayu Widyas Calais Crocodile Leather Bag 28cm"
                    ),
                    ImageDto(
                        id = 2,
                        dateCreated = "2024-01-15T10:00:00",
                        dateModified = "2024-01-15T10:00:00",
                        src = "https://hayuwidyas.com/wp-content/uploads/2025/07/ginee_20250718162858828_1482019247-300x300.jpg",
                        name = "Calais 28cm Side",
                        alt = "Hayu Widyas Calais Side View"
                    )
                ),
                attributes = listOf(
                    AttributeDto(1, "Size", 0, true, false, listOf("28cm")),
                    AttributeDto(2, "Material", 1, true, false, listOf("Crocodile Leather")),
                    AttributeDto(3, "Color", 2, true, true, listOf("Black", "Brown", "Burgundy"))
                ),
                defaultAttributes = listOf(
                    DefaultAttributeDto(3, "Color", "Black")
                ),
                variations = listOf(101, 102, 103),
                groupedProducts = emptyList(),
                menuOrder = 0,
                metaData = emptyList()
            ),
            
            ProductDto(
                id = 2,
                name = "HAYU WIDYAS Kale Landscape tas kulit asli berkualitas – Tas Wanita 25cm",
                slug = "hayu-widyas-kale-landscape-25cm",
                permalink = "https://hayuwidyas.com/product/hayu-widyas-kale-landscape-tas-kulit-asli-berkualitas-tas-wanita-25cm-2/",
                dateCreated = "2024-01-10T10:00:00",
                dateModified = "2024-01-10T10:00:00",
                type = "simple",
                status = "publish",
                featured = true,
                catalogVisibility = "visible",
                description = "Tas kulit landscape dengan desain modern dan elegan. Cocok untuk penggunaan sehari-hari maupun acara formal. Material python leather berkualitas tinggi.",
                shortDescription = "Modern python leather landscape bag",
                sku = "HW-KAL-25-001",
                price = "14650000",
                regularPrice = "14650000",
                salePrice = null,
                onSale = false,
                purchasable = true,
                totalSales = 32,
                virtual = false,
                downloadable = false,
                manageStock = true,
                stockQuantity = 5,
                stockStatus = "instock",
                backorders = "no",
                backordersAllowed = false,
                backordered = false,
                soldIndividually = true,
                weight = "0.7",
                dimensions = DimensionsDto("25", "18", "10"),
                shippingRequired = true,
                shippingTaxable = true,
                shippingClass = "",
                shippingClassId = 0,
                reviewsAllowed = true,
                averageRating = "4.9",
                ratingCount = 8,
                relatedIds = listOf(1, 3, 5),
                upsellIds = emptyList(),
                crossSellIds = emptyList(),
                parentId = 0,
                purchaseNote = "",
                categories = listOf(
                    CategoryDto(3, "Python Series", "python-series"),
                    CategoryDto(4, "Shoulder Bags", "shoulder-bags")
                ),
                tags = listOf(
                    TagDto(1, "Luxury", "luxury"),
                    TagDto(2, "Handmade", "handmade"),
                    TagDto(4, "Daily Use", "daily-use")
                ),
                images = listOf(
                    ImageDto(
                        id = 3,
                        dateCreated = "2024-01-10T10:00:00",
                        dateModified = "2024-01-10T10:00:00",
                        src = "https://hayuwidyas.com/wp-content/uploads/2025/07/ginee_20250716165451242_4547345771-300x300.jpeg",
                        name = "Kale Landscape Front",
                        alt = "Hayu Widyas Kale Landscape Python Leather Bag"
                    ),
                    ImageDto(
                        id = 4,
                        dateCreated = "2024-01-10T10:00:00",
                        dateModified = "2024-01-10T10:00:00",
                        src = "https://hayuwidyas.com/wp-content/uploads/2025/07/ginee_20250716165451452_4691009833-300x300.jpeg",
                        name = "Kale Landscape Detail",
                        alt = "Hayu Widyas Kale Landscape Detail View"
                    )
                ),
                attributes = listOf(
                    AttributeDto(1, "Size", 0, true, false, listOf("25cm")),
                    AttributeDto(2, "Material", 1, true, false, listOf("Python Leather")),
                    AttributeDto(3, "Color", 2, true, true, listOf("Natural", "Black", "Brown"))
                ),
                defaultAttributes = listOf(
                    DefaultAttributeDto(3, "Color", "Natural")
                ),
                variations = listOf(201, 202, 203),
                groupedProducts = emptyList(),
                menuOrder = 0,
                metaData = emptyList()
            ),
            
            ProductDto(
                id = 3,
                name = "HAYU WIDYAS Hasselt Hz tas kulit asli berkualitas – Tas Wanita 39cm",
                slug = "hayu-widyas-hasselt-hz-39cm",
                permalink = "https://hayuwidyas.com/product/hayu-widyas-hasselt-hz-tas-kulit-asli-berkualitas-tas-wanita-39cm/",
                dateCreated = "2024-01-05T10:00:00",
                dateModified = "2024-01-05T10:00:00",
                type = "simple",
                status = "publish",
                featured = true,
                catalogVisibility = "visible",
                description = "Tas kulit berukuran besar dengan desain sophisticated. Perfect untuk traveling atau sebagai statement piece. Material lizard leather dengan craftsmanship terbaik.",
                shortDescription = "Large sophisticated lizard leather bag",
                sku = "HW-HAS-39-001",
                price = "24650000",
                regularPrice = "24650000",
                salePrice = null,
                onSale = false,
                purchasable = true,
                totalSales = 18,
                virtual = false,
                downloadable = false,
                manageStock = true,
                stockQuantity = 2,
                stockStatus = "instock",
                backorders = "no",
                backordersAllowed = false,
                backordered = false,
                soldIndividually = true,
                weight = "1.2",
                dimensions = DimensionsDto("39", "28", "15"),
                shippingRequired = true,
                shippingTaxable = true,
                shippingClass = "",
                shippingClassId = 0,
                reviewsAllowed = true,
                averageRating = "5.0",
                ratingCount = 5,
                relatedIds = listOf(1, 2, 4),
                upsellIds = emptyList(),
                crossSellIds = emptyList(),
                parentId = 0,
                purchaseNote = "",
                categories = listOf(
                    CategoryDto(5, "Lizard Series", "lizard-series"),
                    CategoryDto(6, "Travel Bags", "travel-bags")
                ),
                tags = listOf(
                    TagDto(1, "Luxury", "luxury"),
                    TagDto(2, "Handmade", "handmade"),
                    TagDto(3, "Sultan", "sultan"),
                    TagDto(5, "Travel", "travel")
                ),
                images = listOf(
                    ImageDto(
                        id = 5,
                        dateCreated = "2024-01-05T10:00:00",
                        dateModified = "2024-01-05T10:00:00",
                        src = "https://hayuwidyas.com/wp-content/uploads/2025/07/ginee_20250714104232587_8797003711-300x300.jpeg",
                        name = "Hasselt Hz Front",
                        alt = "Hayu Widyas Hasselt Hz Lizard Leather Bag 39cm"
                    ),
                    ImageDto(
                        id = 6,
                        dateCreated = "2024-01-05T10:00:00",
                        dateModified = "2024-01-05T10:00:00",
                        src = "https://hayuwidyas.com/wp-content/uploads/2025/07/ginee_20250714104232639_7607271006-300x300.jpeg",
                        name = "Hasselt Hz Interior",
                        alt = "Hayu Widyas Hasselt Hz Interior View"
                    )
                ),
                attributes = listOf(
                    AttributeDto(1, "Size", 0, true, false, listOf("39cm")),
                    AttributeDto(2, "Material", 1, true, false, listOf("Lizard Leather")),
                    AttributeDto(3, "Color", 2, true, true, listOf("Black", "Cognac"))
                ),
                defaultAttributes = listOf(
                    DefaultAttributeDto(3, "Color", "Black")
                ),
                variations = listOf(301, 302),
                groupedProducts = emptyList(),
                menuOrder = 0,
                metaData = emptyList()
            ),
            
            ProductDto(
                id = 4,
                name = "HAYU WIDYAS Herve Hz tas kulit asli berkualitas – Tas Wanita 30cm",
                slug = "hayu-widyas-herve-hz-30cm",
                permalink = "https://hayuwidyas.com/product/hayu-widyas-herve-hz-tas-kulit-asli-berkualitas-tas-wanita-30cm/",
                dateCreated = "2024-01-01T10:00:00",
                dateModified = "2024-01-01T10:00:00",
                type = "simple",
                status = "publish",
                featured = false,
                catalogVisibility = "visible",
                description = "Tas kulit dengan desain klasik yang tidak lekang oleh waktu. Cocok untuk berbagai occasion dengan kualitas material terbaik.",
                shortDescription = "Classic timeless leather handbag",
                sku = "HW-HER-30-001",
                price = "21500000",
                regularPrice = "21500000",
                salePrice = null,
                onSale = false,
                purchasable = true,
                totalSales = 25,
                virtual = false,
                downloadable = false,
                manageStock = true,
                stockQuantity = 4,
                stockStatus = "instock",
                backorders = "no",
                backordersAllowed = false,
                backordered = false,
                soldIndividually = true,
                weight = "0.9",
                dimensions = DimensionsDto("30", "22", "13"),
                shippingRequired = true,
                shippingTaxable = true,
                shippingClass = "",
                shippingClassId = 0,
                reviewsAllowed = true,
                averageRating = "4.7",
                ratingCount = 15,
                relatedIds = listOf(1, 2, 3),
                upsellIds = emptyList(),
                crossSellIds = emptyList(),
                parentId = 0,
                purchaseNote = "",
                categories = listOf(
                    CategoryDto(1, "Crocodile Series", "crocodile-series"),
                    CategoryDto(2, "Top Handle Bags", "top-handle-bags")
                ),
                tags = listOf(
                    TagDto(1, "Luxury", "luxury"),
                    TagDto(2, "Handmade", "handmade"),
                    TagDto(6, "Classic", "classic")
                ),
                images = listOf(
                    ImageDto(
                        id = 7,
                        dateCreated = "2024-01-01T10:00:00",
                        dateModified = "2024-01-01T10:00:00",
                        src = "https://hayuwidyas.com/wp-content/uploads/2025/07/ginee_20250711143925985_7560644664-300x300.jpeg",
                        name = "Herve Hz Front",
                        alt = "Hayu Widyas Herve Hz Crocodile Leather Bag 30cm"
                    ),
                    ImageDto(
                        id = 8,
                        dateCreated = "2024-01-01T10:00:00",
                        dateModified = "2024-01-01T10:00:00",
                        src = "https://hayuwidyas.com/wp-content/uploads/2025/07/ginee_20250711143926039_5523846646-300x300.jpeg",
                        name = "Herve Hz Side",
                        alt = "Hayu Widyas Herve Hz Side View"
                    )
                ),
                attributes = listOf(
                    AttributeDto(1, "Size", 0, true, false, listOf("30cm")),
                    AttributeDto(2, "Material", 1, true, false, listOf("Crocodile Leather")),
                    AttributeDto(3, "Color", 2, true, true, listOf("Black", "Brown"))
                ),
                defaultAttributes = listOf(
                    DefaultAttributeDto(3, "Color", "Black")
                ),
                variations = listOf(401, 402),
                groupedProducts = emptyList(),
                menuOrder = 0,
                metaData = emptyList()
            ),
            
            ProductDto(
                id = 5,
                name = "HAYU WIDYAS Calais tas kulit asli berkualitas – Tas Wanita 24cm",
                slug = "hayu-widyas-calais-24cm",
                permalink = "https://hayuwidyas.com/product/hayu-widyas-calais-tas-kulit-asli-berkualitas-tas-wanita-24cm/",
                dateCreated = "2023-12-28T10:00:00",
                dateModified = "2023-12-28T10:00:00",
                type = "simple",
                status = "publish",
                featured = false,
                catalogVisibility = "visible",
                description = "Versi compact dari seri Calais dengan kualitas yang sama. Perfect untuk daily use dengan style yang elegan.",
                shortDescription = "Compact elegant crocodile leather bag",
                sku = "HW-CAL-24-001",
                price = "14800000",
                regularPrice = "14800000",
                salePrice = null,
                onSale = false,
                purchasable = true,
                totalSales = 38,
                virtual = false,
                downloadable = false,
                manageStock = true,
                stockQuantity = 6,
                stockStatus = "instock",
                backorders = "no",
                backordersAllowed = false,
                backordered = false,
                soldIndividually = true,
                weight = "0.6",
                dimensions = DimensionsDto("24", "18", "10"),
                shippingRequired = true,
                shippingTaxable = true,
                shippingClass = "",
                shippingClassId = 0,
                reviewsAllowed = true,
                averageRating = "4.6",
                ratingCount = 22,
                relatedIds = listOf(1, 6, 7),
                upsellIds = emptyList(),
                crossSellIds = emptyList(),
                parentId = 0,
                purchaseNote = "",
                categories = listOf(
                    CategoryDto(1, "Crocodile Series", "crocodile-series"),
                    CategoryDto(7, "Mini Bags", "mini-bags")
                ),
                tags = listOf(
                    TagDto(1, "Luxury", "luxury"),
                    TagDto(2, "Handmade", "handmade"),
                    TagDto(4, "Daily Use", "daily-use")
                ),
                images = listOf(
                    ImageDto(
                        id = 9,
                        dateCreated = "2023-12-28T10:00:00",
                        dateModified = "2023-12-28T10:00:00",
                        src = "https://hayuwidyas.com/wp-content/uploads/2025/07/ginee_20250711143903500_1490068491-300x300.jpeg",
                        name = "Calais 24cm Front",
                        alt = "Hayu Widyas Calais Crocodile Leather Bag 24cm"
                    ),
                    ImageDto(
                        id = 10,
                        dateCreated = "2023-12-28T10:00:00",
                        dateModified = "2023-12-28T10:00:00",
                        src = "https://hayuwidyas.com/wp-content/uploads/2025/07/ginee_20250711143903576_0966219591-300x300.jpeg",
                        name = "Calais 24cm Detail",
                        alt = "Hayu Widyas Calais 24cm Detail View"
                    )
                ),
                attributes = listOf(
                    AttributeDto(1, "Size", 0, true, false, listOf("24cm")),
                    AttributeDto(2, "Material", 1, true, false, listOf("Crocodile Leather")),
                    AttributeDto(3, "Color", 2, true, true, listOf("Black", "Brown", "Navy"))
                ),
                defaultAttributes = listOf(
                    DefaultAttributeDto(3, "Color", "Black")
                ),
                variations = listOf(501, 502, 503),
                groupedProducts = emptyList(),
                menuOrder = 0,
                metaData = emptyList()
            )
        )
    }

    /**
     * Dummy categories based on Hayu Widyas website
     */
    fun getDummyCategories(): List<CategoryDto> {
        return listOf(
            CategoryDto(1, "Crocodile Series", "crocodile-series"),
            CategoryDto(2, "Top Handle Bags", "top-handle-bags"),
            CategoryDto(3, "Python Series", "python-series"),
            CategoryDto(4, "Shoulder Bags", "shoulder-bags"),
            CategoryDto(5, "Lizard Series", "lizard-series"),
            CategoryDto(6, "Travel Bags", "travel-bags"),
            CategoryDto(7, "Mini Bags", "mini-bags"),
            CategoryDto(8, "Cowhide Series", "cowhide-series"),
            CategoryDto(9, "Tote Bags", "tote-bags"),
            CategoryDto(10, "Clutch and Evening", "clutch-evening")
        )
    }

    /**
     * Convert ProductDto to Domain Product model
     */
    fun ProductDto.toDomainModel(): Product {
        return Product(
            id = this.id,
            name = this.name,
            description = this.description,
            shortDescription = this.shortDescription,
            price = this.price.toDoubleOrNull() ?: 0.0,
            regularPrice = this.regularPrice.toDoubleOrNull() ?: 0.0,
            salePrice = this.salePrice?.toDoubleOrNull(),
            onSale = this.onSale,
            stockStatus = this.stockStatus,
            stockQuantity = this.stockQuantity,
            images = this.images.map { it.src },
            categories = this.categories.map { it.name },
            tags = this.tags.map { it.name },
            averageRating = this.averageRating.toDoubleOrNull() ?: 0.0,
            ratingCount = this.ratingCount,
            featured = this.featured,
            sku = this.sku ?: "",
            weight = this.weight,
            dimensions = "${this.dimensions.length}x${this.dimensions.width}x${this.dimensions.height}",
            attributes = this.attributes.associate { it.name to it.options }
        )
    }
}
