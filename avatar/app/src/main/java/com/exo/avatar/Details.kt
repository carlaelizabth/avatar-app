package com.exo.avatar

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.exo.avatar.connection.CharacterAdvDto
import com.exo.avatar.connection.CharactersApi
import com.exo.avatar.databinding.ActivityDetailsBinding
import com.exo.avatar.util.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Details : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras
        val id = bundle?.getString("id","")
        Log.d(Constants.LOGTAG, getString(R.string.tIdRecibido, id))

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.URL_BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val characterApi = retrofit.create(CharactersApi::class.java)
        val call: Call<CharacterAdvDto> = characterApi.getCharacterDetail(id!!)
        call.enqueue(object: Callback<CharacterAdvDto>{
            override fun onResponse(
                p0: Call<CharacterAdvDto>,
                response: Response<CharacterAdvDto>
            ) {
                binding.apply{
                    tvNameCharacter.text = response.body()?.name
                    tvAffiliation.text = response.body()?.affiliation
                    tvGender.text = response.body()?.gender
                    tvProf.text = response.body()?.profession
                    tvPosition.text = response.body()?.position

                    Glide.with(this@Details)
                        .load(response.body()?.photoUrl)
                        .into(ivPCharacter)
                    var colorElement = 0
                    val element = response.body()?.weapon
                    when {
                        element == null -> ivWeapon.visibility = View.INVISIBLE
                        element.contains(getString(R.string.tAir)) -> ivWeapon.setImageResource(R.drawable.air)
                        element.contains(getString(R.string.tEarth)) -> { ivWeapon.setImageResource(R.drawable.earth)
                                                                            colorElement = 1}
                        element.contains(getString(R.string.tWater))-> { ivWeapon.setImageResource(R.drawable.water)
                                                                            colorElement = 2}
                        element.contains(getString(R.string.tFire)) -> { ivWeapon.setImageResource(R.drawable.fire)
                                                                            colorElement = 3}
                        element.contains(getString(R.string.tTheElements)) -> ivWeapon.setImageResource(R.drawable.the_elements)
                        else -> ivWeapon.visibility = View.INVISIBLE
                    }

                    updateUIColors(colorElement)


                }


            }

            override fun onFailure(p0: Call<CharacterAdvDto>, t: Throwable) {
                Toast.makeText(this@Details, getString(R.string.tFConnection), Toast.LENGTH_SHORT).show()
                AlertDialog.Builder(this@Details)
                    .setTitle(getString(R.string.tFConnection))
                    .setMessage(getString(R.string.tQFail))
                    .setPositiveButton(getString(R.string.tReintentar)) { dialog, _ ->
                        dialog.dismiss()
                        characterApi.getCharacterDetail(id).enqueue(this)
                    }
                    .setNegativeButton(getString(R.string.tCancel)) { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }

        })



    }

    private fun updateUIColors(status: Int) {
        val textViewsD =
            listOf(binding.tvDAff, binding.tvDGenrer, binding.tvDProfession, binding.tvDPosition)
        val textViewsC =
            listOf(binding.tvAffiliation, binding.tvGender, binding.tvProf, binding.tvPosition)

        when (status) {
            1 -> {
                binding.cCardBg.setBackgroundColor(ContextCompat.getColor(this, R.color.earth_background))
                binding.clName.setBackgroundColor(ContextCompat.getColor(this, R.color.earth_text_primary))
                textViewsD.forEach { it.setTextColor(ContextCompat.getColor(this, R.color.earth_text_primary)) }
                textViewsC.forEach { it.setTextColor(ContextCompat.getColor(this, R.color.earth_text_secondary)) }
            }

            2 -> {
                binding.cCardBg.setBackgroundColor(ContextCompat.getColor(this, R.color.water_background))
                binding.clName.setBackgroundColor(ContextCompat.getColor(this, R.color.water_text_primary))
                textViewsD.forEach { it.setTextColor(ContextCompat.getColor(this, R.color.water_text_primary)) }
                textViewsC.forEach { it.setTextColor(ContextCompat.getColor(this, R.color.water_text_secondary)) }
            }

            3 -> {
                binding.cCardBg.setBackgroundColor(ContextCompat.getColor(this, R.color.fire_background))
                binding.clName.setBackgroundColor(ContextCompat.getColor(this, R.color.fire_text_primary))
                textViewsD.forEach { it.setTextColor(ContextCompat.getColor(this, R.color.fire_text_primary)) }
                textViewsC.forEach { it.setTextColor(ContextCompat.getColor(this, R.color.fire_text_secondary)) }
            }
            else -> {
            }
        }
    }

}