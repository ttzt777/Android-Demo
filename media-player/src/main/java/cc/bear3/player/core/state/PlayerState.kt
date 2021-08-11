package cc.bear3.player.core.state

/**
 *
 * @author TT
 * @since 2021-4-26
 */
enum class PlayerState {
    Idle,
    Confirm,
    Buffering,
    Playing,
    Paused,
    Ended,
    Error
}