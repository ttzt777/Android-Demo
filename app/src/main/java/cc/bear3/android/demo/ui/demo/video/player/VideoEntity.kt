package cc.bear3.android.demo.ui.demo.video.player

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 *
 * @author TT
 * @since 2021-5-18
 */
@Parcelize
data class VideoEntity(
    val url: String,
    val width: Int = 0,
    val height: Int = 0,
    val imgUrl: String = ""
) : Parcelable

fun createVideoEntityList(): MutableList<VideoEntity> {
    val resultList = mutableListOf<VideoEntity>()

    with(resultList) {
        add(
            VideoEntity(
                "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4",
                640,
                360
            )
        )
        add(
            VideoEntity(
                "https://media.w3.org/2010/05/sintel/trailer.mp4",
                854,
                480
            )
        )
        add(
            VideoEntity(
                "http://vfx.mtime.cn/Video/2019/02/04/mp4/190204084208765161.mp4",
                1000,
                416
            )
        )
        add(
            VideoEntity(
                "http://vfx.mtime.cn/Video/2019/03/21/mp4/190321153853126488.mp4",
                1000,
                562
            )
        )
        add(
            VideoEntity(
                "http://vfx.mtime.cn/Video/2019/03/19/mp4/190319222227698228.mp4",
                1000,
                562
            )
        )
        add(
            VideoEntity(
                "http://vfx.mtime.cn/Video/2019/03/19/mp4/190319212559089721.mp4",
                1000,
                418
            )
        )
        add(
            VideoEntity(
                "http://vfx.mtime.cn/Video/2019/03/18/mp4/190318231014076505.mp4",
                1000,
                418
            )
        )
        add(
            VideoEntity(
                "http://vfx.mtime.cn/Video/2019/03/18/mp4/190318214226685784.mp4",
                1000,
                562
            )
        )
        add(
            VideoEntity(
                "http://vfx.mtime.cn/Video/2019/03/19/mp4/190319104618910544.mp4",
                1000,
                562
            )
        )
        add(
            VideoEntity(
                "http://vfx.mtime.cn/Video/2019/03/19/mp4/190319125415785691.mp4",
                1000,
                562
            )
        )
        add(
            VideoEntity(
                "http://vfx.mtime.cn/Video/2019/03/17/mp4/190317150237409904.mp4",
                1000,
                562
            )
        )
        add(
            VideoEntity(
                "http://vfx.mtime.cn/Video/2019/03/14/mp4/190314223540373995.mp4",
                1000,
                562
            )
        )
        add(
            VideoEntity(
                "http://vfx.mtime.cn/Video/2019/03/14/mp4/190314102306987969.mp4",
                1000,
                562
            )
        )
        add(
            VideoEntity(
                "http://vfx.mtime.cn/Video/2019/03/13/mp4/190313094901111138.mp4",
                1000,
                416
            )
        )
        add(
            VideoEntity(
                "http://vfx.mtime.cn/Video/2019/03/12/mp4/190312143927981075.mp4",
                1000,
                562
            )
        )
        add(
            VideoEntity(
                "http://vfx.mtime.cn/Video/2019/03/12/mp4/190312083533415853.mp4",
                1000,
                562
            )
        )
        add(
            VideoEntity(
                "http://vfx.mtime.cn/Video/2019/03/09/mp4/190309153658147087.mp4",
                1000,
                562
            )
        )
        add(
            VideoEntity(
                "https://vfx.mtime.cn/Video/2019/01/15/mp4/190115161611510728_480.mp4",
                480,
                270
            )
        )
        add(
            VideoEntity(
                "http://vjs.zencdn.net/v/oceans.mp4",
                960,
                400
            )
        )
        add(
            VideoEntity(
                "http://zkvideo-oss.myzaker.com/rgcms/201911/5ddcd82a1bc8e079430001e9_LD.mp4",
                640,
                1138
            )
        )
        // 貌似有点问题的源
        add(
            VideoEntity(
                "https://media.w3.org/2010/05/sintel/trailer.mp4",
                854,
                480
            )
        )
    }

    return resultList
}