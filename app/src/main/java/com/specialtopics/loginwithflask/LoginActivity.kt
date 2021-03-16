package com.specialtopics.loginwithflask

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.gson.JsonObject
import com.specialtopics.loginwithflask.retrofit.UserApiService
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import kotlin.math.log

class LoginActivity : AppCompatActivity() {
    val TAG = "LoginActivity"
    val loginUser = JsonObject()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginUser.addProperty("username", "testuser1")
        loginUser.addProperty("pass", "654321")

        btnLogin.setOnClickListener{
              val username = loginUsername.text.toString();
            Log.d(TAG,username)
              val password = loginPassword.text.toString();

            UserApiService
                .service
                .loginUser(username,password)
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



        btnGoRegister.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java);
            startActivity(intent);
        }

    }


}