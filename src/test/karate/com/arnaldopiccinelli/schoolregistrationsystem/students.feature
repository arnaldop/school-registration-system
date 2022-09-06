Feature: student tests

  Background:

    * callonce read('classpath:utils.feature')

    Given url baseUrl

  Scenario: student CRUD

    # READ
    Given path 'students'
    When method get
    Then status 200
    * def originalCount = response.numberOfElements

    * def payload =
      """
        {
          firstName: '#(randomAlphabetic(5))',
          lastName: '#(randomAlphabetic(5))',
          identifier: '#("SRS-" + randomNumeric(3))'
        }
      """

    # CREATE
    Given path 'students'
    And request payload
    When method post
    Then status 200
    And match response contains payload
    * def payload = response

    # READ
    Given path 'courses'
    When method get
    Then status 200
    * def updatedCount = response.numberOfElements
    * match updatedCount == originalCount + 1

    * payload.firstName = randomAlphabetic(5)

    # UPDATE
    Given path 'students'
    And request payload
    When method put
    Then status 200
    * match response == payload

    # READ
    Given path 'students', payload.id
    When method get
    Then status 200
    * match response == payload

    # DELETE
    Given path 'students'
    And param ids = payload.id
    When method delete
    Then status 200

    # READ
    Given path 'students'
    When method get
    Then status 200
    * def updatedCount = response.numberOfElements
    * match updatedCount == originalCount
