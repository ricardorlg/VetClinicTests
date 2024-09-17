[![Run PetClinic tests](https://github.com/ricardorlg/VetClinicTests/actions/workflows/build_and_test.yml/badge.svg)](https://github.com/ricardorlg/VetClinicTests/actions/workflows/build_and_test.yml)
# BackBase Principal QA Engineer Assignment 
This repository contains the technical assignment for the Principal QA Engineer position at BackBase.
## Deliverables
* [Manual test cases](manual_tests.md)
* [Overall test report](overall_report.md)
* [Test Automation Framework Documentation](test_framework_architecture.md)
* [PetClinic Automated Test Report](https://ricardorlg.github.io/VetClinicTests/)

## How to run the tests

### Requirements
- Java 21
- Maven 
- Docker

### Running the tests
The tests can be executed locally or using the [workflow](.github/workflows/build_and_test.yml) in GitHub Actions.
#### Locally

1. Clone the repository
2. Run the following command in the root folder:
```shell
mvn clean verify
```
By default, the tests will run in headless mode. To run the tests without headless mode, run the following command:
```shell
mvn clean verify -Dheadless.mode=false
```
**Note: All tests are executed in parallel**

By default, all tests will be executed. To run only the API tests, run the following command:
```shell
mvn clean verify -Dgroups="Api"
```

To run only the Web tests, run the following command:
```shell
mvn clean verify -Dgroups="Web"
```
A report will be generated in the `target/site/serenity` folder. To access the report, open the `index.html` file in a browser.

___
#### Using GitHub Actions

1. Goes to [Run PetClinic Automated Tests](https://github.com/ricardorlg/VetClinicTests/actions/workflows/build_and_test.yml)
2. Click on the `Run workflow` button
3. Click on the `Run workflow` button
4. Wait for the tests to finish
5. The test report will be published in [GitHub Pages](https://ricardorlg.github.io/VetClinicTests/)