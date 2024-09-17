Business Need: The Pet Clinic should allow to manage the owners information
  As a VetClinic Manager
  I would like to be able to manage the owners information
  In order to handle all the pets and visits made by them

  Rule: The system should allow to list all the owners registered in the system

    @Api
    Example: the one where Ricardo wants to see the list of all owners using the clinic API
      When Ricardo sends a request to list all the owners using the clinic API
      Then the system should return a 200 response code
      And the response body should include the default owners registered in the system

  Rule: The system should allow to access the personal information of an owner

    @Web
    Example: the one where Ricardo wants to see the personal information of an owner using the Web application
    John Thompson is a new owner registered in the system so he doesn't have any pets or visits yet
      Given Kelly has registered the following owner in the system
        | firstName | lastName | address                   | city       | phone      |
        | John      | Thompson | north-hollandstraat 130-2 | Copenhagen | 4534567890 |
      And Ricardo wants to see the personal information of John Thompson using the Web application
      When he selects the owner in the Owners page
      Then he should see the owner information registered by Kelly
      And the pets and visits information should be empty

    @Web
    Example: the one where Ricardo wants to see the personal information of an owner with pets and visits using the Web application
    Jean Coleman is an owner registered in the system with pets and visits
      Given Ricardo wants to see the personal information of Jean Coleman using the Web application
      When he selects the owner in the Owners page
      Then he should see the registered information of Jean Coleman

    @Api
    Example: the one where Ricardo wants to see the personal information of an owner using the clinic API
      When Ricardo sends a request to see all the information of  the owner with ID 1 using the clinic API
      Then the system should return a 200 response code
      And the response body should include all the registered information of the owner including the pets and visits

  Rule: The system should allow to update the personal information of an owner

    @Web
    Example: the one where Ricardo wants to update the personal information of an owner using the Web application
      Given Kelly has registered the following owner in the system
        | firstName | lastName | address             | city      | phone      |
        | Susan     | White    | central-district 23 | Amsterdam | 0673423456 |
      When Ricardo updates the personal information of Susan White using the Web application to
        | firstName | lastName | address             | city    | phone      |
        | Susan     | Black    | central-district 29 | Chicago | 0123423456 |
      Then the owner should be updated successfully
      And the system should display the updated information

  Rule: The system should allow to delete an owner registered in the system

    @Api
    @manual
    @manual-result:failed
    Example: the one where Ricardo wants to delete an owner using the clinic API
      When Ricardo sends a request to delete an owner in the system
      Then the system should return a 200 response code
      And the owner should not be listed in the system anymore