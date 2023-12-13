import kotlin.math.min

fun main() {
    fun part1(input: List<String>): Long {
        var min = Long.MAX_VALUE
        val (seeds, maps, mapTypesMap) = splitInput(input)
        seeds.forEach { seed ->
            val mapValue = mapSeed("seed", seed, maps, mapTypesMap)
            println("Seed $seed mapped to $mapValue")
            min = min(min, mapValue)
        }
        return min
    }

    fun part2(input: List<String>): Long {
        var min = Long.MAX_VALUE
        val (seeds, maps, mapTypesMap) = splitInput(input)
        for (i in 0..<seeds.size / 2) {
            val seedFrom = seeds[i * 2]
            val seedTo = seedFrom + seeds[i * 2 + 1]
            println("Seed range $seedFrom-$seedTo")
            (seedFrom..seedTo).forEach { seed ->
                val mapValue = mapSeed("seed", seed, maps, mapTypesMap)
                println("Seed $seed mapped to $mapValue")
                min = min(min, mapValue)
            }
        }
        return min
    }

    // test if implementation meets criteria from the description, like:
//    val testInput1 = readInput("Day05_test1")
//    check(part1(testInput1) == 35L)
//    val testInput2 = readInput("Day05_test2")
//    check(part2(testInput2) == 46L)

    val input = readInput("Day05")
//    part1(input).println()
    part2(input).println()
}

fun splitInput(input: List<String>): Triple<List<Long>, Map<String, List<String>>, Map<String, String>> {
    val seeds = input[0].split(" ").drop(1).map { it.toLong() }
    var maps = mutableMapOf<String, MutableList<String>>()
    var mapKey = ""
    var mapTypesMap = mutableMapOf<String, String>()
    for (line in input.drop(2)) {
        if (line.length < 2) {
            continue
        }
        if (line.contains("map")) {
            val mapType = line.split(" ")[0].split("-")
            val mapFrom = mapType[0]
            val mapTo = mapType[2]
            mapKey = mapFrom
            mapTypesMap[mapFrom] = mapTo
            maps[mapKey] = mutableListOf()
        } else {
            val ruleParts = line.trim().split(" ").map { it.toLong() }
            maps[mapKey]?.add(line)
        }
    }
    return Triple(seeds, maps, mapTypesMap)
}

fun mapSeed(mapType: String, mapValue: Long, maps: Map<String, List<String>>, mapTypesMap: Map<String, String>): Long {
    val map: List<String> = maps[mapType] ?: return mapValue
    val mapTypeNext = mapTypesMap[mapType]!!
    return mapSeed(mapTypeNext, mapValue(map, mapTypeNext, mapValue), maps, mapTypesMap)
}

fun mapValue(rules: List<String>, mapType: String, value: Long): Long {
    rules.forEach { rule ->
        val ruleParts = rule.trim().split(" ").map { it.toLong() }
        val destFrom = ruleParts[0]
        val sourceFrom = ruleParts[1]
        val sourceTo = sourceFrom + ruleParts[2]
        if (value in (sourceFrom..sourceTo)) {
            val mapValue = destFrom + value - sourceFrom
            println("Value $value matches $mapType $sourceFrom-$sourceTo dest $destFrom, return $mapValue")
            return mapValue
        }
    }
    println("Value $value no match $mapType, return $value")
    return value
}