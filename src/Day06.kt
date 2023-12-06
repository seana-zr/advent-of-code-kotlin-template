import java.util.regex.Pattern

fun main() {

    fun part1(input: List<String>): Int {
        val result: Int
        val waysPerRace = mutableListOf<Int>()
        val timesToBeat = input[0]
            .dropWhile { !it.isDigit() }
            .split(Pattern.compile("\\s+"))
            .map { it.toInt() }
        val distances = input[1]
            .dropWhile { !it.isDigit() }
            .split(Pattern.compile("\\s+"))
            .map { it.toInt() }
        val races = timesToBeat.zip(distances)
        println("races: $races")
        for ((timeToBeat, raceDistance) in races) {
            var ways = 0
            println("timeToBeat: $timeToBeat | raceDistance: $raceDistance")
            for (chargeTime in 0..<timeToBeat) {
                val timeLeftAfterCharge = timeToBeat - chargeTime
                val distanceTraveled = timeLeftAfterCharge * chargeTime
                if (distanceTraveled > raceDistance) {
                    print("$chargeTime | ")
                    ways++
                }
            }
            waysPerRace.add(ways)
            println("ways: $ways")
        }
        result = waysPerRace.reduce(Int::times)
        println("RESULT: $result")
        return result
    }

    fun part2(input: List<String>): Long {
        var result = 0.toLong()
        val timeToBeat = input[0]
            .filter { it.isDigit() }
            .toLong()
        val raceDistance = input[1]
            .filter { it.isDigit() }
            .toLong()
        for (chargeTime in 1..<timeToBeat) {
            val timeLeftAfterCharge = timeToBeat - chargeTime
            val distanceTraveled = timeLeftAfterCharge * chargeTime
            if (distanceTraveled > raceDistance) {
                result++
            }
        }
        println("RESULT: $result")
        return result
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part2(testInput) == 71503.toLong())

    val input = readInput("Day06")
//    part1(input).println()
    part2(input).println()
}
