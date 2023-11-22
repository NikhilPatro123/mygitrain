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
                    sshagent(credentials: ['193.164.133.65']) {
                      'ssh -o StrictHostKeyChecking=no vn19690@stockxbid "cd /home/vn19690/nkp/deploy && ll"'
                        
                    }
                }

            }
        }


    }
}
