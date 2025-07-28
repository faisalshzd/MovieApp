package com.example.movieapp.Login_Signup_Screeens.Login

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.movieapp.R
import android.graphics.Color
import android.widget.TextView
import com.example.movieapp.Reset_Password.ResetPasswordActivity

class LoginScreenFragment : Fragment() {

    private var isPasswordVisible = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_screen_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Back button logic
        val backButton = view.findViewById<ImageButton>(R.id.backButton)
        backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        // Password toggle logic
        val passwordEditText = view.findViewById<EditText>(R.id.passwordEditText)
        val passwordToggle = view.findViewById<ImageView>(R.id.passwordToggle)

        passwordToggle.setOnClickListener {
            isPasswordVisible = !isPasswordVisible

            if (isPasswordVisible) {
                passwordEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                passwordToggle.setImageResource(R.drawable.password_eye_open)

                passwordEditText.setHintTextColor(Color.parseColor("#92929D"))
                passwordEditText.typeface = ResourcesCompat.getFont(requireContext(), R.font.montserrat_medium)
            } else {
                passwordEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                passwordToggle.setImageResource(R.drawable.password_eye_off)

                passwordEditText.setHintTextColor(Color.parseColor("#92929D"))
                passwordEditText.typeface = ResourcesCompat.getFont(requireContext(), R.font.montserrat_medium)
            }

            passwordEditText.setSelection(passwordEditText.text?.length ?: 0)
        }
        val forgotPasswordTextView = view.findViewById<TextView>(R.id.forgotPassword)
        forgotPasswordTextView.setOnClickListener {
            val intent = Intent(requireContext(), ResetPasswordActivity::class.java)
            startActivity(intent)
            requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

    }
}
