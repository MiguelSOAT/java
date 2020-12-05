import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import cs.edu.uv.http.dynamicresponse.client.WebResponse;
import cs.edu.uv.http.dynamicresponse.client.ThingsAboutRequest;
import cs.edu.uv.http.dynamicresponse.client.ThingsAboutResponse;

import java.io.PrintWriter;
import java.util.HashMap;

public class ImProcServer extends WebResponse{
    // Directorio donde guardaremos las imágenes que nos envían
   private static final String LOCAL_PATH_SRC="/var/web/resources/subidas";   

   // Directorio de donde deben leer los workers las imágenes procesadas
   private static final String PATH_SRC = "/data/subidas";

   // Directorio donde deben guardar los workers las imágenes procesadas
   private static final String PATH_DST = "/data/procesadas";
   public ImProcServer(){}


   public void ifPost(ThingsAboutRequest req, ThingsAboutResponse resp) throws Exception{
      // TODO Comprobar que se trata de una petición multipart/form-data
      if (req.isMultipart()){

         HashMap<String,String> params = new HashMap<String,String>();
         HashMap<String,String> files = new HashMap<String,String>();
         // TODO Obtener la acción a realizar y la imagen del cuerpo de la petición
         //      ver ejemplo en MultiPartExample
         Channel c = ConexionRabbitMQ.getChannel();
         
         req.parseMultipart(params, files, path, false);
         // TODO Crear una instancia del tipo ImageJob con la información necesaria
         ImageJob ij = null;
         
         ij.setImageDst(files.get("IMAGE")+"_"+params.get("ACTION"));
         ij.setPathDst(PATH_DST);
         ij.setImageSrc(files.get("IMAGE"));
         ij.setPathSrc(PATH_SRC);
         ij.setAction(params.get("ACTION"));

         // Serialización de la instancia del tipo ImageJob a JSON (String)
         Gson gson = new Gson();
         String json = gson.toJson(ij);  

         // Obtención del cuerpo del mensaje
         byte[] messageBody = json.getBytes();

         c.basicQos(1);

         // Usamos el canal para definir: el exchange, la cola y la asociación exchange-cola
         c.exchangeDeclare(RabbitMQStuff.EXCHANGE, "direct", true);
         c.queueDeclare(RabbitMQStuff.COLA_TRABAJOS, true, false, false, null);
         c.queueBind(RabbitMQStuff.COLA_TRABAJOS, RabbitMQStuff.EXCHANGE, RabbitMQStuff.RK_TRABAJOS);

         // Publicar el mensaje con el trabajo a realizar
         c.basicPublish(RabbitMQStuff.EXCHANGE, RabbitMQStuff.RK_TRABAJOS, null, messageBody);

         resp.setStatus(200);
         PrintWriter pw = resp.getWriter();
         pw.println(params.get("ACTION"));
         pw.println(files.get("IMAGE"));				
         pw.flush();
         pw.close();
   }}
}
