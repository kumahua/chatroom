package com.example.chatroom

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chatroom.databinding.ChatItemBinding
import com.example.chatroom.databinding.StickerChatItemBinding

class ChatAdapter(private var chats: MutableList<Chat>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var selectedName: String = ""
    private var itemListener: ItemClickListener? = null
    private val messageViewType = 1
    private val stickerViewType = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if(viewType == 1) {

            MyViewHolder(ChatItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        } else {

            StickerViewHolder(StickerChatItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val lastChat = chats[position]

        if (holder is MyViewHolder) {

            holder.bind(lastChat, itemListener!!, selectedName)
        } else if (holder is StickerViewHolder) {

            holder.bind(lastChat, itemListener!!, selectedName)
        }
    }

    override fun getItemViewType(position: Int): Int {

        return if (chats[position].sticker == 0) messageViewType else stickerViewType
    }

    override fun getItemCount(): Int {

        return chats.size
    }

    fun addChat(chat: Chat) {

        chats.add(chat)
        notifyItemInserted(chats.size - 1)
    }

    fun clearChat() {

        chats.clear()
        notifyDataSetChanged()
    }

    fun setItemListener(listener: ItemClickListener) { this.itemListener = listener }

    interface ItemClickListener {

        fun onPhotoClick(currentChat: Chat)
        fun onNameClick(currentChat: Chat)
    }
}
