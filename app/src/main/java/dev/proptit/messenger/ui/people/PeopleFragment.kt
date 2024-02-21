package dev.proptit.messenger.ui.people

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation.findNavController
import dev.proptit.messenger.R
import dev.proptit.messenger.databinding.FragmentPeopleBinding
import dev.proptit.messenger.ui.MainViewModel
import dev.proptit.messenger.ui.adapters.ContactAdapter


class PeopleFragment : Fragment() {

    private var _binding: FragmentPeopleBinding? = null
    private lateinit var peopleAdapter: ContactAdapter
    private val binding get() = _binding!!
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
        peopleViewModel.apply {
            allContactList.observe(viewLifecycleOwner) {
                peopleAdapter.submitList(it)
            }
        }
    }


    private fun setupAdapter() {
        peopleAdapter = ContactAdapter(mutableListOf()) {
            val bundle = Bundle()
            bundle.putInt("idContact", it)
            findNavController(requireView()).navigate(
                R.id.action_peopleFragment_to_chatFragment,
                bundle
            )
        }
        binding.recyclerViewContact.adapter = peopleAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}