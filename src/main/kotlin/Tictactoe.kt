const val EMPTY = 0
const val PLAYER_1 = 1
const val PLAYER_2 = 2
const val COLS = 3
const val ROWS = 3

val gameboard = Array(COLS * ROWS) { EMPTY }

fun main() {
    startGameLoop()
}

fun printBoard() {
    println()
    gameboard.forEachIndexed { i, tile ->
        if (i % 3 == 0) println("\n=========")
        print("|${tile}|")
    }
    println("\n=========")
}

// TODO take in index in col, row format
fun getUserMove(): Int {
    fun printPrompt() {
        println("what's your next move (abs index)?  ")
        print(">> ")
    }

    fun safeToInt(input: String): Int {
        return try {
            input.toInt()
        } catch (e: NumberFormatException) {
            println("use number!")
            printPrompt()
            val input = readLine()!!

            safeToInt(input)
        }
    }

    printPrompt()
    val input = readLine()!!

    return safeToInt(input)
}

// TODO implement move validation
fun getRandomMove() = (0 until COLS * ROWS).random()

fun getWinner(): Int? {

    fun offsetIndex(index: Int, dx: Int, dy: Int, rep: Int = 1) = index + rep * (dx + COLS * dy)
    // fun isInBound(index: Int) = index >= 0 && index < COLS * ROWS
    fun match(index: Int, tile: Int, dx: Int, dy: Int): Boolean {
        if (gameboard[offsetIndex(index, dx, dy)] == tile)
            if (gameboard[offsetIndex(index, dx, dy, 2)] == tile)
                return true
        return false
    }

    var dx: Int
    var dy: Int

    var emptyTiles = 0

    // look for winner or draw
    gameboard.forEachIndexed { crrIndex, tile ->
        if (tile != EMPTY) {
            if (crrIndex % 3 == 0) {
                dx = 1
                dy = 0
                if (match(crrIndex, tile, dx, dy)) return tile
            }

            if (crrIndex < 3) {
                dx = 0
                dy = 1
                if (match(crrIndex, tile, dx, dy)) return tile
            }

            if (crrIndex == 0) {
                dx = 1
                dy = 1
                if (match(crrIndex, tile, dx, dy)) return tile
            }

            if (crrIndex == 2) {
                dx = -1
                dy = 1
                if (match(crrIndex, tile, dx, dy)) return tile
            }
        } else emptyTiles ++
    }

    // draw
    if(emptyTiles == 0) return EMPTY

    return null
}

fun startGameLoop() {
    var turn = false

    while (true) {
        turn = !turn

        var nextMove: Int
        // player's turn
        if (turn) {
            nextMove = getUserMove()
            while (gameboard[nextMove] != EMPTY) {
                println("tile occupied!")
                println()
                nextMove = getUserMove()
            }
        }
        // AI's turn
        else {
            // find a random valid move
            nextMove = getRandomMove()
            while (gameboard[nextMove] != EMPTY) nextMove = getRandomMove()
        }

        // make move
        gameboard[nextMove] = when (turn) {
            true -> PLAYER_1
            false -> PLAYER_2
        }

        printBoard()

        // check for winner and draw
        val winner = getWinner()
        if (winner != null) {
            if(winner == EMPTY) println("draw!")
            else println((if (winner == 1) "player 1" else "player 2") + " has won!")
            break
        }
    }
}
