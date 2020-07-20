package com.example.arkanoid

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var PREFS_NAME = "SUPER GAME"
    lateinit var sharedPref : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        sharedPref = getSharedPreferences( PREFS_NAME, Context.MODE_PRIVATE)
        tryToRemember()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            gameView.score = "0 : 0"
            gameView.me = 0
            gameView.bot = 0

            val intent = Intent(this, MainActivity::class.java)
            this.startActivity(intent)
            this.finishAffinity()
        }
        return true
    }

    fun tryToRemember() {
        gameView.ballX = sharedPref.getFloat("ballX", 0f)
        gameView.ballY = sharedPref.getFloat("ballY", 0f)
        gameView.dx = sharedPref.getFloat("dx", 0f)
        gameView.dy = sharedPref.getFloat("dy", 3f)
        gameView.rect1X1 = sharedPref.getFloat("rect1X1", 0f)
        gameView.rect2X1 = sharedPref.getFloat("rect2X1", 0f)
        gameView.rect1X2 = sharedPref.getFloat("rect1X2", 0f)
        gameView.rect2X2 = sharedPref.getFloat("rect2X2", 0f)
        gameView.rect1Y1 = sharedPref.getFloat("rect1Y1", 15f)
        gameView.rect2Y1 = sharedPref.getFloat("rect2Y1", 0f)
        gameView.rect1Y2 = sharedPref.getFloat("rect1Y2", 30f)
        gameView.rect2Y2 = sharedPref.getFloat("rect2Y2", 0f)
        gameView.me = sharedPref.getInt("me", 0)
        gameView.bot = sharedPref.getInt("bot", 0)
        gameView.score = sharedPref.getString("score", "0 : 0")
    }


    override fun onPause() {
        super.onPause()
        val editor : SharedPreferences.Editor = sharedPref.edit()
        editor.putFloat("ballX", gameView.ballX)
        editor.putFloat("ballY", gameView.ballY)
        editor.putFloat("dx", gameView.dx)
        editor.putFloat("dy", gameView.dy)
        editor.putFloat("rect1X1", gameView.rect1X1)
        editor.putFloat("rect2X1", gameView.rect2X1)
        editor.putFloat("rect1X2", gameView.rect1X2)
        editor.putFloat("rect2X2", gameView.rect2X2)
        editor.putFloat("rect1Y1", gameView.rect1Y1)
        editor.putFloat("rect1Y2", gameView.rect1Y2)
        editor.putFloat("rect2Y1", gameView.rect2Y1)
        editor.putFloat("rect2Y2", gameView.rect2Y2)
        editor.putInt("me", gameView.me)
        editor.putInt("bot", gameView.bot)
        editor.putString("score", gameView.score)

        editor.apply()
    }

}
