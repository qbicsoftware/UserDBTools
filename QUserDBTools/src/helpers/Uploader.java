/*******************************************************************************
''' * QBiC User DB Tools enables users to add people and affiliations to our mysql user database.
 * Copyright (C) 2016 Andreas Friedrich
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program. If
 * not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package helpers;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.commons.io.FilenameUtils;

import logging.Log4j2Logger;
import qdbtools.main.QuserdbtoolsUI;

import com.vaadin.ui.Upload;

/**
 * Uploader for tsv files
 * 
 * @author Andreas Friedrich
 * 
 */
@SuppressWarnings("serial")
public class Uploader implements Upload.SucceededListener, Upload.FailedListener, Upload.Receiver {

  private File file; // File to write to.
  private String error;

  private logging.Logger logger = new Log4j2Logger(Uploader.class);
  private String fileName;

  public Uploader() {}

  /**
   * Callback method to begin receiving the upload.
   */
  public OutputStream receiveUpload(String filename, String MIMEType) {
    FileOutputStream fos = null; // Output stream to write to
    error = null;
    fileName = filename;
    file = new File(QuserdbtoolsUI.tmpFolder + "up_" + filename);
    // TODO probably not needed; some browsers set MIME information to application/octet-stream,
    // which leads to bug
    // if (!MIMEType.equals("text/plain") && !MIMEType.equals("text/tab-separated-values")) {
    // error = "Wrong File type. Please only upload tsv or txt files.";
    // }
    try {
      // Open the file for writing.
      fos = new FileOutputStream(file);
    } catch (FileNotFoundException e) {
      // Error while opening the file. Not reported here.
      e.printStackTrace();
      return null;
    }
    return fos; // Return the output stream to write to
  }

  public String getError() {
    return error;
  }

  public String getBaseFileName() {
    return fileName;
  }

  public String getFileNameWithoutExtension() {
    return FilenameUtils.removeExtension(fileName);
  }

  public File getFile() {
    return file;
  }

  /**
   * This is called if the upload is finished.
   */
  public void uploadSucceeded(Upload.SucceededEvent event) {
    // Display the uploaded file in the image panel.
    logger.info(
        "Uploading " + event.getFilename() + " of type '" + event.getMIMEType() + "' successful.");
  }

  /**
   * This is called if the upload fails.
   */
  public void uploadFailed(Upload.FailedEvent event) {
    // Log the failure on screen.
    logger.error(
        "Uploading " + event.getFilename() + " of type '" + event.getMIMEType() + "' failed.");
  }
}
