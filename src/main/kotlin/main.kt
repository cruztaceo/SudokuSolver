import kotlin.math.sqrt

fun main() {
    val sudokuFirst = arrayOf(
        mutableListOf(5, 0, 0, 9, 1, 3, 7, 2, 0),
        mutableListOf(3, 0, 0, 0, 8, 0, 5, 0, 9),
        mutableListOf(0, 9, 0, 2, 5, 0, 0, 8, 0),
        mutableListOf(6, 8, 0, 4, 7, 0, 2, 3, 0),
        mutableListOf(0, 0, 9, 5, 0, 0, 4, 6, 0),
        mutableListOf(7, 0, 4, 0, 0, 0, 0, 0, 5),
        mutableListOf(0, 2, 0, 0, 0, 0, 0, 0, 0),
        mutableListOf(4, 0, 0, 8, 9, 1, 6, 0, 0),
        mutableListOf(8, 5, 0, 7, 2, 0, 0, 0, 3)
    )

    solveSudoku(sudokuFirst)

    printSudoku(sudokuFirst)

    println("==========================================================")

    val sudokuSecond = arrayOf(
        mutableListOf(6, 9, 0, 0, 0, 0, 7, 0, 0),
        mutableListOf(0, 0, 0, 0, 9, 6, 0, 0, 0),
        mutableListOf(0, 8, 0, 7, 5, 3, 0, 9, 0),
        mutableListOf(0, 2, 0, 3, 7, 4, 5, 6, 1),
        mutableListOf(3, 6, 0, 0, 0, 5, 0, 2, 0),
        mutableListOf(0, 0, 0, 9, 6, 0, 3, 7, 8),
        mutableListOf(0, 0, 6, 0, 3, 1, 0, 8, 4),
        mutableListOf(0, 4, 5, 8, 0, 7, 6, 0, 0),
        mutableListOf(0, 0, 0, 0, 0, 0, 0, 5, 7)
    )

    solveSudoku(sudokuSecond)

    printSudoku(sudokuSecond)
}

fun isAvailable(sudoku: Array<MutableList<Int>>, row: Int, col: Int, number: Int): Boolean {
    // Row has unique
    sudoku[row].forEach { num ->
        if (num == number)
            return false
    }

    // Column has unique
    sudoku.forEach { num ->
        if (num[col] == number)
            return false
    }

    // Unique in box
    val sqrt = sqrt(sudoku.size.toDouble()).toInt()
    val boxRowStart = row - row.mod(sqrt)
    val boxColumnStart = col - col.mod(sqrt)

    for (r in boxRowStart until boxRowStart + sqrt) {
        for (c in boxColumnStart until boxColumnStart + sqrt)
            if (sudoku[r][c] == number)
                return false
    }
    return true
}

fun solveSudoku(sudoku: Array<MutableList<Int>>): Boolean {
    var isEmpty = true

    sudoku.forEachIndexed { row, list ->
        list.forEachIndexed { col, item ->
            if (item == 0) {
                isEmpty = false
                for (number in 1..9) {
                    if (isAvailable(sudoku, row, col, number)) {
                        list[col] = number
                        if (solveSudoku(sudoku)) {
                            return true
                        } else {
                            list[col] = 0
                        }
                    }
                }
                return false
            }
        }
    }

    if (isEmpty)
        return true

    return false
}

fun printSudoku(sudoku: Array<MutableList<Int>>) {
    println("╔═══════════╦═══════════╦═══════════╗")
    for (i in sudoku.indices) {
        if (i != 0 && i.mod(3) == 0)
            println("╠═══════════╬═══════════╬═══════════╣")
        sudoku[i].forEachIndexed { index, num ->
            if (index.mod(3) == 0)
                print("║")
            else
                print("│")
            print(" $num ")
        }
        println("║")
    }
    println("╚═══════════╩═══════════╩═══════════╝")
}