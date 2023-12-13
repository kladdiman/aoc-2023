fun main() {
    fun part1(input: List<String>): Int {
        var tot = 0
        input.forEach {
            println(it)
        }
        input.forEachIndexed { rowIdx, row ->
            var numStart: Int? = null
            for (i in row.indices) {
                if (row[i].isDigit()) {
                    if (i + 1 == row.length) {
                        if (numStart == null) {
                            numStart = i
                        }
                        tot += checkNum(input, rowIdx, numStart, i)
                    } else if (numStart == null) {
                        numStart = i
                    }
                } else if (numStart != null) {
                    tot += checkNum(input, rowIdx, numStart, i - 1)
                    numStart = null
                }
            }
        }
        return tot
    }

    fun part2(input: List<String>): Int {
        var tot = 0
        return tot
    }

    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("Day03_test1")
    check(part1(testInput1) == 4361)
//    val testInput2 = readInput("Day03_test2")
//    check(part2(testInput2) == 0)

    val input = readInput("Day03")
    part1(input).println()
//    part2(input).println()
}

fun checkNum(input: List<String>, rowIdx: Int, numStart: Int, numEnd: Int): Int {
    val row = input[rowIdx]
    val num = Integer.valueOf(row.substring(numStart..numEnd))
    var cornerLeft = numStart
    var cornerRight = numEnd
    if (numStart > 0) {
        cornerLeft--
        val charBefore = row[cornerLeft]
        checkForSymbol(charBefore) && return num
    }
    if (numEnd + 1 < row.length) {
        cornerRight++
        val charAfter = row[cornerRight]
        checkForSymbol(charAfter) && return num
    }
    if (rowIdx > 0) {
        val rowAbove = input[rowIdx - 1].substring(cornerLeft..cornerRight)
        checkForSymbol(rowAbove) && return num
    }
    if (rowIdx + 1 < input.size) {
        val rowBelow = input[rowIdx + 1].substring(cornerLeft..cornerRight)
        checkForSymbol(rowBelow) && return num
    }
    return 0
}

fun checkForSymbol(str: String) = str.any { checkForSymbol(it) }

fun checkForSymbol(c: Char) = !c.isDigit() && c != '.'
