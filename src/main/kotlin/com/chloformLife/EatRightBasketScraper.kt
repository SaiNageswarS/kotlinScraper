package com.chloformLife

import org.jsoup.Jsoup
import java.io.File

class EatRightBasketScraper {
    private val source = "EatRightBasket"
    private val baseURL = "https://www.eatrightbasket.com/category/"
    private val categories = listOf("veggies", "fruit", "pulses-cereals",
        "flour-atta", "ghee-oil", "spices", "salt-sugar", "dry-fruits",
        "honey", "pickle", "healthy-snacks", "herbal-products", "personal-care")

    fun getPrices(): List<ProductInfo> {
        val productPriceData = mutableListOf<ProductInfo>()

        for (category in categories) {
            val doc = Jsoup.connect(baseURL + category).get()
            val productInfoList = doc.select(".product-info")

            for (prodInfo in productInfoList) {
                val productName = prodInfo.select(".product_title").eachText()[0]
                val price = prodInfo.select(".price").eachText()[0]

                val productInfo = ProductInfo(productName, price,
                    source)
                productPriceData.add(productInfo)
            }

            println("Finished $category")
        }

        return productPriceData
    }
}

fun main() {
    val productInfoList = EatRightBasketScraper().getPrices()

    File("/Users/saisatch/EatRightBasketPrices.csv")
        .printWriter().use { out ->
            productInfoList.forEach {
                out.println("${it.productName}, ${it.price}")
            }
        }
}