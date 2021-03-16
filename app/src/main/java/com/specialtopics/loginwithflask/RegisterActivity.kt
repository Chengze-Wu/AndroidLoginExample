package com.specialtopics.loginwithflask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.specialtopics.loginwithflask.retrofit.UserApiService
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    val TAG = "Register Activity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btnRegister.setOnClickListener{
            val username = registerUsername.text.toString();
            Log.d(TAG,username)
            val password = registerPassword.text.toString();

            UserApiService
                .service
                .registerUser(username,password)
                .enqueue(object :retrofit2.Callback<ResponseBody>{
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Log.d(TAG,t.message.toString())
                    }

                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        if(response.isSuccessful){
                            val message = response.body()?.string().toString()
                            Log.d(TAG,message)
                            Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
                        }
                    }
                })
        }
    }
}