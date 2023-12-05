import kotlin.math.min

fun main() {

    data class RangeMap(
        val sourceStart: Long,
        val sourceEnd: Long,
        val destStart: Long,
        val destEnd: Long,
    ) {
        fun appliesTo(n: Long): Boolean = n in sourceStart..sourceEnd

        fun offset(): Long = destStart - sourceStart
    }

    fun parseMap(iterator: Iterator<String>): List<RangeMap> {
        println(iterator.next())

        val rangeMaps = mutableListOf<RangeMap>()
        while (iterator.hasNext()) {
            val next = iterator.next()
            if (next.isBlank()) break
            val (startX, startY, range) = next.split(" ").map { it.toLong() }
            rangeMaps.add(
                RangeMap(
                    sourceStart = startY,
                    sourceEnd = startY + range - 1,
                    destStart = startX,
                    destEnd = startX + range - 1
                )
            )
        }
        return rangeMaps
    }

    fun part1(input: List<String>): Long {
        var result = Long.MAX_VALUE
        val seeds = input.first()
            .drop(7)
            .split(" ")
            .map { it.toLong() }

        val iterator = input.iterator()

        println(iterator.next())
        iterator.next()

        val seedToSoil = parseMap(iterator)
        val soilToFertilizer = parseMap(iterator)
        val fertilizerToWater = parseMap(iterator)
        val waterToLight = parseMap(iterator)
        val lightToTemperature = parseMap(iterator)
        val temperatureToHumidity = parseMap(iterator)
        val humidityToLocation = parseMap(iterator)

        fun convert(n: Long, rangeMaps: List<RangeMap>): Long =
            n + (rangeMaps
                .find { it.appliesTo(n) }
                ?.offset() ?: 0)

        seeds.forEach { seed ->
            val soil = convert(seed, seedToSoil)
            val fertilizer = convert(soil, soilToFertilizer)
            val water = convert(fertilizer, fertilizerToWater)
            val light = convert(water, waterToLight)
            val temperature = convert(light, lightToTemperature)
            val humidity = convert(temperature, temperatureToHumidity)
            val location = convert(humidity, humidityToLocation)
            result = min(result, location)
        }

        println("RESULT: $result")
        return result
    }

    fun part2(input: List<String>): Int {
        var result = 0

        println("RESULT: $result")
        return result
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 35.toLong())

    val input = readInput("Day05")
    part1(input).println()
//    part2(input).println()
}
