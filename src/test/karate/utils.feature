@ignore
Feature:

  Scenario:

    * def hello = function(){ return 'hello' }
    * def world = function(){ return 'world' }

    * def randomEmail = function(){ return org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(8) + '@delete.me' }
    * def randomPassword = function(){ return org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(12) }
    * def randomStripeToken = function(){ return org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(15) }
    * def randomAlphabetic = function(length){ return org.apache.commons.lang3.RandomStringUtils.randomAlphabetic(length) }
    * def randomAlphanumeric = function(length){ return org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(length) }
    * def randomNumeric = function(length){ return org.apache.commons.lang3.RandomStringUtils.randomNumeric(length) }

    * def sleep = function(pause){ java.lang.Thread.sleep(pause) }

    * def nowEpochSeconds =
      """
        function() {
          var Instant = Java.type('java.time.Instant');
          return Instant.now().getEpochSecond();
        }
      """
    * def today =
      """
        function() {
          var SimpleDateFormat = Java.type('java.text.SimpleDateFormat');
          var sdf = new SimpleDateFormat('yyyy-MM-dd');
          var date = new java.util.Date();
          return sdf.format(date);
        }
      """
    * def getYear =
      """
        function() {
          var SimpleDateFormat = Java.type('java.text.SimpleDateFormat');
          var sdf = new SimpleDateFormat('yyyy');
          var date = new java.util.Date();
          return sdf.format(date);
        }
      """
    * def stringToDate =
      """
        function(dateStr) {
          var SimpleDateFormat = Java.type('java.text.SimpleDateFormat')
          var sdf = new SimpleDateFormat('yyyy-MM-dd')
          return sdf.parse(dateStr).time // '.getTime()' would also have worked instead of '.time'
        }
      """
    * def dateWith =
      """
        function(year, month, day) {
          var LocalDate = Java.type('java.time.LocalDate')
          var newDate = LocalDate.now()
          if (year) newDate = newDate.withYear(year)
          if (month) newDate = newDate.withMonth(month)
          if (day) newDate = newDate.withDayOfMonth(day)

          var DateTimeFormatter = Java.type('java.time.format.DateTimeFormatter')
          var dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd")
          return dtf.format(newDate)
        }
      """
    * def dateOffset =
      """
        function(yearOffset, monthOffset, dayOffset, pattern) {
          var LocalDate = Java.type('java.time.LocalDate')
          var newDate = LocalDate.now()
          newDate = newDate.plusYears(yearOffset).plusMonths(monthOffset).plusDays(dayOffset)

          var DateTimeFormatter = Java.type('java.time.format.DateTimeFormatter')

          if (pattern) {
            var dtf = DateTimeFormatter.ofPattern(pattern)
          } else {
            var dtf = DateTimeFormatter.ofPattern('yyyy-MM-dd')
          }

          return dtf.format(newDate)
        }
      """
    * def dateDay1OfMonthOffset =
      """
        function(monthOffset) {
          var LocalDate = Java.type('java.time.LocalDate')
          var newDate = LocalDate.now()
          newDate = newDate.plusMonths(monthOffset).withDayOfMonth(1)

          var DateTimeFormatter = Java.type('java.time.format.DateTimeFormatter')

          return DateTimeFormatter.ofPattern('yyyy-MM-dd').format(newDate)
        }
      """
    * def dateMinusMonthsDayMax28 =
      """
        function(monthOffset) {
          var Math = Java.type('java.lang.Math')
          var LocalDate = Java.type('java.time.LocalDate')
          var newDate = LocalDate.now()
          newDate = newDate.plusMonths(monthOffset)
          newDate = newDate.withDayOfMonth(Math.min(newDate.getDayOfMonth(), 28))

          var DateTimeFormatter = Java.type('java.time.format.DateTimeFormatter')

          return DateTimeFormatter.ofPattern('yyyy-MM-dd').format(newDate)
        }
      """

    * def logRequestResponse =
      """
        function(karate) {
          if (debugExchange) {
            karate.log('METHOD/URL', karate.prevRequest.method, karate.prevRequest.url)
            karate.log('REQ BODY', karate.prevRequest.body)
            karate.log('RES BODY', karate.get('response'))
          }
        }
      """
    * def getLogLevel =
      """
        function() {
          var LoggerFactory = Java.type('org.slf4j.LoggerFactory');
          var logger = LoggerFactory.getLogger('com.intuit.karate');
          return logger.getLevel() == null ? 'DEBUG' : logger.getLevel().toString();
        }
      """
    * def setLogLevel =
      """
        function(logLevel) {
          var LoggerFactory = Java.type('org.slf4j.LoggerFactory');
          var logger = LoggerFactory.getLogger('com.intuit.karate');
          var Level = Java.type('ch.qos.logback.classic.Level')
          logger.setLevel(Level.toLevel(logLevel));
        }
      """
