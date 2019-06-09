import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement
import org.w3c.dom.HTMLElement
import org.w3c.dom.css.CSSStyleDeclaration
import kotlin.browser.document

abstract class El(tagName: String) {
    protected val el = when (tagName) {
        "body" -> document.body ?: throw Throwable("no body")
        else -> document.createElement(tagName) as HTMLElement
    }

    var html: String
        get() = el.innerHTML
        set(value) {
            el.innerHTML = value
        }

    // el[key]
    operator fun get(key: String) = el.getAttribute(key) ?: ""

    // el[key] = value
    operator fun set(key: String, value: Any) = el.setAttribute(key, "$value")

    // el() = return el
    operator fun invoke() = el

    // el += child
    operator fun plusAssign(child: El) {
        // el.appendChild(child.el)
        el.appendChild(child()) // invoke
    }

    // el -= child
    operator fun minusAssign(child: El) {
        el.removeChild(child())
    }

    val style: CSSStyleDeclaration get() = el.style
}

object Body : El("body")
class Div : El("div")
class H2 : El("h2")
class Canvas : El("canvas") {
    val context: CanvasRenderingContext2D? get() = (el as? HTMLCanvasElement)?.getContext("2d") as? CanvasRenderingContext2D
}

val wrapper = Div()

fun htmlBuilder() {
    Body += wrapper
    wrapper += H2().apply {
        html = "HTML Builder" // getter
    }
    // for 0~5
    (0..5).map {
        Div().apply { html = "div-$it" }
    }.forEach {
        wrapper += it // plusAssign
    }

    wrapper += Canvas().apply {
        this["width"] = 500 // set
        this["height"] = 500 // set
        context?.run {
            // hide this keyword
            lineWidth = 10.0
            strokeRect(75.0, 140.0, 150.0, 110.0)
            fillRect(130.0, 190.0, 40.0, 60.0)
            moveTo(50.0, 140.0)
            lineTo(150.0, 60.0)
            lineTo(250.0, 140.0)
            closePath()
            stroke()
        }
    }

}