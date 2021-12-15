# TDD Log-Stats Cron-Job Kata

![build](https://github.com/andrej-dyck/kata-tdd-log-stats/actions/workflows/gradle-ci.yml/badge.svg?branch=main)

## Task

A daily cron-job consumes yesterdays webservice logs from a log-file and send a plain-text email presenting some stats.

A success log entry has the following structure:
```
yyyy-MM-ddTHH:mm:ss INFO - /endpoint (took timeInMilliseconds ms)
```

A failure log entry has the following structure:
```
yyyy-MM-ddTHH:mm:ss ERROR - /endpoint (after timeInMilliseconds ms)
```

The stats should be presented something like this:
```
Stats for 15.12.2021
  /products avg: 56ms total: 217x failures: 12x
  /teaser avg: 94ms total: 501x failures: 19x
  /profile avg: 150ms total: 17x failures: 31x
```

## Generate a Lot of Logs (if you need it)

The branch `feature/generate-logs` comprises a script that writes a log file with ten-thousands of randomly generated log entries. A use for it could be performance tests. Execute the `main` and it will create the `resources/logs.log` file.

## Non-TDD Implementation

The branch `feature/non-tdd-cron-job` comprises an ugly implementation that is (on purpose) not testable. You could use this kata also as a refactoring training.

## Tool of Choice

This project is set up for [Kotlin (JVM)](https://kotlinlang.org/docs/home.html) with [JUnit 5](https://junit.org/junit5/docs/current/user-guide/) and [AssertJ](https://assertj.github.io/doc/). Further, there is a static analyzer [Detekt](https://detekt.github.io/detekt/) with some strict rules.

However, this kata is not bound to any programming language and tool; so chose your own toolset of your liking.

### Build with Gradle
```
./gradlew build
```

## Feedback

Last but not least, you can submit a pull request to this repository and ask me for feedback. 