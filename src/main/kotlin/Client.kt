import com.kyant.pamh.data.Change
import com.kyant.pamh.data.PamData
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.html.div
import kotlinx.html.dom.append
import kotlinx.html.img
import kotlinx.html.style
import kotlinx.html.unsafe
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.w3c.dom.Node
import org.w3c.fetch.Request

fun main() {
    window.onload = { document.body?.body() }
}

private val json = Json {
    isLenient = true
    ignoreUnknownKeys = true
    coerceInputValues = true
}

fun Node.body() {
    val name = "peashooter"
    val animationIndex = 0
    append {
        window.fetch(Request("$name/$name.pam.json")).then { response ->
            response.text().then { text ->
                val data = json.decodeFromString<PamData>(text)
                val images = data.image.map {
                    it.copy(name = "./$name/${it.name.substringBefore("|")}.png")
                }
                val sprites = data.sprite.map {
                    images[it.frame[0].append[0].resource].name
                }
                val resMap = mutableMapOf<Int, Int>()
                data.mainSprite.frame.forEach { frame ->
                    resMap += frame.append.associate {
                        it.index to it.resource
                    }
                }
                val animations = data.mainSprite.frame.map { it.label }.filterNot { it == null }
                val startFrameIndex = data.mainSprite.frame.indexOfFirst {
                    it.label == animations[animationIndex]
                }
                val endFrameIndex = data.mainSprite.frame.indexOfFirst {
                    it.label == animations.getOrElse(animationIndex + 1) {}
                }.takeIf { it != -1 } ?: data.mainSprite.frame.lastIndex
                val frameCount = endFrameIndex - startFrameIndex - 1
                val initialFrame = data.mainSprite.frame.indexOfFirst { it.label == animations[animationIndex] }
                var localCurrentFrame = 0
                val frames = mutableMapOf<Int, Change>()
                CoroutineScope(Dispatchers.Main).launch {
                    while (true) {
                        val currentFrame = initialFrame + localCurrentFrame
                        append {
                            div("frame_$name") {
                                val frame = data.mainSprite.frame[currentFrame]
                                if (currentFrame == initialFrame) {
                                    frames.clear()
                                }
                                frame.change.forEach {
                                    if (frames.containsKey(it.index)) {
                                        frames[it.index] = it.copy(color = it.color ?: frames[it.index]?.color)
                                    } else {
                                        frames[it.index] = it
                                    }
                                }
                                frame.remove.forEach {
                                    frames.remove(it.index)
                                }
                                frames
                                    .filter { (_, change) -> change.color?.getOrNull(3) != 0.0 }
                                    .forEach { (index, change) ->
                                        val src = sprites[resMap.getValue(index)]
                                        val image = images.find { it.name == src }!!
                                        val transform =
                                            TransformData.parse(image.transform) then TransformData.parse(change.transform)
                                        style {
                                            unsafe {
                                                raw(".main_frame_${currentFrame}_$index { width: ${image.size[0] * image.transform[0]}px; height: ${image.size[1] * image.transform[3]}px; ${transform.toCssTransformString()} transform-origin: ${-image.transform[4]}px ${-image.transform[5]}px; filter: brightness(${change.color?.average() ?: 1}); }")
                                            }
                                        }
                                        img(
                                            src = src,
                                            classes = "image main_frame_${currentFrame}_$index"
                                        )
                                    }
                            }
                        }
                        delay(1000L / data.frameRate)
                        window.document.querySelector(".frame_$name")?.remove()
                        localCurrentFrame += 1
                        localCurrentFrame = localCurrentFrame.mod(frameCount)
                    }
                }
            }
        }
    }
}
