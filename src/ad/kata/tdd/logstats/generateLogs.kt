package ad.workshop.tdd.example

import java.io.File
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Suppress("*")
fun main() {
    generateLogFile()
}

fun generateLogFile(
    filename: String = "resources/logs.log",
    now: LocalDateTime = LocalDateTime.now(),
    amountOfDays: Int = 3,
    amountOfLogsPerDay: IntRange = 7000..10000
) {
    val writer = File(filename).bufferedWriter()
    writer.write("")

    val today = now.toLocalDate()
    val days = (0 until amountOfDays).reversed().map {
        today.minusDays(it.toLong())
    }

    days.forEach { day ->
        generateSequence {
            day.withRandomTime()
        }.take(
            amountOfLogsPerDay.random()
        ).sorted().filter {
            it < now
        }.map { timestamp ->
            randomLogEntry(timestamp)
        }.forEach {
            writer.append(it)
            writer.newLine()
        }
    }

    writer.close()
}

fun randomLogEntry(timestamp: LocalDateTime): String {
    val logLevel = logLevels.increasedChanceOfInfo().random()
    val endpoint = exampleEndpoints.random()
    val ms = (17..254).random()

    return "%s %s - %s (%s %d ms)".format(
        timestamp.format(DateTimeFormatter.ISO_DATE_TIME),
        logLevel,
        endpoint,
        if (logLevel == "INFO") "after" else "took",
        ms
    )
}

fun LocalDate.withRandomTime(): LocalDateTime =
    atTime((0..23).random(), (0..59).random(), (0..59).random())

private fun Set<String>.increasedChanceOfInfo() =
    flatMap { l -> if (l == "INFO") List(9) { l } else listOf(l) }

val logLevels = setOf("INFO", "ERROR")
val exampleEndpoints = setOf("/products", "/teaser", "/profile")