data class Monkey(var inspectCount: Int, val items: MutableList<Int>, val inspect: (Int) -> Int, val test: (i: Int) -> Int)

fun main() {
    fun parseInitialItems(line: String) =
        line.substringAfterLast(":").trim().split(", ").map { it.toInt() }.toCollection(
            ArrayList()
        )

    fun parseOperation(line: String): (Int) -> Int {
        val regex = "Operation: new = old (.+) (.+)".toRegex()
        val findAll = regex.findAll(line)
            .flatMap { it.groupValues }
            .toList()

        val operator: Int.(Int) -> Int = when(findAll[1]) {
            "*" -> Int::times
            "+" -> Int::plus
            else -> throw IllegalArgumentException()
        }

        val operand = when(findAll[2]) {
            "old" -> { i: Int -> i }
            else -> { _: Int -> findAll[2].toInt()}
        }

        return { i: Int ->
            val op = operand(i)

            operator(i, op)
        }
    }

    fun parseTest(test: String, trueOutcome: String, falseOutcome: String): (i: Int) -> Int {
        val divisor = test.substringAfterLast(" ").toInt()

        val trueOutcomeMonkey = trueOutcome.substringAfterLast(" ").toInt()
        val falseOutcomeMonkey = falseOutcome.substringAfterLast(" ").toInt()

        return {i: Int ->
            if (i % divisor == 0)
                trueOutcomeMonkey
            else
                falseOutcomeMonkey
        }
    }

    fun parseMonkey(input: String): Monkey {
        val lines = input.lines()

        // 1: Initial items
        // 2: Operation
        // 3: Test
        // 4: True outcome
        // 5: False outcome

        val initialItems = parseInitialItems(lines[1])
        val operation = parseOperation(lines[2])
        val test = parseTest(lines[3], lines[4], lines[5])

        return Monkey(0, initialItems,  operation, test)
    }

    val input = fileFromResource("day11/input.txt").readText()

    val monkeys = input.split("\n\r\n").map(::parseMonkey)

    repeat(20) {
        for (monkey in monkeys) {
            while (monkey.items.isNotEmpty()) {
                val item = monkey.items.removeFirst()
                monkey.inspectCount += 1
                val inspectedItem = monkey.inspect(item)
                val boredItem = inspectedItem / 3
                val monkeyIndex = monkey.test(boredItem)
                monkeys[monkeyIndex].items.add(boredItem)
            }
        }
    }

    for ((index, monkey) in monkeys.withIndex()) {
        println("Monkey $index inspected items ${monkey.inspectCount}")
    }

    val monkeyBusinessLevel =
        monkeys.sortedByDescending { it.inspectCount }
            .take(2)
            .map { it.inspectCount }
            .reduce { acc, i -> acc * i }

    println(monkeyBusinessLevel)
}