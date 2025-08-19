![Build](https://img.shields.io/badge/build-passing-brightgreen)
![Java](https://img.shields.io/badge/java-11+-blue)
![TestNG](https://img.shields.io/badge/testng-framework-orange)
![Allure](https://img.shields.io/badge/report-Allure-purple)
[![Java CI](https://github.com/SeenBetterDes/SauceDemoQA/actions/workflows/JavaCI.yml/badge.svg)](https://github.com/SeenBetterDes/SauceDemoQA/actions/workflows/JavaCI.yml)
# Selenium Test Automation Project : Java + Maven

## Overview
 Welcome to my portfolio project! This is an automated testing framework built for the [SauceDemo](https://www.saucedemo.com/) website.
 The project is build with Selenium, Maven and Allure in order to perform different scenarios covering multiple functionalities such as Login and Inventory Management.
 
The project comes equipped with its own ``Selenium_project.xlsx`` Excel file, mirroring realistic test suites and scenarios. 
The actual structure follows Hybrid Automation Framework style, integrating Page Object Model(POM) to correctly find and perform operations of different elements.

### Key Highlights:

- **Hybrid Automation Framework design**
- **Page Object Model (POM) for element handling**
- **Data-driven testing with Excel ``Selenium_project.xlsx``**
- **Grouping for flexible test execution (smoke, regression, feature)**
- **Functional Test reporting**
- **CI/CD via GitHub Actions**

## Project Structure 

```Project_Selenium/
├── pom.xml                  # Maven dependencies
├── reports/                 # Execution reports
├── src/
│   ├── main/java/
│   │   ├── listeners/       # Allure + TestNG listeners
│   │   ├── pages/           # Page Object Model classes
│   │   ├── utils/           # Config & Excel utilities
│   └── test/java/tests/     # Test classes (LoginTest, InventoryTest)
├── src/main/resources/      # Configurations
├── src/test/resources/      # Test data/resources
└── Selenium_project.xlsx    # External test case repository
```


## Test Case Categories
The initial 25 Tests are built to test both visual and practical functionality  of Login and Inventory pages, relying on ``dataProvider`` class for inputting different credentials. This approach allows the test suite to be flexible and integratable with future changes to the Excel file.

**Login Tests**

- Valid Login  

- Locked-out User

- Invalid Password

- Empty username/password handling

- Handling of different problematic/standard user combinations

**Inventory Tests**

- Verify item count
- Different locator functions

- Sorting functionalities

- Wrapper menu functionalities

- Valid element transitions

# Setup Instructions

### Clone the repository:

```git clone https://github.com/your-username/selenium-project.git```

```cd Project_Selenium```

### Import Project:
- Open a valid editor that supports Java

- Import as Maven project

### Install Dependencies

- Maven will automatically install all dependencies defined in ``pom.xml``

### Run tests
Run tests via maven:

``mvn clean test``

or to run a specific test according to grouping

``mvn -Dtest=LoginTest test``

``mvn test -Dgroups="smoke"``

``mvn test -Dgroups="regression"``

``mvn test -Dgroups="feature"``

# Tools and Technologies

The project uses various tools in order to ensure a realistic test suite used in real life projects.

- Java 11+ 
- Maven
- Selenium WebDriver
- TestNG
- Allure Reports
- Apache POI

## Reporting

Project also includes a html reporting functionality via both TestNG and Allure listener classes.

- Default TestNG reports 
- Allure Reports

Allure Reports can be generated via terminal commands:

``allure serve target/allure-results``

**Note: This project is intended as a personal portfolio showcase.**
This project is licensed under the [MIT License](LICENSE).
