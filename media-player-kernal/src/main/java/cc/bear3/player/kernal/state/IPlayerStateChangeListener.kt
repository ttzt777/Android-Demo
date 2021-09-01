package cc.bear3.player.kernal.state

/**
 *
 * @author TT
 * @since 2021-5-31
 */
interface IPlayerStateChangeListener {
    fun onPlayerStateChanged(playerState: PlayerState)
}