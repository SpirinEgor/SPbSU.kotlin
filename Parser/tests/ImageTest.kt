import Model.BMP_model
import Observer.BMP_observer

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

import java.awt.image.BufferedImage
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import javax.imageio.ImageIO.read

internal class ImageTest {

    private val `8bit_pictures` = listOf(
            "bogts",
            "freebsd2",
            "hm",
            "lena",
            "man"
    )

    private val `24bit_pictures` = listOf(
            "beaut",
            "dodj",
            "love",
            "per",
            "su85"
    )

    private val `32bit_pictures` = listOf(
            "panda",
            "led",
            "pahom",
            "sh"
    )

    @Test
    fun `8bit_test`(){
        for (pic in `8bit_pictures`){
            if (pic == "lena")
                continue
            println(pic)
            val model = BMP_model(BMP_observer(), true)
            val realImage: BufferedImage
            val parserImage: BufferedImage
            val path = "./Pictures/8bit_$pic.bmp"
            realImage = read(File(path))
            model.set(Files.readAllBytes(Paths.get(path)).toTypedArray())
            parserImage = model.get_image()
            assertEquals(parserImage.height, realImage.height)
            assertEquals(parserImage.width, realImage.width)
            for (i in  0..parserImage.width - 1)
                for (j in 0..parserImage.height - 1)
                    assertEquals(parserImage.getRGB(i, j), realImage.getRGB(i, j))
            println()
        }
    }

    @Test
    fun `24bit_test`(){
        for (pic in `24bit_pictures`){
            println(pic)
            val model = BMP_model(BMP_observer(), true)
            val realImage: BufferedImage
            val parserImage: BufferedImage
            val path = "./Pictures/24bit_$pic.bmp"
            realImage = read(File(path))
            model.set(Files.readAllBytes(Paths.get(path)).toTypedArray())
            parserImage = model.get_image()
            assertEquals(parserImage.height, realImage.height)
            assertEquals(parserImage.width, realImage.width)
            for (i in  0..parserImage.width - 1)
                for (j in 0..parserImage.height - 1)
                    assertEquals(parserImage.getRGB(i, j), realImage.getRGB(i, j))
            println()
        }
    }

    @Test
    fun `32bit_test`(){
        for (pic in `32bit_pictures`){
            println(pic)
            val model = BMP_model(BMP_observer(), true)
            val realImage: BufferedImage
            val parserImage: BufferedImage
            val path = "./Pictures/32bit_$pic.bmp"
            realImage = read(File(path))
            model.set(Files.readAllBytes(Paths.get(path)).toTypedArray())
            parserImage = model.get_image()
            assertEquals(parserImage.height, realImage.height)
            assertEquals(parserImage.width, realImage.width)
            for (i in  0..parserImage.width - 1)
                for (j in 0..parserImage.height - 1)
                    assertEquals(parserImage.getRGB(i, j), realImage.getRGB(i, j))
            println()
        }
    }

}