pipeline {
    agent any
    stages {
        stage('Download') {
            steps {
                echo "Download Code from Github"
            }
        }
        stage('Build') {
            steps {
                echo "Build the Application"
            }
        }
        stage('Archive') {
            steps {
                echo "Archive the Application Artifacts"
            }
        }
    }
}

// This Jenkinsfile defines a simple CI pipeline with three stages: Download, Build, and Archive.

pipeline {
    agent any
    tools {
        jdk 'java21'
        maven 'maven3' // Ensure Maven is installed and configured in Jenkins
    }
    stages {
        stage('Download') {
            steps {
                echo "Download Code from Github"
                git branch: 'main', url: 'https://github.com/bheesham-devops/maven-jenkins10.git'
            }
        }
        stage('Build') {
            steps {
                echo "Build the Application"
                sh 'mvn clean package'
            }
        }
        stage('Archive') {
            steps {
                echo "Archive the Application Artifacts"
                archiveArtifacts artifacts: '**/*.war', followSymlinks: false
            }
        }
        stage('Trigger Deploy Job') {
            steps {
                echo "Trigger Deploy Job"
               build wait: false, job: 'deploy-pipeline'
            }
        }
    }
}

// This updated Jenkinsfile includes Deploy to Tomcat Server from the artifacts generated in the build steps.

pipeline {
    agent any
    stages {
        stage('Copy Artifacts') {
            steps {
                echo "Download Artifacts from Build Pipeline"
                copyArtifacts filter: '**/*.war', fingerprintArtifacts: true, projectName: 'build-pipeline', selector: lastSuccessful()
            }
        }
        stage('Deploy to Tomcat Server') {
            steps {
                echo "Deploy the Application"
                deploy adapters: [tomcat9(alternativeDeploymentContext: '', credentialsId: 'tomcatcreds', path: '', url: 'http://34.31.164.49:8090/')], contextPath: null, war: '**/*.war'
            }
        }
    }
}