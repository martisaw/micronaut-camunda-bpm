# micronaut-external-task-worker
You may use the following properties (typically in application.yml) to configure the Camunda integration.
TODO

## Currently not supported
TODO
maybe disable auto fetching?

## Get Started
TODO

## TopicSubscription
| Property                    | Default | Description                                                                  |
|-----------------------------|---------|------------------------------------------------------------------------------|
| topicName                   |         | Mandatory, The topic name the client subscribes to.                          |
| lockDuration                | 20000   | Lock duration in milliseconds to lock external tasks. Must be greater than zero. |
| variables                   |         | The name of the variables that are supposed to be retrieved.                 |
| localVariables              | false   | Whether or not variables from greater scope than the external task should be fetched. false means all variables visible in the scope of the external task will be fetched, true means only local variables (to the scope of the external task) will be fetched. |
| businessKey                 |         | A business key to filter for external tasks that are supposed to be fetched and locked. |
| processDefinitionId         |         | A process definition id to filter for external tasks that are supposed to be fetched and locked. |
| processDefinitionIdIn       |         | Process definition ids to filter for external tasks that are supposed to be fetched and locked. |
| processDefinitionKey        |         | A process definition key to filter for external tasks that are supposed to be fetched and locked. |
| processDefinitionKeyIn      |         | Process definition keys to filter for external tasks that are supposed to be fetched and locked. |
| processDefinitionVersionTag |         |                                                                              |
| withoutTenantId             | false   | Filter for external tasks without tenant.                                    |
| tenantIdIn                  |         | Tenant ids to filter for external tasks that are supposed to be fetched and locked. |
| includeExtensionProperties  | false   | Whether or not to include custom extension properties for fetched external tasks. true means all extensionProperties defined in the external task activity will be provided. false means custom extension properties are not available within the external-task-client |


## Custom Backoff Strategies
TODO

## Request Interceptors
TODO

## Configuration Properties

| Prefix                | Property         | Default               | Description                                       |
|-----------------------|------------------|-----------------------|---------------------------------------------------|
| external-client       | .base-url        |                       | Mandatory, Base url of the Camunda BPM Platform REST API. |
|                       | .worker-id       | Generated out of hostname + 128 Bit UUID | A custom worker id the Workflow Engine is aware of. |
|                       | .max-Tasks       | 10                    | Maximum amount of tasks that will be fetched with each request. |
|                       | .use-priority    | true                  | Specifies whether tasks shoud be fetched based on their priority or arbitrarily. |
|                       | .default-serialization-format | application/json | Specifies the serialization format that is used to serialize objects when no specific format is requested. |
|                       | .date-format     |                       | Specifies the date format to de-/serialize date variables. |
|                       | .async-response-timeout |                | Asynchronous response (long polling) is enabled if a timeout is given. Specifies the maximum waiting time for the response of fetched and locked external tasks. The response is performed immediately, if external tasks are available in the moment of the request. Unless a timeout is given, fetch and lock responses are synchronous. |
|                       | .lock-duration   | 20000 (milliseconds)  | Lock duration in milliseconds to lock external tasks. Must be greater than zero. This gets overridden by the lock duration configured on a topic subscription |
| TODO IS THIS USEFUL?  | .disable-auto-fetching | false           | Disables immediate fetching for external tasks after calling build to bootstrap the client. To start fetching ExternalTaskClient.start() must be called. |
|                       | .disable-backoff-strategy | false        | Disables the client-side backoff strategy. On invocation, the configuration option backoffStrategy is ignored. Please bear in mind that disabling the client-side backoff can lead to heavy load situations on engine side. To avoid this, please specify an appropriate async-response-timeout(long). |

## "Old way" of building a client
currently both ways are supported.