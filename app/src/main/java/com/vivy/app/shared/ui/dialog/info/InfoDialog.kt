package com.vivy.app.shared.ui.dialog.info

import androidx.core.content.ContextCompat
import com.vivy.app.R
import com.vivy.app.shared.ui.defaultimpl.DefaultViewModel
import com.vivy.app.shared.ui.dialog.LoadingDialogHelper
import com.vivy.app.shared.ui.frag.BaseDialogFrag
import com.vivy.app.shared.util.ThreadUtil
import kotlinx.android.synthetic.main.frag_dialog_info.*

class InfoDialog : BaseDialogFrag<DefaultViewModel>() {

    private var messageType: MessageType? = null
    private var isCancellable: Boolean = false
    private var message: String? = null
    private var closeCallback: (() -> Unit)? = null

    enum class MessageType {
        INFO,
        WARNING,
        EXCEPTION
    }

    override var layoutId: Int = R.layout.frag_dialog_info

    override fun doOnViewCreated() {

    }

    override fun setupUi() {
        dialog?.setCanceledOnTouchOutside(isCancellable)

        tv_message!!.text = message

        var color = -1
        if (messageType != null)
            when (messageType) {

                MessageType.WARNING -> {
                    color = R.color.warning
                    tv_message!!.setTextColor(ContextCompat.getColor(context!!, R.color.warning))
                }

                MessageType.EXCEPTION -> color = R.color.exception

                else -> {}
            }

        if (color != -1)
            tv_message!!.setTextColor(ContextCompat.getColor(context!!, color))
        btn_close.setOnClickListener {
            if (closeCallback != null) closeCallback!!.invoke()
            // Try to close all dialogs if duplicated
            dismiss()
        }
    }

    companion object {

        fun newInstance(
                type: MessageType,
                message: String,
                isCancellable: Boolean,
                closeCallback: (() -> Unit)?): InfoDialog {
            val fragment = InfoDialog()
            fragment.messageType = type
            fragment.message = message
            fragment.isCancellable = isCancellable
            fragment.closeCallback = closeCallback
            return fragment
        }
    }
}

object InfoDialogHelper {
    var instances: MutableList<InfoDialog?> = mutableListOf()

    fun add(dialog: InfoDialog){
        ThreadUtil.runOnUiThread {
            LoadingDialogHelper.instances.forEach {
                it?.dismiss()
            }
            instances.clear()
            instances.add(dialog)
        }
    }

}


