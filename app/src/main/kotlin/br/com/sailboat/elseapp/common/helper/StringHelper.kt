package br.com.sailboat.elseapp.common.helper

object StringHelper {

    fun isEmpty(text: String?): Boolean {
        return text == null || text.trim { it <= ' ' }.isEmpty()
    }

}
