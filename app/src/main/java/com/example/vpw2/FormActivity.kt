package com.example.vpw2

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.text.isDigitsOnly
import com.example.vpw2.Database.GlobalVar
import com.example.vpw2.Model.Ayam
import com.example.vpw2.Model.Hewan
import com.example.vpw2.Model.Kambing
import com.example.vpw2.Model.Sapi
import com.example.vpw2.databinding.ActivityFormBinding


class FormActivity : AppCompatActivity() {

    private lateinit var viewBind: ActivityFormBinding
    private lateinit var hewan: Hewan
    private lateinit var ayam:Ayam
    private lateinit var sapi: Sapi
    private lateinit var kambing:Kambing
    private var position = -1
    private var pass = true
    var adaimg: Boolean = false
    private lateinit var uriss: String
    private var speciess = -1

    private val GetResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == Activity.RESULT_OK){   // APLIKASI GALLERY SUKSES MENDAPATKAN IMAGE
            val uri = it.data?.data                 // GET PATH TO IMAGE FROM GALLEY
            viewBind.editimgIV.setImageURI(uri)  // MENAMPILKAN DI IMAGE VIEW
            uriss = uri.toString()
            adaimg = true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.show()

        super.onCreate(savedInstanceState)
        viewBind = ActivityFormBinding.inflate(layoutInflater)
        setContentView(viewBind.root)

        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Edit Animal"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)

        if(position == -1){
            viewBind.editimgIV.setImageDrawable(
                ContextCompat.getDrawable(
                    this, // Context
                    R.drawable.uc_logo // Drawable
                )
            )
        }

        GetIntent()
        Listener()

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun Listener() {
        viewBind.saveBTN.setOnClickListener(){
            var name = viewBind.namaTIL.editText?.text.toString().trim()
            var species = speciess
            var usia = viewBind.usiaTIL.editText?.text.toString().trim()

            pass = true

            if(name.isEmpty()){
                viewBind.namaTIL.error = "Fill in Name"
                pass = false
            }else{
                viewBind.namaTIL.error = ""
            }
            if(species == -1){
                Toast.makeText(this, "Choose Species", Toast.LENGTH_SHORT).show()
                pass = false
            }else{

            }
            if(usia.isEmpty()){
                viewBind.usiaTIL.error = "Fill in Age"
                pass = false
            }else{
                if(!usia.isDigitsOnly()){
                    viewBind.usiaTIL.error = "Age must be a number"
                    pass = false
                }else{
                    viewBind.usiaTIL.error = ""
                }
            }
            if(pass){
                hewan = Hewan(name, speciess.toString(), usia, "")
                save(hewan)
            }
        }

        viewBind.editimgBTN.setOnClickListener{
            val myIntent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            myIntent.type = "image/*"
            GetResult.launch(myIntent)
        }

        viewBind.editimgIV.setOnClickListener{
            val myIntent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            myIntent.type = "image/*"
            GetResult.launch(myIntent)
        }
    }

    private fun save(hewan:Hewan) {
        if (adaimg){
            hewan.imageUri = uriss
        }

        if (position == -1){
            when (hewan.radiochecked) {
                R.id.Chicken.toString() -> {
                    ayam = Ayam(hewan.name, hewan.radiochecked, hewan.usia, "Chicken")
                    GlobalVar.listDataHewan.add(ayam)
                }
                R.id.Cow.toString() -> {
                    sapi = Sapi(hewan.name, hewan.radiochecked, hewan.usia, "Cow")
                    GlobalVar.listDataHewan.add(sapi)
                }
                else -> {
                    kambing = Kambing(hewan.name, hewan.radiochecked, hewan.usia, "Goat")
                    GlobalVar.listDataHewan.add(kambing)
                }
            }
        }
        else{
            when (hewan.radiochecked) {
                R.id.Chicken.toString() -> {
                    ayam = Ayam(hewan.name, hewan.radiochecked, hewan.usia, "Chicken")
                    GlobalVar.listDataHewan[position] = ayam
                }
                R.id.Cow.toString() -> {
                    sapi = Sapi(hewan.name, hewan.radiochecked, hewan.usia, "Cow")
                    GlobalVar.listDataHewan[position] = sapi
                }
                else -> {
                    kambing = Kambing(hewan.name, hewan.radiochecked, hewan.usia, "Goat")
                    GlobalVar.listDataHewan[position] = kambing
                }
            }
        }
        finish()
    }

    private fun GetIntent() {
        position = intent.getIntExtra("position", -1)
        if(position != -1){
            val hewans = GlobalVar.listDataHewan[position]
            Display(hewans)
        }
    }

    private fun Display(hewans: Hewan) {
        viewBind.usiaTIL.editText?.setText(hewans.usia)
        viewBind.namaTIL.editText?.setText(hewans.name)
        viewBind.radioGroup.check(hewans.radiochecked!!.toInt())

        if (hewans.imageUri.isNotEmpty()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                baseContext.getContentResolver().takePersistableUriPermission(
                    Uri.parse(hewans.imageUri),
                    Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                )
            }
            viewBind.editimgIV.setImageURI(Uri.parse(hewans.imageUri))
            adaimg = true
        }else{
            viewBind.editimgIV.setImageDrawable(
                ContextCompat.getDrawable(
                    this, // Context
                    R.drawable.uc_logo // Drawable
                )
            )
        }

    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.Chicken ->
                    if (checked) {
                        speciess = R.id.Chicken
                    }
                R.id.Cow ->
                    if (checked) {
                        speciess = R.id.Cow
                    }
                R.id.Goat ->
                    if (checked) {
                        speciess = R.id.Goat
                    }
            }
        }
    }
}