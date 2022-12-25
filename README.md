### Introduction

There is a major new technology that is destined to be a disruptive force in the field of transportation: **the drone**. Just as the mobile phone allowed developing countries to leapfrog older technologies for personal communication, the drone has the potential to leapfrog traditional transportation infrastructure.

Useful drone functions include delivery of small items that are (urgently) needed in locations with difficult access.

### Task description

We have a fleet of **10 drone**. A drone is capable of carrying devices, other than cameras, and capable of delivering small loads. For our use case **the load is medications**.

A **Drone** has:
- serial number (100 characters max);
- model (Lightweight, Middleweight, Cruiserweight, Heavyweight);
- weight limit (500gr max);
- battery capacity (percentage);
- state (IDLE, LOADING, LOADED, DELIVERING, DELIVERED, RETURNING).

Each **Medication** has: 
- name (allowed only letters, numbers, ‘-‘, ‘_’);
- weight;
- code (allowed only upper-case letters, underscore and numbers);
- image (picture of the medication case).

Develop a service via REST API that allows clients to communicate with the drones (i.e. **dispatch controller**). The specific communicaiton with the drone is outside the scope of this task. 


### The service should allow:

- registering a drone;
- loading a drone with medication items;
- checking loaded medication items for a given drone; 
- checking available drones for loading;
- check drone battery level for a given drone;

#### Functional requirements

- There is no need for UI;
- Prevent the drone from being loaded with more weight that it can carry;
- Prevent the drone from being in LOADING state if the battery level is **below 25%**;
- Introduce a periodic task to check drones’ battery levels and create history/audit event log for this.

---

#### Non-functional requirements

- Input/output data must be in JSON format;
- Your project must be buildable and runnable;
- Your project must have a README file with build/run/test instructions (use DB that can be run locally, e.g. in-memory, via container);
- Required data must be preloaded in the database.
- JUnit tests are optional but advisable (if you have time);
- Advice: Show us how you work through your commit history.

Pre-Requirements
        Java 8
        Database – H2 DB
        IntelliJ IDE
        Postman
        
Main Steps to Build and Run the project

01.	Clone the project through GitHub repo link -> https://github.com/Dhanut/DroneService.git
          
          
02.	Go to the file directory where you have cloned and go inside the repo and right click on the pom.xml file and open with IntelliJ.

![image](https://user-images.githubusercontent.com/24970163/209461507-b78e375d-c064-43f4-8dbf-aa94046e0d9d.png)
 
03.	Setup the Java SDK for Java 1.8 and wait for few minutes until all maven dependencies downloaded in IntelliJ.

![image](https://user-images.githubusercontent.com/24970163/209461521-c91a83bb-6944-45e5-b55d-a5d3df4954cd.png)

04.	Build the project and run the main Java class and ensure project is working without any error.

Pre-Inserted data to the H2 database

Once we run the application, using the data.sql file it will automatically insert the below data to H2 DB.
 

![image](https://user-images.githubusercontent.com/24970163/209461537-ac5a143f-e318-4734-b6c5-d9308d78bc02.png)



Testing the End Points via Postman

All the end points in this project are authenticated with the Basic Authentication. Therefore, username and password need to be configured in all the endpoints.
 
 ![image](https://user-images.githubusercontent.com/24970163/209461544-f8ec1cc1-2a69-4d4a-b4ec-58685e8f7ad3.png)

Username - admin

Password - admin 
01.	Registering a drone


End Point - http://localhost:8888/api/demo/drone/register


Method – POST


Input - {
   	    "droneSerialNumber":"AB999000",
    "droneModel":"LIGHTWEIGHT",
    "droneWeight":"200",
    "droneBatteryCapacity":"50",
    "droneState":"IDLE"
}
 
 ![image](https://user-images.githubusercontent.com/24970163/209461558-a38fbe0c-184b-4974-b303-7dce5cb5bbee.png)


Response: - 

 ![image](https://user-images.githubusercontent.com/24970163/209461561-7f27f367-1fd5-48dc-b574-95599fd71589.png)


02.	Load a drone with medications

Assumptions: -

01.	Loading drone should be available in the database.
02.	A Drone can be loaded if and only if it is in IDLE or LOADING state only.
03.	More than one medication can be loaded at the same time.
04.	Total weight of loading medications + drone weight should not be over 500g.
05.	Drone batter capacity should be over 25%.
06.	If the selected drone initially in LOADING state and once after the medications loaded and Total weight of loading medications + drone weight = 500g, then the state of Drone should be updated to LOADED state.
07.	Same medication item can be loaded for the same drone n times, if the above conditional checks pass.
08.	All medications that are going to load should be first available in medication table. And weight of medications always a pre-defined value. This will not change during the load stage even.
09.	During the medication load, the weight of drone in the database will be updated accordingly.

End Point - http://localhost:8888/api/demo/drone/load


Method    – POST


Input        -  {
    "loadingDroneSerialNumber":"TR222783",
    "loadingTotalQuantity":2,
    "loadingDeliveryAddress":" No23, TownHall, Colombo",
    "loadingMedicationList":[{
    "medicationCode" : "001_AT_77",
    "medicationName": "ZIAGEN",
    "medicationWeight": 5.00,
    "medicationImage": "ZIAGEN" },
{
    "medicationCode" : "002_ZXT_12",
    "medicationName": "PLENAXIS",
    "medicationWeight": 1.00,
    "medicationImage": "PLENAXIS" }
  ]

 	}

![image](https://user-images.githubusercontent.com/24970163/209461579-c87d212f-5119-4a56-9d30-c09e5b32e6d5.png)

 

Response: - 
 
 ![image](https://user-images.githubusercontent.com/24970163/209461583-568ac7fb-ea50-4fc6-8586-d9cb6b5f1081.png)



03.	Checking loaded medications

End Point - http://localhost:8888/api/demo/drone/check/medications/TR222783

Method – GET

Input - Path param – {drone_serial_number}

 ![image](https://user-images.githubusercontent.com/24970163/209461595-91d35cbe-6c9b-42a3-bc78-79fcced5d237.png)


Response: - 
 
![image](https://user-images.githubusercontent.com/24970163/209461601-84d7264a-b182-48d7-828e-6d10e2acc48b.png)



04.	Checking available drones
Assumption: Drone can be available if it is in IDLE or LOADING state only.

End Point - http://localhost:8888/api/demo/drone/check/available

Method – GET

Input - Path param – NA

![image](https://user-images.githubusercontent.com/24970163/209461612-da531e3f-d694-45d0-86ce-566e3f518136.png)

 
Response: - 
 
![image](https://user-images.githubusercontent.com/24970163/209461615-33618f1c-9d3f-422f-9c29-2112136f625e.png)

05.	Checking battery level of drones

End Point - http://localhost:8888/api/demo/drone/check/batteryLevel/AB999000

Method – GET

Input - Path param – Path param – {drone_serial_number}

![image](https://user-images.githubusercontent.com/24970163/209461624-6248990c-e98f-4749-ac55-fb426452e6f1.png)

 
Response: - 
 

![image](https://user-images.githubusercontent.com/24970163/209461628-de2635e8-0453-4473-9f14-ab5c00679328.png)



###logging the battery level of each drone in database using a scheduler
 
 ![image](https://user-images.githubusercontent.com/24970163/209461636-0242c95e-2ad5-445d-b219-b956799e121a.png)

###SNAP SHOT of H2 DATABASE
 
![image](https://user-images.githubusercontent.com/24970163/209461646-c4ee72cf-3ee1-40f7-9e97-7f5980f8f5b7.png)

##END
