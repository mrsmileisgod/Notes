package the.mrsmile.notes

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import the.mrsmile.notes.databinding.FragmentFeedbackBinding

class FeedbackFragment : Fragment() {

    private lateinit var binding: FragmentFeedbackBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFeedbackBinding.inflate(layoutInflater, container, false)



        binding.btnFeedbackSubmit.setOnClickListener {
            val email = arrayOf("mrsmileisgod@gmail.com")
            val subject = binding.editTextFeedbackSubject.text.toString()
            val textEmail = binding.editTextFeedback.text.toString()
            if (subject.isNotEmpty() && textEmail.isNotEmpty()) {
                composeEmail(email, subject, textEmail)

            } else {
                closeSoftKeyboard(binding.root.context, binding.root)
                Snackbar.make(
                    binding.editTextFeedback,
                    "Please fill both fields.",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

        return binding.root
    }

    private fun closeSoftKeyboard(context: Context, v: View) {
        val iMm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        iMm.hideSoftInputFromWindow(v.windowToken, 0)
        v.clearFocus()
    }

    private fun composeEmail(addresses: Array<String>, subject: String, text: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")

            putExtra(Intent.EXTRA_EMAIL, addresses)
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, text)
        }

        if (intent.resolveActivity(binding.root.context.packageManager) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(binding.root.context, "No email app installed", Toast.LENGTH_SHORT)
                .show()
        }


    }


}