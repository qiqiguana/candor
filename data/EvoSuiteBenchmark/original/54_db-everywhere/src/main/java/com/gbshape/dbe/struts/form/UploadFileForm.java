/*
Copyright 2007 DB-Everywhere
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
 	http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package com.gbshape.dbe.struts.form;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class UploadFileForm  extends ActionForm implements java.io.Serializable{

	private FormFile file;

    public void setFile(FormFile myFile) {
        this.file = myFile;
    }

    public FormFile getFile() {
        return file;
    }

}
