package com.example.chatroom

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.chatroom.databinding.ChatItemBinding

class MyViewHolder(v: ChatItemBinding) : RecyclerView.ViewHolder(v.root){

    private val chatItem = v.mainChatItem
    private val photo = v.photo
    private val name = v.name
    private val time = v.time
    private val message = v.message

    fun bind(currentChat: Chat, listener: ChatAdapter.ItemClickListener, selectedName: String) {

        photo.setImageResource(currentChat.photo)
        name.text = currentChat.name
        time.text = currentChat.time
        message.text = currentChat.message

        name.setOnClickListener { listener.onNameClick(currentChat) }
        photo.setOnClickListener { listener.onPhotoClick(currentChat) }

        when(selectedName) {

            currentChat.name -> chatItem.setBackgroundColor(Color.parseColor("#E69966"))
            else -> chatItem.setBackgroundColor(Color.parseColor("#F5F5F5"))
        }
    }
}