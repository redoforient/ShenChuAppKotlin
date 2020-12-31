package com.shenchu.kotlin.app.framework

interface IHttpRequest {
    fun setUrl(url: String)
    fun setParams(params: ByteArray)
    fun execute()

    //两个接口需要连接在一起
    fun setListener(listener: IHttpListener)
}