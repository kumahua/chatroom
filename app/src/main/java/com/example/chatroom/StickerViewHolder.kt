package com.example.chatroom

import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import com.example.chatroom.databinding.StickerChatItemBinding

class StickerViewHolder(v: StickerChatItemBinding) : RecyclerView.ViewHolder(v.root) {

    private val stickerItem = v.stickerItem
    private val photo = v.photo
    private val sticker = v.sticker

    fun bind(currentChat: Chat, listener: ChatAdapter.ItemClickListener, selectedName: String) {

        photo.setImageResource(currentChat.photo)
        sticker.setImageResource(currentChat.sticker)

        photo.setOnClickListener { listener.onPhotoClick(currentChat) }

        when(selectedName) {

            currentChat.name -> stickerItem.setBackgroundColor(Color.parseColor("#E69966"))
            else -> stickerItem.setBackgroundColor(Color.parseColor("#F5F5F5"))
        }
    }
}