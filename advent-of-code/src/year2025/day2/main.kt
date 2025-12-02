package year2025.day2

import kotlin.time.measureTime

fun main() {
    val filePath = "src/year2025/day2/puzzleInput.txt"
    val invalidProductFinderPartOne = InvalidProductFinderPartOne()
    val invalidProductFinderPartTwo = InvalidProductFinderPartTwo()


    var answerPartOne: Long = 0
    val timePartOne = measureTime {
        answerPartOne = invalidProductFinderPartOne.findSumOfInvalidIds(filePath)
    }
    println(String.format("Part One: answer %d, time %s", answerPartOne, timePartOne.toString()))

    var answerPartTwo: Long = 0
    val timePartTwo = measureTime {
        answerPartTwo = invalidProductFinderPartTwo.findSumOfInvalidIds(filePath)
    }
    println(String.format("Part Two: answer %d, time %s", answerPartTwo, timePartTwo.toString()))
}