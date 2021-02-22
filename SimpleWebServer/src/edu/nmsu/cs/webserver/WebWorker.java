//Modified by : Dylan Lassard
//Date : 2-19-21
//Course : CS 371 Software Development : Program1

package edu.nmsu.cs.webserver;

/**
 * Web worker: an object of this class executes in its own new thread to receive and respond to a
 * single HTTP request. After the constructor the object executes on its "run" method, and leaves
 * when it is done.
 *
 * One WebWorker object is only responsible for one client connection. This code uses Java threads
 * to parallelize the handling of clients: each WebWorker runs in its own thread. This means that
 * you can essentially just think about what is happening on one client at a time, ignoring the fact
 * that the entirety of the webserver execution might be handling other clients, too.
 *
 * This WebWorker class (i.e., an object of this class) is where all the client interaction is done.
 * The "run()" method is the beginning -- think of it as the "main()" for a client interaction. It
 * does three things in a row, invoking three methods in this class: it reads the incoming HTTP
 * request; it writes out an HTTP header to begin its response, and then it writes out some HTML
 * content for the response content. HTTP requests and responses are just lines of text (in a very
 * particular format).
 * 
 * @author Jon Cook, Ph.D.
 * modifed by Dylan Lassard
 **/


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.text.DateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.net.*;
import java.io.*;
import java.util.*;




public class WebWorker implements Runnable {

   private Socket socket;
   private boolean fileExists = false;
   private DataOutputStream os;
   private FileInputStream fis;
   private String fileName;
   static String fileType;
   static String type;
   static String contentTypeLine = null;

   public WebWorker(Socket s) {
      socket = s;
   }


   public void run() {
      System.err.println("Handling connection...");
      try {
          //Gets a reference to the socket's input and output stream
           InputStream is = socket.getInputStream();
           os = new DataOutputStream(socket.getOutputStream());
           readHTTPRequest(is);
           writeHTTPHeader(os, type);
           writeContent(os);
           os.flush();
           socket.close();
           fis.close();
      } catch (Exception e) {
               System.err.println("Output error: " + e);
      }
            System.err.println("Done handling connection.");
            return;
    }

private void readHTTPRequest(InputStream is) {

               //String Variable
                String line;

                      //This sets up the input stream filter
                      BufferedReader r = new BufferedReader(new InputStreamReader(is));
                       int check = 1;
                        while (true) {
                        try {
                         while (!r.ready()) Thread.sleep(1);
                          //Gets the request line of the HTTP Request message
                           line = r.readLine();

                        if (check > 0) {
                          StringTokenizer tokens = new StringTokenizer(line);
                           tokens.nextToken();
                            fileName = tokens.nextToken();

                       //Prepand a "." so that the file request is within the directory we are working with
                         fileName = "." + fileName;
                         type = fileType.split("/")[0];

                                  System.out.println("*****************");
                                 System.out.println(fileType);
                                  System.out.println("*****************");

                                      fis = null;
                                      fileExists = true;

                             try {
                               fis = new FileInputStream(fileName);
                             } catch (FileNotFoundException e) {
                                   fileExists = false;
                             } //end of try and catch
                                 check--;
                              }

                        /*
                           *print to terminal for debugging purposes
                        */
                          //This line keeps showing the line of code that the processor is throwing at you
                               System.err.println("Request line: (" + line + ")");
                               if (line.length() == 0) break;
                               } catch (Exception e) {
                                  System.err.println("Request error: " + e);
                                       break;
                                }
                               }
                                      return;
                              }

                    private void writeHTTPHeader(OutputStream os, String contentType) throws Exception {

                               if (fileExists) {
                                  os.write("HTTP/1.1 200 OK\n".getBytes());
                                  os.write(type.getBytes());
                               } else {
                                    os.write("HTTP/1.1 404 Not Found\n".getBytes());
                                    os.write(type.getBytes());
                                 }

                                     Date d = new Date();
                                     DateFormat df = DateFormat.getDateTimeInstance();
                                     df.setTimeZone(TimeZone.getTimeZone("GMT"));
                                     os.write("HTTP/1.1 200 OK\n".getBytes());
                                     os.write("Date: ".getBytes());
                                     os.write((df.format(d)).getBytes());
                                     os.write("\n".getBytes());
                                     os.write("Server: Jon's very own server\n".getBytes());
                                     //os.write("Last-Modified: Mon, 22 Feb 2021 23:11:55 GMT\n".getBytes());
                                     //os.write("Content-Length: 438\n".getBytes());
                                     os.write("Connection: close\n".getBytes());
                                     os.write("Content-Type: ".getBytes());
                                     os.write(contentType.getBytes());
                                     os.write("\n\n".getBytes());
                                      return;
                      }

private static void fileInput(FileInputStream fis, OutputStream os) throws Exception {
  
               byte[] buffer = new byte[1024];
               int bytes = 0;
               Date date = new Date();
               String fileContents;
               String server = "Dylan's Server";
               InetAddress ip = InetAddress.getLocalHost();
                   if (type.equals("image")) {
                      while ((bytes = fis.read(buffer)) != -1) {
                         os.write(buffer, 0, bytes);
                      }
                   } else {
                       os.write("<html><head><title>GC Server</title><link rel=\"icon\" href=\"/test/favicon.ico\" type=\"image/x-icon\"/><link rel=\"shortcut icon\" href=\"/test/favicon.ico\" type=\"image/x-icon\"/></head>".getBytes());

                  while ((bytes = fis.read(buffer)) != -1) {
                       fileContents = new String(buffer, 0, bytes);
                      //System.out.println("Buffer line: " + fileContents); //debugging purposes
                         if (fileContents.contains("<cs371date>")) {
                             fileContents = fileContents.replace("<cs371date>", date.toString());
                         }
                         if (fileContents.contains("<cs371server>")) {
                          fileContents = fileContents.replace("<cs371server>", server);
                         }
                          //Just for Extra and it tells you the ip address you're using.
                            if(fileContents.contains("<ip>")){
                                fileContents = fileContents.replace("<cs371server>", ip.toString());
                             }


				                //System.out.println(fileContents); DEBUGGING PURPOSES
					              buffer = fileContents.getBytes();
				                //System.out.println(buffer); DEBUGGING PURPOSES
					              os.write(buffer, 0, buffer.length);
                 }
              }
           }

    private void writeContent(OutputStream os) throws Exception {
          if (fileExists) {
             fileInput(fis, os);
          } else {
               os.write("<html><head><title>GC Server</title><link rel=\"icon\" href=\"/test/favicon.png\" type=\"image/png\"/><link rel=\"shortcut icon\" href=\"/test/favicon.png\" type=\"image/png\"/></head><body>".getBytes());
               os.write("<h3>HTTP/1.1 404 Not Found\n</h3>\n".getBytes());
               os.write("</html></head></body>".getBytes());

           }
       }

 }