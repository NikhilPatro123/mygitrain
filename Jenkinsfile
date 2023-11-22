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
                 script {
                    sshagent(credentials: ['65aad684-984d-490a-b1b9-ca1c7a7be7c0']) {
                         sh 'ssh -o StrictHostKeyChecking=no vn19690@193.164.133.65 "cd /home/vn19690/nkp/deploy && git pull"'
                    }
                }

            }
        }


    }
}
