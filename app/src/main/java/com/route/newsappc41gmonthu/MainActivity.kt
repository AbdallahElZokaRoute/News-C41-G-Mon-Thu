package com.route.newsappc41gmonthu

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.route.newsappc41gmonthu.api.ApiManager
import com.route.newsappc41gmonthu.api.model.SourcesResponse
import com.route.newsappc41gmonthu.ui.theme.NewsAppC41GMonThuTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    // 1- DiffUtil ->
    // 2- APIs & networking
    // 3- LaunchedEffect
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppC41GMonThuTheme {

                LaunchedEffect(Unit) {
                    // when counter changes Launched Effect will trigger
                    ApiManager.newsService.getSources()
                        .enqueue(object :
                            Callback<SourcesResponse> {
                            override fun onResponse(
                                call: Call<SourcesResponse>,
                                response: Response<SourcesResponse>
                            ) {
                                Log.e("TAG", "onResponse: ${response.body()}")
                            }

                            override fun onFailure(
                                call: Call<SourcesResponse>,
                                throwable: Throwable
                            ) {
                                Log.e("TAG", "onFailure: ${throwable.message} ")
                            }


                        }) // Run on Background Thread and returns result on Main Thread
//                        .execute() // Run on Main Thread

                }
            }

        }
    }
}
