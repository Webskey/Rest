pipeline {
    agent any
    stages {
        
        stage('Clone sources') {
            steps{
                git url: 'https://github.com/webskey/rest.git'
            }
        }
        
        stage('Build') { 
            steps {
                bat 'mvn package' 
            }
        } 
        
        stage('Test') { 
            steps {
                bat 'mvn test' 
            }
        }
    }
    
    post {
        always {
            junit 'target/surefire-reports/*.xml' 
            archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
        }
    }
}