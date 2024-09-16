Business Need: The Pet Clinic should allow to manage the pets information
  As a VetClinic Manager
  I would like to be able to manage the pets information
  In order to be able to add new pets to owners registered in the system
  Or edit the information of the pets

  Rule: The system should allow to register a new pet in the system
  Web form uses the following format for the birthDate: MM-dd-yyyy

    @Web
    Example: the one where Kelly see the different types of pets available in the system
      Given Kelly wants to register a new pet for Betty Davis using the Web application
      Then she should see the following types of pets available
        | bird    |
        | cat     |
        | dog     |
        | hamster |
        | lizard  |
        | snake   |

    @Web
    Example: the one where Ricardo wants to register a new pet using the Web application
      Given Ricardo has registered the following owner in the system
        | firstName | lastName | address                   | city      | phone      |
        | Sally     | Plum     | oudezijds-voorburgwal 123 | Amsterdam | 0612345678 |
      When he registers the following pet for Sally Plum using the Web application
        | name | birthDate  | type |
        | Luna | 01-10-2015 | dog  |
      Then the pet information should be displayed in the pets and visits section

    @Web
    Example: the one where Ricardo wants to register multiple pets using the Web application
      Given Ricardo has registered the following owner in the system
        | firstName | lastName | address                   | city      | phone      |
        | Chris     | Brown    | oudezijds-voorburgwal 123 | Amsterdam | 0612345638 |
      When he registers the following pets for Chris Brown using the Web application
        | name   | birthDate  | type   |
        | Nala   | 02-15-2016 | cat    |
        | Nagini | 01-05-2019 | snake  |
        | coco   | 06-01-2000 | lizard |
      Then the pets  should be displayed in the owner information page

    @Api
    Example: the one where Ricardo wants to register a new pet using the clinic API
      Given Ricardo has registered the following owner in the system
        | firstName | lastName | address             | city        | phone      |
        | Michael   | Jackson  | neverland-ranch 123 | Los Angeles | 1234567890 |
      When he sends a request to register the following pet for Michael Jackson using the clinic API
        | name   | birthDate  | type |
        | Killer | 2015-01-20 | dog  |
      Then the system should return a 204 response code
      And the information of the pet should be added to the owner information

    @Api
    Scenario Template: : the one where Ricardo wants to register a new pet with invalid data using the clinic API
      Given Ricardo has registered the following owner in the system
        | firstName | lastName | address             | city        | phone      |
        | Michael   | Jackson  | neverland-ranch 123 | Los Angeles | 1234567890 |
      When he sends a request to register the following pet for Michael Jackson using the clinic API
        | name   | birthDate   | type   |
        | <name> | <birthDate> | <type> |
      Then the system should return a <expected_response> response code
      Examples:
        | name                                | birthDate  | type  | expected_response |
        | more_than_30_characters_for_request | 2015-01-20 | dog   | 500               |
        | Killer                              | not_a_date | dog   | 400               |
        | Killer                              | 2015-01-20 | horse | 500               |


  Rule: The system should allow to update the information of a pet registered in the system
  Web form uses the following format for the birthDate: MM-dd-yyyy

    @Web
    Example: the one where Ricardo wants to update the information of a pet using the Web application
      Given Ricardo has registered the following owner in the system
        | firstName | lastName | address              | city        | phone      |
        | Tom       | Cruise   | hollywood-street 123 | Los Angeles | 1467890123 |
      And he has registered the following pet for Tom Cruise
        | name   | birthDate  | type |
        | Killer | 2015-01-20 | dog  |
      When he updates the information of the pet using the Web application to
        | name   | birthDate  | type |
        | Cookie | 02-21-2015 | cat  |
      Then the updated pet information should be displayed in the pets and visits section

