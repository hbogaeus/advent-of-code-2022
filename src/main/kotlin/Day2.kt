enum class Move(val score: Int) {
    ROCK(1),
    PAPER(2),
    SCISSORS(3);

    companion object {
        fun fromChar(char: Char) = when (char) {
            'A', 'X' -> ROCK
            'B', 'Y' -> PAPER
            'C', 'Z' -> SCISSORS
            else -> throw IllegalStateException()
        }
    }
}

enum class Outcome(val score: Int) {
    WIN(6),
    DRAW(3),
    LOSE(0);
}

fun main() {
    var score = 0

    fileFromResource("day2/input.txt").forEachLine {
        val villainMove = Move.fromChar(it[0])
        val yourMove = Move.fromChar(it[2])

        val outcome = getOutcome(yourMove, villainMove)

        score += outcome.score + yourMove.score
    }

    println(score)
}

val outcomeArray = arrayOf(
    arrayOf(
        Outcome.DRAW,
        Outcome.LOSE,
        Outcome.WIN
    ), arrayOf(
        Outcome.WIN,
        Outcome.DRAW,
        Outcome.LOSE
    ), arrayOf(
        Outcome.LOSE,
        Outcome.WIN,
        Outcome.DRAW
    )
)

private fun getOutcome(yourMove: Move, villainMove: Move) = outcomeArray[yourMove.ordinal][villainMove.ordinal]
