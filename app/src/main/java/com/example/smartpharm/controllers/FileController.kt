package com.example.smartpharm.controllers


import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.smartpharm.R
import java.io.File




object FileController {

    private var _listFile = MutableLiveData<List<File>?>()

    private var _rootFile = MutableLiveData<File?>()

    val listFile : LiveData<List<File>?>
        get() = _listFile


    private fun getOutputDirectory(context:Context){
        val mediaDir = context?.externalMediaDirs?.firstOrNull()?.let {
            File(it, context.resources.getString(R.string.app_name)).apply { mkdirs() } }
        _rootFile.value = if (mediaDir != null && mediaDir.exists())
                                    mediaDir else context.filesDir
    }

    fun destroyListFileVariable(){
        _listFile.value = null
        _rootFile.value = null
    }

    fun destroyFileSystem(position:Int){
        if(_listFile.value!=null){
            _listFile.value!![position].deleteRecursively()
            _listFile.value = _listFile.value!!.filterIndexed { index, _ -> index != position }
        }
    }

     fun destroyAllFiles(){
        if(_listFile.value!=null){
            for (i in _listFile.value!!.indices) {
                    _listFile.value!![i].deleteRecursively()
            }
            destroyListFileVariable()
        }
    }

    fun emptyDir(context:Context){
        getOutputDirectory(context)
        val listAllFiles = _rootFile.value?.listFiles()
        if (listAllFiles != null && listAllFiles.isNotEmpty()) {
            for (currentFile in listAllFiles) {
                currentFile.deleteRecursively()
            }
        }
    }

    fun imagesReader(context:Context) {
        getOutputDirectory(context)
        val files: ArrayList<File> = ArrayList()
        val listAllFiles = _rootFile.value?.listFiles()
        if (listAllFiles != null && listAllFiles.isNotEmpty()) {
            for (currentFile in listAllFiles) {
                if (currentFile.name.endsWith(".jpg")) {
                    files.add(currentFile.absoluteFile)
                }
            }
            _listFile.value = files
        }
    }




}