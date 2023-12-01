fun main() {
    fun getCalibrationValue(s: String): Int {
        // we know there MUST be a first and last digit in the input
        val first = s.first { it.isDigit() }.digitToInt()
        val last = s.last { it.isDigit() }.digitToInt()
        return first.times(10).plus(last)
    }

    fun part1(input: List<String>): Int {
        return input.sumOf { getCalibrationValue(it) }
    }

    val wordsDigits = mapOf(
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9
    )

    fun getRealCalibrationValue(s: String): Int {
        // first we consider the digits like last time
        val initialFirst = s.find { it.isDigit() }
        val initialLast = s.findLast { it.isDigit() }

        var first = initialFirst?.digitToInt() ?: 0
        var firstIndex = s.indexOfFirst { it == initialFirst }
        var last = initialLast?.digitToInt() ?: 0
        var lastIndex = s.indexOfLast { it == initialLast }

        // now we look for each word, and compare indices if they're found
        for ((word, digit) in wordsDigits) {
            val i = s.indexOf(word)
            val j = s.lastIndexOf(word)
            if (i != -1 && (i < firstIndex || firstIndex == -1)) {
                first = digit
                firstIndex = i
            }
            if (j != -1 && (j > lastIndex || lastIndex == -1)) {
                last = digit
                lastIndex = j
            }
        }

        return first.times(10).plus(last)
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { getRealCalibrationValue(it) }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part2(testInput) == 281)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
