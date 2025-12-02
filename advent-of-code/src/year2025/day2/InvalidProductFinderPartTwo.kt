package year2025.day2

import java.io.File
import java.io.InputStream
import kotlin.math.log10

class InvalidProductFinderPartTwo {
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
            if (isRepeated(i)) {
                sum += i
            }
        }

        return sum
    }

    private fun isRepeated(num: Long): Boolean {
        var chunkSize = getNumDigits(num) / 2
        if (chunkSize == 0) {
            return false
        }

        val current = num.toString()
        while (chunkSize > 0) {
            if (isRepeatedChunks(current, chunkSize)) {
                return true
            }

            chunkSize--
        }

        return false
    }

    private fun getNumDigits(num: Long): Int {
        return log10(num.toDouble()).toInt() + 1
    }

    private fun isRepeatedChunks(current: String, chunkSize: Int): Boolean {
        val chunked = current.chunked(chunkSize)
        if (chunked.size <= 1) {
            return false
        }

        for (chunk in chunked) {
            if (chunk != chunked[0]) {
                return false
            }
        }

        return true
    }
}