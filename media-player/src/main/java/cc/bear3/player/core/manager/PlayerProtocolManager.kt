package cc.bear3.player.core.manager

object PlayerProtocolManager {
    var protocol : IPlayerProtocol = DefaultPlayerProtocol()
}

interface IPlayerProtocol {
    fun getApplicationId() : String
}

class DefaultPlayerProtocol : IPlayerProtocol {
    override fun getApplicationId(): String {
        return "cc.bear3.player"
    }
}