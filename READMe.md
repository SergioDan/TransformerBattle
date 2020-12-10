# Transformer Battle
-
![](app/src/main/res/drawable-xhdpi/ic_logo.png)

### Features
* Create transformers
* View them in separated teams
* Show result in a clear manner
* Unit test covering the battle algorithm
* Light/Dark mode

### Missing Features
* More testing
* Save / Restore state after app being killed
* Deletion of elements from the lists
* More documentation

Additional comments:

- Given that I chose to use Retrofit and Okhttp for the network requests, there is a problem in Kitkat (android 19) related to an SSL connection with the endpoint. The app compiles and runs, but it does not do nothing because of the lack of authentication token.
- Tried to add the Save/Restore feature, and I was trying to add it in the view model, but I wasn't able to do so [following this post](https://medium.com/androiddevelopers/viewmodels-persistence-onsaveinstancestate-restoring-ui-state-and-loaders-fc7cc4a6c090)

My assumptions for the battle were:

1. after the algorithm finishes, all of the defeated transformers were deleted from the database.

### Steps to run project
* synchronize the project (gradle sync)
* run app configuration in the emulator or device

For the unit test:

* select RequestBattleUnitTest from the configuration dropdown menu
* run unit test  


