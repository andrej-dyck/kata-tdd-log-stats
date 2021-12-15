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

## Build with Gradle
```
./gradlew build
```