import DeviceInstruction.*

enum class DeviceInstruction(val cycleCount: Int) {
    NOOP(1) {
        override fun execute(register: Int, arg: Int?) = register
    },
    ADDX(2) {
        override fun execute(register: Int, arg: Int?) = register + arg!!
    };

    abstract fun execute(register: Int, arg: Int?): Int
}

data class ParsedInstruction(val instruction: DeviceInstruction, val arg: Int?)

fun main() {
    fun parseInstruction(input: String): ParsedInstruction {
        val split = input.split(" ")
        val instructionName = split[0]
        val arg = split.getOrNull(1)?.toInt()

        val deviceInstruction = when (instructionName) {
            "noop" -> NOOP
            "addx" -> ADDX
            else -> throw IllegalStateException()
        }

        return ParsedInstruction(deviceInstruction, arg)
    }

    fun problem1(path: String) {
        var register = 1
        var cycleCount = 0
        var sampledSignalStrength = 0

        fileFromResource(path).forEachLine { line ->
            val parsedInstruction = parseInstruction(line)

            repeat(parsedInstruction.instruction.cycleCount) {
                cycleCount += 1

                // Sample signal strength
                if (cycleCount == 20 || (cycleCount - 20) % 40 == 0) {
                    val currentSignalStrength = cycleCount * register
                    println("[$cycleCount] Sampling strength. Register $register, signal strength $currentSignalStrength")
                    sampledSignalStrength += currentSignalStrength
                }
            }

            register = parsedInstruction.instruction.execute(register, parsedInstruction.arg)
        }

        println("$register $cycleCount $sampledSignalStrength")
    }

    fun problem2(path: String) {
        var spriteMiddlePosition = 1
        var cycleCount = 0

        fileFromResource(path).forEachLine { line ->
            val parsedInstruction = parseInstruction(line)

            repeat(parsedInstruction.instruction.cycleCount) {
                val charToPrint = if (cycleCount % 40 in spriteMiddlePosition - 1..spriteMiddlePosition + 1) {
                    "#"
                } else {
                    "."
                }

                print(charToPrint)

                cycleCount += 1
                if (cycleCount % 40 == 0) {
                    print("\n")
                }
            }

            spriteMiddlePosition = parsedInstruction.instruction.execute(spriteMiddlePosition, parsedInstruction.arg)
        }
    }

    problem2("day10/input.txt")
}