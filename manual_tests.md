# Manual tests executed

The manual tests were executed in the PetClinic application, which is a web application that displays and manages
information related to pets and veterinarians in a pet clinic.

For the manual tests, the following functionalities were tested:

1. Allow to create a new Pet for an existing Owner
2. Allow to register a new visit to the clinic for a Pet
3. View all the Pets and visits for a specific Owner
4. Register a new owner using the same data as an existing owner
5. Delete a owner using the API

* The tests were executed locally using the application running in a Docker container.
* The tests were executed in the following browsers:
    * Chrome
    * Edge

## Test Cases

All the test has a precondition that the application is running.

### Test Case 1: Allow to create a new Pet for an existing Owner

#### Steps

1. Access the application at `http://localhost:8080`
2. Click on the `Owners` menu
3. Click on the `All` option
4. In the Owners table, click on `Harold Davis`
5. In the Owner details page, click on the `Add New Pet` button
6. Fill the Pet form with the following data:
    * Name: `Rex`
    * Birth Date: `2022-01-01`
    * Type: `dog`
7. Click on the `Submit` button

#### Expected Result

The Pet `Rex` should appear in the Pets table for the Owner `Harold Davis`

### Test Case 2: Allow to register a new visit to the clinic for a Pet

Precondition: The Pet `Rex` is created for the Owner `Harold Davis`

#### Steps

1. Access the application at `http://localhost:8080`
2. Click on the `Owners` menu
3. Click on the `All` option
4. In the Owners table, click on `Harold Davis`
5. In the Owner details page, click on the `Add Visit` button for the Pet `Rex`
6. Fill the Visit form with the following data:
    * Date: `2023-01-01`
    * Description: `Annual checkup`
7. Click on the `Submit` button

#### Expected Result

The visit should appear in the Visits table for the Pet `Rex`

### Test Case 3: View all the Pets and visits for a specific Owner

#### Steps

1. Access the application at `http://localhost:8080`
2. Click on the `Owners` menu
3. Click on the `All` option
4. In the Owners table, click on `Jean Coleman`

#### Expected Result

The following Pets should appear in the Pets table:

| Name     | Birth Date  | Type |
|----------|-------------|------|
| Max      | 2012 Sep 04 | cat  |
| Samantha | 2012 Sep 04 | cat  |

The following Visits should appear in the Visits table for the Pet `Max`:

| Date        | Description |
|-------------|-------------|
| 2013 Jan 03 | neutered    |
| 2013 Jan 02 | rabies shot |

The following Visits should appear in the Visits table for the Pet `Samantha`:

| Date        | Description |
|-------------|-------------|
| 2013 Jan 04 | spayed      |
| 2013 Jan 01 | rabies shot |

### Test Case 4: Register a new owner using the same data as an existing owner
For this test, the Owner `Jean Coleman` will be used as the existing owner.
#### Steps
1. Access the application at `http://localhost:8080`
2. Click on the `Owners` menu
3. Click on the `Register` button
4. Fill the Owner form with the following data:
    * First Name: `Jean`
    * Last Name: `Coleman`
    * Address: `105 N. Lake St.`
    * City: `Monona`
    * Telephone: `6085552654`
5. Click on the `Submit` button

#### Expected Result
An error message should appear indicating that the Owner already exists.

### Test Case 5: Delete a owner using the API
For this test, I use postman file to delete an owner using the API.
#### Steps
1. Open Postman
2. send a DELETE request to `http://localhost:8080/owners/1`

#### Expected Result
Based on the API documentation, the owner with id 1 should be deleted, and the response should be 200 OK.
