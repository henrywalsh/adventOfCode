package year2025.day2

import java.io.File
import java.io.InputStream
import kotlin.math.log10
import kotlin.math.pow

class InvalidProductFinderPartOne {
    fun findSumOfInvalidIds(filePath: String): Long {
        val ranges = readInput(filePath)

        return sumInvalidRanges(ranges)
    }

    private fun readInput(filePath: String): List<Range> {
        val inputStream: InputStream = File(filePath).inputStream()
        val ranges = mutableListOf<Range>()


        inputStream.bufferedReader().forEachLine {
            val values = it.split(",")

            for (value in values) {
                val floor = value.substringBefore("-").toLong()
                val ceiling = value.substringAfter("-").toLong()

                ranges.add(Range(floor, ceiling))
            }
        }

        return ranges
    }

    private fun sumInvalidRanges(ranges: List<Range>): Long {
        var sum: Long = 0

        for (range in ranges) {
            sum += sumInvalidInRange(range)
        }

        return sum
    }

    private fun sumInvalidInRange(range: Range): Long {
        var sum: Long = 0
        for (i in range.floor..range.ceiling) {
            val digits = getNumDigits(i)

            // skip if digits not even
            if (digits % 2 != 0) {
                continue
            }

            val split = getSplit(digits)

            // get both sides
            val left = (i / split).toInt()
            val right = (i % split).toInt()

            // if two are equal add to sum
            if (left == right) {
                sum += i
            }
        }

        return sum
    }

    private fun getNumDigits(num: Long): Int {
        return log10(num.toDouble()).toInt() + 1
    }

    private fun getSplit(digits: Int): Double {
        return 10.0.pow(digits / 2)
    }
}