package com.fatalien.scannerapp.helpers

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun LocalDate.toEpochMilli() : Long{
    return this.atStartOfDay(ZoneId.of("Europe/Moscow")).toInstant().toEpochMilli();
}

fun LocalDate.toDateString() : String{
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    return this.format(formatter)
}

fun Long.toDateString() : String{
    val date = this.toLocalDate()
    return date.toDateString()
}
fun Long.toLocalDate() : LocalDate{
    return Instant.ofEpochMilli(this).atZone(ZoneId.of("Europe/Moscow")).toLocalDate();
}

fun String.toEpochMilli() : Long{
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    return LocalDate.parse(this, formatter).toEpochMilli()
}