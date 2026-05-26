package net.nekocurit.netease_skin_server.route

import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.calllogging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import net.nekocurit.netease_skin_server.route.error.ServerRestException
import net.nekocurit.netease_skin_server.route.error.ServerRestNotFoundException.Companion.notFound
import org.slf4j.LoggerFactory
import org.slf4j.event.Level

private val logger = LoggerFactory.getLogger("NeteaseSkinServer")

fun Application.installBase() {
    install(ContentNegotiation) {
        json()
    }
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            when (cause) {
                is ServerRestException -> call.respond(cause.code, cause)
                else -> {
                    logger.error("Unhandled request error", cause)
                    call.respond(HttpStatusCode.InternalServerError, "Internal Server Error")
                }
            }
        }
        status(HttpStatusCode.NotFound) { _, _ ->
            notFound()
        }
    }
    install(CallLogging) {
        level = Level.INFO
        format { call ->
            "${ call.request.httpMethod.value} ${call.request.uri} ${call.request.getRemoteAddress()}"
        }
    }
}

fun ApplicationRequest.getRemoteAddress() = "${call.request.local.remoteHost}:${call.request.local.remotePort}"
