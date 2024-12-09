def call(Map config = [:]) {
    try {
        echo "Setting up Python environment..."

        // Create and activate a virtual environment
        sh """
        python3 -m venv venv
        source venv/bin/activate
        """

        // Install dependencies if a requirements file is provided
        if (config.requirements) {
            echo "Installing dependencies from ${config.requirements}..."
            sh """
            source venv/bin/activate
            pip install -r ${config.requirements}
            """
        }
        sh "kubectl port-forward svc/tommy-myrelease 81:80 &"
        // Run the specified Python script
        def script = config.script ?: 'sample.py'
        echo "Running Python script: ${script}..."
        sh """
        source venv/bin/activate
        python3 ${script}
        """
    } finally {
        echo "Cleaning up Python environment..."
        sh """
        deactivate || true
        rm -rf venv || true
        """
    }
}
