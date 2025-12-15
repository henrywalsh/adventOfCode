package year2025.day8

import java.io.File
import java.io.InputStream
import java.util.PriorityQueue
import java.util.Stack
import kotlin.collections.iterator
import kotlin.collections.set
import kotlin.math.pow
import kotlin.math.sqrt

class JunctionBoxConnector {
    fun partOne(filePath: String, numConnections: Int, numCircuits: Int): Int {
        // get boxes
        val junctionBoxes = readInput(filePath)

        // get shortest X connections
        val shortestConnections = getShortestConnections(junctionBoxes, numConnections)

        // get map of shortest connections
        val connections = createMapFromConnections(shortestConnections)

        // dfs the map to get circuit sizes
        val circuitSizes = getOrderedCircuitSizesWithDfs(connections)

        // multiple first Y items
        return multiplySizes(circuitSizes, numCircuits)
    }

    private fun readInput(filePath: String): List<JunctionBox> {
        val junctionBoxes = mutableListOf<JunctionBox>()

        val inputStream: InputStream = File(filePath).inputStream()
        inputStream.bufferedReader().forEachLine {
            val coordinates = it.split(",")

            junctionBoxes.add(
                JunctionBox(
                    coordinates[0].toDouble(),
                    coordinates[1].toDouble(),
                    coordinates[2].toDouble()
                )
            )
        }

        return junctionBoxes
    }

    private fun getShortestConnections(junctionBoxes: List<JunctionBox>, numConnections: Int): List<Connection> {
        val shortestConnections = PriorityQueue<Connection>{ c1, c2 ->
            c2.distance.compareTo(c1.distance)
        }

        for (i in 0..<junctionBoxes.size) {
            for (j in i+1..<junctionBoxes.size) {
                shortestConnections.add(
                    Connection(
                        i,
                        j,
                        getDistanceBetween(junctionBoxes[i], junctionBoxes[j])
                    )
                )

                if (shortestConnections.size > numConnections) {
                    shortestConnections.remove()
                }
            }
        }

        return shortestConnections.toList()
    }

    private fun getDistanceBetween(a: JunctionBox, b: JunctionBox): Double {
        return sqrt((b.x - a.x).pow(2) + (b.y - a.y).pow(2) + (b.z - a.z).pow(2))
    }

    private fun createMapFromConnections(shortestConnections: List<Connection>): Map<Int, Set<Int>> {
        val connections = mutableMapOf<Int, MutableSet<Int>>()

        for (shortestConnection in shortestConnections) {
            if (connections[shortestConnection.a] == null) {
                connections[shortestConnection.a] = mutableSetOf(shortestConnection.b)
            } else {
                connections[shortestConnection.a]!!.add(shortestConnection.b)
            }

            if (connections[shortestConnection.b] == null) {
                connections[shortestConnection.b] = mutableSetOf(shortestConnection.a)
            } else {
                connections[shortestConnection.b]!!.add(shortestConnection.a)
            }
        }

        return connections
    }

    private fun getOrderedCircuitSizesWithDfs(connections: Map<Int, Set<Int>>): List<Int> {
        val circuitSizes = mutableListOf<Int>()
        val seen = mutableSetOf<Int>()

        for (connection in connections) {
            if (seen.contains(connection.key)) {
                continue
            }

            seen.add(connection.key)
            var circuitSize = 1

            val next = Stack<Int>()
            next.addAll(connection.value)

            while (next.isNotEmpty()) {
                val current = next.pop()

                if (seen.contains(current)) {
                    continue
                }

                seen.add(current)
                circuitSize++

                next.addAll(connections[current]!!)
            }

            circuitSizes.add(circuitSize)
        }

        return circuitSizes.sortedDescending()
    }

    private fun multiplySizes(orderedCircuits: List<Int>, limit: Int): Int {
        var answer = 1

        for (i in 0..<limit) {
            if (i >= orderedCircuits.size) {
                return answer
            }

            answer *= orderedCircuits[i]
        }

        return answer
    }

    fun partTwo(filePath: String): Long {
        val junctionBoxes = readInput(filePath)
        val allConnections = getAllConnections(junctionBoxes)

        val map = mutableMapOf<Int, MutableSet<Int>>()

        for (connection in allConnections) {
            if (map[connection.a] == null) {
                map[connection.a] = mutableSetOf(connection.b)
            } else {
                map[connection.a]!!.add(connection.b)
            }

            if (map[connection.b] == null) {
                map[connection.b] = mutableSetOf(connection.a)
            } else {
                map[connection.b]!!.add(connection.a)
            }

            val size = getMapSize(map)
            if (size == junctionBoxes.size) {
                return (junctionBoxes[connection.a].x * junctionBoxes[connection.b].x).toLong()
            }
        }

        return 0
    }

    private fun getAllConnections(junctionBoxes: List<JunctionBox>): List<Connection> {
        val allConnections = mutableListOf<Connection>()

        for (i in 0..<junctionBoxes.size) {
            for (j in i+1..<junctionBoxes.size) {
                allConnections.add(
                    Connection(
                        i,
                        j,
                        getDistanceBetween(junctionBoxes[i], junctionBoxes[j])
                    )
                )
            }
        }

        return allConnections.sortedBy { it.distance }
    }

    private fun getMapSize(map: Map<Int, Set<Int>>): Int {
        if (map[0] == null) {
            return 0
        }

        val seen = mutableSetOf(0)

        val next = Stack<Int>()
        next.addAll(map[0]!!)

        var size = 1
        while (next.isNotEmpty()) {
            val current = next.pop()

            if (seen.contains(current)) {
                continue
            }

            seen.add(current)
            size++

            next.addAll(map[current]!!)
        }

        return size
    }
}