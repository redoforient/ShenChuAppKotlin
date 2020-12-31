package com.shenchu.kotlin.app.framework

import com.alibaba.fastjson.JSON

/**
 * @author zhangll
 * @description
 * @date 2020/11/26 10:40
 */
class HttpTask<T>(
    url: String, requestData: T,
    private val mHttpRequest: IHttpRequest,
    private val mHttpListener: IHttpListener
) : Runnable {
    override fun run() {
        mHttpRequest.execute()
    }

    init {
        mHttpRequest.setUrl(url)
        mHttpRequest.setListener(mHttpListener)
        if (requestData != null) {
            //将请求对象转化成对应格式的字符串
            val dataStr: String = JSON.toJSONString(requestData)
            try {
                mHttpRequest.setParams(dataStr.toByteArray(charset("UTF-8")))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}