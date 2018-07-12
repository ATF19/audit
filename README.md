# AUDIT - An Auditing Tool With XLS Export

## Introduction
> A modern online auditing tool that uses CMI scoring system to create a report with graphical radar chart which is exported to excel format.
Backend for admins is also available.

## Features
* Real-time chart (updated as you write)
* Export to excel
* Administrative backend
* API Firewall (tokens based verification)
* Responsive
* PWA
* Could be converted to a mobile application easily

## Technologies
* MySQL database
* JAVA / JEE
* Hibernate
* Springboot / Spring JPA / Spring Data 
* Apache POI
* React
* Admin on rest

## Usage
> Follow these steps to see how to run this code
1. Clone or download this repo
2. Create a database on your MySQL server and update the `application.properties` file accordingly
3. On your terminal navigate to the project root folder and run `mvn spring-boot:run`. This will start the server 
4. To start the admin dashboard, navigate to the `public/audit-admin` and run `npm install`. When it finish loading run `npm start`
5. To start the client do the same as above but inside the `public/audit` directory

## Build And Deploy
> To build the project follow these steps
1. Run `mvn package` inside the root folder to build the server in a .jar format. You will find the output inside the target folder.
2. To build the admin dashboard, navigate to the `public/audit-admin` and run `npm run build`. See more about how to build React projects [on this link](https://github.com/facebook/create-react-app "ReactJS github repo")
3. To build the client, follow the same steps as above inside the `public/audit` directory

> You can deploy the app on any Paas cloud provider (i recommend Heroku) or any server that support Java Web Applications.
The client and the admin dashboard can be run on any serverless hosting or even locally.

## Contact

> If you have any problem you can contact me at: **atef.najar@icloud.com**

