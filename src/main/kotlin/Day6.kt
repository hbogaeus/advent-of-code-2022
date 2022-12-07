import java.util.Optional

const val START_OF_PACKET_LENGTH = 4
const val START_OF_MESSAGE_LENGTH = 14

val EXAMPLES = listOf(
    "bvwbjplbgvbhsrlpgdmjqwftvncz",         // 5
    "nppdvjthqldpwncqszvftbrmjlhg",         // 6
    "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg",    // 10
    "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"      // 11
)

fun main() {
    for (example in EXAMPLES) {
        val packetMarker =
            findMarker(
                example,
                START_OF_MESSAGE_LENGTH
            ).orElseThrow { IllegalStateException("No start-of-packet marker found") }
        println(packetMarker)
    }

    val input = fileFromResource("day6/input.txt").readText()
    val packetMarker =
        findMarker(
            input,
            START_OF_MESSAGE_LENGTH
        ).orElseThrow { IllegalStateException("No start-of-packet marker found") }
    println(packetMarker)
}

private fun findMarker(input: String, markerLength: Int): Optional<Int> {
    for (i in markerLength..input.length) {
        val set = input.substring(i - markerLength, i).toSet()

        if (set.size == markerLength) {
            return Optional.of(i)
        }
    }
    return Optional.empty()
}