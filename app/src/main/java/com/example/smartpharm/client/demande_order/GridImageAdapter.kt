package com.example.smartpharm.client.demande_order


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
import com.example.smartpharm.controllers.FileController.destroyFileSystem


class GridImageAdapter(val context: FragmentActivity?,var data: List<File>?):
    RecyclerView.Adapter<GridViewHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridViewHolder {
        return GridViewHolder(LayoutInflater.from(context).inflate(R.layout.image_view_display_item, parent, false))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        if(!data.isNullOrEmpty()){
            var myBitmap : Bitmap = decodeFile(data!![position].absolutePath)
            holder.photoOrdonnance.setImageBitmap(myBitmap)
            holder.photoOrdonnance.setOnClickListener{
                holder.deleteBtn.visibility = View.VISIBLE
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