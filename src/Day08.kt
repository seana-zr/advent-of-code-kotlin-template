fun main() {

    data class Branch(
        val left: String,
        val right: String
    )

    fun part1(input: List<String>): Int {
        var result = 0
        val directions = input[0]
        val mapInput = input.drop(2)
        val map = mutableMapOf<String, Branch>()
        mapInput.forEach {
            val key = it.substring(0..2)
            val left = it.substring(7..9)
            val right = it.substring(12..14)
            map[key] = Branch(left, right)
        }
        var directionIndex = 0
        var location = "AAA"
        while (location != "ZZZ") {
            if (directions[directionIndex] == 'L') {
                location = map[location]!!.left
            } else {
                location = map[location]!!.right
            }
            result++
            directionIndex = (directionIndex + 1) % (directions.length)
        }
        println("RESULT: $result")
        return result
    }

    fun String.getLoop(map: Map<String, Branch>, directions: String): Int {
        var result = 0
        var directionIndex = 0
        var location = this
        while (location.last() != 'Z') {
            if (directions[directionIndex] == 'L') {
                location = map[location]!!.left
            } else {
                location = map[location]!!.right
            }
            result++
            directionIndex = (directionIndex + 1) % (directions.length)
        }
        return result
    }

    fun calculateLCM(numbers: LongArray): Long {
        // Function to find the GCD of two numbers using Euclidean algorithm
        fun findGCD(a: Long, b: Long): Long {
            return if (b == 0.toLong()) a else findGCD(b, a % b)
        }

        // Function to find the LCM of two numbers
        fun findLCMOfTwo(a: Long, b: Long): Long {
            return (a * b) / findGCD(a, b)
        }

        // Function to find the LCM of an array of numbers
        fun findLCMOfArray(numbers: LongArray, index: Int, currentLCM: Long): Long {
            return if (index == numbers.size) {
                currentLCM
            } else {
                val currentNumber = numbers[index]
                val gcd = findGCD(currentLCM, currentNumber)
                val lcm = (currentLCM * currentNumber) / gcd
                findLCMOfArray(numbers, index + 1, lcm)
            }
        }

        // Check if the array has at least two elements
        if (numbers.size < 2) {
            throw IllegalArgumentException("Array must have at least two elements.")
        }

        // Start with the LCM of the first two numbers
        val initialLCM = findLCMOfTwo(numbers[0], numbers[1])

        // Find the LCM of the remaining numbers in the array
        return findLCMOfArray(numbers, 2, initialLCM)
    }

    fun part2(input: List<String>): Long {
        var result = 0.toLong()
        val directions = input[0]
        val mapInput = input.drop(2)
        val map = mutableMapOf<String, Branch>()
        val trackedNodes = mutableListOf<String>()
        mapInput.forEach {
            val key = it.substring(0..2)
            val left = it.substring(7..9)
            val right = it.substring(12..14)
            map[key] = Branch(left, right)
            if (key.last() == 'A') trackedNodes.add(key)
        }

        val loops = trackedNodes
            .map { node -> node.getLoop(map, directions).toLong() }

        println(trackedNodes)
        println(loops)

        // https://www.calculatorsoup.com/calculators/math/lcm.php
        result = calculateLCM(loops.toLongArray())

        println("RESULT: $result")
        return result
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 2)
    val testInput2 = readInput("Day08_test_2")
    check(part1(testInput2) == 6)
    val testInput3 = readInput("Day08_test_3")
    check(part2(testInput3) == 6.toLong())

    val input = readInput("Day08")
//    part1(input).println()
    part2(input).println()
}