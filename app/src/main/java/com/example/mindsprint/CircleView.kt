package com.example.mindsprint

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.animation.ValueAnimator
import kotlin.math.cos
import kotlin.math.sin

class CircleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint().apply {
        color = Color.parseColor("#2ED6B5")
        style = Paint.Style.FILL
        isAntiAlias = true
        alpha = 100
    }
    private val paint2 = Paint().apply {
        color = Color.parseColor("#B2EBF2")
        style = Paint.Style.FILL
        isAntiAlias = true
        alpha = 100
    }
    private val paint3 = Paint().apply {
        color = Color.parseColor("#32CD32")
        style = Paint.Style.FILL
        isAntiAlias = true
        alpha = 100
    }

    // Blurred circle paint
    private val blurredPaint = Paint().apply {
        color = Color.parseColor("#26C6DA") // semi-transparent
        style = Paint.Style.FILL
        isAntiAlias = true
        maskFilter = BlurMaskFilter(200f, BlurMaskFilter.Blur.NORMAL)
    alpha=100}
    private val blurredPaint2 = Paint().apply {
        color = Color.parseColor("#802ED6B5") // semi-transparent
        style = Paint.Style.FILL
        isAntiAlias = true
        maskFilter = BlurMaskFilter(200f, BlurMaskFilter.Blur.NORMAL)
        alpha=100}
    private var cx = 0f
    private var cy = 0f
    private var radius = 0f

    private var cx2 = 0f
    private var cy2 = 0f
    private var radius2 = 0f

    private var cx3 = 0f
    private var cy3 = 0f
    private var radius3 = 0f
    private var angle = 0f
    private var orbitRadius = 500f

    private val animator1 = ValueAnimator.ofFloat(0f, 1000f).apply {
        duration = 2000
        repeatMode = ValueAnimator.RESTART
        repeatCount = ValueAnimator.INFINITE
        addUpdateListener { animation ->
            cx = animation.animatedValue as Float
            invalidate()
        }
    }

    private val animator2 = ValueAnimator.ofFloat(0f, 1500f).apply {
        duration = 2000
        repeatMode = ValueAnimator.RESTART
        repeatCount = ValueAnimator.INFINITE
        addUpdateListener { animation ->
            cy2 = animation.animatedValue as Float
            invalidate()
        }
    }

    private val animator3 = ValueAnimator.ofFloat(0f, (2 * Math.PI).toFloat()).apply {
        duration = 3000
        repeatMode = ValueAnimator.RESTART
        repeatCount = ValueAnimator.INFINITE
        addUpdateListener { animation ->
            angle = animation.animatedValue as Float
            invalidate()
        }
    }

    init {
        // Enable blur
        setLayerType(LAYER_TYPE_SOFTWARE, null)

        animator1.start()
        animator2.start()
        animator3.start()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        cx = 0f
        cy = h / 4f
        radius = 500f

        cx2 = w / 2f
        cy2 = 0f
        radius2 = 500f

        cx3 = w / 2f
        cy3 = h / 2f
        radius3 = 500f
        orbitRadius = Math.min(w, h) / 3f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // First circle (horizontal)
        canvas.drawCircle(cx, cy, radius, paint)

        // Second circle (vertical)
        canvas.drawCircle(cx2, cy2, radius2, paint2)

        // Third circle (circular path)
        val orbitCenterX = width / 2f
        val orbitCenterY = height / 2f
        cx3 = orbitCenterX + orbitRadius * cos(angle)
        cy3 = orbitCenterY + orbitRadius * sin(angle)
        canvas.drawCircle(cx3, cy3, radius3, paint3)

        // Blurred circle (static, bottom-right corner)
        val blurX = width - 200f
        val blurY = height - 200f
        val blurRadius = 1000f
        canvas.drawCircle(blurX, blurY, blurRadius, blurredPaint)
        //blurred 2 circle
        val blur2X =  300f
        val blur2Y = 300f
        val blur2Radius = 1000f
        canvas.drawCircle(blur2X, blur2Y, blur2Radius, blurredPaint2)
    }
}
