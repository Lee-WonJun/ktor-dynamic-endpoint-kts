package dynamicEndpoint
import dynamicEndpoint.objects.DynamicRoute
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.PartData
import io.ktor.http.content.forEachPart
import io.ktor.http.content.streamProvider
import io.ktor.server.application.*
import io.ktor.server.request.receiveMultipart
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import java.io.File

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureRouting()
}

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        post("/dynamic-endpoint") {
            val multipart = call.receiveMultipart()

            var path: String? = null
            var httpMethod: HttpMethod? = null
            var scriptFile: String? = null

            multipart.forEachPart { part ->
                when (part) {
                    is PartData.FormItem -> {
                        if (part.name == "path") {
                            part.value.let { value ->
                                path = value
                            }
                        }
                        if (part.name == "httpMethod") {
                            part.value.let { value ->
                                httpMethod = HttpMethod(value)
                            }
                        }
                    }
                    is PartData.FileItem -> {
                        if (part.name == "file") {
                            val ext = File(part.originalFileName).extension
                            if (ext == "kts") {

                                part.streamProvider().readAllBytes().let { bytes ->
                                    val text = String(bytes)
                                    scriptFile = text
                                }
                            }
                        }
                    }
                    else -> Unit
                }
                part.dispose()
            }

            DynamicRoute.addFunction(path!!, httpMethod!!, scriptFile!!)
            DynamicRoute.createDynamicEndpoint(this@routing, path!!, httpMethod!!)

            call.respond(HttpStatusCode.OK)
        }
    }
}
