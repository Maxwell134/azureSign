package utils

import groovy.json.JsonSlurper

def azLogin(String azureCredentials, String environment) {

    try {
        echo "Attempting to login to Azure account for environment: ${environment}..."

        // Retrieve credentials from Jenkins' credentials store
        withCredentials([string(credentialsId: azureCredentials, variable: 'AZURE_CREDENTIALS')]) {

            // Parse the JSON stored in AZURE_CREDENTIALS
            def credentialJsonobj = new JsonSlurper().parseText(AZURE_CREDENTIALS)
            def USERNAME = credentialJsonobj['username']
            def PASSWORD = credentialJsonobj['password']
            def TENANT_ID = credentialJsonobj['tenant_id']

            // Check if all required parameters are provided
            if (USERNAME?.trim() && PASSWORD?.trim() && TENANT_ID?.trim()) {
                echo "Logging in with provided credentials..."

                // Perform Azure login with the service principal credentials
                sh "az login --service-principal --username $USERNAME --password $PASSWORD --tenant $TENANT_ID"
                echo "Login successful."
            } else {
                echo "Missing one or more required parameters: username, password, or tenant_id. Please check and try again."
                defaultLogin()  // Fallback login attempt
            }
        }
    } catch (Exception e) {
        // Log the error message and the exception details
        echo "An error occurred during Azure login: ${e.message}"
        currentBuild.result = 'FAILURE'  // Mark the build as failed if an exception occurs
        defaultLogin()  // Fallback login attempt
    }
}

def defaultLogin() {
    try {
        echo "Attempting default Azure login..."

        // Perform a default login (you can adjust this command as needed)
        sh(script: 'az login', returnStatus: true)

        echo "Default login successful."
        return true  // Indicate the success of default login

    } catch (Exception e) {
        echo "Default login failed: ${e.message}"
        return false  // Indicate failure of default login
    }
}
