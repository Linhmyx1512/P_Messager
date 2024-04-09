package dev.proptit.messenger.presentation.people

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.proptit.messenger.R
import dev.proptit.messenger.databinding.FragmentPeopleBinding
import dev.proptit.messenger.presentation.MainViewModel
import dev.proptit.messenger.presentation.adapters.PeopleAdapter
import dev.proptit.messenger.setup.Keys
import dev.proptit.messenger.setup.PrefManager
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PeopleFragment : Fragment() {

    private var _binding: FragmentPeopleBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var prefManager: PrefManager
    private val peopleAdapter: PeopleAdapter by lazy {
        PeopleAdapter()
    }
    private val peopleViewModel: MainViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPeopleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        observeData()
    }

    private fun observeData() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                peopleViewModel.allContacts.collect {
                    peopleAdapter.submitList(it)
                }
            }
        }
    }


    private fun setupAdapter() {
        peopleAdapter.onContactClick = {
            peopleViewModel.setContact(
                prefManager.get(Keys.MY_ACCOUNT_ID, -1),
                it
            )
            findNavController().navigate(R.id.action_peopleFragment_to_chatFragment)
        }
        binding.recyclerViewContact.adapter = peopleAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}