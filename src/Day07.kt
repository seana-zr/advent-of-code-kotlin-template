const val JOKER = true

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

fun String.jokerHand(): String {
    // edge case: most frequent card is J
    val frequencies = frequencies().filter { it.key != 'J' }
    // edge case: JJJJJ
    if (frequencies.isEmpty()) return "11111"

    val highestFrequency = frequencies.values.max()
    val mostFrequentCard = first { frequencies[it] == highestFrequency }
    return replace('J', mostFrequentCard)
}

// turn the jokers into whatever card has the highest frequency
fun String.getHandTypeWithJoker(): HandType {
    return jokerHand().getHandType()
}

enum class Card(val n: Int) {
    A(14), K(13), Q(12), J(if (JOKER) 1 else 11), T(10)
}

fun Char.cardValue(): Int {
    val asString = this.toString()
    return if (Card.entries.any { it.name == asString }) Card.valueOf(asString).n
    else this.digitToInt()
}

data class Player(
    val hand: String,
    val bid: Int
) : Comparable<Player> {
    override fun compareTo(other: Player): Int {
        hand.zip(other.hand).forEach { (our, other) ->
            val difference = our.cardValue() - other.cardValue()
            if (difference != 0) return difference
        }
        return 0
    }
}

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
                compareBy({ it.hand.getHandType() }, { it })
            )
        players.forEachIndexed { index, player ->
            val rank = index + 1
            val score = player.bid * rank
            println("${player.hand} ${player.bid} rank $rank score $score ${player.hand.getHandType()}")
            result += score
        }
        println("RESULT: $result")
        return result
    }

    fun part2(input: List<String>): Long {
        var result = 0.toLong()
        val players = input
            .map {
                val (hand, bid) = it.split(" ")
                Player(hand = hand, bid = bid.toInt())
            }
            .sortedWith(
                compareBy({ it.hand.getHandTypeWithJoker() }, { it })
            )
        players.forEachIndexed { index, player ->
            val rank = index + 1
            val score = player.bid * rank
            println("${player.hand} ${player.bid} rank $rank hand ${player.hand.jokerHand()} ${player.hand.getHandTypeWithJoker()}")
            result += score
        }
        println("RESULT: $result")
        return result
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test_2")
//    check(part1(testInput) == 6440)
    check(part2(testInput) == 6839.toLong())

    val input = readInput("Day07")
//    part1(input).println()
    part2(input).println()
}