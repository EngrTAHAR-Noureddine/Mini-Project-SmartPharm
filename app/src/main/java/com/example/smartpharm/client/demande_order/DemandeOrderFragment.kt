package com.example.smartpharm.client.demande_order

import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.example.smartpharm.R
import com.example.smartpharm.databinding.DemandeOrderFragmentBinding
import kotlinx.android.synthetic.main.client_home_fragment.*
import java.io.File
import androidx.recyclerview.widget.GridLayoutManager
import com.example.smartpharm.client.home.ListPharmacistsAdapter

object FileList{
    var fileList = MutableLiveData<List<File>>()
}


class DemandeOrderFragment : Fragment() {

    private lateinit var binding: DemandeOrderFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.demande_order_fragment,container,false)

        binding.floatingBtnCamera.setOnClickListener{

            activity?.findNavController(R.id.myNavHostFragment)?.navigate(R.id.to_Camera_Fragment)

        }

       // var gpath: String = context?.getExternalFilesDir()?.absolutePath
        //var spath = "Download"
        var fullpath = getOutputDirectory()
        Log.w("fullpath", "" + fullpath)

        ImageReaderNew(fullpath)


           FileList.fileList.observe(viewLifecycleOwner, {
               it?.let {
                   Log.v("FIleListRecycle", it.size.toString())
                   binding.GridRecycleView.layoutManager = GridLayoutManager(context, 3)
                   binding.GridRecycleView.adapter = GridImageAdapter(activity, it)
               }
           })




        return binding.root
    }
    private fun getOutputDirectory(): File {
        val mediaDir = context?.externalMediaDirs?.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() } }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else requireContext().filesDir
    }

    override fun onDestroy() {
        super.onDestroy()
        FileList.fileList.value = null
    }

    private fun ImageReaderNew(root: File) {
        val files: ArrayList<File> = ArrayList()
        val listAllFiles = root.listFiles()
        Log.w("listAllFiles", "size : " + listAllFiles.size)
        if (listAllFiles != null && listAllFiles.isNotEmpty()) {
            for (currentFile in listAllFiles) {
                if (currentFile.name.endsWith(".jpg")) {
                    // File absolute path
                    Log.e("fullpath", currentFile.absolutePath)
                    // File Name
                    Log.e("fullpath", currentFile.name)
                    files.add(currentFile.absoluteFile)
                }
            }
            FileList.fileList.value = files
            Log.w("fileList", "" + FileList.fileList.value?.size)


        }
    }
}