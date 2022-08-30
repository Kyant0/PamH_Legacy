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
    append {
        val name = "peashooter"
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
                val animationIndex = 0
                val initialFrame = data.mainSprite.frame.indexOfFirst { it.label == animations[animationIndex] }
                var currentFrame = initialFrame
                val frames = mutableMapOf<Int, Change>()
                CoroutineScope(Dispatchers.Main).launch {
                    while (true) {
                        append {
                            div("frame_$name") {
                                val frame = data.mainSprite.frame[currentFrame]
                                frame.change.forEach { change ->
                                    frames[change.index] = change
                                }
                                frames.forEach { (index, change) ->
                                    val src = sprites[resMap.getValue(index)]
                                    val image = images.find { it.name == src }!!
                                    val transform =
                                        TransformData.parse(image.transform) then
                                            TransformData.parse(change.transform)
                                    style {
                                        unsafe {
                                            raw(".main_frame_${currentFrame}_$index { width: ${image.size[0] * image.transform[0]}px; height: ${image.size[1] * image.transform[3]}px; ${transform.toCssTransformString()} transform-origin: ${-image.transform[4]}px ${-image.transform[5]}px; }")
                                        }
                                    }
                                    img(
                                        src = src,
                                        classes = "image main_frame_${currentFrame}_$index"
                                    )
                                }
                            }
                        }
                        delay((1000f / data.frameRate).toLong())
                        repeat(2) {
                            window.document.querySelector(".frame_$name")?.remove()
                        }
                        currentFrame = initialFrame + (currentFrame + 1).mod(data.frameRate)
                    }
                }
            }
        }
    }
}
