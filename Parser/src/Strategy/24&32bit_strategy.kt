package Strategy

import java.awt.image.BufferedImage

class `24&32bit_strategy`(val info: MutableMap<String, Int>, val data: Array<Byte>, val byteCount: Int): Strategy {

    private fun parser(data: Array<Byte>, index: Int, count: Int): Int{
        var result: Int = 0
        for (i in 1..count){
            var num = data[index + i - 1].toInt()
            if (num < 0) num += 256
            result += num.shl(i * 8)
        }
        return result
    }

    override fun render(): BufferedImage {
        val height = info["bitHeight"]!!
        val width = info["bitWidth"]!!
        val file_size = info["fileSize"]
        val shift = (file_size!! - 54) / height - width * byteCount
        val image = BufferedImage(width, height, 1)
        if (info["bitUsed"] == 0){
            for (i in 0..height - 1){
                for (j in 0..width - 1){
                    val pixel = parser(data, i * (shift + width * byteCount) + j * byteCount, byteCount)
                    image.setRGB(j, height - i - 1, pixel)
                }
            }
        }
        return image
    }

}