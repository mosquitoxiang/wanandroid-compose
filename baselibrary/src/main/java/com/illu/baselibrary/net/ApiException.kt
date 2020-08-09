package com.illu.baselibrary.net

import java.lang.RuntimeException

class ApiException (var code: Int, override var message: String): RuntimeException()