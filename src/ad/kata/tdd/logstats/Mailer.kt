package ad.kata.tdd.logstats

import co.touchlab.kermit.Logger
import java.io.File
import java.util.*

@Deprecated("this is an ugly implementation on purpose")
class Mailer(configFile: String) {

    private val connection by lazy { establishConnection(configFile) }

    private fun establishConnection(configFile: String): Connection {
        val props = Properties()
        File(configFile).inputStream().use { props.load(it) }
        val host = props.getProperty("smtpHost")
        val port = props.getProperty("smtpPort").toInt()

        return Connection(host, port)
    }

    fun send(to: String, message: String) {
        // this is just a dummy
        Logger.setTag("mailer ${connection.host}:${connection.port}")
        Logger.i {
            "send email to: \"%s\"\n\n%s".format(
                to,
                message
            )
        }
    }

    private class Connection(val host: String, val port: Int)
}