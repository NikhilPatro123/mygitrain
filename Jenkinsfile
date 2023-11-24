pipeline {
    agent any
     tools {
        // Install the Maven version configured as "M3" and add it to the path.
        maven "maven"
    }


    environment {
        TOMCAT_REMOTE_HOST = '193.164.133.65' // Replace with the actual IP or hostname
        TOMCAT_REMOTE_PORT = '8443' // Replace with the SSH port of the remote server
        TOMCAT_REMOTE_USER = 'stockxbid' // Replace with the SSH user of the remote server
        TOMCAT_REMOTE_PASSWORD = credentials('193.164.133.65') // Use Jenkins credentials to securely store SSH private key or password
        TOMCAT_REMOTE_PATH = '/opt/tomcat/tomcat857' // Replace with the actual path to Tomcat on the remote server
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Deploy to Tomcat') {
            steps {
                script {
                    // Copy the WAR file to the remote Tomcat webapps directory
                    sshagent(credentials: ['jenkins-ssh-key']) {
                        sh "scp -P ${TOMCAT_REMOTE_PORT} target/your-app.war ${TOMCAT_REMOTE_USER}@${TOMCAT_REMOTE_HOST}:${TOMCAT_REMOTE_PATH}/webapps/"
                    }
                }
            }
        }

        stage('Restart Tomcat') {
            steps {
                script {
                    // Restart Tomcat on the remote server (adjust the command based on your Tomcat setup)
                    sshagent(credentials: ['jenkins-ssh-key']) {
                        sh "ssh -p ${TOMCAT_REMOTE_PORT} ${TOMCAT_REMOTE_USER}@${TOMCAT_REMOTE_HOST} '${TOMCAT_REMOTE_PATH}/bin/shutdown.sh && sleep 5 && ${TOMCAT_REMOTE_PATH}/bin/startup.sh'"
                    }
                }
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
