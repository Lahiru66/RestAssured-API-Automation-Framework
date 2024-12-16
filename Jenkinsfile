pipeline {
    agent any  // This tells Jenkins to run the pipeline on any available agent (node)

    environment {
        // You can define environment variables here (e.g., for Docker, Java versions, etc.)
        JAVA_HOME = 'C:/Program Files/Java/openjdk-17_windows-x64_bin/jdk-17'
        DOCKER_USERNAME = 'techguy6'
        DOCKER_PASSWORD = 'dockercool'
        IMAGE_NAME = 'techguy6/rest assured'
        IMAGE_TAG = '${BUILD_NUMBER}'

    }

    stages {
        stage('Clone Repository') {
            steps {
                // Checkout your project from GitHub repository
                git url: 'https://github.com/Lahiru66/RestAssured-API-Automation-Framework.git'
            }
        }

        stage('Build') {
            steps {
                // Run Maven or Gradle to build the project
                script {
                    sh './mvn clean install' // Or use Gradle: './gradlew build'
                }
            }
        }

        stage('Run Tests') {
            steps {
                // Run your API tests (e.g., REST Assured tests)
                script {
                      // Run your tests, generating Allure results
                     sh 'mvn clean test -Dallure.results.directory=target/allure-results'
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
                            sh 'docker login -u $DOCKER_USERNAME -p $DOCKER_PASSWORD'
                        }
                    }
                }

         stage('Build Docker Image') {
                    steps {
                        script {
                            // Build the Docker image
                            sh "docker build -t $IMAGE_NAME:$IMAGE_TAG ."
                        }
                    }
                }

                stage('Push Docker Image') {
                    steps {
                        script {
                            // Push the Docker image to Docker Hub
                            sh "docker push $IMAGE_NAME:$IMAGE_TAG"
                        }
                    }
                }
    }

    post {
        always {
            // This block runs after every build (success or failure)
            echo 'Cleaning up...'
            // Clean up work, like removing temporary files, can be done here.
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


