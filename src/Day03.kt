import java.util.regex.Pattern
import kotlin.math.max
import kotlin.math.min

fun main() {

    // [start, end)
    fun extractNumbersWithIndices(line: String): List<Triple<Int, Int, Int>> {
        val pattern = Pattern.compile("\\d+")
        val matcher = pattern.matcher(line)

        val matches = mutableListOf<Triple<Int, Int, Int>>()

        while (matcher.find()) {
            val start = matcher.start()
            val end = matcher.end()
            val number = matcher.group().toInt()
            matches.add(Triple(number, start, end))
        }

        return matches
    }

    fun isPartNumber(row: Int, startCol: Int, endCol: Int, input: List<String>): Boolean {
        val startRowBound = max(0, row - 1)
        val endRowBound = min(input.size - 1, row + 1)
        val startColBound = max(0, startCol - 1)
        val endColBound = min(input.first().length - 1, endCol)
        print("row $row startCol $startCol endCol $endCol \t")
        print("rows [$startRowBound,$endRowBound]\tcols [$startColBound,$endColBound]\t")
        for (r in startRowBound .. endRowBound) {
            for (c in startColBound .. endColBound) {
                if (r == row && startCol <= c && c < endCol) continue
                if (input[r][c] != '.' && !input[r][c].isDigit()) {
                    println("${input[r][c]} at ($r, $c)")
                    return true
                }
            }
        }
        println("no")
        return false
    }

    fun part1(input: List<String>): Int {
        var result = 0
        input.forEachIndexed { row, s ->
            for ((n, start, end) in extractNumbersWithIndices(s)) {
                print("$n: \t")
                if (isPartNumber(row, start, end, input)) {
                    result += n
                }
            }
        }
        println(result)
        return result
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 4361)

    val input = readInput("Day03")
    part1(input).println()
//    part2(input).println()
}
