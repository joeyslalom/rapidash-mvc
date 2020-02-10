package github.chickenbane.integration

import github.chickenbane.mvc.rest.model.ItemResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder

@Component
class RestClient(restTemplateBuilder: RestTemplateBuilder) {

    private val restTemplate = restTemplateBuilder.build()

    @Value("\${rest.url}")
    lateinit var restUrl: String

    fun mvcItems(): List<String> {
        val uri = UriComponentsBuilder.fromHttpUrl(restUrl)
                .path("/mvc/items")
                .build()
                .toUri()

        val array = restTemplate.getForObject(uri, Array<String>::class.java) ?: arrayOf()
        return array.toList()
    }

    fun mvcItem(itemId: String): ItemResponse {
        val args = mapOf("itemId" to itemId)
        val uri = UriComponentsBuilder.fromHttpUrl(restUrl)
                .path("/mvc/item/{itemId}")
                .build(args)

        return restTemplate.getForObject(uri, ItemResponse::class.java)!!
    }
}
