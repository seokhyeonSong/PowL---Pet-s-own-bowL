# PowL-Pets-own-bowL
Graduation project
# Notice
At first, we were tried to make it with raspberry pi.<br>
But we couldn't do it for some reason.<br>
The python program will replace the raspberry pi to show flow of our project.<br>
We hope that you will take that into consideration.<br>
Thanks

---
You have to download python code at [here](https://drive.google.com/file/d/1HaZm8umBth1hv2_463ssejWnQnLsyWlW/view?usp=sharing)<br>
save them at the same directory of this repository<br>

---
**Python**
---
You have to change your flow.py 's IP address code to your current pc's IP address<br>
flow.py Line 12, HOST ="your PC ip address"

**Android**
---
Also, you have to change your com.powl.graduation.ui.gallery.java and com.powl.graduation.enroll.java's IP address code to your current pc's IP address<br>
com.powl.graduation.ui.gallery.java Line 64, String ip ="your PC ip address";
com.powl.graduation.enroll.java Line 22, String ip="your PC ip address";


# Presentation video
[Youtube_video](https://youtu.be/JyCRdHj9BLg)

# Requirements

```
tensorflow = 1.13.1
opencv-python = 4.1.2
PIL = 7.1.2
keras = 2.2.4
tqdm = 4.46.1
beautifulsoup4 = 4.9.1
selenium = 3.141.0
tensorboard
```

# Datasets
Basic datasets are from [here](http://vision.stanford.edu/aditya86/ImageNetDogs/)<br>
And Added some crawled data<br>
There are 16 kinds of species<br>
And 6160 pictures in total<br>

# Getting started

## Installation

Clone the git repository. <br>
We will call the cloned repository/python/Tensorflow-Dog-Breed-Classifier/ as ***$home***<br>

    git clone https://github.com/seokhyeonSong/PowL-Pets_own_bowL.git
    cd PowL-Pets_own_bowL.git

## Crawling (optional)
**This can be skipped**<br>
To make dataset more rich, you can crawl some data from google by this code<br>
Crawled data will be at /dog_images/each_folder<br>
You have to distinguish some garbage images from dataset<br>
num_images are used to set the number of crawling image<br>
default is 50<br>

    python crawling.py [--num_images number_of_images]


## Retraining (optional)
**This can be skipped**<br>
To retrain the features of dogs, you can use this code<br>

    python retraining.py --image_dir /dog_images

This code is came from [here](https://github.com/AthulDilip/Tensorflow-Dog-Breed-Classifier)

## Run

You have to download our application to register<br>
Or you can just enter your dog's breed as insert value with args<br>
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

You can register your dog with application<br>
Your phone and notebook should be in same Wi-Fi<br>

> To register your dog

1. run flow.py and select server on mode
2. send your dog's picture by album or take a picutre
3. You will receive your dog's breed prediction from server
you can choose your dog's breed
4. Then your registered dog will be added to breed.txt

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
for train result :  `tensorboard --log_dir=$home/../tmp/retrain_logs/train`<br>
 for validation result : `tensorboard --log_dir=$home/../tmp/retrain_logs/validation`
3. You can see the result at [here](http://localhost:6006 )

# Citation
    author : @AthulDilip, @sabeersulaiman
    title : Tensorflow Dog Breed Classifier
    publisher : {GitHub}
    journal : {GitHub repository}
    howpublished : https://github.com/AthulDilip/Tensorflow-Dog-Breed-Classifier#tensorflow-dog-breed-classifier
    
