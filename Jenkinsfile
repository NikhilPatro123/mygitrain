pipeline {
    agent any
    tools {
        // Install the Maven version configured as "M3" and add it to the path.
        maven "maven"
    }

    environment {
        TOMCAT_HOME = '/opt/tomcat/apache-tomcat-9.0.83/'
    
    }

    stages {
        stage('Checkout') {
            steps {
                // Checkout your source code from the repository
                checkout scm
            }
        }

        stage('Build') {
            steps {
                // Build your Maven project
                sh "${MAVEN_HOME}/bin/mvn clean package"
            }
        }

        stage('Deploy to Tomcat') {
            steps {
                // Copy the WAR file to the Tomcat webapps directory
                sh "cp /var/lib/jenkins/workspace/tomcat-deploy-25/target/stockxbid-email-0.0.1-SNAPSHOT.jar ${TOMCAT_HOME}/webapps/"
            }
        }

        stage('Restart Tomcat') {
            steps {
                // Restart Tomcat (adjust the command based on your Tomcat setup)
                sh "${TOMCAT_HOME}/bin/shutdown.sh"
                sh "${TOMCAT_HOME}/bin/startup.sh"
            }
        }
    }

    post {
        success {
            echo 'Deployment successful!'
        }
        failure {
            echo 'Deployment failed!'
        }
    }
}
