package br.com.sailboat.elseapp.model

class RepeatType(id: Int) {

    companion object {
        val NOT_REPEAT = 0
        val SECOND = 1
        val MINUTE = 2
        val HOUR = 3
        val DAY = 4
        val WEEK = 5
        val MONTH = 6
        val YEAR = 7
    }

}