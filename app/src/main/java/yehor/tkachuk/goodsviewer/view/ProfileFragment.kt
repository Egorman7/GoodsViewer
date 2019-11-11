package yehor.tkachuk.goodsviewer.view

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.dialog_remove_image.view.*
import kotlinx.android.synthetic.main.dialog_select_image.view.*
import kotlinx.android.synthetic.main.fragment_profile.*
import yehor.tkachuk.goodsviewer.R
import yehor.tkachuk.goodsviewer.base.BaseFragment
import yehor.tkachuk.goodsviewer.viewmodel.MainViewModel
import java.io.File

class ProfileFragment : BaseFragment<MainViewModel>(MainViewModel::class){
    companion object{
        private const val REQUEST_PERMISSION = 1234

        private const val REQUEST_GALLERY = 333
        private const val REQUEST_CAMERA = 332

        private const val AUTHORITIES = "yehor.tkachuk.goodsviewer.FileProvider"
    }

    private var selectedSource = -1
    private var tempFile = File("")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedViewModel.profile.observe(viewLifecycleOwner, Observer { profile ->
            profile_name.setText(profile.firstName)
            profile_surname.setText(profile.lastName)
            if(File(profile.avatar).exists()){
                profile_img_add.visibility = View.GONE
                Glide.with(profile_img)
                    .load(profile.avatar)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .error(R.drawable.ic_account_no_ava)
                    .placeholder(R.drawable.ic_account_no_ava)
                    .circleCrop()
                    .into(profile_img)
                profile_img_remove.visibility = View.VISIBLE
            } else {
                profile_img_add.visibility = View.VISIBLE
                profile_img_remove.visibility = View.GONE
                profile_img.setImageResource(R.drawable.ic_account_no_ava)
            }
        })

        sharedViewModel.loadProfile()

        profile_img_remove.setOnClickListener {
            showRemoveDialog()
        }

        profile_img_add.setOnClickListener {
            showChooseDialog()
        }

        profile_button_save.setOnClickListener {
            val name = profile_name.text.toString()
            val surname = profile_surname.text.toString()
            sharedViewModel.saveProfile(name, surname)
        }
    }

    private fun showRemoveDialog(){
        context?.also {
            val v = LayoutInflater.from(it).inflate(R.layout.dialog_remove_image, null ,false)
            AlertDialog.Builder(it)
                .setView(v)
                .setCancelable(false)
                .create().apply {
                    v.dialog_remove_ok.setOnClickListener {
                        sharedViewModel.removeAvatar()
                        dismiss()
                    }
                    v.dialog_remove_cancel.setOnClickListener {
                        dismiss()
                    }
                    window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    show()
                }
        }
    }

    private fun showChooseDialog(){
        context?.also {
            val v = LayoutInflater.from(it).inflate(R.layout.dialog_select_image, null, false)
            AlertDialog.Builder(it)
                .setView(v)
                .setCancelable(true)
                .create().apply {
                    v.dialog_select_image_camera.setOnClickListener {
                        launchCamera()
                        dismiss()
                    }
                    v.dialog_select_image_gallery.setOnClickListener {
                        launchGallery()
                        dismiss()
                    }
                    window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    show()
                }
        }
    }

    private fun launchCamera(){
        selectedSource = 1
        if(isPermissionsNotGot()){
            requestPermissions()
        } else {
            context?.also {
                //tempFile = File(Environment.getExternalStorageDirectory(), "temp.jpg")
                tempFile = File.createTempFile("temp", ".jpg", Environment.getExternalStorageDirectory())
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
                    putExtra(
                        MediaStore.EXTRA_OUTPUT,
                        if(Build.VERSION.SDK_INT > 24){
                            FileProvider.getUriForFile(it, AUTHORITIES, tempFile)
                        } else {
                            Uri.fromFile(tempFile)
                        }
                    )
                }
                startActivityForResult(intent, REQUEST_CAMERA)
            }
        }
    }

    private fun launchGallery(){
        selectedSource = 2
        if(isPermissionsNotGot()){
            requestPermissions()
        } else {
            val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                type = "image/*"
            }
            startActivityForResult(intent, REQUEST_GALLERY)
        }
    }

    private fun isPermissionsNotGot(): Boolean{
        return context?.let {
            ActivityCompat.checkSelfPermission(it, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(it, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(it, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        } ?: false
    }

    private fun requestPermissions(){
                requestPermissions(arrayOf(Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_PERMISSION)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == REQUEST_PERMISSION && grantResults.all { it == PackageManager.PERMISSION_GRANTED }){
            when(selectedSource){
                1 -> {launchCamera()}
                2 -> {launchGallery()}
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            when(requestCode){
                REQUEST_GALLERY -> {
                    data?.also { intent ->
                        val uri = intent.data
                        sharedViewModel.saveImage((uri ?: "").toString())
                    }
                }
                REQUEST_CAMERA -> {
                    context?.also {
                        val uri = if(Build.VERSION.SDK_INT > 24){
                            FileProvider.getUriForFile(it, AUTHORITIES, tempFile)
                        } else {
                            Uri.fromFile(tempFile)
                        }
                        sharedViewModel.saveImage(uri.toString())
                    }
                }
            }
        }
    }
}