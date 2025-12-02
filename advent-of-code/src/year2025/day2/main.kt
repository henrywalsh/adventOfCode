package year2025.day2

fun main() {
    val filePath = "src/year2025/day2/puzzleInput.txt"

    val invalidProductFinderPartOne = InvalidProductFinderPartOne()
    val invalidProductFinderPartTwo = InvalidProductFinderPartTwo()

    println(invalidProductFinderPartOne.findSumOfInvalidIds(filePath))
    println(invalidProductFinderPartTwo.findSumOfInvalidIds(filePath))
}