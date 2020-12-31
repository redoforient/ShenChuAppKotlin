package com.shenchu.kotlin.app.framework

/**
 * 给用户的接口
 */
interface IDataListener<T> {
    fun onSuccess(t: T)
    fun onFailure()
}