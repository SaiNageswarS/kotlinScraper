package com.chloformLife

import java.io.File

abstract class PriceScraper {
    abstract fun getPrices(): Sequence<ProductInfo>
    abstract val source: String

    fun scrapeAndWriteCSV(path: String) {
        File(path)
            .printWriter().use { out ->
                getPrices().forEach {
                    out.println("${it.productName}, ${it.price}")
                }
            }
    }
}