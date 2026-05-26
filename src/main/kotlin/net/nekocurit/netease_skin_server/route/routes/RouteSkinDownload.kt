package net.nekocurit.netease_skin_server.route.routes

import io.ktor.server.response.*
import io.ktor.server.routing.*
import net.nekocurit.netease_skin_server.NeteaseSkinServer
import net.nekocurit.netease_skin_server.route.error.ServerRestBadRequestException
import net.nekocurit.netease_skin_server.route.error.ServerRestNotFoundException.Companion.notFound
import net.nekocurit.x19.extensions.getSkinFromUUID

private val uuidRegex = Regex("^[0-9a-fA-F]{8}-?[0-9a-fA-F]{4}-?[0-9a-fA-F]{4}-?[0-9a-fA-F]{4}-?[0-9a-fA-F]{12}$")

fun Route.routeDownload(system: NeteaseSkinServer) {
    get("download/{uuid}") {
        val uuid = call.parameters["uuid"] ?: notFound()
        if (!uuidRegex.matches(uuid)) throw ServerRestBadRequestException("Invalid uuid")

        val skin = system.accountManager.session.getSkinFromUUID(uuid)
        call.respond(system.cacheManager.getOrDownload(skin.java))
    }
}
