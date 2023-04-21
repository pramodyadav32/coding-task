# Coding Task: Pose estimation on Android

Welcome to our Android coding task.

The goal of this task is for you to produce some code in your own time (no live coding), so that
we have some material to discuss in our conversation afterwards.

Overall, you shouldn't have to spend more than 2 hours on the overall task. If you get stuck,
document your progress and just move forward.

## Resources and allowed tools
The tasks are designed to be completed by just writing code, no additional packages are needed.
However, there are no restrictions on what you can use to finish the tasks.

## Task overview
You will find the starting structure of an Android app project. Open the project in Android Studio
to get started.

The tasks are centered around keypoint detection and basic image rendering, which is a simplified
representation of what we typically work on in our real product.

You don't need any understanding of keypose estimation to finish this task. But just in case, here
is the main resource if you want to look at the keypoint layout and library:
https://developers.google.com/ml-kit/vision/pose-detection

## Task 1 
Look at the function `drawPose(...)`. It currently only draws a single line from shoulder to
shoulder. Implement this function so that the full pose of keypoints is drawn.

## Task 2
The button with the id `btnNewImage` currently has no functionality. Change it, so that the image
is swapped out with a random image from the assets folder. There are 8 jpg images in total, one of
them should be picked randomly whenever this button is clicked.

Make sure that your the function `drawPose(...)` is still called correctly so that the keypoints
are also drawn on the new image.

## Task 3
Implement a new function `handIsRaised(pose: Pose): Boolean`. It should return `true` when the
person in the image has raised a hand and `false` otherwise. What exact rule you come up with is
up to you. For inspiration check out the images in `src/main/assets` which show examples both for 
raised and non-raised arms.

## Task 4
With the previous functions implemented, add the following functionality: When an image is loaded,
a message is shown to the user: "Hello!" if a raised hand has been detected, "Bye!" otherwise.

What mechanism you use to show a message is up to you. Adding a UI element is fine, using a toast
is also fine.

## Task 5
Add a new button labeled "Mirror". When the user clicks on the button, the app navigates to a
second screen. On this screen, the current image is displayed in a mirrored version, including the
keypoints overlay (mirroring = right and left side are flipped).

There should be a way to navigate back to the original screen (with a back button or by tapping
"back" on the android system buttons).

## Task 6
The last one is a non-coding task. Look over the finished code and overall example app. 
What suggestions do you have to improve the code structure?
What would you change if this was the code base for an actual product and you were the main developer?
Write down your thoughts as a comment block in the `MainActivity.kt` (top of the file).
