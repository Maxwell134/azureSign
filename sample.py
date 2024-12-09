from kubernetes import config, client
from kubernetes.client.exceptions import ApiException

# Load kube config
config.load_kube_config()

def extract_pod_details(namespace='default'):
    try:
        v1 = client.CoreV1Api()
        pods = v1.list_namespaced_pod(namespace=namespace)
        pod_details = []

        # Iterate over the pods
        for pod in pods.items:
            name = pod.metadata.name
            container_statuses = pod.status.container_statuses
            failure_reason = None
            container_name = None
            for container_status in container_statuses:

                # Check if the container is in the waiting state
                if container_status.state.waiting:
                    failure_reason = container_status.state.waiting.reason
                    container_name = container_status.name

                # Check if the container is terminated
                elif container_status.state.terminated:
                    failure_reason = container_status.state.terminated.reason
                    container_name = container_status.name

                elif container_status.state.running:
                    container_name = container_status.name

                # Append the pod details to the list
            pod_details.append({
                            'pod_name': name,
                            'Failure_reason': failure_reason,
                            'container_name': container_name
                     })
        return pod_details

    except ApiException as e:
        print("Error occurred", e)
        return []


def extract_pod_logs(namespace='default'):
    try:
        v1 = client.CoreV1Api()
        output = extract_pod_details(namespace)

        logs = {}

        # Fetch logs for each container in each pod
        for details in output:
            pod_name = details['pod_name']
            container_name = details['container_name']
            failure_reason = details['Failure_reason']

            try:
                # Get logs for the specific container
                container_logs = v1.read_namespaced_pod_log(namespace=namespace, name=pod_name,
                                                            container=container_name)
                logs[pod_name] = {'container': container_name, 'failure_reason': failure_reason, 'logs': container_logs[:300]}
            except ApiException as e:
                logs[pod_name] = {'container': container_name, 'failure_reason': failure_reason,
                                  'logs': f"Error fetching logs: {e}"}

        return logs

    except ApiException as e:
        print("Error occurred", e)
        return {}


if __name__ == '__main__':
    output = extract_pod_logs()

    # Print the logs and failure reasons for each pod
    for pod_name, pod_data in output.items():
        print(f"POD_details: {pod_name}")
        print(f"Container: {pod_data['container']}")
        print(f"Failure reason: {pod_data['failure_reason']}")
        print(f"Logs: {pod_data['logs']}")
        print("***************************")
