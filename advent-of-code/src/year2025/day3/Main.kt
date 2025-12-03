package year2025.day3

import kotlin.time.measureTime

fun main() {
    val filePath = "src/year2025/day3/input/puzzleInput.txt"

    var answerPartOne: Long = 0
    val timePartOne = measureTime {
        val joltageCalculator = JoltageCalculator()
        answerPartOne = joltageCalculator.findJoltage(filePath, 2)
    }
    println("partOne: answer $answerPartOne, time $timePartOne")

    var answerPartTwo: Long = 0
    val timePartTwo = measureTime {
        val joltageCalculator = JoltageCalculator()
        answerPartTwo = joltageCalculator.findJoltage(filePath, 12)
    }
    println("partTwo: answer $answerPartTwo, time $timePartTwo")
}