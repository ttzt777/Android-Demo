package cc.bear3.android.demo.ui.demo.video.core

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
    Stop,
    Error
}