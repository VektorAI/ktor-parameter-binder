# Ktor Parameter Binder
[![Release](https://jitpack.io/v/VektorAI/ktor-parameter-binder.svg)](https://jitpack.io/#VektorAI/ktor-parameter-binder)

An extension library that provides parameter binding ability to the ktor.ai micro framework

## Usage

Define handler with parameter binding annotations:
```kotlin
fun handler(
    @QueryParam("param") a: Int,
    @HeaderParam("Custom-Header") b: Double,
    @Body input: Input
): String {
    log.info("${input.message} = ${a * b}")
    return "${input.message} = ${a * b}"
}
```

Create parameter binder, setup ktor server and register handler in the routing function:
```kotlin
val binder = ParamBinder()
embeddedServer(Netty, port = 8089) {
        routing {
            registerHandler(handler, binder)
        }
        install(ContentNegotiation) {
            jackson()
        }
    }.start(wait = true)
```
Send request to a server to check result:
```bash
$ curl -XPOST 'localhost:8089/one?param=99' -H "Content-Type: application/json" -d '{"message":"result"}' -H "Custom-Header: 3"
result = 297.0
```

## Examples
Take a look at the [sample directory](https://github.com/VektorAI/ktor-parameter-binder/tree/master/samples).

This directory contains a demo app which will help you to experiment with available features.

## Download
+ Add the JitPack repository to your root build.gradle file:

```gradle
repositories {
    maven { url "https://jitpack.io" }
}
```

+ Add the code to your **module**'s build.gradle file:

```gradle
dependencies {
    implementation 'com.github.VektorAI.ktor-parameter-binder:binder:x.y.z'
}
```

## Contributing

1. **Fork** and **clone** the repo
2. Run `./gradlew ktlintFormat`
3. Run `./gradlew build` to see if tests and [ktlint](https://github.com/pinterest/ktlint) pass.
4. **Commit** and **push** your changes
5. Submit a **pull request** to get your changes reviewed

## License
Ktor Parameter Binder is under the Apache 2.0 license. See the [LICENSE](LICENSE) for more information.