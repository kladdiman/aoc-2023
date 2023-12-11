fun main() {
    fun part1(input: List<String>): Int {
        var tot = 0
        input.forEach {
            tot += getDigitSum(it)
        }
        return tot
    }

    fun part2(input: List<String>): Int {
        var tot = 0
        input.forEach {
            val inp = replaceNumNames(it)
            val y = getDigitSum(inp)
            tot += y
            println("$it $inp $y $tot ${checkReplaceNumNames(it, inp)}")
        }
        return tot
    }

    val testInput1 = readInput("Day01_test1")
    check(part1(testInput1) == 142)
    val testInput2 = readInput("Day01_test2")
    check(part2(testInput2) == 281)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}

fun getDigitSum(inp: String): Int {
    val a = inp.find { c -> c.isDigit() }
    val b = inp.findLast { c -> c.isDigit() }
    return Integer.valueOf("" + a + b)
}

val nums = mapOf(
    "one" to 1,
    "two" to 2,
    "three" to 3,
    "four" to 4,
    "five" to 5,
    "six" to 6,
    "seven" to 7,
    "eight" to 8,
    "nine" to 9,
)

fun checkReplaceNumNames(input: String, newVal: String): Boolean {
    var inp = input
    var n = newVal
    nums.entries.forEach { (name, num) ->
        inp = inp.replace("" + num, name)
        n = n.replace("" + num, name)
    }
    return inp == n
}

    fun replaceNumNames(input: String): String {
        var inp = input
        var idx = 0
        while (idx < inp.length) {
            var found = false
            for (name in nums.keys.sortedBy { it.length }) {
                val num = nums[name]
                if (idx + name.length > inp.length) {
                    continue
                }
                if (inp.substring(idx, idx + name.length) == name) {
                    inp = inp.substring(0, idx) + num + inp.substring(idx + name.length)
                    found = true
                    idx = 0
                    break
                }
            }
            if (!found) {
                idx++
            }
        }
        return inp
    }
