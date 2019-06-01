# [CodeSpitz82] 코틀린JS로 배우는 코틀린 기초

그룹: https://www.facebook.com/groups/codespitz/

출석부(영상 + 교안): https://codespitz.com/s82/list.php

## 1일차

[코드스피츠82 코틀린JS로 배우는 코틀린 기초 - 1회차](https://www.youtube.com/watch?v=bGIPJHl2W1I)

* gradle 멀티 프로젝트 생성. `app` `lib`
    
    `settings.gradle`
    
    ```
    // 프로젝트를 만들기 전에 include를 먼저 해야 한다?
    include ':app'
    include ':lib'
    ```

* app 프로젝트에서 lib 프로젝트를 컴파일에 포함
    
    `app/build.gradle`
    
    ```
    dependencies {
        compile "org.jetbrains.kotlin:kotlin-stdlib-js:$kotlin_version"
        // lib 프로젝트 포함시키기
        compile project(":lib")
    }
    ```
    
* [Kotlin-DCE](https://kotlinlang.org/docs/reference/javascript-dce.html)

    gradle을 사용하는 이유가 Kotlin-DCE 때문이라고 한다.
    
    `app/build.gradle`
    
    ```
    apply plugin: 'kotlin-dce-js'
    ```
    
## 2일차

[코드스피츠82 코틀린JS로 배우는 코틀린 기초 - 2회차(1)](https://www.youtube.com/watch?v=9ZCk16-bFns)<br/>
[코드스피츠82 코틀린JS로 배우는 코틀린 기초 - 2회차(2)](https://www.youtube.com/watch?v=hkwiI4ebJpA)

* gradle + qunit 을 이용한 단위 테스트
* Kotlin Types
* 펑션의 마지막 인자가 람다일 경우 밖에다 쓰는게 가능!? 근데 이게 더 좋은건가? -_-
* 사칙연산 계산기
* 괄호를 계산하는 계산기

## 3일차

[코드스피츠82 코틀린JS로 배우는 코틀린 기초 - 3회차](https://www.youtube.com/watch?v=_EuTI8fmYDo)

* `Int`에 null을 넣을 수 없다. null을 허용하고싶으면 `Int?`로 선언
    ```kotlin
    var n:Int = null // 컴파일 에러
    var a:Int? = null // ok
    ```

* `Int`와 `Int?`는 다른 데이터 타입니다.
    ```kotlin
    val double:(Int)->Int = {it * 2}
    val n:Int = null
    double(n) // 컴파일 에러. Int를 인자로 넣어야하지만 Int?를 인자로 넣음
    ```
    
* Smart Cast
    ```kotlin
    val double:(Int)->Int = {it * 2}
    val n:Int = null
    // if에 의해서 변수 n이 null이 아닌 것으로 확정 되었기 때문에
    // Smart Cast 기능(?)에 의해 자동 변환된다.
    if (n != null) double(n) // ok
    ```
* inline function
  - 여러번 호출되는 펑션을 통쨰로 가져다가 나 코드의 라인으로 넣는다.
  - 함수 콜 비용이 사라진다. = 연산 비용 늘리고 메모리 비용 감소
  - 전체적인 바이너리의 크기가 커진다. = 연산 비용 줄고 메모리 비용 증가
  - 조건: 함수의 인자중 하나가 람다여야 한다.

* Extention function(extensions)
  - 클래스의 펑션을 상속 같은 방법이 아니라 바로 그냥 추가한다.
  - 마치 자바스크립트의 프로토타입 같은 건가?
    ```kotlin
    val v1:Int? = null
    v1?.let{ println("${double(v1)}") }
    val v2 = v1?.l et{double(it) } ?: 0 // ?: Elvis operator. null 이면 뒤의 값 사용
    ```

* typealias
  - 보기 힘든(?) 람다 타입에 별칭을 사용할 수 있다.
  - 마치 varchar(36)을 UUID 라는 도메인으로 사용하는 것처럼!?
    ```kotlin
    typealias Listener = (String) -> Unit
    ```
* [BNF 문법](https://ko.wikipedia.org/wiki/%EB%B0%B0%EC%BB%A4%EC%8A%A4-%EB%82%98%EC%9A%B0%EB%A5%B4_%ED%91%9C%EA%B8%B0%EB%B2%95)

* tailrec
  - 완전 재귀일 경우 for문으로 바뀐다. 단 현재 자바에서만 된다고 한다?
  - 리턴타입을 확정 지어줘야 한다.




