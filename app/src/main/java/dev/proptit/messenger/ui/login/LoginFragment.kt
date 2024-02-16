package dev.proptit.messenger.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dev.proptit.messenger.R
import dev.proptit.messenger.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var bottomNav: BottomNavigationView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBottomNav()
    }

    private fun setupBottomNav() {
        bottomNav= requireActivity().findViewById(R.id.bottom_nav)
        bottomNav.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bottomNav.visibility = View.VISIBLE
        _binding = null
    }
}