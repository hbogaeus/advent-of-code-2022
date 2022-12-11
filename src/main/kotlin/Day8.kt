data class Tree(val height: Int)

fun main() {
    val input = fileFromResource("day8/input.txt").readLines()

    val grid = parseTrees(input)

    val gridHeight = grid.size
    val gridWidth = grid[0].size

    var visibleTrees = gridHeight * 2 + gridWidth * 2 - 4

    for (y in 1 until gridHeight - 1) {
        for (x in 1 until gridWidth - 1) {
            val currentTree = grid[y][x]
            println("[$y][$x]")

            var isVisibleFromLeft = true

            // Check left
            for (z in x - 1 downTo 0) {
                val tree = grid[y][z]

                if (tree.height >= currentTree.height) {
                    isVisibleFromLeft = false
                    break
                }
            }

            var isVisibleFromRight = true

            // Check right
            for (z in x + 1 until  gridWidth) {
                val tree = grid[y][z]

                if (tree.height >= currentTree.height) {
                    isVisibleFromRight = false
                    break
                }
            }

            var isVisibleFromUp = true

            // Check up
            for (z in y - 1 downTo 0) {
                val tree = grid[z][x]

                if (tree.height >= currentTree.height) {
                    isVisibleFromUp = false
                    break
                }
            }

            var isVisibleFromDown = true

            // Check down
            for (z in y + 1 until  gridHeight) {
                val tree = grid[z][x]

                if (tree.height >= currentTree.height) {
                    isVisibleFromDown = false
                    break
                }
            }

            if (isVisibleFromLeft || isVisibleFromRight || isVisibleFromUp || isVisibleFromDown) {
                visibleTrees += 1
            }

        }
    }

    println(visibleTrees)
}

fun parseTrees(input: List<String>): List<List<Tree>> {
    val height = input.size
    val width = input[0].length

    val grid = mutableListOf<List<Tree>>()

    for (row in input) {
        val toList = row.map { it.digitToInt() }.map { Tree(it) }.toList()
        grid.add(toList)
    }

    return grid
}
