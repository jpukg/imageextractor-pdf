package pdf.imageExtractor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.itextpdf.text.pdf.parser.ImageRenderInfo;
import com.itextpdf.text.pdf.parser.PdfImageObject;
import com.itextpdf.text.pdf.parser.RenderListener;
import com.itextpdf.text.pdf.parser.TextRenderInfo;

class MyImageRenderListener implements RenderListener
{
  private static final String PNG = "png"; //$NON-NLS-1$
  final String name;
  final File folder;
  int counter = 100000;
  private HashMap<Integer, Boolean> signatureList = new HashMap<>();
  public MyImageRenderListener(File folder, String name)
  {
    this.name = name;
    this.folder = folder;
  }
  

  public boolean isSignatureAvaailable(int number) {
    return signatureList.containsKey(number);
  }




  public void beginTextBlock() { }
  public void renderText(TextRenderInfo renderInfo) { }
  public void endTextBlock() { }

  public void renderImage(ImageRenderInfo renderInfo)
  {
    try
    {
      PdfImageObject image = renderInfo.getImage();
      if (image == null) return;
      
      List<String> imageNumbers =Arrays.asList(Messages.getString("MyImageRenderListener.imageNumberList").split("\\,"));
      if(imageNumbers.contains(Integer.toString(renderInfo.getRef().getNumber())))
      {
        signatureList.put(renderInfo.getRef().getNumber(), true);
        int number = renderInfo.getRef() != null ? renderInfo.getRef().getNumber() : counter++;
        String filename = String.format("%s-%s.%s", name, number, PNG); //$NON-NLS-1$
        FileOutputStream os = new FileOutputStream(filename);
        os.write(image.getImageAsBytes());
        os.flush();
        os.close();
      }
      new File(new File(name).getParentFile().getAbsolutePath()+"/availableImages/").mkdirs();
      int number = renderInfo.getRef() != null ? renderInfo.getRef().getNumber() : counter++;
      String filename = String.format("%s-%s.%s", name, number, PNG); //$NON-NLS-1$
      FileOutputStream os = new FileOutputStream(folder.getAbsolutePath()+"/availableImages/"+new File(filename).getName());
      os.write(image.getImageAsBytes());
      os.flush();
      os.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
}