pipeline {
    agent any  // This tells Jenkins to run the pipeline on any available agent (node)

    environment {
        // You can define environment variables here (e.g., for Docker, Java versions, etc.)
        JAVA_HOME = 'C:\\Program Files\\Java\\openjdk-17_windows-x64_bin\\jdk-17'
        DOCKER_USERNAME = 'techguy6'
        DOCKER_PASSWORD = 'dockercool'
        IMAGE_NAME = 'techguy6/rest-assured'
        IMAGE_TAG = '${BUILD_NUMBER}'

    }

    stages {
        stage('Checkout Code') {
            steps {
                git branch: 'main', credentialsId: 'github-credentials', url: 'https://github.com/Lahiru66/RestAssured-API-Automation-Framework.git'
            }
        }

        stage('Build') {
            steps {
                // Run Maven or Gradle to build the project
                script {
                    bat 'mvn clean install'
                }
            }
        }

        stage('List Target Directory') {
            steps {
                script {
                    bat 'dir target'  // List the contents of the target directory
                }
            }
        }


        stage('Run Tests') {
            steps {
                // Run your API tests (e.g., REST Assured tests)
                script {
                    bat 'mvn test -Dallure.results.directory=target/allure-results'
                }
            }
        }


        stage('Publish Allure Results') {
            steps {
                  allure([
                           includeProperties: false, // Set to true if you want to include additional properties
                           jdk: '', // Optional, specify JDK if needed
                           results: [[path: 'target/allure-results']] // Specify the directory for Allure results

                       ])
            }
        }

         stage('Print current directory and files 1') {
                         steps{
                             script {
                                 bat 'echo Current working directory: %cd%' // Print current directory
                                 bat 'dir target' // List files in the current directory
                               }
                         }
                   }

        stage('Docker Login') {
                    steps {
                        script {
                           bat 'docker login -u %DOCKER_USERNAME% -p %DOCKER_PASSWORD%'
                        }
                    }
                }

        stage('Print current directory and files 2') {
                 steps{
                     script {
                         bat 'echo Current working directory: %cd%' // Print current directory
                         bat 'dir target' // List files in the current directory
                       }
                 }
           }
         stage('Build Docker Image') {
             steps {
                 script {

                  // Print the target directory contents for debugging
                             bat 'dir target'

                     // Debug: Check JAR file existence using bat
                               bat """
                                   if exist "target\\RestAssuredAssignment-1.0-SNAPSHOT.jar" (
                                       echo JAR file exists, proceeding with Docker build.
                                   ) else (
                                       echo JAR file not found. Build failed!
                                       exit 1
                                   )
                               """


                         echo "Building Docker image with tag: ${IMAGE_TAG}"
                         // Use IMAGE_TAG directly for the tag
                         bat "docker build -t ${IMAGE_NAME}:${IMAGE_TAG} ."
                        }
                    }
                }

                stage('Push Docker Image') {
                    steps {
                        script {
                                    echo "Pushing Docker image: ${IMAGE_NAME}:${IMAGE_TAG}"
                                    // Push the Docker image with the correct tag
                                    bat "docker push ${IMAGE_NAME}:${IMAGE_TAG}"
                        }
                    }
                }
            }

    post {
        always {
           echo 'Cleaning up...'
           // Example: Clean up temporary directories, logs, etc., but leave `target` intact.
           //bat 'del /q /s *.tmp'  // Deletes .tmp files but not the JAR or target folder
        }

        success {
            // This block runs only if the build is successful
            echo 'Build completed successfully!'
        }

        failure {
            // This block runs only if the build fails
            echo 'Build failed.'
        }
    }
}