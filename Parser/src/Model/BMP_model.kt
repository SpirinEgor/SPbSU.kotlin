package Model

import Observer.*
import Strategy.*
import java.awt.image.BufferedImage

class BMP_model(var busy: Boolean = false): Model{
    val obs: BMP_observer = BMP_observer()
    var observers: MutableSet<BMP_observer> = mutableSetOf()
    var image: BufferedImage? = null
    var height: Int = 0
    var width: Int = 0

    fun parser(data: Array<Byte>, index: Int, count: Int): Int{
        var result: Int = 0
        for (i in 1..count){
            var num = data[index + i - 1].toInt()
            if (num < 0) num += 256
            result += num.shl(i * 8)
        }
        return result
    }

    fun getInfo(data: Array<Byte>): MutableMap<String, Int>{
        val info: MutableMap<String, Int> = mutableMapOf()
        val VERSION = parser(data, 14, 4)
        if (VERSION == 40){
            info.put("fileSize", parser(data, 2, 4))
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
        return info
    }

    fun getData(data: Array<Byte>): Array<Byte> =
            data.sliceArray(IntRange(parser(data, 10, 4), parser(data, 2, 4) - 1))

    fun getColorTable(data: Array<Byte>, info: MutableMap<String, Int>): Array<Byte>{
        if (info["bitUsed"] == 0){
            info["bitUsed"] = 1.shl(info["bitCount"]!!)
        }
        return data.sliceArray(IntRange(54, 54 + info["bitUsed"]!! * 4 - 1))
    }

    override fun set(data: Array<Byte>): String? {
        val type = parser(data, 0, 2)
        if (type != 19778)
            return ("Invalid signature")
        register()
        val info = getInfo(data)
        val data_image = getData(data)
        var render: Strategy? = null
        when (info["bitCount"]){
            8 ->{
                val colorTable = getColorTable(data, info)
                render = `8bit_strategy`(info, data, data_image, colorTable)
            }
            24 ->{
                render = `24&32bit_strategy`(info, data, 3)
            }
            32 ->{
                render = `24&32bit_strategy`(info, data, 4)
            }
        }
        image = render?.render()
        if (!busy){
            event(image!!, info)
        }
        return null
    }

    override fun delete(){
        observers.remove(obs)
    }

    override fun register(){
        observers.add(obs)
    }

    override fun event(image: BufferedImage, info: MutableMap<String, Int>) {
        for (obs in observers){
            obs.update(image, info)
        }
    }

}