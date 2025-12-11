@smoketest
Feature: Shopping Cart Functionality
  This feature ensures that users can add items to the cart and handle payment restrictions appropriately.

  Background:
    Given the user has access to the Fabindia website
    And the Chrome browser is installed
    And the user navigates to the Fabindia homepage
    And the user is on the "Western Wear" page
    And the user has selected a "Shirt" from the "Category" filter
    And the user has selected size "L" for the shirt
    And the shirt is added to the cart
    And the user proceeds to checkout
    And the user logs in using email and password
    And the user continues to the payment page
    And the user selects "Card Payment" as the payment method
    And the user enters dummy card details:
      | card_number        | cardholder_name |
      | 4111 1111 1111 1111| Test User       |
  
  Scenario: Verify card saving restriction error message
    When the user attempts to save the card
    Then the system should display "Saving of this card type as per RBI guidelines is not supported. You can still use it to complete your purchase."