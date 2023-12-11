fun main() {
    fun part1(input: List<String>): Int {
        var tot = 0
        input.forEachIndexed { idx, line ->
            val lineIdx = idx + 1
            if (checkLine(line)) {
                println("Line $lineIdx valid")
                tot += lineIdx
            }
        }
        return tot
    }

    fun part2(input: List<String>): Int {
        var tot = 0
        input.forEach { line ->
            val (rMin, gMin, bMin) = getMinGameLine(line)
            tot += rMin * gMin * bMin
        }
        return tot
    }

    val testInput1 = readInput("Day02_test1")
    check(part1(testInput1) == 8)
    val testInput2 = readInput("Day02_test2")
    check(part2(testInput2) == 2286)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}

fun getMinGameLine(line: String): Triple<Int, Int, Int> {
    val lineNoPrefix = line.split(":")[1]
    println(lineNoPrefix)
    val lineSplit = lineNoPrefix.split(";")
    println("Get min line $lineSplit")
    var rMin = Int.MIN_VALUE
    var gMin = Int.MIN_VALUE
    var bMin = Int.MIN_VALUE
    lineSplit.forEach { round ->
        val roundSplit = round.split(",")
        println("Get min round $roundSplit")
        roundSplit.forEach { colourStr ->
            val (colourNum, colourName) = getColourCount(colourStr)
            when (colourName) {
                "red" -> if (colourNum > rMin) rMin = colourNum
                "green" -> if (colourNum > gMin) gMin = colourNum
                "blue" -> if (colourNum > bMin) bMin = colourNum
            }
        }
    }
    return Triple(rMin, gMin, bMin).also { println("Min line: $it") }
}

fun checkLine(line: String): Boolean {
    val lineNoPrefix = line.split(":")[1]
    println(lineNoPrefix)
    val lineSplit = lineNoPrefix.split(";")
    println("Check line $lineSplit")
    return lineSplit.all { checkRound(it) }.also { println("Line valid: $it") }
}

fun checkRound(round: String): Boolean {
    val roundSplit = round.split(",")
    println("Check round $roundSplit")
    return roundSplit.all { checkColour(it) }.also { println("Round valid: $it") }
}

fun checkColour(colour: String): Boolean {
    return checkColourCount(getColourCount(colour))
}

fun getColourCount(colour: String): Pair<Int, String> {
    val (colourNumStr, colourName) = colour.trim().split(" ", limit = 2)
    return Pair(Integer.valueOf(colourNumStr), colourName)
}

fun checkColourCount(colour: Pair<Int, String>, rMax: Int = 12, gMax: Int = 13, bMax: Int = 14): Boolean {
    val (colourNum, colourName) = colour
    when (colourName) {
        "red" -> if (colourNum > rMax) return false
        "green" -> if (colourNum > gMax) return false
        "blue" -> if (colourNum > bMax) return false
    }
    return true
}
