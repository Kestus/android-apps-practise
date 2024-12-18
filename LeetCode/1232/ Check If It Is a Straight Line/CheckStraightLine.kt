package `1232`.` Check If It Is a Straight Line`


fun solution1(coordinates: Array<IntArray>): Boolean {
    if (coordinates.size == 2) return true
    for (i in 0 until coordinates.size-2) {
        val x = coordinates[i]
        val y = coordinates[i+1]
        val z = coordinates[i+2]

        val area = 0.5 * (
                x[0] * (y[1] - z[1]) +
                y[0] * (z[1] - x[1]) +
                z[0] * (x[1] - y[1])
                )

        if (area != 0.0) return false

    }
    return true
}

fun main() {

    val result1 = solution1(
        arrayOf(
            intArrayOf(1, 2),
            intArrayOf(2, 3),
            intArrayOf(3, 4),
            intArrayOf(4, 5),
            intArrayOf(5, 6),
            intArrayOf(6, 7),
        )
    )
    println(result1)

    val result2 = solution1(
        arrayOf(
            intArrayOf(1, 1),
            intArrayOf(2, 2),
            intArrayOf(3, 4),
            intArrayOf(4, 5),
            intArrayOf(5, 6),
            intArrayOf(7, 7),
        )
    )
    println(result2)

    val result3 = solution1(
        arrayOf(
            intArrayOf(1, 2),
            intArrayOf(2, 3),
            intArrayOf(3, 5),
        )
    )
    println("expected false: $result3") // expect false

    val result4 = solution1(
        arrayOf(
            intArrayOf(0, 0),
            intArrayOf(0, 1),
            intArrayOf(0, -1),
        )
    )
    println("expected true: $result4") // expect true

}