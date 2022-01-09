# Smart Home Gesture Control
The application detects user's gestures to classify them and control smart home accordingly 

## Project Structure
```
Project
│   main.py
│   handshape_feature_extractor.py
│   frameextractor.py
│   cnn_model.h5
│   results.csv
│   README.md
│   Report.pdf
│ 
└───test
│    │   Gesture1.mp4
│    │   Gesture2.mp4
│    │   ... 
│ 
└───traindata
│    │   Gesture1.mp4
│    │   Gesture2.mp4
│    │   ... 
│
└───train_frames
│    │   frame1.png
│    │   frame2.png
│    │   .... 
│
└───test_frames
│    │   frame1.png
│    │   frame2.png
│    │   ...

```
## Initial Setup
Follow the steps below to setup the application:
1. Download the source code of the application, cnn_model file and place them in the project path.
2. Capture a video for each smart gesture for testing and save them in the directory: "./test".
3. Capture three training videos for each smart gesture for training and save them in the directory: "./traindata".

## How to Run
Follow the steps given below to run the application and test the accuracy of the application:
1. Run the application using the command:
```
python main.py
```
2. The indices of each gesture type is written in rows corresponding to the test videos after comparison done by the application in the file: "Results.csv".

## Author
Name: Sundaravadivel Chandrasekaran Ponmudi
