fun main() {

    fun part1(input: List<String>): Int {
        var tot = 0
        input.forEach {
            val (numsLeft, numsRight) = getNumsSplit(it)
            println("$numsLeft || $numsRight")
            var totCard = 0
            numsLeft.forEach { i ->
                if (i in numsRight) {
                    if (totCard == 0) {
                        totCard = 1
                    } else {
                        totCard *= 2
                    }
                    println("Num $i tot card $totCard")
                }
            }
            tot += totCard
            println("Tot $tot")
        }
        return tot
    }

    fun part2(input: List<String>): Int {
        var tot = 0
        val mults: MutableList<Int> = (1..input.size).map { 1 }.toMutableList()
        input.forEachIndexed { index, inp ->
            val (numsLeft, numsRight) = getNumsSplit(inp)
            println("$numsLeft || $numsRight")
            val mult = mults[index]
            tot += mult
            println("$mult copies of $index, $tot copies total")
            var nextIdx = index + 1
            numsLeft.forEach { i ->
                if (i in numsRight) {
                    println("Add $mult copies of $nextIdx")
                    mults[nextIdx] += mult
                    nextIdx++
                }
            }
        }
        return tot
    }

    val testInput1 = readInput("Day04_test1")
    check(part1(testInput1) == 13)
    val testInput2 = readInput("Day04_test2")
    check(part2(testInput2) == 30)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}

fun getNumsSplit(input: String): Pair<List<Int>, List<Int>> {
    val inpSplit = input.split(Regex("\\s+"))
    val idxBreak = inpSplit.indexOf("|")
    val numsLeft = inpSplit.slice(2..<idxBreak).map { i -> Integer.valueOf(i) }
    val numsRight = inpSplit.slice(idxBreak + 1..<inpSplit.size).map { i -> Integer.valueOf(i) }
    return Pair(numsLeft, numsRight)
}
