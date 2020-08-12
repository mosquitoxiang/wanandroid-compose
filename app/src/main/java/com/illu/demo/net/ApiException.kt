package com.illu.demo.net

import java.lang.RuntimeException

class ApiException (var code: Int, override var message: String): RuntimeException()