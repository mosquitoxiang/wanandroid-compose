package com.illu.demo.test

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

class PieView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr){

    private lateinit var paint: Paint
    private var dataList: List<Name>? = null
    private var mWidth: Float = 0f
    private var mHeight: Float = 0f
    private var currentAngle = 0f
    private var sweepAngle = 0f
    private var totalNum = 0
    private val mColors = intArrayOf(
        -0x330100, -0x9b6a13, -0x1cd9ca, -0x800000, -0x7f8000, -0x7397, -0x7f7f80,
        -0x194800, -0x830400
    )

    init {
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.style = Paint.Style.FILL
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w.toFloat()
        mHeight = h.toFloat()
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.translate(mWidth/2, mHeight/2)
        val r = (Math.min(mWidth, mHeight)/2 * 0.8).toFloat()
        val rectF = RectF(-r, -r, r, r)
        for (i in dataList!!.indices) {
            paint.setColor(mColors[i])
            sweepAngle = (dataList!![i].num * 360 / totalNum).toFloat()
//            sweepAngle = (dataList!![i].num / totalNum * 360).toFloat()
            canvas.drawArc(rectF, currentAngle, sweepAngle, true, paint)
            currentAngle += sweepAngle
        }
        println("currentAngle${currentAngle}")
    }

    fun setData(list: List<Name>) {
        dataList = list
        for (i in dataList!!.indices) {
            totalNum += dataList!![i].num
        }
        invalidate()
    }
}