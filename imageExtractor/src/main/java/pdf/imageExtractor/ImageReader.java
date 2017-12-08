package pdf.imageExtractor;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;

public class ImageReader {
  public static void main(String[] args) throws Exception {
    System.out.println(Messages.getString("MyImageRenderListener.imageNumberList"));
    extractImage();
  }

  private static void extractImage() throws IOException
  {
    File folder = new File("D:/JK/Project/pdfImageExtract");

    String[] files = folder.list(new FilenameFilter() {

      @Override
      public boolean accept(File dir, String name) {
        return name.endsWith(".pdf");
      }
    });
    for(String file: files)
    {
      String fileName = folder.getAbsolutePath()+"/"+file;
      PdfReader reader = new PdfReader(fileName);
      PdfReaderContentParser parser = new PdfReaderContentParser(reader);
      MyImageRenderListener listener = new MyImageRenderListener(folder, fileName);
      MyImageRenderListener imageRenderListener =  parser.processContent(2, listener);
      System.out.println(fileName + " Signature 1 " + imageRenderListener.isSignatureAvaailable(55));
      System.out.println(fileName + "Signature 2 " + imageRenderListener.isSignatureAvaailable(57));
    }
  }
}
