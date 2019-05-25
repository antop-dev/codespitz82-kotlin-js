import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.KeyboardEvent
import kotlin.browser.document

fun main() {
    app()
}

fun app() {
    document.querySelector("#input")?.addEventListener("keyup", {
        // shield pattern
        if ((it as KeyboardEvent).keyCode != 13) return@addEventListener
        val input = it.target as HTMLInputElement
        val v = input.value

        document.querySelector("#result")?.innerHTML = "$v = ${calc(v)}"
    })
}

// (1 + 2) + 3 * (-1 / ( 1 - 0.5 ) )