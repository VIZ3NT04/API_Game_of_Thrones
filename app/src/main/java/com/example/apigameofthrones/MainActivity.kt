package com.example.apigameofthrones

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: AdapterCharacter
    private lateinit var recyclerView: RecyclerView
    private val charactersList = mutableListOf<Character>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar RecyclerView
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = AdapterCharacter(charactersList)
        recyclerView.adapter = adapter

        // Configura Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://anapioficeandfire.com/api/") // Asegúrate de incluir "/api/" al final
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(GameOfThronesApiService::class.java)

        // Llama a la API usando corrutinas
        for (page in 500..583) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = apiService.getCharacters(page = page, pageSize = 1)
                    //charactersList.clear()
                    response.forEach {
                        if (it.name != null && it.name != "" && it.culture != null && it.culture != "") {
                            Log.d("Characters", "Name: ${it.name}, Culture: ${it.culture}")
                            charactersList.add(it)
                        }
                    }

                    // Actualizar el RecyclerView en el hilo principal
                    runOnUiThread {
                        adapter.notifyDataSetChanged()
                    }
                } catch (e: Exception) {
                    Log.e("Error", "Error fetching characters: ${e.message}")
                }
            }
        }
    }
}