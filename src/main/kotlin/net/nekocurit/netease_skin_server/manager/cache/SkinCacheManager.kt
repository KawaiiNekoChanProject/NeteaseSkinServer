package net.nekocurit.netease_skin_server.manager.cache

import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsBytes
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import net.nekocurit.netease_skin_server.NeteaseSkinServer
import net.nekocurit.x19.extensions.X19SimplePlayerSkin
import java.io.File
import java.nio.file.StandardCopyOption.ATOMIC_MOVE
import java.nio.file.StandardCopyOption.REPLACE_EXISTING
import java.util.concurrent.ConcurrentHashMap

class SkinCacheManager(val system: NeteaseSkinServer) {

    private val locks = ConcurrentHashMap<ULong, Mutex>()

    val cacheDir = File("cache")
        .apply {
            mkdirs()
        }

    suspend fun getOrDownload(skin: X19SimplePlayerSkin.Detail): ByteArray {
        val file = File(cacheDir, "${skin.id}.png")
        if (file.isFile) return file.readBytes()

        return locks.getOrPut(skin.id) { Mutex() }.withLock {
            if (file.isFile) return@withLock file.readBytes()

            val bytes = downloadSkin(skin)
            writeCache(file, bytes)
            bytes
        }
    }

    private suspend fun downloadSkin(skin: X19SimplePlayerSkin.Detail): ByteArray {
        return system.accountManager.session.client.get(skin.url).bodyAsBytes()
    }

    private fun writeCache(file: File, bytes: ByteArray) {
        val tmp = File(file.parentFile, "${file.name}.tmp")

        tmp.writeBytes(bytes)
        java.nio.file.Files.move(
            tmp.toPath(),
            file.toPath(),
            REPLACE_EXISTING,
            ATOMIC_MOVE
        )
    }
}
