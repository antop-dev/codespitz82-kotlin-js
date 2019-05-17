# [CodeSpitz82] 코틀린JS로 배우는 코틀린 기초

그룹: https://www.facebook.com/groups/codespitz/

출석부: https://codespitz.com/s82/list.php

## 1일차

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