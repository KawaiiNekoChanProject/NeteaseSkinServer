package net.nekocurit.netease_skin_server.route.routes

import io.ktor.server.response.*
import io.ktor.server.routing.*
import net.nekocurit.netease_skin_server.NeteaseSkinServer
import net.nekocurit.x19.extensions.getSkinFromUUID

fun Route.routeDownload(system: NeteaseSkinServer) {
    get("download/{uuid}") {
        val uuid = call.parameters["uuid"]!!

        system.accountManager.session
            .let {
                val skin = it.getSkinFromUUID(uuid)
                call.respond(system.cacheManager.getOrDownload(skin.java))
            }
    }
}