package time.to.relax

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import kotlin.math.sqrt


class GameView(context: Context, attributeSet: AttributeSet)
    : SurfaceView(context, attributeSet), SurfaceHolder.Callback {

    val thread : GameThread


    private val red = Paint()
    private val white = Paint()
    private val green = Paint()
    private val blue = Paint()

    private var circleRed: Circle
    private var circleGreen: Circle
    private var circleWhite: Circle
    private var circleBlue: Circle

    private var isAnimation = false
    private var isDrawing = false
    private var isDrawingWhileAnimation = false

    private var posXcoef = 0f
    private var posYcoef = 0f
    private var radiusCoef = 0f
    private var coef = 0f
    private var time = 5

    init {
        holder.addCallback(this)
        thread = GameThread(holder, this)
        red.setARGB(255,255,0,0)
        white.setARGB(255, 255, 255, 255)
        green.setARGB(255, 0, 255, 0)
        blue.setARGB(255, 0, 0, 255)

        circleRed = Circle(200f, 200f, 20f, red)
        circleGreen = Circle(200f, 200f, 50f, green)
        circleWhite = Circle(200f, 200f, 100f, white)
        circleBlue = Circle(200f, 200f, 150f, blue)


        circleRed.color.style = Paint.Style.STROKE
        circleGreen.color.style = Paint.Style.STROKE
        circleWhite.color.style = Paint.Style.STROKE
        circleBlue.color.style = Paint.Style.STROKE
    }


    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        thread.setRunning(false)
        thread.join()
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        thread.setRunning(true)
        thread.start()
    }

    fun update() {
        if (isAnimation) {
            posXcoef = (circleGreen.posX - circleRed.posX) * (coef / (60 * time)) // 60 - fps
            posYcoef = (circleGreen.posY - circleRed.posY) * (coef / (60 * time))
            radiusCoef = (circleGreen.radius - circleRed.radius) * (coef / (60 * time))
            circleWhite.posX = circleRed.posX + posXcoef
            circleWhite.posY = circleRed.posY + posYcoef
            circleWhite.radius = circleRed.radius + radiusCoef
            coef++
            if (coef == 60 * time + 1f) {
                isAnimation = false
                isDrawingWhileAnimation = false
                coef = 0f
                copyCircleTo(circleGreen, circleRed)
                if (isDrawing) {
                    copyCircleTo(circleBlue, circleGreen)
                }
            }
        }
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)

        if (canvas == null) return


        canvas.drawOval(circleRed.getCircle(), circleRed.color)

        if (isDrawingWhileAnimation) {
            canvas.drawOval(circleGreen.getCircle(), circleGreen.color)
            canvas.drawOval(circleWhite.getCircle(), circleWhite.color)
            canvas.drawOval(circleBlue.getCircle(), circleBlue.color)
        } else if (isAnimation) {
            canvas.drawOval(circleGreen.getCircle(), circleGreen.color)
            canvas.drawOval(circleWhite.getCircle(), circleWhite.color)
        } else if (isDrawing) {
            canvas.drawOval(circleGreen.getCircle(), circleGreen.color)
        }

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        if (event.action == MotionEvent.ACTION_DOWN && !isAnimation) {
            circleGreen = Circle(event.getX(0), event.getY(0), 0f, green)
            isDrawing = true
        } else if (event.action == MotionEvent.ACTION_MOVE && !isAnimation) {
            circleGreen.radius = sqrt((event.getX(0) - circleGreen.posX)*(event.getX(0) - circleGreen.posX) + (event.getY(0) - circleGreen.posY) * (event.getY(0) - circleGreen.posY))
        } else if (event.action == MotionEvent.ACTION_UP && !isAnimation) {
            isAnimation = true
            isDrawing = false
        } else if (event.action == MotionEvent.ACTION_DOWN && isAnimation) {
            circleBlue = Circle(event.getX(0), event.getY(0), 0f, blue)
            isDrawingWhileAnimation = true
            isDrawing = true
        } else if (event.action == MotionEvent.ACTION_MOVE && isAnimation) {
            circleBlue.radius = sqrt((event.getX(0) - circleBlue.posX)*(event.getX(0) - circleBlue.posX) + (event.getY(0) - circleBlue.posY) * (event.getY(0) - circleBlue.posY))
        } else if (event.action == MotionEvent.ACTION_UP && isAnimation) {
            isDrawing = false
            isDrawingWhileAnimation = false
            coef = 0f
            copyCircleTo(circleBlue, circleGreen)
            copyCircleTo(circleWhite, circleRed)
        }

        return true
    }

    fun copyCircleTo(c1: Circle, c2:Circle) {
        c2.posX = c1.posX
        c2.posY = c1.posY
        c2.radius = c1.radius
    }

}