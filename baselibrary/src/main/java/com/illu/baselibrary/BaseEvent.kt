package com.illu.niubilife.event

data class BaseEvent<T>(val code: Int) {

    constructor(code: Int, data: T) : this(code)
}
