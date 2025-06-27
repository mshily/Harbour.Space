package com.vina_esima.final_project.UnitTests

import com.vina_esima.final_project.converters._fromDate
import com.vina_esima.final_project.converters._fromDateDay
import com.vina_esima.final_project.converters._toDate
import com.vina_esima.final_project.converters._toDateDay
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.MockitoAnnotations

class UnitTests {
    @Test
    fun `test date conversion functions`() {
        val dateStr = "2021:02:01:15:30:45"
        val dateObj = _toDate(dateStr)

        assertEquals(2021, dateObj.year)
        assertEquals(2, dateObj.month)
        assertEquals(1, dateObj.day)
        assertEquals(15, dateObj.hour)
        assertEquals(30, dateObj.minute)
        assertEquals(45, dateObj.second)

        val convertedBack = _fromDate(dateObj)
        assertEquals(dateStr, convertedBack)
    }

    @Test
    fun `test dateDay conversion functions`() {
        val dateDayStr = "2021:01:01"
        val dateDayObj = _toDateDay(dateDayStr)

        assertEquals(2021, dateDayObj.year)
        assertEquals(1, dateDayObj.month)
        assertEquals(1, dateDayObj.day)

        val convertedBack = _fromDateDay(dateDayObj)
        assertEquals(dateDayStr, convertedBack)
    }


    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}