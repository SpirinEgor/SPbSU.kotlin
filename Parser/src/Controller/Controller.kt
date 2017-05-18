package Controller

import Model.Model

interface Controller {

    fun Validator(path: String): Boolean
    fun PassData(model: Model, path: String): String?

}