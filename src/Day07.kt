fun main() {
    fun part1(input: List<String>): Long {
        var tot = 0L
        val jokers = false
        val handsBids: List<Pair<CharArray, Long>> = input.map { readHandBid(it) }
        val handsSorted = handsBids.sortedWith { hand1, hand2 -> compareHands(hand1.first, hand2.first, jokers) }
        handsSorted.forEachIndexed { idx, hand ->
            println("$idx: ${hand.first.concatToString()} ${getHandType(hand.first, jokers)} ${hand.second}")
            tot += (idx + 1) * hand.second
        }
        return tot
    }

    fun part2(input: List<String>): Long {
        var tot = 0L
        val jokers = true
        val handsBids: List<Pair<CharArray, Long>> = input.map { readHandBid(it) }
        val handsSorted = handsBids.sortedWith { hand1, hand2 -> compareHands(hand1.first, hand2.first, jokers) }
        handsSorted.forEachIndexed { idx, hand ->
            println("$idx: ${hand.first.concatToString()} ${getHandType(hand.first, jokers)} ${hand.second}")
            tot += (idx + 1) * hand.second
        }
        return tot
    }

    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("Day07_test1")
    check(part1(testInput1) == 6440L)
//    val testInput2 = readInput("Day07_test2")
//    check(part2(testInput2) == 5905L)

    val input = readInput("Day07")
    part1(input).println()
//    part2(input).println()
}

enum class HandType(val rank: Int) {
    FIVE(7),
    FOUR(6),
    FULL_HOUSE(5),
    THREE(4),
    TWO_PAIRS(3),
    ONE_PAIR(2),
    HIGH(1),
}

fun readHandBid(line: String): Pair<CharArray, Long> {
    val lineSplit = line.split(" ")
    return Pair(lineSplit[0].toCharArray(), lineSplit[1].toLong())
}

fun compareHands(hand1: CharArray, hand2: CharArray, jokers: Boolean = false): Int {
    val type1 = getHandType(hand1, jokers)!!
    val type2 = getHandType(hand2, jokers)!!
    if (type1 != type2) {
        return type1.rank - type2.rank
    }
    hand1.indices.forEach { idx ->
        val comp = compareCards(hand1[idx], hand2[idx], jokers)
        if (comp != 0) {
            return comp
        }
    }
    return 0
}

fun compareCards(card1: Char, card2: Char, jokers: Boolean = false): Int {
    if (card1 == card2) {
        return 0
    }
    if (jokers) {
        if (card1 == 'J') {
            return -1
        }
        if (card2 == 'J') {
            return 1
        }
    }
    if (card1.isDigit()) {
        if (card2.isDigit()) {
            return card1.digitToInt() - card2.digitToInt()
        }
        return -1
    }
    if (card2.isDigit()) {
        return 1
    }
    val letterCards = listOf('K', 'Q', 'J', 'T')
    return letterCards.indexOf(card2) - letterCards.indexOf(card1)
}

fun getHandType(hand: CharArray, jokers: Boolean = false): HandType? {
    val counts = mutableMapOf<Char, Int>()
    hand.forEach { card ->
        if (!counts.containsKey(card)) {
            counts[card] = 0
        }
        counts[card] = counts[card]!! + 1
    }
    if (jokers) {
        val jokerCount = counts['J'] ?: 0
        if (jokerCount == 5) {
            return HandType.FIVE
        }
        counts.remove('J')
        val maxCountNoJ = counts.values.max()
        val maxEntry = counts.entries.first { (_, value) -> value == maxCountNoJ }
        maxEntry.setValue(maxEntry.value + jokerCount)
    }
    val maxCount = counts.values.max()
    when (maxCount) {
        5 -> return HandType.FIVE
        4 -> return HandType.FOUR
        1 -> return HandType.HIGH
        3 -> {
            if (counts.values.contains(2)) {
                return HandType.FULL_HOUSE
            }
            return HandType.THREE
        }
        2 -> {
            if (counts.values.count { it == 2 } <= 1) {
                return HandType.ONE_PAIR
            }
            return HandType.TWO_PAIRS
        }
    }
    return null
}
