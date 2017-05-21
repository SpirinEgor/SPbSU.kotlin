package Model

import Observer.*
import Strategy.*
import java.awt.image.BufferedImage

class BMP_model(val obs: Observer, var busy: Boolean = false): Model{
    private var image: BufferedImage? = null
    private var height: Int = 0
    private var width: Int = 0

    fun parser(data: Array<Byte>, index: Int, count: Int): Int{
        var result: Int = 0
        for (i in 0..count - 1){
            var num = data[index + i].toInt()
            if (num < 0) num += 256
            result += num.shl(i * 8)
        }
        return result
    }

    fun getInfo(data: Array<Byte>): MutableMap<String, Int>{
        val info: MutableMap<String, Int> = mutableMapOf()
        val VERSION = parser(data, 14, 4)
        println(VERSION)
        if (VERSION == 40){
            info.put("fileSize", parser(data, 2, 4))
            info.put("bitDataOfSet", parser(data, 10, 4))
            info.put("infoSize", parser(data, 14, 4))
            info.put("bitWidth", parser(data, 18, 4))
            width = info["bitWidth"]!!
            info.put("bitHeight", parser(data, 22, 4))
            height = info["bitHeight"]!!
            info.put("bitCount", parser(data, 28, 2))
            info.put("bitCompression", parser(data, 30, 4))
            info.put("bitSize", parser(data, 34, 4))
            info.put("bitUsed", parser(data, 46, 4))
            info.put("bitImportant", parser(data, 50, 4))
        }
        for (i in info){
            println("${i.key} ${i.value}")
        }
        return info
    }

    fun getData(data: Array<Byte>): Array<Byte> =
            data.sliceArray(IntRange(parser(data, 10, 4), parser(data, 2, 4) - 1))

    fun getColorTable(data: Array<Byte>, info: MutableMap<String, Int>): Array<Byte>{
        if (info["bitUsed"]!! == 0){
            info.put("bitUsed", 1.shl(info["bitCount"]!!))
        }
        return data.sliceArray(IntRange(54, 54 + info["bitUsed"]!! * 4 - 1))
    }

    override fun set(data: Array<Byte>): String? {
        val type = parser(data, 0, 2)
        if (type != 19778)
            return ("Invalid signature")
        val info = getInfo(data)
        val data_image = getData(data)
        when (info["bitCount"]){
            8 ->{
                val colorTable = getColorTable(data, info)
                image = `8bit_strategy`(info, data_image, data, colorTable).render()
            }
            24 ->{
                image = `24&32bit_strategy`(info, data_image, 3).render()
            }
            32 ->{
                image = `24&32bit_strategy`(info, data_image, 4).render()
            }
        }
        if (!busy){
            event(image!!, info)
        }
        return null
    }

    fun get_image(): BufferedImage = image!!

    override fun event(image: BufferedImage, info: MutableMap<String, Int>) {
        obs.update(image, info)
    }

}