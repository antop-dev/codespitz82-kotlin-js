import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLTextAreaElement
import org.w3c.dom.events.KeyboardEvent
import kotlin.browser.document

fun main() {
    app()
    parser()
    htmlBuilder()
    testFetch()
}

fun app() {
    document.querySelector("#input")?.addEventListener("keyup", {
        // shield pattern
        if ((it as KeyboardEvent).keyCode != 13) return@addEventListener
        val input = it.target as HTMLInputElement
        val v = input.value

        document.querySelector("#result")?.innerHTML = "${calc(v)}"
    })
}

fun parser() {
    document.querySelector("#parse")?.addEventListener("click", {
        // parse
        val input = document.querySelector("#html") as HTMLTextAreaElement
        val v = input.value.trim()
        val parsed = parseHTML(v) as Element
        // print
        val printArea = document.querySelector("#parsed") as HTMLTextAreaElement
        printArea.value = ""
        printElement(printArea, parsed)
    })
}

fun printElement(printArea: HTMLTextAreaElement, node: Node, indent: Int = 0) {
    if (node is Element) {
        printArea.value += "${" ".repeat(indent)}Element <${node.tagName}>"
        if (node.attributes.isNotEmpty()) {
            printArea.value += " (${node.attributes.map { (k, v) -> "$k = '$v'" }.joinToString(", ")}})\n"
        } else {
            printArea.value += "\n"
        }
        node.children.forEach { printElement(printArea, it, indent + 2) }
    } else if (node is TextNode) {
        printArea.value += "${" ".repeat(indent)}TextNode '${node.text}'\n"
    }
}