import println
import readInput
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.sqrt

fun main() {
    fun part1(input: List<String>): Long {
        var tot = 1L
        val times = parseLine(input[0])
        val distances = parseLine(input[1])
        times.indices.forEach { idx ->
            val time = times[idx]
            val distance = distances[idx]
            tot *= countTimes(time, distance)
            // h(t-h) > d | h2-th+d < 0 | (h-t/2)2 < t2/4 - d | 2h < t +- sqrt(t2-4d)
        }
        return tot
    }

    fun part2(input: List<String>): Long {
        val time = parseLine2(input[0])[0]
        val distance = parseLine2(input[1])[0]
        return countTimes(time, distance)
    }

    // test if implementation meets criteria from the description, like:
//    val testInput1 = readInput("Day06_test1")
//    check(part1(testInput1) == 288L)
    val testInput2 = readInput("Day06_test2")
    check(part2(testInput2) == 71503L)

    val input = readInput("Day06")
//    part1(input).println()
    part2(input).println()
}

fun parseLine(line: String): List<Long> = line.replace(Regex("\\s+"), " ").split(" ").drop(1).map { it.toLong() }

fun parseLine2(line: String): List<Long> = line.replace(Regex("\\s+"), "").split(":").drop(1).map { it.toLong() }

fun countTimes(time: Long, distance: Long): Long {
    val det = sqrt((time * time - 4 * distance).toDouble())
    val whole = floor(det) == det
    val max = (time + det) / 2
    val min = (time - det) / 2
    var h = (floor(max) - ceil(min) + 1).toLong()
    if (whole) h -= 2
    return h
}