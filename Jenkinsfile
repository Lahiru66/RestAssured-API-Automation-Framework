pipeline {
    agent any

    environment {
        JAVA_HOME = 'C:\\Program Files\\Java\\openjdk-17_windows-x64_bin\\jdk-17'
        DOCKER_USERNAME = 'techguy6'
        DOCKER_PASSWORD = 'dockercool'
        IMAGE_NAME = 'techguy6/rest-assured'
    }

    stages {
        stage('Checkout Code') {
            steps {
                git branch: 'main', credentialsId: 'github-credentials', url: 'https://github.com/Lahiru66/RestAssured-API-Automation-Framework.git'
            }
        }

        stage('Build') {
            steps {
                script {
                    bat 'mvn clean install'
                }
            }
        }


        stage('Run Tests') {
            steps {
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

        stage('Docker Login') {
                    steps {
                        script {
                           bat 'docker login -u %DOCKER_USERNAME% -p %DOCKER_PASSWORD%'
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
                  // Ensure the IMAGE_TAG resolves correctly in the batch script
                       def imageTag = env.BUILD_NUMBER // Use Jenkins' environment variable
                       echo "Building Docker image with tag: ${imageTag}"

                       // Build the Docker image
                       bat """
                           docker build -t ${IMAGE_NAME}:${imageTag} .
                       """
                       // Set IMAGE_TAG globally for later stages
                       env.IMAGE_TAG = imageTag

                        }
                    }
                }

        stage('Push Docker Image') {
             steps {
                 script {
                      echo "Pushing Docker image: ${IMAGE_NAME}:${env.IMAGE_TAG}"

                      // Push the Docker image using the globally set IMAGE_TAG
                      bat """
                          docker push ${IMAGE_NAME}:${env.IMAGE_TAG}
                      """
                        }
                    }
                }
            }

    post {
        always {
          emailext(
                      subject: "Build Status: ${currentBuild.currentResult}",
                      body: """
                          Build Status: ${currentBuild.currentResult}
                          Project: ${env.JOB_NAME}
                          Build Number: ${env.BUILD_NUMBER}
                          Console Output: ${env.BUILD_URL}/console
                      """,
                      to: "lahirukasun666@gmail.com"
                  )
        }

    }
}