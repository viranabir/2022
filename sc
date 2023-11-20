import threading
import os
from tkinter import *
from tkinter.ttk import *
from PIL import Image
from hikvisionapi import Client
import csv
import cv2
import time
from requests.exceptions import ConnectionError
import requests
import codecs
from openpyxl import load_workbook
import atexit
import shutil, sys  
import openpyxl
import pandas as pd
import tkinter as tk
import tkinter.font as tkFont
from tkinter import Button
from bidi.algorithm import get_display
from PIL import Image, ImageFont, ImageDraw , ImageTk,ImageOps
from time import strftime
import tkinter as tk
from tkinter import ttk
import unicodecsv as csv
from openpyxl import Workbook
from skimage import io, draw, exposure, transform, img_as_float, color
import matplotlib.pyplot as plt
import cv2
import xlrd
import csv
import time
import xlwt
import arabic_reshaper
from bidi.algorithm import get_display
import numpy as np
import pyglet#, os
from tkinter.filedialog import asksaveasfile
import arabic_reshaper
from sys import exit
import time
import base64
import os
import tkinter as tk
import tkinter.messagebox
import threading
import wmi

previous_number = 0

def encrypt_timer(timer_value):
    encrypted_value = base64.b64encode(str(timer_value * 12345917364872692463482367849236482307527364230745237481634785236482365482370640239748).encode()).decode()
    return encrypted_value

                # تابع رمزگشایی مقدار تایمر از روش Base64
def decrypt_timer(encrypted_value):
    decoded_value = base64.b64decode(encrypted_value).decode()
    return int(decoded_value) // 12345917364872692463482367849236482307527364230745237481634785236482365482370640239748

                # مسیر فایل تایمر
timer_file_path = "system."

                # مقدار تایمر
target_number = 311040000
current_number = 0

                # بررسی وجود فایل تایمر
if os.path.isfile(timer_file_path):
                    # خواندن مقدار تایمر از فایل تکست و رمزگشایی آن
    with open(timer_file_path, 'r') as file:
        encrypted_timer = file.read()
        current_number = decrypt_timer(encrypted_timer)
else:
    print("فایل تایمر وجود ندارد. برنامه متوقف می‌شود")
    exit()

                # تابع اجرای تایمر
def run_timer():
    global current_number
    while current_number < target_number:
                        # هر ثانیه یک واحد به متغیر تایمر افزوده و در فایل تکست ذخیره می‌شود
        current_number += 1
                        # رمزنگاری مقدار تایمر و ذخیره در فایل تکست
        encrypted_timer = encrypt_timer(current_number)
        with open(timer_file_path, 'w') as file:
            file.write(encrypted_timer)
                        # چاپ مقدار تایمر
        print("مقدار تایمر:", current_number)
                        # تاخیر یک ثانیه
        time.sleep(1)
    print("مقدار هدف به دست آمد. تایمر متوقف می‌شود.")
                    # ایجاد پنجره tkinter برای نمایش پیام
    root = tk.Tk()
    root.withdraw()  # مخفی کردن پنجره اصلی
    message = "اتمام مهلت نسخه آزمایشی با ما تماس بگیرید"
    message_box = tk.messagebox.showinfo("پایان آزمایش", message)
    root.mainloop()
timer_thread = threading.Thread(target=run_timer)
timer_thread.start()  # شروع تایمر در یک Thread جدید



def get_usb_serial_numbers():
    c = wmi.WMI()
    usb_devices = c.Win32_PnPEntity (Description="USB Mass Storage Device")
    serial_numbers = [device.PNPDeviceID for device in usb_devices]
    return serial_numbers

def main():
    serial_numbers = get_usb_serial_numbers()
    
    if not serial_numbers:
        print("هیچ فلش مموری‌ای یافت نشد.")
    else:
        print("لیست شماره‌های سریال فلش مموری‌ها:")
        for serial in serial_numbers:
            print(serial)
            if serial=='USB\\VID_346D&PID_5678\\5413661077187119742' :
            # if serial=='USB\\VID_325D&PID_6410\\9000265FAD96EA85' :
                print("ok")
                with open('bin/Load/option_1.csv', newline='',encoding="utf-8") as option1:
                    reader = csv.reader(option1)
                    option1 = list(reader)
                global ipcam1
                ipcam1='http://'+option1[0][0]
                global camUser1
                camUser1=option1[1][0]
                global camPass
                camPass1=option1[2][0]
                
                with open('bin/Load/option_2.csv', newline='',encoding="utf-8") as option2:
                    reader = csv.reader(option2)
                    option2 = list(reader)
                global ipcam2
                ipcam2='http://'+option2[0][0]
                global camUser2
                camUser2=option2[1][0]
                global camPass2
                camPass2=option2[2][0]
                print("G1")
                def task1():
                    while current_number < target_number:
                        print("UUUUUUUUUUUUUUUUUUUUUUUUUUUUUU")
                        if ipcam1!=ipcam2:
                            try:
                                start_time = time.time()
                                cam = Client(ipcam1, camUser1, camPass1, timeout=20)
                                response = cam.Streaming.channels[101].picture(method='get', type='opaque_data')
                                with open('output/screen1.jpg', 'wb') as f:
                                    for chunk in response.iter_content(chunk_size=1024):
                                        if chunk:
                                            f.write(chunk)
                                with open("tools/croping_image_1.csv",newline="",encoding="utf-8") as cropi2:
                                    reader = csv.reader(cropi2)
                                    cropi2 = list(reader)
                                x = int(cropi2[0][0])
                                y = int(cropi2[1][0])
                                z = int(cropi2[2][0])
                                d = int(cropi2[3][0])
                                im = Image.open('output/screen1.jpg')
                                im = im.crop((z,x,d,y))
                                im.save('output/screen1.jpg') # saves the image   
                                print("--- %s seconds ---" % (time.time() - start_time))
                            except requests.Timeout as err:
                                time.sleep(2)
                                print("requests.Timeout 1")
                            except ConnectionError :
                                time.sleep(2)
                                print("ConnectionError 1")
                            except OSError as error :  
                                time.sleep(2)
                                print("os error 1")
                            except Exception :
                                time.sleep(2)
                                print("Exception 1")
                print("G2")
                def task2():  
                    while current_number < target_number :
                        print("PPPPPPPPPPPPPPPPPPPP")
                        if ipcam1!=ipcam2:
                            try:
                                start_time = time.time()
                                cam = Client(ipcam2, camUser2, camPass2, timeout=20)
                                response = cam.Streaming.channels[101].picture(method='get', type='opaque_data')
                                with open('output/screen2.jpg', 'wb') as f:
                                    for chunk in response.iter_content(chunk_size=1024):
                                        if chunk:
                                            f.write(chunk)
                                with open("tools/croping_image_2.csv",newline="",encoding="utf-8") as cropi2:
                                    reader = csv.reader(cropi2)
                                    cropi2 = list(reader)
                                x = int(cropi2[0][0])
                                y = int(cropi2[1][0])
                                z = int(cropi2[2][0])
                                d = int(cropi2[3][0])
                                im = Image.open('output/screen2.jpg')
                                im = im.crop((z,x,d,y))
                                im.save('output/screen2.jpg') # saves the image   
                                print("--- %s seconds ---" % (time.time() - start_time))
                            except requests.Timeout as err:
                                time.sleep(2)
                                print("requests.Timeout 2")
                            except ConnectionError :
                                time.sleep(2)
                                print("ConnectionError 2")
                            except OSError as error :  
                                time.sleep(2)
                                print("os error 2")
                            except Exception :
                                time.sleep(2)
                                print("Exception 2")
                               
                def task3():
                        pyglet.font.add_file('tools/Far_Nazanin.ttf') 
                        global toc
                        toc=0
                        newWindowRuning=0
                        global userOption
                        global passOption
                        global userMoj
                        global passMoj
                        global userAdmin
                        global passAdmin
                        global Usb_KEY
                        Usb_KEY = 'Dejpol_Key'
                        from cryptography.fernet import Fernet
                        key = b'42rRfjeZHnohh6EpFuAfND5ZAUQYWc8e1A_pZUUUbR0='
                        fernet = Fernet(key)
                        with open('tools/unknown.csv', newline='') as passwordha:
                            reader = csv.reader(passwordha)
                            passwordhalist = list(reader)
                        userAdmin=passwordhalist[0][0]
                        passAdmin=passwordhalist[1][0]
                        userOption=passwordhalist[2][0]
                        passOption=passwordhalist[3][0]
                        userMoj=passwordhalist[4][0]
                        passMoj=passwordhalist[5][0]
                        print("G3") 
                        class App(tk.Tk): 
                                def __init__(self):
                                    super().__init__()
                                    self.title("سامانه کنترل تردد دژپل")
                                    self.iconbitmap('tools/logo.ico')
                                    width=1080
                                    height=800
                                    screenwidth = self.winfo_screenwidth()
                                    screenheight = self.winfo_screenheight()
                                    alignstr = '%dx%d+%d+%d' % (width, height, (screenwidth - width) / 1.025, (screenheight - height) / 40)
                                    self.geometry(alignstr)
                                    self.resizable(width=False, height=False)
                                    self.minsize(1080,800)
                                    my_label= ttk.Label(self)
                                    my_label.pack(pady=1)
                                    
                                    img_old=Image.open("tools/logo.png")
                                    img_resized = ImageOps.fit(img_old,(110,85), Image.ANTIALIAS)
                                    img = ImageTk.PhotoImage(img_resized)
                                    imagelabel = ttk.Label(self, image = img)
                                    imagelabel.image = img
                                    imagelabel.place(x=485,y=90,width=111,height=86)
                                    def time():
                                            string = strftime('%H:%M:%S %p')
                                            string = arabic_reshaper.reshape(string)
                                            lbl.config(text=string)
                                            lbl.after(1000, time)
                                    lbl = Label(font=('Far_Nazanin', 20, 'bold'),background='white',foreground='black')
                                                                                                    # ثبت پلاک به تفکیک خروج و ورود
                                    lbl.pack(anchor='center')
                                    time()
                                    if current_number < target_number :
                                        self.reload() 
                                        self.Button()
                                def Button(self) :
                                    GButton_839=tk.Button(self)
                                    GButton_839["bg"] = "#efefef"
                                    ft = tkFont.Font(family='Times',size=10)
                                    GButton_839["font"] = ft
                                    GButton_839["fg"] = "#000000"
                                    GButton_839["justify"] = "center"
                                    GButton_839["text"] = "تنظیمات دوربین دو"
                                    GButton_839.place(x=20,y=410,width=109,height=49)
                                    GButton_839["command"] = self.optionWindow_cam2
                            
                            
                                    GButton_438=tk.Button(self)
                                    GButton_438["bg"] = "#efefef"
                                    ft = tkFont.Font(family='Times',size=10)
                                    GButton_438["font"] = ft
                                    GButton_438["fg"] = "#000000"
                                    GButton_438["justify"] = "center"
                                    GButton_438["text"] = "دریافت تردد"
                                    GButton_438.place(x=490,y=370,width=109,height=49)
                                    GButton_438["command"] = self.load_xlsx
                                    
                                    
                                    GButton_CHP=tk.Button(self)
                                    GButton_CHP["bg"] = "#efefef"
                                    ft = tkFont.Font(family='Times',size=10)
                                    GButton_CHP["font"] = ft
                                    GButton_CHP["fg"] = "#000000"
                                    GButton_CHP["justify"] = "center"
                                    GButton_CHP["text"] = "تغییر رمز"
                                    GButton_CHP.place(x=490,y=420,width=109,height=49)
                                    GButton_CHP["command"] = self.PassWindow
                            
                            
                                    GButton_MOJ=tk.Button(self)
                                    GButton_MOJ["bg"] = "#efefef"
                                    ft = tkFont.Font(family='Times',size=10)
                                    GButton_MOJ["font"] = ft
                                    GButton_MOJ["fg"] = "#000000"
                                    GButton_MOJ["justify"] = "center"
                                    GButton_MOJ["text"] = "ثبت مجوز"
                                    GButton_MOJ.place(x=490,y=320,width=109,height=49)
                                    GButton_MOJ["command"] = self.newWindow
                                    
                                    GButton_carpic=tk.Button(self)
                                    GButton_carpic["bg"] = "#efefef"
                                    ft = tkFont.Font(family='Times',size=10)
                                    GButton_carpic["font"] = ft
                                    GButton_carpic["fg"] = "#000000"
                                    GButton_carpic["justify"] = "center"
                                    GButton_carpic["text"] = "دریافت تصاویر"
                                    GButton_carpic.place(x=490,y=270,width=109,height=49)
                                    GButton_carpic["command"] = self.load_carpic
                            
                            
                                    GButton_470=tk.Button(self)
                                    GButton_470["bg"] = "#efefef"
                                    ft = tkFont.Font(family='Times',size=10)
                                    GButton_470["font"] = ft
                                    GButton_470["fg"] = "#000000"
                                    GButton_470["justify"] = "center"
                                    GButton_470["text"] = "تنظیمات دوربین یک"
                                    GButton_470.place(x=950,y=410,width=109,height=49)
                                    GButton_470["command"] = self.optionWindow_cam1
                                    self.pelak_list()
                                def reload(self):
                                        print("reload SHOD")
                                        LastPelakLength = 1
                                        var1 = 0
                                        global toc
                                        tic = time.perf_counter()
                                        elap=tic-toc
                                        if LastPelakLength != var1:# or elap>10
                                         if current_number < target_number :
                                            toc = time.perf_counter()
                                            print("taghir dasht")
                                            self.pelak_list()
                                            file_name = "text_file.txt"
                                            previous_number = 0
                                            def read_and_print():
                                                global previous_number
                                                while True:
                                                    try:
                                                        with open(file_name, "r") as file:
                                                            current_number = int(file.read())
                                                    except FileNotFoundError:
                                                        current_number = 0
                                                    if previous_number != current_number:
                                                        print(f"تغییر داشت: {current_number}")
                                                        self.reload()
                                                        time.sleep(2)
                                                    else:
                                                        print("تغییر نداشت")
                                                    previous_number = current_number
                                                    time.sleep(2)
                                            read_thread = threading.Thread(target=read_and_print)
                                            read_thread.start() 
                                def pelak_list(self):
                                    print("entery")
                                    GLabel_820=tk.Label(self)
                                    ft = tkFont.Font(family='Times',size=10)
                                    GLabel_820["font"] = ft
                                    GLabel_820["fg"] = "#333333"
                                    GLabel_820["justify"] = "center"
                                    GLabel_820["text"] = ""
                                    GLabel_820.place(x=10,y=500,width=1070,height=249)
                                    style = ttk.Style(GLabel_820)
                                    style.theme_use('clam')
                                    style.configure("mystyle.Treeview", highlightthickness=0,bd=0,font=('Far_Nazanin', 11)) # Modify the font of the body
                                    style.configure("mystyle.Treeview.Heading", font=('Far_Nazanin', 15)) # Modify the font of the headings
                                    style.layout("mystyle.Treeview", [('mystyle.Treeview.treearea', {'sticky': 'nswe'})]) # Remove the borders
                                    tree = ttk.Treeview(GLabel_820)
                                    tedadtardod=0      
                                    filename = 'output/PelakList.xlsx'
                                    filename = r"{}".format(filename)
                                    df = pd.read_excel(filename,engine='openpyxl')
                                    # df = pd.read_excel(r'output/pelak.xlsx')
                                    tedadkhat=len(df)
                                    # print(tedadkhat)
                                    # Add new data in Treeview widget
                                    tree["column"] = list(df.columns)
                                    tree["show"] = "headings"
                                    # For Headings iterate over the columns
                                    for col in tree["column"]:
                                        tree.heading(col, text=col)
                                    # Put Data in Rows
                                    df_rows = df.to_numpy().tolist()
                            
                                    for row in df_rows:
                                        tedadtardod=tedadtardod+1
                                        if tedadtardod>tedadkhat-30:
                                           tree.insert("",0,values=row)
                                    tree.pack(anchor='center')
                                    # def imagesaver():
                                    #     exec(imagesaver2.py)
                        
                                    path = "output/PelakList.xlsx"
                                    # To open the workbook
                                    # workbook object is created
                                    wb_obj = openpyxl.load_workbook(path)
                                    # Get workbook active sheet object
                                    # from the active attribute
                                    sheet_obj = wb_obj.active
                                    a1=sheet_obj._current_row
                                    a2="خروج"
                                    for i in range(10000):
                                        a3=sheet_obj.cell(a1-i,1).value
                                        if a3==a2:
                                            cell_value = sheet_obj.cell(a1-i, 2).value
                                            a = cell_value[6]+cell_value[7]+cell_value[5]+cell_value[0]+cell_value[1]+cell_value[2]+" "+cell_value[3]+cell_value[4]
                                            username_label = ttk.Label(text=a,font=('Far_Nazanin', 25))
                                            username_label.place(x=650,y=420,width=200,height=30)
                                            img = Image.open('tools/1.png')
                                            d1 = ImageDraw.Draw(img)
                                            myFont = ImageFont.truetype('tools/Far_Nazanin.ttf', 98)
                                            d1.text((65,15),(a), fill =(0, 0, 0),font=myFont)
                                            img.save("icon/result2.png")
                                            ttk.Style(GLabel_820)
                                            # img.show()
                                            break
                            
                                    path = "output/PelakList.xlsx"
                                    
                                    # To open the workbook
                                    # workbook object is created÷÷÷÷÷÷
                                    wb_obj = openpyxl.load_workbook(path)
                                    
                                    # Get workbook active sheet object
                                    # from the active attribute
                                    sheet_obj = wb_obj.active
                                    a1=sheet_obj._current_row
                                    a2="ورود"
                                    for i in range(10000):
                                        a3=sheet_obj.cell(a1-i,1).value
                                        if a3==a2:
                                            cell_value = sheet_obj.cell(a1-i, 2).value
                                            a = cell_value[6]+cell_value[7]+cell_value[5]+cell_value[0]+cell_value[1]+cell_value[2]+" "+cell_value[3]+cell_value[4]
                                            username_label = ttk.Label(text=a,font=('Far_Nazanin', 25))
                                            username_label.place(x=650,y=420,width=200,height=30)
                                            img = Image.open('tools/1.png')
                                            d1 = ImageDraw.Draw(img)
                                            myFont = ImageFont.truetype('tools/Far_Nazanin.ttf', 98)
                                            d1.text((65,15),(a), fill =(0, 0, 0),font=myFont)
                                            img.save("icon/result1.png")
                                            ttk.Style(GLabel_820)
                                            break
                                                                    #تصویر دوربین شماره یک
                                    screen_width1 = self.winfo_screenwidth()
                                    screen_height1 = self.winfo_screenheight()
                                    while True:
                                        try:
                                            img_old=Image.open("output/main_2.png")
                                            img_resized = ImageOps.fit(img_old,(440,350), Image.ANTIALIAS)
                                            break
                                        except:
                                            print("no image 1234565674674564565464564564")
                                            pass
                                    img = ImageTk.PhotoImage(img_resized)
                                    imagelabel = ttk.Label(self, image = img)
                                    imagelabel.image = img
                                    imagelabel.place(x=20,y=20,width=440,height=350)
                                                                            #تصویر پلاک
                                    
                                    pelak_old=Image.open("icon/result2.png")
                                    img_resized1 = ImageOps.fit(pelak_old,(270,57), Image.ANTIALIAS)
                                    img1 = ImageTk.PhotoImage(img_resized1)
                                    imagelabel = ttk.Label(self, image = img1)
                                    imagelabel.image = img1
                                    imagelabel.place(x=155,y=405,width=270,height=57)
                                                                    #تصویر دوربین شماره دو
                                    screen_width2 = self.winfo_screenwidth()
                                    screen_height2 = self.winfo_screenheight()
                                    while True:
                                        try:
                                            img_old=Image.open("output/main_1.png")
                                            img_resized = ImageOps.fit(img_old,(440,350), Image.ANTIALIAS)
                                            break
                                        except:
                                            print("no image 1234565674674564565464564564")
                                            pass
                                    img = ImageTk.PhotoImage(img_resized)
                                    imagelabel = ttk.Label(self, image = img)
                                    imagelabel.image = img
                                    imagelabel.place(x=620,y=20,width=440,height=350)
                                                                    #تصویر پلاک
                                    pelak_old=Image.open("icon/result1.png")
                                    img_resized1 = ImageOps.fit(pelak_old,(270,57), Image.ANTIALIAS)
                                    img1 = ImageTk.PhotoImage(img_resized1)
                                    imagelabel = ttk.Label(self, image = img1)
                                    imagelabel.image = img1
                                    imagelabel.place(x=650,y=405,width=270,height=59)   
                        
                        
                                def newWindow(self):
                                    print("bale")
                                    screen_width = self.winfo_screenwidth()
                                    screen_height = self.winfo_screenheight()
                                    screen_width = 450
                                    screen_height = 200
                                    newsize=str(int(screen_width/1))+'x'+str(int(screen_height/1))       
                                    newWindow = tk.Toplevel(self)
                                    newWindow.title("ثبت مجوز")
                                    newWindow.iconbitmap('tools/nastaligh.ico')    
                                    newWindow.minsize(800,300)
                                    newWindow.maxsize(800,300)
                                    possible_users = {fernet.decrypt(str.encode(userMoj)).decode(): fernet.decrypt(str.encode(passMoj)).decode(), fernet.decrypt(str.encode(userAdmin)).decode(): fernet.decrypt(str.encode(passAdmin)).decode()}  # dictionary of corresponding user name and passwords
                                    the_user = tk.StringVar()  # used to retrieve input from entry
                                    the_pass = tk.StringVar()
                                    myLabel1 = ttk.Label(newWindow, text="Username :", background='lightblue')
                                    myLabel2 = ttk.Label(newWindow, text="Password :", background='lightblue')
                                    bad_pass = ttk.Label(newWindow, text="Incorrect Username or Password")   
                                    # Entry fields
                                    username_1 = ttk.Entry(newWindow, textvariable=the_user)
                                    password_1 = ttk.Entry(newWindow, show='*', textvariable=the_pass)
                                    # Putting labels onto screen
                                    myLabel1.grid(row=0, column=0)
                                    myLabel2.grid(row=1, column=0)
                            
                                    def check_login():
                                        bad_pass = ttk.Label(newWindow, text="Incorrect Username or Password")
                                        requested_user = the_user.get()
                                        try:
                                            if possible_users[requested_user] == the_pass.get():
                                                login(requested_user)
                                            else:
                                                bad_pass.grid(row=2, column=1)
                                        except KeyError:
                                            bad_pass.grid(row=2, column=1)
                                    def login(user):
                                        forget_login_window()
                                        next_window("ثبت خودروهای مجاز")
                                    def delete():
                                        # Get selected item to Delete
                                        selected_item = newWindow.treeview.selection()[0]
                                        newWindow.treeview.delete(selected_item)
                                    def insert_data():
                                        insert1=arabic_reshaper.reshape(newWindow.name_entry.get())
                                        insert1text= get_display(insert1,encoding="utf-8")
                                        
                                        # insert2=newWindow.idnumber_entry.get()
                                        insert2 =arabic_reshaper.reshape(newWindow.idnumber1_entry.get()[:2]+str(newWindow.idnumber2_entry.get())+newWindow.idnumber3_entry.get()[:3]+newWindow.idnumber4_entry.get()[:2])
                                        insert2text= get_display(insert2,encoding="utf-8")
                                        insert3=arabic_reshaper.reshape(newWindow.car_entry.get())
                                        insert3text= get_display(insert3,encoding="utf-8")
                                        newWindow.treeview.insert('', 'end', iid=newWindow.iid, text=newWindow.id,values=(insert3text,insert2,insert1text))
                                        newWindow.iid = newWindow.iid + 1
                                        newWindow.id = newWindow.id + 1
                                        newWindow.idnumber1_entry.delete(0, 'end')
                                        newWindow.idnumber2_entry.delete(0, 'end')
                                        newWindow.idnumber3_entry.delete(0, 'end')
                                        newWindow.idnumber4_entry.delete(0, 'end')
                                        newWindow.car_entry.delete(0, 'end')
                                        newWindow.name_entry.delete(0, 'end')
                                    def forget_login_window():  # forget all the grid items.
                            
                                        def save_csv():       
                                            excel_file = 'output/Mojavezha.xlsx'
                                            wb = openpyxl.Workbook()
                                            ws = wb.active
                                            headerha = ['pelak111', 'nam', 'khodro']
                                            for col_num, header in enumerate(headerha, 1):
                                                cell = ws.cell(row=1, column=col_num, value=header)
                                            start_row = 2
                                            
                                            for row_id in newWindow.tree.get_children():
                                                row = newWindow.tree.item(row_id)['values']
                                                row0 = get_display(row[0])
                                                row1 = row[1]
                                                row2 = get_display(row[2])
                                                rowfinal = [row1, row2, row0]
                                                for col_num, value in enumerate(rowfinal, 1):
                                                    cell = ws.cell(row=start_row, column=col_num, value=value)
                                                
                                                start_row += 1
                                            wb.save(excel_file)
                                
                                        username_1.grid_forget()
                                        password_1.grid_forget()
                                        myLabel1.grid_forget()
                                        myLabel2.grid_forget()
                                        loginButton1.grid_forget()
                                        cancelButton3.grid_forget()
                                        bad_pass.grid_forget()
                            
                                        style = ttk.Style()
                                        style.configure("mystyle1.Treeview", highlightthickness=0, bd=0, font=('Far_Nazanin', 15)) # Modify the font of the body
                                        style.configure("mystyle1.Treeview.Heading", font=('Far_Nazanin', 15)) # Modify the font of the headings
                                        #style.layout("mystyle.Treeview", [('mystyle.Treeview.treearea', {'sticky': 'nswe'})]) # Remove the borders
                                        newWindow.tree = ttk.Treeview(newWindow, columns=('Khodro','Pelak','ESM'),style="mystyle1.Treeview")
                            
                                        newWindow.tree.heading('#0', text='')
                                        newWindow.tree.column("#0", minwidth=0, width=0)
                            
                                        taradodtext= arabic_reshaper.reshape("خودرو")
                                        taradodtextfarsi= get_display(taradodtext)
                                        newWindow.tree.heading('#1', text=taradodtextfarsi)
                                        newWindow.tree.column("#1", minwidth=0, width=100)
                            
                                        pelaktext= arabic_reshaper.reshape("پلاک")
                                        pelaktextfarsi=get_display(pelaktext)
                                        newWindow.tree.heading('#2', text=pelaktextfarsi)
                                        newWindow.tree.column("#2", minwidth=0, width=110)
                                        
                                        esmtext= arabic_reshaper.reshape("نام و نام خانوادگی")
                                        esmtextfarsi= get_display(esmtext)
                                        newWindow.tree.heading('#3', text=esmtextfarsi)
                                        newWindow.tree.column("#3", minwidth=0, width=200)
                            
                                        # Specify attributes of the columns (We want to stretch it!)
                                        newWindow.tree.column('#0', stretch=tk.YES,anchor="center")
                                        newWindow.tree.column('#1', stretch=tk.YES,anchor="center")
                                        newWindow.tree.column('#2', stretch=tk.YES,anchor="center")
                                        newWindow.tree.column('#3', stretch=tk.YES,anchor="center")
                                    
                                        newWindow.tree.grid(column=0,row=0, columnspan=3,rowspan=10, sticky='nsew')
                                        newWindow.treeview = newWindow.tree
                                        newWindow.id = 0
                                        newWindow.iid = 0
                                        
                                        
                                        excel_file = 'output/Mojavezha.xlsx'
                                        wb = openpyxl.load_workbook(excel_file)
                                        ws = wb.active
                                        
                                        for row in ws.iter_rows(min_row=2, values_only=True):
                                            khodro1 = row[2]  # شماره ستون خودرو در فایل XLSX (مثلاً ستون C)
                                            khodrotex1 = arabic_reshaper.reshape(khodro1)
                                            khodrotexfarsi1 = get_display(khodrotex1)
                                            
                                            pelak1 = row[0]  # شماره ستون پلاک در فایل XLSX (مثلاً ستون A)
                                            
                                            esm1 = row[1]  # شماره ستون نام در فایل XLSX (مثلاً ستون B)
                                            esmtex1 = arabic_reshaper.reshape(esm1)
                                            esmtexfarsi1 = get_display(esmtex1)
                                            
                                            newWindow.tree.insert("", 0, values=(khodrotexfarsi1, pelak1, esmtexfarsi1))
                            
                                        del_btn = ttk.Button(newWindow, text="Delete", command=delete)
                                        del_btn.grid(row=12,column=0)
                                        
                                        newWindow.name_label = tk.Label(newWindow, text=esmtextfarsi,font=('Far_Nazanin', 15))
                                        newWindow.name_entry = tk.Entry(newWindow,font=('Far_Nazanin', 15))
                                        newWindow.name_label.grid(row=0, column=4, sticky=tk.W)
                                        newWindow.name_entry.grid(row=0, column=5)
                                        
                                        newWindow.idnumber1_label = tk.Label(newWindow, text=pelaktextfarsi,font=('Far_Nazanin', 15))
                                        newWindow.idnumber1_entry = tk.Entry(newWindow,font=('Far_Nazanin', 15))
                                        newWindow.idnumber1_label.grid(row=1, column=4, sticky=tk.W)
                                        newWindow.idnumber1_entry.place(x=572, y=55,width=26,height=30)
                                        
                                        # data=( "ب", "ط", "ج","د","س","ص","ق","ل","م","ن","و","ه","ی")
                                        data=("A","ب", "ج", "د","س","ص","ط","ق","ل","م","ن","و","ه","ی","ت","ث","M","ش","ف","پ","ز","ع")
                
                                        newWindow.idnumber2_entry = Combobox(newWindow, values=data, font=("Far_Nazanin", 14), justify="center")
                                        newWindow.idnumber2_entry.place(x=607, y=55,width=56,height=30)
                                        
                                        newWindow.idnumber3_label = tk.Label(newWindow, text=pelaktextfarsi,font=('Far_Nazanin', 15))
                                        newWindow.idnumber3_entry = tk.Entry(newWindow,font=('Far_Nazanin', 15))
                                        newWindow.idnumber3_label.grid(row=1, column=4, sticky=tk.W)
                                        newWindow.idnumber3_entry.place(x=661, y=55,width=39,height=30)
                                        
                                        newWindow.idnumber4_label = tk.Label(newWindow, text=pelaktextfarsi,font=('Far_Nazanin', 15))
                                        newWindow.idnumber4_entry = tk.Entry(newWindow,font=('Far_Nazanin', 15))
                                        newWindow.idnumber4_label.grid(row=1, column=4, sticky=tk.W)
                                        newWindow.idnumber4_entry.place(x=713, y=55,width=26,height=30)
                            
                                        newWindow.car_label = tk.Label(newWindow, text=taradodtextfarsi,font=('Far_Nazanin', 15))
                                        newWindow.car_entry = tk.Entry(newWindow,font=('Far_Nazanin', 15))
                                        newWindow.car_label.grid(row=2, column=4, sticky=tk.W)
                                        newWindow.car_entry.grid(row=2, column=5)
                            
                                        newWindow.submit_button = tk.Button(newWindow, text="Insert", command=insert_data)
                                        newWindow.submit_button.grid(row=12, column=1, sticky=tk.W)
                                        
                                        newWindow.save_button = tk.Button(newWindow, text="save",command=lambda: save_csv())
                                        newWindow.save_button.grid(row=12, column=2, sticky=tk.W)
                                                 
                                    def next_window(my_user):
                                        newWindow.title(my_user)  # desired changes here 
                                        #  you will need to create your tkinter objects (buttons, labels etc) in global and pack / grid / place them here.
                                    def closeNewWindow():
                                        newWindow.destroy()
                            
                                    # Entry field Locations
                                    username_1.grid(row=0, column=1)
                                    password_1.grid(row=1, column=1)
                                    logintext= arabic_reshaper.reshape("ورود")
                                    logintextfarsi= get_display(logintext)
                                    canceltext= arabic_reshaper.reshape("لغو")
                                    canceltextfarsi= get_display(canceltext)
                                    s = ttk.Style()
                                    s.configure('my.TButton',font=('Far_Nazanin', 15))
                                    loginButton1 = ttk.Button(newWindow, text=logintextfarsi,style='my.TButton', command=check_login)
                                    cancelButton3 = ttk.Button(newWindow, text=canceltextfarsi,style='my.TButton',command=closeNewWindow)
                                    loginButton1.grid_rowconfigure(1, weight=1)
                                    loginButton1.grid_columnconfigure(1, weight=1)
                                    #   Putting buttons onto screen
                            
                                    loginButton1.grid(row=6, column=1)
                                    cancelButton3.grid(row=7, column=1)    
                            
                                def optionWindow_cam2(self):
                                    print("bale2")
                                    screen_width = self.winfo_screenwidth()
                                    screen_height = self.winfo_screenheight()
                                    screen_width = 450
                                    screen_height = 200
                                    newsize=str(int(screen_width/1))+'x'+str(int(screen_height/1))       
                                    # Toplevel object which will
                                    # be treated as a new window
                                    optionWindow_cam2 = tk.Toplevel(self)
                                    # sets the title of the
                                    # Toplevel widget
                                    optionWindow_cam2.title("تنظیمات دوربین دو")
                                    optionWindow_cam2.iconbitmap('tools/nastaligh.ico')    
                                    # sets the geometry of toplevel
                                    optionWindow_cam2.minsize(560,300)
                                    optionWindow_cam2.maxsize(560,300)
                                    possible_users = {fernet.decrypt(str.encode(userOption)).decode(): fernet.decrypt(str.encode(passOption)).decode(), fernet.decrypt(str.encode(userAdmin)).decode(): fernet.decrypt(str.encode(passAdmin)).decode()}  # dictionary of corresponding user name and passwords    
                                    # StringVars
                                    the_user = tk.StringVar()  # used to retrieve input from entry
                                    the_pass = tk.StringVar()
                                    # Creating Label Widget
                                    myLabel1 = ttk.Label(optionWindow_cam2, text="Username :", background='lightblue')
                                    myLabel2 = ttk.Label(optionWindow_cam2, text="Password :", background='lightblue')
                                    bad_pass = ttk.Label(optionWindow_cam2, text="Incorrect Username or Password")
                                
                                    # Entry fields
                                    username_1 = ttk.Entry(optionWindow_cam2, textvariable=the_user)
                                    password_1 = ttk.Entry(optionWindow_cam2, show='*', textvariable=the_pass)
                                    # Putting labels onto screen
                                    myLabel1.grid(row=0,column=2,columnspan=1)
                                    myLabel2.grid(row=1, column=2,columnspan=1)
                                    def check_login():
                                        bad_pass = ttk.Label(optionWindow_cam2, text="Error")
                                        requested_user = the_user.get()
                                        global requestuss
                                        requestuss = requested_user
                                        try:
                                            if possible_users[requested_user] == the_pass.get():
                                                login(requested_user)
                                            else:
                                                bad_pass.grid(row=2, column=3)
                                        except KeyError:
                                            bad_pass.grid(row=2, column=3)
                                    def login(user):
                                        forget_login_window()
                                        next_window("تنظیمات دوربین دو")
                                        
                                    def delete():
                                        # Get selected item to Delete
                                        selected_item = optionWindow_cam2.treeview.selection()[0]
                                        optionWindow_cam2.treeview.delete(selected_item)
                                    def saveOption():
                                            with open('bin/Load/option_2.csv','w', newline='',encoding="utf-8") as option:
                                                writer = csv.writer(option)
                                                writer.writerow([optionWindow_cam2.ip2_entry.get()]) 
                                                writer.writerow([optionWindow_cam2.camuser2_entry.get()])
                                                writer.writerow([optionWindow_cam2.campass2_entry.get()])
                                                writer.writerow([optionWindow_cam2.camdelay2_entry.get()])
                                                writer.writerow([optionWindow_cam2.relay2_entry.get()])
                                                writer.writerow([optionWindow_cam2.tasvir2_entry.get()])
                                                
                                    def forget_login_window():  # forget all the grid items.
                                
                                        username_1.grid_forget()
                                        password_1.grid_forget()
                                        myLabel1.grid_forget()
                                        myLabel2.grid_forget()
                                        loginButton1.grid_forget()
                                        cancelButton3.grid_forget()
                                        bad_pass.grid_forget()
                                        style = ttk.Style()
                                        style.configure("mystyle1.Treeview", highlightthickness=0, bd=0, font=('Far_Nazanin', 15)) # Modify the font of the body
                                        style.configure("mystyle1.Treeview.Heading", font=('Far_Nazanin', 15)) # Modify the font of the headings
                                        with open('bin/Load/option_2.csv', newline='',encoding="utf-8") as option2:
                                            writer = csv.writer(option2)
                                        iptextfarsi=get_display(arabic_reshaper.reshape("آی پی دوربین "))
                                        optionWindow_cam2.ip1_label = tk.Label(optionWindow_cam2, text=iptextfarsi,font=('Far_Nazanin', 11))
                                        optionWindow_cam2.ip2_entry = tk.Entry(optionWindow_cam2,font=('Far_Nazanin', 15))
                                        optionWindow_cam2.ip1_label.grid(row=0, column=0, sticky=tk.W)
                                        optionWindow_cam2.ip2_entry.grid(row=0, column=1)
                                        
                                        usernametextfarsi=get_display(arabic_reshaper.reshape(' نام کاربری دوربین '))
                                        optionWindow_cam2.camuser_label = tk.Label(optionWindow_cam2, text=usernametextfarsi,font=('Far_Nazanin', 11))
                                        optionWindow_cam2.camuser2_entry = tk.Entry(optionWindow_cam2,font=('Far_Nazanin', 15))
                                        optionWindow_cam2.camuser_label.grid(row=1, column=0, sticky=tk.W)
                                        optionWindow_cam2.camuser2_entry.grid(row=1, column=1)
                                        
                                        
                                        campasstextfarsi=get_display(arabic_reshaper.reshape('رمز ورود دوربین '))
                                        optionWindow_cam2.campass_label = tk.Label(optionWindow_cam2, text=campasstextfarsi,font=('Far_Nazanin', 11))
                                        optionWindow_cam2.campass2_entry = tk.Entry(optionWindow_cam2,font=('Far_Nazanin', 15))
                                        optionWindow_cam2.campass_label.grid(row=2, column=0, sticky=tk.W)
                                        optionWindow_cam2.campass2_entry.grid(row=2, column=1)
                                        
                                        delaytextfarsi=get_display(arabic_reshaper.reshape('تاخیر ثبت پلاک تکراری'))
                                        optionWindow_cam2.camdelay_label = tk.Label(optionWindow_cam2, text=delaytextfarsi,font=('Far_Nazanin', 11))
                                        optionWindow_cam2.camdelay2_entry = tk.Entry(optionWindow_cam2,font=('Far_Nazanin', 15))
                                        optionWindow_cam2.camdelay_label.grid(row=3, column=0, sticky=tk.W)
                                        optionWindow_cam2.camdelay2_entry.grid(row=3, column=1)
                        
                                        relaytextfarsi=get_display(arabic_reshaper.reshape('درب باز کن'))
                                        optionWindow_cam2.relay_label = tk.Label(optionWindow_cam2, text=relaytextfarsi,)
                                        data=('فعال','غیر فعال')
                                        optionWindow_cam2.relay2_entry=Combobox(optionWindow_cam2,state="readonly",values=data,justify='center',font=("Far_Nazanin", 11))
                                        optionWindow_cam2.relay2_entry.grid(row=8, column=1, sticky=tk.W)
                                        optionWindow_cam2.relay_label.grid(row=8, column=0, sticky=tk.W)
                                        
                                        
                                        
                                        imagetextfarsi=get_display(arabic_reshaper.reshape('ذخیره تصاویر دوربین'))
                                        optionWindow_cam2.tasvir_label = tk.Label(optionWindow_cam2, text=imagetextfarsi,)
                                        data=('فعال','غیر فعال')
                                        optionWindow_cam2.tasvir2_entry=Combobox(optionWindow_cam2,state="readonly",values=data,justify='center',font=("Far_Nazanin", 11))
                                        optionWindow_cam2.tasvir2_entry.grid(row=10, column=1, sticky=tk.W)
                                        optionWindow_cam2.tasvir_label.grid(row=10, column=0, sticky=tk.W)
                                        optionWindow_cam2.crop = tk.Button(optionWindow_cam2,width=20,height=2, text="محدوده تصویر دو", command=crop_window2)
                                        optionWindow_cam2.crop.place(x=150,y=250)
                                        
                                        optionWindow_cam2.submit_button = tk.Button(optionWindow_cam2,width=20,height=2, text="ذخیره", command=lambda: saveOption())
                                        optionWindow_cam2.submit_button.place(x=300,y=250)
                            
                                    def next_window(my_user):
                                        optionWindow_cam2.title(my_user)  # desired changes here 
                                        #  you will need to create your tkinter objects (buttons, labels etc) in global and pack / grid / place them here.
                                    def closeoptionWindow_cam2():
                                        optionWindow_cam2.destroy()
                                
                                    # Entry field Locations
                                    username_1.grid(row=0, column=3,columnspan=1)
                                    password_1.grid(row=1, column=3,columnspan=1)
                                    logintext= arabic_reshaper.reshape("ورود")
                                    logintextfarsi= get_display(logintext)
                                    canceltext= arabic_reshaper.reshape("لغو")
                                    canceltextfarsi= get_display(canceltext)
                                    s = ttk.Style()
                                    s.configure('my.TButton',font=('Far_Nazanin', 15))
                                    loginButton1 = ttk.Button(optionWindow_cam2, text=logintextfarsi,style='my.TButton', command=check_login)
                                    cancelButton3 = ttk.Button(optionWindow_cam2, text=canceltextfarsi,style='my.TButton',command=closeoptionWindow_cam2)
                                
                                    loginButton1.grid(row=5, column=4,rowspan=20)
                                    cancelButton3.grid(row=5, column=3,rowspan=20)
                            
                                    def crop_window2():
                                         global x_start, y_start, x_end, y_end, cropping
                                         cropping = False
                                    
                                         x_start, y_start, x_end, y_end = 0, 0, 0, 0
                                    
                                         #image = cv2.imread('screen1.jpg')
                                         import requests
                                         with open('bin/Load/option_2.csv', newline='',encoding="utf-8") as option:
                                             reader = csv.reader(option)
                                             option = list(reader)
                                         ipcam2=option[0][0]
                                         camUser2=option[1][0]
                                         camPass2=option[2][0]
                                         relayDelay2=int(option[3][0])*1000
                                         relayAction2=(option[4][0])
                                         imageSaver2=(option[5][0])
                                         url2='rtsp://'+camUser2+':'+camPass2+'@'+ipcam2+':554/Streaming/channels/101/'
                                         cap=cv2.VideoCapture(url2)
                                         ret,image = cap.read()
                                         dim=(int(image.shape[1]/2),int(image.shape[0]/2))
                                         image =cv2.resize(image,dim)
                                         oriImage = image.copy()
                                    
                                    
                                         def mouse_crop2(event, x, y, flags, param):
                                             # grab references to the global variables
                                             global x_start, y_start, x_end, y_end, cropping
                                             if event == cv2.EVENT_LBUTTONDOWN:
                                                 x_start, y_start, x_end, y_end = x, y, x, y
                                                 cropping = True
                                    
                                             # Mouse is Moving
                                             elif event == cv2.EVENT_MOUSEMOVE:
                                                 if cropping == True:
                                                     x_end, y_end = x, y
                                    
                                             # if the left mouse button was released
                                             elif event == cv2.EVENT_LBUTTONUP:
                                                 # record the ending (x, y) coordinates
                                                 x_end, y_end = x, y
                                                 cropping = False # cropping is finished
                                    
                                                 refPoint = [(x_start, y_start), (x_end, y_end)]
                                    
                                                 if len(refPoint) == 2: #when two points were found
                                                     roi = oriImage[refPoint[0][1]:refPoint[1][1], refPoint[0][0]:refPoint[1][0]]
                                                     
                                                     with open('tools/croping_image_2.csv','w', newline='') as option_crop2:
                                                         writer = csv.writer(option_crop2)
                                                         writer.writerow([refPoint[0][1]*2]) 
                                                         writer.writerow([refPoint[1][1]*2])
                                                         writer.writerow([refPoint[0][0]*2])
                                                         writer.writerow([refPoint[1][0]*2])
                                                     cv2.imshow("press 'E' for end", roi,)
                                         cv2.namedWindow("Draw the box from left to right")
                                         cv2.setMouseCallback("Draw the box from left to right", mouse_crop2)
                                    
                                         while True:
                                    
                                             i = image.copy()
                                    
                                             if not cropping:
                        
                                                 cv2.imshow("Draw the box from left to right", image)
                                    
                                             elif cropping:
                                                 cv2.rectangle(i, (x_start, y_start), (x_end, y_end), (255, 0, 0), 2)
                                                 cv2.imshow("Draw the box from left to right", i)
                                    
                                             cv2.waitKey(0)
                                             if cv2.waitKey(0) == ord('e') or ord('E') or ord('ی') :
                                                 break
                                
                                         #close all open windows
                                         cv2.destroyAllWindows()    
                                def optionWindow_cam1(self):
                                    print("bale3")
                                    screen_width = self.winfo_screenwidth()
                                    screen_height = self.winfo_screenheight()
                                    screen_width = 450
                                    screen_height = 200
                                    newsize=str(int(screen_width/1))+'x'+str(int(screen_height/1))
                                    # Toplevel object which will
                                    # be treated as a new window
                                    optionWindow_cam1 = tk.Toplevel()
                                    # sets the title of the
                                    # Toplevel widget
                                    optionWindow_cam1.title("تنظیمات دوربین یک")
                                    optionWindow_cam1.iconbitmap('tools/nastaligh.ico')
                                    # sets the geometry of toplevel
                                    optionWindow_cam1.minsize(560,300)
                                    optionWindow_cam1.maxsize(560,300)
                                    possible_users = {fernet.decrypt(str.encode(userOption)).decode(): fernet.decrypt(str.encode(passOption)).decode(), fernet.decrypt(str.encode(userAdmin)).decode(): fernet.decrypt(str.encode(passAdmin)).decode()}  # dictionary of corresponding user name and passwords
                                    # StringVars
                                    the_user = tk.StringVar()  # used to retrieve input from entry
                                    the_pass = tk.StringVar()
                                    # Creating Label Widget
                                    myLabel1 = ttk.Label(optionWindow_cam1, text="Username :", background='lightblue')
                                    myLabel2 = ttk.Label(optionWindow_cam1, text="Password :", background='lightblue')
                                    bad_pass = ttk.Label(optionWindow_cam1, text="Incorrect Username or Password")
                                    # Entry fields
                                    username_1 = ttk.Entry(optionWindow_cam1, textvariable=the_user)
                                    password_1 = ttk.Entry(optionWindow_cam1, show='*', textvariable=the_pass)
                            
                                    # Putting labels onto screen
                                    myLabel1.grid(row=0,column=2,columnspan=1)
                                    myLabel2.grid(row=1, column=2,columnspan=1)
                                    def check_login():
                                        bad_pass = ttk.Label(optionWindow_cam1, text="Error")
                                        requested_user = the_user.get()
                                        global requestuss
                                        requestuss = requested_user
                                        try:
                                            if possible_users[requested_user] == the_pass.get():
                                                login(requested_user)
                                            else:
                                                bad_pass.grid(row=2, column=3)
                                        except KeyError:
                                            bad_pass.grid(row=2, column=3)
                                    def login(user):
                                        forget_login_window()
                                        next_window("تنظیمات دوربین یک")
                                        
                                    def delete():
                                        selected_item = optionWindow_cam1.treeview.selection()[0]
                                        optionWindow_cam1.treeview.delete(selected_item)
                            
                                    def saveOption():
                                        with open('bin/Load/option_1.csv','w', newline='',encoding="utf-8") as option:
                                            writer = csv.writer(option)
                                            writer.writerow([optionWindow_cam1.ip1_entry.get()]) 
                                            writer.writerow([optionWindow_cam1.camuser1_entry.get()])
                                            writer.writerow([optionWindow_cam1.campass1_entry.get()])
                                            writer.writerow([optionWindow_cam1.camdelay1_entry.get()])
                                            writer.writerow([optionWindow_cam1.relay1_entry.get()])
                                            writer.writerow([optionWindow_cam1.tasvir1_entry.get()])
                                    def forget_login_window():  # forget all the grid items.
                                
                                        username_1.grid_forget()
                                        password_1.grid_forget()
                                        myLabel1.grid_forget()
                                        myLabel2.grid_forget()
                                        loginButton1.grid_forget()
                                        cancelButton3.grid_forget()
                                        bad_pass.grid_forget()
                                        style = ttk.Style()
                                        style.configure("mystyle1.Treeview", highlightthickness=0, bd=0, font=('Far_Nazanin', 15)) # Modify the font of the body
                                        style.configure("mystyle1.Treeview.Heading", font=('Far_Nazanin', 15)) # Modify the font of the headings
                                        with open('bin/Load/option_1.csv',"r+", newline='',encoding="utf-8") as option1:
                                            reader = csv.reader(option1)
                                            option1 = list(reader)
                                        iptextfarsi=get_display(arabic_reshaper.reshape("آی پی دوربین "))
                                        optionWindow_cam1.ip1_label = tk.Label(optionWindow_cam1, text=iptextfarsi)
                                        optionWindow_cam1.ip1_entry = tk.Entry(optionWindow_cam1,font=('Far_Nazanin', 15))
                                        optionWindow_cam1.ip1_label.grid(row=0, column=0, sticky=tk.W)
                                        optionWindow_cam1.ip1_entry.grid(row=0, column=1)
                                        
                                        usernametextfarsi=get_display(arabic_reshaper.reshape(' نام کاربری دوربین '))
                                        optionWindow_cam1.camuser_label = tk.Label(optionWindow_cam1, text=usernametextfarsi)
                                        optionWindow_cam1.camuser1_entry = tk.Entry(optionWindow_cam1,font=('Far_Nazanin', 15))
                                        optionWindow_cam1.camuser_label.grid(row=2, column=0, sticky=tk.W)
                                        optionWindow_cam1.camuser1_entry.grid(row=2, column=1)
                                        
                                        
                                        campasstextfarsi=get_display(arabic_reshaper.reshape('رمز ورود دوربین '))
                                        optionWindow_cam1.campass_label = tk.Label(optionWindow_cam1, text=campasstextfarsi)
                        
                                        optionWindow_cam1.campass1_entry = tk.Entry(optionWindow_cam1,font=('Far_Nazanin', 15))
                                        optionWindow_cam1.campass_label.grid(row=4, column=0, sticky=tk.W)
                                        optionWindow_cam1.campass1_entry.grid(row=4, column=1)
                                        
                                        delaytextfarsi=get_display(arabic_reshaper.reshape('تاخیر ثبت پلاک تکراری'))
                                        optionWindow_cam1.camdelay_label = tk.Label(optionWindow_cam1, text=delaytextfarsi)
                        
                                        optionWindow_cam1.camdelay1_entry = tk.Entry(optionWindow_cam1,font=('Far_Nazanin', 15))
                                        optionWindow_cam1.camdelay_label.grid(row=6, column=0, sticky=tk.W)
                                        optionWindow_cam1.camdelay1_entry.grid(row=6, column=1)
                                        
                                        relaytextfarsi=get_display(arabic_reshaper.reshape('درب باز کن'))
                                        optionWindow_cam1.relay_label = tk.Label(optionWindow_cam1, text=relaytextfarsi)
                        
                                        data=('فعال','غیر فعال')
                                        optionWindow_cam1.relay1_entry=Combobox(optionWindow_cam1,state="readonly",values=data,justify='center',font=("Far_Nazanin", 11))
                                        optionWindow_cam1.relay1_entry.grid(row=8, column=1, sticky=tk.W)
                                        optionWindow_cam1.relay_label.grid(row=8, column=0, sticky=tk.W)
                                        
                                        
                                        
                                        imagetextfarsi=get_display(arabic_reshaper.reshape('ذخیره تصاویر دوربین'))
                                        optionWindow_cam1.tasvir_label = tk.Label(optionWindow_cam1, text=imagetextfarsi)
                        
                                        data=('فعال','غیر فعال')
                                        optionWindow_cam1.tasvir1_entry=Combobox(optionWindow_cam1,state="readonly",values=data,justify='center',font=("Far_Nazanin", 11))
                                        optionWindow_cam1.tasvir1_entry.grid(row=10, column=1, sticky=tk.W)
                                        optionWindow_cam1.tasvir_label.grid(row=10, column=0, sticky=tk.W)
                        
                                        
                                        optionWindow_cam1.crop = tk.Button(optionWindow_cam1,width=20,height=2, text="محدوده تصویر یک", command=crop_window1)
                                        optionWindow_cam1.crop.place(x=150,y=250)
                                        
                                        optionWindow_cam1.submit_button = tk.Button(optionWindow_cam1,width=20,height=2, text="ذخیره", command=lambda: saveOption())
                                        optionWindow_cam1.submit_button.place(x=300,y=250)
                                        
                                    def next_window(my_user):
                                        optionWindow_cam1.title(my_user)  # desired changes here 
                                        #  you will need to create your tkinter objects (buttons, labels etc) in global and pack / grid / place them here.
                                    def closeoptionWindow_cam1():
                                        optionWindow_cam1.destroy()
                                
                                    # Entry field Locations
                                    username_1.grid(row=0, column=3,columnspan=1)
                                    password_1.grid(row=1, column=3,columnspan=1)
                                    logintext= arabic_reshaper.reshape("ورود")
                                    logintextfarsi= get_display(logintext)
                                    canceltext= arabic_reshaper.reshape("لغو")
                                    canceltextfarsi= get_display(canceltext)
                                    s = ttk.Style()
                                    s.configure('my.TButton',font=('Far_Nazanin', 15))
                                    loginButton1 = ttk.Button(optionWindow_cam1, text=logintextfarsi,style='my.TButton', command=check_login)
                                    cancelButton3 = ttk.Button(optionWindow_cam1, text=canceltextfarsi,style='my.TButton',command=closeoptionWindow_cam1)
                                
                                    loginButton1.grid(row=5, column=4,rowspan=20)
                                    cancelButton3.grid(row=5, column=3,rowspan=20)
                                    
                            
                                    def crop_window1():
                                         global x_start, y_start, x_end, y_end, cropping
                                         cropping = False
                                         x_start, y_start, x_end, y_end = 0, 0, 0, 0
                                    
                                         import requests
                                         with open('bin/Load/option_1.csv', newline='',encoding="utf-8") as option:
                                             reader = csv.reader(option)
                                             option = list(reader)
                                         ipcam1=option[0][0]
                                         camUser1=option[1][0]
                                         camPass1=option[2][0]
                                         relayDelay1=int(option[3][0])*1000
                                         relayAction1=(option[4][0])
                                         imageSaver1=(option[5][0])
                                         url1='rtsp://'+camUser1+':'+camPass1+'@'+ipcam1+':554/Streaming/channels/101/'
                                         cap=cv2.VideoCapture(url1)
                                         ret,image = cap.read()
                                             
                                         dim=(int(image.shape[1]/2),int(image.shape[0]/2))
                                         image =cv2.resize(image,dim)
                                         oriImage = image.copy()
                                
                                         def mouse_crop1(event, x, y, flags, param):
                                             # grab references to the global variables
                                             global x_start, y_start, x_end, y_end, cropping
                                    
                                             # if the left mouse button was DOWN, start RECORDING
                                             # (x, y) coordinates and indicate that cropping is being
                                             if event == cv2.EVENT_LBUTTONDOWN:
                                                 x_start, y_start, x_end, y_end = x, y, x, y
                                                 cropping = True
                                    
                                             # Mouse is Movingeee
                                             elif event == cv2.EVENT_MOUSEMOVE:
                                                 if cropping == True:
                                                     x_end, y_end = x, y
                                    
                                             # if the left mouse button was released
                                             elif event == cv2.EVENT_LBUTTONUP:
                                                 # record the ending (x, y) coordinates
                                                 x_end, y_end = x, y
                                                 cropping = False # cropping is finished
                                    
                                                 refPoint = [(x_start, y_start), (x_end, y_end)]
                                    
                                                 if len(refPoint) == 2: #when two points were found
                                                     roi = oriImage[refPoint[0][1]:refPoint[1][1], refPoint[0][0]:refPoint[1][0]]
                                                     with open('tools/croping_image_1.csv','w', newline='') as option_crop1:
                                                         writer = csv.writer(option_crop1)
                                                         writer.writerow([refPoint[0][1]*2]) 
                                                         writer.writerow([refPoint[1][1]*2])
                                                         writer.writerow([refPoint[0][0]*2])
                                                         writer.writerow([refPoint[1][0]*2])
                                                     cv2.imshow("press 'E' for end", roi,)
                                    
                                         cv2.namedWindow("Draw the box from left to right")
                                         cv2.setMouseCallback("Draw the box from left to right", mouse_crop1)
                                    
                                         while True:
                                    
                                             i = image.copy()
                                    
                                             if not cropping:
                                                 cv2.imshow("Draw the box from left to right", image)
                                    
                                             elif cropping:
                                                 cv2.rectangle(i, (x_start, y_start), (x_end, y_end), (255, 0, 0), 2)
                                                 cv2.imshow("Draw the box from left to right", i)
                                    
                                             cv2.waitKey(0)
                                             if cv2.waitKey(0) == ord('e') or ord('E') or ord('ی') :
                                                 break
                                    
                                         #close all open windows
                                         cv2.destroyAllWindows()    
                                def load_xlsx(self):
                                         shutil.copy("output/PelakList.xlsx","C:\\Users\\"+os.getlogin()+"\\Desktop\\لیست ترددها.xlsx")
                                         path = "C:\\Users\\"+os.getlogin()+"\\Desktop\\لیست ترددها.xlsx"
                                         os.startfile(path)
                                def load_carpic(self):
                                                             # این کد عالیه os.getcwd() برای آدرس دایرکتوری خود نرم افزار هرجا ک منتقل کنی باز خودش دایرکتوری نرم افزار رو میده
                                         command = 'explorer.exe ' + os.getcwd() +'\image'
                                         os.system(command)
                        
                                         
                                def PassWindow(self):
                                    screen_width = self.winfo_screenwidth()
                                    screen_height = self.winfo_screenheight()
                                    screen_width = 450
                                    screen_height = 200
                                    newsize=str(int(screen_width*0.25))+'x'+str(int(screen_height*0.25))
                            
                                    # Toplevel object which will
                                    # be treated as a new window
                                    PassWindow = tk.Toplevel(self)
                                    # sets the title of the
                                    PassWindow.iconbitmap('tools/nastaligh.ico')
                                    # Toplevel widget
                                    PassWindow.title("تغییر رمز")
                                    # sets the geometry of toplevel
                                    PassWindow.geometry(newsize)
                                    possible_users = {fernet.decrypt(str.encode(userMoj)).decode(): fernet.decrypt(str.encode(passMoj)).decode(),fernet.decrypt(str.encode(userOption)).decode(): fernet.decrypt(str.encode(passOption)).decode(), fernet.decrypt(str.encode(userAdmin)).decode(): fernet.decrypt(str.encode(passAdmin)).decode()}  # dictionary of corresponding user name and passwords
                                    
                                    PassWindow.minsize(450,200)
                                    PassWindow.maxsize(450,200)
                                    # StringVars
                                    the_oldpass = tk.StringVar()  # used to retrieve input from entry
                                    the_newpass = tk.StringVar()
                                    the_newpass1= tk.StringVar()
                                    
                                    iptextfarsi=get_display(arabic_reshaper.reshape('انتخاب کاربر'))
                                    PassWindow.uss1_label = tk.Label(PassWindow, text=iptextfarsi,font=('Far_Nazanin', 15))
                                    PassWindow.uss1_COMBO = ttk.Combobox(PassWindow)
                                    PassWindow.uss1_label.grid(row=0, column=0, sticky=tk.W)
                                    PassWindow.uss1_COMBO.grid(row=0, column=1,columnspan=2)
                                    PassWindow.uss1_COMBO['values']=('Mojavez','Option')
                            
                            
                                    iptextfarsi=get_display(arabic_reshaper.reshape('رمز قبلی'))
                                    PassWindow.pass1_label = tk.Label(PassWindow, text=iptextfarsi,font=('Far_Nazanin', 15))
                                    PassWindow.pass1_entry = tk.Entry(PassWindow,show='*',textvariable=the_oldpass)
                                    PassWindow.pass1_label.grid(row=1, column=0, sticky=tk.W)
                                    PassWindow.pass1_entry.grid(row=1, column=1,columnspan=2)
                                    
                                    iptextfarsi=get_display(arabic_reshaper.reshape('رمز جدید'))
                                    PassWindow.pass2_label = tk.Label(PassWindow, text=iptextfarsi,font=('Far_Nazanin', 15))
                                    PassWindow.pass2_entry = tk.Entry(PassWindow,show='*',textvariable=the_newpass)
                                    PassWindow.pass2_label.grid(row=2, column=0, sticky=tk.W)
                                    PassWindow.pass2_entry.grid(row=2, column=1,columnspan=2)
                                    
                                    iptextfarsi=get_display(arabic_reshaper.reshape('تکرار رمز جدید'))
                                    PassWindow.pass3_label = tk.Label(PassWindow, text=iptextfarsi,font=('Far_Nazanin', 15))
                                    PassWindow.pass3_entry = tk.Entry(PassWindow,show='*',textvariable=the_newpass1)
                                    PassWindow.pass3_label.grid(row=3, column=0, sticky=tk.W)
                                    PassWindow.pass3_entry.grid(row=3, column=1,columnspan=2)
                                    
                                    
                                    okpassfarsi=get_display(arabic_reshaper.reshape('رمز جدید ذخیره شد'))
                                    badpassfarsi=get_display(arabic_reshaper.reshape('رمز وارد شده اشتباه است'))
                                    samepassfarsi=get_display(arabic_reshaper.reshape('تکرار رمز اشتباه است'))
                                    PassWindow.bad_pass = ttk.Label(PassWindow, text=badpassfarsi,font=('Far_Nazanin', 12))
                                    PassWindow.same_pass = ttk.Label(PassWindow, text=samepassfarsi,font=('Far_Nazanin', 12))
                                    PassWindow.ok_pass = ttk.Label(PassWindow, text=okpassfarsi,font=('Far_Nazanin', 12))
                                
                                    def check_Pass():
                                        global passOption
                                        global passMoj
                                        requested_user = PassWindow.uss1_COMBO.get()
                                        PassWindow.same_pass.grid_forget()
                                        PassWindow.bad_pass.grid_forget()
                                        PassWindow.ok_pass.grid_forget()
                                        global requestusscombo
                                        requestusscombo = requested_user
                                        
                                        if ((possible_users[requested_user] == the_oldpass.get())and(the_newpass.get()==the_newpass1.get())):
                                            PassWindow.ok_pass.grid(row=5,column=0,columnspan=3,pady=5)
                                            if requested_user=='Mojavez':
                                                passMoj=fernet.encrypt(str.encode(the_newpass.get()))
                                                passMoj=passMoj.decode("utf-8")
                                                rowunknown=[[userAdmin],[passAdmin],[userOption],[passOption],[userMoj],[passMoj]]
                                                with open('tools/unknown.csv',"r+",newline='') as passwordha:
                                                    csvwriter=csv.writer(passwordha, delimiter=',')
                                                    csvwriter.writerows(rowunknown)
                                       
                                            if requested_user=='Option':
                                                passOption=fernet.encrypt(str.encode(the_newpass.get()))
                                                passOption=passOption.decode("utf-8")
                                                rowunknown=[[userAdmin],[passAdmin],[userOption],[passOption],[userMoj],[passMoj]]
                                                with open('tools/unknown.csv',"r+",newline='') as passwordha:
                                                    csvwriter=csv.writer(passwordha, delimiter=',')
                                                    csvwriter.writerows(rowunknown)
                            
                                        if ((possible_users[requested_user] == the_oldpass.get())and(the_newpass.get()!=the_newpass1.get())):
                                            PassWindow.same_pass.grid(row=6, column=0,columnspan=3,pady=5)
                                        if ((the_newpass.get()==the_newpass1.get())and(possible_users[requested_user] != the_oldpass.get())):
                                            PassWindow.bad_pass.grid(row=7, column=0,columnspan=3,pady=5)
                                        if ((the_newpass.get()!=the_newpass1.get())and(possible_users[requested_user] != the_oldpass.get())):
                                            PassWindow.bad_pass.grid(row=7, column=0,columnspan=3,pady=5)
                                            PassWindow.same_pass.grid(row=6, column=0,columnspan=3,pady=5)
                            
                                    def savePass():
                                        with open('bin/load/option_1.csv',"r+", newline='',encoding="utf-8") as option:
                                            writer = csv.writer(option)
                                            writer.writerow([PassWindow.ip_entry.get()])
                                            writer.writerow([PassWindow.camuser_entry.get()])
                                            writer.writerow([PassWindow.campass_entry.get()])
                                            writer.writerow([PassWindow.camdelay_entry.get()])
                                            writer.writerow([PassWindow.relay_entry.get()])
                                            writer.writerow([PassWindow.tasvir_entry.get()])
                                    def savePass():
                                       with open('bin/load/option_2.csv',"r+", newline='',encoding="utf-8") as option:
                                            writer = csv.writer(option)
                                            writer.writerow([PassWindow.ip_entry.get()])
                                            writer.writerow([PassWindow.camuser_entry.get()])
                                            writer.writerow([PassWindow.campass_entry.get()])
                                            writer.writerow([PassWindow.camdelay_entry.get()])
                                            writer.writerow([PassWindow.relay_entry.get()])
                                            writer.writerow([PassWindow.tasvir_entry.get()])
                            
                                    def closePassWindow():
                                        PassWindow.destroy()
                            
                                    # Entry field Locations
                                    logintext= arabic_reshaper.reshape("ذخیره رمز")
                                    logintextfarsi= get_display(logintext)
                                    canceltext= arabic_reshaper.reshape("لغو")
                                    canceltextfarsi= get_display(canceltext)
                                    s = ttk.Style()
                                    s.configure('my.TButton',font=('Far_Nazanin', 15))
                                    loginButton1 = ttk.Button(PassWindow, text=logintextfarsi,style='my.TButton', command=check_Pass)
                                    cancelButton3 = ttk.Button(PassWindow, text=canceltextfarsi,style='my.TButton',command=closePassWindow)
                                    loginButton1.grid_rowconfigure(1, weight=1)
                                    loginButton1.grid_columnconfigure(1, weight=1)
                                    #   Putting buttons onto screen
                            
                                    loginButton1.grid(row=4, column=1)
                                    cancelButton3.grid(row=4, column=2)
                                    print("G2")
                        if current_number < target_number : 
                            print("پروسس شروع میشه")
                            os.startfile(os.getcwd()+"\\SecondEngine.exe")
                            time.sleep(20)
                            os.startfile(os.getcwd()+"\\FirstEngine.exe")
                            if __name__ == "__main__":
                                app = App()
                                app.mainloop()
                if __name__ == "__main__":
                    # creating threads
                    t1 = threading.Thread(target=task1, name='t1')
                    t2 = threading.Thread(target=task2, name='t2') 
                    t3 = threading.Thread(target=task3, name='t3')
                    # starting threads
                    t1.start()
                    t2.start()
                    t3.start()
                    # wait until all threads finish
                    t1.join()
                    t2.join()
                    t3.join()
            else :
                exit()
if __name__ == "__main__":
    main()
