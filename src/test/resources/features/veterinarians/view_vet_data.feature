Business Need: The Pet Clinic should allow to view the data of the veterinarians in the system
  As a VetClinic Manager
  I would like to be able to view the data of the veterinarians in the system
  In order to know information about the veterinarians that work in the clinic

  The default veterinarians are:
  | Name           | Specialties           |
  |----------------|-----------------------|
  | James Carter   |                       |
  | Helen Leary    | radiology             |
  | Linda Douglas  | dentistry surgery     |
  | Rafael Ortega  | surgery               |
  | Henry Stevens  | radiology             |
  | Sharon Jenkins |                       |


  @Web
  Scenario: the one where Kelly wants to view the data of all veterinarians using the Web application
    Given Kelly is using the Pet Clinic Web application
    When she opens the veterinarians page
    Then she should see the following veterinarians information
      | name           | specialities      |
      | James Carter   | [empty]           |
      | Helen Leary    | radiology         |
      | Linda Douglas  | dentistry surgery |
      | Rafael Ortega  | surgery           |
      | Henry Stevens  | radiology         |
      | Sharon Jenkins | [empty]           |

  @Api
  Scenario: the one where Ricardo wants to view the data of all veterinarians using the API
    When Ricardo sends a request to get the list of veterinarians
    Then the system should return a 200 response code
    And the response body should include the default veterinarians information