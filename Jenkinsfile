pipeline {
    agent any

    tools {
        // Install the Maven version configured as "M3" and add it to the path.
        maven "maven"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
         stage('Build') {
            steps {
                sh "mvn clean compile"

            }
        }
        stage('test') {
            steps {
                sh "mvn test"

            }
        }
        stage('deploy') {
            steps {
                sh 'ssh-keygen'

            }
        }


    }
}
