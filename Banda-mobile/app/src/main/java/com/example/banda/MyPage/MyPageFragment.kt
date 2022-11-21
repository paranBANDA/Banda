package com.example.banda.MyPage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.banda.LoginActivity
import com.example.banda.R

class MyPageFragment : Fragment(){
    private lateinit var firstDogName: String
    private lateinit var email: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun loadFirstDog(){
        val pref = activity?.getSharedPreferences("pref",0)
        firstDogName = pref?.getString("pet","").toString()
    }
    private fun loadUserEmail() {
        val pref = activity?.getSharedPreferences("pref", 0)
        email = pref?.getString("email", "").toString()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadFirstDog()
        loadUserEmail()
        var logout = getView()?.findViewById<Button>(R.id.btn_logout)
        logout?.setOnClickListener {
            val intent = Intent(activity, LoginActivity::class.java)
            val pref = requireActivity().getPreferences(0)
            val editor = pref.edit()
            editor.putString("email","")
            editor.apply()
            startActivity(intent)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mypage, container, false)
    }
}