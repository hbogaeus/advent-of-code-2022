class Directory(val name: String, val children: MutableList<Directory>, val parent: Directory?, var size: Int = 0) {
    companion object {
        fun create(name: String, parent: Directory?): Directory = Directory(name, mutableListOf(), parent)
    }
}

fun main() {
    val (root, directories) = parseDirectoryStructure("day7/input.txt")

    problem2(root, directories)

}

private const val TOTAL_SIZE = 70_000_000
private const val SIZE_OF_UPDATE = 30_000_000

private fun problem2(root: Directory, directories: Map<String, Directory>) {
    val usedSpace = localSum(root)
    val unusedSpace = TOTAL_SIZE - usedSpace
    val spaceToFreeUp = SIZE_OF_UPDATE - unusedSpace

    println("Unused space is $unusedSpace, required space to free up is $spaceToFreeUp")

    var min = Int.MAX_VALUE
    for (directory in directories.values) {
        val localSum = localSum(directory)
        println("${directory.name}: $localSum")

        if (localSum > spaceToFreeUp && localSum < min) {
            min = localSum
        }
    }
    println(min)
}

private fun problem1(root: Directory) {
    val conditionalSum = conditionalSum(root)
    println(conditionalSum)
}


private fun parseDirectoryStructure(input: String): Pair<Directory, Map<String, Directory>> {
    val directories = mutableMapOf<String, Directory>();
    val root = Directory.create("/", null)

    var currentDir = root

    directories["/"] = root

    fileFromResource(input).forEachLine { line ->
        val isCommand = line.startsWith("$")

        if (isCommand) {
            val command = line.substring(2, 4)

            if (command == "cd") {
                val dir = line.substringAfterLast(" ")

                currentDir = when (dir) {
                    "/" -> {
                        root
                    }

                    ".." -> {
                        currentDir.parent!!
                    }

                    else -> {
                        directories[dir]!!
                    }
                }
            }

            if (command == "ls") {
                // Do nothing
            }
        } else {
            // Is file
            if (line.startsWith("dir")) {
                val directoryName = line.substringAfterLast(" ")
                val newDirectory = Directory.create(directoryName, currentDir)

                directories[directoryName] = newDirectory
                currentDir.children.add(newDirectory)
            } else {
                val fileSize = line.split(" ")[0].toInt()
                currentDir.size += fileSize
            }
        }
    }
    return Pair(root, directories)
}

fun localSum(directory: Directory): Int {
    var sum = directory.size
    for (child in directory.children) {
        sum += localSum(child)
    }
    return sum
}

fun conditionalSum(directory: Directory): Int {
    var sum = 0
    val localSum = localSum(directory)
    if (localSum <= 100000) {
        sum += localSum
    }
    for (child in directory.children) {
        sum += conditionalSum(child)
    }
    return sum
}