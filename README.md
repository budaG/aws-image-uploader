# AWS Image Uploader REST API
A Spring Boot application with react front-end to upload images to AWS S3 buckets. This project provides the boilerplate code to upload images to AWS S3 buckets.

### Setup Spring Boot

#### Setting local database
Run `Postgres` locally/remotely. I set up mine using docker, works really well with window's WSL.\
I used `Postgres` but you can use really any relational database or non-relational database.\
If you are using other relational database, just make sure you add the driver dependency to pom.xml

For non-relational database user, it might be more work. look into `Spring Data MongoDB`.

#### Setting credentials.properties
Set up a credentials.properties file that stores your AWS access key.\
aws.key=`accessKey`\
aws.value=`accessValue`\

This is how I formatted and wrote a java class `Credential` to match the property. You may change the formatting if you like but make sure you change them both in `Credential` class and `credentials.properties` resource file. This is basically leveraging Spring Boot's `@ConfigurationProperties` and `@PropertySource` annotation to read credentials. Good practice to extract sensitive information like credentials to external file.


#### Run Spring Boot Application
Go to `AwsImageUploaderApplication`, right-click on the code (anywhere).\
Click `Run AwsImageUpload...main()`. \
Your REST API should be up and running.\
Go to `UserProfileResouce` class, the API endpoints are defined there.

For local endpoints, running on port:8080\
`GET` `http://localhost:8080/api/v1/userProfiles`, gets the user profiles.\
`POST` `http://localhost:8080/api/v1/userProfiles` saves the user profile.

### Setup React App

In the React project directory under `src/main/frontend`, you can run:

#### `npm install`

This will install the react-script package along with other packages needed to run the React app script.

### `npm start`

Runs the app in the development mode.\
Open [http://localhost:3000](http://localhost:3000) to view it in your browser.

The page will reload when you make changes.\
You may also see any lint errors in the console.

# Warning !!!
This boilerplate doesn't provide any unit tests or validations. Please add  your own.