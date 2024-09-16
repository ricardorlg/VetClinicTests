Business Need: The Pet Clinic should allow to register a pet owner in the system
  As a VetClinic Manager
  I would like to be able to register a new owner in the system
  In order to handle all the pets and visits made by him

  Rule: New owners must be registered using a full personal information
  the mandatory fields are: firstname, lastname, address, city and telephone

    @Web
    Example: the one where Ricardo registers a new owner using the Web form with all mandatory data
      Given Ricardo wants to register a new owner using the web application
      When he fills the registration form with the following information
        | firstName | lastName | address                  | city      | phone      |
        | Estefania | Ramirez  | zuid-hollandstraat 130-2 | Amsterdam | 0629416321 |
      Then the owner should be registered successfully

    @Web
    Example: the one where Kelly tries to register a new owner using the Web form with invalid data
      Given Kelly wants to register a new owner using the web application
      When she fills the registration form with the following information
        | firstName | lastName | address | city    | phone |
        | [blank]   | [blank]  | [blank] | [blank] | abc   |
      Then she should see an alert containing the following error
      """
      Bad Request
      address: must not be empty
      city: must not be empty
      firstName: must not be empty
      lastName: must not be empty
      telephone: numeric value out of bounds
      """

    @Api
    Example: the one where Ricardo registers a new owner using the clinic API with all mandatory data
      When Ricardo sends a registration request using the clinic API with the following data
        | firstName | lastName   | address                | city   | phone      |
        | Natalia   | Larrahondo | carrera 12F #30-15 sur | Bogota | 3158230923 |
      Then the system should return a 201 response code

    @Api
    Scenario Template: the one where Kelly tries to register a new owner using the clinic API with invalid data
      When Kelly sends a registration request using the clinic API with the following data
        | firstName   | lastName   | address   | city   | phone   |
        | <firstName> | <lastName> | <address> | <city> | <phone> |
      Then the system should return a 400 response code
      And the response body should include the error with code <code_error> and field <field_error>
      Examples: invalid data
        | firstName | lastName | address | city      | phone       | code_error | field_error |
        | [empty]   | perez    | address | amsterdam | 0629416300  | NotEmpty   | firstName   |
        | ricardo   | [empty]  | address | amsterdam | 0629416300  | NotEmpty   | lastName    |
        | ricardo   | perez    | [empty] | amsterdam | 0629416300  | NotEmpty   | address     |
        | ricardo   | perez    | address | [empty]   | 0629416300  | NotEmpty   | city        |
        | ricardo   | perez    | address | amsterdam | [empty]     | NotEmpty   | telephone   |
        | ricardo   | perez    | address | amsterdam | abc         | Digits     | telephone   |
        | ricardo   | perez    | address | amsterdam | 12312312312 | Digits     | telephone   |

  Rule: The system should not allow to register an owner with same personal information of an existing owner

    @Web
    Example: the one where Ricardo tries to register a new owner using the Web form with an existing owner
      Given Kelly has already registered an owner with the following information
        | firstName | lastName | address           | city      | phone      |
        | Josh      | Long     | prinsengracht 263 | Amsterdam | 0687234567 |
      And Ricardo wants to register a new owner using the web application
      When He fills the registration form with the same data used by Kelly
      Then he should see an alert containing the following error
            """
            Bad Request
            An owner with the same personal information already exists
            """

    @Api
    Example: the one where Ricardo tries to register a new owner using the clinic API with an existing owner
      Given Ricardo has already registered an owner with the following information
        | firstName | lastName | address          | city      | phone      |
        | Peter     | Anderson | nassaukade 161-2 | Amsterdam | 0629446321 |
      When Kelly sends a registration request using the clinic API with the same data used by Ricardo
      Then the system should return a 400 response code
      And the response body should include the error with code AlreadyExists and field owner