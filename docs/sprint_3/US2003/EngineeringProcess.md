# US2003
=======================================


# 1. Requirements

**US2003** - As Warehouse Employee, I want to access the list of orders that need to be prepared by an AGV and be able to ask/force any of those orders to be immediately prepared by an AGV available.
___
Question: 

    "...what would be the criterion to define whether or not this feature was functional?" 

Answer:

    The warehouse employee is able to (i) select an order that needs to be prepared; (ii): select the AGV from the ones that are available and able to perform the task; (iii) the task is sent to the selected AGV and (iv) the order change its status. 

Reference: [Forum Question](https://moodle.isep.ipp.pt/mod/forum/discuss.php?d=16290#p20988)
___

According to the requirements specified previously and also the context of the integrative project within EAPLI and LAPR4, the plan for the user story is the followning:

- Create a sub-menu within the main menu UI with the title "Assign Order To An AGV".
- Ask the user to select one order to be prepared.
- Ask the user to select one agv to prepare the order.
- Change the status of the chosen Order and assign to the agv.

# 2. Analysis
At first view, this User Story seems almost as simple as listing all of the registered orders and idle AGVs so the Warehouse Employee can assign one other to an agv. The main challenge here is that, since the change of a status could be done in different scenarios within different status', this User Story can be developed in a way that provides more generic tools for other User Stories that will need this type of functionality.

We had an approach, regarding Statuses, that is as pragmatic as one can be. Every e-commerce business with a warehouse management component will follow a well defined list of statuses that an order can go through. It does not make much sense that the available statuses will be changed any time after being defined in the beginning. Unless there is a major structural change in the business, an Order will always follow the same steps. Taking this in mind, we could have either gone for a JSON file with all the defined statuses or, to really simplify our work and the business' work, use an ENUM. That is what we have done.

The ones we have defined are the following:

![ORDERSTATUS_ENUMS](orderstatus_enum.png)

## User interaction
### Menu Layout
To make sure that the user was able to easily interact with the functionality to assign/force an order to an agv, the planned approach consisted in adding an item to an already existing sub-menu focused on Orders within the main menu with the title **Assign Order To An AGV**.

## Domain concepts
According to the gathered requirements and also the user story specification, we could extract the following domain concepts:

### Entities
In this case, we are focusing on Orders and Agv. It is the two main entities, we will focus on their statuses.

### Value Objects
The most import Value Object for this User Story is the Order's Order Status and the Agv's Agv Status. 

**Value Objects**: OrderStatus, AgvStatus

### Application Engineering
The developed code should follow an approach where there is a clear separation of concerns within the application and a clear division of responsibilities for each developed class, meaning, there should be a clear separation between the code that is meant to handle the User Interface, the code that is meant to handle the persistence of the orders and agvs and also the code that is meant to make sure the business rules are applied properly. 
In order to adhere to the previous statement the approach taken consisted of employing the following layers for the developed code:

**Action Layer**: The action layer is responsible for initiating the action necessary to display the correct user interface to the user so that the user can type all the data required.

**User Interface Layer**: The user interface layer is responsible for handling all the user inputs and all the interaction with the user.

**Controller Layer**: The controller layer is responsible for receiving the user input from the UI and performing the necessary operations that are needed to fulfill the user story.

**Service Layer**: The service layer is responsible for having the functionalities that most likely will be shared between User Stories. It is mostly useful to avoid code duplication.

**Persistence Layer**: The persistence layer is responsible for actually persisting the data that is meant to be persisted under the context of the user story.

**Domain Layer**: The domain layer is responsible for employing and enforcing all the business rules related to the Order and OrderStatus.

This layered approach helps to adhere to vital SOLID principles such as the Single Responsibility Principle and Open Closed Principle.

# 3. Design

## 3.1. Realization of Functionality
The following system sequence diagram displays the interaction between the user and the system:


**System Sequence Diagram**:

![SSD_US2003](US2003_SSD.svg)

The following sequence diagram displays the interaction between all the developed components inherent to this user story:

**Sequence Diagram**:
![SD_US2003](US2003_SD.svg)

## 3.2. Class Diagram
In order to make the different layers of the application loosely coupled a set of interfaces was defined to make sure that the dependencies between layers were upon abstractions and not actual concrete implementations. The defined interfaces were: AssignOrderToAvgController. 

The AssignOrderToAvgController interface defines the contract fulfilled by the controller or the supported operations by the controller layer.


![CD_US2003](US2003_CD.svg)

## 3.3. Software Patterns

### Layered architecture
As already mentioned on other sections of this document and also the previously displayed class diagram, a well thought out layered approach was followed for the development of this feature. Five layers were developed with concrete responsibilities in mind:

**Action Layer**: The action layer is responsible for initiating the action necessary to display the correct user interface to the user so that the user can type all the data required.

**User Interface Layer**: The user interface layer is responsible for handling all the user inputs and all the interaction with the user.

**Controller Layer**: The controller layer is responsible for receiving the user input from the UI and performing the necessary operations that are needed to fulfill the user story.

**Service Layer**: The service layer is responsible for having the functionalities that most likely will be shared between User Stories. It is mostly useful to avoid code duplication.

**Persistence Layer**: The persistence layer is responsible for actually persisting the data that is meant to be persisted under the context of the user story.

**Domain Layer**: The domain layer is responsible for employing and enforcing all the business rules related to the Order, Agv, OrderStatus and AgvStatus.

### SOLID Principles
According to the class diagrams displayed and also the explanations provided in the Design section we can infer that the following SOLID principles were employed in the developed of this feature:

**Single Responsibility Principle**: Each developed class has a specific purpose attributed to it and a specific responsibility assigned to it. This can also be inferred from the very small set of methods provided by each class.

**Open/Closed Principle**: Each developed class can be further extended but cannot be modified as its behavior is strictly defined and well outlined.

**Interface Segregation Principle**: Each defined interface is really small in size and very specific which adheres to the principle of Interface Segregation from SOLID.

**Dependency Inversion Principle**: Dependencies between modules are bound by the abstractions created by the interfaces and not by actual concrete implementations, which adhere to the principle of dependency inversion from SOLID.

### Explicit dependencies
From the class diagram, one can infer that each class has a constructor explicitly defining the dependencies that the class needs in order to perform its operations which is considered to a good practice to follow since it makes it transparent to the consumer of the class which is the dependencies of the consumed class. 

The usage of explicit dependencies also helps with unit testing since it allows for "injected" mocked versions of the dependencies which can be manipulated and leveraged in order to create unit tests with the dependencies isolated from the class to be tested.

### Interfaces
One can infer from the class diagram that interfaces were defined and used during the development of this feature. The usage of these interfaces makes the codebase more coherent and promotes well-defined responsibilities. Interfaces were also used to make sure that the different modules of the developed code can depend upon abstractions and not actual concrete implementations. The usage of interfaces also made it easier and improved the created unit tests, since the usage of dependencies through interfaces allows the usage of mocks in order to isolate dependencies from the classes being tested.

### Immutability
One can infer from the class diagram that each developed class has a single constructor with all the required dependencies and no setters defined. This promotes immutability within the codebase which also helps to have concrete and predictable behavior in the code that was developed.

## 3.4. Tests 

### Regular Unit Tests

For this type of functionality, we do not really have a way to Test Business Rules, per say.
The Order entity and the OrderStatus should already have their tests defined sames as the Agv and AgvStatus. 
In this case we have a listing functionality and a Value Object updating functionality.

So that this is not incomplete, we can test it ourselves. After inserting an order and make sure that are available agv we can start doing the following steps:

We start by choosing the submenu Orders:

![Step1](step1.png)

We go into Assign Order To An AGV:

![Step2](step2.png)

It lists all of the orders that need to be prepared (as we can see, the status is **REGISTERED**) and we will choose number one:

![Step3](step3.png)

Now it lists all of the agv available (as we can see, the status is **IDLE**) and we will choose number one:

![Step4](step4.png)

It shows the success message:

![Step5](step5.png)

To confirm we can consult the orders and the agv to see the status changed.

# 4. Implementation

Implementation of the user story went according to the plan and the analysis described on this document.

## Commits

![Commits](Commits.png)

## Tasks

![Tasks](Tasks.png)

# 5. Integration/Demonstration

During the development of this feature, was used code developed in US2004 to make sure that there is no code duplicated.

# 6. Observations
None


