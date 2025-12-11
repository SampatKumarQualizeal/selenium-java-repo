# Selenium Cucumber Test Framework

This Maven-based Selenium Java automation framework is designed using the Page Object Model and integrates Cucumber for BDD-style testing.

## Project Structure
- **src/test/java/pages/**: Contains Page Object Model classes.
- **src/test/java/gluecode/**: Contains Step Definitions and Shared Class.
- **src/test/java/tests/**: Contains Test Runner classes.
- **src/test/resources/features/**: Contains Cucumber Feature files.
- **src/test/resources/**: Contains configuration files (e.g., config.properties, testng.xml).

## Import Corrections

### Incorrect Imports
- In `gluecode/ShoppingCartSteps.java`, replace `import io.cucumber.datatable.DataTable;` with `import io.cucumber.datatable.DataTable;` to ensure correct data table usage.

### Corrected Imports
- Ensure all imports are correctly specified in each Java class file.

## Maven Profiles
- **run-specific-tests**: To run individual runner classes.
- **run-suite-xml**: To run tests using a suite XML file.

## Execution
- Ensure the correct browser drivers are installed and configured.
- Specify the browser type and other configurations in `src/test/resources/config.properties`.
- Use the Maven command `mvn clean test` to execute tests.

## Notes
- Maintain the version properties in the `pom.xml` file for easy upgrades.
- Ensure the `maven-surefire-plugin` configuration is correct for running tests.
