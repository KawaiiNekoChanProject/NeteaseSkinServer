package net.nekocurit.netease_skin_server.route.routes

import io.ktor.http.HttpStatusCode
import io.ktor.server.response.*
import io.ktor.server.routing.*

@Suppress("SpellCheckingInspection")
fun Route.routeBase() {
    get("/") {
        call.respondText("NteaseSkinServer is running..")
    }
    get("/robots.txt") {
        call.respondText("""
            User-agent: *
            Disallow: /    
        """.trimIndent())
    }
    get("/sitemap.xml") {
        call.respondText("""
            <?xml version="1.0" encoding="UTF-8"?>
            <urlset xmlns="https://www.sitemaps.org/schemas/sitemap/0.9"/>
        """.trimIndent())
    }
    get("/generate_204") {
        call.respond(HttpStatusCode.NoContent)
    }
}