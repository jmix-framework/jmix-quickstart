Task specification
=====================

Task BDD test
----------------

We will create a new task, assign it to a user, add it to the project and then check if that the task was created
correctly using the Masquerade UI testing library.

* Open URL of an application
* Log in as user with "dev1" username and "dev1" password
* Open the task browser
* Open the task editor
* Fill form fields with following values: name is "BDD testing", start date is "03/11/2021", estimated efforts are "10", project is "Jmix Education"
* Save new task
* Make sure the new task with "BDD testing" name is added to tasks table
