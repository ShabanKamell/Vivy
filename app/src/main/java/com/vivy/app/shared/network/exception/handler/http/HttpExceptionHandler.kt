package com.vivy.app.shared.network.exception.handler.http

import com.annimon.stream.Stream
import com.vivy.app.shared.network.exception.ExceptionPresenter
import com.vivy.app.shared.ui.view.BaseView

/**
 * Created by Sha on 10/9/17.
 */

abstract class HttpExceptionHandler {

    protected lateinit var info: HttpExceptionInfo
    protected lateinit var view: BaseView
    protected lateinit var throwable: Throwable
    protected lateinit var presenter: ExceptionPresenter

    protected abstract fun handle()
    protected abstract fun supportedExceptions(): List<Int>

    fun canHandle(code: Int): Boolean {
        val opt = Stream.of(supportedExceptions())
                .filter { item -> code == item }
                .findFirst()
        return opt.isPresent
    }

    fun handle(info: HttpExceptionInfo) {
        this.info = info
        this.throwable = info.throwable
        this.view = info.presenter.view
        this.presenter = info.presenter
        handle()
    }


}
