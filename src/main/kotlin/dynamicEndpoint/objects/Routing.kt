package dynamicEndpoint.objects

import io.ktor.http.HttpMethod
import io.ktor.server.application.*
import io.ktor.server.request.httpMethod
import io.ktor.server.request.path
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.pipeline.PipelineContext
import javax.script.ScriptEngineManager

object DynamicRoute {
    private val fns : MutableMap<Pair<String, HttpMethod>, String> = mutableMapOf()

    private val body: suspend PipelineContext<Unit, ApplicationCall>.(Unit) -> Unit = {
        val scriptEngine = ScriptEngineManager().getEngineByExtension("kts")
        val result = scriptEngine.eval(fns[(call.request.path() to call.request.httpMethod)])
        call.respondText(result.toString())
    }

    fun addFunction(path: String, http: HttpMethod, script: String) {
        fns[path to http] = script
    }

    fun createDynamicEndpoint(routing: Routing, path: String, http: HttpMethod) {
        routing.children.firstOrNull{ it.selector.toString() == path }?.let {
            return
        }

        routing {
            when (http) {
                HttpMethod.Get -> get("/$path", body)
                HttpMethod.Post -> post("/$path", body)
                HttpMethod.Put -> put("/$path", body)
                HttpMethod.Delete -> delete("/$path", body)
                HttpMethod.Patch -> patch("/$path", body)
                HttpMethod.Options -> options("/$path", body)
                else -> throw IllegalArgumentException("Unsupported HTTP method")
            }
        }
    }
}