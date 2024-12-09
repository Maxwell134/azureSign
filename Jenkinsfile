@Library('my-shared-library') _  // Import the shared library if needed

pipeline {
    agent any
        environment {
        PATH = "${env.PATH}:/opt/homebrew/bin"  // Add /opt/homebrew/bin to the PATH
        KUBECONFIG = "${WORKSPACE}/.kube/config"
        
        }
    stages {

        stage('PIP Builder') {
            steps {
                script {
                    pip_builder(requirements: 'requirements.txt', // Optional
                        script: 'pod_monitor.py' 
                    // Default is 'sample.py')
                    )
                }
            }
        }
        stage('Azure Login') {
            steps {
                script {
                    sh 'mkdir -p ${WORKSPACE}/.kube'
                    sh 'cp ~/.kube/config ${WORKSPACE}/.kube/config'     
                    sh "kubectl get pods"
                    sh "kubectl port-forward svc/tommy-myrelease1 80:80 &"
                    sh "curl localhost:80"
                    // try {
                    //     // Call the function directly from the utils package
                        
                    //     aksLogin('azure-credentials-id', 'production')
                    // } catch (Exception e) {
                    //     echo "Failed to perform Azure login: ${e.message}"
                    //     currentBuild.result = 'FAILURE'  // Mark the build as failed
                    // }
                }
            }
        }
        stage('Remove kubeconfig') {
            steps {
                script {
                    sh 'pip3 install -r requirements.txt'
                    sh 'python3 pod_monitor.py'
                    sh "rm -rf ${WORKSPACE}/.kube/config"
                    sh "ls ${WORKSPACE}"
                }
            }
        }
    }
}
