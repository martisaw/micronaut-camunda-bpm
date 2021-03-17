# micronaut-external-task-worker
You may use the following properties (typically in application.yml) to configure the Camunda integration.
TODO

## Currently not supported
TODO
maybe disable auto fetching?

## Get Started
TODO

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