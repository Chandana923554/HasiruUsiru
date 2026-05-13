package com.example.hasiruusiru

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddTreeActivity : AppCompatActivity() {

    private lateinit var photoUri: Uri
    private lateinit var imgPreview: ImageView
    private lateinit var tvLocation: TextView
    private lateinit var locationManager: LocationManager
    private val CAMERA_REQUEST = 100
    private val CAMERA_PERMISSION = 101
    private val LOCATION_PERMISSION = 102
    private var currentLat = 12.9716
    private var currentLng = 77.5946

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_tree)

        // Species Spinner
        val spinner = findViewById<Spinner>(R.id.spinnerSpecies)
        val species = listOf(
            "Neem", "Peepal", "Honge",
            "Mango", "Banyan"
        )
        val adapter = ArrayAdapter(this,
            android.R.layout.simple_spinner_item, species)
        adapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        // Views
        imgPreview = findViewById(R.id.imgPreview)
        tvLocation = findViewById(R.id.tvLocation)
        val btnPhoto = findViewById<Button>(R.id.btnTakePhoto)
        val btnSave = findViewById<Button>(R.id.btnSaveTree)
        val btnGetLocation = findViewById<Button>(R.id.btnGetLocation)
        val etGirth = findViewById<EditText>(R.id.etGirth)
        val checkEmptyPit = findViewById<CheckBox>(R.id.checkEmptyPit)

        // Location Manager
        locationManager = getSystemService(
            LOCATION_SERVICE) as LocationManager

        // Get Location Button
        btnGetLocation.setOnClickListener {
            getLocation()
        }

        // Camera Button
        btnPhoto.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                openCamera()
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA),
                    CAMERA_PERMISSION
                )
            }
        }

        // Save Button
        btnSave.setOnClickListener {
            val selectedSpecies = spinner.selectedItem.toString()
            val girth = etGirth.text.toString()
            val isEmptyPit = checkEmptyPit.isChecked

            if (girth.isEmpty() && !isEmptyPit) {
                Toast.makeText(this,
                    "Please enter tree girth!",
                    Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val girthValue = if (girth.isEmpty()) 0f
            else girth.toFloat()

            val speciesFactor = when (selectedSpecies) {
                "Neem" -> 1.5f
                "Peepal" -> 2.0f
                "Honge" -> 1.8f
                "Mango" -> 1.3f
                "Banyan" -> 1.9f
                else -> 1.0f
            }

            val oxygenScore = girthValue * speciesFactor

            // Save to Room DB
            val db = AppDatabase.getInstance(this)
            val tree = Tree(
                species = selectedSpecies,
                girth = girthValue,
                health = "Good",
                isEmptyPit = isEmptyPit,
                latitude = currentLat,
                longitude = currentLng
            )
            db.treeDao().insertTree(tree)

            val message = if (isEmptyPit) {
                "Empty Pit marked at location!"
            } else {
                "Tree saved! O2 Score: $oxygenScore"
            }

            Toast.makeText(this,
                message, Toast.LENGTH_LONG).show()
            finish()
        }

        // Auto get location on start
        getLocation()
    }

    private fun getLocation() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            tvLocation.text = "Getting location..."

            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                0L,
                5f, // 5 meter accuracy
                object : LocationListener {
                    override fun onLocationChanged(
                        location: Location
                    ) {
                        currentLat = location.latitude
                        currentLng = location.longitude
                        val accuracy = location.accuracy

                        tvLocation.text =
                            "📍 Lat: ${"%.4f".format(currentLat)}" +
                                    "\nLng: ${"%.4f".format(currentLng)}" +
                                    "\nAccuracy: ${accuracy}m"

                        // Stop after getting location
                        locationManager
                            .removeUpdates(this)
                    }
                }
            )
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
                LOCATION_PERMISSION
            )
        }
    }

    private fun openCamera() {
        val timeStamp = SimpleDateFormat(
            "yyyyMMdd_HHmmss",
            Locale.getDefault()
        ).format(Date())

        val photoFile = File(
            getExternalFilesDir(
                Environment.DIRECTORY_PICTURES
            ),
            "TREE_${timeStamp}.jpg"
        )

        photoUri = FileProvider.getUriForFile(
            this,
            "com.example.hasiruusiru.fileprovider",
            photoFile
        )

        val cameraIntent = Intent(
            MediaStore.ACTION_IMAGE_CAPTURE
        )
        cameraIntent.putExtra(
            MediaStore.EXTRA_OUTPUT, photoUri
        )
        startActivityForResult(
            cameraIntent, CAMERA_REQUEST
        )
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(
            requestCode, resultCode, data
        )
        if (requestCode == CAMERA_REQUEST &&
            resultCode == RESULT_OK
        ) {
            imgPreview.setImageURI(photoUri)
            Toast.makeText(this,
                "Photo captured!",
                Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(
            requestCode, permissions, grantResults
        )
        when (requestCode) {
            CAMERA_PERMISSION -> {
                if (grantResults.isNotEmpty() &&
                    grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    openCamera()
                }
            }
            LOCATION_PERMISSION -> {
                if (grantResults.isNotEmpty() &&
                    grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    getLocation()
                }
            }
        }
    }
}