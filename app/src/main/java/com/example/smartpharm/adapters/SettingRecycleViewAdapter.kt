package com.example.smartpharm.adapters

import android.content.Context
import android.content.SharedPreferences
import android.text.Editable
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.smartpharm.R
import com.example.smartpharm.controllers.UserController.updateUser
import com.example.smartpharm.models.User
import com.google.gson.Gson

internal class InputTypeItem(private val name: String, private val value: Int)

class SettingRecycleViewAdapter(val context: FragmentActivity?,val user: User):
    RecyclerView.Adapter<ViewSettingHolder>() {

    private var inputTypes: ArrayList<InputTypeItem>? = null
    private val titles = listOf("Nom :","Localisation :","Mot de passe : ","Facebook :","Instagram :","Nemero de Telephone :")
    private val placeholder = listOf("Nom...","Localisation...","Mot de passe...","Facebook...","Instagram...","Nemero de Telephone...")
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

        with(holder) {
            inputFieldUser.inputType = inputNumber

            inputFieldUser.setOnEditorActionListener {
                    _, actionId, _ ->
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    doEditing(holder, position)
                    true
                } else {
                    false
                }
            }
        }

        holder.editFieldUser.setOnClickListener{
            doEditing(holder, position)
        }




    }

    private fun doEditing(holder: ViewSettingHolder, position: Int){
        isEditable.value = !isEditable.value!!
        holder.inputFieldUser.isEnabled = (isEditable.value == true)
        holder.editFieldUser.setImageResource(if(isEditable.value == true) R.drawable.ic_save_24 else R.drawable.ic_edit_24)
        val textInput : Editable? = holder.inputFieldUser.text

        val pref = context!!.getSharedPreferences("UserProfile", Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = pref.edit()
        val gson = Gson()



        if(!textInput.isNullOrEmpty()){

            when(position){
                1->{
                    user.locationUser = textInput.toString()
                    val json = gson.toJson(user)
                    editor.apply{
                        putString("userProfile",json)
                    }.apply()
                    updateUser(user, textInput.toString(), "locationUser")
                }
                2->{
                    user.passwordUser = textInput.toString()
                    val json = gson.toJson(user)
                    editor.apply{
                        putString("userProfile",json)
                    }.apply()

                    updateUser(user, textInput.toString(), "passwordUser")
                }
                3->{
                    user.facebookAccount = textInput.toString()
                    val json = gson.toJson(user)
                    editor.apply{
                        putString("userProfile",json)
                    }.apply()
                    updateUser(user, textInput.toString(), "facebookAccount")
                }
                4->{
                    user.instagramAccount = textInput.toString()
                    val json = gson.toJson(user)
                    editor.apply{
                        putString("userProfile",json)
                    }.apply()
                    updateUser(user, textInput.toString(), "instagramAccount")
                }
                5->{
                    user.phoneNumber = textInput.toString()
                    val json = gson.toJson(user)
                    editor.apply{
                        putString("userProfile",json)
                    }.apply()
                    updateUser(user, textInput.toString(), "phoneNumber")
                }
                else -> {
                    user.name = textInput.toString()
                    val json = gson.toJson(user)
                    editor.apply{
                        putString("userProfile",json)
                    }.apply()
                    updateUser(user, textInput.toString(), "name")
                }
            }

        }
    }

    override fun getItemCount() = titles.size


}
class ViewSettingHolder(view: View) : RecyclerView.ViewHolder(view) {
    val item = view.findViewById(R.id.itemSetting) as View
    val titleHeaderUser = view.findViewById(R.id.TitleHeaderUser) as TextView
    val inputFieldUser = view.findViewById(R.id.InputFieldUser) as EditText
    val editFieldUser = view.findViewById(R.id.editFieldUser) as ImageButton
}