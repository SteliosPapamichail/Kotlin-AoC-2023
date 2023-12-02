import kotlin.math.max

fun main() {
    fun part1(input: List<String>): Int {
        val RED_CUBE_LIMIT = 12
        val BLUE_CUBE_LIMIT = 14
        val GREEN_CUBE_LIMIT = 13
        val greenCubesMap = mutableMapOf<String, Int>()
        val blueCubesMap = mutableMapOf<String, Int>()
        val redCubesMap = mutableMapOf<String, Int>()
        val gamesPossibilities = mutableMapOf<String, Boolean>()
        input.forEach {
            val gameInfo = it.split(":")
            val gameId = gameInfo[0].substringAfter("Game ")
            // map init
            greenCubesMap[gameId] = 0
            blueCubesMap[gameId] = 0
            redCubesMap[gameId] = 0

            val rounds = gameInfo[1].split(";")
            // check constraints and game validity
            var isGameValid = true
            rounds.forEach { round ->
                val cubesDrawn = round.split(",")
                cubesDrawn.forEach { drawnCubeSegment ->
                    if (drawnCubeSegment.contains("green")) {
                        val greenCubesDrawn = drawnCubeSegment.substringBefore("green").trim().toInt()
                        if (greenCubesDrawn > GREEN_CUBE_LIMIT) isGameValid = false
                    } else if (drawnCubeSegment.contains("blue")) {
                        val blueCubesDrawn = drawnCubeSegment.substringBefore("blue").trim().toInt()
                        if (blueCubesDrawn > BLUE_CUBE_LIMIT) isGameValid = false
                    } else if (drawnCubeSegment.contains("red")) {
                        val redCubesDrawn = drawnCubeSegment.substringBefore("red").trim().toInt()
                        if (redCubesDrawn > RED_CUBE_LIMIT) isGameValid = false
                    }
                }
            }
            gamesPossibilities[gameId] = isGameValid
        }
        return gamesPossibilities.filter { it.value }.keys.sumOf { it.toInt() }
    }

    fun part2(input: List<String>): Int {
        // for each draw phase of each round, store the max number of each color for each game
        // order of cube colors in triple : [red, green, blue]
        val gameWithMaxNumOfCubes = mutableMapOf<String, Triple<Int, Int, Int>>()
        input.forEach {
            val gameInfo = it.split(":")
            val gameId = gameInfo[0].substringAfter("Game ")
            gameWithMaxNumOfCubes[gameId] = Triple(0, 0, 0)
            val rounds = gameInfo[1].split(";")

            rounds.forEach { round ->
                val cubesDrawn = round.split(",")
                cubesDrawn.forEach { drawnCubeSegment ->
                    if (drawnCubeSegment.contains("green")) {
                        val greenCubesDrawn = drawnCubeSegment.substringBefore("green").trim().toInt()
                        gameWithMaxNumOfCubes[gameId] = gameWithMaxNumOfCubes[gameId]!!.copy(
                            second = max(
                                greenCubesDrawn,
                                gameWithMaxNumOfCubes[gameId]!!.second
                            )
                        )
                    } else if (drawnCubeSegment.contains("blue")) {
                        val blueCubesDrawn = drawnCubeSegment.substringBefore("blue").trim().toInt()
                        gameWithMaxNumOfCubes[gameId] = gameWithMaxNumOfCubes[gameId]!!.copy(
                            third = max(
                                blueCubesDrawn,
                                gameWithMaxNumOfCubes[gameId]!!.third
                            )
                        )
                    } else if (drawnCubeSegment.contains("red")) {
                        val redCubesDrawn = drawnCubeSegment.substringBefore("red").trim().toInt()
                        gameWithMaxNumOfCubes[gameId] = gameWithMaxNumOfCubes[gameId]!!.copy(
                            first = max(
                                redCubesDrawn,
                                gameWithMaxNumOfCubes[gameId]!!.first
                            )
                        )
                    }
                }
            }
        }

        return gameWithMaxNumOfCubes.filter {
            !it.value.isAnyValueEmpty()
        }.values.sumOf { getCubeSetPower(it) }
    }

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}

fun Triple<Int, Int, Int>.isAnyValueEmpty() = first == 0 || second == 0 || third == 0
fun getCubeSetPower(rgbCubes: Triple<Int, Int, Int>) = rgbCubes.first * rgbCubes.second * rgbCubes.third
