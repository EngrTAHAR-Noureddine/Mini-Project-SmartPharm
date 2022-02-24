package com.example.smartpharm.adapters


import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.smartpharm.R
import java.io.File
import android.graphics.Bitmap
import android.graphics.BitmapFactory.decodeFile
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.findNavController
import com.example.smartpharm.controllers.FileController.destroyFileSystem
import com.example.smartpharm.models.User
import com.google.gson.Gson


class GridImageAdapter(val context: FragmentActivity?,var data: List<File>?):
    RecyclerView.Adapter<GridViewHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridViewHolder {
        return GridViewHolder(LayoutInflater.from(context).inflate(R.layout.image_view_display_item, parent, false))
    }


    private fun getData(file:String, string: String): String?{
        val prefUser = context?.getSharedPreferences(file, Context.MODE_PRIVATE)
        return prefUser?.getString(string,"")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        if(!data.isNullOrEmpty()){
            var myBitmap : Bitmap = decodeFile(data!![position].absolutePath)
            holder.photoOrdonnance.setImageBitmap(myBitmap)

            holder.photoOrdonnance.setOnClickListener{

                val gson = Gson()
                val json: String = getData("UserProfile", "userProfile") ?: ""
                val user : User? = gson.fromJson(json, User::class.java)

                val pref = context?.getSharedPreferences("ImageOrder", Context.MODE_PRIVATE)
                val editorToken : SharedPreferences.Editor? = pref?.edit()

                editorToken?.apply{
                    putString("imageOrder",data!![position].absolutePath)
                }?.apply()

                if(user!=null && user!!.typeUser=="Pharmacy"){
                    context?.findNavController(R.id.myPharmacyNavHostFragment)?.navigate(R.id.action_destination_Pharmacy_Order_Detail_to_fullscreenImageFragment2)
                }else{
                    context?.findNavController(R.id.myNavHostFragment)?.navigate(R.id.action_destination_Client_Orders_to_fullscreenImageFragment)
                }

           }
            holder.photoOrdonnance.setOnLongClickListener{
                holder.deleteBtn.visibility = View.VISIBLE
                true
            }
            holder.deleteBtn.setOnClickListener{
                destroyFileSystem(position)
                holder.photoOrdonnance.visibility = View.GONE
                holder.deleteBtn.visibility = View.GONE
            }
        }
    }

    override fun getItemCount(): Int = if(!data.isNullOrEmpty()) data?.size!! else 0

}

class GridViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val item = view.findViewById<View>(R.id.ItemImageViewDisplay) as View
    val photoOrdonnance = view.findViewById<ImageView>(R.id.PhotoOrdonnance) as ImageView
    val deleteBtn = view.findViewById<ImageView>(R.id.DeleteBtn) as ImageView
}