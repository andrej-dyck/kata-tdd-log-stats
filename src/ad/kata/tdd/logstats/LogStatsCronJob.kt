package ad.kata.tdd.logstats

import co.touchlab.kermit.Logger
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@Deprecated("this is an ugly implementation on purpose")
@Suppress(
    "LongMethod",
    "TooGenericExceptionCaught",
    "DestructuringDeclarationWithTooManyEntries",
    "Deprecation"
)
class LogStatsCronJob(
    private val logfile: String,
    private val configFile: String
) : CronJob {

    override fun execute() {
        Logger.setTag("log stats")
        Logger.i { "start cron job" }
        try {
            Mailer(configFile).send(
                to = admin(),
                message = formattedStats()
            )
            Logger.i { "email with log stats send" }
        } catch (e: Exception) {
            Logger.e(e) { "cron job failed" }
        }
    }

    private fun admin(): String {
        val props = Properties()
        File(configFile).inputStream().use { props.load(it) }
        return props.getProperty("adminEmail")
    }

    private fun formattedStats(): String {
        val builder = StringBuilder()
        builder.appendLine(
            "Stats for ${
                LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
            }"
        )

        val endpointsStats = yesterdaysLogs().mapNotNull {
            logEntryRegex.matchEntire(it)?.destructured
        }.map { (_, level, endpoint, duration) ->
            Triple((level == "INFO"), endpoint, duration.toInt())
        }.groupBy { (_, endpoint, _) ->
            endpoint
        }.mapValues {
            aggregateEndpointStats(it.value.map { (isSuccess, _, duration) -> isSuccess to duration })
        }

        endpointsStats.forEach {
            val (avg, count, failures) = it.value
            builder.appendLine(
                " %s avg: %.1fms total: %dx failures: %dx".format(
                    it.key,
                    avg,
                    count,
                    failures
                )
            )
        }

        return builder.toString()
    }

    private fun aggregateEndpointStats(logs: List<Pair<Boolean, Int>>) = Triple(
        logs.filter { it.first }.map { it.second }.average(),
        logs.size,
        logs.filterNot { it.first }.size
    )

    private fun yesterdaysLogs(): Sequence<String> {
        val yesterday = LocalDate.now().minusDays(1).format(DateTimeFormatter.ISO_DATE)
        return File(logfile).bufferedReader()
            .lineSequence()
            .filter { it.startsWith(yesterday) }
    }

    companion object {
        private val logEntryRegex =
            """([\d-T:]+) (INFO|ERROR) - (/\w+) \(\w+ (\d+) ms\)""".toRegex()
    }
}

