package year2025.day1

fun main() {
    val secretEntranceSolver = SecretEntranceSolver()
    val filePath = "src/year2025/day1/puzzleInput.txt"

    println(secretEntranceSolver.solvePartOne(filePath))
    println(secretEntranceSolver.solvePartTwo(filePath))
}