"""Cuts the bust of Hermes out of its photograph (hermes-bust-smk-kas1636.jpg) into hermes-bust.png, the source of
the application icon (see gen_icons.py). GrabCut (OpenCV) with seeds for what is surely bust and surely wall, in the
coordinates of a 500 pixel wide view of the photograph. Needs: pip install opencv-python-headless numpy.
"""
import os

import cv2
import numpy as np

HERE = os.path.dirname(os.path.abspath(__file__))
img=cv2.imread(os.path.join(HERE, 'hermes-bust-smk-kas1636.jpg')); h,w=img.shape[:2]; k=w/500.0
def P(x,y): return (int(x*k),int(y*k))
mask=np.full((h,w),cv2.GC_PR_BGD,np.uint8)
cv2.fillPoly(mask,[np.array([P(10,300),P(30,110),P(150,25),P(330,25),P(470,190),P(480,230),P(400,330),P(400,690),P(130,690),P(15,560)])],cv2.GC_PR_FGD)
cv2.ellipse(mask,P(245,170),(int(190*k),int(120*k)),0,0,360,cv2.GC_FGD,-1)
cv2.rectangle(mask,P(140,250),P(340,540),cv2.GC_FGD,-1)
cv2.rectangle(mask,P(200,540),P(330,660),cv2.GC_FGD,-1)
cv2.fillPoly(mask,[np.array([P(40,540),P(200,520),P(200,600),P(60,580)])],cv2.GC_FGD)
cv2.rectangle(mask,(0,0),(w-1,h-1),cv2.GC_BGD,int(6*k))
cv2.rectangle(mask,P(440,340),P(500,700),cv2.GC_BGD,-1)
cv2.fillPoly(mask,[np.array([P(368,330),P(440,330),P(440,520),P(398,545),P(362,470)])],cv2.GC_BGD)
cv2.rectangle(mask,P(0,340),P(70,500),cv2.GC_BGD,-1)
cv2.rectangle(mask,P(0,0),P(500,12),cv2.GC_BGD,-1)
cv2.rectangle(mask,P(0,710),P(500,990),cv2.GC_BGD,-1)
bg=np.zeros((1,65)); fg=np.zeros((1,65))
cv2.grabCut(img,mask,None,bg,fg,10,cv2.GC_INIT_WITH_MASK)
m=np.where((mask==1)|(mask==3),255,0).astype('uint8')
n,lab,st,_=cv2.connectedComponentsWithStats(m); kk=1+np.argmax(st[1:,cv2.CC_STAT_AREA]); m=np.where(lab==kk,255,0).astype('uint8')
inv=255-m; n2,lab2,st2,_=cv2.connectedComponentsWithStats(inv)
for i in range(1,n2):
    x,y,ww,hh,a=st2[i]
    if x>0 and y>0 and x+ww<w and y+hh<h: m[lab2==i]=255
m=cv2.morphologyEx(m,cv2.MORPH_OPEN,np.ones((5,5),np.uint8)); m=cv2.morphologyEx(m,cv2.MORPH_CLOSE,np.ones((11,11),np.uint8))
m=cv2.GaussianBlur(m,(5,5),0)
ys,xs=np.where(m>8); x0,x1,y0,y1=xs.min(),xs.max(),ys.min(),ys.max()
rgba=cv2.cvtColor(img,cv2.COLOR_BGR2BGRA); rgba[:,:,3]=m
cv2.imwrite(os.path.join(HERE, 'hermes-bust.png'), rgba[y0:y1+1, x0:x1+1])
