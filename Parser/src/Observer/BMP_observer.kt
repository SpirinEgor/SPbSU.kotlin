package Observer

import java.awt.Canvas
import java.awt.Graphics
import java.awt.image.BufferedImage
import javax.swing.JFrame
import javax.swing.WindowConstants

class BMP_observer: Observer {

    override fun update(image: BufferedImage, info: MutableMap<String, Int>) {
        val frame = JFrame()
        frame.defaultCloseOperation = WindowConstants.DISPOSE_ON_CLOSE
        frame.setSize(image.width, image.height)
        frame.add(BMPCanvas(image))
        frame.isVisible = true
    }
}

class BMPCanvas(var image: BufferedImage): Canvas(){
    override fun paint(g: Graphics){
        super.paint(g)
        g.drawImage(image, 0, 0, image.width, image.height, this)
    }
}