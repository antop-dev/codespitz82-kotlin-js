/// """...""" : no escaping, newlines
val cleanUp = """[^.\d-+*\/]""".toRegex()
val mulDiv = """((?:\+-)?[.\d]+)([*\/])((?:\+-)?[.\d]+)""".toRegex()
val paren = """\(([^()]*)\)""".toRegex()

fun ex(v: String) =
    v.replace(cleanUp, "").replace("-", "+-").replace(mulDiv) {
        // _ 변수 : 사용하지 않을 변수를 선언
        val (_, left, op, right) = it.groupValues // destructuring
        val l = left.replace("+", "").toDouble()
        val r = right.replace("+", "").toDouble()
        "${if (op == "*") l * r else l / r}".replace("-", "+-")
    }.split('+').fold(0.0) { sum, v ->
        sum + if (v.isBlank()) 0.0 else v.toDouble()
    }

fun calc(v: String): Double {
    var r = v // 인자로 들어온 변수는 final
    while (paren.containsMatchIn(r)) {
        println("--")
        println("cycle = $r")

        r = r.replace(paren) {
            println("() = ${it.groupValues[1]}")
            "${ex(it.groupValues[1])}"
            // 문자가 아닌 것(?)을 문자로 만드는 마법
            // ex) return 1 + "";
        }
        println("to = $r")
    }
    return ex(r)
}