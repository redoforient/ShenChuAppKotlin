package com.shenchu.kotlin.app.framework

import java.io.InputStream

/**
 * 响应的顶层接口
 */
interface IHttpListener {
    fun onSuccess(inputStream: InputStream?)
    fun onFailure()
}