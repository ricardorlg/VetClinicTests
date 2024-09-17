# Overall test report

This document contains the overall evaluation report for the PetClinic tests executed.

## Test approach

Initially I did a exploratory test session to understand the application and its functionalities. After that, I review
the openapi specification to understand the API endpoints and the expected behavior. From this information, I created
the manual test cases and automated tests, for the automated tests I used the BDD approach to create the scenarios.

The automated tests were executed in parallel to provide a quick feedback loop and ensure the application is working as
expected. I use the latest Gherkin syntax to create the scenarios so each scenario is focus on a single functionality.

## Manual tests results

| Test case                                                                                                                                                  | Result   | Issues                                                                                                           |
|------------------------------------------------------------------------------------------------------------------------------------------------------------|----------|------------------------------------------------------------------------------------------------------------------|
| [Allow to create a new Pet for an existing Owner](manual_tests.md#test-case-1-allow-to-create-a-new-pet-for-an-existing-owner)                             | Passed ✅ |                                                                                                                  |
| [Allow to register a new visit to the clinic for a Pet](manual_tests.md#test-case-2-allow-to-register-a-new-visit-to-the-clinic-for-a-pet)                 | Passed ✅ |                                                                                                                  |
| [View all the Pets and visits for a specific Owner](manual_tests.md#test-case-3-view-all-the-pets-and-visits-for-a-specific-owner)                         | Passed ✅ |                                                                                                                  |
| [Register a new owner using the same data as an existing owner](manual_tests.md#test-case-4-register-a-new-owner-using-the-same-data-as-an-existing-owner) | Failed ❌ | the application allows to create a new owner with the same data as an existing owner [evidence](failed_tc_4.png) |
| [Delete a owner using the API](manual_tests.md#test-case-5-delete-a-owner-using-the-api)                                                                   | Failed ❌ | the API responds with 405 code                                                                                   |

## Automated tests Information

For the automated tests, the following functionalities were tested:

1. Allow to register, edit and review Owners
2. Allow to register, edit and review Pets
3. Allow to register and review Visits
4. Check all the Veterinarians are displayed
5. Filter owners by all the fields

All the scenarios can be found in [Features](src/test/resources/features) folder.

The automated tests were executed using the [Serenity BDD](https://serenity-bdd.github.io) framework with
the [Cucumber](https://cucumber.io/) integration.

All the tests were executed in parallel and the results can be accessed in
the [PetClinic Automated Test Report](https://ricardorlg.github.io/VetClinicTests/).

## Why the test cases were chosen to automate

The test cases were chosen to automate because they cover the main functionalities of the application and are the most
critical ones. Automating these test cases will provide a quick feedback loop and ensure the application is working as
expected.

Following the BDD approach, the test cases were written in a way that they can be understood by all stakeholders, not
only the technical team. I didn't add implementation details in the cucumber scenarios, only the expected behavior.

Also with the chosen tests, I was able to demonstrate my knowledge in the automation field and show how I can create
automated tests for different functionalities. For both Web and API layers.

## Other additional information

The provided openapi specification was incomplete or outdated, so I wasn't sure if the API was working as expected. I
tried to use the API to delete an owner, but it responded with a 405 code. I couldn't find any information about the API
in the application, so I'm not sure if it's working as expected, I made the assumption that it should be possible to
delete an owner using the API.

It should be nice if the application has the concept of roles and permissions, so it would be possible to split the
tests by what a manager can do and what a regular user can do. I asssume that the application is for a VetClinic
manager, so I didn't define any test related to permissions.
