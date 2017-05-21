package Model

import Observer.Observer
import java.awt.image.BufferedImage

interface Model {

    fun set(data: Array<Byte>): String?
    fun event(image: BufferedImage, info: MutableMap<String, Int>)

}