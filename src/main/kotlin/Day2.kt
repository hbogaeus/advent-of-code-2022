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

    companion object {
        fun fromChar(char: Char) = when (char) {
            'X' -> LOSE
            'Y' -> DRAW
            'Z' -> WIN
            else -> throw IllegalStateException()
        }
    }
}

fun main() {
    var score = 0

    fileFromResource("day2/input.txt").forEachLine {
        val villainMove = Move.fromChar(it[0])
        val desiredOutcome = Outcome.fromChar(it[2])

        val expectedMove = getExpectedMove(villainMove, desiredOutcome)

        score += desiredOutcome.score + expectedMove.score
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

val moveArray = arrayOf(
    arrayOf(
        Move.PAPER,
        Move.ROCK,
        Move.SCISSORS
    ), arrayOf(
        Move.SCISSORS,
        Move.PAPER,
        Move.ROCK
    ), arrayOf(
        Move.ROCK,
        Move.SCISSORS,
        Move.PAPER
    )
)

private fun getExpectedMove(villainMove: Move, desiredOutcome: Outcome) = moveArray[villainMove.ordinal][desiredOutcome.ordinal]
