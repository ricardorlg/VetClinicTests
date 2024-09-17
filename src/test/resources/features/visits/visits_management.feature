Business Need: The Pet Clinic should allow to manage the visits information
  As a VetClinic Manager
  I would like to be able to manage the visits information
  In order to be able to know the information of the visits made by the pets
  Or register new visits for the pets

  @Web
  Scenario: the one where Kelly wants to register a new visit using the Web application
    Given Kelly has registered the following owner in the system
      | firstName | lastName | address                 | city | phone      |
      | Ryan      | Reynolds | hollywood boulevard 123 | LA   | 1452369874 |
    And she has registered the following pets for Ryan Reynolds
      | name    | birthDate  | type |
      | Pikachu | 2015-05-20 | dog  |
      | Loki    | 2017-01-20 | cat  |
    When she registers the following visit for Pikachu using the Web application
      | date       | description |
      | 03-15-2016 | checkup     |
    Then the visit should be displayed in the owner information page

  @Web
  Scenario: the one where Kelly wants to register a new visit on a pet with already registered visits using the Web application
    Given Kelly has registered the following owner in the system
      | firstName | lastName | address              | city | phone      |
      | Hugh      | Jackman  | hollywood avenue 123 | LA   | 1762369874 |
    And she has registered the following pets for Hugh Jackman
      | name   | birthDate  | type  |
      | Nagiri | 2013-05-20 | snake |
      | Mew    | 2019-01-20 | cat   |
    And she has registered the following visit for Mew using the Web application
      | date       | description        |
      | 03-15-2016 | check skin problem |
    When she registers the following visit for Mew using the Web application
      | date       | description                            |
      | 08-25-2019 | snake is having problems with the skin |
    Then the visit should be displayed in the owner information page

  @Web
  Scenario: previous visit should be displayed in the Register Visit page
    Given Ricardo is in the Jean Coleman owner information page
    When he goes to the Register Visit for the pet named Max
    Then he should see all the previous visits made by Max
