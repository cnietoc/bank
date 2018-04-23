### How would you improve your solution? What would be the next steps?
There are some issues pending to use the app:
- Consider the case of that an Account doesn't exist (using optional for example).
- Use a real implementation of the AccountRepository using the model and a persistence layer.
- Add some test cases, like the case of error because the amount is so high or the integration test with the error cases.
### After implementation, is there any design decision that you would have done different?
- Maybe the model is so complex for your use case.
### How would you adapt your solution if transfers are not instantaneous?
- Adding to a queue for example and processing it on other thread, for example.
### Observations
[Lombok Library](https://projectlombok.org/) is used in this project, then it's required to install the plugin to build the project using an IDE.