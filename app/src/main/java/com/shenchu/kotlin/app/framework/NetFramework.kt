package com.shenchu.kotlin.app.framework

import com.shenchu.kotlin.app.json.JsonHttpListener
import com.shenchu.kotlin.app.json.JsonHttpRequest

/**
 * @author zhangll
 * @description
 * @date 2020/11/26 14:11
 */
object NetFramework {
    /**
     * @param url
     * @param requestParams
     * @param reponse
     * @param dataListener
     * @param <T>：请求参数（输入）
     * @param <M>：响应结果（输出）
    </M></T> */
    fun <T, M> sendJsonRequest(
        url: String?,
        requestParams: T,
        reponse: Class<M>?,
        dataListener: IDataListener<M>?
    ) {
        val httpRequest: IHttpRequest = JsonHttpRequest()
        val httpListener: IHttpListener = JsonHttpListener(reponse!!, dataListener!!)
        val httpTask: HttpTask<*> = HttpTask(url, requestParams, httpRequest, httpListener)
        ThreadManager.instance.addTask(httpTask)
    }
    //public static void sendXmlRequest();
    //public static void sendProtobufRequest();
}