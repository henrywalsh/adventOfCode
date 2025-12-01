package year2025.day1

import java.io.File
import java.io.InputStream
import kotlin.math.absoluteValue

class SecretEntranceSolver {
    fun solvePartOne(filePath: String): Int {
        val inputs = readInput(filePath)

        return getPartOneAnswer(inputs)
    }

    private fun readInput(filePath: String): MutableList<String> {
        val inputStream: InputStream = File(filePath).inputStream()
        val inputs = mutableListOf<String>()

        inputStream.bufferedReader().forEachLine {
            inputs.add(it)
        }

        return inputs
    }

    private fun getPartOneAnswer(inputs: List<String>): Int {
        var answer = 0
        var location = 50
        for (input in inputs) {
            val move = parseInput(input)

            location = getNewLocation(location, move)

            if (location == 0) {
                answer++
            }
        }

        return answer
    }

    private fun parseInput(input: String): Int {
        val direction = input[0]
        var move = input.substring(1).toInt()

        if (direction == 'L') {
            move *= -1
        }

        return move
    }

    private fun getNewLocation(location: Int, move: Int): Int {
        var newLocation = location + (move % 100)

        // adjust range to be within 0 -> 99
        if (newLocation < 0) {
            newLocation += 100
        } else if (newLocation > 99) {
            newLocation -= 100
        }

        return newLocation
    }

    fun solvePartTwo(filePath: String): Int {
        val inputs = readInput(filePath)

        return getPartTwoAnswer(inputs)
    }

    private fun getPartTwoAnswer(inputs: List<String>): Int {
        var answer = 0
        var location = 50
        for (input in inputs) {
            val move = parseInput(input)

            // every full turn we increment the counter
            answer += move.absoluteValue / 100

            val oldLocation = location
            location = getNewLocation(location, move)

            // if we were already on 0, don't count as moving past 0
            if (oldLocation == 0) {
                continue
            }

            if (location == 0) { // if we landed on 0, increment counter
                answer++
            } else if (move < 0 && location > oldLocation) { // if we moved left and passed zero, increment answer
                answer++
            } else if (move > 0 && location < oldLocation) { // if we moved right and passed zero, increment answer
                answer++
            }
        }

        return answer
    }
}

