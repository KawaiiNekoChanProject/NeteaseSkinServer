package net.nekocurit.netease_skin_server

import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.routing.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.asCoroutineDispatcher
import net.nekocurit.netease_skin_server.config.ConfigManager
import net.nekocurit.netease_skin_server.manager.account.AccountManager
import net.nekocurit.netease_skin_server.manager.cache.SkinCacheManager
import net.nekocurit.netease_skin_server.route.installBase
import net.nekocurit.netease_skin_server.route.routes.routeBase
import net.nekocurit.netease_skin_server.route.routes.routeDownload
import net.nekocurit.netease_skin_server.utils.addShutdownHook
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.concurrent.Executors

class NeteaseSkinServer {

    val dispatch = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2).asCoroutineDispatcher()
    val scope = CoroutineScope(SupervisorJob() + dispatch)

    val logger: Logger = LoggerFactory.getLogger("NeteaseSkinServer")

    val configManager = ConfigManager(this)
    val accountManager = AccountManager(this)
    val cacheManager = SkinCacheManager(this)

    suspend fun start() {
        configManager.readConfig()
        accountManager.login()

        addShutdownHook {
            accountManager.logout()
        }

        embeddedServer(factory = CIO, host = configManager.base.server.host, port = configManager.base.server.port) {
            routing {
                installBase()
                routeBase()
                routeDownload(this@NeteaseSkinServer)
            }
        }.start(wait = true)
    }
}