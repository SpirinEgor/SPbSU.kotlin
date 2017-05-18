package Observer

import java.awt.image.BufferedImage

interface Observer {

    fun update(image: BufferedImage, info: MutableMap<String, Int>)

}