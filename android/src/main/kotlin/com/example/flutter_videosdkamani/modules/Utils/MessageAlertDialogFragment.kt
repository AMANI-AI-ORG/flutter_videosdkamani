import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.flutter_videosdkamani.R


class MessageAlertDialogFragment(
    private val message: String,
    private val onConfirm: () -> Unit,
    private val onCancel: (() -> Unit)? = null
) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_message_alert_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val messageTextView = view.findViewById<TextView>(R.id.messageTextView)
        val confirmButton = view.findViewById<Button>(R.id.confirmButton)
        val cancelButton = view.findViewById<Button>(R.id.cancelButton)

        messageTextView.text = message

        confirmButton.setOnClickListener {
            dismiss()
            onConfirm()
        }

        cancelButton.setOnClickListener {
            dismiss()
            onCancel?.invoke()
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
    }

    override fun getDialog(): android.app.Dialog {
        val dialog = super.getDialog()
        dialog?.setCancelable(false)
        dialog?.setCanceledOnTouchOutside(false)
        return dialog!!
    }
}

// class MessageAlertDialogFragment(
//     private val message: String,
//     private val onConfirm: () -> Unit,
//     private val onCancel: (() -> Unit)? = null
// ) : DialogFragment() {

//     override fun onCreateView(
//         inflater: LayoutInflater,
//         container: ViewGroup?,
//         savedInstanceState: Bundle?
//     ): View? {
//         return inflater.inflate(R.layout.fragment_message_alert_dialog, container, false)
//     }

//     override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//         super.onViewCreated(view, savedInstanceState)

//         val messageTextView = view.findViewById<TextView>(R.id.messageTextView)
//         val confirmButton = view.findViewById<Button>(R.id.confirmButton)
//         val cancelButton = view.findViewById<Button>(R.id.cancelButton)

//         messageTextView.text = message

//         confirmButton.setOnClickListener {
//             dismiss()
//             onConfirm()
//         }

//         cancelButton.setOnClickListener {
//             dismiss()
//             onCancel?.invoke()
//         }
//     }

//     override fun getDialog(): android.app.Dialog {
//         val dialog = super.getDialog()
//         dialog?.setCancelable(false)
//         dialog?.setCanceledOnTouchOutside(false)
//         return dialog!!
//     }
// }