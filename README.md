# Commission-Service
```$xslt
This project is used in fetching calculated commission for customers 
doing process in parallel asynchronously. It is even-driven 
as such you only need one endpoint to do your stuffs.

```
## Spring boot Setup
```$xslt
A starter project can be downloaded with all dependencies from
with the following dependencies if not already exist

1. Devtools
2. Sprinf Data Mongo
3. Spring Web
4. Lombok
5. Spring Security
```
<a href="https://start.spring.io/">spring.io</a>


## Environment Setup
```$xslt
1. setup the mongo Url if it not present

2. User1 and User2 in the application.yml files are the only authroize user to the system
       
```
<span style="background-color: blue; padding: 5px ">/application.properties</a>


<h3>Steps  to setup project</h3>
```$xslt
1. Open the project starter pack in Intellij IDEA; my favourite you can use others
2. Make sure to connect to internet, then open the command line interface in your IDEA.
3. input $ git clone ::::::::::::::
4. It will download to your work directory.
5. install Mongodb in your computer and make sure the server is running
```

## How to Run
```$xslt
Application runs in main method after the applicationcontext boostup 
the files is loaded in the database asynchronously. it is event-driven
```
## How to Build the Image

```$xslt

To build the image run 

$ Docker build -t NamOfYOurChoice: latest .

```
## Important APIs

<h3>Secured EndPoints</h3>


###
1. GET http://localhost:8080/commission-request

### RequestParam

```$xslt

 REQUEST: elasticCustomerId= "1,2,3" or 1 or ALL
 
 The requestParam can accept time string either COMMA seperated or 
 single value or all, it is also of type not mandatory
 
```

## Thank you for reading.
