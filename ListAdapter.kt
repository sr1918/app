package com.example.roomapp1

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.findFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.roomapp.viewmodel.UserViewModel
import com.example.roomapp1.data.User

class ListAdapter : RecyclerView.Adapter<com.example.roomapp1.ListAdapter.MyViewHolder>(){
    private var userlist= emptyList<User>()
    private lateinit var mUserViewModel: UserViewModel
    class MyViewHolder(itemview:View):RecyclerView.ViewHolder(itemview){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row,parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       var curritem=userlist[position]
       // holder.itemView.findViewById<TextView>(R.id.id).text=curritem.id.toString()
        holder.itemView.findViewById<TextView>(R.id.firstname).text=curritem.firstName
        holder.itemView.findViewById<TextView>(R.id.lastname).text=curritem.lastName
        holder.itemView.findViewById<TextView>(R.id.age).text=curritem.age.toString()
        //holder.itemView.findViewById<ImageView>(R.id.imageView).load(curritem.profilePhoto)
      //  lifecycle
       /* holder.itemView.findViewById<ConstraintLayout>(R.id.rowlayout).setOnClickListener{
            var action=ListFragmentDirections.actionListFragmentToUpdateFragment(curritem)
            holder.itemView.findNavController().navigate(R.id.action_listFragment_to_updateFragment)
        }*/
        holder.itemView.findViewById<ImageButton>(R.id.delete).setOnClickListener(View.OnClickListener{v->
            val builder = AlertDialog.Builder(v.context)
            mUserViewModel = ViewModelProvider(v.findFragment()).get(UserViewModel::class.java)
            builder.setPositiveButton("Yes") { _, _ ->
                mUserViewModel.deleteUser(curritem)
                //holder.itemView.findNavController().navigate(R.id.action_updateFragment_to_listFragment)
            }
            builder.setNegativeButton("No") { _, _ -> }
            builder.setTitle("Delete ${curritem.firstName}?")
            builder.setMessage("Are you sure you want to delete ${curritem.firstName}?")
            builder.create().show()

    })
        holder.itemView.findViewById<ImageButton>(R.id.update).setOnClickListener {
            val action=ListFragmentDirections.actionListFragmentToUpdateFragment(curritem)
            holder.itemView.findNavController().navigate(action)
        }

    }
    override fun getItemCount(): Int {
        return userlist.size
    }
    fun setData(user:List<User>){
        this.userlist=user
        notifyDataSetChanged()
    }
}