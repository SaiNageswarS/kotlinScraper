package com.chloformLife

import kong.unirest.Unirest
import kong.unirest.json.JSONObject


class BigBasketScraper: PriceScraper()  {
    override val source = "BigBasket"
    private val url = "https://www.bigbasket.com/product/get-products/?slug=organic&tab_type=[%22all%22]&sorted_on=relevance&listtype=ps"

    override fun getPrices() = sequence {
        for (page in 2..83) {
            val bigBasketResponse = Unirest.get(url).queryString("page", page).
                    asJson().body.`object`

            val priceList = bigBasketResponse.getJSONObject("tab_info")
                .getJSONObject("product_map").getJSONObject("all")
                .getJSONArray("prods")

            for (priceInfo in priceList) {
                priceInfo as JSONObject
                val productInfo = ProductInfo(
                    priceInfo.get("p_desc") as String,
                    priceInfo.get("sp") as String,
                    source
                )

                yield(productInfo)
            }
        }
    }
}

fun main() {
    BigBasketScraper().scrapeAndWriteCSV("/Users/saisatch/BigBasketPrices.csv")
}