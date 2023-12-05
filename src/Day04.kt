import java.util.LinkedList
import java.util.regex.Pattern
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow

fun main() {

    data class Card(
        val id: Int,
        val winning: Set<Int>,
        val hand: Set<Int>
    )

    fun parseCard(card: String): Card {
        val combos = card
            .dropWhile { it != ':' }
            .drop(2)
            .trim()
            .split("|")
            .map { s ->
                s
                    .trim()
                    .split(regex = Pattern.compile("\\s+"))
                    .map { it.toInt() }
                    .toSet()
            }
        assert(combos.size == 2)
        return Card(-1, combos.first(), combos.last())
    }

    fun part1(input: List<String>): Int {
        var result = 0
        for (card in input) {
            val (_, winning, hand) = parseCard(card)
            result += 2.0.pow(winning.intersect(hand).size-1).toInt()
        }
        return result
    }

    fun part2(input: List<String>): Int {
        var result = 0
        val cardMenu = input.mapIndexed { index, s ->  parseCard(s).copy(id = index) }
        val queue = LinkedList(cardMenu)
        while (queue.isNotEmpty()) {
            result++

            val card = queue.pop()
            val (id, winning, hand) = card
            val numCopies = winning.intersect(hand).size

            print("${card.id} won $numCopies | \t")

            val range = (id + 1..min(input.size - 1 , id + numCopies))
            print("adding $range \t")

            range.forEach { i ->
                queue.offer(cardMenu[i])
            }
            println()
        }
        println("RESULT: $result")
        return result
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part2(testInput) == 30)

    val input = readInput("Day04")
//    part1(input).println()
    part2(input).println()
}
