package time.to.relax

import android.graphics.Paint

import android.graphics.RectF

class Circle(
    var posX: Float,
    var posY: Float,
    var radius: Float,
    var color: Paint
) {

    init {
        color.flags = Paint.ANTI_ALIAS_FLAG
    }

    fun getCircle(): RectF {
        return RectF(posX-radius, posY-radius, posX+radius, posY+radius)
    }
}