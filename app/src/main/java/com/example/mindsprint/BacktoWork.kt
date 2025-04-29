package com.example.mindsprint

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class BacktoWork @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {


    private val paintRed = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#DDDDDD")
        style = Paint.Style.FILL
        maskFilter = BlurMaskFilter(50f, BlurMaskFilter.Blur.NORMAL)
        alpha = 50
    }

    private val paintGreen = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#5F7BD1")
        style = Paint.Style.FILL
        maskFilter = BlurMaskFilter(50f, BlurMaskFilter.Blur.NORMAL)
        alpha = 50
    }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    private val paint2 = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    private var radius = 400f
    private var radius2 = 400f

    private var cx = 0f
    private var cy = 0f

    private var cx2 = 0f
    private var cy2 = 0f

    init {
        setLayerType(LAYER_TYPE_SOFTWARE, null) // Important for blur to work
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        // First circle - Top Left corner
        cx = 100f
        cy = 100f

        // Second circle - Bottom Right corner
        cx2 = w - 100f
        cy2 = h - 100f

        // Gradient for solid circles
        paint.shader = RadialGradient(
            cx, cy, radius,
            intArrayOf(
                Color.parseColor("#2B3A67"), // center
                Color.parseColor("#14213D"), // middle
                Color.parseColor("#D0F0FF"), // outer
                Color.TRANSPARENT            // transparent edge
            ),
            floatArrayOf(
                0f, 0.5f, 0.8f, 1f
            ),
            Shader.TileMode.CLAMP
        )

        paint2.shader = RadialGradient(
            cx2, cy2, radius2,
            intArrayOf(
                Color.parseColor("#4169E1"), // center //2C3E50
                Color.parseColor("#191D3A"), // middle
                Color.parseColor("#7BA3E3"), // outer
                Color.TRANSPARENT
            ),
            floatArrayOf(
                0f, 0.5f, 0.8f, 1f
            ),
            Shader.TileMode.CLAMP
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(Color.BLACK) // Background

        // Solid circles with multi-layer gradient
        canvas.drawCircle(cx, cy, radius, paint)
        canvas.drawCircle(cx2, cy2, radius2, paint2)

        val blurRadius = 700f

        // Blurred circles
        val circleX = width - 200f
        val circleY = height - 200f
        canvas.drawCircle(circleX, circleY, blurRadius, paintRed)

        val circleX1 = 200f
        val circleY1 = 200f
        canvas.drawCircle(circleX1, circleY1, blurRadius, paintGreen)
    }
}
