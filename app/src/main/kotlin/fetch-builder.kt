import org.w3c.fetch.Request
import org.w3c.fetch.RequestInit
import kotlin.browser.window

class FetchParam {
    val queries = mutableMapOf<String, Any>()
    val headers = mutableMapOf<String, String>()
    var method = "GET"
}

fun fetch(url: String, block: FetchParam.() -> Unit) = FetchParam().apply {
    // block 람다는 생성된 FetchParam을 this로 받아서 인자를 변경할 수 있게 한다.
    block()
}.let {
    window.fetch(Request(url, RequestInit(
        // 기명 인자 호출?!
        method = it.method,
        headers = run {
            val obj = js("{}") // dynamic type
            it.headers.forEach { (k, v) ->
                obj[k] = v // javascript syntax
            }
            obj
        },
        body = if (it.method != "GET") it.queries.toList()
            .joinToString("&") { (k, v) ->
                // &로 조인을 하면서 값까지 변환한다.
                "$k=$v"
            }
        else null
    )))
}

fun testFetch() {
    fetch("test.json") {
        headers["X-Auth"] = "kotlin"
        queries["page"] = 1
        method = "POST"
    }.then {
        // success
        it.text()
    }.then {
        // fail
        println(it)
    }
}