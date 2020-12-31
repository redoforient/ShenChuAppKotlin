package com.shenchu.kotlin.app.json

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.alibaba.fastjson.JSON
import com.shenchu.kotlin.app.framework.IDataListener
import com.shenchu.kotlin.app.framework.IHttpListener
import java.io.ByteArrayOutputStream
import java.io.InputStream

/**
 * @author zhangll
 * @description
 * @date 2020/11/26 14:05
 */
class JsonHttpListener<T>(
    //1、用户用什么样的javabean来接收数据
    private val response: Class<T>,
    dataListener: IDataListener<T>
) : IHttpListener {

    //2、需要把最后的结果以对象的方式交给用户
    private val mDataListener: IDataListener<T>

    //3、线程切换
    private val handler = Handler(Looper.getMainLooper())

    override fun onSuccess(inputStream: InputStream?) {
         //inputStream已经有了数据
        //inputStream中的数据转化为String
        val content = getContent(inputStream!!)
        Log.i("JsonHttpListener", "服务端响应报文：$content")
        //按用户的要求转化成对应的javabean
        val responseObj: T = JSON.parseObject(content, response)
        //responseObj需要发送给最终用户
        handler.post { mDataListener.onSuccess(responseObj) }
    }

    private fun getContent(inputStream: InputStream): String {
        var result = ""
        try {
            val baos = ByteArrayOutputStream()
            val buffer = ByteArray(2048)
            var len = -1
            while (inputStream.read(buffer).also { len = it } != -1) {
                baos.write(buffer, 0, len)
            }
            result = baos.toString()
            baos.close()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            return result
        }
    }

    override fun onFailure() {}

    init {
        mDataListener = dataListener
    }
}