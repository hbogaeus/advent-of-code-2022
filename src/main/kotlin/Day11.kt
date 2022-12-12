data class Monkey(var inspectCount: Int, val items: MutableList<Int>, val inspect: (Int) -> Int, val divisor: Int, val trueMonkeyIndex: Int, val falseMonkeyIndex: Int)

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

    fun parse(trueOutcome: String) = trueOutcome.substringAfterLast(" ").toInt()

    fun parseTest(test: String, trueOutcome: String, falseOutcome: String): (i: Int) -> Int {

        val trueOutcomeMonkey = i(trueOutcome)
        val falseOutcomeMonkey = i(falseOutcome)
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
        val divisor = parse(lines[3])
        val trueMonkeyIndex = parse(lines[4])
        val falseMonkeyIndex = parse(lines[5])

        return Monkey(0, initialItems,  operation, divisor, trueMonkeyIndex, falseMonkeyIndex )
    }

    fun problem2(filename: String) {
        val input = fileFromResource(filename).readText()

        val monkeys = input.split("\n\r\n").map(::parseMonkey)

        repeat(20) {
            for (monkey in monkeys) {
                while (monkey.items.isNotEmpty()) {
                    val item = monkey.items.removeFirst()
                    monkey.inspectCount += 1
                    val inspectedItem = monkey.inspect(item)

                    val monkeyIndex =
                        if (inspectedItem % monkey.divisor == 0) {
                            monkey.trueMonkeyIndex
                        } else {
                            monkey.falseMonkeyIndex
                        }

                    // Make inspectedItem smaller here, but still react the same to monkey that the item is thrown to

                    monkeys[monkeyIndex].items.add(inspectedItem)
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

    problem2("day11/example.txt")
}