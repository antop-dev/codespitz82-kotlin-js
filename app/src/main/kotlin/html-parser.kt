abstract class Node(val parent: Node?)

class Element(val tagName: String, parent: Node?) : Node(parent) {
    val attributes = mutableMapOf<String, String>()
    val children = mutableListOf<Node>()
}

class TextNode(val text: String, parent: Node?) : Node(parent)

fun parseHTML(v: String) = parse(Element("root", null), v)

var rex = """<([a-zA-Z0-9]+)((?:\s+[a-zA-Z-]+(?:\s*=\s*"[^"]*")?)*)\s*/?""".toRegex()

tailrec fun parse(parent: Node, v: String): Node {
    println("parse → ${v.replace("\n", "")}")
    if (v.trim().isEmpty()) return parent
    if (v[0] != '<') { //
        val next = v.indexOf('<') // 다음 '<' 문자의 위치를 찾는다
        val text = v.substring(0, next.takeIf { it >= 0 } ?: v.length)
        if (text.trim().isNotBlank()) {
            // '<' 문자가 있으면 그 전까지 text. 없으면 전체가 text
            (parent as Element).children += TextNode(text, parent)
        }
        // 더이상 파싱할 내용이 없다. parent 리턴하고 끝
        // 아니면 text 이후의 '<' 부터 재귀 파싱
        return if (next == -1) parent else parse(parent, v.substring(next))
    } else { // Tag
        if (v[1] == '/') { // A의 닫는 태그
            val next = v.substring(1).indexOf('<')
            return if (parent.parent == null) parent
            else parse(parent.parent, v.substring(next))
        } else {
            val next = v.indexOf('>')
            // 태그 열림('<')부터 '>' 앞까지('>' 를 매칭 문자열에 포함시키지 않는다) 정규 표현식으로 태그명과 속성들을 찾아냄
            // 조건에 안맞으면 null
            // matches[0] : 원문
            // matches[1] : 태그명 ← 무조건 들어있음
            // matches[2] : 속성들 ← 없을 수 있음
            val matches = rex.matchEntire(v.substring(0, next))?.groupValues!!
            val el = Element(matches[1], parent)
            if (matches[2].isNotBlank()) {
                matches[2].trim().split(' ').forEach {
                    if (it.contains('=')) {
                        val kv = it.split('=').map { it.trim() }
                        el.attributes[kv[0]] = kv[1].replace("\"", "")
                    } else {
                        el.attributes[it] = "true"
                    }
                }
            }
            (parent as Element).children += el

            var isClose = v[next - 1] == '/'
            return parse(if (isClose) parent else el, v.substring(next + 1))
        }
    }

}