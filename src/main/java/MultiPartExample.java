
import java.io.PrintWriter;
import java.util.HashMap;

import cs.edu.uv.http.dynamicresponse.client.ThingsAboutRequest;
import cs.edu.uv.http.dynamicresponse.client.ThingsAboutResponse;
import cs.edu.uv.http.dynamicresponse.client.WebResponse;

public class MultiPartExample extends WebResponse{
   public void ifPost(ThingsAboutRequest req, ThingsAboutResponse resp) throws Exception {
		try {
            if (req.isMultipart()){
				// This is a HashMap to store params from multipart body
				HashMap<String,String> params = new HashMap<String,String>();
				// This is a HashMap to store info about files saved from multipart body
				HashMap<String,String> files = new HashMap<String,String>();
				// This is the path where files will be stored
				String path="/tmp";

				req.parseMultipart(params, files, path, false);

				// Generación de la respuesta
				resp.setStatus(200);
				PrintWriter pw = resp.getWriter();
				pw.println(params.get("ACTION"));
				pw.println(files.get("IMAGE"));				
				pw.flush();
				pw.close();
			}
		} catch (Exception ex) {
			resp.writeInternalServerError();
		}
	}
}
