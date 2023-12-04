import java.lang.IllegalStateException
import java.util.regex.Pattern
import kotlin.math.max

fun main() {
    data class GameRound(
        var red: Int = 0,
        var green: Int = 0,
        var blue: Int = 0,
    )

    /**
     * which games would have been possible if the bag contained only
     * 12 red cubes, 13 green cubes, and 14 blue cubes
     */
    fun isRoundValid(red: Int, green: Int, blue: Int): Boolean {
        return red <= 12 && green <= 13 && blue <= 14
    }

    fun extractNumberAndColor(input: String): Pair<Int, String> {
        val pattern = Pattern.compile("(\\d+)([a-zA-Z]+)")
        val matcher = pattern.matcher(input)

        if (matcher.find()) {
            val number = matcher.group(1).toInt()
            val color = matcher.group(2)
            return Pair(number, color)
        } else {
            throw IllegalArgumentException("Invalid input format: $input")
        }
    }

    fun parseGameToRounds(game: String): List<GameRound> {
        val roundInputs = game
            .dropWhile { it != ':' }
            .drop(2)
            .filterNot { it.isWhitespace() }
            .split(";")
        val rounds = roundInputs.map { input ->
            var round = GameRound()
            input.split(",").forEach { cubes ->
                val (number, color) = extractNumberAndColor(cubes)
                round = when (color) {
                    "red" -> round.copy(red = number)
                    "blue" -> round.copy(blue = number)
                    "green" -> round.copy(green = number)
                    else -> throw IllegalStateException("$color is not a valid color")
                }
            }
            round
        }
        return rounds
    }

    fun part1(input: List<String>): Int {
        var result = 0
        input.forEachIndexed { index, game ->
            val gameId = index + 1
            val rounds = parseGameToRounds(game)
            val gameIsValid = rounds.all { isRoundValid(it.red, it.green, it.blue) }
            result += if (gameIsValid) gameId else 0
        }
        return result
    }

    fun getMinimumCubeSet(rounds: List<GameRound>): GameRound {
        var result = GameRound()
        rounds.forEach { round ->
            result = result.copy(
                red = max(result.red, round.red),
                blue = max(result.blue, round.blue),
                green = max(result.green, round.green)
            )
        }
        return result
    }

    fun part2(input: List<String>): Int {
        var result = 0
        input.forEach { game ->
            val rounds = parseGameToRounds(game)
            val minCubeSet = getMinimumCubeSet(rounds)
            result += minCubeSet.blue * minCubeSet.green * minCubeSet.red
        }
        return result
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part2(testInput) == 2286)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
