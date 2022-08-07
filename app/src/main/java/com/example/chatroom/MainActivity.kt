package com.example.chatroom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatroom.databinding.ActivityMainBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), StickerSheet.StickerSheetListener, ChatAdapter.ItemClickListener {

    private lateinit var v: ActivityMainBinding
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var bottomSheet: BottomSheetDialog

    private val photos = arrayOf(R.drawable.man, R.drawable.business, R.drawable.woman)
    private val names = arrayOf("Leo", "Ken", "Eva")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        v = ActivityMainBinding.inflate(layoutInflater)
        setContentView(v.root)

        val stickerSheet = StickerSheet(this)
        stickerSheet.setListener(this)

//        ShareUtil.clearSharePref(this)

        bottomSheet = BottomSheetDialog(this)

        chatAdapter = ChatAdapter(getChatList())
        chatAdapter.setItemListener(this)
        v.chatRecyclerView.adapter = chatAdapter
        v.chatRecyclerView.layoutManager = LinearLayoutManager(this)

        setAutoAdjustLayout()

        onClickSend()

        v.stickerBtn.setOnClickListener {

            bottomSheet.setContentView(stickerSheet)
            bottomSheet.show()
        }
        v.clearBtn.setOnClickListener {

            chatAdapter.clearChat()
            ShareUtil.clearSharePref(this)
        }
    }

    override fun sendSticker(sticker: Int) {

        addAndSaveChat(" ", sticker)
        v.chatRecyclerView.scrollToPosition(chatAdapter.itemCount - 1)

        bottomSheet.dismiss()
    }

    override fun onPhotoClick(currentChat: Chat) {

        chatAdapter.selectedName = currentChat.name
        chatAdapter.notifyDataSetChanged()
    }

    override fun onNameClick(currentChat: Chat) {

        Toast.makeText(this, "${currentChat.name} ${getCurrentTime()}", Toast.LENGTH_SHORT).show()
    }

    private fun getChatList(): MutableList<Chat> {

        val photoList = getChatItemList("photo")
        val nameList = getChatItemList("name")
        val timeList = getChatItemList("time")
        val messageList = getChatItemList("message")
        val stickerList = getChatItemList("sticker")

        val chatList = mutableListOf<Chat>()

        if(photoList.size == nameList.size && nameList.size == timeList.size &&
            timeList.size == messageList.size && messageList.size == stickerList.size) {

            for(i in 0..photoList.lastIndex) {

                chatList.add(Chat(photoList[i].toInt(),
                    nameList[i],
                    timeList[i],
                    messageList[i],
                    stickerList[i].toInt())
                )
            }
        }

        return chatList
    }

    private fun getChatItemList(key: String): List<String> {

        val usedItem = ShareUtil.getString(key, this)!!
        return if(usedItem.isNotEmpty()) usedItem.split(",") else listOf()
    }

    private fun setAutoAdjustLayout() {

        (v.chatRecyclerView.layoutManager as LinearLayoutManager).stackFromEnd = true

        v.chatRecyclerView.apply {

                addOnScrollListener(object : RecyclerView.OnScrollListener() { // 監聽畫面滑動

                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)

                    hideKeyboard()
                }
            })
        }
    }

    private fun onClickSend() {

        v.sendBtn.setOnClickListener {

            val message = v.messageText.text.toString()
            if(message.isNotEmpty()) {

                addAndSaveChat(message, 0)
                v.messageText.text.clear()
                v.chatRecyclerView.scrollToPosition(chatAdapter.itemCount - 1)
            }
        }
    }

    private fun addAndSaveChat(message: String, sticker: Int) {

        val i = (0..2).random()
        val chat = Chat(photos[i], names[i], getCurrentTime(), message, sticker)

        chatAdapter.addChat(chat)
        saveChat(chat)
    }

    private fun saveChat(chat: Chat) {

        saveChatItem("photo", chat.photo.toString())
        saveChatItem("name", chat.name)
        saveChatItem("time", chat.time)
        saveChatItem("message", chat.message)
        saveChatItem("sticker", chat.sticker.toString())
    }

    private fun saveChatItem(key: String, value: String) {

        val savedItem = ShareUtil.getString(key, this)
        val item = if (savedItem!!.isEmpty()) value else "$savedItem,$value"

        ShareUtil.putString(key, item, this)
    }

    private fun getCurrentTime(): String {

        val simpleDateFormat = SimpleDateFormat("HH:mm", Locale.TAIWAN)
        val date = Date()

        return simpleDateFormat.format(date)
    }

    private fun hideKeyboard() {

        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }
}