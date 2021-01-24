package com.phablecare.phableassignment.view.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.phablecare.phableassignment.R
import com.phablecare.phableassignment.app.App
import com.phablecare.phableassignment.data.entity.User
import com.phablecare.phableassignment.databinding.FragmentAddUserBinding
import com.phablecare.phableassignment.utils.hideKeyboard
import com.phablecare.phableassignment.utils.isValidEmail
import com.phablecare.phableassignment.view.activity.HomeActivity
import com.phablecare.phableassignment.viewmodel.SharedUserViewModel
import com.phablecare.phableassignment.viewmodelFactory.SharedUserViewModelFactory
import javax.inject.Inject

class AddOrUpdateUserFragment : Fragment() {

    private lateinit var binding: FragmentAddUserBinding
    private lateinit var viewModel:SharedUserViewModel
    private var userAction: Int = 0
    private var updateId: Int = 0
    @Inject
    lateinit var factor:SharedUserViewModelFactory



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_add_user, container, false)
        setupDefault()
        setupEvent()
        return binding.root
    }

    private fun setupDefault() {

        if (arguments != null) {
            arguments?.getInt(ListOfUserFragment.USER_ACTION)?.apply {
                userAction = this
                setTitle(userAction)
            }
        }

        (requireActivity().application as App).appComponent.inject(this)
        viewModel=ViewModelProvider(requireActivity(),factor).get(SharedUserViewModel::class.java)
        binding.viewmodel=viewModel
        binding.fragment=this
        binding.lifecycleOwner=this
    }


    private fun setupEvent(){
        observerEvent()
        onNameTextWatcherEvent()
        onEmailTextWatcherEvent()
        onActionDone()
    }

    private fun onNameTextWatcherEvent(){
        binding.tieName.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (!s.isNullOrEmpty()){
                    postUserNameError("")
                }
            }
        })
    }


    private fun onEmailTextWatcherEvent(){
        binding.tieMail.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (!s.isNullOrEmpty()){
                    postEmailError("")
                }
            }
        })
    }

    private fun onActionDone(){
         binding.tieMail.setOnEditorActionListener { v, actionId, event ->
             if (actionId == EditorInfo.IME_ACTION_DONE) {
                 onAddOrUpdateEvent()
                 return@setOnEditorActionListener  true
             }
             return@setOnEditorActionListener  false
         }
    }

    private fun observerEvent(){
        viewModel.updateUser.observe(viewLifecycleOwner, {
            it?.let {
                viewModel.inputName.postValue(it.userName)
                viewModel.inputEmail.postValue(it.email)
                updateId=it.uuid
            }
        })

        viewModel.userNameErrorMessage.observe(viewLifecycleOwner, {
            binding.tilName.error=it
        })

        viewModel.emailErrorMessage.observe(viewLifecycleOwner, {
            binding.tilMail.error=it
        })

        viewModel.addAndUpdateStatusMessage.observe(viewLifecycleOwner, {
            it?.let {
                when(userAction){
                    ListOfUserFragment.USER_ACTION_ADD->{
                        if (it){
                            Toast.makeText(requireActivity(), getString(R.string.user_added), Toast.LENGTH_LONG).show()
                            onBackToList()
                        }else{
                            Toast.makeText(requireActivity(), getString(R.string.user_unable_to_add), Toast.LENGTH_LONG).show()
                        }
                    }
                    ListOfUserFragment.USER_ACTION_UPDATE->{
                        if (it){
                            Toast.makeText(requireActivity(), getString(R.string.user_update), Toast.LENGTH_LONG).show()
                            onBackToList()
                        }else{
                            Toast.makeText(requireActivity(), getString(R.string.user_unable_to_update), Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }

        })
    }

    fun onAddOrUpdateEvent(){
        if (binding.tieName.text.isNullOrEmpty()){
            postUserNameError(getString(R.string.enter_name))
        }

        if (binding.tieMail.text.isNullOrEmpty()){
            postEmailError(getString(R.string.enter_email))
        }else{
            if (!binding.tieMail.text.isValidEmail()){
                postEmailError(getString(R.string.enter_validate_email))
            }
        }


        if (!binding.tieName.text.isNullOrEmpty()&&!binding.tieMail.text.isNullOrEmpty()&&binding.tieMail.text.isValidEmail()){
            when(userAction){
                ListOfUserFragment.USER_ACTION_ADD->{
                    val newUser=User(0,binding.tieName.text.toString(),binding.tieMail.text.toString())
                    viewModel.insert(newUser)
                }

                ListOfUserFragment.USER_ACTION_UPDATE -> {
                    val newUser = User(
                        updateId,
                        binding.tieName.text.toString(),
                        binding.tieMail.text.toString()
                    )
                    viewModel.update(newUser)
                }
            }
        }
    }

    private fun setTitle(action: Int) {
        when (action) {
            ListOfUserFragment.USER_ACTION_ADD -> {
                (requireActivity() as HomeActivity).supportActionBar?.title = getString(R.string.add_user)
                binding.btnAddOrUpdate.text = getString(R.string.add)
            }

            ListOfUserFragment.USER_ACTION_UPDATE -> {
                (requireActivity() as HomeActivity).supportActionBar?.title = getString(R.string.update_user)
                binding.btnAddOrUpdate.text = getString(R.string.update)
            }
        }
    }

    private fun onBackToList(){
        viewModel.addAndUpdateStatusMessage.value=null
        viewModel.inputName.value=null
        viewModel.inputEmail.value=null
        hideKeyboard()
        findNavController().popBackStack()
    }

    private fun postUserNameError(error:String){
        viewModel.userNameErrorMessage.postValue(error)
    }

    private fun postEmailError(error:String){
        viewModel.emailErrorMessage.postValue(error)
    }

}