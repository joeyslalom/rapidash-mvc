package github.chickenbane.mvc.rest

import github.chickenbane.mvc.rest.api.MvcApi
import github.chickenbane.mvc.rest.model.ItemResponse
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller

@Controller
class ItemController : MvcApi {
    override fun getItem(id: String?): ResponseEntity<ItemResponse> {
        val response = ItemResponse()
                .message("best message")
                .numbers(listOf(12.toBigDecimal()))
        return ResponseEntity.ok(response)
    }

    override fun getListItemsIds(): ResponseEntity<List<String>> {
        return ResponseEntity.ok(listOf("abc", "def", "ghi"))
    }
}