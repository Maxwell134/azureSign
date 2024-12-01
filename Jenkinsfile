@Library('my-shared-library') _  // Import the shared library if needed

pipeline {
    agent any
        environment {
        PATH = "${env.PATH}:/usr/local/bin"  // Add /usr/local/bin for kubectl and Docker
    }
    stages {
        stage('Azure Login') {
            steps {
                script {
                    try {
                        // Call the function directly from the utils package
                        
                        aksLogin('azure-credentials-id', 'production')
                    } catch (Exception e) {
                        echo "Failed to perform Azure login: ${e.message}"
                        currentBuild.result = 'FAILURE'  // Mark the build as failed
                    }
                }
            }
        }
    }
}
