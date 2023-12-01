fun main() {
    fun getCalibrationValue(s: String): Int {
        val first = s.find { it.isDigit() }!!.digitToInt()
        val last = s.findLast { it.isDigit() }!!.digitToInt()
        return first.times(10).plus(last)
    }

    fun part1(input: List<String>): Int {
        return input.sumOf { getCalibrationValue(it) }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 142)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
