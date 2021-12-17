package the.mrsmile.notes

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
            composeEmail(email, subject, textEmail)
        }

        return binding.root
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