enum class HandType {
    HIGH, ONE_PAIR, TWO_PAIR, THREE, FULL, FOUR, FIVE;
}

fun String.distinct() = toSet().size

fun String.frequencies() = groupingBy { it }.eachCount()

fun String.getHandType(): HandType {
    return when (distinct()) {
        1 -> HandType.FIVE
        2 -> {
            val firstCardCount = count { it == this.first() }
            if (firstCardCount == 2 || firstCardCount == 3) {
                HandType.FULL
            } else {
                HandType.FOUR
            }
        }

        3 -> {
            val frequencies = frequencies()
            if (frequencies.any { it.value == 3 }) {
                HandType.THREE
            } else {
                HandType.TWO_PAIR
            }
        }

        4 -> HandType.ONE_PAIR
        5 -> HandType.HIGH
        else -> throw IllegalStateException()
    }
}

enum class Card(val n: Int) {
    A(14), K(13), Q(12), J(11), T(10)
}

fun Char.cardValue(): Int {
    val asString = this.toString()
    return if (Card.entries.any { it.name == asString }) Card.valueOf(asString).n
    else this.digitToInt()
}

// TODO: THIS IS TOTALLY WRONG, MAKE A COMPARATOR INSTEAD
fun String.getHandValue(): Long {
    var value: Long = 0
    forEach {
        value *= 15
        value += it.cardValue()
    }
    return value
}

data class Player(
    val hand: String,
    val bid: Int
)

fun main() {

    fun part1(input: List<String>): Int {
        var result = 0
        // the rank of each player is their (index + 1)
        val players = input
            .map {
                val (hand, bid) = it.split(" ")
                Player(hand = hand, bid = bid.toInt())
            }
            .sortedWith(
                compareBy({ it.hand.getHandType() }, { it.hand.getHandValue() })
            )
        players.forEachIndexed { index, player ->
            val rank = index + 1
            val score = player.bid * rank
            println("${player.hand} ${player.bid} rank $rank score $score ${player.hand.getHandType()} ${player.hand.getHandValue()}")
            result += score
        }
        println("RESULT: $result")
        return result
    }

    fun part2(input: List<String>): Long {
        var result = 0.toLong()

        println("RESULT: $result")
        return result
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 6440)

    val input = readInput("Day07")
    part1(input).println()
//    part2(input).println()
}