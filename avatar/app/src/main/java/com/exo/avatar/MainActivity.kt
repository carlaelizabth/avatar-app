package com.exo.avatar

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.exo.avatar.connection.CharacterDto
import com.exo.avatar.connection.CharactersApi
import com.exo.avatar.databinding.ActivityMainBinding
import com.exo.avatar.util.Constants
import com.exo.avatar.util.Constants.CHARACTERS_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.URL_BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val characterApi = retrofit.create(CharactersApi::class.java)

        val call: Call<List<CharacterDto>> = characterApi.getCharacters(CHARACTERS_URL)

        call.enqueue(object: Callback<List<CharacterDto>>{
            override fun onResponse(
                p0: Call<List<CharacterDto>>,
                response: Response<List<CharacterDto>>
            ) {
                binding.pbMain.visibility = View.INVISIBLE
                Log.d(Constants.LOGTAG, getString(R.string.tMessLog,response.toString()))
                response.body()?.let { characters ->
                    val characterAdapter = CharacterAdapter(characters) {character ->
                        character.id?.let { id ->
                            val bundle = bundleOf(
                                "id" to id
                            )

                            val intent = Intent(this@MainActivity,Details::class.java)
                            intent.putExtras(bundle)
                            startActivity(intent)
                        }

                    }

                    binding.rvMain.apply {
                        layoutManager = LinearLayoutManager(this@MainActivity)
                        adapter = characterAdapter
                    }

                }
            }

            override fun onFailure(p0: Call<List<CharacterDto>>, t: Throwable) {
                binding.pbMain.visibility = View.INVISIBLE
                Toast.makeText(this@MainActivity, getString(R.string.tFConnection), Toast.LENGTH_SHORT).show()
                AlertDialog.Builder(this@MainActivity)
                    .setTitle(getString(R.string.tFConnection))
                    .setMessage(getString(R.string.tQFail))
                    .setPositiveButton(getString(R.string.tReintentar)) { dialog, _ ->
                        dialog.dismiss()
                        characterApi.getCharacters(CHARACTERS_URL).enqueue(this)
                    }
                    .setNegativeButton(getString(R.string.tCancel)) { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()

            }

        })


    }
}