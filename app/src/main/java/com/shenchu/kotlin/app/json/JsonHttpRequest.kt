package com.shenchu.kotlin.app.json

import com.shenchu.kotlin.app.framework.IHttpListener
import com.shenchu.kotlin.app.framework.IHttpRequest
import java.net.HttpURLConnection
import java.net.URL

/**
 * @author zhangll
 * @description
 * @date 2020/11/26 10:54
 */
class JsonHttpRequest : IHttpRequest {
    private var url: String? = null
    private lateinit var params: ByteArray
    private var mHttpListener: IHttpListener? = null
    override fun setUrl(url: String?) {
        this.url = url
    }

    override fun setParams(params: ByteArray) {
        this.params = params
    }

    /**
     * 真实的网络操作在这里执行
     * urlconnection socket okhtp
     * 只要能支持到inputStream的方式都能用
     */
    override fun execute() {
        //执行网络操作
        var connection: HttpURLConnection? = null
        try {
            //创建URL对象
            val url = URL(url)
            //打开连接，得到HttpURLConnection对象
            connection = url.openConnection() as HttpURLConnection
            //设置请求方式，连接超时，读取数据超时
            connection.requestMethod = "POST"
            connection!!.connectTimeout = 5000
            connection.readTimeout = 6000
            connection.setRequestProperty("Content-Type", "application/json;charset=utf-8")
            //链接服务器
            connection.connect()
            val out = connection.outputStream
            out.write(params)
            out.close()
            //发送请求，得到响应数据
            val responseCode = connection.responseCode
            //必须是200才读
            if (responseCode == HttpURLConnection.HTTP_OK) {
                //得到InputStream，读取成string
                val `is` = connection.inputStream
                mHttpListener?.onSuccess(`is`)
                `is`.close()
            } else {
                mHttpListener?.onFailure()
                throw RuntimeException("请求失败")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            throw RuntimeException("请求失败")
        } finally {
            //关闭httpURLConnection对象
            connection!!.disconnect()
        }
    }

    //两个接口需要连接在一起
    override fun setListener(listener: IHttpListener?) {
        mHttpListener = listener
    }
}