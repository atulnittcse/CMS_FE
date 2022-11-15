package com.cms.android.cleaningmanagementsystem.app.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.cms.android.cleaningmanagementsystem.app.R
import com.cms.android.cleaningmanagementsystem.app.data.COMPLETED
import com.cms.android.cleaningmanagementsystem.app.data.PENDING
import com.cms.android.cleaningmanagementsystem.app.data.VIEW
import com.cms.android.cleaningmanagementsystem.app.databinding.FragmentHomeBinding
import com.cms.android.cleaningmanagementsystem.app.databinding.FragmentLoginBinding
import com.cms.android.cleaningmanagementsystem.app.databinding.FragmentSubmittaskBinding
import com.cms.android.cleaningmanagementsystem.app.firebase.MainViewmodel
import com.cms.android.cleaningmanagementsystem.app.others.Constants
import com.cms.android.cleaningmanagementsystem.app.others.MyDialog
import com.cms.android.cleaningmanagementsystem.app.others.SharedPref
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*

const val imgCode  = 1001
const val img2Code = 1002
const val img3Code = 1003
const val img4Code = 1004
class SubmitFragment : Fragment(R.layout.fragment_submittask) {

    lateinit var binding : FragmentSubmittaskBinding
    lateinit var viewmodel : MainViewmodel
    lateinit var myDialog: MyDialog
    private val firebaseStorage = Firebase.storage.reference
    var imageUri : Uri? = null
    var image2Uri : Uri? = null
    var image3Uri : Uri? = null
    var image4Uri : Uri? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSubmittaskBinding.bind(view)
        setUI()

    }

    private fun setUI() {

        viewmodel =
            ViewModelProvider(this).get(MainViewmodel::class.java)
        binding.tvTaskname.text = Constants.curTaskData.name
        myDialog = MyDialog(requireContext())

        binding.ivPic.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(requireContext(),android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),1500)
                return@setOnClickListener
            }
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent,imgCode)
        }

        binding.ivPic2.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(requireContext(),android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),1500)
                return@setOnClickListener
            }
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, img2Code)
        }

        binding.ivPic3.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(requireContext(),android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),1500)
                return@setOnClickListener
            }
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, img3Code)
        }


        binding.ivPic4.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(requireContext(),android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),1500)
                return@setOnClickListener
            }
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, img4Code)
        }

        binding.tvTimevalue.text = Calendar.getInstance().time.toString()

        if (Constants.curCleaningData.comment.isNotEmpty()){
            binding.edComment.setText(Constants.curCleaningData.comment)
        }



        if (Constants.curCleaningData.slot != COMPLETED){
            binding.spSlot.setSelection(0)
        }else{
            binding.spSlot.setSelection(1)
        }

        if (Constants.curCleaningData.img.isNotEmpty()){
           Glide.with(requireActivity()).load(Constants.curCleaningData.img).into(binding.ivPic)
        }
        if (Constants.curCleaningData.img2.isNotEmpty()){
            Glide.with(requireActivity()).load(Constants.curCleaningData.img2).into(binding.ivPic2)
        }
        if (Constants.curCleaningData.img3.isNotEmpty()){
            Glide.with(requireActivity()).load(Constants.curCleaningData.img3).into(binding.ivPic3)
        }
        if (Constants.curCleaningData.img4.isNotEmpty()){
            Glide.with(requireActivity()).load(Constants.curCleaningData.img4).into(binding.ivPic4)
        }

        val sharedPref = SharedPref(requireContext())

        val userType = sharedPref.getUserData().userType

        if (userType == "Contractor"){
            binding.ratingbar.setIsIndicator(true)
        }  else{
            if (Constants.curCleaningData.slot != COMPLETED){
                binding.ratingbar.setIsIndicator(false)
            }else{
                binding.ratingbar.setIsIndicator(true)
            }
        }

        binding.ratingbar.rating = Constants.curCleaningData.rating.toFloat()




        binding.tvSubmit.setOnClickListener {

            if (imageUri == null){
                updateTask()
            }else{
               val filename = UUID.randomUUID().toString().substring(0,15)
                uploadImage(filename,imageUri!!,1)
            }

        }


        viewmodel.updateDailyRecordLive.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            myDialog.dismissProgressDialog()
            Toast.makeText(requireContext(), "Successfully updated", Toast.LENGTH_SHORT).show()
            requireActivity().onBackPressed()
        })
        viewmodel.errorUpdateDailyRecordLive.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            myDialog.dismissProgressDialog()
            myDialog.showErrorAlertDialog(it)
        })


    }

    private fun updateTask2() {
        val rating = binding.ratingbar.rating.toInt()
        val comment = binding.edComment.text.toString()
        val slot = binding.spSlot.selectedItem.toString()

        Constants.curDailyRecordData.apply {
            this.tasksList.filter { it.id == Constants.curTaskData.id }[0].apply {
                this.CleaningData.filter { it.shift == Constants.curCleaningData.shift }[0].apply {
                    this.rating = rating
                    this.comment = comment
                    this.status = VIEW
                    this.slot = slot
                }
            }
        }

        viewmodel.createDailyRecord(Constants.curDailyRecordData)

    }

    private fun updateTask() {
        val rating = binding.ratingbar.rating.toInt()
        val comment = binding.edComment.text.toString()
        val slot = binding.spSlot.selectedItem.toString()

        Constants.curDailyRecordData.apply {
            this.tasksList.filter { it.id == Constants.curTaskData.id }[0].apply {
                this.CleaningData.filter { it.shift == Constants.curCleaningData.shift }[0].apply {
                    this.rating = rating
                    this.comment = comment
                    this.status = VIEW
                    this.slot = slot
                }
            }
        }

        viewmodel.createDailyRecord(Constants.curDailyRecordData)

        myDialog.showProgressDialog("Please wait",this)
    }

    @SuppressLint("SimpleDateFormat")
    fun getDate(it : Long) : String{
        val yearFormat = SimpleDateFormat("dd MMM yy")
        return yearFormat.format(it)
    }

    private fun uploadImage(fileName : String,it : Uri,count : Int) {
        myDialog.showProgressDialog("Please wait",this)

        CoroutineScope(Dispatchers.Main).launch {
            firebaseStorage.child("cms/$fileName").putFile(it).addOnSuccessListener {
                firebaseStorage.child("cms/$fileName").downloadUrl.addOnSuccessListener {
                    CoroutineScope(Dispatchers.Main).launch {
                        when(count){
                            1 -> {
                                Constants.curDailyRecordData.apply {
                                    this.tasksList.filter { it.id == Constants.curTaskData.id }[0].apply {
                                        this.CleaningData.filter { it.shift == Constants.curCleaningData.shift }[0].apply {
                                            this.img = it.toString()
                                        }
                                    }
                                }
                                if (image2Uri == null) {
                                    updateTask2()
                                }else{
                                    myDialog.dismissProgressDialog()
                                    val filename = UUID.randomUUID().toString().substring(0,15)
                                    uploadImage(filename,image2Uri!!,2)
                                }
                            }
                            2 -> {
                                Constants.curDailyRecordData.apply {
                                    this.tasksList.filter { it.id == Constants.curTaskData.id }[0].apply {
                                        this.CleaningData.filter { it.shift == Constants.curCleaningData.shift }[0].apply {
                                            this.img2 = it.toString()
                                        }
                                    }
                                }
                                if (image3Uri == null) {
                                    updateTask2()
                                }else{
                                    myDialog.dismissProgressDialog()
                                    val filename = UUID.randomUUID().toString().substring(0, 15)
                                    uploadImage(filename, image3Uri!!, 3)
                                }
                            }
                            3 -> {
                                Constants.curDailyRecordData.apply {
                                    this.tasksList.filter { it.id == Constants.curTaskData.id }[0].apply {
                                        this.CleaningData.filter { it.shift == Constants.curCleaningData.shift }[0].apply {
                                            this.img3 = it.toString()
                                        }
                                    }
                                }
                                if (image4Uri == null){
                                    updateTask2()
                                }else{
                                    myDialog.dismissProgressDialog()
                                    val filename = UUID.randomUUID().toString().substring(0,15)
                                    uploadImage(filename,image4Uri!!,4)
                                }
                            }
                            4 -> {
                                Constants.curDailyRecordData.apply {
                                    this.tasksList.filter { it.id == Constants.curTaskData.id }[0].apply {
                                        this.CleaningData.filter { it.shift == Constants.curCleaningData.shift }[0].apply {
                                            this.img4 = it.toString()
                                        }
                                    }
                                }
                                updateTask2()
                            }
                        }
                    }
                }.addOnFailureListener {
                    myDialog.dismissProgressDialog()
                    myDialog.showErrorAlertDialog(it.message)
                }
            }.addOnFailureListener {
                myDialog.dismissProgressDialog()
                myDialog.showErrorAlertDialog(it.message)
            }
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK){

            when(requestCode){
                imgCode -> {
                    val data2 = data?.extras?.get("data") as Bitmap
                    val uri = getImageUri(data2)
                    imageUri = uri
                    binding.ivPic.setImageURI(uri)
                }
                img2Code -> {
                    val data2 = data?.extras?.get("data") as Bitmap
                    val uri = getImageUri(data2)
                    image2Uri = uri
                    binding.ivPic2.setImageURI(uri)
                }
                img3Code -> {
                    val data2 = data?.extras?.get("data") as Bitmap
                    val uri = getImageUri(data2)
                    image3Uri = uri
                    binding.ivPic3.setImageURI(uri)
                }
                img4Code -> {
                    val data2 = data?.extras?.get("data") as Bitmap
                    val uri = getImageUri(data2)
                    image4Uri = uri
                    binding.ivPic4.setImageURI(uri)
                }
            }

        }
    }

    private fun getImageUri(photo: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        photo.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(
            requireActivity().contentResolver,
            photo,
            "Title",
            null
        )
        return Uri.parse(path)
    }
}