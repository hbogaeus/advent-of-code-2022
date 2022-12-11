class Directory(val name: String, val children: MutableList<Directory>, val parent: Directory?, var size: Int = 0) {
    companion object {
        fun create(name: String, parent: Directory?): Directory = Directory(name, mutableListOf(), parent)
    }
}

fun main() {
    val directories = mutableMapOf<String, Directory>();
    val root = Directory.create("/", null)

    var currentDir = root

    directories["/"] = root

    fileFromResource("day7/input.txt").forEachLine { line ->
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

    val conditionalSum = conditionalSum(root)
    println(conditionalSum)
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