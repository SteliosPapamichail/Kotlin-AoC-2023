fun main() {
    fun part1(input: List<String>): Int {
        val calibrationVals = input.map {
            val firstDigit = it.first { chr ->
                chr.isDigit()
            }
            val lastDigit = it.last { chr ->
                chr.isDigit()
            }
            "$firstDigit$lastDigit".toInt()
        }
        //calibrationVals.println()
        return calibrationVals.sum()
    }

    fun part2(input: List<String>): Int {
        // using a format that allows for overlapping matches
        // i.e. eighthree should be replaced with 83.
        // using this map we would get: "eightthree" -> "8three" -> "83"
        val textToDigitMap = mapOf(
                "one" to "1ne",
                "two" to "2o",
                "three" to "3e",
                "four" to "4",
                "five" to "5e",
                "six" to "6",
                "seven" to "7n",
                "eight" to "8t",
                "nine" to "9e"
        )

        val decodedList = input.map { line ->
            var updatedLine = line

            // check if for-each pair, the key is contained within the line
            // if so, map the key to the index using indexOf
            // sort based on the index and replace
            val indexToReplacementMap = mutableMapOf<Int, String>()
            for (key in textToDigitMap.keys) {
                if (key in updatedLine) {
                    indexToReplacementMap[updatedLine.indexOf(key)] = key
                }
            }
            for (index in indexToReplacementMap.keys.sorted()) {
                // ensure key is still present in the string after potential
                // previous replacements
                if (indexToReplacementMap[index]!! in updatedLine) {
                    updatedLine = updatedLine.replace(indexToReplacementMap[index]!!, textToDigitMap[indexToReplacementMap[index]!!].toString())
                }
            }

            updatedLine
        }

        //decodedList.println()
        return part1(decodedList)
    }

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
