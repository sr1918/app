package com.example.roomapp1

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.roomapp.viewmodel.UserViewModel
import com.example.roomapp1.data.User
import com.example.roomapp1.data.UserDao
import com.example.roomapp1.data.UserDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class AddFragment : Fragment() {

    private lateinit var userDao: UserDao
    private lateinit var data1: UserDatabase
    private lateinit var mUserViewModel: UserViewModel
    private lateinit var errorage:TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_add, container, false)
        view.findViewById<EditText>(R.id.addAge_et).addTextChangedListener(watcher1)
        errorage=view.findViewById<TextView>(R.id.seterrorage)

        view.findViewById<Button>(R.id.add_btn).setOnClickListener {
            insertDataToDatabase()
        }

        return view
    }

    private fun insertDataToDatabase() {
        val firstName = view?.findViewById<EditText>(R.id.addFirstName_et)!!.text.toString()
        val lastName = view?.findViewById<EditText>(R.id.addLastName_et)!!.text.toString()
        val age =  view?.findViewById<EditText>(R.id.addAge_et)!!.text

        if(inputCheck(firstName, lastName, age)){
            val user = User(0, firstName, lastName, Integer.parseInt(age.toString()))
                mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
                mUserViewModel.addUser(user)
            val builder = AlertDialog.Builder(requireContext())
            builder.setPositiveButton("Ok") { _, _ ->
            }
            builder.setMessage("Successfully added")
            builder.create().show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }else{
            val builder = AlertDialog.Builder(requireContext())
            builder.setPositiveButton("Ok") { _, _ ->
            }
            builder.setMessage("Enter the fields properly")
            builder.create().show()
        }
    }

    private fun inputCheck(firstName: String, lastName: String, age: Editable): Boolean{
        return (!(firstName.isEmpty() || lastName.isEmpty() || age.isEmpty()||Integer.parseInt(age.toString())>150))
    }
    val watcher1 = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if(s?.length==0){
                errorage.setText("Field can't be empty")
                errorage.setTextColor(Color.parseColor("#FF0000"))
            }
            else if(Integer.parseInt(s.toString())>150){
                errorage.setText("Age cannot be greater than 150")
                errorage.setTextColor(Color.parseColor("#FF0000"))
            }
            else{
                errorage.setText("Perfect")
                errorage.setTextColor(Color.parseColor("#FF75CA12"))
            }
        }
    }
}