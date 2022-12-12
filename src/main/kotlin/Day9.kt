import Direction.*
import kotlin.math.abs

data class Point(val x: Int, val y: Int) {
    operator fun plus(point: Point): Point {
        return Point(x + point.x, y + point.y)
    }

    operator fun minus(point: Point): Point {
        return Point(x - point.x, y - point.y)
    }
}

enum class Direction {
    NOOP {
        override fun move() = Point(0, 0)
    },
    UP {
        override fun move() = Point(0, 1)
    },
    LEFT {
        override fun move() = Point(-1 ,0)
    },
    DOWN {
        override fun move() = Point(0, -1)
    },
    RIGHT {
        override fun move() = Point(1, 0)
    };

    abstract fun move(): Point
}

fun main() {
    var head = Point(0, 0)
    var tail = Point(0, 0)

    var visitedLocations = mutableSetOf(tail)

    fileFromResource("day9/input.txt").forEachLine { line ->
        val (direction, amount) = parseInstruction(line)

        repeat(amount) {
            // Move head
            head += direction.move()

            // Update tail
            val tailDirection = calculateTailMove(head, tail)
            tail += tailDirection
            visitedLocations.add(tail)
        }
    }

    println(visitedLocations.size)
}

private fun calculateTailMove(head: Point, tail: Point): Point {
    val (x, y) = head - tail

    // If touching, don't move
    if (abs(x) < 2 && abs(y) < 2) {
        return NOOP.move()
    }

    // x > 0 -> head is to the right of tail
    // x < 0 -> head is to the left of tail
    // y > 0 -> head is above tail
    // y < 0 -> head is below tail

    val horizontalDirection = if (x > 0) {
        RIGHT
    } else if (x < 0) {
        LEFT
    } else {
        NOOP
    }

    val verticalDirection = if (y > 0) {
        UP
    } else if (y < 0) {
        DOWN
    } else {
        NOOP
    }

    return horizontalDirection.move() + verticalDirection.move()
}

private fun parseInstruction(line: String): Pair<Direction, Int> {
    val parts = line.split(" ")

    val direction = when (parts[0]) {
        "U" -> UP
        "D" -> DOWN
        "L" -> LEFT
        "R" -> RIGHT
        else -> throw IllegalStateException()
    }
    val amount = parts[1].toInt()

    return Pair(direction, amount)
}

