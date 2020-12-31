package com.shenchu.kotlin.app;

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.shenchu.kotlin.app.bean.RequestBean
import com.shenchu.kotlin.app.bean.ResponseBean
import com.shenchu.kotlin.app.framework.IDataListener
import com.shenchu.kotlin.app.framework.NetFramework

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun OnClick(view: View) {
        val url = "https://v.juhe.cn/historyWeather/citys"
        val param = RequestBean("2", "bb52107206585ab074f5e59a8c73875b")
        NetFramework.sendJsonRequest(url, param,
            ResponseBean::class.java,
            object : IDataListener<ResponseBean> {
                override fun onSuccess(responseBean: ResponseBean) {
                    Toast.makeText(
                        this@MainActivity, "响应码是：" + responseBean.error_code,
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onFailure() {
                    Log.i("MainActivity", "请求失败...")
                }
            })
    }

}