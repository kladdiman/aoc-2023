package template

import println
import readInput

fun main() {
    fun part1(input: List<String>): Int {
        var tot = 0
        return tot
    }

    fun part2(input: List<String>): Int {
        var tot = 0
        return tot
    }

    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("DayXX_test1")
    check(part1(testInput1) == 0)
    val testInput2 = readInput("DayXX_test2")
    check(part2(testInput2) == 0)

    val input = readInput("DayXX")
    part1(input).println()
    part2(input).println()
}
