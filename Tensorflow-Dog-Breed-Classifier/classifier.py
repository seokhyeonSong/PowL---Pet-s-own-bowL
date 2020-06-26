import tensorflow as tf 
import numpy as np
import sys
import numpy as np
from keras.applications.resnet50 import preprocess_input, decode_predictions, ResNet50
from keras.preprocessing import image
from tqdm import tqdm
old_v = tf.logging.get_verbosity()
tf.logging.set_verbosity(tf.logging.ERROR)
import os
os.environ['TF_CPP_MIN_LOG_LEVEL'] = '3'
ResNet50_model = ResNet50(weights='imagenet')

class Dogclassify:
    def __init__(self, path, breed):
        self.path = path
        self.breed = breed

    def path_to_tensor(self, path):
        img = image.load_img(path, target_size=(224, 224))
        x = image.img_to_array(img)
        return np.expand_dims(x, axis=0)

    def ResNet50_predict_labels(self, img_path):
        img = preprocess_input(self.path_to_tensor(img_path))
        return np.argmax(ResNet50_model.predict(img))

    def detect(self):
        img_path = self.path
        prediction = self.ResNet50_predict_labels(img_path)
        return ((prediction <= 268) & (prediction >= 151))

    def register(self):
        # Global variables
        image_path = self.path
        label_path = './output_labels.txt'
        model_path = './output_graph.pb'

        FLAGS = tf.flags.FLAGS
        FLAGS(sys.argv)

        image = tf.gfile.FastGFile(image_path, 'rb').read()
        labels = [line.rstrip() for line in tf.gfile.GFile("output_labels.txt")]

        with tf.gfile.FastGFile(model_path, 'rb') as f:
            graph_def = tf.GraphDef()
            graph_def.ParseFromString(f.read())
            tf.import_graph_def(graph_def, name='')

        with tf.Session() as sess:
            softmax_tensor = sess.graph.get_tensor_by_name('final_result:0')

            # predict
            predictions = sess.run(softmax_tensor, {'DecodeJpeg/contents:0': image})

            # Sort to show labels of first prediction in order of confidence
            top_k = predictions[0].argsort()[-len(predictions[0]):][::-1]

            human_string = [labels[top_k[0]],labels[top_k[1]],labels[top_k[2]],labels[top_k[3]],labels[top_k[4]]]
            return human_string
    def predict(self):
        #Global variables
        image_path = self.path
        label_path = './output_labels.txt'
        model_path = './output_graph.pb'

        FLAGS = tf.flags.FLAGS
        FLAGS(sys.argv)

        image = tf.gfile.FastGFile(image_path, 'rb').read()
        labels = [line.rstrip() for line in tf.gfile.GFile("output_labels.txt")]

        with tf.gfile.FastGFile(model_path, 'rb') as f:
            graph_def = tf.GraphDef()
            graph_def.ParseFromString(f.read())
            tf.import_graph_def(graph_def, name='')


        with tf.Session() as sess:
            softmax_tensor = sess.graph.get_tensor_by_name('final_result:0')
    
            #predict
            predictions = sess.run(softmax_tensor, {'DecodeJpeg/contents:0': image})
    
            #Sort to show labels of first prediction in order of confidence
            top_k = predictions[0].argsort()[-len(predictions[0]):][::-1]

            human_string = labels[top_k[0]]
            score = predictions[0][top_k[0]]


            if(self.breed==human_string):
                return True
            else:
                return False