package com.chloformLife

import org.jsoup.Jsoup

class EatRightBasketScraper: PriceScraper() {
    override val source = "EatRightBasket"

    private val baseURL = "https://www.eatrightbasket.com/category/"
    private val categories = listOf("veggies", "fruit", "pulses-cereals",
        "flour-atta", "ghee-oil", "spices", "salt-sugar", "dry-fruits",
        "honey", "pickle", "healthy-snacks", "herbal-products", "personal-care")

    override fun getPrices() = sequence {
        for (category in categories) {
            val doc = Jsoup.connect(baseURL + category).get()
            val productInfoList = doc.select(".product-info")

            for (prodInfo in productInfoList) {
                val productName = prodInfo.select(".product_title").eachText()[0]
                val price = prodInfo.select(".price").eachText()[0]

                val productInfo = ProductInfo(productName, price,
                    source)
                yield(productInfo)
            }

            println("Finished $category")
        }
    }
}

fun main() {
    EatRightBasketScraper().scrapeAndWriteCSV("/Users/saisatch/EatRightBasketPrices.csv")
}