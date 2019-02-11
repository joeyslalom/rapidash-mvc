package github.chickenbane

import github.chickenbane.mvc.rest.model.ItemResponse
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity

@ShellComponent
class RestClient(builder: RestTemplateBuilder) {
    val restTemplate: RestTemplate = builder.build()

    @ShellMethod("item")
    fun item(id: String): ItemResponse {
        val entity = restTemplate.getForEntity<ItemResponse>("http://localhost:8080/mvc/item/{id}", id, ItemResponse::class.java)
        return entity.body!!
    }

    @ShellMethod("item list")
    fun itemList(): List<String> {
        val entity = restTemplate.getForEntity<List<String>>("http://localhost:8080/mvc/items", List::class.java)
        return entity.body!!
    }

}