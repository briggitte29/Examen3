package com.example.myapplication

import android.Manifest
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide

class PerfilFragment : Fragment() {

    lateinit var imagenPerfil: ImageView
    lateinit var editImage: Button
    var progressDialog: ProgressDialog? = null
    private val CAMERA_REQUEST = 100
    private val IMAGE_PICK_CAMERA_REQUEST = 400
    var permissionCamera: Array<String> = emptyArray()
    var imgUri: Uri? = null
    var profileOnCoverImage: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_perfil,container,false)
    }

    override fun onPause() {
        super.onPause()
        Glide.with(this).load(imgUri).into(imagenPerfil)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imagenPerfil = view.findViewById(R.id.imgProfile)
        editImage = view.findViewById(R.id.editImage)
        progressDialog = ProgressDialog(context)
        progressDialog?.setCanceledOnTouchOutside(false)
        permissionCamera = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        editImage.setOnClickListener {
            progressDialog?.setMessage("Actualizar Perfil")
            profileOnCoverImage = "image"
            showImagePicDialog()

        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            CAMERA_REQUEST -> {
                if (grantResults.size > 0) {
                    val cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED
                    val writeStorageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED
                    if (cameraAccepted && writeStorageAccepted) {
                        pickFromCamera()
                    } else {
                        Toast.makeText(
                            context,
                            "Please enable camera and storage permission",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    Toast.makeText(context, "Something went wrong! try again...", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    private fun showImagePicDialog() {
        var options:Array<String> = arrayOf("Camera", "Gallery")
        val builder=AlertDialog.Builder(context)
        builder.setTitle("Pick image from")
        builder.setItems(options, DialogInterface.OnClickListener { dialogInterface, i ->
            if (i == 0) {
                if (!checkCameraPermission()) {
                    requestCameraPermission()
                } else {
                    pickFromCamera()
                }
            }
        })
        builder.create().show()
    }

    private fun pickFromCamera() {
        val contentValues=ContentValues()
        contentValues.put(MediaStore.Images.Media.TITLE, "Temp_pic")
        contentValues.put(MediaStore.Images.Media.DESCRIPTION, "Temp description")
        imgUri= context?.contentResolver?.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        )
        val cameraIntent=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri)
        startActivityForResult(cameraIntent, IMAGE_PICK_CAMERA_REQUEST)
    }

    private fun requestCameraPermission() {
        requestPermissions(permissionCamera, CAMERA_REQUEST)
    }

    private fun checkCameraPermission():Boolean{
        val result1:Boolean=ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)==(PackageManager.PERMISSION_DENIED)
        val result2:Boolean=ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)==(PackageManager.PERMISSION_GRANTED)
        return result1&&result2
    }

}