import org.junit.jupiter.api.Assertions.*

internal class Main_Test_Junit5 {
    @org.junit.jupiter.api.Test
    fun getMax() {
        assertEquals(3, getMax(1, 3))
        assertEquals(2, getMax(1, 2))

    }

}