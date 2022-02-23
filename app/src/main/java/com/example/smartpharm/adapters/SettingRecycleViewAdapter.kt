package com.example.smartpharm.settings

import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.smartpharm.R
import com.example.smartpharm.models.User

internal class InputTypeItem(private val name: String, private val value: Int)

class SettingRecycleViewAdapter(val context: FragmentActivity?,val user: User):
    RecyclerView.Adapter<ViewSettingHolder>() {

    private var inputTypes: ArrayList<InputTypeItem>? = null
    private val titles = listOf<String>("Nom :","Localisation :","Mot de passe : ","Facebook :","Instagram :","Nemero de Telephone :")
    private val placeholder = listOf<String>("Nom...","Localisation...","Mot de passe...","Facebook...","Instagram...","Nemero de Telephone...")
    private var isEditable = MutableLiveData<Boolean>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewSettingHolder {
        isEditable.value = false
        return ViewSettingHolder(LayoutInflater.from(context).inflate(R.layout.field_setting_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewSettingHolder, position: Int) {

        holder.titleHeaderUser.text = titles[position]

        val text : String = when(position){
            1-> user.locationUser
            2-> user.passwordUser
            3 -> user.facebookAccount
            4 -> user.instagramAccount
            5 -> user.phoneNumber
            else -> user.name
        }
        holder.inputFieldUser.setText(text)

        val inputNumber = when(position){
            1-> InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            2->InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            3 -> InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_NORMAL
            4->InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_NORMAL
            5 -> InputType.TYPE_CLASS_PHONE
            else -> InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PERSON_NAME
        }
        holder.inputFieldUser.inputType = inputNumber

        holder.editFieldUser.setOnClickListener{
            isEditable.value = !isEditable.value!!
            holder.inputFieldUser.isEnabled = (isEditable.value == true)
            holder.editFieldUser.setImageResource(if(isEditable.value == true) R.drawable.ic_save_24 else R.drawable.ic_edit_24)
        }



    }

    override fun getItemCount() = titles.size


}
class ViewSettingHolder(view: View) : RecyclerView.ViewHolder(view) {
    val item = view.findViewById<View>(R.id.itemSetting) as View
    val titleHeaderUser = view.findViewById<TextView>(R.id.TitleHeaderUser) as TextView
    val inputFieldUser = view.findViewById<EditText>(R.id.InputFieldUser) as EditText
    val editFieldUser = view.findViewById<ImageButton>(R.id.editFieldUser) as ImageButton
}