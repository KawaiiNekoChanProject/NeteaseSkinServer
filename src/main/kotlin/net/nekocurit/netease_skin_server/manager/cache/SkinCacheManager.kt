package net.nekocurit.netease_skin_server.manager.cache

import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsBytes
import net.nekocurit.netease_skin_server.NeteaseSkinServer
import net.nekocurit.x19.extensions.X19SimplePlayerSkin
import java.io.File

class SkinCacheManager(val system: NeteaseSkinServer) {

    val cacheDir = File("cache")
        .apply {
            mkdirs()
        }

    suspend fun getOrDownload(skin: X19SimplePlayerSkin.Detail) = File(cacheDir, "${skin.id}.png")
        .let { file ->
            file.takeIf { it.isFile }
                ?.readBytes()
                ?:system.accountManager.session.client.get(skin.url).bodyAsBytes()
                    .also { file.writeBytes(it) }
        }
}