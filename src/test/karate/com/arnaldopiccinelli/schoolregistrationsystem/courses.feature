Feature: course tests

  Background:

    * callonce read('classpath:utils.feature')

    Given url baseUrl

  Scenario: course CRUD

    # READ
    Given path 'courses'
    When method get
    Then status 200
    * def originalCount = response.numberOfElements

    * def payload =
      """
        {
          name: '#(randomAlphabetic(5))',
          identifier: '#("SRS-" + randomNumeric(3))',
          description: '#("Description " + randomNumeric(10))',
        }
      """

    # CREATE
    Given path 'courses'
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

    * payload.name = randomAlphabetic(5)

    # UPDATE
    Given path 'courses'
    And request payload
    When method put
    Then status 200
    * match response == payload

    # READ
    Given path 'courses', payload.id
    When method get
    Then status 200
    * match response == payload

    # DELETE
    Given path 'courses'
    And param ids = payload.id
    When method delete
    Then status 200

    # READ
    Given path 'courses'
    When method get
    Then status 200
    * def updatedCount = response.numberOfElements
    * match updatedCount == originalCount
