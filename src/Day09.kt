fun main() {

    fun List<Int>.getDifferences(): MutableList<Int> {
        val result = mutableListOf<Int>()
        for (i in 1..this.lastIndex) {
            result.add(this[i] - this[i - 1])
        }
        return result
    }

    fun List<Int>.isAllZeros(): Boolean = all { it == 0 }

    fun part1(input: List<String>): Int {
        var result = 0
        input.forEach { line ->
            val firstSequence = line
                .split(" ")
                .map { it.toInt() }
                .toMutableList()
            val sequences = mutableListOf(firstSequence)
            while (!sequences.last().isAllZeros()) {
                sequences.add(sequences.last().getDifferences())
            }
//            println(sequences)
            sequences.last().add(0)
            val reversed = sequences.reversed()
            reversed.drop(1).forEachIndexed { index, sequence ->
                sequence.add(reversed[index].last() + sequence.last())
            }
            println(sequences)
            result += sequences.first().last()
            println(result)
        }
        println("RESULT: $result")
        return result
    }

    fun part2(input: List<String>): Int {
        var result = 0
        input.forEach { line ->
            val firstSequence = line
                .split(" ")
                .map { it.toInt() }
                .reversed()
                .toMutableList()
            val sequences = mutableListOf(firstSequence)
            while (!sequences.last().isAllZeros()) {
                sequences.add(sequences.last().getDifferences())
            }
//            println(sequences)
            sequences.last().add(0)
            val reversed = sequences.reversed()
            reversed.drop(1).forEachIndexed { index, sequence ->
                sequence.add(reversed[index].last() + sequence.last())
            }
            println(sequences)
            result += sequences.first().last()
            println(result)
        }
        println("RESULT: $result")
        return result
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
//    check(part1(testInput) == 114)
    check(part2(testInput) == 2)

    val input = readInput("Day09")
//    part1(input).println()
    part2(input).println()
}