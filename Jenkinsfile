@Library(['my-shared-library']) _
pipeline {
    agent any

    environment {
        AZURE_CREDENTIALS = credentials('azure-credentials-id') // Jenkins credentials ID
    }

    stages {
        stage('Azure Login') {
            steps {
                echo "Hello world"
                // script {
                    // Pass 'dev' as the environment parameter
                    // azureLogin(AZURE_CREDENTIALS, 'dev')  // You can replace 'dev' with other environments (like 'prod', etc.)
                // }
            }
        }
    }
}
