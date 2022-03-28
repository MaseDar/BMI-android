package com.example.bmimolotkov

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun LocalDateTime.dateFormat(): String =
    DateTimeFormatter.ofPattern("dd.MM.yyyy").format(this)

fun LocalDateTime.dateTimeFormat(): String =
    DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm").format(this)
