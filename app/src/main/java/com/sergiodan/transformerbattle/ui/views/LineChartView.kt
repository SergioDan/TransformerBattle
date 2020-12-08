package com.sergiodan.transformerbattle.ui.views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.sergiodan.transformerbattle.R
import com.sergiodan.transformerbattle.ui.getColor

private val paint: Paint by lazy {
    val paint = Paint()
    paint.color = Color.BLACK
    paint.strokeWidth = 1f
    paint.style = Paint.Style.STROKE
    paint
}

private val cartesianPaint: Paint by lazy {
    val paint = Paint()
    paint.color = Color.BLACK
    paint.strokeWidth = 3f
    paint.style = Paint.Style.STROKE
    paint
}

private val textPaint: Paint by lazy {
    val paint = Paint()
    paint.textSize = 18f
    paint
}

class LineChartView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private var path: Path = Path()
    private var yCoordinates: Array<Float> = arrayOf()
    private var labels: Array<String> = arrayOf()

//   test values
//    var myPath = arrayOf(PointF(0f, 80f),
//            PointF(20f, 20f),
//            PointF(40f, 80f),
//            PointF(60f, 20f),
//            PointF(80f, 80f)
//    )

    fun setCoordinatesAndLabels(yList: Array<Float>, labels: Array<Int>) {
        this.yCoordinates = yList
        this.labels = labels.map {
            context.getString(it)
        }.toTypedArray()
        invalidate()
    }

    private fun calculateOffset(): Float = (measuredWidth.toFloat()) / labels.size
    private fun initialX(): Float = 18f

    private fun createCoordinatesList() {
        val xOffset = calculateOffset()
        var x = initialX()
        val coordinates = yCoordinates.map {
            val point = PointF(x, it)
            x += xOffset
            point
        }
        updateChart(coordinates.toTypedArray())
    }

    private fun updateChart(coordinatesList: Array<PointF>) {
        path = Path()
        path.moveTo(coordinatesList[0].x, coordinatesList[0].y)
        for (i in 1 until coordinatesList.size) {
            path.lineTo(coordinatesList[i].x, coordinatesList[i].y)
        }

    }

    protected override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        createCoordinatesList()
        canvas.drawPath(path, paint.apply { this.color = context.getColor(arrayOf(R.attr.textColor)) })
        canvas.drawLine(10f, 0f, 10f, 100f, cartesianPaint.apply { this.color = context.getColor(arrayOf(R.attr.textColor)) })
        canvas.drawLine(10f, 100f, (measuredWidth.toFloat()-10f), 100f, cartesianPaint.apply { this.color = context.getColor(arrayOf(R.attr.textColor)) })
        drawLabels(canvas)
    }

    private fun drawLabels(canvas: Canvas) {
        val offset = calculateOffset()
        var x = initialX()
        labels.map{ s ->
            val pair = Pair<String, Float>(s.subSequence(0,3).toString(), x+10f)
            x += offset
            pair
        }.forEach {
            canvas.save()
            canvas.rotate(-90f)
            canvas.drawText(
                it.first,
                -140f,
                it.second,
                textPaint.apply { this.color = context.getColor(arrayOf(R.attr.textColor)) }
            )
            canvas.restore()
        }
    }
}
