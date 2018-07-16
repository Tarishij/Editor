import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.regex.*;

class Notepad  implements ActionListener{
TextArea t;
String p="",prev="";
int flag=0,flag1,index,flagnew,flagopen,findnext,flagf_r;
int i,j,l,len,indexx;
TextField t1,t2;
String location;
Frame f,f1,f2,f3,f4,f5;
MenuBar mb;
WindowCloser wc=new WindowCloser();
WindowCloser1 wc1;
Menu m1,m2;

MenuItem nw,opn,sv,svas,ext,find,f_r;
public Notepad(){

f=new Frame("Untitled-Notepad");
f.setSize(400,400);

mb=new MenuBar();
m1=new Menu("File");
m2=new Menu("Edit");

nw=new MenuItem("New");nw.addActionListener(this);
opn=new MenuItem("Open...");opn.addActionListener(this);
sv=new MenuItem("Save");sv.addActionListener(this);
svas=new MenuItem("Save As...");svas.addActionListener(this);
ext=new MenuItem("Exit");ext.addActionListener(this);

find=new MenuItem("Find");find.addActionListener(this);
f_r=new MenuItem("Find and Replace");
f_r.addActionListener(this);

m1.add(nw);m1.add(opn);m1.add(sv);m1.add(svas);
m1.addSeparator();m1.add(ext);

m2.add(find);m2.add(f_r);
mb.add(m1);
mb.add(m2);
f.setMenuBar(mb);
t=new TextArea();
f.add(t);
f.setVisible(true);

wc1=new WindowCloser1();
f.addWindowListener(wc1);
}

public class WindowCloser extends WindowAdapter{
public void windowClosing(WindowEvent e){
flag1=0;flagf_r=0;
Window w=e.getWindow();
w.setVisible(false);
w.dispose();
}
}

public class WindowCloser1 extends WindowAdapter{
public void windowClosing(WindowEvent e){

if(t.getText().equals(prev)){

Window w=e.getWindow();
w.setVisible(false);
w.dispose();
System.exit(1);
}
else{
askUser();
}
}
}



public void actionPerformed(ActionEvent e){
String str=e.getActionCommand();
if(str.equals("Exit")){

if(t.getText().equals(prev)){
System.exit(1);
}
else{

askUser();
}


}

else if(str.equals("New")){

if(t.getText().equals(prev))
{
f.dispose();
Notepad n=new Notepad();
}

else{
flagnew=1;
askUser();
}

}
else if(str.equals("Don't Save")){

f4.dispose();
f.dispose();
if(flagnew==1){
flagnew=0;
Notepad n=new Notepad();
}
if(flagopen==1){
flagopen=0;

FileDialog fd=new FileDialog(f,"Open",FileDialog.LOAD);
fd.setVisible(true);
f.setVisible(true);
String path,name;
name=fd.getFile();
path=fd.getDirectory();
location=path+name;

//file read
t.setText("");
try{
File file=new File(location);
FileInputStream fis=new FileInputStream(file);
int ch;
while((ch=fis.read())!=-1)
t.setText(t.getText()+(char)ch);
fis.close();
f.setTitle(name);
}
catch(Exception e1){
e1.printStackTrace();
}


}
}
else if(str.equals("Cancel")){
flag1=0;
f4.dispose();

}
else if(str.equals("Open...")){
flagopen=1;
if(!t.getText().equals(prev)){
askUser();
}

else{
FileDialog fd=new FileDialog(f,"Open",FileDialog.LOAD);
fd.setVisible(true);
f.setVisible(true);
String path,name;
name=fd.getFile();
path=fd.getDirectory();
location=path+name;

//file read
t.setText("");
try{
File file=new File(location);
FileInputStream fis=new FileInputStream(file);
int ch;
while((ch=fis.read())!=-1)
t.setText(t.getText()+(char)ch);
fis.close();
f.setTitle(name);
}
catch(Exception e1){
e1.printStackTrace();
}
}
}

else if(str.equals("Save As...")){

saveas();
prev=t.getText();
}

else if(str.equals("Save")){

if(f.getTitle().equals("Untitled-Notepad")){
saveas();
}
else{

try{
File f1=new File(location);
FileOutputStream fos=new FileOutputStream(f1);
String st=t.getText();
byte[] b=st.getBytes();
for(byte by:b){
fos.write(by);
}
fos.close();
}

catch(Exception e2){
e2.printStackTrace();
}

}
prev=t.getText();
}

else if(str.equals("Save..")){
f4.dispose();

if(f.getTitle().equals("Untitled-Notepad")){
saveas();
}
else{

try{
File f1=new File(location);
FileOutputStream fos=new FileOutputStream(f1);
String st=t.getText();
byte[] b=st.getBytes();
for(byte by:b){
fos.write(by);
}
fos.close();
}

catch(Exception e2){
e2.printStackTrace();
}

}
prev=t.getText();
}
else if(str.equals("Find")){

if(flagf_r==1){
i=1;
f5=new Frame("Notepad");
f5.setBounds(500,200,300,100);
f5.setLayout(new GridLayout(2,0));
Panel p1=new Panel(new FlowLayout(FlowLayout.CENTER));
Panel p2=new Panel(new FlowLayout(FlowLayout.CENTER));

Label ll=new Label("Close Find and Replace tab first");
p1.add(ll);
Button b=new Button("Ok");
b.addActionListener(this);
p2.add(b);
f5.add(p1);f5.add(p2);

f5.setVisible(true);
f5.addWindowListener(wc);





}
else{
flag1=1;
//make new frame

f1=new Frame("Find");
f1.setBounds(500,200,400,200);
f1.setLayout(new GridBagLayout());
GridBagConstraints gbc=new GridBagConstraints();
gbc.gridx=0;gbc.gridy=0;
gbc.weightx=1.0;gbc.weighty=1.0;

Label l1=new Label("Find what:");
t1=new TextField();
Button b1=new Button("Find Next");
b1.addActionListener(this);
Button b2=new Button("Close");
b2.addActionListener(this);
f1.add(l1,gbc);

gbc.gridy=0;gbc.gridx=1;
gbc.ipadx=200;
f1.add(t1,gbc);

gbc.gridy=1;gbc.gridx=0;gbc.ipadx=0;
f1.add(b1,gbc);

gbc.gridx=1;gbc.gridy=1;
f1.add(b2,gbc);



if(p!=""){
t1.setText("");
p="";
}

f1.setVisible(true);
f1.addWindowListener(wc);
}
}
else if(str.equals("Find Next")){
findnext=1;

flag=0;
String line=t.getText();
String[] lines=line.split("\n");
try{

if(p==""||(!p.equals(t1.getText()))){
index=0;l=0;len=0;
p=t1.getText();
}
Pattern pat=Pattern.compile(p);
while(true){

if(l<lines.length){

Matcher m=pat.matcher(lines[l]);
if(index<lines[l].length()&&m.find(index)){
index=m.start()+1;
flag=1;
t.select(m.start()+len,m.end()+len);
break;
}
else{
len=len+lines[l].length();
l++;
index=0;}
}
else
break;
}

if(flag==0){

f2=new Frame("Notepad");
f2.setBounds(500,200,300,100);
f2.setLayout(new GridLayout(2,0));
Panel p1=new Panel(new FlowLayout(FlowLayout.CENTER));
Panel p2=new Panel(new FlowLayout(FlowLayout.CENTER));

Label ll=new Label("Cannot find : "+"\""+p+"\"");
p1.add(ll);
Button b=new Button("Ok");
b.addActionListener(this);
p2.add(b);
f2.add(p1);f2.add(p2);

f2.setVisible(true);
f2.addWindowListener(wc);
}



f.setVisible(true);
}
catch(Exception ee){
}

}
else if(str.equals("Ok")){
if(i==1){
i=0;
f5.dispose();
}
else if(j==1){
j=0;f5.dispose();
}
else{
f2.dispose();
}
}
else if(str.equals("Close")){
if(flag1==1){
flag1=0;
f1.dispose();
}
else if(flagf_r==1){
flagf_r=0;
f3.dispose();
}
}

else if(str.equals("Find and Replace")){

if(flag1==1){

//dialog box of closing Find tab

j=1;
f5=new Frame("Notepad");
f5.setBounds(500,200,300,100);
f5.setLayout(new GridLayout(2,0));
Panel p1=new Panel(new FlowLayout(FlowLayout.CENTER));
Panel p2=new Panel(new FlowLayout(FlowLayout.CENTER));

Label ll=new Label("Close Find tab first");
p1.add(ll);
Button b=new Button("Ok");
b.addActionListener(this);
p2.add(b);
f5.add(p1);f5.add(p2);

f5.setVisible(true);
f5.addWindowListener(wc);


}
else{
flagf_r=1;
//make new frame

f3=new Frame("Find And Replace");
f3.setBounds(500,200,600,200);
f3.setLayout(new GridBagLayout());
GridBagConstraints gbc=new GridBagConstraints();
gbc.gridx=0;gbc.gridy=0;
gbc.weightx=1.0;gbc.weighty=1.0;

Label l1=new Label("Find what: ");
t1=new TextField();
Label l2=new Label("Replace With: ");
t2=new TextField();
Button b1=new Button("Find Next");
b1.addActionListener(this);
Button b2=new Button("Replace");
b2.addActionListener(this);
Button b3=new Button("Replace All");
b3.addActionListener(this);
Button b4=new Button("Close");
b4.addActionListener(this);

f3.add(l1,gbc);

gbc.gridy=0;gbc.gridx=1;
gbc.ipadx=200;
f3.add(t1,gbc);

gbc.gridy=1;gbc.gridx=0;gbc.ipadx=0;
f3.add(l2,gbc);

gbc.gridy=1;gbc.gridx=1;
gbc.ipadx=200;
f3.add(t2,gbc);

gbc.gridy=2;gbc.gridx=0;gbc.ipadx=0;
f3.add(b1,gbc);

gbc.gridx=1;
f3.add(b2,gbc);
gbc.gridx=2;
f3.add(b3,gbc);
gbc.gridx=3;
f3.add(b4,gbc);

if(p!=""){
t1.setText("");
p="";
}

f3.setVisible(true);
f3.addWindowListener(wc);
}
}
else if(str.equals("Replace")){



if(findnext==1){

int start1=t.getSelectionStart();
indexx=start1+1;
if(start1!=-1){
int last1=t.getSelectionEnd();
StringBuilder sb=new StringBuilder(t.getText());
sb.replace(start1,last1,t2.getText());
t.setText(sb.toString());
findnext=0;
}
}
else{

Pattern p=Pattern.compile(t1.getText());
Matcher m=p.matcher(t.getText());

if(m.find(indexx)){
StringBuilder sb=new StringBuilder(t.getText());

sb.replace(m.start(),m.end(),t2.getText());
t.setText(sb.toString());
indexx=m.start()+1;
}

else{
f2=new Frame("Notepad");
f2.setBounds(500,200,300,100);
f2.setLayout(new GridLayout(2,0));
Panel p1=new Panel(new FlowLayout(FlowLayout.CENTER));
Panel p2=new Panel(new FlowLayout(FlowLayout.CENTER));

Label ll=new Label("Cannot find : "+"\""+p+"\"");
p1.add(ll);
Button b=new Button("Ok");
b.addActionListener(this);
p2.add(b);
f2.add(p1);f2.add(p2);

f2.setVisible(true);
f2.addWindowListener(wc);

}

}


}

else if(str.equals("Replace All")){

Pattern p=Pattern.compile(t1.getText());
Matcher m=p.matcher(t.getText());
String s=m.replaceAll(t2.getText());
t.setText(s);


}

}
public void saveas(){

FileDialog fd=new FileDialog(f,"Save As",FileDialog.SAVE);
fd.setVisible(true);
f.setVisible(true);
String path,name;
name=fd.getFile();
path=fd.getDirectory();
location=path+name;

try{
File f1=new File(location);
FileOutputStream fos=new FileOutputStream(f1);
String st=t.getText();
byte[] b=st.getBytes();
for(byte by:b){
fos.write(by);
}
fos.close();
f.setTitle(name);
}

catch(Exception e2){
e2.printStackTrace();
}

}
public void askUser(){



//open a dialog box asking the user to save the contents

f4=new Frame("Notepad");
f4.setBounds(100,100,400,200);
f4.setLayout(new GridLayout(2,0));
Panel p1=new Panel(new FlowLayout(FlowLayout.CENTER));

Label l1=new Label("Do you want to save changes to "+f.getTitle()+" ?");
p1.add(l1);
f4.add(p1);
Button b1=new Button("Save..");b1.addActionListener(this);
Button b2=new Button("Don't Save");b2.addActionListener(this);
Button b3=new Button("Cancel");b3.addActionListener(this);
Panel p=new Panel(new FlowLayout(FlowLayout.CENTER));

p.add(b1);p.add(b2);p.add(b3);
f4.add(p);
f4.setVisible(true);

f4.addWindowListener(wc);
}
public static void main(String[] args){
Notepad n=new Notepad();
}
}
