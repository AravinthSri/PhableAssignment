package com.phablecare.phableassignment.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.phablecare.phableassignment.R
import com.phablecare.phableassignment.app.App
import com.phablecare.phableassignment.data.entity.User
import com.phablecare.phableassignment.databinding.FragmentListOfUserBinding
import com.phablecare.phableassignment.utils.hideKeyboard
import com.phablecare.phableassignment.view.adapter.UserListAdapter
import com.phablecare.phableassignment.viewmodel.SharedUserViewModel
import com.phablecare.phableassignment.viewmodelFactory.SharedUserViewModelFactory
import javax.inject.Inject

class ListOfUserFragment:Fragment() {

    companion object{
        const val USER_ACTION="USER_ACTION"
        const val USER_ACTION_ADD=1
        const val USER_ACTION_UPDATE=2
    }

    lateinit var binding:FragmentListOfUserBinding
    @Inject
    lateinit var factory: SharedUserViewModelFactory
    lateinit var viewModel: SharedUserViewModel



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list_of_user, container, false)

        setupDefault()
        setupEvent()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        hideKeyboard()
        viewModel.inputName.value=null
        viewModel.inputEmail.value=null
    }

    private fun setupDefault(){
        (requireActivity().application as App).appComponent.inject(this)
        viewModel=ViewModelProvider(requireActivity(),factory).get(SharedUserViewModel::class.java)

    }

    private fun setupEvent(){
        observerEvent()
        onAddUserEvent()

    }


    private fun onAddUserEvent(){
        binding.fabAddUser.setOnClickListener {
            val bundle=Bundle()
            bundle.putInt(USER_ACTION, USER_ACTION_ADD)
            viewModel.updateUser.value=null
            findNavController().navigate(R.id.action_user_list_screen_to_add_or_update_user_screen,bundle)
        }
    }


    private fun observerEvent(){
        viewModel.userList.observe(viewLifecycleOwner, {
            binding.loading.loadingContainer.visibility=View.GONE
            if (it.isNullOrEmpty()){
                binding.emptyView.emptyContainer.visibility=View.VISIBLE

            }else{
                binding.emptyView.emptyContainer.visibility=View.GONE
                setAdapter(it)
            }
        })
    }


    private fun setAdapter(userList:List<User>){
        val userListAdapter=UserListAdapter()
        userListAdapter.setList(ArrayList(userList),object :UserListAdapter.UserClickListener{
            override fun onClicked(user: User,position: Int) {
                showAlert(userListAdapter,user, position)
            }

        })
        binding.rvListOfUser.layoutManager=LinearLayoutManager(requireContext())
        binding.rvListOfUser.adapter=userListAdapter

    }

    private fun showAlert(adapter:UserListAdapter,user: User,position: Int){
        MaterialAlertDialogBuilder(requireContext())
            .setMessage(getString(R.string.are_u_sure))
            .setNeutralButton(resources.getString(android.R.string.cancel)) { _, _ ->

            }
            .setNegativeButton(getString(R.string.delete)) { _, _ ->
                viewModel.delete(user)
                adapter.removeUser(user,position)
            }
            .setPositiveButton(getString(R.string.update)) { _, _ ->
                val bundle=Bundle()
                bundle.putInt(USER_ACTION, USER_ACTION_UPDATE)
                viewModel.sendUser(user)
                findNavController().navigate(R.id.action_user_list_screen_to_add_or_update_user_screen,bundle)
            }
            .show()
    }

}