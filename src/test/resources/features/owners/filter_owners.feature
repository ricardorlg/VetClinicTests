@Web
Feature: The Pet Clinic should allow to filter the owners in the system
  As a VetClinic Manager
  I would like to be able to filter the owners in the system
  In order to find the owners and their pets easily

  Example: the one where Ricardo filters the owners by city
    Given Ricardo has registered the following owners in the system
      | firstName | lastName | address     | city  | phone      |
      | John      | Doe      | 123 Main St | Miami | 3051234567 |
      | Jane      | Doe      | 128 Main St | Miami | 3051234267 |
    And he wants to filter the data of the owners displayed in the Owners page
    When he filters using the following information "Miami"
    Then he should see the following owners in the table
      | firstName | lastName | address     | city  | phone      |
      | John      | Doe      | 123 Main St | Miami | 3051234567 |
      | Jane      | Doe      | 128 Main St | Miami | 3051234267 |

  Example: the one where Ricardo filters the owners by phone
    Given Ricardo has registered the following owners in the system
      | firstName | lastName | address                  | city      | phone      |
      | Jost      | Vander   | zuid-hollandstraat 130-2 | Amsterdam | 0629423456 |
    And he wants to filter the data of the owners displayed in the Owners page
    When he filters using the following information "0629423456"
    Then he should see the following owners in the table
      | firstName | lastName | address                  | city      | phone      |
      | Jost      | Vander   | zuid-hollandstraat 130-2 | Amsterdam | 0629423456 |

  Example: the one where Ricardo filters the owners by first name
    Given Ricardo has registered the following owner in the system
      | firstName | lastName   | address                | city   | phone      |
      | Paola     | Larrahondo | carrera 15F #30-15 sur | Bogota | 3158230923 |
    And he wants to filter the data of the owners displayed in the Owners page
    When he filters using the following information "Paola"
    Then he should see the following owners in the table
      | firstName | lastName   | address                | city   | phone      |
      | Paola     | Larrahondo | carrera 15F #30-15 sur | Bogota | 3158230923 |

  Example: the one where Ricardo filters the owners by last name
    Given Ricardo has registered the following owner in the system
      | firstName | lastName | address    | city   | phone      |
      | Arthur    | Dent     | 42 Main St | London | 0207946060 |
    And he wants to filter the data of the owners displayed in the Owners page
    When he filters using the following information "Dent"
    Then he should see the following owners in the table
      | firstName | lastName | address    | city   | phone      |
      | Arthur    | Dent     | 42 Main St | London | 0207946060 |

  Example: the one where Ricardo filters the owners by address
    Given Ricardo has registered the following owner in the system
      | firstName | lastName | address     | city      | phone      |
      | Selen     | Poe      | 328 Main St | Cartagena | 3051134267 |
    And he wants to filter the data of the owners displayed in the Owners page
    When he filters using the following information "328 Main St"
    Then he should see the following owners in the table
      | firstName | lastName | address     | city      | phone      |
      | Selen     | Poe      | 328 Main St | Cartagena | 3051134267 |

  Example: the one where Ricardo filters the owners by first and last name
    Given Ricardo wants to filter the data of the owners displayed in the Owners page
    When he filters using the following information "John Doe"
    Then he should see no owners

  Example: the one where Ricardo filters the owners by unknown city
    Given Ricardo wants to filter the data of the owners displayed in the Owners page
    When he filters using the following information "Rotterdam"
    Then he should see no owners