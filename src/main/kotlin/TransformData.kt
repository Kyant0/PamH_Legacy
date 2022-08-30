import kotlin.math.PI

data class TransformData(
    val a: Double = 1.0,
    val b: Double = 0.0,
    val c: Double = 0.0,
    val d: Double = 1.0,
    val rotate: Double = 0.0,
    val x: Double = 0.0,
    val y: Double = 0.0
) {
    private val rotationRad = rotate * 2000 * PI

    infix fun then(other: TransformData): TransformData {
        return TransformData(
            a = this.a * other.a + this.b * other.c,
            b = this.a * other.b + this.b * other.d,
            c = this.c * other.a + this.d * other.c,
            d = this.c * other.b + this.d * other.d,
            rotate = this.rotate + other.rotate,
            x = this.x + other.x,
            y = this.y + other.y
        )
    }

    fun toCssTransformString(): String {
        return "transform: matrix($a, $b, $c, $d, $x, $y) rotate(${rotationRad}rad);"
    }

    companion object {
        fun parse(data: List<Double>): TransformData {
            return when (data.size) {
                6 -> TransformData(
                    a = data[0],
                    b = data[1],
                    c = data[2],
                    d = data[3],
                    x = data[4],
                    y = data[5]
                )

                3 -> TransformData(
                    rotate = data[0],
                    x = data[1],
                    y = data[2]
                )

                2 -> TransformData(
                    x = data[0],
                    y = data[1]
                )

                else -> error("Invalid transform size")
            }
        }
    }
}
