import kotlin.math.sqrt

fun main(args: Array<String>) {
    val sudoku = arrayOf(
        mutableListOf(5, 0, 0, 9, 1, 3, 7, 2, 0),
        mutableListOf(3, 0, 0, 9, 8, 3, 5, 0, 9),
        mutableListOf(0, 9, 0, 2, 5, 0, 0, 8, 0),
        mutableListOf(6, 8, 0, 4, 7, 0, 2, 3, 0),
        mutableListOf(0, 0, 9, 5, 0, 0, 4, 6, 0),
        mutableListOf(7, 0, 4, 0, 0, 0, 0, 0, 5),
        mutableListOf(0, 2, 0, 0, 0, 0, 0, 0, 0),
        mutableListOf(4, 0, 0, 8, 9, 1, 6, 0, 0),
        mutableListOf(8, 5, 0, 7, 2, 0, 0, 0, 3)
    )

    solveSudoku(sudoku)

    sudoku.forEach { row -> println(row.toIntArray().contentToString()) }
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
    var isEmpty = true;

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