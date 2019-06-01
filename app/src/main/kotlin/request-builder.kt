enum class Method { GET, POST }

typealias Listener = (String) -> Unit

class Request(
    val url: String,
    val method: Method,
    val form: MutableMap<String, String>?,
    val timeout: Int,
    val ok: Listener?,
    val fail: Listener?
)

class RequestBuilder(private val url: String) {
    var method: Method = Method.GET
    private val form = mutableMapOf<String, String>()
    var timeout = 0
    var ok: Listener? = null
    var fail: Listener? = null
    fun form(key: String, value: String) {
        this.form[key] = value
    }

    fun build() = Request(url, method, form.takeIf { it.isNotEmpty() }, timeout, ok, fail)
}

// 같은 이름의 클래스와 펑션이 존재할 수 있다.
fun RequestBuilder(url: String, block: RequestBuilder.() -> Unit) = RequestBuilder(url).apply(block).build()

val request = RequestBuilder("http://api.com") {
    method = Method.POST
    form("name", "hika")
    form("email", "antop@naver.com")
    timeout = 5_000
    ok = {}
    fail = {}
}
    