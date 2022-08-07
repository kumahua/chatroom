package com.example.chatroom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.chatroom.databinding.StickerSheetBinding

class StickerSheet : LinearLayout {

    private lateinit var v: StickerSheetBinding
    private var listener: StickerSheetListener? = null

    constructor(context: Context) : super(context) {

        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {

        init(context, attrs)
    }

    interface StickerSheetListener {

        fun sendSticker(sticker: Int)
    }

    fun setListener(listener: StickerSheetListener) { this.listener = listener }

    private fun init(context: Context, attrs: AttributeSet?) {

        v = StickerSheetBinding.inflate(LayoutInflater.from(context), this, true)

        v.lol.setOnClickListener { onStickClick(StickerSheetType.LOL) }
        v.ok.setOnClickListener { onStickClick(StickerSheetType.OK) }
        v.omg.setOnClickListener { onStickClick(StickerSheetType.OMG) }
        v.wtf.setOnClickListener { onStickClick(StickerSheetType.WTF) }
        v.wow.setOnClickListener { onStickClick(StickerSheetType.WOW) }
        v.go.setOnClickListener { onStickClick(StickerSheetType.GO) }
    }

    private fun onStickClick(sticker: StickerSheetType) {

        if(listener != null) {

            when(sticker) {

                StickerSheetType.LOL -> listener!!.sendSticker(R.drawable.lol)
                StickerSheetType.OK -> listener!!.sendSticker(R.drawable.ok)
                StickerSheetType.OMG -> listener!!.sendSticker(R.drawable.omg)
                StickerSheetType.WTF -> listener!!.sendSticker(R.drawable.wtf)
                StickerSheetType.WOW -> listener!!.sendSticker(R.drawable.wow)
                StickerSheetType.GO -> listener!!.sendSticker(R.drawable.go)
            }
        }
    }
}

enum class StickerSheetType { LOL, OK, OMG, WTF, WOW, GO }