package com.julian.taipeitour.common.ex

object OtherEx {

    fun Any.emptyString()=""


    inline fun <reified T2> Any.case() = this as T2

}