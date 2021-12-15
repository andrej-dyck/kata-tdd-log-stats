package ad.kata.tdd.logstats

fun main() {
    LogStatsCronJob("resources/logs.log", "resources/config.properties").execute()
}