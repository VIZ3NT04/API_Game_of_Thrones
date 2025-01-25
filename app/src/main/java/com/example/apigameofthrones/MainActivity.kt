package com.example.apigameofthrones

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: AdapterCharacter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        // Configura Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://anapioficeandfire.com/api/") // Asegúrate de incluir "/api/" al final
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(GameOfThronesApiService::class.java)

        // Llama a la API usando corrutinas

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = apiService.getCharacters(page = 583 , pageSize = 1)
                    response.forEach {
                        if (it.name != null && it.name != "" && it.culture != null && it.culture != "") {
                            Log.d("Characters", "Name: ${it.name}, Culture: ${it.culture}")
                        }

                    }
                } catch (e: Exception) {
                    Log.e("Error", "Error fetching characters: ${e.message}")
                }
            }

    }
}