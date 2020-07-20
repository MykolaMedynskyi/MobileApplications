package com.example.tictactoe

import android.content.res.Resources
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var board = Board()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_main)

        findViewById<TextView>(R.id.textViewTurn).text = "Turn : x"
        findViewById<Button>(R.id.buttonRestart).visibility = View.INVISIBLE

    }

    fun buttonPressed(view: View) {
        val buSelected = view as Button
        buSelected.text = board.turn
        buSelected.isEnabled = false
        if (board.turn == "x") buSelected.setTextColor(Color.parseColor("#FF002E"))
        else buSelected.setTextColor(Color.parseColor("#3003CF"))

        board.put(buSelected.transitionName)
        findViewById<TextView>(R.id.textViewTurn).text = "Turn : ${board.turn}"

        board.check4Win()
        if (board.result == "-") return


        textViewTurn.text = board.result
        disableButtons()
        image.visibility = View.VISIBLE
        imageView.setImageDrawable(resources.getDrawable(resources.getIdentifier(board.result, "drawable", packageName)))

    }

    fun imagePressed(view: View) {
        image.visibility = View.INVISIBLE
        findViewById<Button>(R.id.buttonRestart).visibility = View.VISIBLE
    }

    fun disableButtons() {
        findViewById<Button>(R.id.button00).isEnabled = false
        findViewById<Button>(R.id.button01).isEnabled = false
        findViewById<Button>(R.id.button02).isEnabled = false
        findViewById<Button>(R.id.button10).isEnabled = false
        findViewById<Button>(R.id.button11).isEnabled = false
        findViewById<Button>(R.id.button12).isEnabled = false
        findViewById<Button>(R.id.button20).isEnabled = false
        findViewById<Button>(R.id.button21).isEnabled = false
        findViewById<Button>(R.id.button22).isEnabled = false
    }

    fun restart(view: View) {
        findViewById<Button>(R.id.button00).text = ""
        findViewById<Button>(R.id.button00).isEnabled = true
        findViewById<Button>(R.id.button01).text = ""
        findViewById<Button>(R.id.button01).isEnabled = true
        findViewById<Button>(R.id.button02).text = ""
        findViewById<Button>(R.id.button02).isEnabled = true
        findViewById<Button>(R.id.button10).text = ""
        findViewById<Button>(R.id.button10).isEnabled = true
        findViewById<Button>(R.id.button11).text = ""
        findViewById<Button>(R.id.button11).isEnabled = true
        findViewById<Button>(R.id.button12).text = ""
        findViewById<Button>(R.id.button12).isEnabled = true
        findViewById<Button>(R.id.button20).text = ""
        findViewById<Button>(R.id.button20).isEnabled = true
        findViewById<Button>(R.id.button21).text = ""
        findViewById<Button>(R.id.button21).isEnabled = true
        findViewById<Button>(R.id.button22).text = ""
        findViewById<Button>(R.id.button22).isEnabled = true
        board.restart()
        view.visibility = View.INVISIBLE
        findViewById<TextView>(R.id.textViewTurn).text = "Turn : x"
    }

}
