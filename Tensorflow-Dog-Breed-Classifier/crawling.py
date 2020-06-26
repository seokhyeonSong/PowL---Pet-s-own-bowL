from bs4 import BeautifulSoup as bs
from urllib.request import urlretrieve
from selenium import webdriver
import argparse
import os

FLAGS = None
parser = argparse.ArgumentParser()
parser.add_argument(
    '--num_image',
    type=int,
    default='50',
    help='number of crawling images'
)
FLAGS, unparsed = parser.parse_known_args()

filelist = os.listdir("./dog_images/")
for i in range(len(filelist)):
    if(i<14):
        continue;
    else:
        search = filelist[i]
        driver = webdriver.Chrome("C:\chromedriver.exe")
        url = "https://www.google.com/search?q=" + search + "&source=lnms&tbm=isch&sa=X&ved=2ahUKEwjo-IbthprqAhVxJaYKHWqKBQwQ_AUoAXoECBcQAw&biw=1536&bih=722"
        driver.get(url)
        for j in range(FLAGS.num_image):
            driver.execute_script("window.scrollBy(0,10000)")
        html = driver.page_source
        soup =bs(html)

        img = soup.select('.rg_i.Q4LuWd')
        n=1
        imgurl = []
        for j in img:
            try:
                imgurl.append(j.attrs["src"])
            except KeyError:
                imgurl.append(j.attrs["data-src"])
        """
        for j in imgurl:
            urlretrieve(j,"./dog_images/"+filelist[i]+"/"+search+str(n)+".jpg")
            n+=1
            print(j,"picture"+search+str(n)+".jpg")
        """

        driver.close()
