package cc.bear3.player.core.state

/**
 *
 * @author TT
 * @since 2021-5-31
 */
interface IPlayerStateChangeListener {
    fun onPlayerStateChanged(playerState: PlayerState)
}