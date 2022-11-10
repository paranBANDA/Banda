package com.example.banda.polaroid

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.kakaologin.DogProfileAdapter
import com.example.banda.DogProfileData
import com.example.banda.PolariodAdapter
import com.example.banda.databinding.ChangeDogDialogBinding

class ChangeDogDialog(context: Context): Dialog(context) {

    private lateinit var binding: ChangeDogDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 만들어놓은 dialog_profile.xml 뷰를 띄운다.
        binding = ChangeDogDialogBinding.inflate(layoutInflater)
        // 뒤로가기 버튼, 빈 화면 터치를 통해 dialog가 사라지지 않도록
        //setCancelable(false)
        val datas = mutableListOf<DogProfileData>()
        datas.apply{

        }


        binding.changeDogDialogRecyclerView.adapter = DogProfileAdapter(datas)
        binding.changeDogDialogRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() = with(binding) {

        // background를 투명하게 만듦
        // (중요) Dialog는 내부적으로 뒤에 흰 사각형 배경이 존재하므로, 배경을 투명하게 만들지 않으면
        // corner radius의 적용이 보이지 않는다.
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        // OK Button 클릭에 대한 Callback 처리. 이 부분은 상황에 따라 자유롭게!
    }
}