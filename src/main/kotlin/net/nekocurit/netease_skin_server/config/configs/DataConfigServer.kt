package net.nekocurit.netease_skin_server.config.configs

import kotlinx.serialization.Serializable

@Serializable
data class DataConfigServer(
    val host: String = "127.0.0.1",
    val port: Int = 8080
)
