package com.example.arkanoid

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView


class GameView(context: Context, attributeSet: AttributeSet)
    : SurfaceView(context, attributeSet), SurfaceHolder.Callback {

    val thread : GameThread

    var ballX = 0f
    var ballY = 0f
    var dx = 0f
    var dy = 3f
    private val SIZE = 16f

    var rect1X1 = 0f
    var rect1Y1 = 15f
    var rect1X2 = 0f
    var rect1Y2 = 30f

    var rect2X1 = 0f
    var rect2Y1 = 0f
    var rect2X2 = 0f
    var rect2Y2 = 0f

    private val rect1Dx = 5f
    private val rect2Dx = 4f

    private var rect1Dir = 0
    private var rect2Dir = 0

    private val red = Paint()
    private val white = Paint()

    var bot = 0
    var me = 0
    var score = "0 : 0"

    init {
        holder.addCallback(this)
        thread = GameThread(holder, this)
        red.setARGB(255,255,0,0)
        white.setARGB(255, 255, 255, 255)

    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (score == "0 : 0") {
            ballX = width.toFloat() / 2
            ballY = height.toFloat() / 2

            rect1X1 = width.toFloat() / 2 - 54
            rect1X2 = width.toFloat() / 2 + 54

            rect2X1 = width.toFloat() / 2 - 54
            rect2X2 = width.toFloat() / 2 + 54
            rect2Y1 = height.toFloat() - rect1Y2
            rect2Y2 = height.toFloat() - rect1Y1
        }
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
        //BALL
        ballX+=dx
        ballY+=dy
        if (ballX <= 0 || ballX+SIZE >= width) {
            dx = -dx
        }
        if (dy < 0) {
            val ballTop = ballX + SIZE/2
            if (ballY > rect1Y1 && ballY < rect1Y2 && ballTop > rect1X1 && ballTop < rect1X2) {
                if (ballTop - rect1X1 < 18) {
                    dy = 6f
                    dx = -6f
                } else if (ballTop - rect1X1 < 36) {
                    dy = 8f
                    dx = -4f
                } else if (ballTop - rect1X1 < 54) {
                    dy = 10f
                    dx = -2f
                } else if (ballTop - rect1X1 < 72) {
                    dy = 10f
                    dx = 2f
                } else if (ballTop - rect1X1 < 90) {
                    dy = 8f
                    dx = 4f
                } else {
                    dy = 6f
                    dx = 6f
                }
            }
        } else {
            val ballBottom = ballX + SIZE/2
            if (ballY + SIZE > rect2Y1 && ballY + SIZE < rect2Y2 && ballBottom > rect2X1 && ballBottom < rect2X2) {
                if (ballBottom - rect2X1 < 22) {
                    dy = -6f
                    dx = -6f
                } else if (ballBottom - rect2X1 < 38) {
                    dy = -8f
                    dx = -4f
                } else if (ballBottom - rect2X1 < 54) {
                    dy = -10f
                    dx = -2f
                } else if (ballBottom - rect2X1 < 70) {
                    dy = -10f
                    dx = 2f
                } else if (ballBottom - rect2X1 < 86) {
                    dy = -8f
                    dx = 4f
                } else {
                    dy = -6f
                    dx = 6f
                }
            }
        }
        if (ballY <= -10 || ballY+SIZE >= height+10) {
            if (ballY <= 10) {
                me++
            } else {
                bot++
            }
            score = "$me : $bot"
            ballX = width.toFloat()/2
            ballY = height.toFloat()/2
            dx = 0f
            dy = 3f

        }


        //RECTS
        val rect1Center = (rect1X1 + rect1X2)/2
        val circleCenter = (ballX + SIZE/2)

        if (rect1Center > circleCenter) {
            rect1Dir = 1
        } else {
            rect1Dir = 2
        }

        if (rect2Dir == 1) {
            if (rect2X1 - rect2Dx > 0) {
                rect2X1 -= rect2Dx
                rect2X2 -= rect2Dx
            }
        } else if (rect2Dir == 2) {
            if (rect2X2 + rect2Dx < width) {
                rect2X1 += rect2Dx
                rect2X2 += rect2Dx
            }
        }
        if (rect1Dir == 1) {
            if (rect1X1 - rect1Dx > 0) {
                rect1X1 -= rect1Dx
                rect1X2 -= rect1Dx
            }
        } else if (rect1Dir == 2) {
            if (rect1X2 + rect2Dx < width) {
                rect1X1 += rect1Dx
                rect1X2 += rect1Dx
            }
        }

    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)

        if (canvas == null) return

        canvas.drawOval(RectF(ballX, ballY,ballX+SIZE,ballY+SIZE), red)

        canvas.drawRect(rect1X1, rect1Y1, rect1X2, rect1Y2, white)

        canvas.drawRect(rect2X1, rect2Y1, rect2X2, rect2Y2, white)

        canvas.drawText(score, width.toFloat()/2, height.toFloat()/2, white)

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        if(event.action == MotionEvent.ACTION_DOWN) {
            if (event.getX(0) < width / 2 && event.getY(0) > height / 2) {
                    rect2Dir = 1
            } else if (event.getX(0) > width / 2 && event.getY(0) > height / 2){
                    rect2Dir = 2
            }
        } else if (event.action == MotionEvent.ACTION_MOVE) {
            if (event.getX(0) < width / 2 && event.getY(0) > height / 2) {
                    rect2Dir = 1
            } else if (event.getX(0) > width / 2 && event.getY(0) > height / 2){
                    rect2Dir = 2
            }
        } else if (event.action == MotionEvent.ACTION_UP) {
            rect2Dir = 0
        }
        return true

    }

}