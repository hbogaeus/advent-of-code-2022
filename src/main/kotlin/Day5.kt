import java.util.*

data class Instruction(val from: Int, val to: Int, val amount: Int)

fun main() {
    val readText = fileFromResource("day5/input.txt").readText()
    val (cratesString, instructionsString) = readText.split("\n\r\n")

    val crateStacks = parseCrates(cratesString)
    val instructions = parseInstructions(instructionsString)

    crateMover9001(instructions, crateStacks)

    crateStacks.forEach {
        print(it.pop())
    }
}

fun crateMover9001(instructions: List<Instruction>, crateStacks: MutableList<Stack<Char>>) {
    for (instruction in instructions) {
        val from = crateStacks[instruction.from - 1]
        val to = crateStacks[instruction.to - 1]

        val temp = Stack<Char>()
        repeat(instruction.amount) {
            val pop = from.pop()
            temp.push(pop)
        }

        while (temp.iterator().hasNext()) {
            val pop = temp.pop()
            to.push(pop)
        }
    }
}

private fun crateMover9000(
    instructions: List<Instruction>,
    crateStacks: MutableList<Stack<Char>>
) {
    for (instruction in instructions) {

        val from = instruction.from
        val to = instruction.to
        repeat(instruction.amount) {

            val pop = crateStacks[from - 1].pop()
            crateStacks[to - 1].push(pop)
        }
    }
}

fun parseInstructions(instructions: String): List<Instruction> {
    return instructions.lines().map { input ->
        val regex = "move (\\d+) from (\\d+) to (\\d+)".toRegex()
        val (amount, from, to) = regex.findAll(input)
            .flatMap { it.groupValues }
            .drop(1)
            .map { it.toInt() }
            .toList()

        Instruction(from, to, amount)
    }
}

fun parseCrates(input: String): MutableList<Stack<Char>> {
    val lines = input.lines().reversed()

    val mutableListOf = mutableListOf<Stack<Char>>()

    val count = lines[1].count { c: Char -> !c.isWhitespace() }

    repeat(count) {
        mutableListOf.add(Stack())
    }

    for (i in 2 until lines.size) {
        val line = lines[i]
        var j = 1;
        var k = 0
        while (j < line.length) {
            val c = line[j]
            if (!c.isWhitespace()) {
                mutableListOf[k].push(c)
            }
            k++
            j += 4
        }
    }

    return mutableListOf
}