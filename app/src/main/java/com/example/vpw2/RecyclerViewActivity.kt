package com.example.vpw2

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vpw2.Adapter.ListDataRVAdapter
import com.example.vpw2.Database.GlobalVar
import com.example.vpw2.Interface.CardListener
import com.example.vpw2.databinding.ActivityRecyclerViewBinding
import com.example.vpw2.Model.Biji
import com.example.vpw2.Model.Hewan
import com.example.vpw2.Model.Rumput
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class RecyclerViewActivity : AppCompatActivity(), CardListener {

    private lateinit var viewBind: ActivityRecyclerViewBinding
    private var adapter = ListDataRVAdapter(GlobalVar.listDataHewan, this)
    private var filtered = ListDataRVAdapter(GlobalVar.listFiltered, this)
    private lateinit var selectedFilter: String
    private var selectedFilterIndex: Int = 0
    private val filters = arrayOf("All", "Chicken", "Cow", "Goat")


    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()

        super.onCreate(savedInstanceState)
        viewBind = ActivityRecyclerViewBinding.inflate(layoutInflater)
        setContentView(viewBind.root)

        CheckPermissions()
        setupRecyclerView()
        listener()
    }

    private fun listener() {
        viewBind.addHewanFAB.setOnClickListener{
            val myIntent = Intent(this, FormActivity::class.java)
            startActivity(myIntent)
        }

        viewBind.filterBTN.setOnClickListener(){
                selectedFilter = filters[selectedFilterIndex]
                MaterialAlertDialogBuilder(this)
                    .setTitle("Filters Available")
                    .setSingleChoiceItems(filters, selectedFilterIndex) { dialog_, which ->
                        selectedFilterIndex = which
                        selectedFilter = filters[which]
                    }
                    .setPositiveButton("Ok") { dialog, which ->
                        setupRecyclerView()
                        Toast.makeText(this, "$selectedFilter Selected", Toast.LENGTH_SHORT).show()
                    }
                    .setNegativeButton("Cancel") { dialog, which ->
                        dialog.dismiss()
                    }
                    .show()

        }
    }

    private fun setupRecyclerView() {
        when (selectedFilterIndex) {
            0 -> {
                adapter = ListDataRVAdapter(GlobalVar.listDataHewan, this)
                val layoutManager = LinearLayoutManager(baseContext)
                viewBind.rvHewan.layoutManager = layoutManager   // Set layout
                viewBind.rvHewan.adapter = adapter   // Set adapter
            }
            1 -> {
                GlobalVar.listFiltered.clear()
                for (animal in GlobalVar.listDataHewan){
                    if(animal.species == "Chicken"){
                        GlobalVar.listFiltered.add(animal)
                    }
                    adapter = filtered
                    val layoutManager = LinearLayoutManager(baseContext)
                    viewBind.rvHewan.layoutManager = layoutManager   // Set layout
                    viewBind.rvHewan.adapter = adapter   // Set adapter
                    adapter.notifyDataSetChanged()
                }
            }
            2 -> {
                GlobalVar.listFiltered.clear()
                for (animal in GlobalVar.listDataHewan){
                    if(animal.species == "Cow"){
                        GlobalVar.listFiltered.add(animal)
                    }
                    adapter = filtered
                    val layoutManager = LinearLayoutManager(baseContext)
                    viewBind.rvHewan.layoutManager = layoutManager   // Set layout
                    viewBind.rvHewan.adapter = adapter   // Set adapter
                    adapter.notifyDataSetChanged()
                }
            }
            3 -> {
                GlobalVar.listFiltered.clear()
                for (animal in GlobalVar.listDataHewan){
                    if(animal.species == "Goat"){
                        GlobalVar.listFiltered.add(animal)
                    }
                    adapter = filtered
                    val layoutManager = LinearLayoutManager(baseContext)
                    viewBind.rvHewan.layoutManager = layoutManager   // Set layout
                    viewBind.rvHewan.adapter = adapter   // Set adapter
                    adapter.notifyDataSetChanged()
                }
            }
        }

    }

    private fun CheckPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            // Requesting the permission
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), GlobalVar.STORAGE_PERMISSION_CODE)
        } else {
            Toast.makeText(this, "Storage Permission already granted", Toast.LENGTH_SHORT).show()
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            // Requesting the permission
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), GlobalVar.STORAGE_PERMISSION_CODE)
        } else {
            Toast.makeText(this, "Storage Permission already granted", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == GlobalVar.STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Storage Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Storage Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }

    override fun onEdit(position: Int) {
        val myIntent = Intent(this, FormActivity::class.java).apply {
            putExtra("position", position)
        }
        startActivity(myIntent)
    }

    override fun onDelete(position: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure you want to Delete?")
            .setCancelable(false)
            .setPositiveButton("Yes") { dialog, id ->
                GlobalVar.listDataHewan.removeAt(position)
                adapter.notifyDataSetChanged()
                Toast.makeText(this, "Deletion Successful", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("No") { dialog, id ->
                // Dismiss the dialog
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }

    override fun onFeed(position: Int) {
        if(GlobalVar.listDataHewan[position].radiochecked == R.id.Chicken.toString()){
            var biji = Biji("Biji")
            Toast.makeText(this, GlobalVar.listDataHewan[position].eat(biji), Toast.LENGTH_SHORT).show()
        }else{
            var rumput = Rumput("Rumput")
            Toast.makeText(this, GlobalVar.listDataHewan[position].eat(rumput), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSound(position: Int) {
        Toast.makeText(this, GlobalVar.listDataHewan[position].sound(), Toast.LENGTH_SHORT).show()

    }


}