# PowL---Pet-s-own-bowL
Graduation project
# Notice
At first, we were tried to make it with raspberry pi.&nbsp;
But we couldn't do it for some reason.&nbsp;
The python program will replace the raspberry pi to show flow of our project.&nbsp;
We hope that you will take that into consideration.&nbsp;
Thanks
# Requirements

tensorflow = 1.13.1&nbsp;
opencv-python = 4.1.2&nbsp;
PIL = 7.1.2&nbsp;
keras = 2.2.4&nbsp;
tqdm = 4.46.1&nbsp;
beautifulsoup4 = 4.9.1&nbsp;
selenium = 3.141.0&nbsp;
tensorboard&nbsp;

# Getting started

## Installation

Clone the git repository. &nbsp;
We will call the cloned repository as ***$home***

    git clone https://github.com/seokhyeonSong/PowL-Pets_own_bowL.git
    cd PowL-Pets_own_bowL.git

## Crawling (optional)
**This can be skipped**
To make dataset more rich, you can crawl some data from google by this code&nbsp;
Crawled data will be at /dog_images/each_folder&nbsp;
You have to distinguish some garbage images from dataset&nbsp;
num_images are used to set the number of crawling image&nbsp;
default is 50&nbsp;

    python crawling.py [--num_images number_of_images]


## Retraining (optional)
This can be skipped&nbsp;
to retrain the features of dogs, you can use this code&nbsp;

    python retraining.py --image_dir /dog_images

This code is came from [here](https://github.com/AthulDilip/Tensorflow-Dog-Breed-Classifier)

## Run

You have to download our application to register&nbsp; 
Or you can just enter your dog's breed as insert value with args&nbsp;
|Dog's breed|insert value|Dog's breed|insert value|
|--|--|--|--|
|Beagle|beagle|Border Collie|border collie|
|Chihuahua|chihuahua|Cocker Spaniel|cocker spaniel|
|Doberman|doberman|French Bulldog|french bulldog|
|Golden Retriever|golden retriever|Labrador Retriever|labrador retriever|
|Maltese|maltese dog|Papillon|papillon|
|Pekinese|pekinese|Pomeranian|pomeranian|
|Schnauzer|schnauzer|Shih Tzu|shih tzu|
|Poddles|standard poodle|Yorkshire Terrier|yourkshire terrier|

    python flow.py [--breed breed_insert_value]

You can register your dog with application&nbsp;
Your phone and notebook should be in same Wi-Fi&nbsp;

> To register your dog

1. run flow.py and select register mode
2. send your dog's picture by album or take a picutre
3. You will receive your dog's breed prediction from server
you can choose your dog's breed

> To run PowL
1. Set your labtop front of dog's bowl
Before you run PowL mode, the camera's scene should be static
2. Run PowL
3. Then program will run, and check whether you've selected breed it or not



## Evaluate
*notice*
-
This codes are from [here](https://github.com/AthulDilip/Tensorflow-Dog-Breed-Classifier)

Run
-
You can see the training and validation result with tensorboard
1. Enter [here](http://localhost:6006 )
2. Run this code
for train result :  `tensorboard --log_dir=$home/../tmp/retrain_logs/train` 
 for validation result : 
 `tensorboard --log_dir=$home/../tmp/retrain_logs/validation`
3. You can see the result at [here](http://localhost:6006 )
