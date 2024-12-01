@Library('my-shared-library') _  // Import the shared library if needed

pipeline {
    agent any

    stages {
        stage('Azure Login') {
            steps {
                script {
                    try {
                        // Call the function directly from the utils package
                        def commonUtils = new utils.AzureLogin()
                        commonUtils.azLogin('azure-credentials-id', 'production')
                    } catch (Exception e) {
                        echo "Failed to perform Azure login: ${e.message}"
                        currentBuild.result = 'FAILURE'  // Mark the build as failed
                    }
                }
            }
        }
    }
}
