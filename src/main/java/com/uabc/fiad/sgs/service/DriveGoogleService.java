package com.uabc.fiad.sgs.service;


import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.api.client.auth.oauth2.Credential;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;
import java.io.ByteArrayOutputStream;

import org.springframework.stereotype.Service;




@Service
public class DriveGoogleService {
  
  public static String uploadPDF(java.io.File filePDF) throws IOException, GeneralSecurityException{
    try {
      NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
      
      InputStream in = DriveGoogleService.class.getResourceAsStream("/credentials.json");
    if (in == null) {
      throw new FileNotFoundException("Resource not found: " + "/credentials.json");
    }
    GoogleClientSecrets clientSecrets =
        GoogleClientSecrets.load(GsonFactory.getDefaultInstance(), new InputStreamReader(in));

    // Build flow and trigger user authorization request.
    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
        HTTP_TRANSPORT, GsonFactory.getDefaultInstance(), clientSecrets, DriveScopes.all())
        .setDataStoreFactory(new FileDataStoreFactory(new java.io.File("tokens")))
        .setAccessType("online")
        .build();
    LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
    Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("306295956869-5rsnduohkg41nlch3fql3nkke09f11m9.apps.googleusercontent.com");
    //returns an authorized Credential object.


      Drive service = new Drive.Builder(HTTP_TRANSPORT, GsonFactory.getDefaultInstance(), 
          credential)
          .setApplicationName("cliente_sgs_drive")
          .build();



      File fileMetadata = new File();
      fileMetadata.setName(filePDF.getName());

      FileContent mediaContent = new FileContent("text/pdf", filePDF);

      File file = service.files().create(fileMetadata, mediaContent)
          .setFields("id")
          .execute();
      
      return file.getId();
    } catch (GoogleJsonResponseException e) {
      // TODO(developer) - handle error appropriately
      System.err.println("Unable to upload file: " + e.getDetails());
      return null;
    }
  
  
  }


  public static Map<ByteArrayOutputStream,Object> downloadPDF(String fileId) throws IOException, GeneralSecurityException{
    try {
      NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
      
      InputStream in = DriveGoogleService.class.getResourceAsStream("/credentials.json");
      if (in == null) {
        throw new FileNotFoundException("Resource not found: " + "/credentials.json");
      }
      GoogleClientSecrets clientSecrets =
          GoogleClientSecrets.load(GsonFactory.getDefaultInstance(), new InputStreamReader(in));

      // Build flow and trigger user authorization request.
      GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
          HTTP_TRANSPORT, GsonFactory.getDefaultInstance(), clientSecrets, DriveScopes.all())
          .setDataStoreFactory(new FileDataStoreFactory(new java.io.File("tokens")))
          .setAccessType("online")
          .build();
      LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
      Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("306295956869-5rsnduohkg41nlch3fql3nkke09f11m9.apps.googleusercontent.com");
      //returns an authorized Credential object.


      Drive service = new Drive.Builder(HTTP_TRANSPORT, GsonFactory.getDefaultInstance(), 
            credential)
            .setApplicationName("cliente_sgs_drive")
            .build();



    
      OutputStream outputStream = new ByteArrayOutputStream();

      

      service.files().get(fileId)
          .executeMediaAndDownloadTo(outputStream);

      

      List<File> files = new ArrayList<File>();

      String nombreFile = "nohay";
      String pageToken = null;
      do {
        FileList result = service.files().list()
            .setQ("mimeType='text/pdf'")
            .setSpaces("drive")
            .setFields("nextPageToken, items(id, title)")
            .setPageToken(pageToken)
            .execute();
        for (File file : result.getFiles()) {
          System.out.printf("Found file: %s (%s)\n",file.getName(), file.getId());
          if(file.getId().equals(fileId)){
            nombreFile = file.getName();
          }
        }

        files.addAll(result.getFiles());

        pageToken = result.getNextPageToken();
      } while (pageToken != null);


      Map<ByteArrayOutputStream, Object> mapeOfileNombre = new HashMap<>();
      mapeOfileNombre.put( (ByteArrayOutputStream) outputStream, nombreFile);
      return  mapeOfileNombre;
    } catch (GoogleJsonResponseException e) {
      // TODO(developer) - handle error appropriately
      System.err.println("Unable to move file: " + e.getDetails());
      throw e;
    } 
  
  
  }



}