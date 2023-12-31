package com.example.cj

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.cj.databinding.ActivityStopboxResultBinding
import com.google.firebase.storage.FirebaseStorage

class StopboxResultActivity : AppCompatActivity() {
    private var mBinding: ActivityStopboxResultBinding? = null
    //매번 null 체크하지 않도록 확인 후 재선언
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stopbox_result)

        mBinding = ActivityStopboxResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //val intent2 = Intent(this,CheckActivity::class.java)
        // 추가 데이터 수신
        val text = intent.getStringExtra("key")

        val intent = Intent(this, MainActivity::class.java)
        binding.homebtn.setOnClickListener { startActivity(intent) }

        // 이미지를 표시할 ImageView
        val imageView = binding.resultimg

        // 수신한 데이터를 사용하기 전에 null이 아닌지 확인
        if (text != null) {
            Log.d("python", text)
            val textView = binding.text
            textView.text = text
        } else {
            Log.d("python", "받은 텍스트는 null입니다")
        }


        // Firebase Storage 인스턴스 가져오기
        val storage = FirebaseStorage.getInstance()

        // 이미지의 경로를 지정
        val imagePath = "image_result/50_A.jpg"

        // 이미지 다운로드
        val imageRef = storage.reference.child(imagePath)
        imageRef.downloadUrl.addOnSuccessListener { uri ->
            // Glide를 사용하여 이미지 로드 및 표시
            Glide.with(this)
                .load(uri)
                .into(imageView)
        }.addOnFailureListener { exception ->
            // 이미지 다운로드 실패 처리
            // 예를 들어, 이미지가 없는 경우 등
            // 실패 시 처리할 내용을 여기에 추가
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        mBinding = null
    }
}