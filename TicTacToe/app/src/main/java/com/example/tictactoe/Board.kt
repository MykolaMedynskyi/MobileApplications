package com.example.tictactoe

class Board internal constructor() {

    private var board = Array(3) { arrayOfNulls<String>(3) }
    var turn: String? = null
        private set
    private var count: Int = 0
    var result: String? = null

    init {
        for (i in 0..2) {
            for (j in 0..2) {
                board[i][j] = "-"
            }
        }

        turn = "x"
        count = 0
        result = "-"
    }

    fun printBoard() {
        println("-")
        for (i in 0..2) {
            for (j in 0..2) {
                print(board[i][j])
            }
            println()
        }
    }


    fun put(xy: String) {
        val x = Integer.parseInt(xy.substring(0, 1))
        val y = Integer.parseInt(xy.substring(1, 2))
        board[x][y] = turn
        if (turn == "x") {
            turn = "o"
        } else
            turn = "x"
        count++
    }

    fun restart() {
        for (i in 0..2) {
            for (j in 0..2) {
                board[i][j] = "-"
            }
        }
        turn = "x"
        count = 0
    }


    fun check4Win(){
        if (count < 5) result = "-"
        if (board[0][0] == "x" && board[0][1] == "x" && board[0][2] == "x") result =  "xwin"
        if (board[0][0] == "o" && board[0][1] == "o" && board[0][2] == "o") result =  "owin"
        if (board[1][0] == "x" && board[1][1] == "x" && board[1][2] == "x") result =  "xwin"
        if (board[1][0] == "o" && board[1][1] == "o" && board[1][2] == "o") result =  "owin"
        if (board[2][0] == "x" && board[2][1] == "x" && board[2][2] == "x") result =  "xwin"
        if (board[2][0] == "o" && board[2][1] == "o" && board[2][2] == "o") result =  "owin"

        if (board[0][0] == "x" && board[1][0] == "x" && board[2][0] == "x") result =  "xwin"
        if (board[0][0] == "o" && board[1][0] == "o" && board[2][0] == "o") result =  "owin"
        if (board[0][1] == "x" && board[1][1] == "x" && board[2][1] == "x") result =  "xwin"
        if (board[0][1] == "o" && board[1][1] == "o" && board[2][1] == "o") result =  "owin"
        if (board[0][2] == "x" && board[1][2] == "x" && board[2][2] == "x") result =  "xwin"
        if (board[0][2] == "o" && board[1][2] == "o" && board[2][2] == "o") result =  "owin"

        if (board[0][0] == "x" && board[1][1] == "x" && board[2][2] == "x") result =  "xwin"
        if (board[0][0] == "o" && board[1][1] == "o" && board[2][2] == "o") result =  "owin"

        if (board[0][2] == "x" && board[1][1] == "x" && board[2][0] == "x") result =  "xwin"
        if (board[0][2] == "o" && board[1][1] == "o" && board[2][0] == "o") result = "owin"

        if (count == 9) result = " tie"
        //result = "-"
    }

}
