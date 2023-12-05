import kotlin.math.min

fun main() {

    data class RangeMap(
        val sourceStart: Long,
        val sourceEnd: Long,
        val destStart: Long,
        val destEnd: Long,
    ) {
        fun appliesTo(n: Long): Boolean = n in sourceStart..sourceEnd

        fun appliesBackwardsTo(n: Long): Boolean = n in destStart..destEnd

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

    data class SeedRange(
        val startSeed: Long,
        val length: Long
    ) {
        val endSeed = startSeed + length - 1
    }

    fun isValidSeed(n: Long, seedRanges: List<SeedRange>): Boolean = seedRanges.any { seedRange ->
        n in seedRange.startSeed..seedRange.endSeed
    }

    fun part2(input: List<String>): Long {
        val seedRanges = input.first()
            .drop(7)
            .split(" ")
            .map { it.toLong() }
            .chunked(2)
            .map { SeedRange(it[0], it[1]) }

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

        fun convertBackwards(n: Long, rangeMaps: List<RangeMap>): Long =
            n - (rangeMaps
                .find { it.appliesBackwardsTo(n) }
                ?.offset() ?: 0)

        var location = 0.toLong()
        while (true) {
            val humidity = convertBackwards(location, humidityToLocation)
            val temperature = convertBackwards(humidity, temperatureToHumidity)
            val light = convertBackwards(temperature, lightToTemperature)
            val water = convertBackwards(light, waterToLight)
            val fertilizer = convertBackwards(water, fertilizerToWater)
            val soil = convertBackwards(fertilizer, soilToFertilizer)
            val seed = convertBackwards(soil, seedToSoil)
            if (isValidSeed(seed, seedRanges)) {
                println("RESULT: $location")
                return location
            }
            location++
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part2(testInput) == 46.toLong())

    val input = readInput("Day05")
//    part1(input).println()
    part2(input).println()
}
