@Library('my-shared-library') _  // Import the shared library if needed

pipeline {
    agent any
        environment {
        PATH = "${env.PATH}:/opt/homebrew/bin"  // Add /opt/homebrew/bin to the PATH
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
