package Strategy

import java.awt.image.BufferedImage

class `8bit_strategy`(val info: MutableMap<String, Int>, val data: Array<Byte>, val data_image: Array<Byte>, val colorTable: Array<Byte>) : Strategy {

    private fun parser(data: Array<Byte>, index: Int, count: Int): Int{
        var result: Int = 0
        for (i in 0..count - 1){
            var num = data[index + i].toInt()
            if (num < 0) num += 256
            result += num.shl(i * 8)
        }
        return result
    }

    override fun render(): BufferedImage {
        var num: Int
        val height = info["bitHeight"]!!
        val width = info["bitWidth"]!!
        val file_size = info["fileSize"]!!
        //val shift = (file_size - 4 * info["bitUsed"]!! + 54) / height - width
        val image = BufferedImage(width, height, 1)
        for (i in 0..height - 1){
            for (j in 0..width - 1){
                num = parser(data, i * width + j, 1)
                val pixel = parser(colorTable, num * 4, 4)
                image.setRGB(j, height - i - 1, pixel)
            }
        }
        return image
    }
}