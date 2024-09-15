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