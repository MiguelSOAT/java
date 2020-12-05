public class ImageJob{
   private String pathSrc;
   private String imageSrc;
   private String action;
   private String pathDst;
   private String imageDst;
   private long tsCreationMessage;
   public ImageJob(){}

   public String getAction(){
      return action;
   }
   public void setAction(String aux){
      action = aux;
   }

   public long getTsCreationMessage(){
      return tsCreationMessage;
   }
   public void setTsCreationMessage(long aux){
      tsCreationMessage = aux;
   }
   public String getImageDst(){
      return imageDst;
   }
   public void setImageDst(String aux){
      imageDst = aux;
   }
   public String getPathDst(){
      return pathDst;
   }
   public void setPathDst(String aux){
      pathDst = aux;
   }
   public String getImageSrc(){
      return imageSrc;
   }
   public void setImageSrc(String aux){
      imageSrc = aux;
   }
   public String getPathSrc(){
      return pathSrc;
   }
   public void setPathSrc(String aux){
      pathSrc = aux;
   }

   @Override
   public String toString() {
      return "ImageJob [action=" + action + ", imageDst=" + imageDst + ", imageSrc=" + imageSrc + ", pathDst=" + pathDst
            + ", pathSrc=" + pathSrc + ", tsCreationMessage=" + tsCreationMessage + "]";
   }

}
