package yehor.tkachuk.goodsviewer.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Single
import yehor.tkachuk.goodsviewer.model.Good
import yehor.tkachuk.goodsviewer.model.SaveResult
import java.io.File
import java.lang.Exception

class LocalDataManagerImpl(private val context: Context) : LocalDataManager{
    companion object{
        private const val FILENAME = "saved.json"
        private const val IMAGES_DIR = "images"
    }
    private val savedList = mutableListOf<Good>()

    override fun save(good: Good): Single<SaveResult> {
        return Single.create { emitter ->
            if(savedList.any { it.id == good.id }){
                emitter.onSuccess(SaveResult.exists("Product already saved!"))
            } else {
                try {
                    savedList.add(good)
                    val file = getFile()
                    val list = Gson().toJson(savedList)
                    file.writeBytes(list.toByteArray())
                    val dir = getImagesDir()
                    Glide.with(context)
                        .asBitmap()
                        .load(good.getImageUrl())
                        .toBitmap { image ->
                            val imageFile = File(dir, good.getLocalImage())
                            val stream = imageFile.outputStream()
                            image.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                            stream.flush()
                            stream.close()
                        }
                    emitter.onSuccess(SaveResult.saved("Saved successfully!"))
                } catch (e: Exception){
                    emitter.onSuccess(SaveResult.error("Can't save product!"))
                }
            }
        }

    }

    override fun getLocalList(): Single<List<Good>> {
        return Single.create { emitter ->
            try {
                val list = String(getFile().readBytes())
                val type = object : TypeToken<List<Good>>() {}.type
                val parsedList: List<Good> = Gson().fromJson(list, type)
                savedList.clear()
                savedList.addAll(parsedList)
            } catch (e: Exception){ }
            finally {
                emitter.onSuccess(savedList)
            }
        }
    }

    private fun getFile(): File{
        return File(context.filesDir, FILENAME)
    }

    private fun getImagesDir(): File{
        return File(context.filesDir, IMAGES_DIR).also {
            if(!it.exists()) it.mkdir()
        }
    }

    private fun RequestBuilder<Bitmap>.toBitmap(todo: (Bitmap) -> Unit){
        into(object: CustomTarget<Bitmap>(){
            override fun onLoadCleared(placeholder: Drawable?) {}
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                todo(resource)
            }
        })
    }
}