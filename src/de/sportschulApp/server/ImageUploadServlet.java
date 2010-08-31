package de.sportschulApp.server;

/*
 * Copyright 2009 Manuel Carrasco Minion. (manuel_carrasco at users.sourceforge.net) 
 * http://code.google.com/p/gwtupload
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

import gwtupload.server.UploadAction;
import gwtupload.server.exceptions.UploadActionException;

import org.apache.commons.fileupload.FileItem;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class sends by email, all the fields and files received by GWTUpload
 * servlet.
 * 
 * @author Manolo Carrasco Moñino
 * 
 */
public class ImageUploadServlet extends UploadAction {

	private static final long serialVersionUID = 1L;

	Hashtable<String, String> receivedContentTypes = new Hashtable<String, String>();
	/**
	 * Maintain a list with received files and their content types.
	 */
	Hashtable<String, File> receivedFiles = new Hashtable<String, File>();

	/**
	 * Override executeAction to save the received files in a custom place and
	 * delete this items from session.
	 */
	@Override
	public String executeAction(HttpServletRequest request,
			List<FileItem> sessionFiles) throws UploadActionException {
		String response = "";
		int cont = 0;
		for (FileItem item : sessionFiles) {
			if (false == item.isFormField()) {
				cont++;
				try {
					// / Create a new file based on the remote file name in the
					// client
					String saveName = item.getName().replaceAll("[\\\\/><\\|\\s\"'{}()\\[\\]]+", "_");
					File file =new File("/Users/geo/Desktop/test/" + saveName);

					// / Create a temporary file placed in /tmp (only works in
					// unix)
					//File file = File.createTempFile("upload-", ".bin",
						//	new File("/Users/geo/Desktop/test"));

					// / Create a temporary file placed in the default system
					// temp folder
					// File file = File.createTempFile("upload-", ".bin");
					item.write(file);

					// / Save a list with the received files
					receivedFiles.put(item.getFieldName(), file);
					receivedContentTypes.put(item.getFieldName(),
							item.getContentType());

					// / Compose a xml message with the full file information
					// which can be parsed in client side
					response += "<file-" + cont + "-field>"
							+ item.getFieldName() + "</file-" + cont
							+ "-field>\n";
					response += "<file-" + cont + "-name>" + item.getName()
							+ "</file-" + cont + "-name>\n";
					response += "<file-" + cont + "-size>" + item.getSize()
							+ "</file-" + cont + "-size>\n";
					response += "<file-" + cont + "-type>"
							+ item.getContentType() + "</file-" + cont
							+ "type>\n";
				} catch (Exception e) {
					throw new UploadActionException(e.getMessage());
				}
			}
		}

		// / Remove files from session because we have a copy of them
		removeSessionFileItems(request);

		// / Send information of the received files to the client.
		return "<response>\n" + response + "</response>\n";
	}

	/**
	 * Get the content of an uploaded file.
	 */
	@Override
	public void getUploadedFile(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String fieldName = request.getParameter(PARAM_SHOW);
		File f = receivedFiles.get(fieldName);
		if (f != null) {
			response.setContentType(receivedContentTypes.get(fieldName));
			FileInputStream is = new FileInputStream(f);
			copyFromInputStreamToOutputStream(is, response.getOutputStream());
		} else {
			renderXmlResponse(request, response, ERROR_ITEM_NOT_FOUND);
		}
	}

	/**
	 * Remove a file when the user sends a delete request.
	 */
	@Override
	public void removeItem(HttpServletRequest request, String fieldName)
			throws UploadActionException {
		File file = receivedFiles.get(fieldName);
		receivedFiles.remove(fieldName);
		receivedContentTypes.remove(fieldName);
		if (file != null) {
			file.delete();
		}
	}
}
