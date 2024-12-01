import groovy.json.JsonSlurper

def call(String azureCredentials, String environment) {

    def commonUtils =  new utils.azureLogin()

    echo "Calling the Az login....."
    try {
        // You can call the static method directly from the AzLogin class
        commonUtils.azLogin(azureCredentials, environment)
    } catch (Exception e) {
        echo "Failed to perform Azure login: ${e.message}"
        currentBuild.result = 'FAILURE'  // Mark build as failed
    }
}








}
